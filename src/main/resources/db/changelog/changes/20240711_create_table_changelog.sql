--liquibase formatted sql
--changeset admin:create
--product-table splitStatements:true endDelimiter:;

-- --- create table MENU
create table MENU
(
    MENU_ID                             BIGINT PRIMARY KEY IDENTITY(1,1),
    SCREEN_ID                           INT UNIQUE NOT NULL,
    SCREEN_CODE                         VARCHAR(255) NOT NULL,
    URL                                 VARCHAR(255) NOT NULL,
    FUNCTION_NAME                       VARCHAR(255) NOT NULL,
    CREATED_AT                          DATETIME2 NOT NULL,
    UPDATED_AT                          DATETIME2,
    DELETE_FLG			                TINYINT DEFAULT 0
);

-- --- create table GENERAL_SYSTEM_MASTER
create table GENERAL_SYSTEM_MASTER
(
    GSM_ID                                  BIGINT PRIMARY KEY IDENTITY(1,1),
    SCREEN_ID                           INT NOT NULL,
    SCREEN_CODE                         VARCHAR(255) NOT NULL,
    REFERENCE_SCREEN_ID                 INT ,
    REFERENCE_SCREEN_NAME               VARCHAR(255) NOT NULL,
    CREATED_AT                          DATETIME2 NOT NULL,
    UPDATED_AT                          DATETIME2,
    DELETE_FLG			                TINYINT DEFAULT 0 ,
    CONSTRAINT FK_MENU_GSM
        FOREIGN KEY(SCREEN_ID)
            REFERENCES MENU(SCREEN_ID) ,
);

-- --- create table GENERAL_TABLE_SETTING
create table GENERAL_TABLE_SETTING
(
    GTS_ID                                  BIGINT PRIMARY KEY IDENTITY(1,1),
    SCREEN_ID                           INT NOT NULL,
    TABLE_NAME                          VARCHAR(255) NOT NULL,
    CREATED_AT                          DATETIME2 NOT NULL,
    UPDATED_AT                          DATETIME2,
    DELETE_FLG			                TINYINT DEFAULT 0 ,
    CONSTRAINT FK_MENU_GTS
        FOREIGN KEY(SCREEN_ID)
            REFERENCES MENU(SCREEN_ID)
);

-- --- create table SETTING_GENERAL_DATA
create table SETTING_GENERAL_DATA
(
    SETTING_DATA_ID                 BIGINT PRIMARY KEY IDENTITY(1,1),
    SCREEN_ID 		                BIGINT ,
    COLUMN_NAME		                VARCHAR(255) NOT NULL,
    COLUMN_DISPLAY_NAME		        VARCHAR(255) NOT NULL,
    TABLE_NAME		                VARCHAR(255) NOT NULL,
    COLUMN_ORDER		            INT NOT NULL,
    COLUMN_WIDTH	                INT NOT NULL ,
    STATUS                          TINYINT DEFAULT 1 ,
    CREATED_AT                      DATETIME2 NOT NULL,
    UPDATED_AT                      DATETIME2,
    DELETE_FLG			            TINYINT DEFAULT 0
);

------ create table SEIHIN_MASTER
create table SEIHIN_MASTER
(
    SEIHIN_ID			            BIGINT PRIMARY KEY IDENTITY(1,1),
    SEIHIN_CODE                     VARCHAR(255) NOT NULL,
    SEIHIN_NAME                     VARCHAR(255) NOT NULL,
    IRISU                           DECIMAL(20,5),
    TEKIYO			                TEXT ,
    CREATED_AT                      DATETIME2 NOT NULL,
    UPDATED_AT                      DATETIME2,
    DELETE_FLG			            TINYINT DEFAULT 0
);

--- create table NOUHINSAKI_MASTER
create table NOUHINSAKI_MASTER
(
    NOUHINSAKI_ID                     BIGINT PRIMARY KEY IDENTITY(1,1),
    NOUHINSAKI_CODE		              VARCHAR(255) NOT NULL ,
    NOUHINSAKI_NAME		              VARCHAR(255) NOT NULL ,
    CREATED_AT                        DATETIME2 NOT NULL,
    UPDATED_AT                        DATETIME2,
    DELETE_FLG			              TINYINT DEFAULT 0
);

--- create table SOUKO_MASTER
create table SOUKO_MASTER
(
    SOUKO_ID                        BIGINT PRIMARY KEY IDENTITY(1,1),
    SOUKO_NAME		                VARCHAR(255) NOT NULL ,
    CREATED_AT                      DATETIME2 NOT NULL,
    UPDATED_AT                      DATETIME2,
    DELETE_FLG			            TINYINT DEFAULT 0
);

--- create table TANABAN_MASTER
create table TANABAN_MASTER
(
    TANABAN_ID                        BIGINT PRIMARY KEY IDENTITY(1,1),
    TANABAN_NAME		              VARCHAR(255) NOT NULL ,
    SOUKO_ID		                  BIGINT NOT NULL ,
    CREATED_AT                        DATETIME2 NOT NULL,
    UPDATED_AT                        DATETIME2,
    DELETE_FLG			              TINYINT DEFAULT 0 ,
    CONSTRAINT FK_SOUKO_MASTER
        FOREIGN KEY(SOUKO_ID)
            REFERENCES SOUKO_MASTER(SOUKO_ID)
);

--- create table TANTOSHA
create table TANTOSHA
(
    TANTOSHA_ID                       BIGINT PRIMARY KEY IDENTITY(1,1),
    TANTOSHA_NAME		              VARCHAR(255) NOT NULL ,
    TAISHOKU_FLG		              INT NOT NULL ,
    CREATED_AT                        DATETIME2 NOT NULL,
    UPDATED_AT                        DATETIME2,
    DELETE_FLG			              TINYINT DEFAULT 0
);

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

-- --- create table HANYOU_SYSTEM_MASTER
create table HANYOU_SYSTEM_MASTER
(
    ID                           BIGINT PRIMARY KEY IDENTITY(1,1),
    KEY_1                        VARCHAR(255) NOT NULL,
    KEY_2                        VARCHAR(255),
    KEY_3                        VARCHAR(255),
    VALUE_1                      VARCHAR(255) ,
    VALUE_2                      VARCHAR(255) ,
    VALUE_3                      VARCHAR(255) ,
    CREATED_AT                   DATETIME2 NOT NULL,
    UPDATED_AT                   DATETIME2,
    DELETE_FLG			         TINYINT DEFAULT 0
);



