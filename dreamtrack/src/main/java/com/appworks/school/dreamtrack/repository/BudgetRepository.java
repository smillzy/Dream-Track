package com.appworks.school.dreamtrack.repository;

public interface BudgetRepository {
    void insertBudget(Long id, Long budgetAmount);

    void updateBudget(Long id, Long budgetAmount);

    Long getTotalBudget(Long id);
}
