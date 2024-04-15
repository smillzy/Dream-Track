package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.repository.BudgetRepository;
import com.appworks.school.dreamtrack.repository.LiabilitiesRepository;
import com.appworks.school.dreamtrack.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
@Slf4j
@ComponentScan(basePackages = {"com.appworks.school.dreamtrack"})
public class Liability implements CommandLineRunner {

    private final UserRepository userRepository;

    private final LiabilitiesRepository liabilitiesRepository;


    public Liability(UserRepository userRepository, LiabilitiesRepository liabilitiesRepository) {
        this.userRepository = userRepository;
        this.liabilitiesRepository = liabilitiesRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Liability.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String userName = "Kash";
        String userEmail = "Kash@gmail.com";
        String userPassword = "test123";

        // insertUserInfo(userName, userEmail, userPassword);
        Long userId = getUserId(userEmail);
        log.info("Receive userId: " + userId);

        String item = "學貸";
        String action = "借款";
        Long liabilitiesAmount = Long.valueOf(5000);
        insertLiabilities(userId, item, action, liabilitiesAmount);
        log.info("Add liabilities: " + item + action + liabilitiesAmount);

//        String item = "學貸";
//        String action = "還款";
//        Long liabilitiesAmount = Long.valueOf(-3000);
//        insertLiabilities(userId, item, action, liabilitiesAmount);
//        log.info("Add liabilities: " + item + action + liabilitiesAmount);
    }

    public Long getUserId(String userEmail){
        return userRepository.getUserId(userEmail);
    }

    public void insertLiabilities(Long id, String item, String action, Long liabilitiesAmount){
        liabilitiesRepository.insertLiabilities(id, item, action, liabilitiesAmount);
    }
}