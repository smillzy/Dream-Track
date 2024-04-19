package com.appworks.school.dreamtrack.repository;

import com.appworks.school.dreamtrack.data.dto.CurrentDepositDto;
import com.appworks.school.dreamtrack.data.dto.ForeignCurrenciesDto;
import com.appworks.school.dreamtrack.data.dto.StockDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AssetsRepositoryImpl implements AssetsRepository {
    private final JdbcTemplate jdbcTemplate;

    public AssetsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertAssetsCurrentDeposit(Long userId, Long currentDepositAmount) {
        String insertSql = "INSERT INTO assets_current_deposit (user_id, `date`, amount) VALUES (?, NOW(), ?);";
        jdbcTemplate.update(insertSql, userId, currentDepositAmount);
    }

    @Override
    public void updateAssetsCurrentDeposit(Long id, Long currentDepositAmount) {
        String updateSql = "UPDATE assets_current_deposit SET date = NOW(), amount = ? WHERE id = ?;";
        jdbcTemplate.update(updateSql, currentDepositAmount, id);
    }

    @Override
    public List<CurrentDepositDto> findAllAssetsCurrentDeposit(Long userId) {
        String selectSql = """
                    SELECT acd.`id`, acd.`date`, acd.`amount` FROM assets_current_deposit AS acd
                    WHERE user_id = ? 
                    ORDER BY date DESC LIMIT 5;
                """;
        return jdbcTemplate.query(selectSql, new CurrentDepositDto(), userId);
    }

    @Override
    public int getCurrencyId(String currencyName) {
        String getCurrencyId = "SELECT id FROM currency WHERE name = ?;";
        return jdbcTemplate.queryForObject(getCurrencyId, new Object[]{currencyName}, Integer.class);
    }

    @Override
    public void insertAssetsForeignCurrencies(Long userId, int currencyId, String action, Long quantityForeign, Long quantityTWD, Float rate) {
        String insertSql = """
                INSERT INTO assets_foreign_currencies (user_id, currency_id, `date`, `action`, quantity_foreign, quantity_twd, rate) 
                VALUES (?, ?, NOW(), ?, ?, ?, ?);
                """;
        jdbcTemplate.update(insertSql, userId, currencyId, action, quantityForeign, quantityTWD, rate);
    }

    @Override
    public void updateAssetsForeignCurrencies(Long id, int currencyId, String action, Long quantityForeign, Long quantityTWD, Float rate) {
        String updateSql = """
                UPDATE assets_foreign_currencies 
                SET currency_id = ?, date = NOW(), `action` = ?, quantity_foreign = ?, quantity_twd = ?, rate = ? 
                WHERE id = ?;
                """;
        jdbcTemplate.update(updateSql, currencyId, action, quantityForeign, quantityTWD, rate, id);
    }

    @Override
    public List<ForeignCurrenciesDto> findAllAssetsForeignCurrencies(Long userId) {
        String selectSql = """
                        SELECT afc.id, afc.`date`, afc.`action`, currency.`name`, afc.quantity_foreign, afc.quantity_twd, afc.rate
                        FROM assets_foreign_currencies AS afc
                        JOIN currency ON afc.currency_id = currency.id
                        WHERE user_id = ?
                         ORDER BY date DESC LIMIT 5;
                """;
        return jdbcTemplate.query(selectSql, new ForeignCurrenciesDto(), userId);
    }

    @Override
    public int getStockId(String symbol) {
        String getStockId = "SELECT id FROM stocks WHERE symbol = ?;";
        return jdbcTemplate.queryForObject(getStockId, new Object[]{symbol}, Integer.class);
    }

    @Override
    public void insertAssetsStock(Long userId, int stockId, String action, Long price, int quantity, Long stockAmount) {
        String insertSql = """
                INSERT INTO assets_stock (user_id, stock_id, `date`, `action`, price, quantity, amount)
                VALUES (?, ?, NOW(), ?, ?, ?, ?);
                """;
        jdbcTemplate.update(insertSql, userId, stockId, action, price, quantity, stockAmount);
    }

    @Override
    public void updateAssetsStock(Long id, int stockId, String action, Long price, int quantity, Long stockAmount) {
        String updateSql = """
                UPDATE assets_stock 
                SET stock_id = ?, date = NOW(), `action` = ?, price = ?, quantity = ?, amount = ? 
                WHERE id = ?;
                """;
        jdbcTemplate.update(updateSql, stockId, action, price, quantity, stockAmount, id);
    }

    @Override
    public List<StockDto> findAllAssetsStock(Long userId) {
        String selectSql = """
                        SELECT `as`.id, `as`.`date`, `as`.`action`, stocks.`name`, `as`.price, `as`.quantity, `as`.amount
                        FROM assets_stock AS `as`
                        JOIN stocks ON `as`.stock_id = stocks.id
                        WHERE user_id = ?
                        ORDER BY date DESC LIMIT 5;
                """;
        return jdbcTemplate.query(selectSql, new StockDto(), userId);
    }
}
