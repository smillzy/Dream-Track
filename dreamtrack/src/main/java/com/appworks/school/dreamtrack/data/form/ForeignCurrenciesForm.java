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
public class ForeignCurrenciesForm {
    @NotNull(message = "ID cannot be null", groups = ForeignCurrenciesForm.PatchOperation.class)
    @JsonProperty("id")
    private Long id;

    @NotNull(message = "User ID cannot be null", groups = ForeignCurrenciesForm.PostOperation.class)
    @JsonProperty("user_id")
    private Long userId;

    @NotBlank(message = "Currency name cannot be null", groups = Default.class)
    @JsonProperty("currency_name")
    private String currencyName;

    @NotBlank(message = "Action cannot be null", groups = Default.class)
    @JsonProperty("action")
    private String action;

    @NotNull(message = "Foreign quantity cannot be null", groups = Default.class)
    @JsonProperty("quantity_foreign")
    private Long quantityForeign;

    @NotNull(message = "TWD quantity cannot be null", groups = Default.class)
    @JsonProperty("quantity_TWD")
    private Long quantityTWD;

    @NotNull(message = "Rate cannot be null", groups = Default.class)
    @JsonProperty("rate")
    private Float rate;

    public interface PostOperation {
    }

    public interface PatchOperation {
    }
}
