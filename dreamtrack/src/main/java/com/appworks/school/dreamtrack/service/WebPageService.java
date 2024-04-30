package com.appworks.school.dreamtrack.service;

import java.util.Map;

public interface WebPageService {
    Map<String, Object> findExpensesForMonth(Long userId, String date);

    Map<String, Object> findExpensesForHalfMonth(Long userId, String startDate, String endDate);

    Map<String, Object> findExpensesForYear(Long userId, String year);
}
