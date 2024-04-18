package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.form.BudgetForm;

public interface BudgetService {
    Boolean findUserId(Long userId);

    void insertBudget(BudgetForm budgetForm);

    void updateBudget(BudgetForm budgetForm);
}
