package com.appworks.school.dreamtrack.repository;

import java.util.List;

public interface UserRepository {
    void insertUserInfo(String name, String email, String password);

    Long getUserId(String email);

    List<Long> findAllUserId();
}
