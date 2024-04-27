package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.dto.SignDto;
import com.appworks.school.dreamtrack.data.form.LogInForm;
import com.appworks.school.dreamtrack.data.form.SignupForm;

public interface UserService {
    Boolean findUserId(Long userId);

    SignDto signUp(SignupForm signupForm) throws UserEmailExistException;

    SignDto login(LogInForm logInForm) throws UserNotExistException, UserPasswordMismatchException;

    sealed class UserException extends
            Exception permits UserEmailExistException, UserNotExistException, UserPasswordMismatchException {
        public UserException(String message) {
            super(message);
        }
    }

    final class UserEmailExistException extends UserException {
        public UserEmailExistException(String message) {
            super(message);
        }
    }

    final class UserNotExistException extends UserException {
        public UserNotExistException(String message) {
            super(message);
        }
    }

    final class UserPasswordMismatchException extends UserException {
        public UserPasswordMismatchException(String message) {
            super(message);
        }
    }


}
