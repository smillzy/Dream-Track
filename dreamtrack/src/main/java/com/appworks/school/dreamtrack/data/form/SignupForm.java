package com.appworks.school.dreamtrack.data.form;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignupForm {

    @NotBlank(message = "Name cannot be null")
    @Pattern(regexp = "^[a-zA-Z\\u4e00-\\u9fa5\\s]+$", message = "Name must be composed of English or Chinese characters")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "Email cannot be null")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email type wrong.")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Password cannot be null")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z]).{6,20}$", message = "Password must contain at least one number, one lowercase letter, and be 6-20 characters long.")
    @JsonProperty("password")
    private String password;

}
