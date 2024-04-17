package com.appworks.school.dreamtrack.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BudgetRepositoryImpl implements BudgetRepository {

    private final JdbcTemplate jdbcTemplate;

    public BudgetRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertBudget(Long userId, Long budgetAmount) {
        String insertSql = "INSERT INTO budget (user_id, budget_amount) VALUES (?, ?)";
        jdbcTemplate.update(insertSql, userId, budgetAmount);
    }

    @Override
    public void updateBudget(Long userId, Long budgetAmount) {
        String updateSql = "UPDATE budget SET budget_amount = ? WHERE user_id = ?;";
        jdbcTemplate.update(updateSql, budgetAmount, userId);
    }

    @Override
    public Long getTotalBudget(Long userId) {
        String sql = "SELECT budget_amount FROM budget WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, userId);
    }

}
