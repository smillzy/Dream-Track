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
public class CurrentDepositDto implements RowMapper<CurrentDepositDto> {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @JsonProperty("amount")
    private Long amount;

    @Override
    public CurrentDepositDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        CurrentDepositDto dto = new CurrentDepositDto();
        dto.setId(rs.getLong("id"));
        dto.setDate(rs.getTimestamp("date").toLocalDateTime());
        dto.setAmount(rs.getLong("amount"));
        return dto;
    }
}
