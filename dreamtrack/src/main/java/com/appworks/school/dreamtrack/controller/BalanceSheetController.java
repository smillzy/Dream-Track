package com.appworks.school.dreamtrack.controller;

import com.appworks.school.dreamtrack.service.BalanceSheetService;
import com.appworks.school.dreamtrack.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/balance-sheet")
@Slf4j
public class BalanceSheetController {

    private final UserService userService;

    private final BalanceSheetService balanceSheetService;

    public BalanceSheetController(UserService userService, BalanceSheetService balanceSheetService) {
        this.userService = userService;
        this.balanceSheetService = balanceSheetService;
    }

    @GetMapping("/single")
    public ResponseEntity<?> GetBalanceSheetSingle(@RequestParam(name = "user-id") Long userId,
                                                   @RequestParam(name = "date") String date) {
        Boolean isExist = userService.findUserId(userId);
        if (isExist) {

            Map<String, Object> balanceSheet = balanceSheetService.findBalanceSheet(userId, date);

            Map<String, Object> response = new HashMap<>();
            response.put("data", balanceSheet);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "We don't have this user.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/interval")
    public ResponseEntity<?> GetBalanceSheetInterval(@RequestParam(name = "user-id") Long userId,
                                                     @RequestParam(name = "end-date") String endDate) {
        Boolean isExist = userService.findUserId(userId);
        if (isExist) {

            Map<String, Object> balanceSheetInterval = balanceSheetService.findBalanceSheetInterval(userId, endDate);

            Map<String, Object> response = new HashMap<>();
            response.put("data", balanceSheetInterval);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "We don't have this user.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
