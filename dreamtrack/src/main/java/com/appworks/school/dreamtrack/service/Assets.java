package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.repository.AssetsRepository;
import com.appworks.school.dreamtrack.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Map;

//@SpringBootApplication
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
//        Long currentDepositAmountChange = Long.valueOf(7000);
//        Long id = Long.valueOf(1);;
//        updateAssetsCurrentDeposit(id, currentDepositAmountChange);
//        log.info("Update Current Deposit: " + currentDepositAmountChange);

        String currencyName = "美金";
        int currencyId = getCurrencyId(currencyName);

//        // Insert Foreign Currencies
//        String action = "買進";
//        Long quantityForeign = Long.valueOf(100);
//        Long quantityTWD = Long.valueOf(3000);
//        Float rate = 30.0F;
//        insertAssetsForeignCurrencies(userId, currencyId, action, quantityForeign, quantityTWD, rate);
//        log.info("Insert Foreign Currencies: " + action + ", 美金: " + quantityForeign);

//        // Update Foreign Currencies
//        String action = "買進";
//        Long id = Long.valueOf(1);
//        Long quantityForeignChange = Long.valueOf(300);
//        Long quantityTWDChange = Long.valueOf(9000);
//        Float rateChange = 30.0F;
//        updateAssetsForeignCurrencies(id, currencyId, action, quantityForeignChange, quantityTWDChange, rateChange);
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

//        // Update Assets Stock
//        String action = "買進";
//        Long id = Long.valueOf(1);
//        Long priceChange = Long.valueOf(160);
//        int quantity = 2000;
//        Long stockAmountChange = Long.valueOf(320000);
//        updateAssetsStock(id, stockId, action, priceChange, quantity, stockAmountChange);
//        log.info("Update Assets Stock: " + action + ", 金額: " + stockAmountChange);

        List<Map<String, Object>> assetsCurrentDeposit = assetsRepository.findAllAssetsCurrentDeposit(userId);
        log.info("result assetsCurrentDeposit: " + assetsCurrentDeposit);
        List<Map<String, Object>> assetsForeignCurrencies = assetsRepository.findAllAssetsForeignCurrencies(userId);
        log.info("result assetsForeignCurrencies: " + assetsForeignCurrencies);
        List<Map<String, Object>> assetsStock = assetsRepository.findAllAssetsStock(userId);
        log.info("result assetsStock: " + assetsStock);
    }

    public Long getUserId(String userEmail) {
        return userRepository.getUserId(userEmail);
    }

    public void insertAssetsCurrentDeposit(Long userId, Long currentDepositAmount) {
        assetsRepository.insertAssetsCurrentDeposit(userId, currentDepositAmount);
    }

    public void updateAssetsCurrentDeposit(Long id, Long currentDepositAmount) {
        assetsRepository.updateAssetsCurrentDeposit(id, currentDepositAmount);
    }

    public int getCurrencyId(String currencyName) {
        return assetsRepository.getCurrencyId(currencyName);
    }

    public void insertAssetsForeignCurrencies(Long userId, int currencyId, String action, Long quantityForeign, Long quantityTWD, Float rate) {
        assetsRepository.insertAssetsForeignCurrencies(userId, currencyId, action, quantityForeign, quantityTWD, rate);
    }

    public void updateAssetsForeignCurrencies(Long id, int currencyId, String action, Long quantityForeign, Long quantityTWD, Float rate) {
        assetsRepository.updateAssetsForeignCurrencies(id, currencyId, action, quantityForeign, quantityTWD, rate);
    }

    public int getStockId(String symbol) {
        return assetsRepository.getStockId(symbol);
    }

    public void insertAssetsStock(Long userId, int stockId, String action, Long price, int quantity, Long stockAmount) {
        assetsRepository.insertAssetsStock(userId, stockId, action, price, quantity, stockAmount);
    }

    public void updateAssetsStock(Long id, int stockId, String action, Long price, int quantity, Long stockAmount) {
        assetsRepository.updateAssetsStock(id, stockId, action, price, quantity, stockAmount);
    }

}
