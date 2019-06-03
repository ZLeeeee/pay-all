/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.11 : Database - pay-sign
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`pay-sign` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `pay-sign`;

/*Table structure for table `qrtz_blob_triggers` */

DROP TABLE IF EXISTS `qrtz_blob_triggers`;

CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `qrtz_blob_triggers` */

/*Table structure for table `qrtz_calendars` */

DROP TABLE IF EXISTS `qrtz_calendars`;

CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `qrtz_calendars` */

/*Table structure for table `qrtz_cron_triggers` */

DROP TABLE IF EXISTS `qrtz_cron_triggers`;

CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `qrtz_cron_triggers` */

/*Table structure for table `qrtz_fired_triggers` */

DROP TABLE IF EXISTS `qrtz_fired_triggers`;

CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `qrtz_fired_triggers` */

/*Table structure for table `qrtz_job_details` */

DROP TABLE IF EXISTS `qrtz_job_details`;

CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `qrtz_job_details` */

/*Table structure for table `qrtz_locks` */

DROP TABLE IF EXISTS `qrtz_locks`;

CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `qrtz_locks` */

insert  into `qrtz_locks`(`SCHED_NAME`,`LOCK_NAME`) values ('quartzScheduler','TRIGGER_ACCESS');

/*Table structure for table `qrtz_paused_trigger_grps` */

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;

CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `qrtz_paused_trigger_grps` */

/*Table structure for table `qrtz_scheduler_state` */

DROP TABLE IF EXISTS `qrtz_scheduler_state`;

CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `qrtz_scheduler_state` */

/*Table structure for table `qrtz_simple_triggers` */

DROP TABLE IF EXISTS `qrtz_simple_triggers`;

CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `qrtz_simple_triggers` */

/*Table structure for table `qrtz_simprop_triggers` */

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;

CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `qrtz_simprop_triggers` */

/*Table structure for table `qrtz_triggers` */

DROP TABLE IF EXISTS `qrtz_triggers`;

CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `qrtz_triggers` */

/*Table structure for table `t_accesstoken` */

DROP TABLE IF EXISTS `t_accesstoken`;

CREATE TABLE `t_accesstoken` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `accessToken` varchar(255) NOT NULL COMMENT '请求验证Token',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可用（1可用，2不可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_accesstoken` */

insert  into `t_accesstoken`(`id`,`accessToken`,`status`) values (1,'TQBGWTT3IYYACCNAASQ55YTY3JANR45LUJNBWKSFIM52Z7NWNAZQ1003544',1);

/*Table structure for table `t_account_bank_cards` */

DROP TABLE IF EXISTS `t_account_bank_cards`;

CREATE TABLE `t_account_bank_cards` (
  `id` bigint(20) NOT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `third` char(1) DEFAULT NULL COMMENT '第三方挂号，0-用户 1-第三方',
  `channel_payment_id` bigint(20) DEFAULT NULL COMMENT '支付方式id',
  `accountType` varchar(255) DEFAULT NULL COMMENT '账户类型：支付宝、微信等等',
  `dayQuota` decimal(9,2) DEFAULT NULL COMMENT '单日限额: 0不限额',
  `phone_id` varchar(255) DEFAULT NULL COMMENT '手机标识',
  `bank_name` varchar(255) DEFAULT NULL COMMENT '银行名称',
  `bank_mark` varchar(255) DEFAULT NULL COMMENT '银行缩写',
  `bank_account` varchar(255) DEFAULT NULL COMMENT '持卡人',
  `cardNo` varchar(255) DEFAULT NULL COMMENT '银行卡账号',
  `tradeAmount` decimal(9,2) DEFAULT NULL COMMENT '交易成功金额',
  `status` char(1) DEFAULT NULL COMMENT '状态：0禁用。1启用',
  `valid` char(1) DEFAULT NULL COMMENT '有效标识：0-无效；1-有效；默认1',
  `signKey` varchar(255) DEFAULT NULL COMMENT '密钥与手机通讯时使用',
  `chard_index` varchar(255) DEFAULT NULL COMMENT '银行卡在支付宝里的id',
  `qrcode` varchar(255) DEFAULT NULL COMMENT '银行任意金额码链接',
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_account_bank_cards` */

/*Table structure for table `t_account_cloud_solids` */

DROP TABLE IF EXISTS `t_account_cloud_solids`;

