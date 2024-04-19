package com.appworks.school.dreamtrack.data.dto;

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
public class BudgetDto implements RowMapper<BudgetDto> {
    @JsonProperty("budget_amount")
    private Long budgetAmount;

    @Override
    public BudgetDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        BudgetDto dto = new BudgetDto();
        dto.setBudgetAmount(rs.getLong("budget_amount"));
        return dto;
    }
}
