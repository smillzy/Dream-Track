package com.appworks.school.dreamtrack.repository;

import java.util.List;
import java.util.Map;

public interface LiabilitiesRepository {
    void insertLiabilities(Long userId, String item, String action, Long liabilitiesAmount);

    void updateLiabilities(Long id, String item, String action, Long liabilitiesAmount);

    List<Map<String, Object>> findAllLiabilities(Long userId);
}
