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
public class StockForm {
    @NotNull(message = "ID cannot be null", groups = StockForm.PatchOperation.class)
    @JsonProperty("id")
    private Long id;

    @NotNull(message = "User ID cannot be null", groups = StockForm.PostOperation.class)
    @JsonProperty("user_id")
    private Long userId;

    @NotBlank(message = "Symbol cannot be null", groups = Default.class)
    @JsonProperty("symbol")
    private String symbol;

    @NotBlank(message = "Action cannot be null", groups = Default.class)
    @JsonProperty("action")
    private String action;

    @NotNull(message = "Price cannot be null", groups = Default.class)
    @JsonProperty("price")
    private Long price;

    @NotNull(message = "Quantity cannot be null", groups = Default.class)
    @JsonProperty("quantity")
    private int quantity;

    @NotNull(message = "Stock amount cannot be null", groups = Default.class)
    @JsonProperty("stock_amount")
    private Long amount;

    public interface PostOperation {
    }

    public interface PatchOperation {
    }
}
