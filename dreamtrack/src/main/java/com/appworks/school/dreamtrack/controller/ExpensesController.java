package com.appworks.school.dreamtrack.controller;

import com.appworks.school.dreamtrack.service.ExpensesService;
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
@RequestMapping("api/v1/expense")
@Slf4j
public class ExpensesController {

    private final UserService userService;

    private final ExpensesService expensesService;

    public ExpensesController(UserService userService, ExpensesService expensesService) {
        this.userService = userService;
        this.expensesService = expensesService;
    }

    @GetMapping("/single")
    public ResponseEntity<?> GetExpenseSingle(@RequestParam(name = "user-id") Long userId,
                                              @RequestParam(name = "date") String date) {
        Boolean isExist = userService.findUserId(userId);
        if (isExist) {

            Map<String, Object> expenses = expensesService.findExpenses(userId, date);

            Map<String, Object> response = new HashMap<>();
            response.put("data", expenses);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "We don't have this user.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/interval")
    public ResponseEntity<?> GetExpenseInterval(@RequestParam(name = "user-id") Long userId,
                                                @RequestParam(name = "start-date") String startDate,
                                                @RequestParam(name = "end-date") String endDate) {
        Boolean isExist = userService.findUserId(userId);
        if (isExist) {

            Map<String, Object> expensesInterval = expensesService.findExpensesInterval(userId, startDate, endDate);

            Map<String, Object> response = new HashMap<>();
            response.put("data", expensesInterval);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "We don't have this user.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
