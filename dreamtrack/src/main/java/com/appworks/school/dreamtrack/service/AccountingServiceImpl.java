package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.form.AccountingForm;
import com.appworks.school.dreamtrack.model.Accounting;
import com.appworks.school.dreamtrack.repository.AccountingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class AccountingServiceImpl implements AccountingService {

    private final AccountingRepository accountingRepository;

    public AccountingServiceImpl(AccountingRepository accountingRepository) {
        this.accountingRepository = accountingRepository;
    }

    @Override
    public Long findCategoryId(String categoryName, String type) {
        return accountingRepository.findCategoryId(categoryName, type);
    }

    @Override
    public void saveAccounting(AccountingForm accountingForm) {
        Accounting a = Accounting.from(accountingForm);
        a.setUserId(accountingForm.getUserId());
        Long CategoryId = findCategoryId(a.getCategoryName(), a.getType());
        accountingRepository.insertAccountingRecord(a.getUserId(), CategoryId, a.getAmount());
    }

    @Override
    public void patchAccounting(AccountingForm accountingForm) {
        Accounting a = Accounting.from(accountingForm);
        a.setId(accountingForm.getId());
        Long CategoryId = findCategoryId(a.getCategoryName(), a.getType());
        accountingRepository.updateAccountingRecord(a.getId(), CategoryId, a.getAmount());
    }

    @Override
    public Boolean findAccountingId(Long id) {
        return accountingRepository.findAccountingId(id);
    }

    @Override
    public void deleteAccounting(long id) {
        accountingRepository.deleteAccountingRecord(id);
    }

    @Override
    public List<Map<String, Object>> findAllAccounting(Long userId, String date) {
        List<Map<String, Object>> results = accountingRepository.findAllAccounting(userId, date);
        for (Map<String, Object> record : results) {
            if (record.get("date") != null) {
                LocalDateTime dateTime = (LocalDateTime) record.get("date");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDate = dateTime.format(formatter);
                record.put("date", formattedDate);
            }
        }
        return results;
    }
}