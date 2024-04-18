package com.appworks.school.dreamtrack.data.form;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountingForm {
    @NotNull(message = "ID cannot be null", groups = PatchOperation.class)
    @JsonProperty("id")
    private Long id;

    @NotNull(message = "User ID cannot be null", groups = PostOperation.class)
    @JsonProperty("user_id")
    private Long userId;

    @NotBlank(message = "Category name cannot be null", groups = Default.class)
    @JsonProperty("category_name")
    private String categoryName;

    @NotBlank(message = "Type cannot be null", groups = Default.class)
    @JsonProperty("type")
    private String type;

    @Positive(message = "Amount must be greater than 0", groups = Default.class)
    @JsonProperty("amount")
    private Long amount;

    public interface PostOperation {
    }

    public interface PatchOperation {
    }
}
