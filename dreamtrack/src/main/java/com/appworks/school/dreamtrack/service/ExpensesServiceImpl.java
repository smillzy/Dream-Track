package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.dto.BudgetDto;
import com.appworks.school.dreamtrack.data.dto.ExpensesCategoryDto;
import com.appworks.school.dreamtrack.data.dto.TotalRevenueAndExpensesDto;
import com.appworks.school.dreamtrack.repository.AccountingRepository;
import com.appworks.school.dreamtrack.repository.BudgetRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpensesServiceImpl implements ExpensesService {

    private final AccountingRepository accountingRepository;

    private final BudgetRepository budgetRepository;


    public ExpensesServiceImpl(AccountingRepository accountingRepository, BudgetRepository budgetRepository) {
        this.accountingRepository = accountingRepository;
        this.budgetRepository = budgetRepository;
    }

    @Override
    public Map<String, Object> findExpenses(Long userId, String date) {
        LocalDate currentDate = LocalDate.parse(date + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate previousMonthDate = currentDate.minusMonths(1);
        String previousMonth = previousMonthDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        TotalRevenueAndExpensesDto total = accountingRepository.getTotalRevenueAndExpenses(userId, date);
        List<ExpensesCategoryDto> eachCategory = accountingRepository.getTotalExpensesByEachCategory(userId, date);
        List<ExpensesCategoryDto> eachCategoryPreMonth = accountingRepository.getTotalExpensesByEachCategory(userId, previousMonth);
        BudgetDto budget = budgetRepository.getTotalBudget(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("total_revenue", total.getTotalRevenue());
        response.put("total_expense", total.getTotalExpenses());
        response.put("month_expense", eachCategory);
        response.put("pre_month_expense", eachCategoryPreMonth);
        response.put("budget", budget.getBudgetAmount());
        return response;
    }

    @Override
    public Map<String, Object> findExpensesInterval(Long userId, String startDate, String endDate) {
        TotalRevenueAndExpensesDto total = accountingRepository.getTotalRevenueAndExpenses(userId, startDate, endDate);
        List<ExpensesCategoryDto> totalEachCategory = accountingRepository.getTotalExpensesForEachCategory(userId, startDate, endDate);

        Map<String, Object> response = new HashMap<>();
        response.put("total_revenue", total.getTotalRevenue());
        response.put("total_expense", total.getTotalExpenses());
        response.put("month_expense", totalEachCategory);
        return response;
    }
}
