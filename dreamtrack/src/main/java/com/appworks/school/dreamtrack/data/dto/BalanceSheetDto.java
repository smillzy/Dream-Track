package com.appworks.school.dreamtrack.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BalanceSheetDto implements RowMapper<BalanceSheetDto> {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

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
    public BalanceSheetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        BalanceSheetDto dto = new BalanceSheetDto();
//        dto.setUserId(rs.getLong("user_id"));
//        dto.setDate(rs.getTimestamp("date").toLocalDateTime());
        dto.setAssetCurrent(rs.getLong("asset_current"));
        dto.setAssetCurrencies(rs.getLong("asset_currencies"));
        dto.setAssetStock(rs.getLong("asset_stock"));
        dto.setLiability(rs.getLong("liability"));
        dto.setNetIncome(rs.getLong("net_income"));
        return dto;
    }

    public List<BalanceItemDto> toBalanceItemDtoList() {
        List<BalanceItemDto> items = new ArrayList<>();
        items.add(new BalanceItemDto("活儲", this.assetCurrent));
        items.add(new BalanceItemDto("外幣", this.assetCurrencies));
        items.add(new BalanceItemDto("股票", this.assetStock));
        items.add(new BalanceItemDto("負債", this.liability));
        return items;
    }
}
