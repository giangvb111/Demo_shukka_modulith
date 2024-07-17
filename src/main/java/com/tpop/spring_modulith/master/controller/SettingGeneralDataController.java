package com.tpop.spring_modulith.master.controller;

import com.tpop.spring_modulith.master.dto.SettingDataDtoImpl;
import com.tpop.spring_modulith.master.service.SettingDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/setting-data")
public class SettingGeneralDataController {

    private final SettingDataService settingDataService;

    @GetMapping(value = "/get-list-by-screen-id")
    public ResponseEntity<List<SettingDataDtoImpl>> getSettingGeneralDataByScreenId(@RequestParam("screenId") Integer screenId) {
        return ResponseEntity.ok(settingDataService.getSettingDataByScreenId(screenId));
    }
}
