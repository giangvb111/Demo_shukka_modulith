package com.tpop.spring_modulith.shukka.repository;


import com.tpop.spring_modulith.shukka.entities.ShukkaHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ShukkaHeaderRepository extends JpaRepository<ShukkaHeader ,Long> {

//    @Procedure(procedureName = "EXEC_CALC_出荷一覧")
    @Query(nativeQuery = true , value = "EXEC EXEC_CALC_出荷一覧 " +
            "@screen_id =:screenId ," +
            "@出荷予定日_FROM =:shukkaYoteiBiFrom ," +
            "@出荷予定日_TO =:shukkaYoteiBiTo ," +
            "@出荷実績日_FROM =:shukkaJisseikiBiFrom ," +
            "@出荷実績日_TO =:shukkaJisseikiBiTo ," +
            "@受注日_FROM =:jyuchuBiFrom ," +
            "@受注日_TO =:jyuchuBiTo ," +
            "@納品先_LIST =:nouhinsakiList ," +
            "@請求先 =:seikyuusaki ," +
            "@担当者_LIST =:tantoshaList ," +
            "@出荷倉庫_LIST =:shukkaSoukoList ," +
            "@出荷棚番_LIST =:shukkaTanabanList ," +
            "@製品コード_LIST =:seihinCodeList ," +
            "@製品名_LIST =:seihinMeiList ," +
            "@キーワード_LIST =:keywordList "
    )
    List<Map<String, Object>> getAllShukkaList(@Param("screenId") Integer screenId ,
                                               @Param("shukkaYoteiBiFrom") String shukkaYoteiBiFrom ,
                                               @Param("shukkaYoteiBiTo") String shukkaYoteiBiTo ,
                                               @Param("shukkaJisseikiBiFrom") String shukkaJisseikiBiFrom ,
                                               @Param("shukkaJisseikiBiTo") String shukkaJisseikiBiTo ,
                                               @Param("jyuchuBiFrom") String jyuchuBiFrom ,
                                               @Param("jyuchuBiTo") String jyuchuBiTo ,
                                               @Param("nouhinsakiList") String nouhinsakiList ,
                                               @Param("seikyuusaki") String seikyuusaki ,
                                               @Param("tantoshaList") String tantoshaList ,
                                               @Param("shukkaSoukoList") String shukkaSoukoList ,
                                               @Param("shukkaTanabanList") String shukkaTanabanList ,
                                               @Param("seihinCodeList") String seihinCodeList ,
                                               @Param("seihinMeiList") String seihinMeiList ,
                                               @Param("keywordList") String keywordList
                                               );
}
