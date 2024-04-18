package com.appworks.school.dreamtrack.service;

import com.appworks.school.dreamtrack.data.form.LiabilitiesForm;
import com.appworks.school.dreamtrack.model.Liabilities;
import com.appworks.school.dreamtrack.repository.LiabilitiesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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
    public List<Map<String, Object>> findAllLiabilities(Long userId) {
        List<Map<String, Object>> results = liabilitiesRepository.findAllLiabilities(userId);
        for (Map<String, Object> record : results) {
            if (record.get("date") != null) {
                LocalDateTime dateTime = (LocalDateTime) record.get("date");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDate = dateTime.format(formatter);
                record.put("date", formattedDate);
            }
        }
        return results;
    }
}
