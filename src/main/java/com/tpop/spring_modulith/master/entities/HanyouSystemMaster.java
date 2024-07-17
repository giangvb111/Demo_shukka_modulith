package com.tpop.spring_modulith.master.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "HANYOU_SYSTEM_MASTER")
public class HanyouSystemMaster extends BaseEntity{

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "KEY_1")
    private String key1;

    @Column(name = "KEY_2")
    private String key2;

    @Column(name = "KEY_3")
    private String key3;

    @Column(name = "VALUE_1")
    private String value1;

    @Column(name = "VALUE_2")
    private String value2;

    @Column(name = "VALUE_3")
    private String value3;

    public HanyouSystemMaster() {
    }

    public HanyouSystemMaster(LocalDateTime createdAt, LocalDateTime updatedAt, int deletedFlg,
                              Long id, String key1, String key2, String key3, String value1, String value2, String value3) {
        super(createdAt, updatedAt, deletedFlg);
        this.id = id;
        this.key1 = key1;
        this.key2 = key2;
        this.key3 = key3;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }
}
