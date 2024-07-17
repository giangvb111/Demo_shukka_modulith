package com.tpop.spring_modulith.master.repository;

import com.tpop.spring_modulith.master.entities.SeihinMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeihinRepository extends JpaRepository<SeihinMaster , Long> {
}
