/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : share_doctor

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-03-29 17:41:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `department`
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department_name` varchar(30) NOT NULL COMMENT '科室名称',
  `parent_department_id` int(11) DEFAULT NULL COMMENT '父科室id',
  `parent_department_name` varchar(255) DEFAULT NULL COMMENT '父科室名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------

-- ----------------------------
-- Table structure for `doctor`
-- ----------------------------
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '医生id',
  `openid` varchar(50) NOT NULL,
  `head_img_url` varchar(100) DEFAULT NULL COMMENT '头像地址',
  `name` varchar(10) DEFAULT NULL COMMENT '姓名',
  `sex` int(11) DEFAULT NULL COMMENT '性别，0：女，1：男',
  `education` int(11) DEFAULT NULL COMMENT '学历，1：大专，2：本科，3：硕士，4：博士，5：其他',
  `work_time` int(11) DEFAULT NULL COMMENT '工作时间',
  `birthday` date DEFAULT NULL COMMENT '出生年月',
  `province` varchar(20) DEFAULT NULL COMMENT '所在省',
  `city` varchar(20) DEFAULT NULL COMMENT '所在市',
  `phone` varchar(11) DEFAULT NULL COMMENT '电话',
  `department_id` int(11) DEFAULT NULL COMMENT '科室id',
  `department_name` varchar(20) DEFAULT NULL COMMENT '科室',
  `title` int(11) DEFAULT NULL COMMENT '职称，1：医师，2：主治医师，3：副主任医师，4：主任医师',
  `profession_card_url` varchar(100) DEFAULT NULL COMMENT '医师职称证',
  `qualification_card_url` varchar(100) DEFAULT NULL COMMENT '医师资格证',
  `practice_card_url` varchar(100) DEFAULT NULL COMMENT '医师执业证',
  `call` text COMMENT 'r荣誉称呼',
  `work_experience` text COMMENT '工作经历[{"time":"2016-06~2018-01","company":"华西医院","department":"口腔外科"}]',
  `education_experience` text COMMENT '教育经历，[{"time":"2016-06~2018-01","school":"四川大学","major":"口腔医学"}]',
  `works` text COMMENT '作品，[{"url":"","description":""}]',
  `good_field` text COMMENT '擅长领域',
  `profile` text COMMENT '个人简介',
  `audit_status` int(11) DEFAULT '0' COMMENT '审核状态，0：未处理，1：已通过，2：未通过',
  `complete_status` int(11) DEFAULT '0' COMMENT '信息完善状态，0：未完善，1：已完善',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `openid_index` (`openid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='医生信息表';

-- ----------------------------
-- Records of doctor
-- ----------------------------

-- ----------------------------
-- Table structure for `doctor_relese`
-- ----------------------------
DROP TABLE IF EXISTS `doctor_relese`;
CREATE TABLE `doctor_relese` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doctor_id` int(11) DEFAULT NULL COMMENT '医生id',
  `start_time` date DEFAULT NULL COMMENT '开始时间',
  `end_time` date DEFAULT NULL COMMENT '结束时间',
  `status` int(11) DEFAULT NULL COMMENT '状态，0：下架，1：上架',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='医生发布';

-- ----------------------------
-- Records of doctor_relese
-- ----------------------------

-- ----------------------------
-- Table structure for `hospital`
-- ----------------------------
DROP TABLE IF EXISTS `hospital`;
CREATE TABLE `hospital` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '医院id',
  `openid` varchar(64) NOT NULL,
  `name` varchar(128) DEFAULT NULL COMMENT '医院名称',
  `logo_img_url` varchar(128) DEFAULT NULL COMMENT 'logo地址',
  `address` varchar(256) DEFAULT NULL COMMENT '地址',
  `province` varchar(20) DEFAULT NULL COMMENT '所在省',
  `city` varchar(20) DEFAULT NULL COMMENT '所在市',
  `phone` varchar(16) DEFAULT NULL COMMENT '联系电话',
  `scale` int(11) DEFAULT NULL COMMENT '规模，1：10人以下，2：10~50人，3：50~100人，4：100~500人，5：500人以上',
  `tag` varchar(256) DEFAULT NULL COMMENT '标签',
  `good_field` text COMMENT '擅长领域',
  `profile` text COMMENT '简介',
  `image_url` text COMMENT '医院图片',
  `business_licence_url` varchar(100) DEFAULT NULL COMMENT '营业执照',
  `works` text COMMENT '作品展示',
  `audit_status` int(11) DEFAULT '0' COMMENT '审核状态，0：未处理，1：已通过，2：未通过',
  `complete_status` int(11) DEFAULT '0' COMMENT '信息完善状态，0：未完善，1：已完善',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `openid_index` (`openid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='医院信息表';

-- ----------------------------
-- Records of hospital
-- ----------------------------

-- ----------------------------
-- Table structure for `hospital_release`
-- ----------------------------
DROP TABLE IF EXISTS `hospital_release`;
CREATE TABLE `hospital_release` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hospital_id` int(11) NOT NULL COMMENT '医院id',
  `department_id` int(11) NOT NULL COMMENT '科室id',
  `department_name` varchar(20) DEFAULT NULL COMMENT '科室名称',
  `title` int(11) NOT NULL COMMENT '职称，1：医师，2：主治医师，3：副主任医师，4：主任医师',
  `education` int(11) NOT NULL COMMENT '学历，1：大专，2：本科，3：硕士，4：博士，5：其他',
  `work_time` int(11) DEFAULT NULL COMMENT '工作年限',
  `time` datetime DEFAULT NULL COMMENT '手术时间',
  `province` varchar(20) DEFAULT NULL COMMENT '所在省',
  `city` varchar(20) DEFAULT NULL COMMENT '所在市',
  `money_type` int(11) DEFAULT NULL COMMENT '待遇类型，1：商议，2：具体金额',
  `job_description` text COMMENT '职位描述',
  `job_require` text COMMENT '职位要求',
  `audit_status` int(11) DEFAULT NULL COMMENT '审核状态，0：未处理，1：已通过，2：未通过',
  `status` int(11) DEFAULT NULL COMMENT '状态，0：下架，1：上架',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hospital_release
-- ----------------------------
