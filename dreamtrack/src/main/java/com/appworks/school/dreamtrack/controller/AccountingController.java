package com.appworks.school.dreamtrack.controller;

import com.appworks.school.dreamtrack.data.dto.AccountingDto;
import com.appworks.school.dreamtrack.data.form.AccountingForm;
import com.appworks.school.dreamtrack.model.ErrorResponse;
import com.appworks.school.dreamtrack.service.AccountingService;
import com.appworks.school.dreamtrack.service.UserService;
import jakarta.validation.groups.Default;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<?> postAccounting(@Validated({AccountingForm.Default.class, AccountingForm.PostOperation.class})
                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                            @RequestBody List<AccountingForm> accountingForms) {

        if (authorization == null || authorization.isEmpty()) {
            Map<HttpStatus, String> response = new HashMap<>();
            response.put(HttpStatus.BAD_REQUEST, "Token is empty!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        String token = authorization.split(" ")[1].trim();
        try {
            log.info("accountingForms:" + accountingForms);
            List<AccountingDto> accountingDtos = accountingService.saveAccounting(token, accountingForms);
            log.info("Successful send.");
            return ResponseEntity.ok().body(accountingDtos);
        } catch (AccountingService.UserNotExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    @PatchMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> patchAccounting(@Validated({AccountingForm.PatchOperation.class, Default.class})
                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                             @RequestBody AccountingForm accountingForm) {
        if (authorization == null || authorization.isEmpty()) {
            Map<HttpStatus, String> response = new HashMap<>();
            response.put(HttpStatus.BAD_REQUEST, "Token is empty!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        String token = authorization.split(" ")[1].trim();
        try {
            log.info("Patch accountingForm:" + accountingForm);
            accountingService.patchAccounting(token, accountingForm);
            log.info("Successful update.");
            return ResponseEntity.ok().build();
        } catch (AccountingService.UserNotExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    @DeleteMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deleteAccounting(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                              @RequestBody Map<String, Long> json) {
        if (authorization == null || authorization.isEmpty()) {
            Map<HttpStatus, String> response = new HashMap<>();
            response.put(HttpStatus.BAD_REQUEST, "Token is empty!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        String token = authorization.split(" ")[1].trim();

        try {
            log.info("Delete accounting id:" + json);
            accountingService.deleteAccounting(json, token);
            log.info("Successful delete.");
            return ResponseEntity.ok().build();
        } catch (AccountingService.UserNotExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }

    }

//    @GetMapping("/detail")
//    public ResponseEntity<?> GetAccounting(@RequestParam(name = "id") Long userId,
//                                           @RequestParam(name = "date") String date) {
//        Boolean isExist = userService.findUserId(userId);
//        if (isExist) {
//            List<AccountingDto> accountingDetail = accountingService.findAllAccounting(userId, date);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("data", accountingDetail);
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//        } else {
//            Map<String, Object> response = new HashMap<>();
//            response.put("error", "We don't have this user.");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//        }
//    }
}
