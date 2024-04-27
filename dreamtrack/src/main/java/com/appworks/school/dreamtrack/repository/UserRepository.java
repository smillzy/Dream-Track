package com.appworks.school.dreamtrack.repository;

import java.util.List;

public interface UserRepository {
    Long insertUserInfo(String name, String email, String password);

    Long getUserId(String email);

    List<Long> findAllUserId();

    Boolean findUserId(Long userId);

    boolean existsByEmail(String email);

    String getUserPassword(String email);
}
