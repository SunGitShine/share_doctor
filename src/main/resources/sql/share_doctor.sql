/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : share_doctor

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-04-10 18:58:39
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
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('1', '内科', null, null);
INSERT INTO `department` VALUES ('2', '外科', null, null);
INSERT INTO `department` VALUES ('3', '整形美容', null, null);
INSERT INTO `department` VALUES ('4', '儿科', null, null);
INSERT INTO `department` VALUES ('5', '骨科', null, null);
INSERT INTO `department` VALUES ('6', '耳鼻喉科', null, null);
INSERT INTO `department` VALUES ('7', '眼科', null, null);
INSERT INTO `department` VALUES ('8', '妇产科', null, null);
INSERT INTO `department` VALUES ('9', '皮肤科', null, null);
INSERT INTO `department` VALUES ('10', '男科', null, null);
INSERT INTO `department` VALUES ('11', '呼吸内科', '1', '内科');
INSERT INTO `department` VALUES ('12', '心血管内科', '1', '内科');
INSERT INTO `department` VALUES ('13', '神经内科', '1', '内科');
INSERT INTO `department` VALUES ('14', '消化内科', '1', '内科');
INSERT INTO `department` VALUES ('15', '肾脏内科', '1', '内科');
INSERT INTO `department` VALUES ('16', '内分泌与代谢科', '1', '内科');
INSERT INTO `department` VALUES ('17', '风湿免疫科', '1', '内科');
INSERT INTO `department` VALUES ('18', '血液内科', '1', '内科');
INSERT INTO `department` VALUES ('19', '感染科', '1', '内科');
INSERT INTO `department` VALUES ('20', '心胸外科', '2', '外科');
INSERT INTO `department` VALUES ('21', '心脏与血管外科', '2', '外科');
INSERT INTO `department` VALUES ('22', '神经外科', '2', '外科');
INSERT INTO `department` VALUES ('23', '肝胆外科', '2', '外科');
INSERT INTO `department` VALUES ('24', '烧伤科', '2', '外科');
INSERT INTO `department` VALUES ('25', '康复科', '2', '外科');
INSERT INTO `department` VALUES ('26', '泌尿外科', '2', '外科');
INSERT INTO `department` VALUES ('27', '肛肠外科', '2', '外科');
INSERT INTO `department` VALUES ('28', '普外科', '2', '外科');
INSERT INTO `department` VALUES ('29', '美容外科', '3', '整形美容');
INSERT INTO `department` VALUES ('30', '美容皮肤科', '3', '整形美容');
INSERT INTO `department` VALUES ('31', '美容口腔', '3', '整形美容');
INSERT INTO `department` VALUES ('32', '小儿科', '4', '儿科');
INSERT INTO `department` VALUES ('33', '新生儿科', '4', '儿科');
INSERT INTO `department` VALUES ('34', '脊柱科', '5', '骨科');
INSERT INTO `department` VALUES ('35', '关节科', '5', '骨科');
INSERT INTO `department` VALUES ('36', '创伤科', '5', '骨科');

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
  `area` int(11) DEFAULT NULL COMMENT '1、华东地区， 2、华南地区， 3：华中地区 ， 4：华北地区， 5：西北地区， 6：西南地区， 7：东北地区， 8：台港澳地区',
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='医生信息表';

-- ----------------------------
-- Records of doctor
-- ----------------------------
INSERT INTO `doctor` VALUES ('1', 'oAl0d0ZMoDRD9vkD-0vqegcYNhsc', 'http://www.baidu.com', '谢强', '1', '2', '3', '2018-04-02', '四川省', '成都市', '6', '18380448932', '1', '内科', '1', 'http://www.baidu.com', 'http://www.baidu.com', 'http://www.baidu.com', '[\"骨干医师\",\"模范代表\"]', '[{\"company\":\"华西医院\",\"time\":\"2016-06~2018-01\",\"department\":\"口腔外科\"}]', '[{\"major\":\"口腔医学\",\"school\":\"四川大学\",\"time\":\"2016-06~2018-01\"}]', '[{\"description\":\"很有难度的一个手术\",\"url\":\"http://www.baidu.com\"}]', '擅长领域', '自我介绍', '1', '1', '2018-04-10 11:07:59', '2018-04-10 11:07:59');

-- ----------------------------
-- Table structure for `doctor_relese`
-- ----------------------------
DROP TABLE IF EXISTS `doctor_relese`;
CREATE TABLE `doctor_relese` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doctor_id` int(11) DEFAULT NULL COMMENT '医生id',
  `start_time` date DEFAULT NULL COMMENT '开始时间',
  `end_time` date DEFAULT NULL COMMENT '结束时间',
  `status` int(11) DEFAULT '1' COMMENT '状态，0：下架，1：上架',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='医生发布';

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
  `area` int(11) DEFAULT NULL COMMENT '1、华东地区， 2、华南地区， 3：华中地区 ， 4：华北地区， 5：西北地区， 6：西南地区， 7：东北地区， 8：台港澳地区',
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='医院信息表';

-- ----------------------------
-- Records of hospital
-- ----------------------------
INSERT INTO `hospital` VALUES ('1', 'oAl0d0X18RY-ttRvMqRQFmnf1xxA', 'csc', 'http://www.baidu.com', '中和镇xx街', '四川省', '成都市', '6', '18380448933', '4', '[\"好医院\",\"社保定点医院\"]', null, '简介', '[\"http://www.baidu.com\",\"http://www.baidu.com\"]', 'http://www.baidu.com', null, '1', '1', '2018-04-10 14:05:09', '2018-04-10 18:23:28');

-- ----------------------------
-- Table structure for `hospital_release`
-- ----------------------------
DROP TABLE IF EXISTS `hospital_release`;
CREATE TABLE `hospital_release` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hospital_id` int(11) NOT NULL COMMENT '医院id',
  `department_id` int(11) NOT NULL COMMENT '科室id',
  `department_name` varchar(20) DEFAULT NULL COMMENT '科室名称',
  `title` varchar(50) NOT NULL COMMENT '标题',
  `doctor_title` int(11) NOT NULL COMMENT '职称，1：医师，2：主治医师，3：副主任医师，4：主任医师',
  `education` int(11) NOT NULL COMMENT '学历，1：大专，2：本科，3：硕士，4：博士，5：其他',
  `work_time` int(11) DEFAULT NULL COMMENT '工作年限',
  `time` datetime DEFAULT NULL COMMENT '手术时间',
  `money_type` int(11) DEFAULT NULL COMMENT '待遇类型，-1：商议，2：具体金额',
  `job_description` text COMMENT '职位描述',
  `job_require` text COMMENT '职位要求',
  `audit_status` int(11) DEFAULT '0' COMMENT '审核状态，0：未处理，1：已通过，2：未通过',
  `status` int(11) DEFAULT '1' COMMENT '状态，0：下架，1：上架',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hospital_release
-- ----------------------------
INSERT INTO `hospital_release` VALUES ('1', '1', '1', '脊柱外科', '急需脊柱外科主治', '2', '2', '2', '2018-04-10 00:00:00', '-1', '职位描述', '职位要求', '0', '0', '2018-04-10 14:44:44', '2018-04-10 15:12:28');
