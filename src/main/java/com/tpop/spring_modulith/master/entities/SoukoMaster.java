package com.tpop.spring_modulith.master.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Table(name = "SOUKO_MASTER")
public class SoukoMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SOUKO_ID")
    private Long soukoId;

    @Column(name = "SOUKO_NAME" , nullable = false)
    private String soukoName;

    public SoukoMaster() {
        super();
    }

    @Builder
    public SoukoMaster(LocalDateTime createdAt, LocalDateTime updatedAt, int deletedFlg,Long soukoId, String soukoName) {
        super(createdAt, updatedAt, deletedFlg);
        this.soukoId = soukoId;
        this.soukoName = soukoName;
    }
}
