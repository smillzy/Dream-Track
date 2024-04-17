package com.appworks.school.dreamtrack.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class LiabilitiesRepositoryImpl implements LiabilitiesRepository {

    private final JdbcTemplate jdbcTemplate;

    public LiabilitiesRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertLiabilities(Long userId, String item, String action, Long liabilitiesAmount) {
        String insertSql = """
                     INSERT INTO liabilities (user_id, item, `action`, liability_amount, `date`) 
                     VALUES (?, ?, ?, ?, now());
                """;
        jdbcTemplate.update(insertSql, userId, item, action, liabilitiesAmount);
    }

    @Override
    public void updateLiabilities(Long id, String item, String action, Long liabilitiesAmount) {
        String insertSql = """
                    UPDATE liabilities 
                    SET item = ?, `action` = ?, liability_amount = ?, date = NOW() 
                    WHERE id = ?;
                """;
        jdbcTemplate.update(insertSql, item, action, liabilitiesAmount, id);
    }

    @Override
    public List<Map<String, Object>> findAllLiabilities(Long userId) {
        String selectSql = "SELECT * FROM liabilities WHERE user_id = ? ORDER BY date DESC LIMIT 5;";
        return jdbcTemplate.queryForList(selectSql, userId);
    }

}
