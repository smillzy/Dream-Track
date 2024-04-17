package com.appworks.school.dreamtrack.repository;

public interface BudgetRepository {
    void insertBudget(Long userId, Long budgetAmount);

    void updateBudget(Long userId, Long budgetAmount);

    Long getTotalBudget(Long userId);
}
