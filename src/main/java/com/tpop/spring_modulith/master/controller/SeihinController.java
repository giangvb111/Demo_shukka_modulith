package com.tpop.spring_modulith.master.controller;

import com.tpop.spring_modulith.exception.CommonException;
import com.tpop.spring_modulith.master.entities.SeihinMaster;
import com.tpop.spring_modulith.master.service.SeihinServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seihin")
public class SeihinController {

    private final SeihinServiceImpl seihinService;

    @GetMapping(value = "/get-list")
    public ResponseEntity<List<SeihinMaster>> getSeihinList() {
       return ResponseEntity.ok(seihinService.getSeihinList());
    }

    @PostMapping(value = "/create")
    public ResponseEntity<List<SeihinMaster>> getSeihinList(@RequestBody List<SeihinMaster> seihinMasterList , Locale locale) throws CommonException {
        return ResponseEntity.ok(seihinService.save(seihinMasterList ,locale));
    }
}
