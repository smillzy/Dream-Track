package com.appworks.school.dreamtrack.repository;

public interface UserRepository {
    void insertUserInfo(String name, String email, String password);

    Long getUserId(String email);
}
