package com.tpop.spring_modulith.shukka.dto;

import com.tpop.spring_modulith.shukka.entities.ShukkaHeader;
import com.tpop.spring_modulith.shukka.entities.ShukkaMesai;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShukkaDto {

    private ShukkaHeader shukkaHeader;

    List<ShukkaMesai> shukkaMesaiList;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer deleteFlg ;

}
