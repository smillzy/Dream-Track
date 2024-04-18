package com.appworks.school.dreamtrack.controller;

import com.appworks.school.dreamtrack.data.dto.AccountingDto;
import com.appworks.school.dreamtrack.data.form.AccountingForm;
import com.appworks.school.dreamtrack.service.AccountingService;
import com.appworks.school.dreamtrack.service.UserService;
import jakarta.validation.groups.Default;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/accounting")
@Slf4j
public class AccountingController {

    private final UserService userService;
    private final AccountingService accountingService;

    public AccountingController(UserService userService, AccountingService accountingService) {
        this.userService = userService;
        this.accountingService = accountingService;
    }


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> postAccounting(@Validated({AccountingForm.PostOperation.class, Default.class})
                                            @RequestBody AccountingForm accountingForm) {
        Boolean isExist = userService.findUserId(accountingForm.getUserId());
        if (isExist) {
            log.info(accountingForm.toString());
            accountingService.saveAccounting(accountingForm);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "We don't have this user.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PatchMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> patchAccounting(@Validated({AccountingForm.PatchOperation.class, Default.class})
                                             @RequestBody AccountingForm accountingForm) {
        log.info(accountingForm.toString());
        accountingService.patchAccounting(accountingForm);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deleteAccounting(@RequestBody Map<String, Long> json) {
        Long id = json.get("id");
        Boolean isExist = accountingService.findAccountingId(id);
        if (isExist) {
            accountingService.deleteAccounting(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "We don't have this id.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/detail")
    public ResponseEntity<?> GetAccounting(@RequestParam(name = "id") Long userId,
                                           @RequestParam(name = "date") String date) {
        Boolean isExist = userService.findUserId(userId);
        if (isExist) {
            List<AccountingDto> accountingDetail = accountingService.findAllAccounting(userId, date);

            Map<String, Object> response = new HashMap<>();
            response.put("data", accountingDetail);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "We don't have this user.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
