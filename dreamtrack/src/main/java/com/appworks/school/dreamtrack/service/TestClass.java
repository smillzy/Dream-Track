package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.repository.AccountingRepository;
import com.appworks.school.dreamtrack.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@SpringBootApplication
@Slf4j
@ComponentScan(basePackages = {"com.appworks.school.dreamtrack"})
public class TestClass implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AccountingRepository accountingRepository;

    public TestClass(UserRepository userRepository, AccountingRepository accountingRepository) {
        this.userRepository = userRepository;
        this.accountingRepository = accountingRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(TestClass.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String userName = "Kash";
        String userEmail = "Kash@gmail.com";
        String userPassword = "test123";

//        insertUserInfo(userName, userEmail, userPassword);
        Long userId = getUserId(userEmail);
        log.info("Receive userId: " + userId);

        // Account insert
//        String type = "支出";
//        String categoryName = "食物";
//        Long categoryId = findCategoryId(categoryName, type);
//        Long amount = Long.valueOf(300);
//        insertAccountingRecord(userId, categoryId, amount);
//        log.info("Insert Account: " + type + categoryName + amount);
//
//        // Account update 1
//        String type = "支出";
//        String categoryName = "食物";
//        Long id = Long.valueOf(3);
//        Long categoryId = findCategoryId(categoryName, type);
//        Long amountChange = Long.valueOf(500);
//        updateAccountingRecord(id, categoryId, amountChange);
//        log.info("Update Account amount: " + type + categoryName + amountChange);
//
//        // Account update 2
//        Long amountChange = Long.valueOf(500);
//        String type = "支出";
//        String categoryNameChange = "飲料";
//        Long categoryIdChange = findCategoryId(categoryNameChange, type);
//        String dateChange = "2024-04-15 20:15:01";
//        updateAccountingRecord(userId, categoryIdChange, amountChange, dateChange);
//        log.info("Update Account Name: " + type + categoryNameChange + amountChange);
//
        // Account delete
        Long id = Long.valueOf(3);
        deleteAccountingRecord(id);
        log.info("Delete Account Record id : " + id);
//
//        // Account query total expanse

//        String month = "2024-04";
//        List<Map<String, Object>> totalExpenses = getTotalExpenses(userId, month);
//        log.info("Total expanse: " + totalExpenses);

//        // Account query total expanse for lots of month
//        String type = "支出";
//        String startDate = "2024-03";
//        String endDate = "2024-04";
//        Long totalExpenses = getTotalExpenses(userId, startDate, endDate, type);
//        log.info("Total expanse: " + totalExpenses);

//        // Account query total expanse for each category
        String date = "2024-04";
        Map<String, Integer> eachCategory = getTotalExpensesByEachCategory(userId, date);
        log.info("Total expanse of eachCategory: " + eachCategory);

//        // Account query total expanse for each category with an interval
//        String startDate = "2024-03";
//        String endDate = "2024-04";
//        Map<String, Integer> eachCategoryInterval = getTotalExpensesForEachCategory(userId, startDate, endDate);
//        log.info("Total expanse of each category with an interval: " + eachCategoryInterval);

//         Account detail
//        String date = "2024-04";
//        List<Map<String, Object>> result = accountingRepository.findAllAccounting(userId, date);
//        log.info("result: " + result);
    }

    public void insertUserInfo(String userName, String userEmail, String userPassword) {
        userRepository.insertUserInfo(userName, userEmail, userPassword);
    }

    public Long getUserId(String userEmail) {
        return userRepository.getUserId(userEmail);
    }

    public Long findCategoryId(String categoryName, String type) {
        return accountingRepository.findCategoryId(categoryName, type);
    }

    public void insertAccountingRecord(Long id, Long categoryId, Long amount) {
        accountingRepository.insertAccountingRecord(id, categoryId, amount);
    }

    public void updateAccountingRecord(Long id, Long categoryId, Long amount) {
        accountingRepository.updateAccountingRecord(id, categoryId, amount);
    }

    public void deleteAccountingRecord(Long id) {
        accountingRepository.deleteAccountingRecord(id);
    }

    public List<Map<String, Object>> getTotalExpenses(Long id, String date) {
        return accountingRepository.getTotalExpenses(id, date);
    }

    public Long getTotalExpenses(Long id, String startDate, String endDate, String type) {
        return accountingRepository.getTotalExpenses(id, startDate, endDate, type);
    }

    public Map<String, Integer> getTotalExpensesByEachCategory(Long id, String date) {
        List<Map<String, Object>> getEachCategory = accountingRepository.getTotalExpensesByEachCategory(id, date);

        Map<String, Integer> expensesByCategory = new HashMap<>();
        for (Map<String, Object> row : getEachCategory) {
            String category = (String) row.get("name");
            Integer sumExpenses = ((BigDecimal) row.get("SUM(accounting.amount)")).intValue();
            expensesByCategory.put(category, sumExpenses);
        }
        return expensesByCategory;
    }

    public Map<String, Integer> getTotalExpensesForEachCategory(Long id, String startDate, String endDate) {
        List<Map<String, Object>> getEachCategoryWithInterval = accountingRepository.getTotalExpensesForEachCategory(id, startDate, endDate);

        Map<String, Integer> expensesByCategory = new HashMap<>();
        for (Map<String, Object> row : getEachCategoryWithInterval) {
            String category = (String) row.get("name");
            Integer sumExpenses = ((BigDecimal) row.get("SUM(accounting.amount)")).intValue();
            expensesByCategory.put(category, sumExpenses);
        }
        return expensesByCategory;
    }


}
