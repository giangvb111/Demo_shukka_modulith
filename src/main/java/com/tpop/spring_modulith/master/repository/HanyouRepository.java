package com.tpop.spring_modulith.master.repository;

import com.tpop.spring_modulith.master.entities.HanyouSystemMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HanyouRepository extends JpaRepository<HanyouSystemMaster , Long> {

    HanyouSystemMaster getHanyouSystemMasterByKey1AndKey2AndKey3(String key1 , String key2 , String key3);
}
