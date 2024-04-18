package com.appworks.school.dreamtrack.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountingDto implements RowMapper<AccountingDto> {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private String type;

    @JsonProperty("amount")
    private Long amount;

    @JsonProperty("date")
    private String date;

    @Override
    public AccountingDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        AccountingDto dto = new AccountingDto();
        LocalDateTime dateTime = rs.getTimestamp("date").toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dto.setDate(dateTime.format(formatter));
        dto.setType(rs.getString("type"));
        dto.setCategoryName(rs.getString("name"));
        dto.setAmount(rs.getLong("amount"));
        return dto;
    }
}
