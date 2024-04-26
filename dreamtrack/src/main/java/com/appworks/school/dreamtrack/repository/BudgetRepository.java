package com.appworks.school.dreamtrack.repository;

import com.appworks.school.dreamtrack.data.dto.BudgetDto;

public interface BudgetRepository {
    void insertBudget(Long userId, Long budgetAmount);

    void updateBudget(Long userId, Long budgetAmount);

    BudgetDto getTotalBudget(Long userId);
}
