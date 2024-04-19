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
public class TotalRevenueAndExpensesDto implements RowMapper<TotalRevenueAndExpensesDto> {

    @JsonProperty("total_revenue")
    private Long totalRevenue;

    @JsonProperty("total_expenses")
    private Long totalExpenses;

    @Override
    public TotalRevenueAndExpensesDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        TotalRevenueAndExpensesDto dto = new TotalRevenueAndExpensesDto();
        dto.setTotalRevenue(rs.getLong("total_revenue"));
        dto.setTotalExpenses(rs.getLong("total_expenses"));
        return dto;
    }
}
