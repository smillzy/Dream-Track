package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.dto.BudgetDto;
import com.appworks.school.dreamtrack.data.dto.TotalRevenueAndExpensesDto;
import com.appworks.school.dreamtrack.repository.AccountingRepository;
import com.appworks.school.dreamtrack.repository.BudgetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WebPageServiceImpl implements WebPageService {

    private final AccountingRepository accountingRepository;

    private final BudgetRepository budgetRepository;


    public WebPageServiceImpl(AccountingRepository accountingRepository, BudgetRepository budgetRepository) {
        this.accountingRepository = accountingRepository;
        this.budgetRepository = budgetRepository;
    }

    @Override
    public Map<String, Object> findExpensesForMonth(Long userId, String date) {
        TotalRevenueAndExpensesDto total = accountingRepository.getTotalRevenueAndExpenses(userId, date);
        BudgetDto budget = budgetRepository.getTotalBudget(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("budget", budget.getBudgetAmount());
        response.put("total_expense", total.getTotalExpenses());
        response.put("total_revenue", total.getTotalRevenue());
        return response;
    }

    @Override
    public Map<String, Object> findExpensesForHalfMonth(Long userId, String startDate, String endDate) {
        TotalRevenueAndExpensesDto total = accountingRepository.getTotalRevenueAndExpenses(userId, startDate, endDate);
        BudgetDto budget = budgetRepository.getTotalBudget(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("budget", budget.getBudgetAmount());
        response.put("total_expense", total.getTotalExpenses());
        response.put("total_revenue", total.getTotalRevenue());
        return response;
    }

    @Override
    public Map<String, Object> findExpensesForYear(Long userId, String year) {
        TotalRevenueAndExpensesDto total = accountingRepository.getTotalRevenueAndExpensesForYear(userId, year);
        BudgetDto budget = budgetRepository.getTotalBudget(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("budget", budget.getBudgetAmount());
        response.put("total_expense", total.getTotalExpenses());
        response.put("total_revenue", total.getTotalRevenue());
        return response;
    }
}
