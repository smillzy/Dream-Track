package com.appworks.school.dreamtrack.model;

import com.appworks.school.dreamtrack.data.form.ForeignCurrenciesForm;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ForeignCurrencies {
    private Long id;
    private Long userId;
    private int currencyId;
    private String action;
    private Long quantityForeign;
    private Long quantityTWD;
    private Float rate;

    public static ForeignCurrencies from(ForeignCurrenciesForm form) {
        ForeignCurrencies fc = new ForeignCurrencies();
        fc.setAction(form.getAction());
        fc.setQuantityForeign(form.getQuantityForeign());
        fc.setQuantityTWD(form.getQuantityTWD());
        fc.setRate(form.getRate());
        return fc;
    }
}
