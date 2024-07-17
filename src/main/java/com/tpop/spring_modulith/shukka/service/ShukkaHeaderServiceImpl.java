package com.tpop.spring_modulith.shukka.service;

import com.tpop.spring_modulith.component.ApiResponse;
import com.tpop.spring_modulith.constant.MessageCode;
import com.tpop.spring_modulith.constant.ResponseStatusConst;
import com.tpop.spring_modulith.constant.ScreenCodeConstant;
import com.tpop.spring_modulith.event.EventPublisher;
import com.tpop.spring_modulith.event.EventType;
import com.tpop.spring_modulith.exception.APIErrorDetail;
import com.tpop.spring_modulith.exception.CommonException;
import com.tpop.spring_modulith.master.dto.SettingDataDtoImpl;
import com.tpop.spring_modulith.shukka.dto.ShukkaDto;
import com.tpop.spring_modulith.shukka.dto.ShukkaSearchDto;
import com.tpop.spring_modulith.shukka.entities.ShukkaHeader;
import com.tpop.spring_modulith.shukka.entities.ShukkaMesai;
import com.tpop.spring_modulith.shukka.repository.ShukkaHeaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ShukkaHeaderServiceImpl implements GenericService{

    private final ShukkaHeaderRepository shukkaHeaderRepository;

    private final ShukkaMesaiServiceImpl shukkaMesaiService;

    private final EventPublisher eventPublisher;

    private final MessageSource messageSource;

    private Integer getScreenId() throws ExecutionException, InterruptedException {
        CompletableFuture<Object> future;
        Integer screenId = null;
        future = eventPublisher.publishCustomEvent(EventType.TYPE_SCREENID, null, ScreenCodeConstant.SHUKKA_ICHIRAN);
        if (!Objects.isNull(future.get())) {
                screenId = (Integer) future.get();
        }
        return screenId;
    }

    @Override
    public Page<ShukkaDto> getShukkaList(Pageable pageable , Locale locale) throws CommonException {
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<SettingDataDtoImpl> getSettingDataList(Integer screenId) throws ExecutionException, InterruptedException {
        CompletableFuture<Object> future ;
       future = eventPublisher.publishCustomEvent(EventType.TYPE_SETTINGDATA , null ,screenId);
        Object result = future.get();
        if (result instanceof List<?> resultList && !resultList.isEmpty() && resultList.get(0) instanceof SettingDataDtoImpl) {
                return (List<SettingDataDtoImpl>) resultList;
            }
        return Collections.emptyList();
    }

    /**
     *
     * @param param
     * @param locale
     * @return
     * @throws CommonException
     */
    public ApiResponse<Object> getAllShukkaList(ShukkaSearchDto param, Locale locale) throws CommonException {
        List<Map<String, Object>> resultList;
        ApiResponse<Object> responseData = new ApiResponse<>();
        try {
            Integer screenId = getScreenId();
            resultList = shukkaHeaderRepository.getAllShukkaList(
                    screenId,
                    param.getShukkaYoteiBiFrom(),
                    param.getShukkaYoteiBiTo(),
                    param.getShukkaJisseikiBiFrom(),
                    param.getShukkaJisseikiBiTo(),
                    param.getJyuchuBiFrom(),
                    param.getJyuchuBiTo(),
                    param.getNouhinsakiList(),
                    param.getSeikyuusaki(),
                    param.getTantoshaList(),
                    param.getShukkaSoukoList(),
                    param.getShukkaTanabanList(),
                    param.getSeihinCodeList(),
                    param.getSeihinMeiList(),
                    param.getKeywordList()
            );

            if (CollectionUtils.isEmpty(resultList)) {
                responseData.setMessage(messageSource.getMessage(MessageCode.NOT_EXISTS, null, locale));
            } else {
                responseData.setMessage(null);
            }
        } catch (Exception e) {
            throw new CommonException(
                    MessageCode.INTERNAL_ERROR,
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        responseData.setStatus(ResponseStatusConst.SUCCESS); // OK
        responseData.setData(resultList);
        return responseData;
    }



    @Override
    @Transactional(rollbackFor = {CommonException.class , Exception.class})
    public ShukkaDto save(ShukkaDto shukkaDto, Locale locale) throws CommonException, ExecutionException, InterruptedException {
        ShukkaDto createdShukka = new ShukkaDto();
        try {

            LocalDateTime currentTime = LocalDateTime.now();
            if (!Objects.isNull(shukkaDto)) {

                CompletableFuture<Map<Long, Object>> futureNouhinsaki =
                        eventPublisher.publishEvent(EventType.TYPE_NOUHINSAKI, null, shukkaDto.getShukkaHeader().getNouhinsakiId());

                CompletableFuture<Map<Long, Object>> futureTantosha =
                        eventPublisher.publishEvent(EventType.TYPE_TANTOSHA, null, shukkaDto.getShukkaHeader().getTantoshaId());

                CompletableFuture<Void> completableFuture = CompletableFuture.allOf(futureNouhinsaki, futureTantosha);

                completableFuture.join();
                List<APIErrorDetail> errorDetailList = new ArrayList<>();
                if (!CollectionUtils.isEmpty(futureNouhinsaki.get())) {
                    List<APIErrorDetail> finalErrorDetailList = new ArrayList<>();
                    futureNouhinsaki.get().forEach((key, value) -> {
                        APIErrorDetail errorDetail = new APIErrorDetail(key,
                                "nouhinsakiId",
                                MessageCode.DATA_NOT_FOUND,
                                messageSource.getMessage(
                                        MessageCode.DATA_NOT_FOUND,
                                        new String[]{"納品先"}, locale)
                        );
                        finalErrorDetailList.add(errorDetail);
                    });
                    errorDetailList.addAll(finalErrorDetailList);
                }


                //check TantoshaId
                if (!CollectionUtils.isEmpty(futureTantosha.get())) {
                    List<APIErrorDetail> errorDetailsTantosha = new ArrayList<>();
                    futureTantosha.get().forEach((key, value) -> {
                        APIErrorDetail errorDetail = new APIErrorDetail(key,
                                "tantoshaId",
                                MessageCode.DATA_NOT_FOUND,
                                messageSource.getMessage(
                                        MessageCode.DATA_NOT_FOUND,
                                        new String[]{"担当者"}, locale)
                        );
                        errorDetailsTantosha.add(errorDetail);
                    });
                    errorDetailList.addAll(errorDetailsTantosha);
                }

                if (!CollectionUtils.isEmpty(errorDetailList)) {
                    throw new CommonException()
                            .setErrorCode(MessageCode.DATA_NOT_FOUND)
                            .setStatusCode(HttpStatus.BAD_REQUEST)
                            .setErrorDetails(errorDetailList);
                }

                //check Shukka Mesai List
                errorDetailList = shukkaMesaiService.checkShukkaMesaiList(shukkaDto.getShukkaMesaiList(), locale);

                if (!CollectionUtils.isEmpty(errorDetailList)) {
                    throw new CommonException()
                            .setErrorCode(MessageCode.DATA_NOT_FOUND)
                            .setStatusCode(HttpStatus.BAD_REQUEST)
                            .setErrorDetails(errorDetailList);
                }

                // Shukka Header save
                ShukkaHeader shukkaHeader = ShukkaHeader.builder()
                        .shukkaHeaderId(shukkaDto.getShukkaHeader().getShukkaHeaderId())
                        .shukkaNo(shukkaDto.getShukkaHeader().getShukkaNo())
                        .shukkaYoteibi(shukkaDto.getShukkaHeader().getShukkaYoteibi())
                        .shukkaJisseikiBi(null)
                        .shukkaUmiFlg(0)
                        .jyuchubi(shukkaDto.getShukkaHeader().getJyuchubi())
                        .nouhinsakiId(shukkaDto.getShukkaHeader().getNouhinsakiId())
                        .tantoshaId(shukkaDto.getShukkaHeader().getTantoshaId())
                        .tekiyoHeader(shukkaDto.getShukkaHeader().getTekiyoHeader())
                        .createdAt(currentTime)
                        .updatedAt(currentTime)
                        .deletedFlg(0)
                        .build();
                shukkaHeaderRepository.saveAndFlush(shukkaHeader);

                // Shukka Mesai List
                List<ShukkaMesai> shukkaMesaiList = shukkaDto.getShukkaMesaiList().stream()
                        .map(sm -> ShukkaMesai.builder()
                                .shukkaHeaderId(shukkaHeader.getShukkaHeaderId())
                                .shukkaMesaiId(sm.getShukkaMesaiId())
                                .shukkaMesaiNo(sm.getShukkaMesaiNo())
                                .shukkaYoteiSuryo(sm.getShukkaYoteiSuryo())
                                .shukkaJisseikiSuryo(sm.getShukkaJisseikiSuryo())
                                .seihinId(sm.getSeihinId())
                                .soukoId(sm.getSoukoId())
                                .tanabanId(sm.getTanabanId())
                                .tekiyoMesai(sm.getTekiyoMesai())
                                .createdAt(currentTime)
                                .updatedAt(currentTime)
                                .deletedFlg(0)
                                .build())
                        .toList();
                List<ShukkaMesai> createdShukkaMesaiList = shukkaMesaiService.createShukkaMesai(shukkaMesaiList);

                createdShukka = ShukkaDto.builder()
                        .shukkaHeader(shukkaHeader)
                        .shukkaMesaiList(createdShukkaMesaiList)
                        .build();
            }

        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(
                    MessageCode.INTERNAL_ERROR,
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        return createdShukka;
    }


        @Override
    public Optional<ShukkaDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }
}
