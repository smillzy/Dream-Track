package com.appworks.school.dreamtrack.model;

import com.appworks.school.dreamtrack.data.form.StockForm;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Stock {
    private Long id;
    private Long userId;
    private int stockId;
    private String action;
    private Long price;
    private int quantity;
    private Long amount;

    public static Stock from(StockForm form) {
        Stock s = new Stock();
        s.setAction(form.getAction());
        s.setPrice(form.getPrice());
        s.setQuantity(form.getQuantity());
        s.setAmount(form.getAmount());
        return s;
    }
}
