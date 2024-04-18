package com.appworks.school.dreamtrack.repository;

import com.appworks.school.dreamtrack.data.dto.AccountingDto;

import java.util.List;
import java.util.Map;

public interface AccountingRepository {
    Long findCategoryId(String categoryName, String type);

    void insertAccountingRecord(Long userId, Long categoryId, Long amount);

    void deleteAccountingRecord(Long id);

    Boolean findAccountingId(Long id);

    List<AccountingDto> findAllAccounting(Long userId, String date);

    List<Map<String, Object>> getTotalExpenses(Long userId, String date);

    List<Map<String, Object>> getTotalExpenses(Long userId, String startDate, String endDate);

    List<Map<String, Object>> getTotalExpensesByEachCategory(Long userId, String date);

    List<Map<String, Object>> getTotalExpensesForEachCategory(Long userId, String startDate, String endDate);

    void updateAccountingRecord(Long id, Long categoryId, Long amount);
}
