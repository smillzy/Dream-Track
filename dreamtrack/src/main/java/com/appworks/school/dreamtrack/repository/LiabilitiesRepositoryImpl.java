package com.appworks.school.dreamtrack.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LiabilitiesRepositoryImpl implements LiabilitiesRepository{

    private final JdbcTemplate jdbcTemplate;

    public LiabilitiesRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void insertLiabilities(Long id, String item, String action, Long liabilitiesAmount) {
        String insertSql = "INSERT INTO liabilities (user_id, item, `action`, liability_amount, `date`) VALUES (?, ?, ?, ?, now());";
        jdbcTemplate.update(insertSql, id, item, action, liabilitiesAmount);
    }
}
