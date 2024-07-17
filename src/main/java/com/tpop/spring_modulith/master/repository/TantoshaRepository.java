package com.tpop.spring_modulith.master.repository;

import com.tpop.spring_modulith.master.entities.Tantosha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TantoshaRepository extends JpaRepository<Tantosha ,Long> {

    List<Tantosha> findTantoshaByTaishokuFlg(Integer taishokuFlg);
}
