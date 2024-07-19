package com.tpop.spring_modulith.master.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SettingDataDtoImpl implements SettingDataDtos {

    private Long settingDataId ;

    private Long screenId;

    private String columnName;

    private String columnDisplayName;

    private Integer columnOrder;

    private String tableName;

    private Integer columnWidth;

    private Integer status;

    private List<String> tableNameList;
}
