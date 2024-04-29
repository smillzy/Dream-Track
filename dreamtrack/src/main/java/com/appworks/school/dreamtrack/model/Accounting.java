package com.appworks.school.dreamtrack.model;

import com.appworks.school.dreamtrack.data.form.AccountingForm;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Accounting {

    private Long id;
    private Long userId;
    private String categoryName;
    private String type;
    private Long amount;
    private String description;
    private Date date;


    public static Accounting from(AccountingForm form) {
        Accounting a = new Accounting();
        a.setId(form.getId());
        a.setCategoryName(form.getCategoryName());
        a.setType(form.getType());
        a.setAmount(form.getAmount());
        a.setDescription(form.getDescription());
        return a;
    }

    public static List<Accounting> from(List<AccountingForm> forms, Long userId) {
        List<Accounting> accountings = new ArrayList<>();
        for (AccountingForm form : forms) {
            Accounting a = new Accounting();
            a.setUserId(userId);
            a.setCategoryName(form.getCategoryName());
            a.setType(form.getType());
            a.setAmount(form.getAmount());
            a.setDescription(form.getDescription());
            a.setDate(Date.valueOf(form.getDate()));
            accountings.add(a);
        }
        return accountings;
    }

//    public static Accounting from(AccountingForm forms, Long userId, LocalDateTime now) {
//        List<Accounting> accountings = new ArrayList<>();
//        for (AccountingForm form : forms) {
//            Accounting a = new Accounting();
//            a.setUserId(userId);
//            a.setCategoryName(form.getCategoryName());
//            a.setType(form.getType());
//            a.setAmount(form.getAmount());
//            a.setDescription(form.getDescription());
//            a.setDate(now);
//            accountings.add(a);
//        }
//        return accountings;
//    }
}
