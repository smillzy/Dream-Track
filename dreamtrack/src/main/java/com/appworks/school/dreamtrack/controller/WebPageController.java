package com.appworks.school.dreamtrack.controller;

import com.appworks.school.dreamtrack.data.dto.*;
import com.appworks.school.dreamtrack.service.AccountingService;
import com.appworks.school.dreamtrack.service.BalanceSheetService;
import com.appworks.school.dreamtrack.service.ExpensesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class WebPageController {

    private final AccountingService accountingService;

    private final ExpensesService expensesService;

    private final BalanceSheetService balanceSheetService;

//    private final AssetsService assetsService;

    public WebPageController(AccountingService accountingService, ExpensesService expensesService, BalanceSheetService balanceSheetService) {
        this.accountingService = accountingService;
        this.expensesService = expensesService;
        this.balanceSheetService = balanceSheetService;
    }

    @GetMapping("/")
    public String home(Model model) {
        Long userId = Long.valueOf(1);
        String date = "2024-04";
        List<AccountingDto> accountingDetail = accountingService.findAllAccounting(userId, date);
        model.addAttribute("events", accountingDetail);
        return "home";
    }

//    @GetMapping("/asset")
//    public String asset(Model model) {
//        Long userId = Long.valueOf(1);
////        String date = "2024-04";
//        Map<String, Object> assetsDetail = assetsService.findAssets(userId);
////        model.addAttribute("events", accountingDetail);
//        return "asset";
//    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(required = false) String date,
                            @RequestParam(required = false) String year,
                            Model model) {
        if (date != null) {
            Long userId = Long.valueOf(1);
            List<ExpensesCategoryDto> eachCategory = expensesService.findOnlyExpenseItems(userId, date);
            List<NowAndPreMonthDto> eachCategoryNowAndPre = expensesService.findTotalExpensesNowAndPreMonth(userId, date);
            Map<String, Object> budget = expensesService.getBudgetStyle(userId, date);

            model.addAttribute("time_container", date);
            model.addAttribute("highlightMonth", true);
            model.addAttribute("highlightYear", false);

            if (!eachCategory.isEmpty()) {
                model.addAttribute("month_expense", eachCategory);
                model.addAttribute("month_and_pre_month_expense", eachCategoryNowAndPre);
            } else {
                model.addAttribute("showNoInfo", true);
            }

            if (!budget.isEmpty()) {
                model.addAttribute("budgetStyle", budget.get("budgetStyle"));
                model.addAttribute("percentage", budget.get("percentage"));
            } else {
                model.addAttribute("hideBudgetDetail", true);
            }

        } else if (year != null) {
            Long userId = Long.valueOf(1);

            List<ExpensesCategoryDto> expensesInterval = expensesService.getTotalExpensesByEachCategoryForYear(userId, year);

            model.addAttribute("time_container", year);
            model.addAttribute("highlightYear", true);
            model.addAttribute("highlightMonth", false);

            if (!expensesInterval.isEmpty()) {
                model.addAttribute("month_expense", expensesInterval);
            } else {
                model.addAttribute("showNoInfo", true);
            }
        }

        return "dashboard";
    }

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
