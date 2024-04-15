package com.appworks.school.dreamtrack.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AssetsRepositoryImpl implements AssetsRepository{
    private final JdbcTemplate jdbcTemplate;

    public AssetsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertAssetsCurrentDeposit(Long id, Long currentDepositAmount) {
        String insertSql = "INSERT INTO assets_current_deposit (user_id, `date`, amount) VALUES (?, NOW(), ?);";
        jdbcTemplate.update(insertSql, id, currentDepositAmount);
    }

    @Override
    public void updateAssetsCurrentDeposit(Long id, String date, Long currentDepositAmount) {
        String updateSql = "UPDATE assets_current_deposit SET date = NOW(), amount = ? WHERE user_id = ? AND `date` = ?;";
        jdbcTemplate.update(updateSql, currentDepositAmount, id, date);
    }

    @Override
    public int getCurrencyId(String currencyName) {
        String getCurrencyId = "SELECT id FROM currency WHERE name = ?;";
        return jdbcTemplate.queryForObject(getCurrencyId, new Object[]{currencyName}, Integer.class);
    }

    @Override
    public void insertAssetsForeignCurrencies(Long id, int currencyId, String action, Long quantityForeign, Long quantityTWD, Float rate) {
        String insertSql = """
        INSERT INTO assets_foreign_currencies (user_id, currency_id, `date`, `action`, quantity_foreign, quantity_twd, rate) 
        VALUES (?, ?, NOW(), ?, ?, ?, ?);
        """;
        jdbcTemplate.update(insertSql, id, currencyId, action, quantityForeign, quantityTWD, rate);
    }

    @Override
    public void updateAssetsForeignCurrencies(Long id, String date, int currencyId, String action, Long quantityForeign, Long quantityTWD, Float rate) {
        String updateSql = """
        UPDATE assets_foreign_currencies 
        SET currency_id = ?, date = NOW(), `action` = ?, quantity_foreign = ?, quantity_twd = ?, rate = ? 
        WHERE user_id = ? AND `date` = ?;
        """;
        jdbcTemplate.update(updateSql, currencyId, action, quantityForeign, quantityTWD, rate, id, date);
    }

    @Override
    public int getStockId(String symbol) {
        String getStockId = "SELECT id FROM stocks WHERE symbol = ?;";
        return jdbcTemplate.queryForObject(getStockId, new Object[]{symbol}, Integer.class);
    }

    @Override
    public void insertAssetsStock(Long id, int stockId, String action, Long price, int quantity, Long stockAmount) {
        String insertSql = """
        INSERT INTO assets_stock (user_id, stock_id, `date`, `action`, price, quantity, amount)
        VALUES (?, ?, NOW(), ?, ?, ?, ?);
        """;
        jdbcTemplate.update(insertSql, id, stockId, action, price, quantity, stockAmount);
    }

    @Override
    public void updateAssetsStock(Long id, String date, int stockId, String action, Long price, int quantity, Long stockAmount) {
        String updateSql = """
        UPDATE assets_stock 
        SET stock_id = ?, date = NOW(), `action` = ?, price = ?, quantity = ?, amount = ? 
        WHERE user_id = ? AND `date` = ?;
        """;
        jdbcTemplate.update(updateSql, stockId, action, price, quantity, stockAmount, id, date);
    }
}
