package com.appworks.school.dreamtrack.repository;

import com.appworks.school.dreamtrack.data.dto.CurrentDepositDto;
import com.appworks.school.dreamtrack.data.dto.ForeignCurrenciesDto;
import com.appworks.school.dreamtrack.data.dto.StockDto;

import java.util.List;

public interface AssetsRepository {
    void insertAssetsCurrentDeposit(Long userId, Long currentDepositAmount);

    void updateAssetsCurrentDeposit(Long id, Long currentDepositAmount);

    List<CurrentDepositDto> findAllAssetsCurrentDeposit(Long userId);

    int getCurrencyId(String currencyName);

    void insertAssetsForeignCurrencies(Long userId, int currencyId, String action, Long quantityForeign, Long quantityTWD, Float rate);

    void updateAssetsForeignCurrencies(Long id, int currencyId, String action, Long quantityForeign, Long quantityTWD, Float rate);

    List<ForeignCurrenciesDto> findAllAssetsForeignCurrencies(Long userId);

    int getStockId(String symbol);

    void insertAssetsStock(Long userId, int stockId, String action, Long price, int quantity, Long stockAmount);

    void updateAssetsStock(Long id, int stockId, String action, Long price, int quantity, Long stockAmount);

    List<StockDto> findAllAssetsStock(Long userId);
}
