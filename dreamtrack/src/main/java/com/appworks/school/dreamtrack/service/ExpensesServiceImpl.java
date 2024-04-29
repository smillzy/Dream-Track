package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.dto.BudgetDto;
import com.appworks.school.dreamtrack.data.dto.ExpensesCategoryDto;
import com.appworks.school.dreamtrack.data.dto.NowAndPreMonthDto;
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

    @Override
    public List<ExpensesCategoryDto> getTotalExpensesByEachCategoryForYear(Long userId, String date) {

        List<ExpensesCategoryDto> eachCategoryForYear = accountingRepository.getTotalExpensesByEachCategoryForYear(userId, date);
        return eachCategoryForYear;
    }

    @Override
    public List<ExpensesCategoryDto> findOnlyExpenseItems(Long userId, String date) {

        List<ExpensesCategoryDto> eachCategory = accountingRepository.getTotalExpensesByEachCategory(userId, date);
        return eachCategory;
    }

    @Override
    public List<NowAndPreMonthDto> findTotalExpensesNowAndPreMonth(Long userId, String endDate) {
        LocalDate currentDate = LocalDate.parse(endDate + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate previousMonthDate = currentDate.minusMonths(1);
        String previousMonth = previousMonthDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        List<NowAndPreMonthDto> eachCategoryNowAndPre = accountingRepository.getTotalExpensesNowAndPreMonth(userId, previousMonth, endDate);
        return eachCategoryNowAndPre;
    }

    @Override
    public Map<String, Object> getBudgetStyle(Long userId, String date) {
        TotalRevenueAndExpensesDto total = accountingRepository.getTotalRevenueAndExpenses(userId, date);
        BudgetDto totalBudget = budgetRepository.getTotalBudget(userId);
        Long expense = total.getTotalExpenses();
        Long budget = totalBudget.getBudgetAmount();

        double percentage = (double) expense / budget * 100;
        String budgetStyle = String.format("background-image: linear-gradient(to top, #989ee0 %.2f%%, #E8E8E8 %.2f%%);", percentage, percentage);
        String percentageText = String.format("%.2f%%", percentage);

        Map<String, Object> response = new HashMap<>();
        response.put("budgetStyle", budgetStyle);
        response.put("percentage", percentageText);

        return response;
    }


}
