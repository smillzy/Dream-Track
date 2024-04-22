// gen multi column
function addNewRow() {
    const table = document
        .getElementById("multi-entries-table")
        .getElementsByTagName("tbody")[0];
    const newRow = table.insertRow(table.rows.length);
    newRow.innerHTML = `
      <td>
      <select onchange="updateDetails(this)">
        <option value="" disabled selected>請選擇類別</option>
        <option value="revenue">收入</option>
        <option value="expense">支出</option>
      </select>
      </td>
      <td>
        <select>
          <!-- 細項選項將根據上面選擇動態更新 -->
        </select>
      </td>
      <td>
        <input type="number" name="amount" min="1" required>
      </td>
    `;
}

function updateDetails(categorySelect) {
    const detailsSelect =
        categorySelect.parentNode.nextElementSibling.querySelector("select");

    detailsSelect.innerHTML = "";

    let options = [];
    if (categorySelect.value === "revenue") {
        options = [
            "薪水",
            "獎金",
            "季獎金",
            "三節",
            "年終",
            "股利",
            "利息",
            "其他",
        ];
    } else if (categorySelect.value === "expense") {
        options = ["食物", "飲料", "治裝", "交通", "居家", "醫藥", "保險", "其他"];
    }

    options.forEach(function (option) {
        var newOption = document.createElement("option");
        newOption.value = option;
        newOption.text = option;
        detailsSelect.appendChild(newOption);
    });
}

// For main feat -> accounting, budget, asset, liability
const accountingBtn = document.getElementById("accounting");
const budgetBtn = document.getElementById("budget");
const assettBtn = document.getElementById("asset");
const liabilityBtn = document.getElementById("liability");
const allContents = document.querySelectorAll(".content");

const accountingWrapper = document.getElementById("accounting-content");
const budgetWrapper = document.getElementById("budget-content");
const assetWrapper = document.getElementById("asset-content");
const liabilityWrapper = document.getElementById("liability-content");

const multi = document.getElementById("accounting-multi-entries");

const showDetail = (showedElement, hideElementArray) => {
    multi.style.display = "none";
    document.querySelectorAll('.selected').forEach((element) => {
        element.classList.remove('selected');
    });
    allContents.forEach((content) => {
        content.classList.add("hide");
    });
    showedElement.classList.remove("hide");
    hideElementArray.forEach((element) => {
        element.classList.add("hide");
    });
};

accountingBtn.addEventListener("click", () => {
    showDetail(accountingWrapper, [
        budgetWrapper,
        assetWrapper,
        liabilityWrapper,
    ]);
});
budgetBtn.addEventListener("click", () => {
    reset(document.getElementById("budget-amount"))
    showDetail(budgetWrapper, [
        accountingWrapper,
        assetWrapper,
        liabilityWrapper,
    ]);
});
assettBtn.addEventListener("click", () => {
    showDetail(assetWrapper, [
        accountingWrapper,
        budgetWrapper,
        liabilityWrapper,
    ]);
});
liabilityBtn.addEventListener("click", () => {
    showDetail(liabilityWrapper, [
        accountingWrapper,
        assetWrapper,
        budgetWrapper,
    ]);
});

// For accounting datail feature

const showElement = (showedElement, hideElement) => {
    showedElement.forEach((element) => element.classList.remove("hide"));
    hideElement.classList.add("hide");
};

const showSingleOrMultiElement = (showedElement, hideElement) => {
    showedElement.classList.remove("hide");
    hideElement.classList.add("hide");
};

const showMultiElement = (showedElement, hideElementArray) => {
    showedElement.classList.remove("hide");
    hideElementArray.forEach((element) => {
        element.classList.add("hide");
    });
};

const accountMulti = document.getElementById("accounting-type-method-multi");
const accountMultiItem = document.getElementById("accounting-multi-entries");
const accountSingle = document.getElementById("accounting-type-method-single");
const accountType = document.getElementById("accounting-type");
const accountRevenue = document.getElementById("accounting-type-revenue");
const accountExpense = document.getElementById("accounting-type-expense");
const accountTitle = document.getElementById("accounting-type-title");
const accountItemTitle = document.getElementById("accounting-item-title");
const accountTitleRevenue = document.getElementById(
    "accounting-type-title-revenue"
);
const accountTitleExpense = document.getElementById(
    "accounting-type-title-expense"
);

