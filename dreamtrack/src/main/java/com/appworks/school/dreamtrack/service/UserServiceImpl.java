package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Boolean findUserId(Long userId) {
        return userRepository.findUserId(userId);
    }
}
