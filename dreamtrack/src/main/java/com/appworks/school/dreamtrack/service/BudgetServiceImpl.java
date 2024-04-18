package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.form.BudgetForm;
import com.appworks.school.dreamtrack.model.Budget;
import com.appworks.school.dreamtrack.repository.BudgetRepository;
import com.appworks.school.dreamtrack.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final UserRepository userRepository;

    private final BudgetRepository budgetRepository;

    public BudgetServiceImpl(UserRepository userRepository, BudgetRepository budgetRepository) {
        this.userRepository = userRepository;
        this.budgetRepository = budgetRepository;
    }

    @Override
    public Boolean findUserId(Long userId) {
        return userRepository.findUserId(userId);
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
}
