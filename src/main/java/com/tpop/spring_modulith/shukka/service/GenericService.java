package com.tpop.spring_modulith.shukka.service;

import com.tpop.spring_modulith.exception.CommonException;
import com.tpop.spring_modulith.shukka.dto.ShukkaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface GenericService {
    Page<ShukkaDto> getShukkaList(Pageable pageable , Locale locale) throws CommonException;
    ShukkaDto save(ShukkaDto shukkaDto , Locale locale) throws CommonException, ExecutionException, InterruptedException;
    Optional<ShukkaDto> findById(Long id);
    void deleteById(Long id);

}
