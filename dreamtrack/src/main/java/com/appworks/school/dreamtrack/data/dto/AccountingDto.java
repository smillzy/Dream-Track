package com.appworks.school.dreamtrack.data.dto;

import com.appworks.school.dreamtrack.model.Accounting;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountingDto implements RowMapper<AccountingDto> {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("start")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @JsonProperty("description")
    private String description;

    @JsonProperty("amount")
    private Long amount;

    @JsonProperty("type")
    private String type;

    @JsonProperty("item")
    private String name;

    public static AccountingDto fromAccounting(Long id, Accounting accounting) {
        AccountingDto dto = new AccountingDto();
        dto.setId(id);
        dto.setTitle(accounting.getCategoryName());
        dto.setDate(accounting.getDate());
        dto.setDescription(accounting.getDescription());
        dto.setAmount(accounting.getAmount());
        dto.setType(accounting.getType());
        dto.setName(accounting.getCategoryName());
        return dto;
    }

    @Override
    public AccountingDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        AccountingDto dto = new AccountingDto();
        dto.setId(rs.getLong("id"));
        dto.setTitle(rs.getString("name"));
        dto.setDate(rs.getDate("date"));
        dto.setDescription(rs.getString("description"));
        dto.setAmount(rs.getLong("amount"));
        dto.setType(rs.getString("type"));
        dto.setName(rs.getString("name"));
        return dto;
    }
}
