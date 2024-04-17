package com.appworks.school.dreamtrack.repository;

import java.util.List;
import java.util.Map;

public interface AccountingRepository {
    Long findCategoryId(String categoryName, String type);

    void insertAccountingRecord(Long userId, Long categoryId, Long amount);

    void deleteAccountingRecord(Long id);

    List<Map<String, Object>> findAllAccounting(Long userId, String date);

    Long getTotalExpenses(Long userId, String date, String type);

    Long getTotalExpenses(Long userId, String startDate, String endDate, String type);

    List<Map<String, Object>> getTotalExpensesByEachCategory(Long userId, String date);

    List<Map<String, Object>> getTotalExpensesForEachCategory(Long userId, String startDate, String endDate);

    void updateAccountingRecord(Long id, Long categoryId, Long amount);
}
