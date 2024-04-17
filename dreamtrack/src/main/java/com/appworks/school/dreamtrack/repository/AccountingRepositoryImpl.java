package com.appworks.school.dreamtrack.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AccountingRepositoryImpl implements AccountingRepository {

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
    public void insertAccountingRecord(Long userId, Long categoryId, Long amount) {
        String insertSql = "INSERT INTO accounting (user_id, date, category_id, amount) VALUES (?, now(), ?, ?)";
        jdbcTemplate.update(insertSql, userId, categoryId, amount);
    }

    @Override
    public void deleteAccountingRecord(Long id) {
        String deleteSql = "DELETE FROM accounting WHERE id = ?;";
        jdbcTemplate.update(deleteSql, id);
    }

    @Override
    public List<Map<String, Object>> findAllAccounting(Long userId, String date) {
        String selectSql = "SELECT * FROM accounting WHERE user_id = ? AND DATE_FORMAT(date, '%Y-%m') = ?;";
        return jdbcTemplate.queryForList(selectSql, userId, date);
    }

    @Override
    public Long getTotalExpenses(Long userId, String date, String type) {
        String sql = """
                    SELECT SUM(amount)
                    FROM accounting
                    JOIN accounting_category ON accounting.category_id = accounting_category.id
                    WHERE accounting_category.type = ?
                    AND DATE_FORMAT(date, '%Y-%m') = ?
                    AND user_id = ?
                """;
        Long sumExpenses = jdbcTemplate.queryForObject(sql, Long.class, type, date, userId);
        return sumExpenses != null ? sumExpenses : 0;
    }

    @Override
    public Long getTotalExpenses(Long userId, String startDate, String endDate, String type) {
        String sql = """
                    SELECT SUM(amount)
                    FROM accounting
                    JOIN accounting_category ON accounting.category_id = accounting_category.id
                    WHERE accounting_category.type = ?
                    AND DATE_FORMAT(date, '%Y-%m') BETWEEN ? AND ?
                    AND user_id = ?;
                """;
        Long sumExpenses = jdbcTemplate.queryForObject(sql, Long.class, type, startDate, endDate, userId);
        return sumExpenses != null ? sumExpenses : 0;
    }

    @Override
    public List<Map<String, Object>> getTotalExpensesByEachCategory(Long userId, String date) {
        String sql = """
                SELECT accounting_category.name, SUM(accounting.amount) 
                FROM accounting
                JOIN accounting_category ON accounting.category_id = accounting_category.id 
                WHERE accounting_category.type = '支出'
                AND DATE_FORMAT(accounting.date, '%Y-%m') = ? 
                AND accounting.user_id = ? 
                GROUP BY accounting_category.name""";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, date, userId);
        return rows;
    }

    @Override
    public List<Map<String, Object>> getTotalExpensesForEachCategory(Long userId, String startDate, String endDate) {
        String sql = """
                SELECT accounting_category.name, SUM(accounting.amount)
                FROM accounting
                JOIN accounting_category ON accounting.category_id = accounting_category.id
                WHERE accounting_category.type = '支出'
                AND DATE_FORMAT(date, '%Y-%m') BETWEEN ? AND ?
                AND user_id = ?
                GROUP BY accounting_category.name;
                """;
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, startDate, endDate, userId);
        return rows;
    }

    @Override
    public void updateAccountingRecord(Long id, Long categoryId, Long amount) {
        String updateSql = "UPDATE accounting SET date = NOW(), category_id = ?, amount = ? WHERE id = ?;";
        jdbcTemplate.update(updateSql, categoryId, amount, id);
    }
}
