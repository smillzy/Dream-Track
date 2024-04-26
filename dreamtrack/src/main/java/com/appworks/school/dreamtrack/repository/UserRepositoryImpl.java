package com.appworks.school.dreamtrack.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void insertUserInfo(String name, String email, String password) {
        String checkEmailQuery = "SELECT COUNT(*) FROM user WHERE email = ?";
        int count = jdbcTemplate.queryForObject(checkEmailQuery, new Object[]{email}, Integer.class);

        if (count == 0) {
            String insertSql = "INSERT INTO user (name, email, password) VALUES (?, ?, ?)";
            jdbcTemplate.update(insertSql, name, email, password);
        } else {
            throw new RuntimeException("Email already exists");
        }
    }

    @Override
    public Long getUserId(String email) {
        String findUserId = "SELECT id FROM user WHERE email = ?";
        return jdbcTemplate.queryForObject(findUserId, new Object[]{email}, Long.class);
    }

    @Override
    public List<Long> findAllUserId() {
        String countUser = "SELECT COUNT(id) FROM user";
        return jdbcTemplate.queryForList(countUser, Long.class);
    }

    @Override
    public Boolean findUserId(Long userId) {
        String findAccountingId = "SELECT COUNT(*) FROM user WHERE id = ?";
        Long count = jdbcTemplate.queryForObject(findAccountingId, Long.class, userId);
        return count != null && count > 0;
    }
}
