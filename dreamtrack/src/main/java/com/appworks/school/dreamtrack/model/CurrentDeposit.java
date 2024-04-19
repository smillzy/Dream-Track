package com.appworks.school.dreamtrack.model;

import com.appworks.school.dreamtrack.data.form.CurrentDepositForm;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurrentDeposit {
    private Long id;
    private Long userId;
    private Long amount;

    public static CurrentDeposit from(CurrentDepositForm form) {
        CurrentDeposit c = new CurrentDeposit();
        c.setAmount(form.getAmount());
        return c;
    }
}
