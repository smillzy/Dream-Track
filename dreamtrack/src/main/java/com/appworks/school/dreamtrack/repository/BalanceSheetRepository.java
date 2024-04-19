package com.appworks.school.dreamtrack.repository;

import com.appworks.school.dreamtrack.data.dto.BalanceSheetDto;
import com.appworks.school.dreamtrack.data.dto.BalanceSheetIntervalDto;
import com.appworks.school.dreamtrack.data.dto.NetIncomeDto;

import java.util.List;
import java.util.Map;

public interface BalanceSheetRepository {
    List<Map<String, Object>> getFinancialDataForUser(Long userId);

    void insertBalanceSheet(Long userId, Long assetCurrent, Long assetCurrencies, Long stock, Long liability, Long netIncome);

    BalanceSheetDto getBalanceSheet(Long userId, String date);

    BalanceSheetIntervalDto getBalanceSheet(Long userId, String startDate, String endDate);

    List<NetIncomeDto> getNetIncome(Long userId, String startDate, String endDate);

}
