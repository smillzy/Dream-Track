package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.repository.AssetsRepository;
import com.appworks.school.dreamtrack.repository.LiabilitiesRepository;
import com.appworks.school.dreamtrack.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@Slf4j
@ComponentScan(basePackages = {"com.appworks.school.dreamtrack"})
public class Assets implements CommandLineRunner {

    private final UserRepository userRepository;

    private final AssetsRepository assetsRepository;


    public Assets(UserRepository userRepository, AssetsRepository assetsRepository) {
        this.userRepository = userRepository;
        this.assetsRepository = assetsRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Assets.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String userName = "Kash";
        String userEmail = "Kash@gmail.com";
        String userPassword = "test123";

        // insertUserInfo(userName, userEmail, userPassword);
        Long userId = getUserId(userEmail);
        log.info("Receive userId: " + userId);

//        // Insert Current Deposit
//        Long currentDepositAmount = Long.valueOf(5000);
//        insertAssetsCurrentDeposit(userId, currentDepositAmount);
//        log.info("Insert Current Deposit: " + currentDepositAmount);

//        // Update Current Deposit
//        Long currentDepositAmountChange = Long.valueOf(6000);
//        String date = "2024-04-16 00:24:24";
//        updateAssetsCurrentDeposit(userId, date, currentDepositAmountChange);
//        log.info("Update Current Deposit: " + currentDepositAmountChange);

//        String currencyName = "美金";
//        int currencyId = getCurrencyId(currencyName);

//        // Insert Foreign Currencies
//        String action = "買進";
//        Long quantityForeign = Long.valueOf(100);
//        Long quantityTWD = Long.valueOf(3000);
//        Float rate = 30.0F;
//        insertAssetsForeignCurrencies(userId, currencyId, action, quantityForeign, quantityTWD, rate);
//        log.info("Insert Foreign Currencies: " + action + ", 美金: " + quantityForeign);

//        // Update Foreign Currencies
//        String action = "買進";
//        String date = "2024-04-16 00:35:34";
//        Long quantityForeignChange = Long.valueOf(200);
//        Long quantityTWDChange = Long.valueOf(6000);
//        Float rateChange = 30.0F;
//        updateAssetsForeignCurrencies(userId, date, currencyId, action, quantityForeignChange, quantityTWDChange, rateChange);
//        log.info("Update Foreign Currencies: " + action + ", 美金: " + quantityForeignChange);

        String symbol = "0050";
        int stockId = getStockId(symbol);

//        // Insert Assets Stock
//        String action = "買進";
//        Long price = Long.valueOf(150);
//        int quantity = 1000;
//        Long stockAmount = Long.valueOf(150000);
//        insertAssetsStock(userId, stockId, action, price, quantity, stockAmount);
//        log.info("Insert Assets Stock: " + action + ", 金額: " + stockAmount);

        // Update Assets Stock
        String action = "買進";
        String date = "2024-04-16 00:45:03";
        Long priceChange = Long.valueOf(160);
        int quantity = 1000;
        Long stockAmountChange = Long.valueOf(160000);
        updateAssetsStock(userId, date, stockId, action, priceChange, quantity, stockAmountChange);
        log.info("Update Assets Stock: " + action + ", 金額: " + stockAmountChange);
    }

    public Long getUserId(String userEmail){
        return userRepository.getUserId(userEmail);
    }

    public void insertAssetsCurrentDeposit(Long id, Long currentDepositAmount){
        assetsRepository.insertAssetsCurrentDeposit(id, currentDepositAmount);
    }

    public void updateAssetsCurrentDeposit(Long id, String date, Long currentDepositAmount){
        assetsRepository.updateAssetsCurrentDeposit(id, date, currentDepositAmount);
    }

    public int getCurrencyId(String currencyName){
        return assetsRepository.getCurrencyId(currencyName);
    }

    public void insertAssetsForeignCurrencies(Long id, int currencyId, String action, Long quantityForeign, Long quantityTWD, Float rate){
        assetsRepository.insertAssetsForeignCurrencies(id, currencyId, action, quantityForeign, quantityTWD, rate);
    }

    public void updateAssetsForeignCurrencies(Long id, String date, int currencyId, String action, Long quantityForeign, Long quantityTWD, Float rate){
        assetsRepository.updateAssetsForeignCurrencies(id, date, currencyId, action, quantityForeign, quantityTWD, rate);
    }

    public int getStockId(String symbol){
        return assetsRepository.getStockId(symbol);
    }

    public void insertAssetsStock(Long id, int stockId, String action, Long price, int quantity, Long stockAmount){
        assetsRepository.insertAssetsStock(id, stockId, action, price, quantity, stockAmount);
    }

    public void updateAssetsStock(Long id, String date, int stockId, String action, Long price, int quantity, Long stockAmount){
        assetsRepository.updateAssetsStock(id, date, stockId, action, price, quantity, stockAmount);
    }

}
