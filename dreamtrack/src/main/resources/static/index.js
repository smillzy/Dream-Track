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

document.addEventListener("DOMContentLoaded", function () {
    var calendarEl = document.getElementById("calendar");
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: "dayGridMonth",
        editable: false,
        events: [
            {
                id: 1,
                title: "薪水",
                start: "2024-04-03",
                description: "Description for Event1",
                amount: 5000,
                type: "收入",
                item: "薪水",
            },
            {
                id: 2,
                title: "食物",
                start: "2024-04-02",
                description: "Description for Event2",
                amount: 300,
                type: "支出",
                item: "食物",
            },
        ],
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
            var contentEl = document.createElement("span");
            var titleEl = document.createElement("b");
            titleEl.textContent = arg.event.title + " - ";

            var amountEl = document.createElement("span");
            amountEl.textContent = "$" + arg.event.extendedProps.amount;

            contentEl.appendChild(titleEl);
            contentEl.appendChild(amountEl);

            return {domNodes: [contentEl]};
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

    $("#eventModal").on("shown.bs.modal", function () {
        $("#saveEvent")
            .off("click")
            .on("click", function () {
                var rows = document.querySelectorAll(
                    "#eventEntries tr:not(:empty)"
                );

                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var type = row.querySelector(".type").value;
                    var title = row.querySelector(".title").value;
                    var description = row.querySelector(".description").value;
                    var amount = row.querySelector(".amount").value;

                    if (!title.trim() || !type.trim() || !amount.trim()) {
                        alert(
                            "Please fill in all required fields (title, type, amount)."
                        );
                        break;
                    }

                    calendar.addEvent({
                        title: title,
                        description: description,
                        amount: parseFloat(amount),
                        type: type,
                        start: currentEvent.start,
                        allDay: currentEvent.allDay,
                    });
                }
                $("#eventModal").modal("hide");
            });
    });

    $("#eventModal2").on("shown.bs.modal", function () {
        $("#saveEvent2")
            .off("click")
            .on("click", function () {
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
                // 更新現有事件
                currentEvent.setProp("title", title);
                currentEvent.setExtendedProp("description", description);
                currentEvent.setExtendedProp("amount", parseFloat(amount));
                currentEvent.setExtendedProp("type", type);

                $("#eventModal2").modal("hide");
            });

        $("#deleteEvent2")
            .off("click")
            .on("click", function () {
                var eventId = $("#eventId").val();
                var event = calendar.getEventById(eventId);
                if (
                    event &&
                    confirm("Are you sure you want to delete this event?")
                ) {
                    event.remove();
                    $("#eventModal2").modal("hide");
                }
            });
    });

    // 更新下拉選單
    $("#type").on("change", function () {
        updateItemsDropdown(this.value);
    });
});

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