package com.tpop.spring_modulith.master.service;

import com.tpop.spring_modulith.exception.CommonException;
import com.tpop.spring_modulith.master.entities.HanyouSystemMaster;
import com.tpop.spring_modulith.master.repository.HanyouRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HanyouSystemService implements GenericService<HanyouSystemMaster>{

    private final HanyouRepository hanyouRepository;

    public HanyouSystemMaster getDataFromSymtem(String key1 , String key2 , String key3) {
        return hanyouRepository.getHanyouSystemMasterByKey1AndKey2AndKey3(key1 ,key2 , key3);
    }
    @Override
    public Page<HanyouSystemMaster> findAll(Pageable pageable) throws CommonException {
        return hanyouRepository.findAll(pageable);
    }

    @Override
    public List<HanyouSystemMaster> save(List<HanyouSystemMaster> t, Locale locale) throws CommonException {
        return hanyouRepository.saveAllAndFlush(t);
    }

    @Override
    public Optional<HanyouSystemMaster> findById(Long id) {
        return hanyouRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        hanyouRepository.deleteById(id);
    }
}
