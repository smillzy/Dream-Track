package com.appworks.school.dreamtrack.data.form;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrentDepositForm {
    @NotNull(message = "ID cannot be null", groups = CurrentDepositForm.PatchOperation.class)
    @JsonProperty("id")
    private Long id;

    @NotNull(message = "User ID cannot be null", groups = CurrentDepositForm.PostOperation.class)
    @JsonProperty("user_id")
    private Long userId;

    @NotNull(message = "Amount cannot be null", groups = Default.class)
    @JsonProperty("current_deposit_amount")
    private Long amount;

    public interface PostOperation {
    }

    public interface PatchOperation {
    }
}
