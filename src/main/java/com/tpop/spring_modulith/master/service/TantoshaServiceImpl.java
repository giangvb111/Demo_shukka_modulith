package com.tpop.spring_modulith.master.service;

import com.tpop.spring_modulith.constant.MessageCode;
import com.tpop.spring_modulith.event.Event;
import com.tpop.spring_modulith.exception.APIErrorDetail;
import com.tpop.spring_modulith.exception.CommonException;
import com.tpop.spring_modulith.master.entities.SoukoMaster;
import com.tpop.spring_modulith.master.entities.Tantosha;
import com.tpop.spring_modulith.master.repository.TanabanRepository;
import com.tpop.spring_modulith.master.repository.TantoshaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class TantoshaServiceImpl implements GenericService<Tantosha>{

    private final TantoshaRepository tantoshaRepository;
    private final MessageSource messageSource;

    @Override
    public Page<Tantosha> findAll(Pageable pageable) throws CommonException {
        return null;
    }

    public List<Tantosha> getTantoshaListByTaishokuFlg(Integer taishokuFlg) {
        List<Tantosha> tantoshaList = tantoshaRepository.findTantoshaByTaishokuFlg(taishokuFlg);
        if (CollectionUtils.isEmpty(tantoshaList)) {
            return Collections.emptyList();
        }
        return tantoshaList;
    }

    @Override
    public List<Tantosha> save(List<Tantosha> tantoshaList, Locale locale) throws CommonException {
        List<Tantosha> createTantoshaList = new ArrayList<>();
        try {

            LocalDateTime currentTime  = LocalDateTime.now();
            if (!CollectionUtils.isEmpty(tantoshaList)) {
                List<APIErrorDetail> errorDetails = new ArrayList<>();
                AtomicInteger i = new AtomicInteger();
                tantoshaList.forEach(s ->{
                    if (s.getTantoshaName().isEmpty()) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.longValue(),
                                "tantoshaName" ,
                                MessageCode.CHECK_EXISTS ,
                                messageSource.getMessage(
                                        MessageCode.CHECK_EXISTS , null , locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }
                    i.getAndIncrement();
                });
                if (!CollectionUtils.isEmpty(errorDetails)) {
                    throw new CommonException()
                            .setErrorCode(MessageCode.CHECK_EXISTS)
                            .setStatusCode(HttpStatus.BAD_REQUEST)
                            .setErrorDetails(errorDetails);
                }

                List<Tantosha> list = new ArrayList<>();
                for (Tantosha t: tantoshaList) {
                    Tantosha tantosha = Tantosha.builder()
                            .tantoshaId(t.getTantoshaId())
                            .tantoshaName(t.getTantoshaName())
                            .taishokuFlg(0)
                            .createdAt(currentTime)
                            .updatedAt(currentTime)
                            .deletedFlg(0)
                            .build();
                    list.add(tantosha);
                }

                createTantoshaList = tantoshaRepository.saveAllAndFlush(list);

            }

        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(
                    MessageCode.INTERNAL_ERROR ,
                    e.getMessage() ,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return createTantoshaList;
    }

    @SuppressWarnings("unchecked")
    public void handleEventTantosha(Event<?> event) {

        Map<Long, Object> messageMap = new LinkedHashMap<>();
        Long tantoshaId = (Long) event.getData();

        Optional<Tantosha> soukoMasterOptional = findById(tantoshaId);
        if (soukoMasterOptional.isEmpty()) {
            messageMap.put(tantoshaId, messageSource.getMessage(MessageCode.DATA_NOT_FOUND, null, LocaleContextHolder.getLocale()));
        }

        if (messageMap.isEmpty()) {
            event.getFuture().complete(null);
        } else {
            event.getFuture().complete(messageMap);
        }

    }

    @Override
    public Optional<Tantosha> findById(Long id) {
        return tantoshaRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {

    }
}
