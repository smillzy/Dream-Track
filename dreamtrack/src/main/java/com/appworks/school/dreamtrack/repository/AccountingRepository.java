package com.appworks.school.dreamtrack.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AccountingRepository {
    Long findCategoryId(String categoryName, String type);
    void insertAccountingRecord(Long id, Long categoryId, Long amount);

    void deleteAccountingRecord(Long id, Long categoryId, String date);

    List<Map<String, Object>> findAllAccounting(Long id, String date);

    Long getTotalExpenses(Long id, String date, String type);

    Long getTotalExpenses(Long id, String startDate, String endDate, String type);

    List<Map<String, Object>> getTotalExpensesByEachCategory(Long id, String date);

    List<Map<String, Object>> getTotalExpensesForEachCategory(Long id, String startDate, String endDate);

    void updateAccountingRecord(Long id, Long categoryId, Long amount, String date);
}
