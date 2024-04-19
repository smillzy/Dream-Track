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
public class NetIncomeDto implements RowMapper<NetIncomeDto> {

    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @JsonProperty("net_income")
    private Long netIncome;

    @Override
    public NetIncomeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        NetIncomeDto dto = new NetIncomeDto();
        dto.setDate(rs.getTimestamp("date").toLocalDateTime());
        dto.setNetIncome(rs.getLong("net_income"));
        return dto;
    }
}
