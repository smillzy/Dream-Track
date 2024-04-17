package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.repository.BalanceSheetRepository;
import com.appworks.school.dreamtrack.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@Slf4j
@ComponentScan(basePackages = {"com.appworks.school.dreamtrack"})
public class BalanceSheet implements CommandLineRunner {
    private final UserRepository userRepository;
    private final BalanceSheetRepository balanceSheetRepository;

    public BalanceSheet(UserRepository userRepository,BalanceSheetRepository balanceSheetRepository) {
        this.userRepository = userRepository;
        this.balanceSheetRepository = balanceSheetRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BalanceSheet.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String userName = "Kash";
        String userEmail = "Kash@gmail.com";
        String userPassword = "test123";

        // insertUserInfo(userName, userEmail, userPassword);
        Long userId = getUserId(userEmail);
        log.info("Receive userId: " + userId);

//        getBalanceSheet();

        String date = "2024-04";
        List<Map<String, Object>> BalanceSheetForOneMonth = balanceSheetRepository.getBalanceSheet(userId, date);
        log.info("Get User Balance Sheet For One Month: " + BalanceSheetForOneMonth);
    }

//    @Scheduled(cron = "0/10 * * 16 * *", zone = "Asia/Taipei")
    public void getBalanceSheet() {
        List<Long> userIds = userRepository.findAllUserId();

        for (Long userId : userIds) {
            List<Map<String, Object>> financialDataList = balanceSheetRepository.getFinancialDataForUser(userId);
            if (!financialDataList.isEmpty()) {
                Map<String, Object> financialData = financialDataList.get(0);
                balanceSheetRepository.insertBalanceSheet(
                        userId,
                        ((BigDecimal) financialData.get("assetCurrent")).longValue(),
                        ((BigDecimal)  financialData.get("assetCurrencies")).longValue(),
                        ((BigDecimal)  financialData.get("assetStock")).longValue(),
                        ((BigDecimal)  financialData.get("liability")).longValue(),
                        ((BigDecimal)  financialData.get("netIncome")).longValue()
                );
                log.info("Insert Balance Sheet userId: " + userId);
            }
        }

    }

    public Long getUserId(String userEmail){
        return userRepository.getUserId(userEmail);
    }

}
