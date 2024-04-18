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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @Override
    public AccountingDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        AccountingDto dto = new AccountingDto();
        dto.setDate(rs.getTimestamp("date").toLocalDateTime());
        dto.setType(rs.getString("type"));
        dto.setCategoryName(rs.getString("name"));
        dto.setAmount(rs.getLong("amount"));
        return dto;
    }
}
