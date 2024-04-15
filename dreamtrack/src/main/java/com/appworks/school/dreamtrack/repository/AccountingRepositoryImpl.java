package com.appworks.school.dreamtrack.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccountingRepositoryImpl implements AccountingRepository{

    private final JdbcTemplate jdbcTemplate;

    public AccountingRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long findCategoryId(String categoryName, String type) {
        String getCategoryId = "SELECT id FROM accounting_category WHERE name = ? AND type = ?";
        return jdbcTemplate.queryForObject(getCategoryId, new Object[]{categoryName, type}, Long.class);
    }

    @Override
    public void insertAccountingRecord(Long id, Long categoryId, Long amount) {
        String insertSql = "INSERT INTO accounting (user_id, date, category_id, amount) VALUES (?, now(), ?, ?)";
        jdbcTemplate.update(insertSql, id, categoryId, amount);
    }

    @Override
    public void deleteAccountingRecord(Long id, Long categoryId, String date) {
        String deleteSql = "DELETE FROM accounting WHERE user_id = ? AND `date` = ? AND category_id = ?;";
        jdbcTemplate.update(deleteSql, id, date, categoryId);
    }

    @Override
    public Long getTotalExpenses(Long id, String date, String type) {
        String sql = """
            SELECT SUM(amount)
            FROM accounting
            JOIN accounting_category ON accounting.category_id = accounting_category.id
            WHERE accounting_category.type = ?
            AND DATE_FORMAT(date, '%Y-%m') = ?
            AND user_id = ?
        """;
        Long sumExpenses = jdbcTemplate.queryForObject(sql, Long.class, type, date, id);
        return sumExpenses != null ? sumExpenses : 0;
    }

    @Override
    public Long getTotalExpenses(Long id, String startDate, String endDate, String type) {
        String sql = """
            SELECT SUM(amount)
            FROM accounting
            JOIN accounting_category ON accounting.category_id = accounting_category.id
            WHERE accounting_category.type = ?
            AND DATE_FORMAT(date, '%Y-%m') BETWEEN ? AND ?
            AND user_id = ?;
        """;
        Long sumExpenses = jdbcTemplate.queryForObject(sql, Long.class, type, startDate, endDate, id);
        return sumExpenses != null ? sumExpenses : 0;
    }

    @Override
    public List<Map<String, Object>> getTotalExpensesByEachCategory(Long id, String date) {
        String sql = """
                SELECT accounting_category.name, SUM(accounting.amount) 
                FROM accounting
                JOIN accounting_category ON accounting.category_id = accounting_category.id 
                WHERE accounting_category.type = '支出'
                AND DATE_FORMAT(accounting.date, '%Y-%m') = ? 
                AND accounting.user_id = ? 
                GROUP BY accounting_category.name""";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, date, id);
        return rows;
    }

    @Override
    public List<Map<String, Object>> getTotalExpensesForEachCategory(Long id, String startDate, String endDate) {
        String sql = """
                SELECT accounting_category.name, SUM(accounting.amount)
                FROM accounting
                JOIN accounting_category ON accounting.category_id = accounting_category.id
                WHERE accounting_category.type = '支出'
                AND DATE_FORMAT(date, '%Y-%m') BETWEEN ? AND ?
                AND user_id = ?
                GROUP BY accounting_category.name;
                """;
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, startDate, endDate, id);
        return rows;
    }

    @Override
    public void updateAccountingRecord(Long id, Long categoryId, Long amount, String date) {
        String updateSql = "UPDATE accounting SET date = NOW(), category_id = ?, amount = ? WHERE user_id = ? AND date = ?;";
        jdbcTemplate.update(updateSql, categoryId, amount, id, date);
    }
}
