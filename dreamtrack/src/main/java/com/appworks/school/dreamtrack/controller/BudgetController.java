package com.appworks.school.dreamtrack.controller;

import com.appworks.school.dreamtrack.data.form.BudgetForm;
import com.appworks.school.dreamtrack.service.BudgetService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/budget")
@Slf4j
public class BudgetController {
    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> postBudget(@Valid @RequestBody BudgetForm budgetForm) {
        Boolean isExist = budgetService.findUserId(budgetForm.getUserId());

        if (isExist) {
            budgetService.insertBudget(budgetForm);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "We don't have this user.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PatchMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> patchBudget(@Valid @RequestBody BudgetForm budgetForm) {
        Boolean isExist = budgetService.findUserId(budgetForm.getUserId());

        if (isExist) {
            budgetService.updateBudget(budgetForm);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "We don't have this user.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> patchBudget(@RequestParam(name = "id") long userId) {
        Boolean isExist = budgetService.findUserId(userId);

        if (isExist) {
            Map<String, Long> result = budgetService.getTotalBudget(userId);

            Map<String, Object> response = new HashMap<>();
            response.put("data", result);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "We don't have this user.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}