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
public class NowAndPreMonthDto implements RowMapper<NowAndPreMonthDto> {

    @JsonProperty("name")
    private String name;

    @JsonProperty("last_month")
    private Long lastMonth;

    @JsonProperty("this_month")
    private Long thisMonth;

    @Override
    public NowAndPreMonthDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        NowAndPreMonthDto dto = new NowAndPreMonthDto();
        dto.setName(rs.getString("name"));
        dto.setLastMonth(rs.getLong("last_month"));
        dto.setThisMonth(rs.getLong("this_month"));
        return dto;
    }
}
