package com.appworks.school.dreamtrack.controller;

import com.appworks.school.dreamtrack.data.dto.LiabilitiesDto;
import com.appworks.school.dreamtrack.data.form.LiabilitiesForm;
import com.appworks.school.dreamtrack.service.LiabilitiesService;
import com.appworks.school.dreamtrack.service.UserService;
import jakarta.validation.groups.Default;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/liabilities")
@Slf4j
public class LiabilitiesController {
    private final UserService userService;
    private final LiabilitiesService liabilitiesService;

    public LiabilitiesController(UserService userService, LiabilitiesService liabilitiesService) {
        this.userService = userService;
        this.liabilitiesService = liabilitiesService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> postLiabilities(@Validated({LiabilitiesForm.PostOperation.class, Default.class})
                                             @RequestBody LiabilitiesForm liabilitiesForm) {
        log.info(liabilitiesForm.toString());
        Boolean isExist = userService.findUserId(liabilitiesForm.getUserId());
        if (isExist) {
            liabilitiesService.saveLiabilities(liabilitiesForm);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "We don't have this user.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PatchMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> patchLiabilities(@Validated({LiabilitiesForm.PatchOperation.class, Default.class})
                                              @RequestBody LiabilitiesForm liabilitiesForm) {
        log.info(liabilitiesForm.toString());
        liabilitiesService.patchLiabilities(liabilitiesForm);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @GetMapping("/detail")
    public ResponseEntity<?> GetLiabilities(@RequestParam(name = "id") Long userId) {
        Boolean isExist = userService.findUserId(userId);
        if (isExist) {
            List<LiabilitiesDto> liabilitiesDetail = liabilitiesService.findAllLiabilities(userId);

            Map<String, Object> response = new HashMap<>();
            response.put("data", liabilitiesDetail);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "We don't have this user.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
