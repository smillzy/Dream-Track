package com.appworks.school.dreamtrack.repository;

import java.util.List;
import java.util.Map;

public interface BalanceSheetRepository {
    List<Map<String, Object>> getFinancialDataForUser(Long userId);

    void insertBalanceSheet(Long userId, Long assetCurrent, Long assetCurrencies, Long stock, Long liability, Long netIncome);

    List<Map<String, Object>> getBalanceSheet(Long userId, String date);

    List<Map<String, Object>> getBalanceSheet(Long userId, String startDate, String endDate);

    List<Map<String, Object>> getNetIncome(Long userId, String startDate, String endDate);

}
