package com.appworks.school.dreamtrack.data.form;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotNull(message = "Date cannot be null", groups = PostOperation.class)
    @JsonProperty("date")
    private String date;

    @NotBlank(message = "Category name cannot be null", groups = Default.class)
    @JsonProperty("title")
    private String categoryName;

    @NotBlank(message = "Type cannot be null", groups = Default.class)
    @JsonProperty("type")
    private String type;

    @Positive(message = "Amount must be greater than 0", groups = Default.class)
    @JsonProperty("amount")
    private Long amount;

    @NotBlank(message = "Description cannot be null", groups = Default.class)
    @JsonProperty("description")
    private String description;

    public interface PatchOperation {
    }

    public interface PostOperation {
    }

    public interface Default {
    }
}
