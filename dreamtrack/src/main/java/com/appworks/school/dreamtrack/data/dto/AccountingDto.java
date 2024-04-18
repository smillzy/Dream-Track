package com.appworks.school.dreamtrack.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountingDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("type")
    private String type;

    @JsonProperty("amount")
    private Long amount;
}
