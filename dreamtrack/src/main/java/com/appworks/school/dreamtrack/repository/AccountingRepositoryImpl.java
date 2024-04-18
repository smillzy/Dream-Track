package com.appworks.school.dreamtrack.repository;

import com.appworks.school.dreamtrack.data.dto.AccountingDto;
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
    public Boolean findAccountingId(Long id) {
        String findAccountingId = "SELECT COUNT(*) FROM accounting WHERE id = ?";
        Long count = jdbcTemplate.queryForObject(findAccountingId, Long.class, id);
        return count != null && count > 0;
    }

    @Override
    public List<AccountingDto> findAllAccounting(Long userId, String date) {
        String selectSql = """
                    SELECT a.`date`, ac.`type`, ac.`name`, a.amount 
                    FROM accounting AS a
                    JOIN accounting_category AS ac ON a.category_id = ac.id
                    WHERE user_id = ?
                    AND DATE_FORMAT(date, '%Y-%m') = ?;
                """;
        return jdbcTemplate.query(selectSql, new AccountingDto(), userId, date);
    }

    @Override
    public List<Map<String, Object>> getTotalExpenses(Long userId, String date) {
        String sql = """
                    SELECT accounting_category.type, SUM(accounting.amount) AS total_amount
                    FROM accounting
                    JOIN accounting_category ON accounting.category_id = accounting_category.id
                    WHERE DATE_FORMAT(accounting.date, '%Y-%m') = ?
                    AND accounting.user_id = ?
                    GROUP BY accounting_category.type;
                """;
        return jdbcTemplate.queryForList(sql, date, userId);
    }

    @Override
    public List<Map<String, Object>> getTotalExpenses(Long userId, String startDate, String endDate) {
        String sql = """
                    SELECT SUM(amount)
                    FROM accounting
                    JOIN accounting_category ON accounting.category_id = accounting_category.id
                    WHERE DATE_FORMAT(date, '%Y-%m') BETWEEN ? AND ?
                    AND user_id = ?
                    GROUP BY accounting_category.type;
                """;
        return jdbcTemplate.queryForList(sql, startDate, endDate, userId);
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
