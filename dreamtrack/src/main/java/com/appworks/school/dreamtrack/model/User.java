package com.appworks.school.dreamtrack.model;

import com.appworks.school.dreamtrack.data.form.LogInForm;
import com.appworks.school.dreamtrack.data.form.SignupForm;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

    private Long userId;
    private String name;
    private String email;
    private String password;

    public static User from(LogInForm form) {
        User u = new User();
        u.setEmail(form.getEmail());
        return u;
    }

    public static User from(SignupForm form) {
        User u = new User();
        u.setEmail(form.getEmail());
        u.setName(form.getName());
        return u;
    }
}
