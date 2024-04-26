package com.appworks.school.dreamtrack.controller;

import com.appworks.school.dreamtrack.data.form.CurrentDepositForm;
import com.appworks.school.dreamtrack.data.form.ForeignCurrenciesForm;
import com.appworks.school.dreamtrack.data.form.StockForm;
import com.appworks.school.dreamtrack.service.AssetsService;
import com.appworks.school.dreamtrack.service.UserService;
import jakarta.validation.groups.Default;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/assets")
@Slf4j
public class AssetsController {

    private final UserService userService;
    private final AssetsService assetsService;

    public AssetsController(UserService userService, AssetsService assetsService) {
        this.userService = userService;
        this.assetsService = assetsService;
    }

    @PostMapping(path = "/current-deposit", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> postCurrentDeposit(@Validated({CurrentDepositForm.PostOperation.class, Default.class})
                                                @RequestBody CurrentDepositForm currentDepositForm) {
        Boolean isExist = userService.findUserId(currentDepositForm.getUserId());
        if (isExist) {
            log.info(currentDepositForm.toString());
            assetsService.insertAssetsCurrentDeposit(currentDepositForm);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "We don't have this user.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PatchMapping(path = "/current-deposit", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> patchCurrentDeposit(@Validated({CurrentDepositForm.PatchOperation.class, Default.class})
                                                 @RequestBody CurrentDepositForm currentDepositForm) {
        log.info(currentDepositForm.toString());
        assetsService.updateAssetsCurrentDeposit(currentDepositForm);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(path = "/foreign-currencies", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> postForeignCurrencies(@Validated({ForeignCurrenciesForm.PostOperation.class, Default.class})
                                                   @RequestBody ForeignCurrenciesForm foreignCurrenciesForm) {
        Boolean isExist = userService.findUserId(foreignCurrenciesForm.getUserId());
        if (isExist) {
            log.info(foreignCurrenciesForm.toString());
            assetsService.insertAssetsForeignCurrencies(foreignCurrenciesForm);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "We don't have this user.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PatchMapping(path = "/foreign-currencies", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> patchForeignCurrencies(@Validated({ForeignCurrenciesForm.PatchOperation.class, Default.class})
                                                    @RequestBody ForeignCurrenciesForm foreignCurrenciesForm) {
        log.info(foreignCurrenciesForm.toString());
        assetsService.updateAssetsForeignCurrencies(foreignCurrenciesForm);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(path = "/stock", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> postStock(@Validated({StockForm.PostOperation.class, Default.class})
                                       @RequestBody StockForm stockForm) {
        Boolean isExist = userService.findUserId(stockForm.getUserId());
        if (isExist) {
            log.info(stockForm.toString());
            assetsService.insertAssetsStock(stockForm);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "We don't have this user.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PatchMapping(path = "/stock", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> patchStock(@Validated({StockForm.PatchOperation.class, Default.class})
                                        @RequestBody StockForm stockForm) {
        log.info(stockForm.toString());
        assetsService.updateAssetsStock(stockForm);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/detail")
    public ResponseEntity<?> GetAssets(@RequestParam(name = "id") Long userId) {
        Boolean isExist = userService.findUserId(userId);
        if (isExist) {
            Map<String, Object> assetsDetail = assetsService.findAssets(userId);

            Map<String, Object> response = new HashMap<>();
            response.put("data", assetsDetail);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "We don't have this user.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
