package com.tpop.spring_modulith.shukka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShukkaSearchDto {
    private String shukkaYoteiBiFrom;
    private String shukkaYoteiBiTo;
    private String shukkaJisseikiBiFrom;
    private String shukkaJisseikiBiTo;
    private String jyuchuBiFrom;
    private String jyuchuBiTo;
    private String nouhinsakiList;
    private String seikyuusaki;
    private String tantoshaList;
    private String shukkaSoukoList;
    private String shukkaTanabanList;
    private String seihinCodeList;
    private String seihinMeiList;
    private String keywordList;
}
