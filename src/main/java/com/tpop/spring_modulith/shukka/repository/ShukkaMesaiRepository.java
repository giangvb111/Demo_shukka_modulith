package com.tpop.spring_modulith.shukka.repository;

import com.tpop.spring_modulith.shukka.entities.ShukkaMesai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShukkaMesaiRepository extends JpaRepository<ShukkaMesai , Long> {


}
