package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.form.CurrentDepositForm;
import com.appworks.school.dreamtrack.data.form.ForeignCurrenciesForm;
import com.appworks.school.dreamtrack.data.form.StockForm;

import java.util.Map;

public interface AssetsService {
    void insertAssetsCurrentDeposit(CurrentDepositForm currentDepositForm);

    void updateAssetsCurrentDeposit(CurrentDepositForm currentDepositForm);

    int getCurrencyId(String currencyName);

    void insertAssetsForeignCurrencies(ForeignCurrenciesForm foreignCurrenciesForm);

    void updateAssetsForeignCurrencies(ForeignCurrenciesForm foreignCurrenciesForm);

    int getStockId(String symbol);

    void insertAssetsStock(StockForm stockForm);

    void updateAssetsStock(StockForm stockForm);

    Map<String, Object> findAssets(Long userId);
}
