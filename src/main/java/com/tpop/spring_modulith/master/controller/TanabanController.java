package com.tpop.spring_modulith.master.controller;

import com.tpop.spring_modulith.exception.CommonException;
import com.tpop.spring_modulith.master.entities.TanabanMaster;
import com.tpop.spring_modulith.master.service.TanabanServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tanaban")
public class TanabanController {

    private final TanabanServiceImpl tanabanService;

    @GetMapping(value = "/get-tanaban-by-souko-id")
    public ResponseEntity<List<TanabanMaster>> getTanabanListBySoukoId(@RequestParam("soukoId") Long soukoId) {
        return ResponseEntity.ok(tanabanService.getTanabanListBySoukoId(soukoId));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<List<TanabanMaster>> createTanabanList(@RequestBody List<TanabanMaster> tanabanMasterList , Locale locale) throws CommonException {
        return ResponseEntity.ok(tanabanService.save(tanabanMasterList ,locale));
    }
}
