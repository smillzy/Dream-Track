package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.form.LiabilitiesForm;

import java.util.List;
import java.util.Map;

public interface LiabilitiesService {

    void saveLiabilities(LiabilitiesForm liabilitiesForm);

    void patchLiabilities(LiabilitiesForm liabilitiesForm);

    List<Map<String, Object>> findAllLiabilities(Long userId);

}
