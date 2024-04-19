package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.dto.BalanceSheetDto;
import com.appworks.school.dreamtrack.data.dto.BalanceSheetIntervalDto;
import com.appworks.school.dreamtrack.data.dto.NetIncomeDto;
import com.appworks.school.dreamtrack.repository.BalanceSheetRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BalanceSheetServiceImpl implements BalanceSheetService {

    private final BalanceSheetRepository balanceSheetRepository;

    public BalanceSheetServiceImpl(BalanceSheetRepository balanceSheetRepository) {
        this.balanceSheetRepository = balanceSheetRepository;
    }

    @Override
    public Map<String, Object> findBalanceSheet(Long userId, String date) {
        BalanceSheetDto balanceSheet = balanceSheetRepository.getBalanceSheet(userId, date);

        LocalDate currentDate = LocalDate.parse(date + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int currentYear = currentDate.getYear();
        String startDate = currentYear + "-01";
        List<NetIncomeDto> netIncomeYear = balanceSheetRepository.getNetIncome(userId, startDate, date);

        Map<String, Object> response = new HashMap<>();
        response.put("balance_sheet", balanceSheet);
        response.put("net_income_year", netIncomeYear);
        return response;
    }

    @Override
    public Map<String, Object> findBalanceSheetInterval(Long userId, String endDate) {
        LocalDate currentDate = LocalDate.parse(endDate + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int currentYear = currentDate.getYear();
        String startDate = currentYear + "-01";

        BalanceSheetIntervalDto balanceSheet = balanceSheetRepository.getBalanceSheet(userId, startDate, endDate);
        List<NetIncomeDto> netIncomeYear = balanceSheetRepository.getNetIncome(userId, startDate, endDate);

        Map<String, Object> response = new HashMap<>();
        response.put("balance_sheet", balanceSheet);
        response.put("net_income_year", netIncomeYear);
        return response;
    }
}
