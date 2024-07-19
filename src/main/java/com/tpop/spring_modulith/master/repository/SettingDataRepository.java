package com.tpop.spring_modulith.master.repository;

import com.tpop.spring_modulith.master.dto.SettingDataDtos;
import com.tpop.spring_modulith.master.entities.SettingGeneralData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SettingDataRepository extends JpaRepository<SettingGeneralData, Long> {

    @Query(nativeQuery = true , value = "SELECT g.* " +
            " FROM SETTING_GENERAL_DATA g where g.SCREEN_ID = :screenId AND g.STATUS = 1 ORDER BY g.COLUMN_ORDER")
    List<SettingDataDtos> getListSettingData(Integer screenId);

    @Query(nativeQuery = true , value = "SELECT COLUMN_NAME \n" +
            "FROM INFORMATION_SCHEMA.COLUMNS \n" +
            "WHERE TABLE_NAME = :tableName")
    List<String> listColumnName(@Param("tableName") String tableName);

    @Query(nativeQuery = true , value = "SELECT sd.* FROM setting_data sd WHERE sd.id IN :settingIdList")
    List<SettingGeneralData> findSettingDataByIds(List<Long> settingIdList);

    @Query(nativeQuery = true , value = "select gts.table_name from general_table_setting gts where gts.screen_id  = :screenId")
    List<String> listTableName (Integer screenId);

    @Query(nativeQuery = true , value = "select gms.reference_screen_id ,gms.reference_screen_name from general_system_master gms where gms.screen_id  = :screenId")
    List<Map<String , Integer>> screenOptionList (Integer screenId);

    @Query(nativeQuery = true , value = "select m.screen_id from menu m where m.url = :url ")
    Integer getScreenIdByUrl(@Param("url") String url);

    @Query(nativeQuery = true , value = "SELECT SCREEN_ID FROM MENU WHERE SCREEN_CODE =:screenCode")
    Integer getScreenIdByScreenCode(@Param("screenCode") String screenCode);
}
