package com.appworks.school.dreamtrack.controller;

import com.appworks.school.dreamtrack.data.dto.BudgetDto;
import com.appworks.school.dreamtrack.data.form.BudgetForm;
import com.appworks.school.dreamtrack.service.BudgetService;
import com.appworks.school.dreamtrack.service.UserService;
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
    private final UserService userService;
    private final BudgetService budgetService;

    public BudgetController(UserService userService, BudgetService budgetService) {
        this.userService = userService;
        this.budgetService = budgetService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> postBudget(@Valid @RequestBody BudgetForm budgetForm) {
        Boolean isExist = userService.findUserId(budgetForm.getUserId());

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
        budgetService.updateBudget(budgetForm);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getBudget(@RequestParam(name = "id") long userId) {
        Boolean isExist = userService.findUserId(userId);

        if (isExist) {
            BudgetDto budget = budgetService.getTotalBudget(userId);

            Map<String, Object> response = new HashMap<>();
            response.put("data", budget);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "We don't have this user.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