const accountItem = document.getElementById("accounting-item");
const accountButton = document.getElementById("accounting-button-single");
const accountRevenueTitle = document.getElementById("accounting-type-title-revenue");
const accountExpenseTitle = document.getElementById("accounting-type-title-expense");

function resetMultiEntries() {
    const tableBody = document.getElementById("multi-entries-table").getElementsByTagName("tbody")[0];

    while (tableBody.rows.length > 0) {
        tableBody.deleteRow(0);
    }

    addNewRow();
}

accountMulti.addEventListener("click", () => {
    resetMultiEntries();
    accountMulti.classList.add("selected");
    accountSingle.classList.remove("selected");
    showMultiElement(accountMultiItem, [accountType, accountItem, accountRevenueTitle, accountExpenseTitle, accountButton, accountItemTitle]);
    accountMultiItem.style.display = "flex";
});

accountSingle.addEventListener("click", () => {
    accountSingle.classList.add("selected");
    accountMulti.classList.remove("selected");
    clearSelections(document.getElementById("accounting-type"));
    multi.style.display = "none";
    showSingleOrMultiElement(accountType, accountMultiItem);
});

function resetDetails(container) {
    container.querySelectorAll('.selected').forEach(el => el.classList.remove('selected'));

    const amountInput = document.getElementById("accounting-amount");
    if (amountInput) {
        amountInput.value = '';
    }
}

accountRevenue.addEventListener("click", () => {
    resetDetails(document.getElementById("accounting-type-title-revenue"));
    accountRevenue.classList.add("selected");
    accountExpense.classList.remove("selected");
    showElement(
        [accountItemTitle, accountTitleRevenue, accountItem, accountButton],
        accountTitleExpense
    );
});
accountExpense.addEventListener("click", () => {
    resetDetails(document.getElementById("accounting-type-title-expense"));
    accountExpense.classList.add("selected");
    accountRevenue.classList.remove("selected");
    showElement(
        [accountItemTitle, accountTitleExpense, accountItem, accountButton],
        accountTitleRevenue
    );
});

function clearSelections(container) {
    container.querySelectorAll('.selected').forEach(el => el.classList.remove('selected'));
}

// Select detail element (16 items)
function handleRevenueItemSelection(event) {
    const clickedElement = event.target;
    clearSelections(clickedElement.parentNode); // Remove element which had been add bofore
    clickedElement.classList.add('selected'); // Add class which is selected now
}

document.querySelectorAll('.revenue').forEach(item => {
    item.addEventListener('click', handleRevenueItemSelection);
});

document.querySelectorAll('.expense').forEach(item => {
    item.addEventListener('click', handleRevenueItemSelection);
});

// Asset
function reset(container) {
    const amountInput = container;
    if (amountInput) {
        amountInput.value = '';
    }
}

const showAssetElement = (showedElement, hideElementArray) => {
    showedElement.forEach((element) => element.classList.remove("hide"));
    hideElementArray.forEach((element) => {
        element.classList.add("hide");
    });
};

const assetCurrentDeposit = document.getElementById("asset-current-deposit");
const assetForeignCurrencies = document.getElementById(
    "asset-foreign-currencies"
);
const assetStock = document.getElementById("asset-stock");

const currentDepositItem = document.getElementById(
    "asset-current-deposit-wrapper"
);
const foreignCurrenciesItem = document.getElementById(
    "asset-foreign-currencies-wrapper"
);
const stockItem = document.getElementById("asset-stock-wrapper");

const assetButton = document.getElementById("asset-button");

assetCurrentDeposit.addEventListener("click", () => {
    assetCurrentDeposit.classList.add("selected");
    assetForeignCurrencies.classList.remove("selected");
    assetStock.classList.remove("selected");
    clearSelections(document.getElementById("asset-current-deposit-wrapper"));
    showAssetElement(
        [currentDepositItem, assetButton],
        [foreignCurrenciesItem, stockItem]
    );
});

