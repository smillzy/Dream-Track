package com.appworks.school.dreamtrack.data.form;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LiabilitiesForm {
    @NotNull(message = "ID cannot be null", groups = LiabilitiesForm.PatchOperation.class)
    @JsonProperty("id")
    private Long id;

    @NotNull(message = "User ID cannot be null", groups = LiabilitiesForm.PostOperation.class)
    @JsonProperty("user_id")
    private Long userId;

    @NotBlank(message = "Item cannot be null", groups = Default.class)
    @JsonProperty("item")
    private String item;

    @NotBlank(message = "Action cannot be null", groups = Default.class)
    @JsonProperty("action")
    private String action;

    @NotNull(message = "Liability amount cannot be null", groups = Default.class)
    @JsonProperty("liability_amount")
    private Long amount;

    public interface PostOperation {
    }

    public interface PatchOperation {
    }
}
