package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.dto.LiabilitiesDto;
import com.appworks.school.dreamtrack.data.form.LiabilitiesForm;

import java.util.List;

public interface LiabilitiesService {

    void saveLiabilities(LiabilitiesForm liabilitiesForm);

    void patchLiabilities(LiabilitiesForm liabilitiesForm);

    List<LiabilitiesDto> findAllLiabilities(Long userId);

}
