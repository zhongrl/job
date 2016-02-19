drop table if exists T_SMS_APP;

/*==============================================================*/
/* Table: T_SMS_APP                                             */
/*==============================================================*/
create table T_SMS_APP
(
   ID                   varchar(36) not null comment 'ID',
   USER_ID              varchar(36) comment '用户ID',
   MESSAGE_NAME         varchar(200) comment '消息名称',
   MESSAGE_PARAM        text comment '消息参数',
   MESSAGE_TYPE         tinyint(1) comment '消息类型(1=活期宝赎回|2=活期宝收益率提升)',
   MESSAGE_STATUS       tinyint(1) comment '消息类型(1=等待2=处理中|3=失败|3=完成)',
   INIT_TIME            datetime default NULL comment '初始时间',
   COMPLETE_TIME        datetime default NULL comment '处理时间',
   primary key (ID)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table T_SMS_APP comment 'APP消息推送';

drop table if exists T_SMS_APP_HIS;

/*==============================================================*/
/* Table: T_SMS_APP_HIS                                         */
/*==============================================================*/
create table T_SMS_APP_HIS
(
   ID                   varchar(36) not null comment 'ID',
   USER_ID              varchar(36) comment '用户ID',
   MESSAGE_NAME         varchar(200) comment '消息名称',
   MESSAGE_PARAM        text comment '消息参数',
   MESSAGE_TYPE         tinyint(1) comment '消息类型(1=活期宝赎回|2=活期宝收益率提升)',
   MESSAGE_STATUS       tinyint(1) comment '消息类型(1=等待2=处理中|3=失败|3=完成)',
   INIT_TIME            datetime default NULL comment '初始时间',
   COMPLETE_TIME        datetime default NULL comment '处理时间',
   primary key (ID)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

alter table T_SMS_APP_HIS comment 'APP消息推送历史';
