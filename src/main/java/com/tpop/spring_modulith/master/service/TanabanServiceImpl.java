package com.tpop.spring_modulith.master.service;

import com.tpop.spring_modulith.constant.MessageCode;
import com.tpop.spring_modulith.exception.APIErrorDetail;
import com.tpop.spring_modulith.exception.CommonException;
import com.tpop.spring_modulith.master.entities.SeihinMaster;
import com.tpop.spring_modulith.master.entities.SoukoMaster;
import com.tpop.spring_modulith.master.entities.TanabanMaster;
import com.tpop.spring_modulith.master.repository.TanabanRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
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
public class TanabanServiceImpl implements GenericService<TanabanMaster>{

    private final TanabanRepository tanabanRepository;
    private final MessageSource messageSource;

    @Override
    public Page<TanabanMaster> findAll(Pageable pageable) throws CommonException {
        return null;
    }

    /**
     * @param tanabanMasterList
     * @param locale
     * @return 登録した棚番リスト
     * @throws CommonException
     */
    @Override
    public List<TanabanMaster> save(List<TanabanMaster> tanabanMasterList, Locale locale) throws CommonException {
        List<TanabanMaster> createTanabanList = new ArrayList<>();
        try {

            LocalDateTime currentTime = LocalDateTime.now();
            if (!CollectionUtils.isEmpty(tanabanMasterList)) {
                List<APIErrorDetail> errorDetails = new ArrayList<>();
                AtomicInteger i = new AtomicInteger();
                tanabanMasterList.forEach(s -> {
                    if (Objects.isNull(s.getSoukoId())) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.longValue(),
                                "soukoId",
                                MessageCode.CHECK_EXISTS,
                                messageSource.getMessage(
                                        MessageCode.CHECK_EXISTS, null, locale
                                )
                        );
                        errorDetails.add(apiErrorDetail);
                    }
                    if (s.getTanabanName().isEmpty()) {
                        APIErrorDetail apiErrorDetail = new APIErrorDetail(
                                i.longValue(),
                                "tanabanName",
                                MessageCode.CHECK_EXISTS,
                                messageSource.getMessage(
                                        MessageCode.CHECK_EXISTS, null, locale
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

                List<TanabanMaster> list = new ArrayList<>();
                for (TanabanMaster t : tanabanMasterList) {
                    TanabanMaster tanabanMaster = TanabanMaster.builder()
                            .tanabanId(t.getTanabanId())
                            .tanabanName(t.getTanabanName())
                            .soukoId(t.getSoukoId())
                            .createdAt(currentTime)
                            .updatedAt(currentTime)
                            .deletedFlg(0)
                            .build();
                    list.add(tanabanMaster);
                }

                createTanabanList = tanabanRepository.saveAllAndFlush(list);
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
        return createTanabanList;
    }

    @Override
    public Optional<TanabanMaster> findById(Long id) {
        return Optional.empty();
    }

    /**
     *
     *
     * @param soukoId
     * @return 倉庫IDによる棚番リストを取得
     */
    public List<TanabanMaster> getTanabanListBySoukoId(@NonNull Long soukoId) {
        return tanabanRepository.getTanabanListBySoukoId(soukoId);
    }

    @Override
    public void deleteById(Long id) {

    }
}
