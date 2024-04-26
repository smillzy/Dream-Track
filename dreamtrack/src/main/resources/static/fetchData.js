let hostName = window.location.protocol;
let domainName = window.location.hostname;
let port = window.location.port;
console.log(hostName)
console.log(domainName)
console.log(port)

// Get data accounting
document
    .getElementById("accounting-button-single")
    .addEventListener("click", () => {
        const amount = document.getElementById("accounting-amount").value;
        const categoryElement = document.querySelector(
            "#accounting-type .selected"
        );
        const typeElement = document.querySelector(
            "#accounting-type-title-revenue .selected, #accounting-type-title-expense .selected"
        );

        const data = [{
            user_id: 1,
            category_name: typeElement.textContent,
            type: categoryElement.textContent,
            amount: amount,
        }];

        console.log(data);
        fetch("http://localhost:8080/api/v1/accounting", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        })
            .then(response => {
                if (response.ok) {
                    console.log("Success: HTTP status is 200");
                    window.location.reload();
                } else {
                    console.error('Failed: HTTP status is ' + response.status);
                    throw new Error('Failed to process the request with status ' + response.status);
                }
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    });

document.getElementById("accounting-button-multi").addEventListener("click", function () {
    const categoryMap = {
        'revenue': '收入',
        'expense': '支出'
    };
    const rows = document.getElementById("multi-entries-table").getElementsByTagName("tbody")[0].rows;
    const entries = [];

    for (let row of rows) {
        let category = row.cells[0].querySelector("select").value;
        const detail = row.cells[1].querySelector("select").value;
        const amount = parseInt(row.cells[2].querySelector("input").value);
        category = categoryMap[category]
        if (category && detail && !isNaN(amount)) {
            entries.push({
                user_id: 1,
                category_name: detail,
                type: category,
                amount: amount,
            });
        }
    }
    console.log(entries);
    if (entries.length > 0) {
        fetch('http://localhost:8080/api/v1/accounting', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(entries)
        })
            .then(response => {
                if (response.ok) {
                    console.log("Success: HTTP status is 200");
                    window.location.reload();
                } else {
                    console.error('Failed: HTTP status is ' + response.status);
                    throw new Error('Failed to process the request with status ' + response.status);
                }
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    } else {
        console.log('No valid entries to send.');
    }
});

// Get budget

document.getElementById("budget-button").addEventListener("click", () => {
    const amount = document.getElementById("budget-amount").value;

    const data = {
        user_id: 1,
        budget_amount: amount,
    };

    console.log(data);
    fetch("http://localhost:8080/api/v1/budget", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then(response => {
            if (response.ok) {
                console.log("Success: HTTP status is 200");
                window.location.reload();
            } else {
                console.error('Failed: HTTP status is ' + response.status);
                throw new Error('Failed to process the request with status ' + response.status);
            }
        })
        .catch((error) => {
            console.error("Error:", error);
        });
});

// // Get asset
//
// document.getElementById("asset-button").addEventListener("click", () => {
//     const assetType = document.querySelector("#asset-type .selected").textContent;
//     let data = {user_id: 1, asset_type: ""};
//
//     if (assetType === "活儲") {
//         data.asset_type = assetType;
//         const assetDepositType = document
//             .querySelector(".asset-current-deposit-type .selected")
//             .textContent.trim();
//         let amount = document.getElementById(
//             "asset-current-deposit-amount-detail"
//         ).value;
//         if (assetDepositType === "提出") {
//             amount = -Math.abs(parseFloat(amount));
//         }
//         data.current_deposit_amount = amount;
//
//         fetch("http://localhost:8080/api/v1/assets/current-deposit", {
//             method: "POST",
//             headers: {
//                 "Content-Type": "application/json",
//             },
//             body: JSON.stringify(data),
//         })
//             .then((response) => response.json())
//             .then((data) => console.log("Success:"))
//             .catch((error) => {
//                 console.error("Error:", error);
//             });
//     } else if (assetType === "外幣") {
//         data.asset_type = assetType;
//         data.currency_name = document.getElementById(
//             "asset-foreign-currencies-name-detail"
//         ).value;
//         const action = document.querySelector(
//             "#asset-foreign-currencies-wrapper .selected"
//         ).textContent;
//         let quantity_foreign = parseInt(
//             document.getElementById("asset-foreign-currencies-amount-foreign-detail")
//                 .value
//         );
//         let quantity_TWD = parseInt(
//             document.getElementById("asset-foreign-currencies-amount-twd-detail")
//                 .value
//         );
//         const rate = parseFloat(
//             document.getElementById("asset-foreign-currencies-rate-detail").value
//         );
//
//         if (action.trim() === "賣出") {
//             quantity_foreign = -Math.abs(parseFloat(quantity_foreign));
//             quantity_TWD = -Math.abs(parseFloat(quantity_TWD));
//         }
//         data.action = action;
//         data.quantity_foreign = quantity_foreign;
//         data.quantity_TWD = quantity_TWD;
//         data.rate = rate;
//
//         fetch("http://localhost:8080/api/v1/assets/foreign-currencies", {
//             method: "POST",
//             headers: {
//                 "Content-Type": "application/json",
//             },
//             body: JSON.stringify(data),
//         })
//             .then((response) => response.json())
//             .then((data) => console.log("Success:"))
//             .catch((error) => {
//                 console.error("Error:", error);
//             });
//
//     } else if (assetType === "股票") {
//         data.asset_type = assetType;
//         data.symbol = document.getElementById("asset-stock-name-detail").value;
//         const action = document.querySelector(
//             "#asset-stock-wrapper .selected"
//         ).textContent;
//         const price = parseFloat(
//             document.getElementById("asset-stock-price-detail").value
//         );
//         let quantity = parseInt(
//             document.getElementById("asset-stock-quantity-detail").value
//         );
//         const unit = document.querySelector(
//             ".asset-stock-quantity .selected"
//         ).textContent;
//         let stock_amount = parseInt(
//             document.getElementById("asset-stock-amount-detail").value
//         );
//         if (unit.trim() === "張") {
//             quantity = quantity * 1000;
//         }
//
//         if (action.trim() === "賣出") {
//             quantity = -Math.abs(parseFloat(quantity));
//             stock_amount = -Math.abs(parseFloat(stock_amount));
//         }
//         data.action = action;
//         data.price = price;
//         data.quantity = quantity;
//         data.stock_amount = stock_amount;
//
//         fetch("http://localhost:8080/api/v1/assets/stock", {
//             method: "POST",
//             headers: {
//                 "Content-Type": "application/json",
//             },
//             body: JSON.stringify(data),
//         })
//             .then((response) => response.json())
//             .then((data) => console.log("Success:"))
//             .catch((error) => {
//                 console.error("Error:", error);
//             });
//     }
//
//     console.log(data);
//
// });
//
// // Get liability
//
// document.getElementById("liability-button").addEventListener("click", () => {
//     const liabilityAction = document.querySelector("#liability-action .selected");
//     const liabilityItem = document.getElementById("liability-item-input").value;
//     let liabilityAmount = document.getElementById("liability-amount-input").value;
//
//     if (!liabilityAction) {
//         alert("請選取類別");
//         return;
//     }
//
//     if (liabilityAction.textContent.trim() === "還款") {
//         liabilityAmount = -Math.abs(parseFloat(liabilityAmount));
//     }
//
//     const data = {
//         user_id: 1,
//         item: liabilityItem,
//         action: liabilityAction.textContent,
//         liability_amount: liabilityAmount,
//     };
//     console.log(data);
//
//     fetch("http://localhost:8080/api/v1/liabilities", {
//         method: "POST",
//         headers: {
//             "Content-Type": "application/json",
//         },
//         body: JSON.stringify(data),
//     })
//         .then((response) => response.json())
//         .then((data) => console.log("Success:"))
//         .catch((error) => {
//             console.error("Error:", error);
//         });
// });
