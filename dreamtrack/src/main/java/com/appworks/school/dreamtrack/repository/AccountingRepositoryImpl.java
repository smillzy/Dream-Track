package com.appworks.school.dreamtrack.repository;

import com.appworks.school.dreamtrack.data.dto.AccountingDto;
import com.appworks.school.dreamtrack.data.dto.ExpensesCategoryDto;
import com.appworks.school.dreamtrack.data.dto.NowAndPreMonthDto;
import com.appworks.school.dreamtrack.data.dto.TotalRevenueAndExpensesDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
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
    public Long insertAccountingRecord(Long userId, Date date, Long categoryId, Long amount, String description) {
        String insertSql = "INSERT INTO accounting (user_id, date, category_id, amount, description) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"id"}); // "id" 是自动生成的主键列名
                    ps.setLong(1, userId);
                    ps.setDate(2, date);
                    ps.setLong(3, categoryId);
                    ps.setLong(4, amount);
                    ps.setString(5, description);
                    return ps;
                },
                keyHolder);

        return keyHolder.getKey().longValue();
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
    public List<AccountingDto> findAllAccounting(Long userId) {
        String selectSql = """
                    SELECT a.id, a.`date`, ac.`type`, ac.`name`, a.amount, a.description
                    FROM accounting AS a
                    JOIN accounting_category AS ac ON a.category_id = ac.id
                    WHERE user_id = ?;
                """;
        return jdbcTemplate.query(selectSql, new AccountingDto(), userId);
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
    public TotalRevenueAndExpensesDto getTotalRevenueAndExpensesForYear(Long userId, String year) {
        String sql = """
                    SELECT
                        SUM(CASE WHEN accounting_category.type = '收入' THEN accounting.amount ELSE 0 END) AS total_revenue,
                        SUM(CASE WHEN accounting_category.type = '支出' THEN accounting.amount ELSE 0 END) AS total_expenses
                    FROM accounting
                    JOIN accounting_category ON accounting.category_id = accounting_category.id
                    WHERE DATE_FORMAT(accounting.date, '%Y') = ?
                    AND accounting.user_id = ?;
                """;
        return jdbcTemplate.queryForObject(sql, new Object[]{year, userId}, new TotalRevenueAndExpensesDto());
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
    public List<ExpensesCategoryDto> getTotalExpensesByEachCategoryForYear(Long userId, String date) {
        String sql = """
                SELECT accounting_category.name, SUM(accounting.amount) AS `amount`
                FROM accounting
                JOIN accounting_category ON accounting.category_id = accounting_category.id
                WHERE accounting_category.type = '支出'
                AND DATE_FORMAT(date, '%Y') = ?
                AND user_id = ?
                GROUP BY accounting_category.name;
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
    public void updateAccountingRecord(Long id, Long categoryId, Long amount, String description) {
        String updateSql = "UPDATE accounting SET category_id = ?, amount = ?, description = ? WHERE id = ?;";
        jdbcTemplate.update(updateSql, categoryId, amount, description, id);
    }

    @Override
    public List<NowAndPreMonthDto> getTotalExpensesNowAndPreMonth(Long userId, String startDate, String endDate) {
        String sql = """
                SELECT\s
                    accounting_category.name,
                    SUM(CASE WHEN DATE_FORMAT(accounting.date, '%Y-%m') = ? THEN accounting.amount ELSE 0 END) AS last_month,
                    SUM(CASE WHEN DATE_FORMAT(accounting.date, '%Y-%m') = ? THEN accounting.amount ELSE 0 END) AS this_month
                FROM accounting
                JOIN accounting_category ON accounting.category_id = accounting_category.id
                WHERE accounting_category.type = '支出'
                AND accounting.user_id = ?
                AND DATE_FORMAT(accounting.date, '%Y-%m') BETWEEN ? AND ?
                GROUP BY accounting_category.name;
                """;
        return jdbcTemplate.query(sql, new NowAndPreMonthDto(), startDate, endDate, userId, startDate, endDate);
    }
}
