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

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockDto implements RowMapper<StockDto> {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @JsonProperty("action")
    private String action;

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private Long price;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("stock_amount")
    private Long amount;

    @Override
    public StockDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        StockDto dto = new StockDto();
        dto.setId(rs.getLong("id"));
        dto.setDate(rs.getTimestamp("date").toLocalDateTime());
        dto.setAction(rs.getString("action"));
        dto.setName(rs.getString("name"));
        dto.setPrice(rs.getLong("price"));
        dto.setQuantity(rs.getInt("quantity"));
        dto.setAmount(rs.getLong("amount"));
        return dto;
    }
}
