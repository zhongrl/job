-- craete table 
CREATE TABLE schedule_job (
  ID  bigint(20) NOT NULL AUTO_INCREMENT ,
  SCHEDULE_JOB_ID  bigint(20) NULL DEFAULT NULL comment '定时任务ID',
  JOB_NAME  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL comment '定时任务名',
  ALIAS_NAME  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL comment '定时任务别名',
  JOB_CLASS  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' comment '定时任务执行类',
  JOB_GROUP  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL comment '任务分组',
  JOB_TRIGGER  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL comment '触发器',
  STATUS  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL comment '状态（0/1）',
  CRON_EXPRESSION  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL comment '任务表达式',
  DAY_OF_MONTH  int(5) NULL DEFAULT 1 comment '执行时间/天',
  HOUR  int(5) NULL DEFAULT 0 comment '时',
  MINUTE  int(5) NULL DEFAULT 0 comment '分',
  SECOND  int(5) null default 0 comment '秒',
  IS_SYNC  int(5) NULL DEFAULT 0 comment '同步异步',
  EXEC_TIME timestamp null comment '当次执行时间',
  NEXT_EXEC_TIME timestamp null comment '当次执行时间',
  DESCRIPTION  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL comment '描述',
  GMT_CREATE  timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP comment '创建时间',
  GMT_MODIFY  timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP comment '修改时间',
  PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT
;


/*==============================================================*/
/* table: user_info                                             */
/*==============================================================*/
create table  job_user_info(
   ID  bigint(20) not null auto_increment,
   USER_ID  varchar(32)  not null comment '用户id',
   USER_LOGIN_NAME  varchar(32)  not null comment '用户 登录账号',
   USER_AS  varchar(32)  default null comment '用户别名',
   LOGIN_PWD  varchar(1024)  default null comment '登录密码',
   SALT  varchar(100)  default null comment '密码盐',
   PAYMENT_PWD  varchar(1024)  default null comment '支付密码',
   PHONE_NUMBER  varchar(11)  default null comment '手机号码',
   EMAIL  varchar(50)  default null comment '邮箱',
   USER_TYPE  char(5)  default null comment '用户类型',
   STATUS  int(11) default '1' comment '状态:(0/1,不可用/可用)',
   CREATE_DATE  timestamp default current_timestamp comment '创建时间',
   UPDATE_DATE  timestamp null comment '修改时间',
   LASE_LOGINTIME  timestamp null comment '最后登录时间',
   IS_CERTIFICATION  int(5) default '0' comment '是否实名认证(0/1)',
   IS_QUESTION int(5) default '0' comment '是否设置密保问题(0/1)',
   IS_PAYMENT_PWD int(5) default '0' comment '是否修改支付密码(0/1)',
  primary key (id)
) engine=innodb default charset=utf8 collate=utf8_unicode_ci comment='用户信息';

/* index: indx_user_info_user_id  */
create unique index indx_user_info_user_id on job_user_info
(
   user_id
);

create unique index uq_user_info_user_name on job_user_info
(
	 user_login_name
);

create unique index uq_user_info_email on job_user_info
(
	 email
);

create unique index uq_user_info_phone on job_user_info
(
	 phone_number
);


