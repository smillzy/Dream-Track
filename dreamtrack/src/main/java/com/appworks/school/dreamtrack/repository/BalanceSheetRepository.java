package com.appworks.school.dreamtrack.repository;

import java.util.List;
import java.util.Map;

public interface BalanceSheetRepository {
    List<Map<String, Object>> getFinancialDataForUser(Long userId);

    void insertBalanceSheet(Long id, Long assetCurrent, Long assetCurrencies, Long stock, Long liability, Long netIncome);

    List<Map<String, Object>> getBalanceSheet(Long id, String date);

    List<Map<String, Object>> getBalanceSheet(Long id, String startDate, String endDate);

}
