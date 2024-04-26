package com.appworks.school.dreamtrack.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class LiabilitiesDto implements RowMapper<LiabilitiesDto> {

    @JsonProperty("item")
    private String item;

    @JsonProperty("action")
    private String action;

    @JsonProperty("liability_amount")
    private Long amount;

    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @Override
    public LiabilitiesDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        LiabilitiesDto dto = new LiabilitiesDto();
        dto.setItem(rs.getString("item"));
        dto.setAction(rs.getString("action"));
        dto.setAmount(rs.getLong("liability_amount"));
        dto.setDate(rs.getTimestamp("date").toLocalDateTime());
        return dto;
    }
}
