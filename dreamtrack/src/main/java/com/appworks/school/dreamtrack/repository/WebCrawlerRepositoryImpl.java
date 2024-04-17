package com.appworks.school.dreamtrack.repository;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class WebCrawlerRepositoryImpl implements WebCrawlerRepository {

    private final JdbcTemplate jdbcTemplate;

    public WebCrawlerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertStockData(List<Map<String, Object>> stocks) {
        String sql = "INSERT INTO stocks (symbol, name, close_price, import_time) VALUES (?, ?, ?, now())";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Map<String, Object> stock = stocks.get(i);
                ps.setString(1, (String) stock.get("code"));
                ps.setString(2, (String) stock.get("name"));
                ps.setBigDecimal(3, (BigDecimal) stock.get("close_price"));
            }

            public int getBatchSize() {
                return stocks.size();
            }
        });
    }

    @Override
    public void updateStockData(List<Map<String, Object>> stocks) {
        String sql = "UPDATE stocks SET close_price = ?, import_time = NOW() WHERE symbol = ?";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Map<String, Object> stock = stocks.get(i);
                ps.setBigDecimal(1, (BigDecimal) stock.get("closePrice"));
                ps.setString(2, (String) stock.get("code"));
            }

            public int getBatchSize() {
                return stocks.size();
            }
        });
    }

    @Override
    public void insertRates(List<Map<String, String>> rates) {
        String sql = "INSERT INTO currency (name, exchange_rate, import_time) VALUES (?, ?, now())";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Map<String, String> rate = rates.get(i);
                ps.setString(1, (String) rate.get("currency"));
                ps.setString(2, (String) rate.get("buying_rate").replaceAll("[^\\d.]", ""));
            }

            public int getBatchSize() {
                return rates.size();
            }
        });
    }

    @Override
    public void updateRates(List<Map<String, String>> rates) {
        String sql = "UPDATE currency SET exchange_rate = ?, import_time = NOW() WHERE name = ?";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Map<String, String> rate = rates.get(i);
                ps.setString(1, (String) rate.get("buying_rate").replaceAll("[^\\d.]", ""));
                ps.setString(2, (String) rate.get("currency"));
            }

            public int getBatchSize() {
                return rates.size();
            }
        });
    }
}
