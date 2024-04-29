package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.JwtUtil;
import com.appworks.school.dreamtrack.data.dto.SignDto;
import com.appworks.school.dreamtrack.data.form.LogInForm;
import com.appworks.school.dreamtrack.data.form.SignupForm;
import com.appworks.school.dreamtrack.model.User;
import com.appworks.school.dreamtrack.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private @Value("${jwt.signKey}") String jwtSignKey;
    private @Value("${jwt.expireTimeAsSec}") long jwtExpireTimeAsSec;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Boolean findUserId(Long userId) {
        return userRepository.findUserId(userId);
    }

    @Override
    public SignDto signUp(SignupForm signupForm) throws UserEmailExistException {
        boolean isEmailExist = userRepository.existsByEmail(signupForm.getEmail());
        if (isEmailExist) {
            throw new UserEmailExistException(signupForm.getEmail() + " is already exist.");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(signupForm.getPassword());

        User user = User.from(signupForm);
        user.setPassword(hashedPassword);

        Long userId = userRepository.insertUserInfo(user.getName(), user.getEmail(), user.getPassword());
        user.setUserId(userId);

        String token = JwtUtil.creatJWTSign(user.getEmail(), jwtSignKey, jwtExpireTimeAsSec);

        SignDto dto = new SignDto(token, jwtExpireTimeAsSec, user.getUserId());

        return dto;
    }

    @Override
    public SignDto login(LogInForm logInForm) throws UserNotExistException, UserPasswordMismatchException {
        boolean isEmailExist = userRepository.existsByEmail(logInForm.getEmail());
        if (!isEmailExist) {
            throw new UserNotExistException("User Not Found with email : " + logInForm.getEmail());
        }

        boolean passwordRight = checkIfPasswordRight(logInForm.getEmail(), logInForm.getPassword());
        if (!passwordRight) {
            throw new UserPasswordMismatchException("Wrong Password.");
        }

        User user = User.from(logInForm);
        String token = JwtUtil.creatJWTSign(user.getEmail(), jwtSignKey, jwtExpireTimeAsSec);

        Long userId = userRepository.getUserId(user.getEmail());

        SignDto dto = new SignDto(token, jwtExpireTimeAsSec, userId);

        return dto;
    }

    private boolean checkIfPasswordRight(String email, String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String hashPassword = userRepository.getUserPassword(email);

        return bCryptPasswordEncoder.matches(password, hashPassword);
    }

    @Override
    public Long getUserId(String email) throws UserNotExistException {
        boolean isEmailExist = userRepository.existsByEmail(email);
        if (!isEmailExist) {
            throw new UserNotExistException("User Not Found with email : " + email);
        }
        return userRepository.getUserId(email);
    }

}
