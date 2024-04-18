package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.dto.AccountingDto;
import com.appworks.school.dreamtrack.data.form.AccountingForm;

import java.util.List;

public interface AccountingService {
    Long findCategoryId(String categoryName, String type);

    void saveAccounting(AccountingForm accountingForm);

    void patchAccounting(AccountingForm accountingForm);

    Boolean findAccountingId(Long id);

    void deleteAccounting(long id);

    List<AccountingDto> findAllAccounting(Long userId, String date);
}
