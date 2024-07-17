package com.tpop.spring_modulith.master.controller;

import com.tpop.spring_modulith.component.ApiResponse;
import com.tpop.spring_modulith.exception.CommonException;
import com.tpop.spring_modulith.master.entities.NouhinsakiMaster;
import com.tpop.spring_modulith.master.service.NouhinsakiServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/nouhinsaki")
public class NouhinsakiController {

    private final NouhinsakiServiceImpl nouhinsakiService;

    @GetMapping(value = "/get-list")
    public ResponseEntity<ApiResponse<Object>> getNouhinsakiList(Locale locale) throws CommonException {
        return ResponseEntity.ok(nouhinsakiService.getNouhisakiList(locale));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<List<NouhinsakiMaster>> createNouhinsakiList(@RequestBody List<NouhinsakiMaster> nouhinsakiMasterList , Locale locale) throws CommonException {
        return ResponseEntity.ok(nouhinsakiService.save(nouhinsakiMasterList , locale));
    }
}
