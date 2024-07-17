package com.tpop.spring_modulith.master.repository;

import com.tpop.spring_modulith.master.entities.NouhinsakiMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NouhisakiRepository extends JpaRepository<NouhinsakiMaster ,Long> {
}
