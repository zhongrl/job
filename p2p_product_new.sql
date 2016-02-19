# Host: 10.10.16.221  (Version: 5.6.23-log)
# Date: 2016-02-19 11:00:24
# Generator: MySQL-Front 5.3  (Build 4.214)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "job_user_info"
#

DROP TABLE IF EXISTS `job_user_info`;
CREATE TABLE `job_user_info` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户id',
  `USER_LOGIN_NAME` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户 登录账号',
  `USER_AS` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户别名',
  `LOGIN_PWD` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '登录密码',
  `SALT` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '密码盐',
  `PAYMENT_PWD` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付密码',
  `PHONE_NUMBER` varchar(11) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手机号码',
  `EMAIL` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `USER_TYPE` char(5) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户类型',
  `STATUS` int(11) DEFAULT '1' COMMENT '状态:(0/1,不可用/可用)',
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_DATE` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `LASE_LOGINTIME` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  `IS_CERTIFICATION` int(5) DEFAULT '0' COMMENT '是否实名认证(0/1)',
  `IS_QUESTION` int(5) DEFAULT '0' COMMENT '是否设置密保问题(0/1)',
  `IS_PAYMENT_PWD` int(5) DEFAULT '0' COMMENT '是否修改支付密码(0/1)',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `indx_user_info_user_id` (`USER_ID`),
  UNIQUE KEY `uq_user_info_user_name` (`USER_LOGIN_NAME`),
  UNIQUE KEY `uq_user_info_email` (`EMAIL`),
  UNIQUE KEY `uq_user_info_phone` (`PHONE_NUMBER`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户信息';

#
# Structure for table "schedule_job"
#

DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SCHEDULE_JOB_ID` bigint(20) DEFAULT NULL COMMENT '定时任务ID',
  `JOB_NAME` varchar(255) DEFAULT NULL COMMENT '定时任务名',
  `ALIAS_NAME` varchar(255) DEFAULT NULL COMMENT '定时任务别名',
  `JOB_CLASS` varchar(255) NOT NULL DEFAULT '' COMMENT '定时任务执行类',
  `JOB_GROUP` varchar(255) DEFAULT NULL COMMENT '任务分组',
  `JOB_TRIGGER` varchar(255) DEFAULT NULL COMMENT '触发器',
  `STATUS` varchar(255) DEFAULT NULL COMMENT '状态（0/1）',
  `CRON_EXPRESSION` varchar(255) DEFAULT NULL COMMENT '任务表达式',
  `DAY_OF_MONTH` int(5) DEFAULT '1' COMMENT '执行时间/天',
  `HOUR` int(5) DEFAULT '0' COMMENT '时',
  `MINUTE` int(5) DEFAULT '0' COMMENT '分',
  `SECOND` int(5) DEFAULT '0' COMMENT '秒',
  `IS_SYNC` int(5) DEFAULT '0' COMMENT '同步异步',
  `EXEC_TIME` timestamp NULL DEFAULT NULL COMMENT '当次执行时间',
  `NEXT_EXEC_TIME` timestamp NULL DEFAULT NULL COMMENT '当次执行时间',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '描述',
  `GMT_CREATE` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `GMT_MODIFY` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `params` varchar(264) DEFAULT '{}' COMMENT '参数',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
