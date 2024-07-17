package com.tpop.spring_modulith.master.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Setter
@Getter
@Table(name = "NOUHINSAKI_MASTER")
public class NouhinsakiMaster extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOUHINSAKI_ID")
    private Long nouhinsakiId;

    @Column(name = "NOUHINSAKI_CODE")
    private String nouhinsakiCode;

    @Column(name = "NOUHINSAKI_NAME")
    private String nouhinsakiName;

    public NouhinsakiMaster() {
        super();
    }

    @Builder
    public NouhinsakiMaster(LocalDateTime createdAt, LocalDateTime updatedAt, int deletedFlg, Long nouhinsakiId, String nouhinsakiCode, String nouhinsakiName) {
        super(createdAt, updatedAt, deletedFlg);
        this.nouhinsakiId = nouhinsakiId;
        this.nouhinsakiCode = nouhinsakiCode;
        this.nouhinsakiName = nouhinsakiName;
    }
}
