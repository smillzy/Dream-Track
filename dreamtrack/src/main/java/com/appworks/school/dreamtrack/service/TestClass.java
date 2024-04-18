package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.repository.AccountingRepository;
import com.appworks.school.dreamtrack.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
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
//        String date = "2024-04";
//        Map<String, Integer> eachCategory = getTotalExpensesByEachCategory(userId, date);
//        log.info("Total expanse of eachCategory: " + eachCategory);

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


    public List<Map<String, Object>> getTotalExpenses(Long id, String date) {
        return accountingRepository.getTotalExpenses(id, date);
    }

    public List<Map<String, Object>> getTotalExpenses(Long id, String startDate, String endDate) {
        return accountingRepository.getTotalExpenses(id, startDate, endDate);
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
