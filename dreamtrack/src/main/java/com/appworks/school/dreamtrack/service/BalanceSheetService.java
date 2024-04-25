package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.dto.BalanceItemDto;
import com.appworks.school.dreamtrack.data.dto.BalanceSheetDto;
import com.appworks.school.dreamtrack.data.dto.NetIncomeDto;

import java.util.List;
import java.util.Map;

public interface BalanceSheetService {
    List<BalanceItemDto> findBalanceSheet(Long userId, String date);

    List<BalanceItemDto> findBalanceSheetYear(Long userId, String year);

    Map<String, Object> findBalanceSheetInterval(Long userId, String endDate);

    BalanceSheetDto getTable(Long userId, String date);

    BalanceSheetDto getTableYear(Long userId, String year);

    List<NetIncomeDto> getIncome(Long userId, String date);
}
