package com.tpop.spring_modulith.master.repository;

import com.tpop.spring_modulith.master.entities.TanabanMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TanabanRepository extends JpaRepository<TanabanMaster , Long> {
    @Query(nativeQuery = true ,value = "SELECT TM.* FROM TANABAN_MASTER TM WHERE TM.SOUKO_ID = :soukoId")
    List<TanabanMaster> getTanabanListBySoukoId(@Param("soukoId") Long soukoId);
}
