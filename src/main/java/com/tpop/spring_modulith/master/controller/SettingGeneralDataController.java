package com.tpop.spring_modulith.master.controller;

import com.tpop.spring_modulith.component.ApiResponse;
import com.tpop.spring_modulith.master.service.SettingDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/setting-data")
public class SettingGeneralDataController {

    private final SettingDataService settingDataService;

    @GetMapping(value = "/get-list-by-screen-id")
    public ResponseEntity<ApiResponse<Object>> getSettingGeneralDataByScreenId(@RequestParam("screenId") Integer screenId , Locale locale) {
        return ResponseEntity.ok(settingDataService.getSettingDataByScreenId(screenId, locale));
    }
}
