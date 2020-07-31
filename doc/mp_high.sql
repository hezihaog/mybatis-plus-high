/*
 Navicat Premium Data Transfer

 Source Server         : mac
 Source Server Type    : MySQL
 Source Server Version : 50716
 Source Host           : localhost:3306
 Source Schema         : mp_high

 Target Server Type    : MySQL
 Target Server Version : 50716
 File Encoding         : 65001

 Date: 31/07/2020 16:30:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `manager_id` bigint(20) DEFAULT NULL COMMENT '直属上级id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '1' COMMENT '版本',
  `deleted` int(1) DEFAULT '0' COMMENT '逻辑删除标识(0.未删除,1.已删除)',
  PRIMARY KEY (`id`),
  KEY `manager_fk` (`manager_id`),
  CONSTRAINT `manager_fk` FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1087982257332887553, '大boss', 40, 'boss@baomidou.com', NULL, '2019-01-11 14:20:20', NULL, 1, 0);
INSERT INTO `user` VALUES (1088248166370832385, '王天风', NULL, NULL, NULL, NULL, '2020-07-31 16:26:35', NULL, 0);
INSERT INTO `user` VALUES (1088250446457389058, '李艺伟', 28, 'lyw@baomidou.com', 1088248166370832385, '2019-02-14 08:31:16', NULL, 1, 0);
INSERT INTO `user` VALUES (1094590409767661570, '张雨琪', 31, 'zjq@baomidou.com', 1088248166370832385, '2019-01-14 09:15:15', NULL, 1, 0);
INSERT INTO `user` VALUES (1094592041087729666, '刘红雨', 32, 'lhm@baomidou.com', 1088248166370832385, '2019-01-14 09:48:16', NULL, 1, 0);
INSERT INTO `user` VALUES (1289111378211680257, '李兴华', 35, NULL, 1088248166370832385, '2020-07-31 16:11:18', '2020-07-31 16:27:18', NULL, 1);
INSERT INTO `user` VALUES (1289111378257817602, '杨红', 29, NULL, 1088248166370832385, '2020-07-31 16:11:18', NULL, NULL, 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
