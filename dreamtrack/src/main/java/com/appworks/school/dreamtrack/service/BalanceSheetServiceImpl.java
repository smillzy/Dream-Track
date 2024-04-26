package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.dto.BalanceItemDto;
import com.appworks.school.dreamtrack.data.dto.BalanceSheetDto;
import com.appworks.school.dreamtrack.data.dto.BalanceSheetIntervalDto;
import com.appworks.school.dreamtrack.data.dto.NetIncomeDto;
import com.appworks.school.dreamtrack.repository.BalanceSheetRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
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
    public List<BalanceItemDto> findBalanceSheet(Long userId, String date) {
        BalanceSheetDto balanceSheet = balanceSheetRepository.getBalanceSheet(userId, date);
        if (balanceSheet != null) {
            return balanceSheet.toBalanceItemDtoList();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<BalanceItemDto> findBalanceSheetYear(Long userId, String year) {
        BalanceSheetDto balanceSheet = balanceSheetRepository.getBalanceSheetYear(userId, year);
        if (balanceSheet.getAssetCurrent() != 0 && balanceSheet.getAssetCurrencies() != 0 && balanceSheet.getAssetStock() != 0 && balanceSheet.getLiability() != 0) {
            return balanceSheet.toBalanceItemDtoList();
        } else {
            return Collections.emptyList();
        }
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

    @Override
    public BalanceSheetDto getTable(Long userId, String date) {
        return balanceSheetRepository.getBalanceSheet(userId, date);
    }

    @Override
    public BalanceSheetDto getTableYear(Long userId, String year) {
        return balanceSheetRepository.getBalanceSheetYear(userId, year);
    }

    @Override
    public List<NetIncomeDto> getIncome(Long userId, String date) {
        LocalDate currentDate = LocalDate.parse(date + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int currentYear = currentDate.getYear();
        String startDate = currentYear + "-01";

        return balanceSheetRepository.getNetIncome(userId, startDate, date);
    }
}
