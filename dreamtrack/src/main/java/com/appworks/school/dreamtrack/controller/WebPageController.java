package com.appworks.school.dreamtrack.controller;

import com.appworks.school.dreamtrack.JwtUtil;
import com.appworks.school.dreamtrack.data.dto.AccountingDto;
import com.appworks.school.dreamtrack.data.dto.BalanceItemDto;
import com.appworks.school.dreamtrack.data.dto.BalanceSheetDto;
import com.appworks.school.dreamtrack.data.dto.NetIncomeDto;
import com.appworks.school.dreamtrack.model.ErrorResponse;
import com.appworks.school.dreamtrack.service.*;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class WebPageController {

    private final AccountingService accountingService;

    private final ExpensesService expensesService;

    private final BalanceSheetService balanceSheetService;

    private final UserService userService;

    private final BudgetService budgetService;

    private @Value("${jwt.signKey}") String jwtSignKey;

//    private final AssetsService assetsService;

    public WebPageController(AccountingService accountingService, ExpensesService expensesService,
                             BalanceSheetService balanceSheetService, UserService userService,
                             BudgetService budgetService) {
        this.accountingService = accountingService;
        this.expensesService = expensesService;
        this.balanceSheetService = balanceSheetService;
        this.userService = userService;
        this.budgetService = budgetService;
    }

//    @GetMapping("/")
//    public String home(@RequestParam(name = "user-id") String userId, Model model) {
//        List<AccountingDto> accountingDetail = accountingService.findAllAccounting(Long.valueOf(userId));
//        model.addAttribute("events", accountingDetail);
//        return "home";
//    }

    @GetMapping("/index")
    public String home() {
        return "home";
    }

    @PostMapping("/index")
    public ResponseEntity<?> home(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        if (authorization == null || authorization.isEmpty()) {
            Map<HttpStatus, String> response = new HashMap<>();
            response.put(HttpStatus.BAD_REQUEST, "Token is empty!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        String token = authorization.split(" ")[1].trim();
        JwtUtil jwtToken = new JwtUtil();
        Claims claims = jwtToken.parseJWT(token, jwtSignKey);
        String email = (String) claims.get("sub");

        try {
            Long userId = userService.getUserId(email);
            List<AccountingDto> accountingDetail = accountingService.findAllAccounting(Long.valueOf(userId));

            return ResponseEntity.ok().body(accountingDetail);
        } catch (UserService.UserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }

    }

    @GetMapping("/sign")
    public String sign() {
        return "sign";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }


//    @GetMapping("/dashboard")
//    public String dashboard(@RequestParam(required = false) String date,
//                            @RequestParam(required = false) String year,
//                            Model model) {
//        if (date != null) {
//            Long userId = Long.valueOf(1);
//            List<ExpensesCategoryDto> eachCategory = expensesService.findOnlyExpenseItems(userId, date);
//            List<NowAndPreMonthDto> eachCategoryNowAndPre = expensesService.findTotalExpensesNowAndPreMonth(userId, date);
//            Map<String, Object> budget = expensesService.getBudgetStyle(userId, date);
//
//            model.addAttribute("time_container", date);
//            model.addAttribute("highlightMonth", true);
//            model.addAttribute("highlightYear", false);
//
//            if (!eachCategory.isEmpty()) {
//                model.addAttribute("month_expense", eachCategory);
//                model.addAttribute("month_and_pre_month_expense", eachCategoryNowAndPre);
//            } else {
//                model.addAttribute("showNoInfo", true);
//            }
//
//            if (!budget.isEmpty()) {
//                model.addAttribute("budgetStyle", budget.get("budgetStyle"));
//                model.addAttribute("percentage", budget.get("percentage"));
//            } else {
//                model.addAttribute("hideBudgetDetail", true);
//            }
//
//        } else if (year != null) {
//            Long userId = Long.valueOf(1);
//
//            List<ExpensesCategoryDto> expensesInterval = expensesService.getTotalExpensesByEachCategoryForYear(userId, year);
//
//            model.addAttribute("time_container", year);
//            model.addAttribute("highlightYear", true);
//            model.addAttribute("highlightMonth", false);
//
//            if (!expensesInterval.isEmpty()) {
//                model.addAttribute("month_expense", expensesInterval);
//            } else {
//                model.addAttribute("showNoInfo", true);
//            }
//        }
//
//        return "dashboard";
//    }

    @GetMapping("/balance-sheet")
    public String balanceSheet(@RequestParam(required = false) String date,
                               @RequestParam(required = false) String year,
                               Model model) {
        if (date != null) {
            Long userId = Long.valueOf(1);
            List<BalanceItemDto> balanceSheet = balanceSheetService.findBalanceSheet(userId, date);

            model.addAttribute("time_container", date);
            model.addAttribute("highlightMonth", true);
            model.addAttribute("highlightYear", false);
            if (!balanceSheet.isEmpty()) {
                model.addAttribute("balance_sheet", balanceSheet);

                BalanceSheetDto balanceSheetTable = balanceSheetService.getTable(userId, date);
                model.addAttribute("balance_sheet_table", balanceSheetTable);

                List<NetIncomeDto> netIncome = balanceSheetService.getIncome(userId, date);
                model.addAttribute("net_income", netIncome);
            } else {
                model.addAttribute("showNoInfo", true);
            }
        } else if (year != null) {
            Long userId = Long.valueOf(1);

            List<BalanceItemDto> balanceSheetYear = balanceSheetService.findBalanceSheetYear(userId, year);
            log.info("balanceSheetYear:" + balanceSheetYear);
            model.addAttribute("time_container", year);
            model.addAttribute("highlightMonth", false);
            model.addAttribute("highlightYear", true);
            if (!balanceSheetYear.isEmpty()) {
                model.addAttribute("balance_sheet", balanceSheetYear);

                BalanceSheetDto balanceSheetTable = balanceSheetService.getTableYear(userId, year);
                model.addAttribute("balance_sheet_table", balanceSheetTable);

//                List<NetIncomeDto> netIncome = balanceSheetService.getIncome(userId, year);
//                model.addAttribute("net_income", netIncome);
            } else {
                model.addAttribute("showNoInfo", true);
            }
        }
        return "balanceSheet";
    }

}
