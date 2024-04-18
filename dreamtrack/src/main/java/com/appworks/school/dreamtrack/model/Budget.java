package com.appworks.school.dreamtrack.model;

import com.appworks.school.dreamtrack.data.form.BudgetForm;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Budget {
    private Long userId;
    private Long amount;

    public static Budget from(BudgetForm form) {
        Budget b = new Budget();
        b.setUserId(form.getUserId());
        b.setAmount(form.getAmount());
        return b;
    }
}
