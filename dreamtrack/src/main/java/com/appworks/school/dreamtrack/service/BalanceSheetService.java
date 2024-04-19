package com.appworks.school.dreamtrack.service;

import java.util.Map;

public interface BalanceSheetService {
    Map<String, Object> findBalanceSheet(Long userId, String date);

    Map<String, Object> findBalanceSheetInterval(Long userId, String endDate);
}
