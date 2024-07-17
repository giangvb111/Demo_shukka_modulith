package com.tpop.spring_modulith.master.controller;

import com.tpop.spring_modulith.exception.CommonException;
import com.tpop.spring_modulith.master.entities.Tantosha;
import com.tpop.spring_modulith.master.service.TantoshaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tantosha")
public class TantoshaController {

    private final TantoshaServiceImpl tantoshaService;

    @GetMapping(value = "/get-tantosha-by-taishoku-flg")
    public ResponseEntity<List<Tantosha>> getTantoshaByTaishokuFlg(@RequestParam("taishokuFlg") Integer taishokuFlg) {
        return ResponseEntity.ok(tantoshaService.getTantoshaListByTaishokuFlg(taishokuFlg));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<List<Tantosha>> createTantosha(@RequestBody List<Tantosha> tantoshaList , Locale locale) throws CommonException {
        return ResponseEntity.ok(tantoshaService.save(tantoshaList , locale));
    }

}
