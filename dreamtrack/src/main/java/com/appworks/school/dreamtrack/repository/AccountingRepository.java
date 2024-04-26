package com.appworks.school.dreamtrack.repository;

import com.appworks.school.dreamtrack.data.dto.AccountingDto;
import com.appworks.school.dreamtrack.data.dto.ExpensesCategoryDto;
import com.appworks.school.dreamtrack.data.dto.NowAndPreMonthDto;
import com.appworks.school.dreamtrack.data.dto.TotalRevenueAndExpensesDto;

import java.util.List;

public interface AccountingRepository {
    Long findCategoryId(String categoryName, String type);

    void insertAccountingRecord(Long userId, Long categoryId, Long amount);

    void deleteAccountingRecord(Long id);

    Boolean findAccountingId(Long id);

    List<AccountingDto> findAllAccounting(Long userId, String date);

    TotalRevenueAndExpensesDto getTotalRevenueAndExpenses(Long userId, String date);

    TotalRevenueAndExpensesDto getTotalRevenueAndExpenses(Long userId, String startDate, String endDate);

    List<ExpensesCategoryDto> getTotalExpensesByEachCategory(Long userId, String date);

    List<ExpensesCategoryDto> getTotalExpensesByEachCategoryForYear(Long userId, String date);

    List<ExpensesCategoryDto> getTotalExpensesForEachCategory(Long userId, String startDate, String endDate);

    void updateAccountingRecord(Long id, Long categoryId, Long amount);

    List<NowAndPreMonthDto> getTotalExpensesNowAndPreMonth(Long userId, String startDate, String endDate);

}
