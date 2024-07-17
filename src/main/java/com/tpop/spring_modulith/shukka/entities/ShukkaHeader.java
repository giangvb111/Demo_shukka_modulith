package com.tpop.spring_modulith.shukka.entities;

import com.tpop.spring_modulith.master.entities.BaseEntity;
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
@Table(name = "SHUKKA_HEADER")
public class ShukkaHeader extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHUKKA_HEADER_ID")
    private Long shukkaHeaderId;

    @Column(name = "SHUKKA_NO")
    private String shukkaNo;

    @Column(name = "JYUCHUU_BI")
    private String jyuchubi;

    @Column(name = "SHUKKA_YOTEIBI")
    private String shukkaYoteibi;

    @Column(name = "SHUKKA_JISSEIKIBI")
    private String shukkaJisseikiBi;

    @Column(name = "NOUHINSAKI_ID")
    private Long nouhinsakiId;

    @Column(name = "TEKIYO_HEADER")
    private String tekiyoHeader;

    @Column(name = "TANTOSHA_ID")
    private Long tantoshaId;

    @Column(name = "SHUKKA_UMI_FLG")
    private Integer shukkaUmiFlg;

    public ShukkaHeader() {
        super();
    }

    @Builder
    public ShukkaHeader(LocalDateTime createdAt, LocalDateTime updatedAt, int deletedFlg,
                        Long shukkaHeaderId, String shukkaNo, String jyuchubi, String shukkaYoteibi,String shukkaJisseikiBi ,
                        Long nouhinsakiId, String tekiyoHeader, Long tantoshaId, Integer shukkaUmiFlg) {
        super(createdAt, updatedAt, deletedFlg);
        this.shukkaHeaderId = shukkaHeaderId;
        this.shukkaNo = shukkaNo;
        this.jyuchubi = jyuchubi;
        this.shukkaYoteibi = shukkaYoteibi;
        this.shukkaJisseikiBi = shukkaJisseikiBi;
        this.nouhinsakiId = nouhinsakiId;
        this.tekiyoHeader = tekiyoHeader;
        this.tantoshaId = tantoshaId;
        this.shukkaUmiFlg = shukkaUmiFlg;
    }
}
