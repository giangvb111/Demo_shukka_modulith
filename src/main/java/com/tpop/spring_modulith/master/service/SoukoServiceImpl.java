package com.tpop.spring_modulith.master.service;

import com.tpop.spring_modulith.constant.MessageCode;
import com.tpop.spring_modulith.event.Event;
import com.tpop.spring_modulith.exception.APIErrorDetail;
import com.tpop.spring_modulith.exception.CommonException;
import com.tpop.spring_modulith.master.entities.NouhinsakiMaster;
import com.tpop.spring_modulith.master.entities.SoukoMaster;
import com.tpop.spring_modulith.master.repository.SoukoRepository;
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
public class SoukoServiceImpl implements GenericService<SoukoMaster>{

    private final SoukoRepository soukoRepository;
    private final MessageSource messageSource;
    @Override
    public Page<SoukoMaster> findAll(Pageable pageable) throws CommonException {
        return null;
    }

    public List<SoukoMaster> getSoukoList() {
        return soukoRepository.findAll();
    }

    /**
     *
     * @param soukoMasterList
     * @param locale
     * @return 登録した倉庫リスト
     * @throws CommonException
     */
    @Override
    public List<SoukoMaster> save(List<SoukoMaster> soukoMasterList, Locale locale) throws CommonException {
        List<SoukoMaster> createSoukoList = new ArrayList<>();
        try {

            LocalDateTime currentTime  = LocalDateTime.now();
            if (!CollectionUtils.isEmpty(soukoMasterList)) {
                List<APIErrorDetail> errorDetails = new ArrayList<>();
                AtomicInteger i = new AtomicInteger();
                soukoMasterList.forEach(s ->{
                    if (s.getSoukoName().isEmpty()) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.longValue(),
                                "soukoName" ,
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

                List<SoukoMaster> list = new ArrayList<>();
                for (SoukoMaster s: soukoMasterList) {
                    SoukoMaster soukoMaster = SoukoMaster.builder()
                            .soukoId(s.getSoukoId())
                            .soukoName(s.getSoukoName())
                            .createdAt(currentTime)
                            .updatedAt(currentTime)
                            .deletedFlg(0)
                            .build();
                    list.add(soukoMaster);
                }

                createSoukoList = soukoRepository.saveAllAndFlush(list);

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
        return createSoukoList;
    }

    @Override
    public Optional<SoukoMaster> findById(Long id) {
        return soukoRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {

    }

    /**
     *
     * ハンドイベント倉庫
     * @param event
     */

    @SuppressWarnings("unchecked")
    public void handEventSouko(Event<?> event) {

        Map<Long ,Object> messageMap = new LinkedHashMap<>();
        List<Long> soukoIdList = (List<Long>) event.getData();
        for (Long aLong: soukoIdList) {
            Optional<SoukoMaster> soukoMasterOptional = findById(aLong);
            if (soukoMasterOptional.isEmpty()) {
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
