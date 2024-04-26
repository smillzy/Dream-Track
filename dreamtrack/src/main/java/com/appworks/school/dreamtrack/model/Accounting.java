package com.appworks.school.dreamtrack.model;

import com.appworks.school.dreamtrack.data.form.AccountingForm;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Accounting {

    private Long id;
    private Long userId;
    private String categoryName;
    private String type;
    private Long amount;


    public static Accounting from(AccountingForm form) {
        Accounting a = new Accounting();
        a.setCategoryName(form.getCategoryName());
        a.setType(form.getType());
        a.setAmount(form.getAmount());
        return a;
    }
}
