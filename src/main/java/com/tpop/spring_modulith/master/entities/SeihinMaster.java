package com.tpop.spring_modulith.master.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "SEIHIN_MASTER")
public class SeihinMaster extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEIHIN_ID")
    private Long seihinId;

    @Column(name = "SEIHIN_CODE" ,nullable = false)
    private String seihinCode;

    @Column(name = "SEIHIN_NAME" , nullable = false)
    private String seihinName;

    @Column(name = "IRISU")
    private BigDecimal irisu;

    @Column(name = "TEKIYO" , columnDefinition = "TEXT")
    private String tekiyo;

    public SeihinMaster() {
        super();
    }

    @Builder

    public SeihinMaster(LocalDateTime createdAt, LocalDateTime updatedAt, int deletedFlg, Long seihinId, String seihinCode, String seihinName, BigDecimal irisu, String tekiyo) {
        super(createdAt, updatedAt, deletedFlg);
        this.seihinId = seihinId;
        this.seihinCode = seihinCode;
        this.seihinName = seihinName;
        this.irisu = irisu;
        this.tekiyo = tekiyo;
    }
}
