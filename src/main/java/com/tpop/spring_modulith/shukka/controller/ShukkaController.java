package com.tpop.spring_modulith.shukka.controller;

import com.tpop.spring_modulith.component.ApiResponse;
import com.tpop.spring_modulith.exception.CommonException;
import com.tpop.spring_modulith.shukka.dto.ShukkaDto;
import com.tpop.spring_modulith.shukka.dto.ShukkaSearchDto;
import com.tpop.spring_modulith.shukka.service.ShukkaHeaderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shukka")
public class ShukkaController {

    private final ShukkaHeaderServiceImpl shukkaHeaderService;

    @PostMapping(value = "/create")
    public ResponseEntity<ShukkaDto> createShukka(@RequestBody ShukkaDto shukkaDto , Locale locale) throws CommonException, ExecutionException, InterruptedException {
        return ResponseEntity.ok(shukkaHeaderService.save(shukkaDto ,locale));
    }

    @PostMapping(value = "/get-list")
    public ResponseEntity<ApiResponse<Object>> getListShukka(@RequestBody ShukkaSearchDto param, Locale locale) throws CommonException {
        return ResponseEntity.ok(shukkaHeaderService.getAllShukkaList(param, locale));
    }

}
