package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.JwtUtil;
import com.appworks.school.dreamtrack.data.dto.AccountingDto;
import com.appworks.school.dreamtrack.data.form.AccountingForm;
import com.appworks.school.dreamtrack.model.Accounting;
import com.appworks.school.dreamtrack.repository.AccountingRepository;
import com.appworks.school.dreamtrack.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AccountingServiceImpl implements AccountingService {

    private final AccountingRepository accountingRepository;
    private final UserRepository userRepository;

    private @Value("${jwt.signKey}") String jwtSignKey;

    public AccountingServiceImpl(AccountingRepository accountingRepository, UserRepository userRepository) {
        this.accountingRepository = accountingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Long findCategoryId(String categoryName, String type) {
        return accountingRepository.findCategoryId(categoryName, type);
    }

    @Override
    public List<AccountingDto> saveAccounting(String token, List<AccountingForm> accountingForms) throws UserNotExistException {
        JwtUtil jwtToken = new JwtUtil();
        Claims claims = jwtToken.parseJWT(token, jwtSignKey);
        String email = (String) claims.get("sub");

        Long userId = userRepository.getUserId(email);

        if (userId == null) {
            throw new UserNotExistException("No user found.");
        }

        List<Accounting> accountings = Accounting.from(accountingForms, userId);
        List<AccountingDto> accountingDtos = new ArrayList<>();

        for (Accounting accounting : accountings) {
            Long categoryId = findCategoryId(accounting.getCategoryName(), accounting.getType());
            Long id = accountingRepository.insertAccountingRecord(accounting.getUserId(), accounting.getDate(), categoryId, accounting.getAmount(), accounting.getDescription());
            AccountingDto dto = AccountingDto.fromAccounting(id, accounting);
            accountingDtos.add(dto);
        }
        log.info("finish");
        return accountingDtos;
    }

    @Override
    public void patchAccounting(String token, AccountingForm accountingForm) throws UserNotExistException {
        JwtUtil jwtToken = new JwtUtil();
        Claims claims = jwtToken.parseJWT(token, jwtSignKey);
        String email = (String) claims.get("sub");

        Long userId = userRepository.getUserId(email);

        if (userId == null) {
            throw new UserNotExistException("No user found.");
        }
        Accounting a = Accounting.from(accountingForm);
        log.info("a: " + a);
        Long CategoryId = findCategoryId(a.getCategoryName(), a.getType());
        accountingRepository.updateAccountingRecord(a.getId(), CategoryId, a.getAmount(), a.getDescription());
    }

    @Override
    public Boolean findAccountingId(Long id) {
        return accountingRepository.findAccountingId(id);
    }

    @Override
    public void deleteAccounting(Map<String, Long> json, String token) throws UserNotExistException {
        JwtUtil jwtToken = new JwtUtil();
        Claims claims = jwtToken.parseJWT(token, jwtSignKey);
        String email = (String) claims.get("sub");

        Long userId = userRepository.getUserId(email);

        if (userId == null) {
            throw new UserNotExistException("No user found.");
        }

        Long id = json.get("id");
        accountingRepository.deleteAccountingRecord(id);
    }

    @Override
    public List<AccountingDto> findAllAccounting(Long userId) {
        return accountingRepository.findAllAccounting(userId);
    }
}
