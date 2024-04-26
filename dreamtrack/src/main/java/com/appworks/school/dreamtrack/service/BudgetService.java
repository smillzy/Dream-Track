package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.dto.BudgetDto;
import com.appworks.school.dreamtrack.data.form.BudgetForm;

public interface BudgetService {

    void insertBudget(BudgetForm budgetForm);

    void updateBudget(BudgetForm budgetForm);

    BudgetDto getTotalBudget(Long userId);
}
