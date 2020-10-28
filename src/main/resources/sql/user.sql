/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50027
Source Host           : localhost:3306
Source Database       : user

Target Server Type    : MYSQL
Target Server Version : 50027
File Encoding         : 65001

Date: 2020-07-15 17:08:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for filter
-- ----------------------------
DROP TABLE IF EXISTS `filter`;
CREATE TABLE `filter` (
  `id` int(11) default NULL,
  `sensitive_word` varchar(255) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of filter
-- ----------------------------
INSERT INTO `filter` VALUES ('1', '张三');
INSERT INTO `filter` VALUES ('2', '哥');

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) default NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES ('1');

-- ----------------------------
-- Table structure for login
-- ----------------------------
DROP TABLE IF EXISTS `login`;
CREATE TABLE `login` (
  `ID` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `money` decimal(8,2) NOT NULL,
  PRIMARY KEY  (`ID`),
  KEY `index_username_password` (`username`,`password`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of login
-- ----------------------------
INSERT INTO `login` VALUES ('1', '张三', '111', '1@qq.com', '11000.00');
INSERT INTO `login` VALUES ('2', '丹丹', '222', '2@qq.com', '13500.00');
INSERT INTO `login` VALUES ('3', '李四', '333', '3@qq.com', '15000.00');
INSERT INTO `login` VALUES ('4', '超舞', '444', '4@qq.com', '162200.00');
INSERT INTO `login` VALUES ('5', '丹丹', '555', '5@qq.com', '1100.00');
INSERT INTO `login` VALUES ('6', '张三', '111', '6@qq.com', '50.00');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL auto_increment,
  `text` varchar(255) default NULL,
  `date` date default NULL,
  `password` varchar(255) default NULL,
  `username` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('1', '迪迪哥', '2020-07-05', '111', '111');
INSERT INTO `message` VALUES ('2', '张三是尔康', '2020-07-09', '222', '222');
INSERT INTO `message` VALUES ('3', '迪儿', '2020-07-16', '333', '333');
INSERT INTO `message` VALUES ('4', '傻逼迪迪', '2020-07-07', '444', '444');
INSERT INTO `message` VALUES ('5', '小龙', '2020-07-13', '555', 'huhu');
INSERT INTO `message` VALUES ('6', '小龙', '2020-07-13', '555', 'huhu');
INSERT INTO `message` VALUES ('7', '小龙', '2020-07-13', '555', 'huhu');
INSERT INTO `message` VALUES ('28', '小龙23', '2020-07-13', '5552', 'huhu');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `pid` int(11) NOT NULL auto_increment,
  `pimage` varchar(255) default NULL,
  PRIMARY KEY  (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('22', '271a1454-04e5-4f63-9758-ff9868f70ff040b2b77318818c8aaadefeb4f83da7f.jpg');
INSERT INTO `product` VALUES ('23', '4b332569-3502-425c-850b-639403d4b91340b2b77318818c8aaadefeb4f83da7f.jpg');

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `id` int(11) NOT NULL,
  `job_name` varchar(255) NOT NULL COMMENT '任务名称',
  `cron_expression` varchar(255) NOT NULL COMMENT '执行表达式',
  `bean_name` varchar(255) NOT NULL COMMENT '类名',
  `method_name` varchar(255) NOT NULL COMMENT '方法名',
  `job_status` int(11) NOT NULL default '1' COMMENT '状态 0.启动 1.暂停',
  `job_describe` varchar(255) NOT NULL COMMENT '描述'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of schedule_job
-- ----------------------------

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL,
  `name` varchar(255) default NULL,
  `fenshu` varchar(255) default NULL,
  `kemu` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '张三', '95', '数学');
INSERT INTO `student` VALUES ('2', '张三', '85', '英语');
INSERT INTO `student` VALUES ('3', '张三', '70', '语文');
INSERT INTO `student` VALUES ('4', '丹丹', '90', '数学');
INSERT INTO `student` VALUES ('5', '丹丹', '80', '英语');
INSERT INTO `student` VALUES ('6', '丹丹', '65', '语文');
INSERT INTO `student` VALUES ('7', '李四', '96', '数学');
INSERT INTO `student` VALUES ('8', '李四', '86', '语文');
INSERT INTO `student` VALUES ('9', '李四', '72', '英语');
INSERT INTO `student` VALUES ('10', '李四', '72', '英语');
