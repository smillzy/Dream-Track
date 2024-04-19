package com.appworks.school.dreamtrack.service;

import java.util.Map;

public interface ExpensesService {

    Map<String, Object> findExpenses(Long userId, String date);

    Map<String, Object> findExpensesInterval(Long userId, String startDate, String endDate);
}
