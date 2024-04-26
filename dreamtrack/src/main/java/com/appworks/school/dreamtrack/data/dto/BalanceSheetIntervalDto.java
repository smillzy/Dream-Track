package com.appworks.school.dreamtrack.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BalanceSheetIntervalDto implements RowMapper<BalanceSheetIntervalDto> {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("asset_current")
    private Long assetCurrent;

    @JsonProperty("asset_currencies")
    private Long assetCurrencies;

    @JsonProperty("asset_stock")
    private Long assetStock;

    @JsonProperty("liability")
    private Long liability;

    @JsonProperty("net_income")
    private Long netIncome;

    @Override
    public BalanceSheetIntervalDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        BalanceSheetIntervalDto dto = new BalanceSheetIntervalDto();
        dto.setUserId(rs.getLong("user_id"));
        dto.setAssetCurrent(rs.getLong("asset_current"));
        dto.setAssetCurrencies(rs.getLong("asset_currencies"));
        dto.setAssetStock(rs.getLong("asset_stock"));
        dto.setLiability(rs.getLong("liability"));
        dto.setNetIncome(rs.getLong("net_income"));
        return dto;
    }
}
