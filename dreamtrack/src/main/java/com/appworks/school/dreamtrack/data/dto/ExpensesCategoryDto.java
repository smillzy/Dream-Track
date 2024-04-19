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
public class ExpensesCategoryDto implements RowMapper<ExpensesCategoryDto> {

    @JsonProperty("name")
    private String name;

    @JsonProperty("amount")
    private Long amount;

    @Override
    public ExpensesCategoryDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        ExpensesCategoryDto dto = new ExpensesCategoryDto();
        dto.setName(rs.getString("name"));
        dto.setAmount(rs.getLong("amount"));
        return dto;
    }
}
