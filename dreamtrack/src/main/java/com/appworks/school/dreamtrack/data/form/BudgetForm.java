package com.appworks.school.dreamtrack.data.form;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BudgetForm {
    @NotNull(message = "User ID cannot be null")
    @JsonProperty("user_id")
    private Long userId;

    @NotNull(message = "Amount cannot be null")
    @JsonProperty("budget_amount")
    private Long amount;
}
