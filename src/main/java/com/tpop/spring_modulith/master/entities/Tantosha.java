package com.tpop.spring_modulith.master.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "TANTOSHA")
public class Tantosha extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TANTOSHA_ID")
    private Long tantoshaId;

    @Column(name = "TANTOSHA_NAME")
    private String tantoshaName;

    @Column(name = "TAISHOKU_FLG")
    private Integer taishokuFlg;

    public Tantosha() {
        super();
    }

    @Builder
    public Tantosha(LocalDateTime createdAt, LocalDateTime updatedAt, int deletedFlg, Long tantoshaId, String tantoshaName, Integer taishokuFlg) {
        super(createdAt, updatedAt, deletedFlg);
        this.tantoshaId = tantoshaId;
        this.tantoshaName = tantoshaName;
        this.taishokuFlg = taishokuFlg;
    }
}