assetForeignCurrencies.addEventListener("click", () => {
    assetForeignCurrencies.classList.add("selected");
    assetCurrentDeposit.classList.remove("selected");
    assetStock.classList.remove("selected");
    reset(document.getElementById("asset-foreign-currencies-name-detail"))
    reset(document.getElementById("asset-foreign-currencies-amount-foreign-detail"))
    reset(document.getElementById("asset-foreign-currencies-amount-twd-detail"))
    reset(document.getElementById("asset-foreign-currencies-rate-detail"))
    clearSelections(document.getElementById("asset-foreign-currencies-wrapper"));
    showAssetElement(
        [foreignCurrenciesItem, assetButton],
        [currentDepositItem, stockItem]
    );
});

assetStock.addEventListener("click", () => {
    assetStock.classList.add("selected");
    assetForeignCurrencies.classList.remove("selected");
    assetCurrentDeposit.classList.remove("selected");
    clearSelections(document.getElementById("asset-stock-wrapper"));
    showAssetElement(
        [stockItem, assetButton],
        [currentDepositItem, foreignCurrenciesItem]
    );
});

const deposit = document.getElementById("asset-current-deposit-type-deposit");
const withdraw = document.getElementById("asset-current-deposit-type-withdraw");

deposit.addEventListener("click", () => {
    deposit.classList.add("selected");
    withdraw.classList.remove("selected");
    reset(document.getElementById("asset-current-deposit-amount-detail"))
});

withdraw.addEventListener("click", () => {
    withdraw.classList.add("selected");
    deposit.classList.remove("selected");
});

const currencyBuy = document.getElementById("asset-foreign-currencies-buy");
const currencySale = document.getElementById("assete-foreign-currencies-sale");

currencyBuy.addEventListener("click", () => {
    currencyBuy.classList.add("selected");
    currencySale.classList.remove("selected");
    reset(document.getElementById("asset-foreign-currencies-amount-foreign-detail"))
    reset(document.getElementById("asset-foreign-currencies-amount-twd-detail"))
    reset(document.getElementById("asset-foreign-currencies-rate-detail"))
});

currencySale.addEventListener("click", () => {
    currencySale.classList.add("selected");
    currencyBuy.classList.remove("selected");
    reset(document.getElementById("asset-foreign-currencies-amount-foreign-detail"))
    reset(document.getElementById("asset-foreign-currencies-amount-twd-detail"))
    reset(document.getElementById("asset-foreign-currencies-rate-detail"))
});

const stockBuy = document.getElementById("asset-stock-action-buy");
const stockSale = document.getElementById("asset-stock-action-sale");

stockBuy.addEventListener("click", () => {
    stockBuy.classList.add("selected");
    stockSale.classList.remove("selected");
});

stockSale.addEventListener("click", () => {
    stockSale.classList.add("selected");
    stockBuy.classList.remove("selected");
});

const lot = document.getElementById("asset-stock-quantity-lot");
const share = document.getElementById("asset-stock-quantity-share");

lot.addEventListener("click", () => {
    lot.classList.add("selected");
    share.classList.remove("selected");
});

share.addEventListener("click", () => {
    share.classList.add("selected");
    lot.classList.remove("selected");
});

// liability

const liabilityPayment = document.getElementById("liability-action-payment");
const liabilityRepayment = document.getElementById("liability-action-repayment");

liabilityPayment.addEventListener("click", () => {
    liabilityPayment.classList.add("selected");
    liabilityRepayment.classList.remove("selected");
    reset(document.getElementById("liability-item-input"))
    reset(document.getElementById("liability-amount-input"))
});

liabilityRepayment.addEventListener("click", () => {
    liabilityRepayment.classList.add("selected");
    liabilityPayment.classList.remove("selected");
    reset(document.getElementById("liability-item-input"))
    reset(document.getElementById("liability-amount-input"))
});


