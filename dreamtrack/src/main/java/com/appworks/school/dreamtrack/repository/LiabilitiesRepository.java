package com.appworks.school.dreamtrack.repository;

public interface LiabilitiesRepository {
    void insertLiabilities(Long id, String item, String action, Long liabilitiesAmount);

    void updateLiabilities(Long id, String date, String item, String action, Long liabilitiesAmount);
}
