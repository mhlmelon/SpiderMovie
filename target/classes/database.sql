/*
Navicat MySQL Data Transfer

Source Server         : eunke-test
Source Server Version : 50622
Source Host           : xen2-dev-1:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50622
File Encoding         : 65001

Date: 2019-10-19 17:15:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for movie
-- ----------------------------
DROP TABLE IF EXISTS `movie`;
CREATE TABLE `movie` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `pic_url` varchar(100) DEFAULT NULL,
  `target_url` varchar(100) DEFAULT NULL,
  `introduction` varchar(1000) DEFAULT NULL,
  `download_url` text,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;
