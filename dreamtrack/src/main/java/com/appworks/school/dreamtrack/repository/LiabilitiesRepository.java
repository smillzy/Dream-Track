package com.appworks.school.dreamtrack.repository;

import com.appworks.school.dreamtrack.data.dto.LiabilitiesDto;

import java.util.List;

public interface LiabilitiesRepository {
    void insertLiabilities(Long userId, String item, String action, Long liabilitiesAmount);

    void updateLiabilities(Long id, String item, String action, Long liabilitiesAmount);

    List<LiabilitiesDto> findAllLiabilities(Long userId);
}
