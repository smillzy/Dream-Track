let hostName = window.location.protocol;
let domainName = window.location.hostname;
let port = window.location.port;
let events;
let calendar;

document.addEventListener("DOMContentLoaded", function () {
    parseURLParameter();
});
const parseURLParameter = async () => {
    try {
        const homeURL = `${hostName}//${domainName}:${port}/index`;
        const homeToken = localStorage.getItem('token');
        if (homeToken === null) {
            window.location.href = '/sign';
        }
        const response = await fetch(homeURL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${homeToken}`
            }
        });

        if (!response.ok) {
            alert("There is something wrong!");
        }

        events = await response.json();
        renderCalendar(events);
    } catch (error) {
        console.error('Error fetching profile data:', error);
    }

};

function renderCalendar(events) {
    console.log(events)
    var calendarEl = document.getElementById("calendar");
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: "dayGridMonth",
        editable: false,
        events: events,
        eventClassNames: function (arg) {
            return arg.event.extendedProps.type === "收入"
                ? ["income-event"]
                : ["expense-event"];
        },
        eventClick: function (info) {
            isNewEvent = false;
            currentEvent = info.event;
            $("#eventId").val(currentEvent.id);
            $("#title").val(currentEvent.title);
            $("#description").val(
                currentEvent.extendedProps?.description || ""
            );
            $("#amount").val(currentEvent.extendedProps?.amount || "");
            $("#type").val(currentEvent.extendedProps?.type || "支出");
            updateItemsDropdown(
                currentEvent.extendedProps?.type || "支出",
                currentEvent.extendedProps?.item
            );
            $("#eventModal2").modal("show");
        },
        dateClick: function (info) {
            isNewEvent = true;
            currentEvent = {
                title: "new",
                start: info.dateStr,
                allDay: true,
            };
            var entries = document.getElementById("eventEntries");
            entries.innerHTML = "";
            entries.appendChild(createEventRow(currentEvent));
            $("#eventModal").modal("show");
        },
        eventContent: function (arg) {
            // Creating a span that will contain both the title and the amount
            var titleEl = document.createElement("span");
            titleEl.textContent = arg.event.title;

            var amountEl = document.createElement("span");
            amountEl.textContent = "$" + arg.event.extendedProps.amount;

            return {domNodes: [titleEl, amountEl]};
        },
    });

    calendar.render();

    document
        .getElementById("addEvent")
        .addEventListener("click", function () {
            var entries = document.getElementById("eventEntries");
            entries.appendChild(createEventRow());
        });

    function createEventRow(event = {}) {
        var row = document.createElement("tr");
        return row;
    }

    let token = localStorage.getItem('token');

    $("#eventModal").on("shown.bs.modal", function () {
        $("#saveEvent")
            .off("click")
            .on("click", async function () {
                try {
                    var events = [];
                    var rows = document.querySelectorAll("#eventEntries tr:not(:empty)");

                    for (var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        var type = row.querySelector(".type").value;
                        var title = row.querySelector(".title").value;
                        var description = row.querySelector(".description").value;
                        var amount = row.querySelector(".amount").value;
                        let date = currentEvent.start;

                        if (!description.trim()) {
                            description = "無輸入";
                        }

                        if (!title.trim() || !type.trim() || !amount.trim()) {
                            alert("Please fill in all required fields (title, type, amount).");
                            return;
                        }

                        events.push({
                            title: title,
                            description: description,
                            amount: parseFloat(amount),
                            type: type,
                            date: date
                        });
                    }
                    const accountingPostURL = `${hostName}//${domainName}:${port}/api/v1/accounting`;
                    console.log(events)
                    const response = await fetch(accountingPostURL, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${token}`
                        },
                        body: JSON.stringify(events)
                    });

                    if (!response.ok) {
                        throw new Error('Network response was not ok.');
                    }

                    const data = await response.json();
                    console.log('Login successful:', data);
                    data.forEach(eventData => {
                        calendar.addEvent({
                            id: eventData.id,
                            title: eventData.title,
                            start: currentEvent.start,
                            description: eventData.description,
                            amount: parseFloat(eventData.amount),
                            type: eventData.type,
                            item: eventData.item
                        });
                    });

                    $("#eventModal").modal("hide");
                } catch (error) {
                    console.error('Error:', error);
                    alert('Error logging in');
                }
            });

        $("#eventModal2").on("shown.bs.modal", function () {
            $("#saveEvent2")
                .off("click")
                .on("click", async function () {
                    try {
                        var eventInputElement = document.getElementById("eventId");
                        var title = $("#title").val();
                        var description = $("#description").val();
                        var amount = $("#amount").val();
                        var type = $("#type").val();
                        if (!title || !type || !amount) {
                            alert(
                                "Please fill in all required fields (title, type, amount)."
                            );
                            return;
                        }

                        let updateEvent = {
                            id: eventInputElement.value,
                            title: title,
                            description: description,
                            amount: parseFloat(amount),
                            type: type
                        };

                        const accountingPatchURL = `${hostName}//${domainName}:${port}/api/v1/accounting`;
                        console.log(updateEvent)
                        const response = await fetch(accountingPatchURL, {
                            method: 'PATCH',
                            headers: {
                                'Content-Type': 'application/json',
                                'Authorization': `Bearer ${token}`
                            },
                            body: JSON.stringify(updateEvent)
                        });

                        if (!response.ok) {
                            throw new Error('Network response was not ok.');
                        }

                        console.log('Successful');
                        // 更新現有事件
                        currentEvent.setProp("title", title);
                        currentEvent.setExtendedProp("description", description);
                        currentEvent.setExtendedProp("amount", parseFloat(amount));
                        currentEvent.setExtendedProp("type", type);

                        $("#eventModal2").modal("hide");
                    } catch (error) {
                        console.error('Error:', error);
                        alert('Error');
                    }
                });

            $("#deleteEvent2")
                .off("click")
                .on("click", async function () {
                    try {
                        var eventInputElement = document.getElementById("eventId");
                        var eventId = $("#eventId").val();
                        var event = calendar.getEventById(eventId);
                        if (
                            event &&
                            confirm("Are you sure you want to delete this event?")
                        ) {
                            let deleteEvent = {
                                id: eventInputElement.value
                            };

                            const accountingDeleteURL = `${hostName}//${domainName}:${port}/api/v1/accounting`;
                            console.log(deleteEvent)
                            const response = await fetch(accountingDeleteURL, {
                                method: 'DELETE',
                                headers: {
                                    'Content-Type': 'application/json',
                                    'Authorization': `Bearer ${token}`
                                },
                                body: JSON.stringify(deleteEvent)
                            });

                            if (!response.ok) {
                                throw new Error('Network response was not ok.');
                            }

                            event.remove();
                            $("#eventModal2").modal("hide");
                        }
                    } catch (error) {
                        console.error('Error:', error);
                        alert('Error');
                    }
                });
        });

        // 更新下拉選單
        $("#type").on("change", function () {
            updateItemsDropdown(this.value);
        });
    });
}


