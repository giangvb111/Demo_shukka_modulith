package com.tpop.spring_modulith.shukka.service;

import com.tpop.spring_modulith.constant.MessageCode;
import com.tpop.spring_modulith.event.EventPublisher;
import com.tpop.spring_modulith.event.EventType;
import com.tpop.spring_modulith.exception.APIErrorDetail;
import com.tpop.spring_modulith.exception.CommonException;
import com.tpop.spring_modulith.shukka.entities.ShukkaMesai;
import com.tpop.spring_modulith.shukka.repository.ShukkaMesaiRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ShukkaMesaiServiceImpl{

    private final ShukkaMesaiRepository shukkaMesaiRepository;
    private final EventPublisher eventPublisher;
    private final MessageSource messageSource;

    public List<ShukkaMesai> createShukkaMesai(List<ShukkaMesai> shukkaMesaiList) throws CommonException{
        return shukkaMesaiRepository.saveAll(shukkaMesaiList);
    }

    /**
     *
     * @param shukkaMesaiList
     * @param locale
     * @return エラーリスト取得
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<APIErrorDetail> checkShukkaMesaiList(@NonNull List<ShukkaMesai> shukkaMesaiList, Locale locale) throws ExecutionException, InterruptedException {
        List<APIErrorDetail> errorDetails = new ArrayList<>();
        CompletableFuture<Map<Long, Object>> future;

        if (!CollectionUtils.isEmpty(shukkaMesaiList)) {
            // Get List SeihinId
            List<Long> seihinIdList  = shukkaMesaiList.stream().
                    map(ShukkaMesai::getSeihinId)
                    .toList();
            future = eventPublisher.publishEvent(EventType.TYPE_SEIHIN , null ,seihinIdList);

            if (!CollectionUtils.isEmpty(future.get())) {
                future.get().forEach((key, value) -> {
                    APIErrorDetail errorDetail = new APIErrorDetail(key,
                            "seihinId",
                            MessageCode.DATA_NOT_FOUND,
                            messageSource.getMessage(
                                    MessageCode.DATA_NOT_FOUND,
                                    new String[]{"製品"}, locale)
                    );
                    errorDetails.add(errorDetail);
                });
            }

            // Get List SoukoId
            List<Long> soukoIdList  = shukkaMesaiList.stream()
                    .map(ShukkaMesai::getSoukoId)
                    .toList();
            future = eventPublisher.publishEvent(EventType.TYPE_SOUKO , null ,soukoIdList);

            if (!CollectionUtils.isEmpty(future.get())) {
                future.get().forEach((key, value) -> {
                    APIErrorDetail errorDetail = new APIErrorDetail(key,
                            "soukoId",
                            MessageCode.DATA_NOT_FOUND,
                            messageSource.getMessage(
                                    MessageCode.DATA_NOT_FOUND,
                                    new String[]{"倉庫"}, locale)
                    );
                    errorDetails.add(errorDetail);
                });
            }
        }

        return errorDetails;
    }
}
