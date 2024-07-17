package com.tpop.spring_modulith.master.service;

import com.tpop.spring_modulith.constant.MessageCode;
import com.tpop.spring_modulith.event.Event;
import com.tpop.spring_modulith.exception.APIErrorDetail;
import com.tpop.spring_modulith.exception.CommonException;
import com.tpop.spring_modulith.master.entities.NouhinsakiMaster;
import com.tpop.spring_modulith.master.entities.SeihinMaster;
import com.tpop.spring_modulith.master.repository.SeihinRepository;
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
public class SeihinServiceImpl implements GenericService<SeihinMaster>{

    private final SeihinRepository seihinRepository;
    private final MessageSource messageSource;
    @Override
    public Page<SeihinMaster> findAll(Pageable pageable) throws CommonException {
        return seihinRepository.findAll(pageable);
    }

    public List<SeihinMaster> getSeihinList() {
        return seihinRepository.findAll();
    }

    @Override
    public List<SeihinMaster> save(List<SeihinMaster> seihinMasterList, Locale locale) throws CommonException {
        List<SeihinMaster> createSeihinList = new ArrayList<>();
        try {

            LocalDateTime currentTime  = LocalDateTime.now();
            if (!CollectionUtils.isEmpty(seihinMasterList)) {
                List<APIErrorDetail> errorDetails = new ArrayList<>();
                AtomicInteger i = new AtomicInteger();
                seihinMasterList.forEach(s ->{
                    if (s.getSeihinCode().isEmpty()) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.longValue(),
                                "seihinCode" ,
                                MessageCode.CHECK_EXISTS ,
                                messageSource.getMessage(
                                        MessageCode.CHECK_EXISTS , null , locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }
                    if (s.getSeihinName().isEmpty()) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.longValue(),
                                "seihinName" ,
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

                List<SeihinMaster> list = new ArrayList<>();
                for (SeihinMaster s: seihinMasterList) {
                    SeihinMaster nouhinsakiMaster = SeihinMaster.builder()
                            .seihinId(s.getSeihinId())
                            .seihinCode(s.getSeihinCode())
                            .seihinName(s.getSeihinName())
                            .irisu(s.getIrisu())
                            .tekiyo(s.getTekiyo())
                            .createdAt(currentTime)
                            .updatedAt(currentTime)
                            .deletedFlg(0)
                            .build();
                    list.add(nouhinsakiMaster);
                }

                createSeihinList = seihinRepository.saveAllAndFlush(list);

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
        return createSeihinList;
    }

    @Override
    public Optional<SeihinMaster> findById(Long id) {
        return seihinRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {

    }

    @SuppressWarnings("unchecked")
    public void handEventSeihin(Event<?> event) {

        Map<Long , Object> messageMap = new LinkedHashMap<>();
        List<Long> seihinLongList = (List<Long>) event.getData();
        for (Long aLong: seihinLongList) {
            Optional<SeihinMaster> seihinMasterOptional = findById(aLong);
            if (seihinMasterOptional.isEmpty()) {
                messageMap.put(aLong , messageSource.getMessage(MessageCode.CHECK_EXISTS , null , LocaleContextHolder.getLocale()));
            }
        }
        if (messageMap.isEmpty()) {
            event.getFuture().complete(null);
        } else {
            event.getFuture().complete(messageMap);
        }
    }

}
