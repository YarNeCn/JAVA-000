/*
Navicat MySQL Data Transfer

Source Server         : 39.105.101.222
Source Server Version : 50647
Source Host           : 39.105.101.222:3306
Source Database       : geekjava

Target Server Type    : MYSQL
Target Server Version : 50647
File Encoding         : 65001

Date: 2020-11-23 21:19:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `g_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `g_name` varchar(255) NOT NULL COMMENT '商品名称',
  `g_type` varchar(255) NOT NULL COMMENT '商品类型',
  `g_description` varchar(255) DEFAULT NULL COMMENT '商品描述',
  `g_price` decimal(10,0) NOT NULL COMMENT '商品价格',
  `g_createtime` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '商品创建时间',
  `g_updatetime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '商品修改时间',
  `g_num` int(20) NOT NULL COMMENT '商品数量',
  PRIMARY KEY (`g_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of goods
-- ----------------------------

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `o_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `o_num` int(2) DEFAULT NULL COMMENT '订单号',
  `o_money` decimal(5,0) NOT NULL COMMENT '订单金额',
  `o_version` tinyint(5) NOT NULL,
  `o_remark` varchar(200) DEFAULT NULL COMMENT '订单备注',
  `o_status` tinyint(1) NOT NULL COMMENT '订单状态 0 初始 1 成功  2 失败 3 关闭',
  `u_id` int(11) NOT NULL COMMENT '用户id',
  `g_id` int(11) NOT NULL COMMENT '商品ID',
  `o_createtime` datetime NOT NULL,
  `o_updatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`o_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of order
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `u_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键用户ID',
  `u_name` varchar(30) NOT NULL COMMENT '用户名',
  `u_sex` tinyint(2) DEFAULT NULL COMMENT '用户性别  1. 男 2. 女',
  `u_createtime` datetime NOT NULL COMMENT '用户注册时间',
  `u_mobile` varchar(20) DEFAULT NULL COMMENT '用户手机号',
  `u_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '用户状态 0 正常 1 冻结 2 删除',
  `u_address` varchar(255) DEFAULT NULL COMMENT '用户邮寄地址',
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
