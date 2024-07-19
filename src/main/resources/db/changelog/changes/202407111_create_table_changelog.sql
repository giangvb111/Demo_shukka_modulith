--liquibase formatted sql
--changeset admin:create
--product-table splitStatements:true endDelimiter:;

----- create table SHUKKA_HEADER
create table SHUKKA_HEADER
(
    SHUKKA_HEADER_ID			    BIGINT PRIMARY KEY IDENTITY(1,1),
    SHUKKA_NO			            VARCHAR(20) NOT NULL,
    JYUCHUU_BI			            VARCHAR(8) NOT NULL,
    SHUKKA_YOTEIBI			        VARCHAR(8) NOT NULL,
    SHUKKA_JISSEIKIBI			    VARCHAR(8),
    NOUHINSAKI_ID			        BIGINT NOT NULL,
    TANTOSHA_ID			            BIGINT NOT NULL,
    TEKIYO_HEADER			        VARCHAR(MAX),
    SHUKKA_KUBUN			        VARCHAR(MAX),
    ZEITANSU			            VARCHAR(MAX),
    KENMEI			                VARCHAR(MAX),
    COMMENT			                VARCHAR(MAX),
    SHUKKA_UMI_FLG			        TINYINT DEFAULT 0,
    CREATED_AT                      DATETIME2 NOT NULL,
    UPDATED_AT                      DATETIME2,
    DELETE_FLG			            TINYINT DEFAULT 0,
    CONSTRAINT FK_NOUHIN_SHUKKA_HEADER
        FOREIGN KEY(NOUHINSAKI_ID)
            REFERENCES NOUHINSAKI_MASTER(NOUHINSAKI_ID),
    CONSTRAINT FK_TANTOSHA_SHUKKA_HEADER
        FOREIGN KEY(TANTOSHA_ID)
            REFERENCES TANTOSHA(TANTOSHA_ID)
);

----- create table SHUKKA_MESAI
create table SHUKKA_MESAI
(
    SHUKKA_MESAI_ID			            BIGINT PRIMARY KEY IDENTITY(1,1),
    SHUKKA_MESAI_NO			            VARCHAR(20) NOT NULL,
    SHUKKA_HEADER_ID			        BIGINT NOT NULL,
    SEIHIN_ID			                BIGINT NOT NULL,
    SOUKO_ID			                BIGINT NOT NULL,
    TANABAN_ID			                BIGINT NOT NULL,
    LOT_NO			                    VARCHAR(255) ,
    TANKA			                    INT ,
    KINGAKU			                    INT ,
    SHUKKA_YOTEI_SURYO			        DECIMAL(20,5),
    SHUKKA_JISSEIKI_SURYO			    DECIMAL(20,5),
    TEKIYO_MESAI			            VARCHAR(MAX),
    CREATED_AT                          DATETIME2 NOT NULL,
    UPDATED_AT                          DATETIME2,
    DELETE_FLG			                TINYINT DEFAULT 0,
    CONSTRAINT FK_SEIHIN
        FOREIGN KEY(SEIHIN_ID)
            REFERENCES SEIHIN_MASTER(SEIHIN_ID) ,
    CONSTRAINT FK_SOUKO
        FOREIGN KEY(SOUKO_ID)
            REFERENCES SOUKO_MASTER(SOUKO_ID) ,
    CONSTRAINT FK_SHUKKA_HEADER
        FOREIGN KEY(SHUKKA_HEADER_ID)
            REFERENCES SHUKKA_HEADER(SHUKKA_HEADER_ID)
);




