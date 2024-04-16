package com.appworks.school.dreamtrack.repository;

import java.util.List;
import java.util.Map;

public interface WebCrawlerRepository {
    void insertStockData(List<Map<String, Object>> stocks);

    void updateStockData(List<Map<String, Object>> stocks);

    void insertRates(List<Map<String, String>> rates);

    void updateRates(List<Map<String, String>> rates);
}
