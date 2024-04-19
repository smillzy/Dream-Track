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
public class ForeignCurrenciesDto implements RowMapper<ForeignCurrenciesDto> {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @JsonProperty("action")
    private String action;

    @JsonProperty("name")
    private String name;

    @JsonProperty("quantity_foreign")
    private Long quantityForeign;

    @JsonProperty("quantity_TWD")
    private Long quantityTWD;

    @JsonProperty("rate")
    private Float rate;

    @Override
    public ForeignCurrenciesDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        ForeignCurrenciesDto dto = new ForeignCurrenciesDto();
        dto.setId(rs.getLong("id"));
        dto.setDate(rs.getTimestamp("date").toLocalDateTime());
        dto.setAction(rs.getString("action"));
        dto.setName(rs.getString("name"));
        dto.setQuantityForeign(rs.getLong("quantity_foreign"));
        dto.setQuantityTWD(rs.getLong("quantity_TWD"));
        dto.setRate(rs.getFloat("rate"));
        return dto;
    }
}
