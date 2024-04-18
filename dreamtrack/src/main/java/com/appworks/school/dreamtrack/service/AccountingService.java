package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.form.AccountingForm;

import java.util.List;
import java.util.Map;

public interface AccountingService {
    Long findCategoryId(String categoryName, String type);

    void saveAccounting(AccountingForm accountingForm);

    void patchAccounting(AccountingForm accountingForm);

    Boolean findAccountingId(Long id);

    void deleteAccounting(long id);

    List<Map<String, Object>> findAllAccounting(Long userId, String date);
}
