package com.appworks.school.dreamtrack.repository;

import com.appworks.school.dreamtrack.data.dto.LiabilitiesDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<LiabilitiesDto> findAllLiabilities(Long userId) {
        String selectSql = """
                    SELECT l.item, l.`action`, l.liability_amount, l.`date` FROM liabilities AS l
                    WHERE user_id = ?
                    ORDER BY date DESC LIMIT 5;
                """;
        return jdbcTemplate.query(selectSql, new LiabilitiesDto(), userId);
    }

}
