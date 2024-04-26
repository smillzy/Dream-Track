package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.dto.AccountingDto;
import com.appworks.school.dreamtrack.data.form.AccountingForm;
import com.appworks.school.dreamtrack.model.Accounting;
import com.appworks.school.dreamtrack.repository.AccountingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
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
        log.info("accountingForm: " + accountingForm);
        Accounting a = Accounting.from(accountingForm);
        a.setUserId(accountingForm.getUserId());
        Long CategoryId = findCategoryId(a.getCategoryName(), a.getType());
        log.info("a: " + a);
        accountingRepository.insertAccountingRecord(a.getUserId(), CategoryId, a.getAmount());
        log.info("finish");
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
    public List<AccountingDto> findAllAccounting(Long userId, String date) {
        return accountingRepository.findAllAccounting(userId, date);
    }
}
