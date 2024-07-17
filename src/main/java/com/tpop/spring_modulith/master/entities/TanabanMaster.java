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
@Table(name = "TANABAN_MASTER")
public class TanabanMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TANABAN_ID")
    private Long tanabanId;

    @Column(name = "TANABAN_NAME" , nullable = false)
    private String tanabanName;

    @Column(name = "SOUKO_ID" , nullable = false)
    private Long soukoId;

    public TanabanMaster() {
    }

    @Builder
    public TanabanMaster(LocalDateTime createdAt, LocalDateTime updatedAt, int deletedFlg, Long tanabanId, String tanabanName, Long soukoId) {
        super(createdAt, updatedAt, deletedFlg);
        this.tanabanId = tanabanId;
        this.tanabanName = tanabanName;
        this.soukoId = soukoId;
    }
}
