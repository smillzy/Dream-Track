package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.dto.CurrentDepositDto;
import com.appworks.school.dreamtrack.data.dto.ForeignCurrenciesDto;
import com.appworks.school.dreamtrack.data.dto.StockDto;
import com.appworks.school.dreamtrack.data.form.CurrentDepositForm;
import com.appworks.school.dreamtrack.data.form.ForeignCurrenciesForm;
import com.appworks.school.dreamtrack.data.form.StockForm;
import com.appworks.school.dreamtrack.model.CurrentDeposit;
import com.appworks.school.dreamtrack.model.ForeignCurrencies;
import com.appworks.school.dreamtrack.model.Stock;
import com.appworks.school.dreamtrack.repository.AssetsRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AssetsServiceImpl implements AssetsService {

    private final AssetsRepository assetsRepository;


    public AssetsServiceImpl(AssetsRepository assetsRepository) {
        this.assetsRepository = assetsRepository;
    }

    @Override
    public void insertAssetsCurrentDeposit(CurrentDepositForm currentDepositForm) {
        CurrentDeposit ac = CurrentDeposit.from(currentDepositForm);
        ac.setUserId(currentDepositForm.getUserId());
        assetsRepository.insertAssetsCurrentDeposit(ac.getUserId(), ac.getAmount());
    }

    @Override
    public void updateAssetsCurrentDeposit(CurrentDepositForm currentDepositForm) {
        CurrentDeposit c = CurrentDeposit.from(currentDepositForm);
        c.setId(currentDepositForm.getId());
        assetsRepository.updateAssetsCurrentDeposit(c.getId(), c.getAmount());
    }

    @Override
    public int getCurrencyId(String currencyName) {
        return assetsRepository.getCurrencyId(currencyName);
    }

    @Override
    public void insertAssetsForeignCurrencies(ForeignCurrenciesForm foreignCurrenciesForm) {
        int currencyId = getCurrencyId(foreignCurrenciesForm.getCurrencyName());
        ForeignCurrencies fc = ForeignCurrencies.from(foreignCurrenciesForm);
        fc.setUserId(foreignCurrenciesForm.getUserId());
        fc.setCurrencyId(currencyId);
        assetsRepository.insertAssetsForeignCurrencies(fc.getUserId(), fc.getCurrencyId(), fc.getAction(), fc.getQuantityForeign(), fc.getQuantityTWD(), fc.getRate());
    }

    @Override
    public void updateAssetsForeignCurrencies(ForeignCurrenciesForm foreignCurrenciesForm) {
        int currencyId = getCurrencyId(foreignCurrenciesForm.getCurrencyName());
        ForeignCurrencies fc = ForeignCurrencies.from(foreignCurrenciesForm);
        fc.setId(foreignCurrenciesForm.getId());
        fc.setCurrencyId(currencyId);
        assetsRepository.updateAssetsForeignCurrencies(fc.getId(), fc.getCurrencyId(), fc.getAction(), fc.getQuantityForeign(), fc.getQuantityTWD(), fc.getRate());
    }

    @Override
    public int getStockId(String symbol) {
        return assetsRepository.getStockId(symbol);
    }

    @Override
    public void insertAssetsStock(StockForm stockForm) {
        int stockId = getStockId(stockForm.getSymbol());
        Stock s = Stock.from(stockForm);
        s.setUserId(stockForm.getUserId());
        s.setStockId(stockId);
        assetsRepository.insertAssetsStock(s.getUserId(), s.getStockId(), s.getAction(), s.getPrice(), s.getQuantity(), s.getAmount());
    }

    @Override
    public void updateAssetsStock(StockForm stockForm) {
        int stockId = getStockId(stockForm.getSymbol());
        Stock s = Stock.from(stockForm);
        s.setId(stockForm.getId());
        s.setStockId(stockId);
        assetsRepository.updateAssetsStock(s.getId(), s.getStockId(), s.getAction(), s.getPrice(), s.getQuantity(), s.getAmount());
    }

    @Override
    public Map<String, Object> findAssets(Long userId) {

        List<CurrentDepositDto> currentDeposit = assetsRepository.findAllAssetsCurrentDeposit(userId);
        List<ForeignCurrenciesDto> foreignCurrenciesDto = assetsRepository.findAllAssetsForeignCurrencies(userId);
        List<StockDto> stockDto = assetsRepository.findAllAssetsStock(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("current_deposit", currentDeposit);
        response.put("foreign_currencies", foreignCurrenciesDto);
        response.put("stock", stockDto);
        return response;
    }
}