function updateItemsDropdown(type, selectedItem) {
    var itemSelect = document.getElementById("title");
    itemSelect.innerHTML = "";

    var items =
        type === "收入"
            ? ["薪水", "獎金", "季獎金", "三節", "年終", "股利", "利息", "其他"]
            : ["食物", "飲料", "治裝", "交通", "居家", "醫藥", "保險", "其他"];

    items.forEach(function (item) {
        var option = document.createElement("option");
        option.value = item;
        option.textContent = item;
        itemSelect.appendChild(option);
    });

    if (selectedItem) {
        itemSelect.value = selectedItem;
    } else {
        itemSelect.value = items[0];
    }
}

// Go to log in page
const logInform = document.getElementById("login");

logInform.addEventListener("click", () => {
    window.location.href = '/sign';
});

document.addEventListener("DOMContentLoaded", function () {
    document
        .getElementById("addEvent")
        .addEventListener("click", function () {
            var entries = document.getElementById("eventEntries");
            var newRow = document.createElement("tr");

            var typeSelectHtml = `<select class="form-control type" onchange="updateItems(this)">
            <option>收入</option>
            <option>支出</option>
        </select>`;
            var titleSelectHtml = `<select class="form-control title"></select>`;

            newRow.innerHTML = `
            <td>${typeSelectHtml}</td>
            <td>${titleSelectHtml}</td>
            <td><textarea class="form-control description"></textarea></td>
            <td><input type="number" class="form-control amount"></td>
            <td><button type="button" class="btn btn-danger removeEvent">刪除</button></td>
        `;

            entries.appendChild(newRow);
            updateItems(newRow.querySelector(".type")); // Initialize title dropdown based on type
        });

    document
        .getElementById("eventEntries")
        .addEventListener("click", function (event) {
            if (event.target.classList.contains("removeEvent")) {
                event.target.closest("tr").remove();
            }
        });
});

function updateItems(typeSelectElement) {
    var itemSelect = typeSelectElement
        .closest("tr")
        .querySelector(".title");
    var items =
        typeSelectElement.value === "收入"
            ? ["薪水", "獎金", "季獎金", "三節", "年終", "股利", "利息", "其他"]
            : ["食物", "飲料", "治裝", "交通", "居家", "醫藥", "保險", "其他"];

    itemSelect.innerHTML = "";
    items.forEach(function (item) {
        var option = document.createElement("option");
        option.value = item;
        option.textContent = item;
        itemSelect.appendChild(option);
    });
}