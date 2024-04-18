package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.form.BudgetForm;
import com.appworks.school.dreamtrack.model.Budget;
import com.appworks.school.dreamtrack.repository.BudgetRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    public BudgetServiceImpl(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public void insertBudget(BudgetForm budgetForm) {
        Budget b = Budget.from(budgetForm);
        budgetRepository.insertBudget(b.getUserId(), b.getAmount());
    }

    @Override
    public void updateBudget(BudgetForm budgetForm) {
        Budget b = Budget.from(budgetForm);
        budgetRepository.updateBudget(b.getUserId(), b.getAmount());
    }

    @Override
    public Map<String, Long> getTotalBudget(Long userId) {
        Long totalBudget = budgetRepository.getTotalBudget(userId);

        Map<String, Long> response = new HashMap<>();
        response.put("budget_amount", totalBudget);
        return response;
    }
}
