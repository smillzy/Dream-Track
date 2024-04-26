package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.dto.LiabilitiesDto;
import com.appworks.school.dreamtrack.data.form.LiabilitiesForm;
import com.appworks.school.dreamtrack.model.Liabilities;
import com.appworks.school.dreamtrack.repository.LiabilitiesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiabilitiesServiceImpl implements LiabilitiesService {

    private final LiabilitiesRepository liabilitiesRepository;

    public LiabilitiesServiceImpl(LiabilitiesRepository liabilitiesRepository) {
        this.liabilitiesRepository = liabilitiesRepository;
    }

    @Override
    public void saveLiabilities(LiabilitiesForm liabilitiesForm) {
        Liabilities l = Liabilities.from(liabilitiesForm);
        l.setUserId(liabilitiesForm.getUserId());
        liabilitiesRepository.insertLiabilities(l.getUserId(), l.getItem(), l.getAction(), l.getAmount());
    }

    @Override
    public void patchLiabilities(LiabilitiesForm liabilitiesForm) {
        Liabilities l = Liabilities.from(liabilitiesForm);
        l.setId(liabilitiesForm.getId());
        liabilitiesRepository.updateLiabilities(l.getId(), l.getItem(), l.getAction(), l.getAmount());
    }

    @Override
    public List<LiabilitiesDto> findAllLiabilities(Long userId) {
        List<LiabilitiesDto> results = liabilitiesRepository.findAllLiabilities(userId);
        return results;
    }
}
