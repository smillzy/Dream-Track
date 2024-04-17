package com.appworks.school.dreamtrack.repository;

import java.util.List;
import java.util.Map;

public interface AssetsRepository {
    void insertAssetsCurrentDeposit(Long userId, Long currentDepositAmount);

    void updateAssetsCurrentDeposit(Long id, Long currentDepositAmount);

    List<Map<String, Object>> findAllAssetsCurrentDeposit(Long userId);

    int getCurrencyId(String currencyName);

    void insertAssetsForeignCurrencies(Long userId, int currencyId, String action, Long quantityForeign, Long quantityTWD, Float rate);

    void updateAssetsForeignCurrencies(Long id, int currencyId, String action, Long quantityForeign, Long quantityTWD, Float rate);

    List<Map<String, Object>> findAllAssetsForeignCurrencies(Long userId);

    int getStockId(String symbol);

    void insertAssetsStock(Long userId, int stockId, String action, Long price, int quantity, Long stockAmount);

    void updateAssetsStock(Long id, int stockId, String action, Long price, int quantity, Long stockAmount);

    List<Map<String, Object>> findAllAssetsStock(Long userId);
}
