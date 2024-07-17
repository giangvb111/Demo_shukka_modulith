package com.tpop.spring_modulith.master.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SettingDataDtoImpl implements SettingDataDtos {

//    @JsonProperty(value = "id")
    private Long settingDataId ;

//    @JsonProperty(value = "screen_id")
    private Long screenId;

//    @JsonProperty(value = "column_name")
    private String columnName;

    private String columnDisplayName;

    private Integer columnOrder;

//    @JsonProperty(value = "table_name")
    private String tableName;

//    @JsonProperty(value = "column_width")
    private Integer columnWidth;

//    @JsonProperty(value = "status")
    private Integer status;

    private List<String> tableNameList;
}
