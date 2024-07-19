package com.tpop.spring_modulith.shukka.entities;

import com.tpop.spring_modulith.master.entities.BaseEntity;
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
@Table(name = "SHUKKA_MESAI")
public class ShukkaMesai extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHUKKA_MESAI_ID")
    private Long shukkaMesaiId;

    @Column(name = "SHUKKA_MESAI_NO")
    private String shukkaMesaiNo;

    @Column(name = "SHUKKA_YOTEI_SURYO")
    private BigDecimal shukkaYoteiSuryo;

    @Column(name = "SHUKKA_JISSEIKI_SURYO")
    private BigDecimal shukkaJisseikiSuryo;

    @Column(name = "SHUKKA_HEADER_ID")
    private Long shukkaHeaderId;

    @Column(name = "SEIHIN_ID")
    private Long seihinId;

    @Column(name = "SOUKO_ID")
    private Long soukoId;

    @Column(name = "TANABAN_ID")
    private Long tanabanId;

    @Column(name = "LOT_NO")
    private String lotNo;

    @Column(name = "TANKA")
    private Integer tanka;

    @Column(name = "KINGAKU")
    private Integer kingaku;

    @Column(name = "TEKIYO_MESAI")
    private String tekiyoMesai;

    public ShukkaMesai() {
        super();
    }

    @Builder

    public ShukkaMesai(LocalDateTime createdAt, LocalDateTime updatedAt, int deletedFlg,
                       Long shukkaMesaiId, String shukkaMesaiNo, BigDecimal shukkaYoteiSuryo, BigDecimal shukkaJisseikiSuryo,
                       Long shukkaHeaderId, Long seihinId, Long soukoId, Long tanabanId, String lotNo, Integer tanka, Integer kingaku, String tekiyoMesai) {
        super(createdAt, updatedAt, deletedFlg);
        this.shukkaMesaiId = shukkaMesaiId;
        this.shukkaMesaiNo = shukkaMesaiNo;
        this.shukkaYoteiSuryo = shukkaYoteiSuryo;
        this.shukkaJisseikiSuryo = shukkaJisseikiSuryo;
        this.shukkaHeaderId = shukkaHeaderId;
        this.seihinId = seihinId;
        this.soukoId = soukoId;
        this.tanabanId = tanabanId;
        this.lotNo = lotNo;
        this.tanka = tanka;
        this.kingaku = kingaku;
        this.tekiyoMesai = tekiyoMesai;
    }
}
