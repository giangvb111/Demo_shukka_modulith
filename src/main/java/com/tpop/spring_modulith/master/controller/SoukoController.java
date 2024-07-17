package com.tpop.spring_modulith.master.controller;

import com.tpop.spring_modulith.exception.CommonException;
import com.tpop.spring_modulith.master.entities.SoukoMaster;
import com.tpop.spring_modulith.master.service.SoukoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/souko")
public class SoukoController {

    private final SoukoServiceImpl soukoService;

    @GetMapping(value = "/get-list")
    public ResponseEntity<List<SoukoMaster>> getSoukoList() {
        return ResponseEntity.ok(soukoService.getSoukoList());
    }

    @PostMapping(value = "/create")
    public ResponseEntity<List<SoukoMaster>> createSoukoList(@RequestBody List<SoukoMaster> soukoMasterList , Locale locale) throws CommonException {
        return ResponseEntity.ok(soukoService.save(soukoMasterList ,locale));
    }
}

