package com.appworks.school.dreamtrack.controller;

import com.appworks.school.dreamtrack.data.dto.SignDto;
import com.appworks.school.dreamtrack.data.form.LogInForm;
import com.appworks.school.dreamtrack.data.form.SignupForm;
import com.appworks.school.dreamtrack.model.ErrorResponse;
import com.appworks.school.dreamtrack.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/signup", consumes = {"application/json"})
    @ResponseBody
    public ResponseEntity<?> signUp(@Valid @RequestBody SignupForm signupForm) {
        try {
            log.info("UserController /signup -> signupForm:" + signupForm);
            SignDto dto = userService.signUp(signupForm);
            log.info("Successful send:" + dto);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (UserService.UserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping(value = "/login", consumes = {"application/json"})
    public ResponseEntity<?> userLogIn(@RequestBody LogInForm logInForm) {
        try {
            log.info("UserController /login -> logInForm:" + logInForm);
            SignDto dto = userService.login(logInForm);
            log.info("Successful send:" + dto);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (UserService.UserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

}
