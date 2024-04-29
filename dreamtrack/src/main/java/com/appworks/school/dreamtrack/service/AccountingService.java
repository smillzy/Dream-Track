package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.dto.AccountingDto;
import com.appworks.school.dreamtrack.data.form.AccountingForm;

import java.util.List;
import java.util.Map;

public interface AccountingService {
    Long findCategoryId(String categoryName, String type);

    List<AccountingDto> saveAccounting(String token, List<AccountingForm> accountingForms) throws UserNotExistException;

    void patchAccounting(String token, AccountingForm accountingForm) throws UserNotExistException;

    Boolean findAccountingId(Long id);

    void deleteAccounting(Map<String, Long> json, String token) throws UserNotExistException;

    List<AccountingDto> findAllAccounting(Long userId);

    sealed class AccountingException extends
            Exception permits UserNotExistException {
        public AccountingException(String message) {
            super(message);
        }
    }

    final class UserNotExistException extends AccountingException {
        public UserNotExistException(String message) {
            super(message);
        }
    }
}
