package com.appworks.school.dreamtrack.repository;

import java.util.List;
import java.util.Map;

public interface LiabilitiesRepository {
    void insertLiabilities(Long id, String item, String action, Long liabilitiesAmount);

    void updateLiabilities(Long id, String date, String item, String action, Long liabilitiesAmount);

    List<Map<String, Object>> findAllLiabilities(Long id);
}
