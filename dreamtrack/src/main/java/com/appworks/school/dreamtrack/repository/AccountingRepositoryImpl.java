package com.appworks.school.dreamtrack.repository;

import com.appworks.school.dreamtrack.data.dto.AccountingDto;
import com.appworks.school.dreamtrack.data.dto.ExpensesCategoryDto;
import com.appworks.school.dreamtrack.data.dto.TotalRevenueAndExpensesDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
                    SELECT a.id, a.`date`, ac.`type`, ac.`name`, a.amount 
                    FROM accounting AS a
                    JOIN accounting_category AS ac ON a.category_id = ac.id
                    WHERE user_id = ?
                    AND DATE_FORMAT(date, '%Y-%m') = ?;
                """;
        return jdbcTemplate.query(selectSql, new AccountingDto(), userId, date);
    }

    @Override
    public TotalRevenueAndExpensesDto getTotalRevenueAndExpenses(Long userId, String date) {
        String sql = """
                    SELECT
                        SUM(CASE WHEN accounting_category.type = '收入' THEN accounting.amount ELSE 0 END) AS total_revenue,
                        SUM(CASE WHEN accounting_category.type = '支出' THEN accounting.amount ELSE 0 END) AS total_expenses
                    FROM accounting
                    JOIN accounting_category ON accounting.category_id = accounting_category.id
                    WHERE DATE_FORMAT(accounting.date, '%Y-%m') = ?
                    AND accounting.user_id = ?;
                """;
        return jdbcTemplate.queryForObject(sql, new Object[]{date, userId}, new TotalRevenueAndExpensesDto());
    }

    @Override
    public TotalRevenueAndExpensesDto getTotalRevenueAndExpenses(Long userId, String startDate, String endDate) {
        String sql = """
                    SELECT
                        SUM(CASE WHEN accounting_category.type = '收入' THEN accounting.amount ELSE 0 END) AS total_revenue,
                        SUM(CASE WHEN accounting_category.type = '支出' THEN accounting.amount ELSE 0 END) AS total_expenses
                    FROM accounting
                    JOIN accounting_category ON accounting.category_id = accounting_category.id
                    WHERE DATE_FORMAT(date, '%Y-%m') BETWEEN ? AND ?
                    AND accounting.user_id = ?;
                """;
        return jdbcTemplate.queryForObject(sql, new Object[]{startDate, endDate, userId}, new TotalRevenueAndExpensesDto());
    }

    @Override
    public List<ExpensesCategoryDto> getTotalExpensesByEachCategory(Long userId, String date) {
        String sql = """
                SELECT accounting_category.name, SUM(accounting.amount) AS `amount`
                FROM accounting
                JOIN accounting_category ON accounting.category_id = accounting_category.id 
                WHERE accounting_category.type = '支出'
                AND DATE_FORMAT(accounting.date, '%Y-%m') = ? 
                AND accounting.user_id = ? 
                GROUP BY accounting_category.name
                """;
        return jdbcTemplate.query(sql, new ExpensesCategoryDto(), date, userId);
    }

    @Override
    public List<ExpensesCategoryDto> getTotalExpensesForEachCategory(Long userId, String startDate, String endDate) {
        String sql = """
                SELECT accounting_category.name, SUM(accounting.amount) AS `amount`
                FROM accounting
                JOIN accounting_category ON accounting.category_id = accounting_category.id
                WHERE accounting_category.type = '支出'
                AND DATE_FORMAT(date, '%Y-%m') BETWEEN ? AND ?
                AND user_id = ?
                GROUP BY accounting_category.name;
                """;
        return jdbcTemplate.query(sql, new ExpensesCategoryDto(), startDate, endDate, userId);
    }

    @Override
    public void updateAccountingRecord(Long id, Long categoryId, Long amount) {
        String updateSql = "UPDATE accounting SET date = NOW(), category_id = ?, amount = ? WHERE id = ?;";
        jdbcTemplate.update(updateSql, categoryId, amount, id);
    }
}
