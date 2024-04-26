package com.appworks.school.dreamtrack.model;

import com.appworks.school.dreamtrack.data.form.LiabilitiesForm;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Liabilities {
    private Long id;
    private Long userId;
    private String item;
    private String action;
    private Long amount;


    public static Liabilities from(LiabilitiesForm form) {
        Liabilities l = new Liabilities();
        l.setItem(form.getItem());
        l.setAction(form.getAction());
        l.setAmount(form.getAmount());
        return l;
    }
}
