package com.appworks.school.dreamtrack.controller;

import com.appworks.school.dreamtrack.data.dto.AccountingDto;
import com.appworks.school.dreamtrack.service.AccountingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
public class WebPageController {

    private final AccountingService accountingService;

    public WebPageController(AccountingService accountingService) {
        this.accountingService = accountingService;
    }

    @GetMapping("/")
    public String home(Model model) {
        Long userId = Long.valueOf(1);
        String date = "2024-04";
        List<AccountingDto> accountingDetail = accountingService.findAllAccounting(userId, date);
        model.addAttribute("events", accountingDetail);
        return "home";
    }
}
