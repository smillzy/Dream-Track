package com.appworks.school.dreamtrack.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long insertUserInfo(String name, String email, String password) {
        String insertSql = "INSERT INTO user (name, email, password) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Long getUserId(String email) {
        String findUserId = "SELECT id FROM user WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(findUserId, new Object[]{email}, Long.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
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

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);
        return count != null && count > 0;
    }

    @Override
    public String getUserPassword(String email) {
        String findPassword = "SELECT password FROM user WHERE email = ?";
        return jdbcTemplate.queryForObject(findPassword, new Object[]{email}, String.class);
    }
}
