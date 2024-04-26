package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.dto.ExpensesCategoryDto;
import com.appworks.school.dreamtrack.data.dto.NowAndPreMonthDto;

import java.util.List;
import java.util.Map;

public interface ExpensesService {

    Map<String, Object> findExpenses(Long userId, String date);

    Map<String, Object> findExpensesInterval(Long userId, String startDate, String endDate);

    List<ExpensesCategoryDto> getTotalExpensesByEachCategoryForYear(Long userId, String date);

    List<ExpensesCategoryDto> findOnlyExpenseItems(Long userId, String date);

    List<NowAndPreMonthDto> findTotalExpensesNowAndPreMonth(Long userId, String date);

    Map<String, Object> getBudgetStyle(Long userId, String date);
}
