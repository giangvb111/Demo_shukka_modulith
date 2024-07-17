package com.tpop.spring_modulith.master.service;

import com.tpop.spring_modulith.component.ApiResponse;
import com.tpop.spring_modulith.constant.MessageCode;
import com.tpop.spring_modulith.constant.ResponseStatusConst;
import com.tpop.spring_modulith.event.Event;
import com.tpop.spring_modulith.exception.APIErrorDetail;
import com.tpop.spring_modulith.exception.CommonException;
import com.tpop.spring_modulith.master.entities.NouhinsakiMaster;
import com.tpop.spring_modulith.master.repository.NouhisakiRepository;
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
public class NouhinsakiServiceImpl implements GenericService<NouhinsakiMaster>{

    private final NouhisakiRepository nouhisakiRepository;

    private final MessageSource messageSource;
    @Override
    public Page<NouhinsakiMaster> findAll(Pageable pageable) throws CommonException {
        return null;
    }

    public ApiResponse<Object> getNouhisakiList(Locale locale) throws CommonException {
        ApiResponse<Object> response = new ApiResponse<>();
        List<NouhinsakiMaster> resultList;
        try {
            resultList = nouhisakiRepository.findAll();
            if (CollectionUtils.isEmpty(resultList)) {
                response.setStatus(ResponseStatusConst.SUCCESS);
                response.setMessage(messageSource.getMessage(MessageCode.NOT_EXISTS, null, locale));
                response.setData(resultList);
            } else {
                response.setStatus(ResponseStatusConst.SUCCESS);
                response.setMessage(null);
                response.setData(resultList);
            }
        } catch (Exception e) {
            throw new CommonException(
                    MessageCode.INTERNAL_ERROR,
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return response;
    }

    @Override
    public List<NouhinsakiMaster> save(List<NouhinsakiMaster> nouhinsakiMasterList, Locale locale) throws CommonException {

        List<NouhinsakiMaster> createNouhinsakiList = new ArrayList<>();
        try {

            LocalDateTime currentTime  = LocalDateTime.now();
            if (!CollectionUtils.isEmpty(nouhinsakiMasterList)) {
                List<APIErrorDetail> errorDetails = new ArrayList<>();
                AtomicInteger i = new AtomicInteger();
                nouhinsakiMasterList.forEach(s ->{
                    if (s.getNouhinsakiCode().isEmpty()) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.longValue(),
                                "nouhinsakiCode" ,
                                MessageCode.CHECK_EXISTS ,
                                messageSource.getMessage(
                                        MessageCode.CHECK_EXISTS , null , locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }
                    if (s.getNouhinsakiName().isEmpty()) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.longValue(),
                                "nouhinsakiName" ,
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

                List<NouhinsakiMaster> list = new ArrayList<>();
                for (NouhinsakiMaster n: nouhinsakiMasterList) {
                    NouhinsakiMaster nouhinsakiMaster = NouhinsakiMaster.builder()
                            .nouhinsakiId(n.getNouhinsakiId())
                            .nouhinsakiCode(n.getNouhinsakiCode())
                            .nouhinsakiName(n.getNouhinsakiName())
                            .createdAt(currentTime)
                            .updatedAt(currentTime)
                            .deletedFlg(0)
                            .build();
                    list.add(nouhinsakiMaster);
                }

                createNouhinsakiList = nouhisakiRepository.saveAllAndFlush(list);

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
        return createNouhinsakiList;
    }

    @Override
    public Optional<NouhinsakiMaster> findById(Long id) {
        return nouhisakiRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
    }

    @SuppressWarnings("unchecked")
    public void handEventNouhinsaki(Event<?> event) {

        Map<Long , Object> messageMap  = new LinkedHashMap<>();
        Long nouhinsakiId  = (Long) event.getData();

            Optional<NouhinsakiMaster> nouhinsakiMasterOptional = findById(nouhinsakiId);
            if (nouhinsakiMasterOptional.isEmpty()) {
                messageMap.put(nouhinsakiId , messageSource.getMessage(MessageCode.DATA_NOT_FOUND , null , LocaleContextHolder.getLocale()));
            }

        if (messageMap.isEmpty()) {
            event.getFuture().complete(null);
        } else {
            event.getFuture().complete(messageMap);
        }
    }
}
