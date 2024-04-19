package com.appworks.school.dreamtrack.repository;

import com.appworks.school.dreamtrack.data.dto.BalanceSheetDto;
import com.appworks.school.dreamtrack.data.dto.BalanceSheetIntervalDto;
import com.appworks.school.dreamtrack.data.dto.NetIncomeDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BalanceSheetRepositoryImpl implements BalanceSheetRepository {

    private final JdbcTemplate jdbcTemplate;

    public BalanceSheetRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Map<String, Object>> getFinancialDataForUser(Long userId) {
        String sql = """
                    SELECT 
                        (SELECT SUM(amount) FROM assets_current_deposit WHERE user_id = ?) AS assetCurrent,
                        (SELECT ROUND(SUM(afc.quantity_foreign * c.exchange_rate)) FROM assets_foreign_currencies afc JOIN currency c ON afc.currency_id = c.id WHERE afc.user_id = ?) AS assetCurrencies,
                        (SELECT ROUND(SUM(`as`.quantity * s.close_price)) FROM assets_stock `as` JOIN stocks s ON `as`.stock_id = s.id WHERE `as`.user_id = ?) AS assetStock,
                        (SELECT SUM(liability_amount) FROM liabilities WHERE user_id = ?) AS liability,
                        (SELECT afc.quantity_twd + ast.amount + acd.amount - l.liability_amount 
                            FROM (SELECT user_id, SUM(quantity_twd) AS quantity_twd FROM assets_foreign_currencies WHERE user_id = ?) AS afc
                            LEFT JOIN (SELECT user_id, SUM(amount) AS amount FROM assets_stock WHERE user_id = ?) AS ast ON afc.user_id = ast.user_id
                            LEFT JOIN (SELECT user_id, SUM(amount) AS amount FROM assets_current_deposit WHERE user_id = ?) AS acd ON afc.user_id = acd.user_id
                            LEFT JOIN (SELECT user_id, SUM(liability_amount) AS liability_amount FROM liabilities WHERE user_id = ?) AS l ON afc.user_id = l.user_id) AS netIncome
                    FROM dual;
                """;

        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, userId, userId, userId, userId, userId, userId, userId, userId);
        return results;
    }

    @Override
    public void insertBalanceSheet(Long userId, Long assetCurrent, Long assetCurrencies, Long stock, Long liability, Long netIncome) {
        String insertSql = """
                    INSERT INTO balance_sheet (user_id, `date`, asset_current, asset_currencies, asset_stock, liability, net_income) 
                    VALUES (?, NOW(), ?, ?, ?, ?, ?);
                """;
        jdbcTemplate.update(insertSql, userId, assetCurrent, assetCurrencies, stock, liability, netIncome);
    }

    @Override
    public BalanceSheetDto getBalanceSheet(Long userId, String date) {
        String sql = "SELECT * FROM balance_sheet WHERE user_id = ? AND DATE_FORMAT(`date`, '%Y-%m') = ? ;";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId, date}, new BalanceSheetDto());
    }

    @Override
    public BalanceSheetIntervalDto getBalanceSheet(Long userId, String startDate, String endDate) {
        String sql = """
                    SELECT user_id,
                           SUM(asset_current) AS asset_current,
                           SUM(asset_currencies) AS asset_currencies,
                           SUM(asset_stock) AS asset_stock,
                           SUM(liability) AS liability,
                           SUM(net_income) AS net_income
                    FROM balance_sheet
                    WHERE user_id = ?
                    AND DATE_FORMAT(`date`, '%Y-%m') BETWEEN ? AND ?;
                """;
        return jdbcTemplate.queryForObject(sql, new Object[]{userId, startDate, endDate}, new BalanceSheetIntervalDto());
    }

    @Override
    public List<NetIncomeDto> getNetIncome(Long userId, String startDate, String endDate) {
        String sql = "SELECT `date`, net_income FROM balance_sheet WHERE user_id = ? AND DATE_FORMAT(`date`, '%Y-%m') BETWEEN ? AND ?;";
        return jdbcTemplate.query(sql, new NetIncomeDto(), userId, startDate, endDate);
    }
}
