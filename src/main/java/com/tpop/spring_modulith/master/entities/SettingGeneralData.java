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
@Table(name = "SETTING_GENERAL_DATA")
public class SettingGeneralData extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SETTING_DATA_ID")
    private Long settingId ;

    @Column(name = "SCREEN_ID")
    private Long screenId;

    @Column(name = "COLUMN_NAME")
    private String columnName;

    @Column(name = "COLUMN_DISPLAY_NAME")
    private String columnDisplayName;

    @Column(name = "COLUMN_ORDER")
    private Integer columnOrder;

    @Column(name = "TABLE_NAME")
    private String tableName;

    @Column(name = "COLUMN_WIDTH")
    private Integer columnWidth;

    @Column(name = "STATUS")
    private Integer status;

    public SettingGeneralData() {
        super();
    }

    @Builder

    public SettingGeneralData(LocalDateTime createdAt, LocalDateTime updatedAt, int deletedFlg,
                              Long settingId, Long screenId, String columnName, String columnDisplayName,
                              Integer columnOrder, String tableName, Integer columnWidth, Integer status) {
        super(createdAt, updatedAt, deletedFlg);
        this.settingId = settingId;
        this.screenId = screenId;
        this.columnName = columnName;
        this.columnDisplayName = columnDisplayName;
        this.columnOrder = columnOrder;
        this.tableName = tableName;
        this.columnWidth = columnWidth;
        this.status = status;
    }

}
