package com.appworks.school.dreamtrack.repository;

import java.util.List;
import java.util.Map;

public interface AssetsRepository {
    void insertAssetsCurrentDeposit(Long id, Long currentDepositAmount);

    void updateAssetsCurrentDeposit(Long id, String date, Long currentDepositAmount);


    int getCurrencyId(String currencyName);

    void insertAssetsForeignCurrencies(Long id, int currencyId, String action, Long quantityForeign, Long quantityTWD, Float rate);

    void updateAssetsForeignCurrencies(Long id, String date, int currencyId, String action, Long quantityForeign, Long quantityTWD, Float rate);

    int getStockId(String symbol);

    void insertAssetsStock(Long id, int stockId, String action, Long price, int quantity, Long stockAmount);

    void updateAssetsStock(Long id, String date, int stockId, String action, Long price, int quantity, Long stockAmount);


}
