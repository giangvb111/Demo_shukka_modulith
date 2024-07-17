package com.tpop.spring_modulith.master.repository;

import com.tpop.spring_modulith.master.entities.SoukoMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoukoRepository extends JpaRepository<SoukoMaster , Long> {
}
