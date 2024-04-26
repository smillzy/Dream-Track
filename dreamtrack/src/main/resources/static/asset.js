const assettBtn = document.getElementById("asset-btn");
const liabilityBtn = document.getElementById("liability-btn");
const assetWrapper = document.getElementById("asset-content");
const liabilityWrapper = document.getElementById("liability-content");
const allContents = document.querySelectorAll(".content");

const showDetail = (showedElement, hideElement) => {
    allContents.forEach((content) => {
        content.classList.add("hide");
    });
    document.querySelectorAll('.selected').forEach((element) => {
        element.classList.remove('selected');
    });
    showedElement.classList.remove("hide");
    hideElement.classList.add("hide");
    hideElement.querySelectorAll('input').forEach((input) => {
        input.value = '';
    });
};

assettBtn.addEventListener("click", () => {
    showDetail(assetWrapper, liabilityWrapper);
});

liabilityBtn.addEventListener("click", () => {
    showDetail(liabilityWrapper, assetWrapper);
});

//////// for wrapper ///////////

const hideDisplay = (showedElements, hideElements) => { // defult is hide
    if (Array.isArray(showedElements)) {
        showedElements.forEach(element => element.classList.remove("hide"));
    } else if (showedElements) {
        showedElements.classList.remove("hide");
    }

    if (Array.isArray(hideElements)) {
        hideElements.forEach(element => element.classList.add("hide"));
    } else if (hideElements) {
        hideElements.classList.add("hide");
    }
};

const selectedDisplay = (showedElements, hideElements) => {
    if (Array.isArray(showedElements)) {
        showedElements.forEach(element => element.classList.add("selected"));
    } else if (showedElements) {
        showedElements.classList.add("selected");
    }

    if (Array.isArray(hideElements)) {
        hideElements.forEach(element => element.classList.remove("selected"));
    } else if (hideElements) {
        hideElements.classList.remove("selected");
    }
};

function reset(container) {
    if (Array.isArray(container)) {
        container.forEach(input => {
            if (input) {
                input.value = '';
            }
        });
    } else if (container) {
        container.value = '';
    }
}

function clearSelections(container) {
    container.querySelectorAll('.selected').forEach(el => el.classList.remove('selected'));
}

// three type -> deposit, currencies, stock
const assetCurrentDeposit = document.getElementById("asset-current-deposit");
const assetForeignCurrencies = document.getElementById("asset-foreign-currencies");
const assetStock = document.getElementById("asset-stock");

// wrapper content without button
const currentDepositItem = document.getElementById("asset-current-deposit-wrapper");
const foreignCurrenciesItem = document.getElementById("asset-foreign-currencies-wrapper");
const stockItem = document.getElementById("asset-stock-wrapper");

// deposit content
const deposit = document.getElementById("asset-current-deposit-type-deposit");
const withdraw = document.getElementById("asset-current-deposit-type-withdraw");
const depositInput = document.getElementById("asset-current-deposit-amount-detail");

// foreign currencies content
const currenciesNameInput = document.getElementById("asset-foreign-currencies-name-detail");
const currencyBuy = document.getElementById("asset-foreign-currencies-buy");
const currencySale = document.getElementById("assete-foreign-currencies-sale");
const currenciesAmountForeignInput = document.getElementById("asset-foreign-currencies-amount-foreign-detail");
const currenciesAmountTwdInput = document.getElementById("asset-foreign-currencies-amount-twd-detail");
const currenciesRateInput = document.getElementById("asset-foreign-currencies-rate-detail");

// stock content
const stockNameInput = document.getElementById("asset-stock-name-detail");
const stockBuy = document.getElementById("asset-stock-action-buy");
const stockSale = document.getElementById("asset-stock-action-sale");
const stockPriceInput = document.getElementById("asset-stock-price-detail");
const stockQuantityInput = document.getElementById("asset-stock-quantity-detail");
const lot = document.getElementById("asset-stock-quantity-lot");
const share = document.getElementById("asset-stock-quantity-share");
const stockAmountInput = document.getElementById("asset-stock-amount-detail");


// button
const assetButton = document.getElementById("asset-button");

assetCurrentDeposit.addEventListener("click", () => {
    selectedDisplay(assetCurrentDeposit, [assetForeignCurrencies, assetStock]);
    clearSelections(currentDepositItem);
    hideDisplay(
        [currentDepositItem, assetButton],
        [foreignCurrenciesItem, stockItem]
    );
    reset(depositInput);
});

assetForeignCurrencies.addEventListener("click", () => {
    selectedDisplay(assetForeignCurrencies, [assetCurrentDeposit, assetStock]);
    clearSelections(foreignCurrenciesItem);
    hideDisplay(
        [foreignCurrenciesItem, assetButton],
        [currentDepositItem, stockItem]
    );
    reset([currenciesNameInput, currenciesAmountForeignInput, currenciesAmountTwdInput, currenciesRateInput]);
});

assetStock.addEventListener("click", () => {
    selectedDisplay(assetStock, [assetCurrentDeposit, assetForeignCurrencies]);
    clearSelections(stockItem);
    hideDisplay(
        [stockItem, assetButton],
        [currentDepositItem, foreignCurrenciesItem]
    );
    reset([stockNameInput, stockPriceInput, stockQuantityInput, stockAmountInput]);
});

deposit.addEventListener("click", () => {
    selectedDisplay(deposit, withdraw);
    reset(depositInput)
});

withdraw.addEventListener("click", () => {
    selectedDisplay(withdraw, deposit);
    reset(depositInput)
});

currencyBuy.addEventListener("click", () => {
    selectedDisplay(currencyBuy, currencySale);
    reset([currenciesAmountForeignInput, currenciesAmountTwdInput, currenciesRateInput])
});

currencySale.addEventListener("click", () => {
    selectedDisplay(currencySale, currencyBuy);
    reset([currenciesAmountForeignInput, currenciesAmountTwdInput, currenciesRateInput])
});

stockBuy.addEventListener("click", () => {
    selectedDisplay(stockBuy, stockSale);
    lot.classList.remove('selected')
    share.classList.remove('selected')
    reset([stockPriceInput, stockQuantityInput, stockAmountInput])
});

stockSale.addEventListener("click", () => {
    selectedDisplay(stockSale, stockBuy);
    lot.classList.remove('selected')
    share.classList.remove('selected')
    reset([stockPriceInput, stockQuantityInput, stockAmountInput])
});


lot.addEventListener("click", () => {
    selectedDisplay(lot, share);
});

share.addEventListener("click", () => {
    selectedDisplay(share, lot);
});

// liability

const liabilityPayment = document.getElementById("liability-action-payment");
const liabilityRepayment = document.getElementById("liability-action-repayment");
const liabilityItemInput = document.getElementById("liability-item-input");
const liabilityAmountInput = document.getElementById("liability-amount-input");

liabilityPayment.addEventListener("click", () => {
    selectedDisplay(liabilityPayment, liabilityRepayment);
    reset([liabilityItemInput, liabilityAmountInput])
});

liabilityRepayment.addEventListener("click", () => {
    selectedDisplay(liabilityRepayment, liabilityPayment);
    reset([liabilityItemInput, liabilityAmountInput])
});