package com.tpop.spring_modulith.master.service;

import com.tpop.spring_modulith.component.ApiResponse;
import com.tpop.spring_modulith.constant.MessageCode;
import com.tpop.spring_modulith.constant.ResponseStatusConst;
import com.tpop.spring_modulith.event.Event;
import com.tpop.spring_modulith.exception.CommonException;
import com.tpop.spring_modulith.master.dto.SettingDataDtoImpl;
import com.tpop.spring_modulith.master.dto.SettingDataDtos;
import com.tpop.spring_modulith.master.entities.SettingGeneralData;
import com.tpop.spring_modulith.master.repository.SettingDataRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SettingDataService implements GenericService<SettingGeneralData>{

    private  final SettingDataRepository settingDataRepository;

    private final MessageSource messageSource;

    private final ModelMapper modelMapper;

    public Integer getScreenIdByScreenCode (String screenCode) {
        return settingDataRepository.getScreenIdByScreenCode(screenCode);
    }

    /**
     *
     * @param screenId
     * @return
     */
    public ApiResponse<Object> getSettingDataByScreenId(Integer screenId , Locale locale) {
        ApiResponse<Object> response = new ApiResponse<>();
        List<SettingDataDtoImpl> resultList = getSettingData(screenId);
        if (CollectionUtils.isEmpty(resultList)) {
            response.setMessage(messageSource.getMessage(MessageCode.NOT_EXISTS, null, locale));
        } else {
            response.setMessage(null);
        }
        response.setStatus(ResponseStatusConst.SUCCESS);
        response.setData(resultList);
        return response;
    }

    /**
     *
     * @param screenId
     * @return
     */
    public List<SettingDataDtoImpl> getSettingData(Integer screenId) {
        List<SettingDataDtoImpl> resultList = new ArrayList<>();
        if (!Objects.isNull(screenId)) {
            List<SettingDataDtos> settingDataDtoList = settingDataRepository.getListSettingData(screenId);
            List<String> tableNameList = settingDataRepository.listTableName(screenId);
            resultList = settingDataDtoList.stream()
                    .map( s -> SettingDataDtoImpl.builder()
                            .settingDataId(s.getSettingDataId())
                            .screenId(s.getScreenId())
                            .columnName(s.getColumnName())
                            .tableName(s.getTableName())
                            .columnDisplayName(s.getColumnDisplayName())
                            .columnOrder(s.getColumnOrder())
                            .columnWidth(s.getColumnWidth())
                            .status(s.getStatus())
                            .tableNameList(tableNameList)
                            .build()
                    )
                    .toList();
        }
        return resultList;
    }

    /**
     *
     * @param event
     */
    @SuppressWarnings("unchecked")
    public void handleEventSettingData(Event<?> event) {
        String screenCode  = String.valueOf(event.getData());
        Integer screenId  = getScreenIdByScreenCode(screenCode);
        if (!Objects.isNull(screenId)) {
            event.getObjectCompletableFuture().complete(screenId);
        } else {
            event.getObjectCompletableFuture().complete(null);
        }
    }

    @SuppressWarnings("unchecked")
    public void handleEventTypeSettingData(Event<?> event) {
        Integer screenId  = (Integer) event.getData();
        List<SettingDataDtoImpl> resultList = getSettingData(screenId);
        if (!Objects.isNull(resultList)) {
            event.getObjectCompletableFuture().complete(resultList);
        } else {
            event.getObjectCompletableFuture().complete(null);
        }
    }

    @Override
    public Page<SettingGeneralData> findAll(Pageable pageable) throws CommonException {
        return null;
    }

    /**
     *
     * @param settingGeneralDataList
     * @param locale
     * @return
     * @throws CommonException
     */
    @Override
    @Transactional(rollbackFor = {CommonException.class , Exception.class})
    public List<SettingGeneralData> save(List<SettingGeneralData> settingGeneralDataList, Locale locale) throws CommonException {
        List<SettingGeneralData> createSettingListGeneralData = new ArrayList<>();
        try {
            LocalDateTime currentTime = LocalDateTime.now();
            if (!CollectionUtils.isEmpty(settingGeneralDataList)) {
                Integer screenId  = settingGeneralDataList.stream().mapToInt(s  -> Math.toIntExact(s.getScreenId())).findFirst().orElse(-1);
                List<SettingGeneralData> list = settingGeneralDataList.stream()
                        .map(s -> SettingGeneralData.builder()
                                .settingId(s.getSettingId())
                                .screenId(s.getScreenId())
                                .columnName(s.getColumnName())
                                .tableName(s.getTableName())
                                .columnWidth(s.getColumnWidth())
                                .status(s.getStatus())
                                .createdAt(currentTime)
                                .updatedAt(currentTime)
                                .deletedFlg(0)
                                .build())
                        .toList();

                settingDataRepository.saveAllAndFlush(list);
                createSettingListGeneralData = modelMapper.map(getSettingDataByScreenId(screenId ,locale), new TypeToken<List<SettingGeneralData>>(){}.getType());
            }
        } catch (Exception e) {
            throw new CommonException(
                    MessageCode.INTERNAL_ERROR ,
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return createSettingListGeneralData;
    }

    /**
     *
     * @param settingGeneralDataList
     * @param locale
     * @return
     * @throws CommonException
     */
    @Transactional(rollbackFor = {Exception.class , CommonException.class})
    public List<SettingGeneralData> updateSettingData (List<SettingGeneralData> settingGeneralDataList, Locale locale) throws CommonException{
            List<SettingGeneralData> updateSettingListGeneralData = new ArrayList<>();
        try {
            LocalDateTime currentTime  = LocalDateTime.now();
            if (!CollectionUtils.isEmpty(settingGeneralDataList)) {
                List<Long> settingDataIdList = settingGeneralDataList.stream()
                        .map(SettingGeneralData:: getSettingId).toList();
                Integer screenId  = settingGeneralDataList.stream().mapToInt(s  -> Math.toIntExact(s.getScreenId())).findFirst().orElse(-1);
                List<SettingGeneralData> dataList = settingDataRepository.findSettingDataByIds(settingDataIdList);

                if (CollectionUtils.isEmpty(dataList)) {
                    throw new CommonException()
                            .setErrorCode(MessageCode.DATA_NOT_FOUND)
                            .setStatusCode(HttpStatus.NOT_FOUND)
                            .setMessage(messageSource.getMessage(
                                    MessageCode.DATA_NOT_FOUND,null , locale
                            ));
                }

                List<SettingGeneralData> list = settingGeneralDataList.stream()
                        .map(s -> SettingGeneralData.builder()
                                .settingId(s.getSettingId())
                                .screenId(s.getScreenId())
                                .columnName(s.getColumnName())
                                .tableName(s.getTableName())
                                .columnWidth(s.getColumnWidth())
                                .status(s.getStatus())
                                .createdAt(currentTime)
                                .updatedAt(currentTime)
                                .deletedFlg(0)
                                .build())
                        .toList();

                 settingDataRepository.saveAll(list);

                 updateSettingListGeneralData = modelMapper.map(getSettingDataByScreenId(screenId , locale), new TypeToken<List<SettingGeneralData>>(){}.getType());
            }
        } catch (Exception e) {
            throw new CommonException(
                    MessageCode.INTERNAL_ERROR,
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return updateSettingListGeneralData;
    }

    @Override
    public Optional<SettingGeneralData> findById(Long id) {
        return settingDataRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        settingDataRepository.deleteById(id);
    }

}