CREATE TABLE `t_account_cloud_solids` (
  `id` bigint(20) NOT NULL,
  `account_cloud_id` bigint(20) DEFAULT NULL COMMENT '云端固码配置id',
  `account` varchar(255) DEFAULT NULL COMMENT '账号',
  `amount` decimal(11,2) DEFAULT NULL COMMENT '金额',
  `accountType` varchar(255) DEFAULT NULL COMMENT '账户类型：支付宝、微信等等',
  `content` varchar(255) DEFAULT NULL COMMENT '固码内容',
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_account_cloud_solids` */

/*Table structure for table `t_account_clouds` */

DROP TABLE IF EXISTS `t_account_clouds`;

CREATE TABLE `t_account_clouds` (
  `id` bigint(20) NOT NULL,
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户id',
  `channel_payment_id` bigint(20) DEFAULT NULL COMMENT '支付方式id',
  `accountType` varchar(255) DEFAULT NULL COMMENT '账户类型：支付宝、微信等等',
  `account` varchar(255) DEFAULT NULL COMMENT '账号',
  `cloudAddress` varchar(255) DEFAULT NULL COMMENT '云端地址',
  `dayQuota` decimal(9,2) DEFAULT NULL COMMENT '单日限额: 0不限额',
  `tradeAmount` decimal(8,2) DEFAULT NULL COMMENT '交易成功金额',
  `content` varchar(255) DEFAULT NULL COMMENT '任意金额码内容',
  `status` char(1) DEFAULT NULL COMMENT '状态：0禁用。1启用',
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_account_clouds` */

/*Table structure for table `t_account_day_counts` */

DROP TABLE IF EXISTS `t_account_day_counts`;

CREATE TABLE `t_account_day_counts` (
  `id` bigint(20) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL COMMENT '账号',
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户id',
  `account_amount` decimal(9,2) DEFAULT NULL COMMENT '账号单日成功交易金额',
  `account_order_count` int(10) DEFAULT NULL COMMENT '账号单日订单量',
  `account_order_suc_count` int(10) DEFAULT NULL COMMENT '账号单日成功量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_account_day_counts` */

insert  into `t_account_day_counts`(`id`,`create_by`,`create_time`,`update_by`,`update_time`,`account`,`user_id`,`account_amount`,`account_order_count`,`account_order_suc_count`) values (1,NULL,NULL,NULL,NULL,'11',NULL,'1111.00',11,10);

/*Table structure for table `t_account_phones` */

DROP TABLE IF EXISTS `t_account_phones`;

CREATE TABLE `t_account_phones` (
  `id` bigint(20) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户id',
  `third` char(1) DEFAULT NULL COMMENT '第三方挂号，0-用户 1-第三方',
  `channel_payment_id` bigint(20) DEFAULT NULL COMMENT '支付方式id',
  `accountType` varchar(255) DEFAULT NULL COMMENT '账户类型：支付宝、微信等等',
  `dayQuota` decimal(9,2) DEFAULT NULL COMMENT '单日限额',
  `phone_id` varchar(255) DEFAULT NULL COMMENT '手机标识',
  `alipayuserid` varchar(255) DEFAULT NULL COMMENT '支付宝userid',
  `alipayusername` varchar(255) DEFAULT NULL COMMENT '支付宝实名',
  `account` varchar(255) DEFAULT NULL COMMENT '收款账号',
  `qrcode` varchar(255) DEFAULT NULL COMMENT '收款任意金额码',
  `tradeAmount` decimal(9,2) DEFAULT NULL COMMENT '交易成功金额',
  `status` char(1) DEFAULT NULL COMMENT '状态：0禁用。1启用',
  `signKey` varchar(255) DEFAULT NULL COMMENT '密钥与手机通讯时使用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_account_phones` */

insert  into `t_account_phones`(`id`,`create_by`,`create_time`,`update_by`,`update_time`,`user_id`,`third`,`channel_payment_id`,`accountType`,`dayQuota`,`phone_id`,`alipayuserid`,`alipayusername`,`account`,`qrcode`,`tradeAmount`,`status`,`signKey`) values (1,NULL,'2019-04-19 11:28:43',NULL,NULL,'11',NULL,11,'支付宝','100000.00','77777777777',NULL,'张三','11',NULL,NULL,'1',NULL),(2,NULL,'2019-04-27 14:32:23',NULL,NULL,'11',NULL,11,'支付宝','100000.00','8888888888888888',NULL,'李四','456789',NULL,NULL,'1',NULL);

/*Table structure for table `t_account_uppers` */

DROP TABLE IF EXISTS `t_account_uppers`;

CREATE TABLE `t_account_uppers` (
  `id` bigint(20) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL COMMENT '账号(签约后生成的设备id)',
  `privatekey` text COMMENT '私钥',
  `publikey` text COMMENT '公钥',
  `signkey` varchar(255) DEFAULT NULL COMMENT '加密key',
  `channel_id` bigint(20) DEFAULT NULL COMMENT '通道id',
  `channel_payment_id` bigint(20) DEFAULT NULL COMMENT '支付方式id',
  `status` char(1) DEFAULT NULL COMMENT '状态：0禁用。1启用',
  `order_num` int(11) DEFAULT NULL COMMENT '订单量',
  `dayQuota` decimal(9,6) DEFAULT NULL COMMENT '限额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_account_uppers` */

insert  into `t_account_uppers`(`id`,`create_by`,`create_time`,`update_by`,`update_time`,`account`,`privatekey`,`publikey`,`signkey`,`channel_id`,`channel_payment_id`,`status`,`order_num`,`dayQuota`) values (1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(5,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `t_anticontent` */

DROP TABLE IF EXISTS `t_anticontent`;

CREATE TABLE `t_anticontent` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `antiContent` varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '拼多多参数anticontent',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_anticontent` */

insert  into `t_anticontent`(`id`,`antiContent`,`status`) values (1,'0alAfxnUmNhYq9dayy8SgULmbaUgtS3k_wj8QqkvgyugpiYuEQ2_HdF_-GMaiGgkwfQLmJC0FqJ5r6OAOZwoyWQRhIq8IQA3og9J9No4sMd2LYDIAiC0RDQNlsk1N47CyANQvCUWLlc1NHpYxaFF9OcIXX1GpVpYSVFkm1Br_kPXaJ7dMvByK7ZI4ydUGXardWgm_8QorJCDk_1n3ks4mkinYZFeljzoKZJP8NMvt1XA0X-CqKXAe9YHOT9BpTrYvjj6VArbsd8DhX1spxPy6IT_O7g76rZI4ScahCK7SdF6qRzeIoGmR6Ymuuis20DuZy4DuAqHwmJSeQBrkj3_qEo99uTe68TDRwmRS47XlA60IBSw_MjWmc87WhnERLPB5UEER9meV6H9W9kFCtbIcz_jtgAB864XmzdwfCTQNBYwdC4MHsF8shIr201H9N7xq8oEhdpRPEuRqzp0LUTSzUDB9YhrY1OpOBUES8XB7PXWP1cig1yxgDMtVWXMnyXDlR14g81zh2hQGIPlNl-IUgunGqJr6vjAEag7M0YWIlcPqpnNP4b3vP0kr_52HgdqDeIwFR4fBqx7pEJVcOjct5ecF1-dD7YOwe8ZjUoMr3hbbNzNLWHXq383ucQj78JpOl1ijR368cqhDJ',1);

/*Table structure for table `t_bank` */

DROP TABLE IF EXISTS `t_bank`;

CREATE TABLE `t_bank` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL COMMENT '银行编码',
  `bankName` varchar(40) DEFAULT NULL COMMENT '银行名称',
  `ico` varchar(255) DEFAULT NULL COMMENT '银行Logo',
  `status` char(1) DEFAULT NULL COMMENT '状态：0禁用，1启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/*Data for the table `t_bank` */

insert  into `t_bank`(`id`,`create_by`,`create_time`,`update_by`,`update_time`,`code`,`bankName`,`ico`,`status`) values (1,NULL,NULL,NULL,NULL,'ICBC','中国工商银行',NULL,'1'),(2,NULL,NULL,NULL,NULL,'CCB','中国建设银行',NULL,'1'),(3,NULL,NULL,NULL,NULL,'ABC','中国农业银行',NULL,'1'),(4,NULL,NULL,NULL,NULL,'BOC','中国银行',NULL,'1'),(5,NULL,NULL,NULL,NULL,'CITIC','中信银行',NULL,'1'),(6,NULL,NULL,NULL,NULL,'CMB','招商银行',NULL,'1'),(7,NULL,NULL,NULL,NULL,'HXBANK','华夏银行',NULL,'1'),(8,NULL,NULL,NULL,NULL,'COMM','交通银行',NULL,'1'),(9,NULL,NULL,NULL,NULL,'PSBC','中国邮政储蓄银行',NULL,'1'),(10,NULL,NULL,NULL,NULL,'SPDB','浦发银行',NULL,'1'),(11,NULL,NULL,NULL,NULL,'CEB','中国光大银行',NULL,'1'),(12,NULL,NULL,NULL,NULL,'CMBC','中国民生银行',NULL,'1'),(13,NULL,NULL,NULL,NULL,'GDB','广发银行',NULL,'1'),(14,NULL,NULL,NULL,NULL,'BJBANK','北京银行',NULL,'1'),(15,NULL,NULL,NULL,NULL,'HKBEA','东亚银行',NULL,'1'),(16,NULL,NULL,NULL,NULL,'NJCB','南京银行',NULL,'1'),(17,NULL,NULL,NULL,NULL,'NBBANK','宁波银行',NULL,'1'),(18,NULL,NULL,NULL,NULL,'SPABANK','平安银行',NULL,'1'),(19,NULL,NULL,NULL,NULL,'SHBANK','上海银行',NULL,'1'),(20,NULL,NULL,NULL,NULL,'CIB','兴业银行',NULL,'1'),(21,NULL,NULL,NULL,NULL,'ANTBANK','网商银行',NULL,'1'),(22,NULL,NULL,NULL,NULL,'CZBANK','浙商银行',NULL,'1'),(23,NULL,NULL,NULL,NULL,'ALIPAY','支付宝',NULL,'1'),(24,NULL,NULL,NULL,NULL,'WECHAT','微信',NULL,'1');

/*Table structure for table `t_bank_cards` */

DROP TABLE IF EXISTS `t_bank_cards`;

CREATE TABLE `t_bank_cards` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户id',
  `bank_id` bigint(20) DEFAULT NULL COMMENT '银行id',
  `bankCardNo` varchar(255) DEFAULT NULL COMMENT '银行卡号',
  `accountName` varchar(255) DEFAULT NULL COMMENT '开户名',
  `branchName` varchar(255) DEFAULT NULL COMMENT '支行名',
  `status` char(1) DEFAULT NULL COMMENT '状态：0禁用，1启用，只能1张卡为1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_bank_cards` */

/*Table structure for table `t_channel` */

DROP TABLE IF EXISTS `t_channel`;

CREATE TABLE `t_channel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `merchant` varchar(191) DEFAULT NULL COMMENT '上游商户号',
  `signkey` varchar(191) DEFAULT NULL COMMENT '上游密钥',
  `channelName` varchar(30) DEFAULT NULL COMMENT '通道名称',
  `channelCode` varchar(20) DEFAULT NULL COMMENT '通道编码',
  `refererDomain` varchar(20) DEFAULT NULL COMMENT '防封域名',
  `status` char(1) DEFAULT NULL COMMENT '状态:0禁用，1启用，2删除',
  `channelQuota` int(10) DEFAULT NULL COMMENT '通道限额：0不限额',
  `tradeAmount` decimal(12,2) DEFAULT NULL COMMENT '交易成功金额',
  `extend` varchar(1000) DEFAULT NULL COMMENT '扩展字段,存放json',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `t_channel` */

insert  into `t_channel`(`id`,`create_by`,`create_time`,`update_by`,`update_time`,`merchant`,`signkey`,`channelName`,`channelCode`,`refererDomain`,`status`,`channelQuota`,`tradeAmount`,`extend`) values (1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL),(2,'66','2019-04-27 10:30:54',NULL,NULL,'123456','66666','支付宝',NULL,NULL,'1',NULL,NULL,NULL),(3,NULL,'2019-04-28 15:10:19',NULL,NULL,NULL,'1','1','1',NULL,'0',NULL,NULL,NULL);

/*Table structure for table `t_channel_payments` */

DROP TABLE IF EXISTS `t_channel_payments`;

CREATE TABLE `t_channel_payments` (
  `id` bigint(20) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `channel_id` bigint(20) DEFAULT NULL COMMENT '通道id',
  `paymentName` varchar(30) DEFAULT NULL COMMENT '支付名称',
  `paymentCode` varchar(30) DEFAULT NULL COMMENT '支付编码',
  `ico` varchar(255) DEFAULT NULL COMMENT '支付方式log',
  `runRate` decimal(9,6) DEFAULT NULL COMMENT '运营费率',
  `costRate` decimal(9,6) DEFAULT NULL COMMENT '成本费率',
  `minAmount` int(11) DEFAULT NULL COMMENT '单笔最小金额',
  `maxAmount` int(11) DEFAULT NULL COMMENT '单笔最大金额',
  `status` char(1) DEFAULT NULL COMMENT '状态：0关闭，1启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_channel_payments` */

insert  into `t_channel_payments`(`id`,`create_by`,`create_time`,`update_by`,`update_time`,`channel_id`,`paymentName`,`paymentCode`,`ico`,`runRate`,`costRate`,`minAmount`,`maxAmount`,`status`) values (1,NULL,NULL,NULL,NULL,1,'支付宝',NULL,NULL,NULL,NULL,NULL,NULL,'1');

/*Table structure for table `t_create_order_token` */

DROP TABLE IF EXISTS `t_create_order_token`;

CREATE TABLE `t_create_order_token` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `create_order_token` varchar(255) NOT NULL COMMENT '创建订单Token',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'token状态(1可用,0不可用)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_create_order_token` */

insert  into `t_create_order_token`(`id`,`create_order_token`,`status`) values (1,'b67997b3-09cd-4a7c-8017-e3f9a2ff5eb0',1);

/*Table structure for table `t_dict` */

DROP TABLE IF EXISTS `t_dict`;

CREATE TABLE `t_dict` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `sort_order` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_dict` */

/*Table structure for table `t_dict_data` */

DROP TABLE IF EXISTS `t_dict_data`;

CREATE TABLE `t_dict_data` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `dict_id` varchar(255) DEFAULT NULL,
  `sort_order` decimal(10,2) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_dict_data` */

/*Table structure for table `t_file` */

DROP TABLE IF EXISTS `t_file`;

CREATE TABLE `t_file` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `size` bigint(20) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `f_key` varchar(255) DEFAULT NULL,
  `location` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_file` */

/*Table structure for table `t_log` */

DROP TABLE IF EXISTS `t_log`;

CREATE TABLE `t_log` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `cost_time` int(11) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `ip_info` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `request_param` longtext,
  `request_type` varchar(255) DEFAULT NULL,
  `request_url` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `log_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_log` */

insert  into `t_log`(`id`,`create_by`,`create_time`,`del_flag`,`update_by`,`update_time`,`cost_time`,`ip`,`ip_info`,`name`,`request_param`,`request_type`,`request_url`,`username`,`log_type`) values ('cf503808e728413d8aa44ef2b94f4a05',NULL,'2019-04-16 16:34:36',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','POST','/permission/add','admin',NULL),('2e2b598cd7e64a3f87302655cd3f67b8',NULL,'2019-04-16 16:34:23',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('c90d41e1d4ba4d0eb7cec1ae19cbb68f',NULL,'2019-04-16 13:50:31',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('25774c8d5b3a43118653d3000afe2f9e',NULL,'2019-04-16 15:00:09',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('d78467be23994e8b8ffc4952349adc96',NULL,'2019-04-16 15:33:29',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','POST','/permission/add','admin',NULL),('2f342e90d6e54b63bc7d4b7a411b3df2',NULL,'2019-04-16 16:08:50',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('a065a2086e534563b70aad46b992923d',NULL,'2019-04-16 16:34:49',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('7bf310d3293f496fa7f024e2859f815e',NULL,'2019-04-16 16:35:01',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','POST','/permission/add','admin',NULL),('1ce13efba57b41eba225b05c78c24e74',NULL,'2019-04-16 16:35:12',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('6e734984b003464aa4f68fc2578a583a',NULL,'2019-04-16 16:35:35',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('3eba2c0a32ed414d8850a4236313c9db',NULL,'2019-04-16 16:36:26',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('acf5b89802ee4335acc4e6449d2fdcf9',NULL,'2019-04-16 16:38:27',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('48e84a80736e47c38636df263428a842',NULL,'2019-04-16 16:39:03',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('3e32933bc69a40f693380c8eef3a6095',NULL,'2019-04-16 16:40:19',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','POST','/permission/add','admin',NULL),('d166050276ad4dc183b8338345d926db',NULL,'2019-04-16 16:40:46',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','DELETE','/permission/delByIds/182f8ec156894fe9812f788e02dbf7d5','admin',NULL),('7a12af4ff63a4813a6bbf079ec1c8798',NULL,'2019-04-16 16:40:52',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','DELETE','/permission/delByIds/d2ff941e32524ab599ae62789f54532c','admin',NULL),('ac0c1a5d520a4aa781205174c2225d4b',NULL,'2019-04-18 09:59:47',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('b3c7e87705e44db2b3273cd3e26f57a3',NULL,'2019-04-19 17:01:08',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','DELETE','/permission/delByIds/9c242fac34874d1e9867f7bad895b45f,7964f35401e3466ba2ca3196d5ae4c50','admin',NULL),('0a57f68a65e9490e9845230fdcc0d775',NULL,'2019-04-19 17:02:44',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','POST','/permission/add','admin',NULL),('b849d2f923364c0baf31c8d25a579cfc',NULL,'2019-04-22 09:40:15',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','DELETE','/permission/delByIds/a26ca23a297947f7bc3b3f3578980177','admin',NULL),('66ab5c021d6f42109a986fc27af97a43',NULL,'2019-04-22 09:41:29',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','POST','/permission/add','admin',NULL),('c271313b80e246c69ef44ac6f4b149be',NULL,'2019-04-22 09:47:39',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','POST','/permission/add','admin',NULL),('b18c3cdc11c445c592b246e0b9e1b426',NULL,'2019-04-22 09:53:40',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','POST','/permission/add','admin',NULL),('a49663b1a5a34a8da851929388afe13a',NULL,'2019-04-22 10:26:28',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('3780bf66ec91474c9f05a24f5fd32748',NULL,'2019-04-26 11:29:13',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('ab5f1e2f2c6746c49b3116e1338e020f',NULL,'2019-04-26 16:43:00',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','POST','/permission/add','admin',NULL),('a3b77523cab54622998b52fcb769c42d',NULL,'2019-04-26 16:43:26',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('0929f332f8b34cd7b87cbae2368460de',NULL,'2019-04-26 16:45:50',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','POST','/permission/add','admin',NULL),('168b576be5eb4d1cb0138a43003a6031',NULL,'2019-04-26 16:47:19',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('f0e12f57be634874878afbacf745481e',NULL,'2019-04-26 16:48:13',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('3e2e1357701149059a4f0397d9d5d523',NULL,'2019-04-26 17:10:18',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('4dadd5a99fbb4d659af8abe99c67310a',NULL,'2019-04-26 17:15:22',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('9cac6d2ba54740a0bfe9e1a56a64f45d',NULL,'2019-04-26 17:15:42',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('f45450d269b64bfca750cca5e79b30ad',NULL,'2019-04-26 17:17:10',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('c50e9831f7f14e1da50203cb89dfa01b',NULL,'2019-04-26 17:21:32',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('89f8c0c7598541e1878cfb187bced2b6',NULL,'2019-04-27 09:55:02',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','POST','/permission/add','admin',NULL),('9afd47398f5c43ab82bdc3be67c8546f',NULL,'2019-04-27 09:56:44',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('831bd16d134a4b0594cc1110662ec5a4',NULL,'2019-04-27 11:29:41',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','POST','/permission/add','admin',NULL),('d50941dc82d445fa889d660eeab03590',NULL,'2019-04-27 11:30:27',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('603cf6d446b247a5bbffbc32f5c0efe8',NULL,'2019-04-27 11:31:57',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('bbfff4215d844204b3b12c46c892dbfa',NULL,'2019-04-27 11:34:41',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('c840f0545b2d48458900daf890491074',NULL,'2019-04-27 15:17:08',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','POST','/permission/add','admin',NULL),('a53d3e4d779047d9bd102f0867c9004d',NULL,'2019-04-27 15:18:12',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('aac4ecec9b774ec39e6781f044652112',NULL,'2019-04-27 17:54:34',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','POST','/permission/add','admin',NULL),('7856b82b0f7b4fb19467bc7c0ba1d33b',NULL,'2019-04-27 17:55:27',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('0e5df82c1cee4911b5dd5d35840da1a9',NULL,'2019-04-28 11:21:30',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','POST','/permission/add','admin',NULL),('088d0e31588d47f7bb1a90e0d35bd9fb',NULL,'2019-04-28 11:22:14',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('b43fd08adb3a43a2a04596175e13dd73',NULL,'2019-04-28 14:28:41',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('03dfecf7ab7a4ba88fd17b5471424fc1',NULL,'2019-05-05 11:48:29',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('435ca838f4ad4fbb8565db4b7a3c2ffa',NULL,'2019-05-05 13:43:43',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('2e1301591e4b4b71a9a74989131ab175',NULL,'2019-05-08 17:05:31',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('83119288671e46f1bfd1b7a34506a52c',NULL,'2019-05-14 16:23:01',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('6d993d127d6743f5a6dbeb825b51d440',NULL,'2019-05-14 16:23:51',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('9cc8ba4fbb04464c90ec3e509f13b9b6',NULL,'2019-05-14 16:26:16',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('ffc510026a8b402f91b7d023f16d14ea',NULL,'2019-05-14 16:27:42',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('88a7a9e4bdd44a889b1b420d31758ef4',NULL,'2019-05-14 17:03:35',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('fbd9721214a34831a6942b58215bc329',NULL,'2019-05-15 11:02:37',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('ff5815900e624e368a98fa29edaa384e',NULL,'2019-05-15 15:21:04',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('ffa91d14779840f08096fb86f4ac961c',NULL,'2019-05-15 15:38:40',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','POST','/permission/add','admin',NULL),('9b5aa343a41c4a44b4a86307f96ab7f4',NULL,'2019-05-15 15:40:18',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('269d42d0839843619a011caf9f8bc439',NULL,'2019-05-16 10:31:26',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('50788111710e4279a4f9deb2b2c3dc3c',NULL,'2019-05-16 14:07:24',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('5a2dae62147b4185a397cd3d9d4cc10f',NULL,'2019-05-16 14:38:30',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','POST','/permission/add','admin',NULL),('b55b43a383ac40b997951ced10ff8386',NULL,'2019-05-16 14:39:45',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('6512a866f6f84c46b73bf8bed618bcea',NULL,'2019-05-16 18:40:51',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('fabf9dcd46194f279919aaa11073890f',NULL,'2019-05-20 14:53:32',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'111','{}','GET','/permission/getMenuList/11','111',NULL),('d55bb88846d2471fb052b1937a265b03',NULL,'2019-05-20 14:53:59',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'111','{}','GET','/permission/getMenuList/11','111',NULL),('e52d22c346ee46688a7d5c9d2dfdec59',NULL,'2019-05-20 14:54:21',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'111','{}','GET','/permission/getMenuList/11','111',NULL),('ffc222a3d03a466eb132e08f85ea0f07',NULL,'2019-05-20 14:56:12',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'111','{}','GET','/permission/getMenuList/11','111',NULL),('63212c6add8847a5bd9f5a73f67613b2',NULL,'2019-05-20 15:10:55',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'111','{}','GET','/permission/getMenuList/11','111',NULL),('ebc9c6ba13ed41ce85be314d0448fcf0',NULL,'2019-05-20 15:14:58',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'pdd','{}','GET','/permission/getMenuList/2d7b48d58a574fc1b6b874a930829a62','pdd',NULL),('6849aa1a8521477c87b38a7780b8747b',NULL,'2019-05-20 15:15:48',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'pdd','{}','GET','/permission/getMenuList/2d7b48d58a574fc1b6b874a930829a62','pdd',NULL),('f2bfd6a62a404ce9aac38d589f99f98f',NULL,'2019-05-20 15:15:59',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('1efcb950d7a547848ca0758f71219db2',NULL,'2019-05-20 15:30:39',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'pdd','{}','GET','/permission/getMenuList/2d7b48d58a574fc1b6b874a930829a62','pdd',NULL),('c81b3f97d1b6492d95af3f3e14ea8fb5',NULL,'2019-05-20 15:45:52',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('6abe26294d2a47a690e6202648f76c11',NULL,'2019-05-20 15:53:01',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'pdd','{}','GET','/permission/getMenuList/2d7b48d58a574fc1b6b874a930829a62','pdd',NULL),('1355efefb7a24a3d89bc518871734602',NULL,'2019-05-20 17:43:05',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('38fc2980891d47ba990043117e15afab',NULL,'2019-05-20 18:49:39',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('997ee88c5c244663a4190fd130214139',NULL,'2019-05-20 18:50:48',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'pdd','{}','GET','/permission/getMenuList/2d7b48d58a574fc1b6b874a930829a62','pdd',NULL),('8279a8e3904e42cf85c8436202321ba1',NULL,'2019-05-20 18:54:45',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'pdd','{}','GET','/permission/getMenuList/2d7b48d58a574fc1b6b874a930829a62','pdd',NULL),('c2990b3cd4c44996b36ad75ff2c966a0',NULL,'2019-05-20 18:55:36',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('c173ff46361e4f58a8441183a3d5576f',NULL,'2019-05-20 18:57:31',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'pdd','{}','GET','/permission/getMenuList/2d7b48d58a574fc1b6b874a930829a62','pdd',NULL),('d470cc88fdd040bd831f5d78cab34241',NULL,'2019-05-20 19:20:38',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'pdd','{}','GET','/permission/getMenuList/2d7b48d58a574fc1b6b874a930829a62','pdd',NULL),('bc64c3fedaa0406989f03884fc2cf88e',NULL,'2019-05-20 19:44:19',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('8e84efe9e49349f3bd56ebaa77906991',NULL,'2019-05-20 19:46:32',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('432b7b5b003a4a14903b9f329fb0236a',NULL,'2019-05-20 19:50:05',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('575181d3d3fd4ebf8917128d765544ba',NULL,'2019-05-20 19:52:52',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('b20de1afa19a4f9286b9b8836c12c55a',NULL,'2019-05-20 19:56:25',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('127f52411f7f48ddb2afd2f1046265f8',NULL,'2019-05-20 22:18:01',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('7d6e11d09cb242be8e322cb8db904669',NULL,'2019-05-22 15:09:44',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('04afbbdde49c4296b85bac6cb31abfea',NULL,'2019-05-22 15:15:38',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','POST','/permission/add','admin',NULL),('0923e85a2de549e1bd1c52ed7b7f6b26',NULL,'2019-05-22 15:17:19',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('3f48b28f13e04830ac4d7994227c8a6d',NULL,'2019-05-23 10:41:31',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('3825cf975a0e439d80c3bb7cddfcac40',NULL,'2019-05-23 10:42:08',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'pdd','{}','GET','/permission/getMenuList/2d7b48d58a574fc1b6b874a930829a62','pdd',NULL),('b3c75613d1264b3e841dc88787e98ba6',NULL,'2019-05-23 10:50:56',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('c99e571912d247b08c7f1dc771037c04',NULL,'2019-05-23 14:55:58',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('1ff62bdceadb4a88aee5f1f8e34dd98b',NULL,'2019-05-23 16:32:06',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'pdd','{}','GET','/permission/getMenuList/2d7b48d58a574fc1b6b874a930829a62','pdd',NULL),('6ed1b0e7a57845ac9e680f3104866483',NULL,'2019-05-23 16:36:20',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'pdd','{}','GET','/permission/getMenuList/2d7b48d58a574fc1b6b874a930829a62','pdd',NULL),('b868ca26ab424f42b6f6acf3d5a7e036',NULL,'2019-05-23 16:41:55',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('865247210a2b4682999ace811d1da6f7',NULL,'2019-05-23 16:53:58',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('ad6c6ee1a2b84ebb9a81b8e5ed6371fb',NULL,'2019-05-23 16:59:27',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('66fee593001b4066a1065a199510f1b9',NULL,'2019-05-23 17:33:34',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('23ebabb49ee64d2a875b402f60d80df3',NULL,'2019-05-23 18:19:18',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('a8dfc59b7898410f8574aea7976b924a',NULL,'2019-05-24 10:49:41',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('4c49ce11daff4f8e8b151d7cb766517b',NULL,'2019-05-24 10:49:59',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('201e953db5714324bd1718c598f316b1',NULL,'2019-05-24 11:09:23',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('c3b09c496aeb40ae989d87e3c7b2f7d5',NULL,'2019-05-24 11:15:55',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('e56e603adf8d498fba4bcb48a85af185',NULL,'2019-05-24 12:25:03',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'pdd','{}','GET','/permission/getMenuList/2d7b48d58a574fc1b6b874a930829a62','pdd',NULL),('ac1d4a5bec4746c4bf2a09bb883396c6',NULL,'2019-05-24 14:48:14',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('88987e07182049b68d438f48fe46d836',NULL,'2019-05-24 14:49:52',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('1947ca759c574893a0a729588f8333a6',NULL,'2019-05-24 17:32:59',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('6f9e41d40e6848728c5b35b29910e575',NULL,'2019-05-29 12:23:32',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'SZPDD','{}','GET','/permission/getMenuList/b744c5fd57d84a86995ac2f9b4c19e52','SZPDD',NULL),('16235f4d61e6494797eb22078bab437e',NULL,'2019-05-29 12:37:19',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('a4d948557ae74b00b72ca8efa7cbe1c2',NULL,'2019-05-29 13:07:41',NULL,NULL,NULL,NULL,'1.80.93.99',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('835efe1f94114e0996ce290e99efdebe',NULL,'2019-05-29 13:14:17',NULL,NULL,NULL,NULL,'1.80.93.99',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('3f6387c156da4b39bbe9ee4ea0fbc99f',NULL,'2019-05-29 13:16:06',NULL,NULL,NULL,NULL,'1.80.93.99',NULL,'SZPDD','{}','GET','/permission/getMenuList/b744c5fd57d84a86995ac2f9b4c19e52','SZPDD',NULL),('d91b6e185d5b49168ab7231b447a27e6',NULL,'2019-05-29 13:17:37',NULL,NULL,NULL,NULL,'59.61.175.223',NULL,'SZPDD','{}','GET','/permission/getMenuList/b744c5fd57d84a86995ac2f9b4c19e52','SZPDD',NULL),('9c7dd4ab28d34b16a966da72eb36fbdf',NULL,'2019-05-29 13:36:10',NULL,NULL,NULL,NULL,'1.80.93.99',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('18699c55d28f4026b06155dcdacd6fdf',NULL,'2019-05-29 14:46:19',NULL,NULL,NULL,NULL,'1.80.93.99',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('55c510d928d04492b879030ce2ec592c',NULL,'2019-05-29 14:57:40',NULL,NULL,NULL,NULL,'1.80.93.99',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('4092823fd5b740d0bbc92cd1e9cbcd79',NULL,'2019-05-29 15:05:24',NULL,NULL,NULL,NULL,'125.71.79.241',NULL,'cy123456','{}','GET','/permission/getMenuList/47c6dc15274343df92ffeee9ccfbfb45','cy123456',NULL),('1b19fd997d3443daa992f288d1357fdb',NULL,'2019-05-29 15:24:37',NULL,NULL,NULL,NULL,'1.80.93.99',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('072aee892a8245d18b0f0ebeeebeaa94',NULL,'2019-05-29 15:37:46',NULL,NULL,NULL,NULL,'125.71.79.241',NULL,'cy123456','{}','GET','/permission/getMenuList/47c6dc15065343df92ffeee9ccfbfb45','cy123456',NULL),('0a2895b2882449b4ab541471dc893228',NULL,'2019-05-29 15:39:43',NULL,NULL,NULL,NULL,'125.71.79.241',NULL,'cy123456','{}','GET','/permission/getMenuList/47c6dc15065343df92ffeee9ccfbfb45','cy123456',NULL),('1ad85b6b34c44932ba21dc1e8afa8b63',NULL,'2019-05-29 20:39:14',NULL,NULL,NULL,NULL,'111.19.45.70',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('df635742f32f4efa91a4bc116c5c9c55',NULL,'2019-05-30 12:27:41',NULL,NULL,NULL,NULL,'1.80.99.61',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('cf77df5e9854491ba83a6d104ed57f6f',NULL,'2019-05-30 12:29:34',NULL,NULL,NULL,NULL,'1.80.99.61',NULL,'cy222333','{}','GET','/permission/getMenuList/f90d70642cb44d47af489d64f2faefe9','cy222333',NULL),('84e419e6090d4526a83286cdfb6b1bc6',NULL,'2019-05-30 12:45:34',NULL,NULL,NULL,NULL,'183.160.114.249',NULL,'cy222333','{}','GET','/permission/getMenuList/f90d70642cb44d47af489d64f2faefe9','cy222333',NULL),('0dae23d6aa134c079219d1130b05e167',NULL,'2019-05-30 12:55:04',NULL,NULL,NULL,NULL,'1.80.99.61',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('348971597c9a423ca1994a5ccf58e984',NULL,'2019-05-30 13:40:41',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('426c2d269e7b4d5992f0cc7a9345314b',NULL,'2019-05-30 14:01:32',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('49e25aea48a9438abfd672a23718d315',NULL,'2019-05-30 14:04:24',NULL,NULL,NULL,NULL,'1.80.99.61',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('f68fd89b36aa439d9f9172071fb38cc0',NULL,'2019-05-30 14:05:48',NULL,NULL,NULL,NULL,'1.80.99.61',NULL,'SZPDD','{}','GET','/permission/getMenuList/b744c5fd57d84a86995ac2f9b4c19e52','SZPDD',NULL),('b4c95f147b874216b50400aefa1f1977',NULL,'2019-05-30 14:19:31',NULL,NULL,NULL,NULL,'1.80.99.61',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('9f19163894f54d0390a582017aff66e5',NULL,'2019-05-30 17:34:16',NULL,NULL,NULL,NULL,'36.18.107.214',NULL,'cy222333','{}','GET','/permission/getMenuList/f90d70642cb44d47af489d64f2faefe9','cy222333',NULL),('5029d931f51b4d5a9fcef20f7b4dac41',NULL,'2019-05-30 20:33:15',NULL,NULL,NULL,NULL,'36.32.48.80',NULL,'cy222333','{}','GET','/permission/getMenuList/f90d70642cb44d47af489d64f2faefe9','cy222333',NULL),('76fc9061fc9a4a8e89689f1cfd0ecd10',NULL,'2019-05-30 20:54:38',NULL,NULL,NULL,NULL,'1.80.99.61',NULL,'SZPDD','{}','GET','/permission/getMenuList/b744c5fd57d84a86995ac2f9b4c19e52','SZPDD',NULL),('9ee8f159e95f4d1ba649889f90096e89',NULL,'2019-05-30 20:55:31',NULL,NULL,NULL,NULL,'1.80.99.61',NULL,'cy123456','{}','GET','/permission/getMenuList/47c6dc15065343df92ffeee9ccfbfb45','cy123456',NULL),('812438d3d3c7455fbc137c4bb639b3f2',NULL,'2019-05-30 21:12:29',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('3bba32c89b4e4d79952cdac2e61a87d3',NULL,'2019-05-30 23:29:57',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('24f5f00d576144b4bff64276ee013b65',NULL,'2019-05-31 18:08:36',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('c46667fed1d548149b035fcfe7c29e26',NULL,'2019-05-31 18:57:21',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('abf8179cf5cc44f98a0706d96b253bc9',NULL,'2019-06-03 10:47:58',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'pdd','{}','GET','/permission/getMenuList/2d7b48d58a574fc1b6b874a930829a62','pdd',NULL),('3c61d0da58ed458f8b7e4871899329b7',NULL,'2019-06-03 10:49:25',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('7262f9fdb9a7405b826e7e45a9f9fbb7',NULL,'2019-06-03 10:50:26',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'pdd','{}','GET','/permission/getMenuList/2d7b48d58a574fc1b6b874a930829a62','pdd',NULL),('634f9389fb404680b9e7e1c90ac93a38',NULL,'2019-06-03 11:40:36',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL),('de9d25991e384d7e925bba2ecf1a9b95',NULL,'2019-06-03 17:36:19',NULL,NULL,NULL,NULL,'127.0.0.1',NULL,'admin','{}','GET','/permission/getMenuList/682265633886208','admin',NULL);

/*Table structure for table `t_order` */

DROP TABLE IF EXISTS `t_order`;

CREATE TABLE `t_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` varchar(255) DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL COMMENT '商户id',
  `merchant` varchar(10) DEFAULT NULL COMMENT '商户号',
  `agent_id` varchar(255) DEFAULT NULL COMMENT '归属代理id',
  `channel_id` bigint(20) DEFAULT NULL COMMENT '通道id',
  `channel_payment_id` bigint(20) DEFAULT NULL COMMENT '支付方式id',
  `account` varchar(255) DEFAULT NULL COMMENT '账号(提供二维码)',
  `phone_id` varchar(255) DEFAULT NULL COMMENT '设备号',
  `phone_uid` bigint(20) DEFAULT NULL COMMENT '设备归属商户',
  `orderMk` varchar(255) DEFAULT NULL COMMENT '固码备注',
  `orderNo` varchar(30) DEFAULT NULL COMMENT '系统订单号',
  `underOrderNo` varchar(50) DEFAULT NULL COMMENT '下游订单号(商户订单号)',
  `onOrderNo` varchar(191) DEFAULT NULL COMMENT '上游订单号',
  `amount` decimal(9,2) DEFAULT NULL COMMENT '订单金额',
  `orderRate` decimal(9,2) DEFAULT NULL COMMENT '订单手续费',
  `sysAmount` decimal(9,2) DEFAULT NULL COMMENT '系统收入',
  `agentAmount` decimal(9,2) DEFAULT NULL COMMENT '代理收入',
  `userAmount` decimal(9,2) DEFAULT NULL COMMENT '用户收入',
  `notifyUrl` varchar(255) DEFAULT NULL COMMENT '商户异步通知地址',
  `successUrl` varchar(255) DEFAULT NULL COMMENT '商户同步通知地址',
  `channelName` varchar(255) DEFAULT NULL COMMENT '通道名称',
  `paymentName` varchar(255) DEFAULT NULL COMMENT '支付方式名称',
  `extend` varchar(1000) DEFAULT NULL COMMENT '扩展字段,存储json',
  `status` tinyint(4) DEFAULT NULL COMMENT '订单状态：0未支付，1支付成功，2支付异常',
  `is_history` int(4) DEFAULT '0' COMMENT '是否为历史0否1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_order` */

/*Table structure for table `t_order_day_count` */

DROP TABLE IF EXISTS `t_order_day_count`;

CREATE TABLE `t_order_day_count` (
  `id` bigint(20) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `agent_id` bigint(20) DEFAULT NULL COMMENT '代理id',
  `merchant_amount` decimal(12,2) DEFAULT NULL COMMENT '商户单日订单成功金额',
  `merchant_income` decimal(12,2) DEFAULT NULL COMMENT '商户单日实际收入',
  `merchant_order_count` int(10) DEFAULT NULL COMMENT '商户单日订单量',
  `merchant_order_suc_count` int(10) DEFAULT NULL COMMENT '商户单日成功订单量',
  `sys_amount` decimal(12,2) DEFAULT NULL COMMENT '平台单日订单成功金额',
  `sys_income` decimal(12,2) DEFAULT NULL COMMENT '平台单日收入金额',
  `sys_order_count` int(10) DEFAULT NULL COMMENT '平台单日订单总数量',
  `sys_order_suc_count` int(10) DEFAULT NULL COMMENT '平台单日成功订单数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_order_day_count` */

/*Table structure for table `t_pdd_account` */

DROP TABLE IF EXISTS `t_pdd_account`;

CREATE TABLE `t_pdd_account` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `account_name` varchar(64) NOT NULL COMMENT '账号名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `phone` varchar(64) NOT NULL COMMENT '电话',
  `limit_amount` decimal(9,2) NOT NULL COMMENT '限额',
  `cookie` varchar(2048) NOT NULL COMMENT '登录后的cooki',
  `return_address_id` varchar(32) NOT NULL COMMENT '退款地址id',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '账号是否可用(0可用1禁用)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `extent` varchar(255) DEFAULT NULL COMMENT '扩展字段',
  `mark` varchar(255) DEFAULT NULL COMMENT '备注',
  `today_amount` decimal(9,2) DEFAULT '0.00' COMMENT '今日额度',
  `total_amount` decimal(9,2) DEFAULT '0.00' COMMENT '累计收益',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_pdd_account` */

/*Table structure for table `t_pdd_goods` */

DROP TABLE IF EXISTS `t_pdd_goods`;

CREATE TABLE `t_pdd_goods` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `sku_id` bigint(20) NOT NULL COMMENT '商品规格id',
  `goods_id` bigint(20) NOT NULL COMMENT '拼多多商品id',
  `group_id` varchar(20) NOT NULL COMMENT '拼团id',
  `amount` decimal(9,2) NOT NULL COMMENT '价格',
  `stock` int(10) DEFAULT NULL COMMENT '库存',
  `pdd_account_id` int(10) NOT NULL COMMENT '拼多多账号id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_pdd_goods` */

/*Table structure for table `t_pdd_order` */

DROP TABLE IF EXISTS `t_pdd_order`;

CREATE TABLE `t_pdd_order` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_croatian_ci NOT NULL,
  `order_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '拼多多订单号',
  `goods_id` int(5) NOT NULL COMMENT '商品id',
  `amount` decimal(9,2) NOT NULL COMMENT '金额',
  `status` tinyint(2) NOT NULL COMMENT '订单状态(0未请求,1未支付,2待发货/已支付,3已发货/未签收,4已签收/已到账,5异常)',
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci NOT NULL COMMENT '商户id',
  `sku_number` int(5) NOT NULL COMMENT '商品数量',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `pdd_user_id` int(5) NOT NULL COMMENT '拼多多用户id',
  `extension` varchar(1023) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '用户自定义Json',
  `access_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci NOT NULL,
  `notify_url` varchar(511) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL COMMENT '回调地址',
  `notify_times` int(4) DEFAULT '0' COMMENT '已回调次数',
  `user_order_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_croatian_ci NOT NULL COMMENT '用户订单号',
  `send_times` int(5) DEFAULT '0' COMMENT '发送次数',
  `pdd_account_id` int(10) NOT NULL COMMENT '拼多多账号id',
  `is_history` int(4) DEFAULT '0' COMMENT '是否是历史记录  0:否   1：是',
  PRIMARY KEY (`id`),
  KEY `index_pdd_order_order_sn` (`order_sn`),
  KEY `index_pdd_order_user_order_se` (`user_order_sn`),
  KEY `index_pdd_order_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_croatian_ci;

/*Data for the table `t_pdd_order` */

/*Table structure for table `t_pdd_user` */

DROP TABLE IF EXISTS `t_pdd_user`;

CREATE TABLE `t_pdd_user` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `pdduid` varchar(32) NOT NULL COMMENT '拼多多用户id',
  `address_id` bigint(20) NOT NULL COMMENT '收货地址id',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '用户状态(1可用,0不可用)',
  `accessToken` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '拼多多的accessToken',
  `extar` varchar(255) DEFAULT NULL COMMENT '扩展字段(json)',
  `mark` varchar(255) DEFAULT NULL COMMENT '备注',
  `phone` varchar(31) NOT NULL COMMENT '手机',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_pdd_user` */

/*Table structure for table `t_permission` */

DROP TABLE IF EXISTS `t_permission`;

CREATE TABLE `t_permission` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `sort_order` decimal(10,2) DEFAULT NULL,
  `component` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `button_type` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_permission` */

insert  into `t_permission`(`id`,`create_by`,`create_time`,`del_flag`,`update_by`,`update_time`,`description`,`name`,`parent_id`,`type`,`sort_order`,`component`,`path`,`title`,`icon`,`level`,`button_type`,`status`,`url`) values ('5129710648430592','','2018-06-04 19:02:29',0,'','2018-09-29 23:11:56','','sys','',0,'6.00','Main','/sys','系统管理','ios-settings',1,'',0,''),('5129710648430593','','2018-06-04 19:02:32',0,'','2018-08-13 15:15:33','','user-manage','5129710648430592',0,'1.10','sys/user-manage/userManage','user-manage','用户管理','md-person',2,'',0,''),('5129710648430594','','2018-06-04 19:02:35',0,'','2018-10-13 13:51:36','','role-manage','5129710648430592',0,'1.60','sys/role-manage/roleManage','role-manage','角色权限管理','md-contacts',2,'',0,''),('5129710648430595','','2018-06-04 19:02:37',0,'','2018-09-23 23:32:02','','menu-manage','5129710648430592',0,'1.70','sys/menu-manage/menuManage','menu-manage','菜单权限管理','md-menu',2,'',0,''),('41370251991977984',NULL,'2018-08-13 18:02:57',0,NULL,'2018-08-13 18:02:57',NULL,'quartz-job','39915540965232640',0,'2.10','sys/quartz-manage/quartzManage','quartz-job','定时任务','md-time',2,'',0,NULL),('25014528525733888','','2018-06-29 14:51:09',0,'','2018-10-08 11:13:27','','','5129710648430593',1,'1.16','','无','上传图片','',3,'upload',0,''),('39915540965232640','','2018-08-09 17:42:28',0,'','2019-03-01 18:20:14','','monitor','',0,'3.00','Main','/monitor','系统监控','ios-analytics',1,'',0,''),('39916171171991552','','2018-08-09 17:44:57',0,'admin','2019-01-20 00:37:29','','druid','39915540965232640',0,'2.40','sys/monitor/monitor','druid','SQL监控','md-analytics',2,'',0,'http://127.0.0.1:8888/druid/login.html'),('39918482854252544','','2018-08-09 17:54:08',0,'admin','2019-01-20 00:37:41','','swagger','39915540965232640',0,'2.50','sys/monitor/monitor','swagger','接口文档','md-document',2,'',0,'http://127.0.0.1:8888/swagger-ui.html'),('40238597734928384',NULL,'2018-08-10 15:06:10',0,NULL,'2018-08-10 15:06:10',NULL,'department-manage','5129710648430592',0,'1.20','sys/department-manage/departmentManage','department-manage','部门管理','md-git-branch',2,'',0,NULL),('41363147411427328','','2018-08-13 17:34:43',0,'','2018-08-20 20:05:21','','log-manage','39915540965232640',0,'2.20','sys/log-manage/logManage','log-manage','操作日志管理','md-list-box',2,'',0,''),('311c3f3360a1485396c204f3e3cb7dd0',NULL,'2019-04-22 09:53:40',NULL,NULL,NULL,NULL,'agency','0cc3ac1e78674852b0a35df668e9fdd9',0,'1.20','manage/agency','agency','代理管理','md-contacts',2,'',0,''),('0cc3ac1e78674852b0a35df668e9fdd9',NULL,'2019-04-22 09:41:29',NULL,NULL,NULL,NULL,'manage',NULL,0,'6.00','Main','/manage','业务管理','ios-settings',1,'',0,''),('ebbdba609aa7466788eaa7499eecdba3',NULL,'2019-04-22 09:47:39',NULL,NULL,NULL,NULL,'businesses','0cc3ac1e78674852b0a35df668e9fdd9',0,'1.40','manage/businesses','businesses','商户管理','md-contacts',2,'',0,''),('081dcc9a63c74337bc37a28bc75d98dd',NULL,'2019-04-26 16:43:00',NULL,NULL,NULL,NULL,'','311c3f3360a1485396c204f3e3cb7dd0',1,'1.00','','passageway','通道管理','',3,'',0,NULL),('aad1e69a699d44d4a374c114e21bbfc9',NULL,'2019-04-26 16:45:50',NULL,NULL,NULL,NULL,'passaGewayS','0cc3ac1e78674852b0a35df668e9fdd9',0,'1.00','manage/passaGewayS','passaGewayS','通道管理','md-contacts',2,'',0,''),('46c5bded14a446039ced703f76a76549',NULL,'2019-04-27 09:55:02',NULL,NULL,NULL,NULL,'payway','0cc3ac1e78674852b0a35df668e9fdd9',0,'1.00','manage/payway','payway','支付管理','md-contacts',2,'',0,''),('3d5fd2d0f473483397e85218a77daf19',NULL,'2019-04-27 11:29:41',NULL,NULL,NULL,NULL,'countumber','0cc3ac1e78674852b0a35df668e9fdd9',0,'1.00','manage/countumber','countumber','所有账号','md-contacts',2,'',0,''),('4cad062649c4445a9a4af3ed0430d576',NULL,'2019-04-27 15:17:08',NULL,NULL,NULL,NULL,'balance','0cc3ac1e78674852b0a35df668e9fdd9',0,'1.00','manage/balance','balance','结算管理','md-contacts',2,'',0,''),('4a7e9fc84ee14deabfadbe1eb553eac9',NULL,'2019-04-27 17:54:32',NULL,NULL,NULL,NULL,'charge','0cc3ac1e78674852b0a35df668e9fdd9',0,'1.00','manage/charge','charge','商户充值','md-contacts',2,'',0,''),('3b01d43245a547cd804d3960085f6cee',NULL,'2019-04-28 11:21:30',NULL,NULL,NULL,NULL,'order','0cc3ac1e78674852b0a35df668e9fdd9',0,'1.00','manage/order','order','订单管理','md-contacts',2,'',0,''),('4efb413019d64cd491f2766e8c2db977',NULL,'2019-05-15 15:38:40',NULL,NULL,NULL,NULL,'Paccounts','0cc3ac1e78674852b0a35df668e9fdd9',0,'1.00','manage/Paccounts','Paccounts','拼多多账户','md-contacts',2,'',0,''),('ec2714921500404186efc7cb9775b379',NULL,'2019-05-16 14:38:30',NULL,NULL,NULL,NULL,'applybalance','0cc3ac1e78674852b0a35df668e9fdd9',0,'1.00','manage/applybalance','applybalance','结算申请','md-contacts',2,'',0,''),('4a6b169bfa6b4408b1f43b27b3e2b31a',NULL,'2019-05-22 15:15:38',NULL,NULL,NULL,NULL,'Pmerchant','0cc3ac1e78674852b0a35df668e9fdd9',0,'1.00','manage/Pmerchant','Pmerchant','拼多多店铺账号管理','md-contacts',2,'',0,'');

/*Table structure for table `t_quartz_job` */

DROP TABLE IF EXISTS `t_quartz_job`;

CREATE TABLE `t_quartz_job` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `cron_expression` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `job_class_name` varchar(255) DEFAULT NULL,
  `parameter` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_quartz_job` */

/*Table structure for table `t_recharges` */

DROP TABLE IF EXISTS `t_recharges`;

CREATE TABLE `t_recharges` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `udpate_time` datetime DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商户id',
  `recharge_amount` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '充值金额',
  `merchant` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商户号',
  `actual_amount` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '实付金额',
  `orderNo` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '系统单号',
  `orderMk` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '固码备注',
  `pay_status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '订单状态：0未支付，1已支付，3异常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充值表';

/*Data for the table `t_recharges` */

insert  into `t_recharges`(`id`,`create_by`,`create_time`,`update_by`,`udpate_time`,`user_id`,`recharge_amount`,`merchant`,`actual_amount`,`orderNo`,`orderMk`,`pay_status`) values (1,'2019-03-18 16:28:55','2019-03-18 16:28:55',NULL,NULL,'1','-1000.00','WJh4rNfZ83','-1000.00','C20190318162855559949','总后台手动充值',1),(2,'2019-03-18 16:29:13','2019-03-18 16:29:13',NULL,NULL,'1','1000.00','WJh4rNfZ83','1000.00','C20190318162913575398','总后台手动充值',1),(3,'2019-03-18 16:29:25','2019-03-18 16:29:25',NULL,NULL,'1','100.00','WJh4rNfZ83','100.00','C20190318162925535555','总后台手动充值',1),(4,'2019-03-31 14:58:53','2019-03-31 14:58:53',NULL,NULL,'1','100.00','WJh4rNfZ83','100.00','C20190331145853100485','总后台手动充值',1),(5,'682265633886208',NULL,NULL,NULL,'682265633886208','100.00','Exrick','100.00','C201905051420008231374',NULL,1),(6,'682265633886208',NULL,NULL,NULL,'682265633886208','1000.00','Exrick','1000.00','C201905051422053245850',NULL,1),(7,'682265633886208',NULL,NULL,NULL,'682265633886208','5000.00','Exrick','5000.00','C201905051424031326433',NULL,1),(8,'682265633886208',NULL,NULL,NULL,'682265633886208','100.00','Exrick','100.00','C201905051425003605314',NULL,1),(9,'682265633886208','2019-05-05 14:26:42',NULL,NULL,'682265633886208','1000.00','Exrick','1000.00','C201905051426042747122',NULL,1),(10,'682265633886208','2019-05-05 14:28:14',NULL,NULL,'682265633886208','100.00','Exrick','100.00','C201905051428014129936',NULL,1),(11,'682265633886208','2019-05-05 14:28:31',NULL,NULL,'682265633886208','1000.00','Exrick','1000.00','C201905051428031875934',NULL,1),(12,'682265633886208','2019-05-05 14:29:18',NULL,NULL,'682265633886208','1000.00','Exrick','1000.00','C201905051429017231645',NULL,1),(13,'682265633886208','2019-05-05 14:29:28',NULL,NULL,'682265633886208','1000.00','Exrick','1000.00','C201905051429027935442',NULL,1),(14,'682265633886208','2019-05-05 14:29:30',NULL,NULL,'682265633886208','1000.00','Exrick','1000.00','C201905051429029853179',NULL,1),(15,'682265633886208','2019-05-05 14:29:47',NULL,NULL,'682265633886208','1100.00','Exrick','1100.00','C201905051429047867875',NULL,1),(16,'682265633886208','2019-05-05 14:30:25',NULL,NULL,'682265633886208','1000.00','Exrick','1000.00','C201905051430025982306',NULL,1),(17,'682265633886208','2019-05-05 14:30:39',NULL,NULL,'682265633886208','4000.00','Exrick','4000.00','C201905051430038683030',NULL,1),(18,'682265633886208','2019-05-05 14:32:10',NULL,NULL,'682265633886208','1000.00','Exrick','1000.00','C201905051432009954888',NULL,1),(19,'682265633886208','2019-05-05 14:35:01',NULL,NULL,'682265633886208','1000.00','Exrick','1000.00','C201905051435001731654',NULL,1),(20,'682265633886208','2019-05-05 14:40:50',NULL,NULL,'682265633886208','100.00','Exrick','100.00','C201905051440050950424',NULL,1),(21,'682265633886208','2019-05-05 14:41:27',NULL,NULL,'682265633886208','100.00','Exrick','100.00','C201905051441026788473',NULL,1),(22,'682265633886208','2019-05-05 14:41:40',NULL,NULL,'682265633886208','1111.00','Exrick','1111.00','C201905051441039230458',NULL,1),(23,'682265633886208','2019-05-05 14:43:43',NULL,NULL,'682265633886208','111.00','Exrick','111.00','C201905051443043414581',NULL,1);

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `default_role` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `data_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_role` */

insert  into `t_role`(`id`,`create_by`,`create_time`,`update_by`,`update_time`,`name`,`del_flag`,`default_role`,`description`,`data_type`) values ('496138616573952','','2018-04-22 23:03:49','admin','2018-11-15 23:02:59','ROLE_ADMIN',0,NULL,'超级管理员 拥有所有权限',0),('496138616573953','','2018-05-02 21:40:03','admin','2018-11-01 22:59:48','ROLE_USER',0,'','普通注册用户 路过看看',0),('16457350655250432','','2018-06-06 00:08:00','admin','2018-11-02 20:42:24','ROLE_TEST',0,NULL,'测试权限按钮显示',1);

/*Table structure for table `t_role_permission` */

DROP TABLE IF EXISTS `t_role_permission`;

CREATE TABLE `t_role_permission` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `permission_id` varchar(255) DEFAULT NULL,
  `role_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_role_permission` */

insert  into `t_role_permission`(`id`,`create_by`,`create_time`,`del_flag`,`update_by`,`update_time`,`permission_id`,`role_id`) values ('3fb2ee3cb54b4f60ba4adbb38cb46988',NULL,'2019-05-22 15:16:44',NULL,NULL,NULL,'4a6b169bfa6b4408b1f43b27b3e2b31a','496138616573952'),('d641e6c1789b42ad9d22215f3cae0145',NULL,'2019-04-12 10:52:06',NULL,NULL,NULL,'5129710648430592','16457350655250432'),('056e2cbec57b409891dc6234b3a484c9',NULL,'2019-04-12 10:52:06',NULL,NULL,NULL,'39915540965232640','16457350655250432'),('220dd2991bb4406396c62c6274224850',NULL,'2019-04-12 10:52:06',NULL,NULL,NULL,'41370251991977984','16457350655250432'),('40a46984e18d49459fdefc544b6e3376',NULL,'2019-04-12 10:52:06',NULL,NULL,NULL,'41363147411427328','16457350655250432'),('a3fafda7b9e740b694483df08290fd26',NULL,'2019-05-22 15:16:44',NULL,NULL,NULL,'ec2714921500404186efc7cb9775b379','496138616573952'),('51a123f33f8240deb5924a8c2eb582a3',NULL,'2019-05-22 15:16:44',NULL,NULL,NULL,'4efb413019d64cd491f2766e8c2db977','496138616573952'),('24c394e9fa8d46d3a812e4d26c062b0c',NULL,'2019-05-22 15:16:44',NULL,NULL,NULL,'3b01d43245a547cd804d3960085f6cee','496138616573952'),('a4c9e40ae89c4a3b8f283057df524fbb',NULL,'2019-05-22 15:16:44',NULL,NULL,NULL,'4a7e9fc84ee14deabfadbe1eb553eac9','496138616573952'),('86fe18d53aba4742b3bf240be6ddb846',NULL,'2019-05-22 15:16:44',NULL,NULL,NULL,'4cad062649c4445a9a4af3ed0430d576','496138616573952'),('9bc647607e314a33ad58a18b467816ac',NULL,'2019-04-19 16:53:07',NULL,NULL,NULL,'25014528525733888','666666666666666'),('6243567801774e908927aea2c9618baa',NULL,'2019-05-22 15:16:44',NULL,NULL,NULL,'3d5fd2d0f473483397e85218a77daf19','496138616573952'),('97fc6ca8d1e34ea28bbb6199b9a8b910',NULL,'2019-05-22 15:16:44',NULL,NULL,NULL,'46c5bded14a446039ced703f76a76549','496138616573952'),('0d6c5ae3e2dd47f1977311aef1876c02',NULL,'2019-05-22 15:16:44',NULL,NULL,NULL,'aad1e69a699d44d4a374c114e21bbfc9','496138616573952'),('198762787b634316acb265a8ec4cc26b',NULL,'2019-05-22 15:16:44',NULL,NULL,NULL,'ebbdba609aa7466788eaa7499eecdba3','496138616573952'),('46b902d08973441d8346ad78b0c211df',NULL,'2019-05-22 15:16:44',NULL,NULL,NULL,'311c3f3360a1485396c204f3e3cb7dd0','496138616573952'),('c422a24a93c0400c8af4a8526c10da09',NULL,'2019-05-22 15:16:44',NULL,NULL,NULL,'081dcc9a63c74337bc37a28bc75d98dd','496138616573952'),('31a70fb5f33749b6a05648f9488e2160',NULL,'2019-05-22 15:16:44',NULL,NULL,NULL,'0cc3ac1e78674852b0a35df668e9fdd9','496138616573952'),('d1204f15cf874cab9b19bd71ab117923',NULL,'2019-05-22 15:16:44',NULL,NULL,NULL,'41363147411427328','496138616573952'),('75d88a18d36f433e95c5677fd34c7c5c',NULL,'2019-05-22 15:16:44',NULL,NULL,NULL,'41370251991977984','496138616573952'),('1ffed15c05eb4168b5c0b5ba36c4abfd',NULL,'2019-05-22 15:16:44',NULL,NULL,NULL,'39915540965232640','496138616573952'),('0b84aa95bb3642baa1163f68526812af',NULL,'2019-05-22 15:16:44',NULL,NULL,NULL,'5129710648430595','496138616573952'),('48dbf11aa00f4f30a6bd157810606158',NULL,'2019-05-22 15:16:44',NULL,NULL,NULL,'5129710648430594','496138616573952'),('9b1ee846d2e24c43b6fa3ff0b5d14c2b',NULL,'2019-05-22 15:16:44',NULL,NULL,NULL,'5129710648430593','496138616573952'),('e0072303e789460a82b35582b0aafed2',NULL,'2019-05-22 15:16:44',NULL,NULL,NULL,'5129710648430592','496138616573952'),('d4cdc3950a0f4fbfb60514e7f590acf1',NULL,'2019-05-20 15:15:40',NULL,NULL,NULL,'ec2714921500404186efc7cb9775b379','496138616573953'),('71bffcee3f364de8be2bf7c48f8f0c10',NULL,'2019-05-20 15:15:40',NULL,NULL,NULL,'3b01d43245a547cd804d3960085f6cee','496138616573953'),('da13789e050642fd990fe875f8777aae',NULL,'2019-05-20 15:15:40',NULL,NULL,NULL,'0cc3ac1e78674852b0a35df668e9fdd9','496138616573953'),('c14d75acf0ae48e2b55754a930e87cfe',NULL,'2019-05-20 15:15:40',NULL,NULL,NULL,'5129710648430592','496138616573953');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `avatar` varchar(1000) DEFAULT NULL COMMENT '头像',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号',
  `merchant` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商户号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `sex` int(11) DEFAULT NULL COMMENT '性别',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `del_flag` int(11) DEFAULT NULL COMMENT '删除标记',
  `payPassword` varchar(255) DEFAULT NULL COMMENT '支付密码',
  `parent_id` varchar(255) DEFAULT NULL COMMENT '上级id',
  `rate` decimal(9,6) DEFAULT NULL COMMENT '费率',
  `sign_key` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`create_by`,`create_time`,`update_by`,`update_time`,`address`,`avatar`,`description`,`email`,`mobile`,`merchant`,`password`,`sex`,`status`,`type`,`username`,`del_flag`,`payPassword`,`parent_id`,`rate`,`sign_key`) values ('682265633886208','','2018-05-01 16:13:51','admin','2018-11-14 21:52:20','[\"510000\",\"510100\",\"510104\"]','https://s1.ax1x.com/2018/05/19/CcdVQP.png','test','2549575805@qq.com','18782059038','Exrick','$2a$10$PS04ecXfknNd3V8d.ymLTObQciapMU4xU8.GADBZZsuTZr7ymnagy',1,0,1,'admin',0,'000000',NULL,NULL,NULL),('47c6dc15065343df92ffeee9ccfbfb45','682265633886208','2019-05-29 14:58:17',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'201905291458017269315','$2a$10$5p7aXm6b0GC3SMqutIydU.RmIr/K1PRppEPSUQawnxJltq2TjVP1K',NULL,0,NULL,'cy123456',NULL,NULL,'682265633886208','0.030000',NULL),('f90d70642cb44d47af489d64f2faefe9','682265633886208','2019-05-30 12:28:13',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'201905301228012836950','$2a$10$KHMHjxy/rYGp8CgZcqFZQudGmm5/i4XGbTzBoXDJNrmbe3ktI9dk6',NULL,0,NULL,'cy222333',NULL,NULL,'682265633886208','0.033000',NULL),('37e90a0221e541dfa0f8d9513a2b31a5','682265633886208','2019-05-30 15:57:34',NULL,NULL,NULL,NULL,NULL,'ceshi001@163.com','13011111111','201905301557033559400','$2a$10$iXM5ysu/abMU7zn9c7myqO7EpqKkiuveuivR3VSgY3.kW1Wn2.R.u',NULL,0,NULL,'201905301557033559400',NULL,NULL,'682265633886208','0.030000',NULL),('f3e5d4a2b94c4961a83afcc6466a4867','682265633886208','2019-05-30 15:58:47',NULL,NULL,NULL,NULL,NULL,'ceshi001@163.com','13011111111','201905301558047967216','$2a$10$Ks6Ve6Zrlz.9OVv6s.6KSOD/KQnoiG9rkVHqpvdx0jvBZtvnttO2W',NULL,0,NULL,'ceshi001',NULL,NULL,'682265633886208',NULL,NULL),('b744c5fd57d84a86995ac2f9b4c19e52','682265633886208','2019-05-29 12:16:46',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'201905291216045546571','$2a$10$V2qCc1Wocmww8NO99FM6C.VQVUAxoJ.gbl5Rf1XZ5JyPCeNBjZc/W',NULL,0,NULL,'SZPDD',NULL,NULL,'682265633886208','0.030000',NULL),('2d7b48d58a574fc1b6b874a930829a62','682265633886208','2019-05-16 18:41:57',NULL,NULL,NULL,NULL,NULL,'1111','1111111','201905161841057626944','$2a$10$l6D/fePWH3PfFIAHhLnDruw0kt8I8Kx2HaaiZXTvW8AHq1Ie8R0WC',NULL,0,NULL,'pdd',NULL,NULL,'682265633886208','0.060000','1cc3352776504514ad4d5ae7be327c22'),('1cc3352776504514ad4d5ae7be327c22','682265633886208','2019-05-30 23:30:33',NULL,NULL,NULL,NULL,NULL,'aaaa','123456','201905302330033520884','$2a$10$aVFZMoWP8dwrzmhApbJqiuLW0D7lcmQ8ceeEeldLBIPKRQ1LX2hCy',NULL,0,NULL,'aaa',NULL,NULL,'682265633886208','0.060000',NULL),('097e2208234f4c678926e0e7a5df05ff','682265633886208','2019-05-30 23:30:59',NULL,NULL,NULL,NULL,NULL,'aaaa','123456','201905302330059240386','$2a$10$bzDtcsIXNsquFQd8dl6fl.dxtnyJVeqP.M2oJt43LpT6OewyPPYcm',NULL,0,NULL,'aaa1',NULL,NULL,'682265633886208','0.060000',NULL),('173aaf69c29c4c048c4af0512a8900c9','682265633886208','2019-05-30 23:31:02',NULL,NULL,NULL,NULL,NULL,'aaaa','123456','201905302331001992738','$2a$10$cUxZscSJf4Omw2dHTsxefedvy0S5d6uRfOc7F10DTex./gTr4RVVy',NULL,0,NULL,'aaa12',NULL,NULL,'682265633886208','0.060000',NULL),('a8799a839b0d44b9a727721af40d8fa7','682265633886208','2019-05-30 23:31:07',NULL,NULL,NULL,NULL,NULL,'aaaa','123456','201905302331006652290','$2a$10$DX0Hx0XtPY4kOTQZFcAAEuDS6A5vevVOXgcIG18OAVX0L7bqS1M.G',NULL,0,NULL,'aaa123',NULL,NULL,'682265633886208','0.060000',NULL),('b3a3439477bd4dfba16ecbbe806996e3','682265633886208','2019-05-30 23:31:10',NULL,NULL,NULL,NULL,NULL,'aaaa','123456','201905302331009259385','$2a$10$EzXKCkE3MmnIty2FBmIlC.6SaIgMZyLdOufj8Q1eD1qN4iv62BlJ2',NULL,0,NULL,'aaa1231',NULL,NULL,'682265633886208','0.060000',NULL),('6c4269bd260541f787fcbc2da35016d1','682265633886208','2019-05-30 23:31:12',NULL,NULL,NULL,NULL,NULL,'aaaa','123456','201905302331012470148','$2a$10$M5E2BNdGLHp7d3uBtJdqwOY6IawjWpxDqLnjYEVpYDlv2W0A456rO',NULL,0,NULL,'aaa12312',NULL,NULL,'682265633886208','0.060000',NULL),('77fc725ddd87423f9f735a634a8df0eb','682265633886208','2019-05-30 23:31:15',NULL,NULL,NULL,NULL,NULL,'aaaa','123456','201905302331014459606','$2a$10$DFgJ2A9E4cc8ZWK1QsG2d.THQp3qiItcJNJYEYngSc6fo5OdRYIA.',NULL,0,NULL,'aaa123123',NULL,NULL,'682265633886208','0.060000',NULL),('68bbe00e29054abe985dc9703fea5573','682265633886208','2019-05-30 23:32:04',NULL,NULL,NULL,NULL,NULL,'11','1234563','201905302332004623284','$2a$10$f7sxARbP1RfrHdu22XzdiOjjwAEX2XDxn3NGgQqAmX8sISsCsDbVa',NULL,0,NULL,'111',NULL,NULL,'682265633886208','1.000000',NULL);

/*Table structure for table `t_user_rates` */

DROP TABLE IF EXISTS `t_user_rates`;

CREATE TABLE `t_user_rates` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户id',
  `channel_id` int(10) unsigned NOT NULL COMMENT '通道id',
  `channel_payment_id` int(10) unsigned NOT NULL COMMENT '支付id',
  `rate` decimal(9,6) NOT NULL DEFAULT '0.000000' COMMENT '商户费率：为0时走通道运营费率',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '状态：0禁用。1启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商户费率表';

/*Data for the table `t_user_rates` */

/*Table structure for table `t_user_role` */

DROP TABLE IF EXISTS `t_user_role`;

CREATE TABLE `t_user_role` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  `role_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_user_role` */

insert  into `t_user_role`(`id`,`create_by`,`create_time`,`update_by`,`update_time`,`del_flag`,`role_id`,`user_id`) values ('70669807116095488',NULL,'2018-11-02 14:28:56',NULL,'2018-11-02 14:28:56',0,'496138616573952','682265633886208'),('98ac97c317524842a778e004b5b0b921',NULL,NULL,NULL,NULL,NULL,'496138616573952',' 1'),('459117729e6e47aea6c9afd6ae3c6404',NULL,NULL,NULL,NULL,NULL,'496138616573953','f90d70642cb44d47af489d64f2faefe9'),('1cc74734a6034ff19f8d33006e666760',NULL,NULL,NULL,NULL,NULL,'496138616573953','2d7b48d58a574fc1b6b874a930829a62'),('f5345d53be1442c1a6be58660f1b0544',NULL,NULL,NULL,NULL,NULL,'496138616573953','47c6dc15065343df92ffeee9ccfbfb45'),('9bb47e7cb94b46abb0ad63422c73d219',NULL,NULL,NULL,NULL,NULL,'496138616573953','b744c5fd57d84a86995ac2f9b4c19e52'),('ceec0a4b5489455481a4062e637f2eba',NULL,NULL,NULL,NULL,NULL,'2','f3e5d4a2b94c4961a83afcc6466a4867'),('0e4c93605dca4c549945c26c341d03bb',NULL,NULL,NULL,NULL,NULL,'496138616573953','1cc3352776504514ad4d5ae7be327c22'),('397e688e5e2f4c8c8bb0777e577ece05',NULL,NULL,NULL,NULL,NULL,'496138616573953','097e2208234f4c678926e0e7a5df05ff'),('2e33f724f575439f9513819eb30d6f57',NULL,NULL,NULL,NULL,NULL,'496138616573953','173aaf69c29c4c048c4af0512a8900c9'),('51e3ed64ee35489790c21746ea62e577',NULL,NULL,NULL,NULL,NULL,'496138616573953','a8799a839b0d44b9a727721af40d8fa7'),('0c0d68bd972648e78386983dbdd65b6d',NULL,NULL,NULL,NULL,NULL,'496138616573953','b3a3439477bd4dfba16ecbbe806996e3'),('a4a03ca9ecd44d908d31da82cfe3508a',NULL,NULL,NULL,NULL,NULL,'496138616573953','6c4269bd260541f787fcbc2da35016d1'),('6e95922be14c4754ac82848070884a66',NULL,NULL,NULL,NULL,NULL,'496138616573953','77fc725ddd87423f9f735a634a8df0eb'),('3c14ed83a2f8463da8f421a15dfc4b1a',NULL,NULL,NULL,NULL,NULL,'496138616573953','68bbe00e29054abe985dc9703fea5573');

/*Table structure for table `t_wallet` */

DROP TABLE IF EXISTS `t_wallet`;

CREATE TABLE `t_wallet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除标记',
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户id',
  `residual_amount` double(32,2) DEFAULT '0.00' COMMENT '余额',
  `type` char(1) DEFAULT NULL COMMENT '类型(1充值,2收益)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

/*Data for the table `t_wallet` */

insert  into `t_wallet`(`id`,`create_by`,`create_time`,`update_by`,`update_time`,`del_flag`,`user_id`,`residual_amount`,`type`) values (1,NULL,NULL,NULL,'2019-05-31 00:04:25',NULL,'682265633886208',0.00,'1'),(2,NULL,NULL,NULL,NULL,NULL,'682265633886208',0.00,'2'),(11,NULL,NULL,NULL,'2019-05-31 00:04:28',NULL,'11',0.00,'1'),(12,NULL,NULL,NULL,'2019-05-31 00:04:29',NULL,'11',0.00,'2'),(13,'682265633886208','2019-05-16 18:41:57',NULL,'2019-05-16 18:55:29',NULL,'2d7b48d58a574fc1b6b874a930829a62',230.00,'1'),(14,'682265633886208','2019-05-16 18:41:57',NULL,'2019-06-03 11:04:37',NULL,'2d7b48d58a574fc1b6b874a930829a62',9895.00,'2'),(15,'682265633886208','2019-05-29 12:16:46',NULL,'2019-05-29 16:46:37',NULL,'b744c5fd57d84a86995ac2f9b4c19e52',0.00,'1'),(16,'682265633886208','2019-05-29 12:16:46',NULL,'2019-05-31 01:14:22',NULL,'b744c5fd57d84a86995ac2f9b4c19e52',5130.16,'2'),(17,'682265633886208','2019-05-29 14:58:17',NULL,'2019-05-29 16:46:40',NULL,'47c6dc15065343df92ffeee9ccfbfb45',0.00,'1'),(18,'682265633886208','2019-05-29 14:58:17',NULL,'2019-05-31 01:21:17',NULL,'47c6dc15065343df92ffeee9ccfbfb45',407254.50,'2'),(19,'682265633886208','2019-05-30 12:28:13',NULL,'2019-05-30 12:28:53',NULL,'f90d70642cb44d47af489d64f2faefe9',0.00,'1'),(20,'682265633886208','2019-05-30 12:28:13',NULL,'2019-05-31 01:21:20',NULL,'f90d70642cb44d47af489d64f2faefe9',308956.50,'2'),(21,'682265633886208','2019-05-30 15:57:34',NULL,NULL,NULL,'37e90a0221e541dfa0f8d9513a2b31a5',0.00,'1'),(22,'682265633886208','2019-05-30 15:57:34',NULL,NULL,NULL,'37e90a0221e541dfa0f8d9513a2b31a5',0.00,'2'),(23,'682265633886208','2019-05-30 15:58:47',NULL,NULL,NULL,'f3e5d4a2b94c4961a83afcc6466a4867',0.00,'1'),(24,'682265633886208','2019-05-30 15:58:47',NULL,NULL,NULL,'f3e5d4a2b94c4961a83afcc6466a4867',0.00,'2'),(25,'682265633886208','2019-05-30 23:30:33',NULL,NULL,NULL,'1cc3352776504514ad4d5ae7be327c22',0.00,'1'),(26,'682265633886208','2019-05-30 23:30:33',NULL,NULL,NULL,'1cc3352776504514ad4d5ae7be327c22',0.00,'2'),(27,'682265633886208','2019-05-30 23:30:59',NULL,NULL,NULL,'097e2208234f4c678926e0e7a5df05ff',0.00,'1'),(28,'682265633886208','2019-05-30 23:30:59',NULL,NULL,NULL,'097e2208234f4c678926e0e7a5df05ff',0.00,'2'),(29,'682265633886208','2019-05-30 23:31:02',NULL,NULL,NULL,'173aaf69c29c4c048c4af0512a8900c9',0.00,'1'),(30,'682265633886208','2019-05-30 23:31:02',NULL,NULL,NULL,'173aaf69c29c4c048c4af0512a8900c9',0.00,'2'),(31,'682265633886208','2019-05-30 23:31:07',NULL,NULL,NULL,'a8799a839b0d44b9a727721af40d8fa7',0.00,'1'),(32,'682265633886208','2019-05-30 23:31:07',NULL,NULL,NULL,'a8799a839b0d44b9a727721af40d8fa7',0.00,'2'),(33,'682265633886208','2019-05-30 23:31:10',NULL,NULL,NULL,'b3a3439477bd4dfba16ecbbe806996e3',0.00,'1'),(34,'682265633886208','2019-05-30 23:31:10',NULL,NULL,NULL,'b3a3439477bd4dfba16ecbbe806996e3',0.00,'2'),(35,'682265633886208','2019-05-30 23:31:12',NULL,NULL,NULL,'6c4269bd260541f787fcbc2da35016d1',0.00,'1'),(36,'682265633886208','2019-05-30 23:31:12',NULL,NULL,NULL,'6c4269bd260541f787fcbc2da35016d1',0.00,'2'),(37,'682265633886208','2019-05-30 23:31:15',NULL,NULL,NULL,'77fc725ddd87423f9f735a634a8df0eb',0.00,'1'),(38,'682265633886208','2019-05-30 23:31:15',NULL,NULL,NULL,'77fc725ddd87423f9f735a634a8df0eb',0.00,'2'),(39,'682265633886208','2019-05-30 23:32:04',NULL,NULL,NULL,'68bbe00e29054abe985dc9703fea5573',0.00,'1'),(40,'682265633886208','2019-05-30 23:32:04',NULL,NULL,NULL,'68bbe00e29054abe985dc9703fea5573',0.00,'2');

/*Table structure for table `t_wallet_history` */

DROP TABLE IF EXISTS `t_wallet_history`;

CREATE TABLE `t_wallet_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除标记',
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户id',
  `residual_amount` double(32,2) DEFAULT '0.00' COMMENT '余额',
  `type` char(1) DEFAULT NULL COMMENT '类型(1充值,2收益)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

/*Data for the table `t_wallet_history` */

insert  into `t_wallet_history`(`id`,`create_by`,`create_time`,`update_by`,`update_time`,`del_flag`,`user_id`,`residual_amount`,`type`) values (1,NULL,NULL,NULL,'2019-05-31 00:04:25',NULL,'682265633886208',0.00,'1'),(2,NULL,NULL,NULL,NULL,NULL,'682265633886208',0.00,'2'),(11,NULL,NULL,NULL,'2019-05-31 00:04:28',NULL,'11',0.00,'1'),(12,NULL,NULL,NULL,'2019-05-31 00:04:29',NULL,'11',0.00,'2'),(13,'682265633886208','2019-05-16 18:41:57',NULL,'2019-05-16 18:55:29',NULL,'2d7b48d58a574fc1b6b874a930829a62',0.00,'1'),(14,'682265633886208','2019-05-16 18:41:57',NULL,'2019-05-31 01:14:24',NULL,'2d7b48d58a574fc1b6b874a930829a62',0.00,'2'),(15,'682265633886208','2019-05-29 12:16:46',NULL,'2019-05-29 16:46:37',NULL,'b744c5fd57d84a86995ac2f9b4c19e52',0.00,'1'),(16,'682265633886208','2019-05-29 12:16:46',NULL,'2019-05-31 01:14:22',NULL,'b744c5fd57d84a86995ac2f9b4c19e52',5130.16,'2'),(17,'682265633886208','2019-05-29 14:58:17',NULL,'2019-05-29 16:46:40',NULL,'47c6dc15065343df92ffeee9ccfbfb45',0.00,'1'),(18,'682265633886208','2019-05-29 14:58:17',NULL,'2019-05-31 01:21:17',NULL,'47c6dc15065343df92ffeee9ccfbfb45',407254.50,'2'),(19,'682265633886208','2019-05-30 12:28:13',NULL,'2019-05-30 12:28:53',NULL,'f90d70642cb44d47af489d64f2faefe9',0.00,'1'),(20,'682265633886208','2019-05-30 12:28:13',NULL,'2019-05-31 01:21:20',NULL,'f90d70642cb44d47af489d64f2faefe9',308956.50,'2'),(21,'682265633886208','2019-05-30 15:57:34',NULL,NULL,NULL,'37e90a0221e541dfa0f8d9513a2b31a5',0.00,'1'),(22,'682265633886208','2019-05-30 15:57:34',NULL,NULL,NULL,'37e90a0221e541dfa0f8d9513a2b31a5',0.00,'2'),(23,'682265633886208','2019-05-30 15:58:47',NULL,NULL,NULL,'f3e5d4a2b94c4961a83afcc6466a4867',0.00,'1'),(24,'682265633886208','2019-05-30 15:58:47',NULL,NULL,NULL,'f3e5d4a2b94c4961a83afcc6466a4867',0.00,'2'),(25,'682265633886208','2019-05-30 23:30:33',NULL,NULL,NULL,'1cc3352776504514ad4d5ae7be327c22',0.00,'1'),(26,'682265633886208','2019-05-30 23:30:33',NULL,NULL,NULL,'1cc3352776504514ad4d5ae7be327c22',0.00,'2'),(27,'682265633886208','2019-05-30 23:30:59',NULL,NULL,NULL,'097e2208234f4c678926e0e7a5df05ff',0.00,'1'),(28,'682265633886208','2019-05-30 23:30:59',NULL,NULL,NULL,'097e2208234f4c678926e0e7a5df05ff',0.00,'2'),(29,'682265633886208','2019-05-30 23:31:02',NULL,NULL,NULL,'173aaf69c29c4c048c4af0512a8900c9',0.00,'1'),(30,'682265633886208','2019-05-30 23:31:02',NULL,NULL,NULL,'173aaf69c29c4c048c4af0512a8900c9',0.00,'2'),(31,'682265633886208','2019-05-30 23:31:07',NULL,NULL,NULL,'a8799a839b0d44b9a727721af40d8fa7',0.00,'1'),(32,'682265633886208','2019-05-30 23:31:07',NULL,NULL,NULL,'a8799a839b0d44b9a727721af40d8fa7',0.00,'2'),(33,'682265633886208','2019-05-30 23:31:10',NULL,NULL,NULL,'b3a3439477bd4dfba16ecbbe806996e3',0.00,'1'),(34,'682265633886208','2019-05-30 23:31:10',NULL,NULL,NULL,'b3a3439477bd4dfba16ecbbe806996e3',0.00,'2'),(35,'682265633886208','2019-05-30 23:31:12',NULL,NULL,NULL,'6c4269bd260541f787fcbc2da35016d1',0.00,'1'),(36,'682265633886208','2019-05-30 23:31:12',NULL,NULL,NULL,'6c4269bd260541f787fcbc2da35016d1',0.00,'2'),(37,'682265633886208','2019-05-30 23:31:15',NULL,NULL,NULL,'77fc725ddd87423f9f735a634a8df0eb',0.00,'1'),(38,'682265633886208','2019-05-30 23:31:15',NULL,NULL,NULL,'77fc725ddd87423f9f735a634a8df0eb',0.00,'2'),(39,'682265633886208','2019-05-30 23:32:04',NULL,NULL,NULL,'68bbe00e29054abe985dc9703fea5573',0.00,'1'),(40,'682265633886208','2019-05-30 23:32:04',NULL,NULL,NULL,'68bbe00e29054abe985dc9703fea5573',0.00,'2');

/*Table structure for table `t_withdraws` */

DROP TABLE IF EXISTS `t_withdraws`;

CREATE TABLE `t_withdraws` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商户id',
  `agent_id` int(10) DEFAULT NULL COMMENT '商户所属代理id',
  `bankName` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '银行名称',
  `withdrawAmount` double(11,2) NOT NULL DEFAULT '0.00' COMMENT '提现金额',
  `withdrawRate` double(6,2) DEFAULT '0.00' COMMENT '提现手续费',
  `toAmount` double(11,2) NOT NULL DEFAULT '0.00' COMMENT '到账金额',
  `accountName` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '银行开户名',
  `bankCardNo` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '银行卡号',
  `branchName` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '银行支行名',
  `bankCode` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '银行编码',
  `orderId` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结算单流水号',
  `outOrderId` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商户侧提交结算单号',
  `upOrderId` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '上游结算单号',
  `channelCode` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结算通道编码',
  `type` tinyint(3) DEFAULT '1' COMMENT '结算类型:1普通结算 2代付结算',
  `comment` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结算备注信息',
  `extend` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '扩展银行卡信息,json格式',
  `status` tinyint(3) DEFAULT '0' COMMENT '体现状态：0未处理，1处理中，2已结算，3结算异常,4取消结算',
  PRIMARY KEY (`id`),
  UNIQUE KEY `withdraws_orderid_unique` (`orderId`),
  UNIQUE KEY `withdraws_uporderid_unique` (`upOrderId`),
  KEY `withdraws_outorderid_index` (`outOrderId`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='提现记录表';

/*Data for the table `t_withdraws` */

insert  into `t_withdraws`(`id`,`create_time`,`create_by`,`update_time`,`update_by`,`user_id`,`agent_id`,`bankName`,`withdrawAmount`,`withdrawRate`,`toAmount`,`accountName`,`bankCardNo`,`branchName`,`bankCode`,`orderId`,`outOrderId`,`upOrderId`,`channelCode`,`type`,`comment`,`extend`,`status`) values (1,'2019-03-23 21:14:05',NULL,'2019-03-23 21:32:01',NULL,'14',6,'中国建设银行',47890.00,1.00,47889.00,'王铜','6217003760022374748','重庆市建设银行','CCB','W2019032321140511787',NULL,NULL,'',1,'1',NULL,2),(2,'2019-03-24 03:22:34',NULL,'2019-03-24 16:22:49',NULL,'14',6,'中国建设银行',23456.00,1.00,23455.00,'王铜','6217003760022374748','重庆市建设银行','CCB','W2019032403223441254',NULL,NULL,'',1,'121121',NULL,2),(3,'2019-03-24 12:27:10',NULL,'2019-03-24 16:22:58',NULL,'14',6,'中国建设银行',12345.00,1.00,12344.00,'王铜','6217003760022374748','重庆市建设银行','CCB','W2019032412271043174',NULL,NULL,'',1,'1',NULL,2),(4,'2019-03-27 14:03:49',NULL,'2019-03-27 14:54:57',NULL,'5',1,'中国农业银行',5500.00,1.00,5499.00,'吕帅','6230520450066976775','邳州支行','ABC','W2019032714034962735',NULL,NULL,'',1,'111',NULL,2),(5,'2019-03-28 16:53:03',NULL,'2019-03-28 17:03:15',NULL,'8',6,'中国农业银行',1067.00,1.00,1066.00,'汪松柏','6230522300015459670','安庆宿松县支行','ABC','W2019032816530319007',NULL,NULL,'',1,'111',NULL,2),(6,'2019-03-28 17:07:01',NULL,'2019-03-28 17:08:56',NULL,'9',6,'中国农业银行',247.00,1.00,246.00,'汪松柏','6230522300015459670','安庆宿松县支行','ABC','W2019032817070147632',NULL,NULL,'',1,'000',NULL,2),(7,'2019-04-01 20:53:24',NULL,'2019-04-01 21:02:38',NULL,'15',6,'中国农业银行',12403.00,1.00,12402.00,'陈科宇','6228484088841471270','贵港荷城分理处','ABC','W2019040120532448693',NULL,NULL,'',1,'0000',NULL,2),(8,'2019-04-03 23:03:00',NULL,'2019-04-03 23:57:09',NULL,'5',1,'中国农业银行',10000.00,1.00,9999.00,'钟思佳','6228480086949236979','广东分行','ABC','W2019040323030070610',NULL,NULL,'',1,'000',NULL,2),(9,'2019-04-04 13:05:55',NULL,'2019-04-04 13:06:14',NULL,'2',1,'中国建设银行',100.00,5.00,95.00,'是是是','622171150122479','建行','CCB','W2019040413055590833',NULL,NULL,'',1,'000',NULL,2),(10,'2019-04-04 13:46:57',NULL,'2019-04-04 13:48:08',NULL,'18',1,'中国民生银行',1000.00,5.00,995.00,'卓保辉','6216912309002502','泉州清濛支行','CMBC','W2019040413465743063',NULL,NULL,'',1,'00',NULL,2),(11,'2019-04-04 22:32:10',NULL,'2019-04-04 23:21:06',NULL,'5',1,'中国农业银行',7000.00,5.00,6995.00,'钟思佳','6228480086949236979','广东分行','ABC','W2019040422321034249',NULL,NULL,'',1,'000',NULL,2),(12,'2019-04-05 01:26:11',NULL,'2019-04-05 01:32:51',NULL,'15',6,'中国农业银行',28755.00,5.00,28750.00,'陈科宇','6228484088841471270','贵港荷城分理处','ABC','W2019040501261146659',NULL,NULL,'',1,'000',NULL,4),(13,'2019-04-05 13:25:49',NULL,'2019-04-05 13:29:02',NULL,'15',6,'中国农业银行',28755.00,5.00,28750.00,'陈科宇','6228484088841471270','贵港荷城分理处','ABC','W2019040513254932189',NULL,NULL,'',1,'00',NULL,2),(14,'2019-04-05 23:57:08',NULL,'2019-04-06 00:21:38',NULL,'5',1,'中国农业银行',5900.00,5.00,5895.00,'吴敬龙','6228481319026837578','枣庄分行','ABC','W2019040523570888851',NULL,NULL,'',1,'000',NULL,4),(15,'2019-04-06 11:10:48',NULL,'2019-04-06 13:19:34',NULL,'5',1,'中国农业银行',11000.00,5.00,10995.00,'吴敬龙','6228481319026837578','枣庄分行','ABC','W2019040611104845035',NULL,NULL,'',1,'0000',NULL,2),(16,'2019-04-07 14:11:42',NULL,'2019-04-07 14:17:18',NULL,'5',1,'中国农业银行',12001.00,5.00,11996.00,'高密市孜金建筑材料有限公司','15452201040002625','中国农业银行潍坊市分行','ABC','W2019040714114261326',NULL,NULL,'',1,'00000',NULL,2),(17,'2019-04-07 15:25:45',NULL,'2019-04-07 15:36:03',NULL,'5',1,'中国农业银行',543.00,5.00,538.00,'高密市孜金建筑材料有限公司','15452201040002625','中国农业银行潍坊市分行','ABC','W2019040715254518560',NULL,NULL,'',1,'000',NULL,2),(18,'2019-04-07 15:26:15',NULL,'2019-04-07 15:36:11',NULL,'5',1,'中国农业银行',645.00,5.00,640.00,'高密市孜金建筑材料有限公司','15452201040002625','中国农业银行潍坊市分行','ABC','W2019040715261596013',NULL,NULL,'',1,'000',NULL,2),(19,'2019-04-07 15:40:23',NULL,'2019-04-07 15:44:15',NULL,'19',1,'中国农业银行',2703.00,5.00,2698.00,'唐志兰','6228480148848561277','广西壮族自治区分行','ABC','W2019040715402382439',NULL,NULL,'',1,'00',NULL,2),(20,'2019-04-08 20:45:41',NULL,'2019-04-08 21:02:59',NULL,'15',6,'中国农业银行',30000.00,5.00,29995.00,'陈科宇','6228484088841471270','贵港荷城分理处','ABC','W2019040820454144112',NULL,NULL,'',1,'000',NULL,2),(21,'2019-04-09 11:23:57',NULL,'2019-04-09 11:41:48',NULL,'15',6,'中国农业银行',35000.00,5.00,34995.00,'陈科宇','6228484088841471270','贵港荷城分理处','ABC','W2019040911235770861',NULL,NULL,'',1,'000',NULL,2),(22,'2019-04-10 11:36:30',NULL,'2019-04-10 11:54:25',NULL,'15',6,'中国农业银行',37055.00,5.00,37050.00,'陈科宇','6228484088841471270','贵港荷城分理处','ABC','W2019041011363018594',NULL,NULL,'',1,'000',NULL,2),(23,'2019-04-11 12:04:27',NULL,'2019-04-11 12:06:58',NULL,'15',6,'中国农业银行',29058.00,5.00,29053.00,'陈科宇','6228484088841471270','贵港荷城分理处','ABC','W2019041112042761882',NULL,NULL,'',1,'0000',NULL,2),(24,'2019-05-20 18:32:23','682265633886208',NULL,NULL,'682265633886208',NULL,'中国农业银行',10.00,5.00,5.00,NULL,'456789',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2),(25,'2019-05-20 18:44:12','2d7b48d58a574fc1b6b874a930829a62',NULL,NULL,'2d7b48d58a574fc1b6b874a930829a62',NULL,'中国农业银行',190.00,5.00,185.00,NULL,'456789',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2),(26,'2019-05-20 18:52:44','2d7b48d58a574fc1b6b874a930829a62',NULL,NULL,'2d7b48d58a574fc1b6b874a930829a62',NULL,'中国农业银行',190.00,5.00,185.00,NULL,'456789',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2),(27,'2019-05-20 18:55:06','2d7b48d58a574fc1b6b874a930829a62',NULL,NULL,'2d7b48d58a574fc1b6b874a930829a62',NULL,'中国农业银行',1.00,5.00,-4.00,NULL,'456789',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(28,'2019-05-20 19:04:27','2d7b48d58a574fc1b6b874a930829a62',NULL,NULL,'2d7b48d58a574fc1b6b874a930829a62',NULL,'北京银行',0.10,5.00,-4.90,NULL,'66666666666644444',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(29,'2019-05-20 19:12:02','2d7b48d58a574fc1b6b874a930829a62',NULL,NULL,'2d7b48d58a574fc1b6b874a930829a62',NULL,'北京银行',0.10,5.00,-4.90,NULL,'66666666666644444',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(30,'2019-05-20 19:12:13','2d7b48d58a574fc1b6b874a930829a62',NULL,NULL,'2d7b48d58a574fc1b6b874a930829a62',NULL,'北京银行',0.10,5.00,-4.90,NULL,'66666666666644444',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(31,'2019-05-20 19:12:30','2d7b48d58a574fc1b6b874a930829a62',NULL,NULL,'2d7b48d58a574fc1b6b874a930829a62',NULL,'北京银行',0.10,5.00,-4.90,NULL,'66666666666644444',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(32,'2019-05-20 19:17:40','2d7b48d58a574fc1b6b874a930829a62',NULL,NULL,'2d7b48d58a574fc1b6b874a930829a62',NULL,'北京银行',0.01,5.00,-4.99,NULL,'66666666666644444',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(33,'2019-05-20 19:20:48','2d7b48d58a574fc1b6b874a930829a62',NULL,NULL,'2d7b48d58a574fc1b6b874a930829a62',NULL,'北京银行',5.00,5.00,0.00,NULL,'66666666666644444',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(34,'2019-05-20 19:43:37','2d7b48d58a574fc1b6b874a930829a62',NULL,NULL,'2d7b48d58a574fc1b6b874a930829a62',NULL,'北京银行',10.00,5.00,5.00,NULL,'66666666666644444',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(35,'2019-05-23 17:23:59','2d7b48d58a574fc1b6b874a930829a62','2019-05-23 17:30:08',NULL,'2d7b48d58a574fc1b6b874a930829a62',NULL,'北京银行',5.00,5.00,0.00,'66666','66666666666644444',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2),(36,'2019-06-03 10:49:05','2d7b48d58a574fc1b6b874a930829a62',NULL,NULL,'2d7b48d58a574fc1b6b874a930829a62',NULL,'中国建设银行',10.00,5.00,5.00,'pdd','123654789987',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(37,'2019-06-03 11:06:48','2d7b48d58a574fc1b6b874a930829a62',NULL,NULL,'2d7b48d58a574fc1b6b874a930829a62',NULL,'中国建设银行',10.00,5.00,5.00,NULL,'123654789987',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(38,'2019-06-03 11:07:45','2d7b48d58a574fc1b6b874a930829a62',NULL,NULL,'2d7b48d58a574fc1b6b874a930829a62',NULL,'中国建设银行',10.00,5.00,5.00,NULL,'123654789987',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(39,'2019-06-03 11:10:10','2d7b48d58a574fc1b6b874a930829a62',NULL,NULL,'2d7b48d58a574fc1b6b874a930829a62',NULL,'中国建设银行',10.00,5.00,5.00,NULL,'123654789987',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(40,'2019-06-03 11:12:17','2d7b48d58a574fc1b6b874a930829a62',NULL,NULL,'2d7b48d58a574fc1b6b874a930829a62',NULL,'中国建设银行',10.00,5.00,5.00,NULL,'123654789987',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(41,'2019-06-03 11:12:59','2d7b48d58a574fc1b6b874a930829a62',NULL,NULL,'2d7b48d58a574fc1b6b874a930829a62',NULL,'中国建设银行',10.00,5.00,5.00,NULL,'123654789987',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(42,'2019-06-03 11:14:09','2d7b48d58a574fc1b6b874a930829a62',NULL,NULL,'2d7b48d58a574fc1b6b874a930829a62',NULL,'中国建设银行',10.00,5.00,5.00,'张伟','123654789987',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),(43,'2019-06-03 11:14:51','2d7b48d58a574fc1b6b874a930829a62',NULL,NULL,'2d7b48d58a574fc1b6b874a930829a62',NULL,'中国建设银行',10.00,5.00,5.00,'张伟','123654789987','白沙路支行',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
