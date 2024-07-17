package com.tpop.spring_modulith.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeihinDto {

    @JsonProperty(value = "SEIHIN_ID")
    private Long seihinId;

    @JsonProperty(value = "SEIHIN_CODE")
    private String seihinCode;

    @JsonProperty(value = "SEIHIN_NAME")
    private String seihinName;

    @JsonProperty(value = "IRISU")
    private BigDecimal irisu;

    @JsonProperty(value = "TEKIYO")
    private String tekiyo;

}
