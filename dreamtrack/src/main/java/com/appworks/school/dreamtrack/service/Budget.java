package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.repository.BudgetRepository;
import com.appworks.school.dreamtrack.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
@Slf4j
@ComponentScan(basePackages = {"com.appworks.school.dreamtrack"})
public class Budget implements CommandLineRunner {

    private final UserRepository userRepository;

    private final BudgetRepository budgetRepository;


    public Budget(UserRepository userRepository, BudgetRepository budgetRepository) {
        this.userRepository = userRepository;
        this.budgetRepository = budgetRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Budget.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String userName = "Kash";
        String userEmail = "Kash@gmail.com";
        String userPassword = "test123";

        // insertUserInfo(userName, userEmail, userPassword);
        Long userId = getUserId(userEmail);
        log.info("Receive userId: " + userId);

//        // Insert budget
//        Long budgetAmount = Long.valueOf(50000);
//        insertBudget(userId, budgetAmount);
//        log.info("Insert Budget: " + budgetAmount);

//        // Update budget
//        Long budgetAmountChange = Long.valueOf(60000);
//        updateBudget(userId, budgetAmountChange);
//        log.info("Update Budget: " + budgetAmountChange);

//        // Get budget
        Long budgetAmount = getTotalBudget(userId);
        log.info("Get budgetAmount from DB: " + budgetAmount);
    }

    public Long getUserId(String userEmail) {
        return userRepository.getUserId(userEmail);
    }

    public void insertBudget(Long userId, Long budgetAmount) {
        budgetRepository.insertBudget(userId, budgetAmount);
    }

    public void updateBudget(Long userId, Long budgetAmount) {
        budgetRepository.updateBudget(userId, budgetAmount);
    }

    public Long getTotalBudget(Long id) {
        return budgetRepository.getTotalBudget(id);
    }
}
