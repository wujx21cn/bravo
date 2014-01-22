/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50018
Source Host           : localhost:3306
Source Database       : bravo

Target Server Type    : MYSQL
Target Server Version : 50018
File Encoding         : 65001

Date: 2014-01-22 14:43:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `bravo_attachment`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_attachment`;
CREATE TABLE `bravo_attachment` (
  `id` decimal(19,0) NOT NULL COMMENT 'id',
  `name` varchar(128) NOT NULL COMMENT '附件名称',
  `relative_Path` varchar(256) NOT NULL COMMENT '存储路径',
  `original_filename` varchar(128) NOT NULL COMMENT '原文件名',
  `file_size` decimal(10,0) NOT NULL COMMENT '文件大小',
  `uploader` decimal(19,0) default NULL COMMENT '上传人',
  `uploadt` date default NULL COMMENT '上传时间',
  PRIMARY KEY  (`id`),
  KEY `FK_BRAVO_AT_REF_UPTER` (`uploader`),
  CONSTRAINT `FK_BRAVO_AT_REF_UPTER` FOREIGN KEY (`uploader`) REFERENCES `bravo_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_attachment
-- ----------------------------

-- ----------------------------
-- Table structure for `bravo_attachment_relation`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_attachment_relation`;
CREATE TABLE `bravo_attachment_relation` (
  `id` decimal(19,0) NOT NULL COMMENT 'id',
  `attachment` decimal(19,0) NOT NULL COMMENT '附件',
  `entity_name` varchar(256) default NULL COMMENT '引用的实体名',
  `entity` decimal(19,0) NOT NULL COMMENT '引用实体',
  PRIMARY KEY  (`id`),
  KEY `FK_BRAVO_AT_REF_RE` (`attachment`),
  CONSTRAINT `FK_BRAVO_AT_REF_RE` FOREIGN KEY (`attachment`) REFERENCES `bravo_attachment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_attachment_relation
-- ----------------------------

-- ----------------------------
-- Table structure for `bravo_audit_history`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_audit_history`;
CREATE TABLE `bravo_audit_history` (
  `id` decimal(19,0) NOT NULL COMMENT '主键',
  `entity_name` varchar(128) default NULL COMMENT '实体名',
  `entity_id` varchar(32) default NULL COMMENT '实体ID',
  `updater` decimal(19,0) default NULL COMMENT '更新人',
  `update_dt` date default NULL COMMENT '更新时间',
  PRIMARY KEY  (`id`),
  KEY `FK_AUDIT_REF_USER` (`updater`),
  CONSTRAINT `FK_AUDIT_REF_USER` FOREIGN KEY (`updater`) REFERENCES `bravo_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_audit_history
-- ----------------------------

-- ----------------------------
-- Table structure for `bravo_audit_history_detail`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_audit_history_detail`;
CREATE TABLE `bravo_audit_history_detail` (
  `id` decimal(19,0) NOT NULL COMMENT '主键',
  `field_name` varchar(32) default NULL COMMENT '字段名',
  `field_type` varchar(128) default NULL COMMENT '字段类型',
  `orignal_value` varchar(512) default NULL COMMENT '初始值',
  `final_value` varchar(512) default NULL COMMENT '改变值',
  `audit_history` decimal(19,0) default NULL COMMENT '对应更新记录',
  PRIMARY KEY  (`id`),
  KEY `FK_AUDIT_HI_REF_DE` (`audit_history`),
  CONSTRAINT `FK_AUDIT_HI_REF_DE` FOREIGN KEY (`audit_history`) REFERENCES `bravo_audit_history` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_audit_history_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `bravo_button`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_button`;
CREATE TABLE `bravo_button` (
  `id` decimal(19,0) NOT NULL COMMENT 'ID',
  `button_name` varchar(128) NOT NULL COMMENT '按钮资源名',
  `oper_condition` varchar(64) NOT NULL default 'true' COMMENT '执行情况',
  `comments` varchar(256) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_button
-- ----------------------------

-- ----------------------------
-- Table structure for `bravo_button_permis`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_button_permis`;
CREATE TABLE `bravo_button_permis` (
  `button_id` decimal(19,0) NOT NULL COMMENT '按钮ID',
  `permis_id` decimal(19,0) NOT NULL COMMENT '权限ID',
  PRIMARY KEY  (`button_id`,`permis_id`),
  KEY `fk_bravo_bu_reference_bravo_pe` (`permis_id`),
  CONSTRAINT `fk_bravo_bu_reference_bravo_bu` FOREIGN KEY (`button_id`) REFERENCES `bravo_button` (`id`),
  CONSTRAINT `fk_bravo_bu_reference_bravo_pe` FOREIGN KEY (`permis_id`) REFERENCES `bravo_permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_button_permis
-- ----------------------------

-- ----------------------------
-- Table structure for `bravo_data_dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_data_dictionary`;
CREATE TABLE `bravo_data_dictionary` (
  `id` decimal(19,0) NOT NULL COMMENT 'id',
  `table_name` varchar(128) default NULL COMMENT '表名',
  `entity_name` varchar(128) default NULL COMMENT '实体名',
  `package_name` varchar(128) default NULL COMMENT '实体包名',
  `entity_title` varchar(128) default NULL COMMENT '实体标题',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_data_dictionary
-- ----------------------------

-- ----------------------------
-- Table structure for `bravo_data_dictionary_detail`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_data_dictionary_detail`;
CREATE TABLE `bravo_data_dictionary_detail` (
  `id` decimal(19,0) NOT NULL COMMENT 'id',
  `data_dictionary` decimal(19,0) default NULL COMMENT '相关数据字典',
  `table_field_name` varchar(128) default NULL COMMENT '表字段名',
  `table_field_type` varchar(128) default NULL COMMENT '表字段类型',
  `entity_field_name` varchar(128) default NULL COMMENT '实体字段名',
  `entity_field_type` varchar(128) default NULL COMMENT '实体字段类型',
  `entity_field_title` varchar(128) default NULL COMMENT '实体字段标题',
  PRIMARY KEY  (`id`),
  KEY `FK_DA_REF_DETAIL` (`data_dictionary`),
  CONSTRAINT `FK_DA_REF_DETAIL` FOREIGN KEY (`data_dictionary`) REFERENCES `bravo_data_dictionary` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_data_dictionary_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `bravo_department`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_department`;
CREATE TABLE `bravo_department` (
  `id` decimal(19,0) NOT NULL COMMENT 'id',
  `code` varchar(64) NOT NULL COMMENT '部门编码',
  `sequences` int(11) default NULL COMMENT '顺序号',
  `name` varchar(128) NOT NULL COMMENT '名称',
  `manager` decimal(19,0) default NULL COMMENT '部门经理',
  `address` varchar(512) default NULL COMMENT '详细地址',
  `postalcode` varchar(16) default NULL COMMENT '邮政编码',
  `telephone` varchar(32) default NULL COMMENT '电话',
  `fax` varchar(32) default NULL COMMENT '传真',
  `parent_dep` decimal(19,0) default NULL COMMENT '上级部门',
  `comments` varchar(2048) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`),
  KEY `FK_DEPARTMENT_MANAGER` (`manager`),
  CONSTRAINT `FK_DEPARTMENT_MANAGER` FOREIGN KEY (`manager`) REFERENCES `bravo_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_department
-- ----------------------------
INSERT INTO `bravo_department` VALUES ('0', 'china', '0', '中国xxx', '31', '00', '0', '', '', null, '');
INSERT INTO `bravo_department` VALUES ('1', 'air', '1', '机场业务部', '31', '广州市', '12321', '21321', null, '0', null);
INSERT INTO `bravo_department` VALUES ('2', 'hw', '2', '运行部', '2', '武汉市', '430081', '777', '989898', '0', null);
INSERT INTO `bravo_department` VALUES ('3', '88wewqe', '3', '人事行政部', '40', 'rewrewfe', '3213213', '3213213213', '3123213', '0', null);
INSERT INTO `bravo_department` VALUES ('4', 'cccc', '4', '分销业务部', '1', '456', '6456', '45646', '45645', '0', null);
INSERT INTO `bravo_department` VALUES ('5', 'rewrew', '5', '财务部', '34', '432frew', '324324', null, '432432', '0', null);
INSERT INTO `bravo_department` VALUES ('6', 'dsf', '6', '市场部', '32', 'sgf', '45345', '43435', '345', '0', null);
INSERT INTO `bravo_department` VALUES ('7', 'sdfsdf', '7', 'Java组', '1', '5345', '34543', '34543', '345', '1', null);
INSERT INTO `bravo_department` VALUES ('8', '34543', '8', '前端组', '34', '345435', '34543', '34534', '5345', '1', null);
INSERT INTO `bravo_department` VALUES ('9', '34324', '9', '数据服务组', '1', '32', null, null, null, '1', null);
INSERT INTO `bravo_department` VALUES ('10', '4335', '10', '离港组', '1', '3', null, null, null, '1', null);
INSERT INTO `bravo_department` VALUES ('12', '2342', '3', '324324', '1', '34234234   2342', '2342', '4234', '23423', '21', null);
INSERT INTO `bravo_department` VALUES ('19', 'test', null, 'test', '2', null, null, null, null, null, null);
INSERT INTO `bravo_department` VALUES ('20', 'teste', null, 'test', '2', null, null, null, null, '1', null);
INSERT INTO `bravo_department` VALUES ('21', 'sdfds', null, 'sdfds', '38', null, null, null, null, null, null);
INSERT INTO `bravo_department` VALUES ('22', '发', null, '的士费', '2', '防盗锁f', '4324', null, '3', null, null);
INSERT INTO `bravo_department` VALUES ('206', 'ewdew', null, 'wed', '2', null, null, null, null, null, null);
INSERT INTO `bravo_department` VALUES ('207', 'test324', '22', 'test324', '32', '体育东路财富广场', '3456323', '123', null, '20', null);
INSERT INTO `bravo_department` VALUES ('208', 'test324', '22222222', 'test324', '32', null, '3456323', '123', null, '20', null);
INSERT INTO `bravo_department` VALUES ('209', '0325', '325', 'test0325', '1', '广州市天河区', '516020', '34443', '333', '208', '财富广场');
INSERT INTO `bravo_department` VALUES ('210', '0325', '325', 'test0325', '1', null, null, '325', null, '208', null);
INSERT INTO `bravo_department` VALUES ('211', '0325', '325', '0325', '1', null, null, '325', null, '209', null);
INSERT INTO `bravo_department` VALUES ('212', '0325', '325', '0325', '1', null, null, '325', null, '209', null);
INSERT INTO `bravo_department` VALUES ('213', '0325', '325', '0325', '1', null, null, '325', null, '209', null);
INSERT INTO `bravo_department` VALUES ('214', '0325', '325', '0325', '1', null, null, '325', null, '209', null);
INSERT INTO `bravo_department` VALUES ('215', '0325', '325', '0325', '1', null, null, '325', null, '209', null);
INSERT INTO `bravo_department` VALUES ('216', '0325', '325', '0325', '1', null, null, '325', null, '209', null);
INSERT INTO `bravo_department` VALUES ('217', '0325', '325', '0325', '1', null, null, '325', null, '209', null);
INSERT INTO `bravo_department` VALUES ('218', '0325', '325', '0325', '1', null, null, '325', null, '209', null);
INSERT INTO `bravo_department` VALUES ('219', '0325', '325', '0325', '1', null, null, '325', null, '209', null);
INSERT INTO `bravo_department` VALUES ('220', '0325', '325', '0325', '1', null, null, '325', null, '209', null);
INSERT INTO `bravo_department` VALUES ('221', '是是', null, '客副部', null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `bravo_entity_oper_permis`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_entity_oper_permis`;
CREATE TABLE `bravo_entity_oper_permis` (
  `ID` decimal(19,0) NOT NULL COMMENT 'ID',
  `PERMIS_ID` decimal(19,0) NOT NULL COMMENT '权限ID',
  `ENTITY_NAME` varchar(128) NOT NULL COMMENT '实体名称',
  `OPER_TYPE` varchar(64) default NULL COMMENT '操作类型',
  PRIMARY KEY  (`ID`),
  KEY `FK_BRAVO_EN_REFERENCE_BRAVO_PE` (`PERMIS_ID`),
  CONSTRAINT `FK_BRAVO_EN_REFERENCE_BRAVO_PE` FOREIGN KEY (`PERMIS_ID`) REFERENCES `bravo_permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_entity_oper_permis
-- ----------------------------
INSERT INTO `bravo_entity_oper_permis` VALUES ('1369', '23', 'org.jbpm.graph.node.EndState', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1370', '23', 'org.jbpm.graph.node.EndState', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1371', '23', 'org.jbpm.graph.node.EndState', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1372', '23', 'org.jbpm.graph.node.EndState', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1373', '23', 'org.jbpm.graph.node.Decision', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1374', '23', 'org.jbpm.graph.node.Decision', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1375', '23', 'org.jbpm.graph.node.Decision', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1376', '23', 'org.jbpm.graph.node.Decision', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1377', '23', 'org.jbpm.graph.node.TaskNode', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1378', '23', 'org.jbpm.graph.node.TaskNode', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1379', '23', 'org.jbpm.graph.node.TaskNode', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1380', '23', 'org.jbpm.graph.node.TaskNode', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1381', '23', 'org.jbpm.graph.node.ProcessState', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1382', '23', 'org.jbpm.graph.node.ProcessState', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1383', '23', 'org.jbpm.graph.node.ProcessState', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1384', '23', 'org.jbpm.graph.node.ProcessState', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1385', '23', 'org.jbpm.graph.node.StartState', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1387', '23', 'org.jbpm.graph.node.StartState', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1388', '23', 'org.jbpm.graph.node.StartState', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1389', '23', 'org.jbpm.graph.node.Join', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1390', '23', 'org.jbpm.graph.node.Join', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1391', '23', 'org.jbpm.graph.node.Join', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1392', '23', 'org.jbpm.graph.node.Join', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1393', '23', 'org.jbpm.graph.node.Fork', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1394', '23', 'org.jbpm.graph.node.Fork', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1395', '23', 'org.jbpm.graph.node.Fork', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1396', '23', 'org.jbpm.graph.node.Fork', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1397', '23', 'org.jbpm.graph.node.State', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1398', '23', 'org.jbpm.graph.node.State', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1399', '23', 'org.jbpm.graph.node.State', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1400', '23', 'org.jbpm.graph.node.State', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1401', '23', 'org.jbpm.graph.def.SuperState', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1402', '23', 'org.jbpm.graph.def.SuperState', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1403', '23', 'org.jbpm.graph.def.SuperState', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1404', '23', 'org.jbpm.graph.def.SuperState', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1405', '23', 'org.jbpm.graph.def.ProcessDefinition', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1406', '23', 'org.jbpm.graph.def.ProcessDefinition', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1407', '23', 'org.jbpm.graph.def.ProcessDefinition', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1408', '23', 'org.jbpm.graph.def.ProcessDefinition', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1409', '23', 'org.jbpm.graph.def.Transition', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1410', '23', 'org.jbpm.graph.def.Transition', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1411', '23', 'org.jbpm.graph.def.Transition', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1412', '23', 'org.jbpm.graph.def.Transition', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1413', '23', 'org.jbpm.graph.def.ExceptionHandler', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1414', '23', 'org.jbpm.graph.def.ExceptionHandler', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1415', '23', 'org.jbpm.graph.def.ExceptionHandler', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1416', '23', 'org.jbpm.graph.def.ExceptionHandler', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1417', '23', 'org.jbpm.graph.def.Action', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1418', '23', 'org.jbpm.graph.def.Action', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1419', '23', 'org.jbpm.graph.def.Action', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1420', '23', 'org.jbpm.graph.def.Action', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1421', '23', 'org.jbpm.graph.def.Event', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1422', '23', 'org.jbpm.graph.def.Event', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1423', '23', 'org.jbpm.graph.def.Event', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1424', '23', 'org.jbpm.graph.def.Event', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1425', '23', 'org.jbpm.graph.def.Node', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1426', '23', 'org.jbpm.graph.def.Node', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1427', '23', 'org.jbpm.graph.def.Node', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1428', '23', 'org.jbpm.graph.def.Node', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1429', '23', 'org.jbpm.graph.exe.Token', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1430', '23', 'org.jbpm.graph.exe.Token', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1431', '23', 'org.jbpm.graph.exe.Token', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1432', '23', 'org.jbpm.graph.exe.Token', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1433', '23', 'org.jbpm.graph.exe.RuntimeAction', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1434', '23', 'org.jbpm.graph.exe.RuntimeAction', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1435', '23', 'org.jbpm.graph.exe.RuntimeAction', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1436', '23', 'org.jbpm.graph.exe.RuntimeAction', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1437', '23', 'org.jbpm.graph.exe.ProcessInstance', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1438', '23', 'org.jbpm.graph.exe.ProcessInstance', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1439', '23', 'org.jbpm.graph.exe.ProcessInstance', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1440', '23', 'org.jbpm.graph.exe.ProcessInstance', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1441', '23', 'org.jbpm.graph.exe.Comment', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1442', '23', 'org.jbpm.graph.exe.Comment', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1443', '23', 'org.jbpm.graph.exe.Comment', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1444', '23', 'org.jbpm.graph.exe.Comment', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1445', '23', 'org.jbpm.graph.log.TransitionLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1446', '23', 'org.jbpm.graph.log.TransitionLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1447', '23', 'org.jbpm.graph.log.TransitionLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1448', '23', 'org.jbpm.graph.log.TransitionLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1449', '23', 'org.jbpm.graph.log.SignalLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1450', '23', 'org.jbpm.graph.log.SignalLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1451', '23', 'org.jbpm.graph.log.SignalLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1452', '23', 'org.jbpm.graph.log.SignalLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1453', '23', 'org.jbpm.graph.log.ProcessInstanceEndLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1454', '23', 'org.jbpm.graph.log.ProcessInstanceEndLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1455', '23', 'org.jbpm.graph.log.ProcessInstanceEndLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1456', '23', 'org.jbpm.graph.log.ProcessInstanceEndLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1457', '23', 'org.jbpm.graph.log.NodeLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1458', '23', 'org.jbpm.graph.log.NodeLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1459', '23', 'org.jbpm.graph.log.NodeLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1460', '23', 'org.jbpm.graph.log.NodeLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1461', '23', 'org.jbpm.graph.log.TokenEndLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1462', '23', 'org.jbpm.graph.log.TokenEndLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1463', '23', 'org.jbpm.graph.log.TokenEndLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1464', '23', 'org.jbpm.graph.log.TokenEndLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1465', '23', 'org.jbpm.graph.log.ProcessInstanceCreateLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1466', '23', 'org.jbpm.graph.log.ProcessInstanceCreateLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1467', '23', 'org.jbpm.graph.log.ProcessInstanceCreateLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1468', '23', 'org.jbpm.graph.log.ProcessInstanceCreateLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1469', '23', 'org.jbpm.graph.log.TokenCreateLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1470', '23', 'org.jbpm.graph.log.TokenCreateLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1471', '23', 'org.jbpm.graph.log.TokenCreateLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1472', '23', 'org.jbpm.graph.log.TokenCreateLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1473', '23', 'org.jbpm.graph.log.ActionLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1474', '23', 'org.jbpm.graph.log.ActionLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1475', '23', 'org.jbpm.graph.log.ActionLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1476', '23', 'org.jbpm.graph.log.ActionLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1477', '23', 'org.jbpm.graph.log.ProcessStateLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1478', '23', 'org.jbpm.graph.log.ProcessStateLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1479', '23', 'org.jbpm.graph.log.ProcessStateLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1480', '23', 'org.jbpm.graph.log.ProcessStateLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1481', '23', 'org.jbpm.graph.action.Script', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1482', '23', 'org.jbpm.graph.action.Script', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1483', '23', 'org.jbpm.graph.action.Script', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1484', '23', 'org.jbpm.graph.action.Script', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1485', '23', 'org.jbpm.context.log.variableinstance.DoubleUpdateLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1487', '23', 'org.jbpm.context.log.variableinstance.DoubleUpdateLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1488', '23', 'org.jbpm.context.log.variableinstance.DoubleUpdateLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1489', '23', 'org.jbpm.context.log.variableinstance.HibernateLongUpdateLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1490', '23', 'org.jbpm.context.log.variableinstance.HibernateLongUpdateLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1491', '23', 'org.jbpm.context.log.variableinstance.HibernateLongUpdateLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1492', '23', 'org.jbpm.context.log.variableinstance.HibernateLongUpdateLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1493', '23', 'org.jbpm.context.log.variableinstance.StringUpdateLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1494', '23', 'org.jbpm.context.log.variableinstance.StringUpdateLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1495', '23', 'org.jbpm.context.log.variableinstance.StringUpdateLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1496', '23', 'org.jbpm.context.log.variableinstance.StringUpdateLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1497', '23', 'org.jbpm.context.log.variableinstance.LongUpdateLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1498', '23', 'org.jbpm.context.log.variableinstance.LongUpdateLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1499', '23', 'org.jbpm.context.log.variableinstance.LongUpdateLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1500', '23', 'org.jbpm.context.log.variableinstance.LongUpdateLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1501', '23', 'org.jbpm.context.log.variableinstance.HibernateStringUpdateLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1502', '23', 'org.jbpm.context.log.variableinstance.HibernateStringUpdateLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1503', '23', 'org.jbpm.context.log.variableinstance.HibernateStringUpdateLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1504', '23', 'org.jbpm.context.log.variableinstance.HibernateStringUpdateLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1505', '23', 'org.jbpm.context.log.variableinstance.ByteArrayUpdateLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1506', '23', 'org.jbpm.context.log.variableinstance.ByteArrayUpdateLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1507', '23', 'org.jbpm.context.log.variableinstance.ByteArrayUpdateLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1508', '23', 'org.jbpm.context.log.variableinstance.ByteArrayUpdateLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1509', '23', 'org.jbpm.context.log.variableinstance.DateUpdateLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1510', '23', 'org.jbpm.context.log.variableinstance.DateUpdateLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1511', '23', 'org.jbpm.context.log.variableinstance.DateUpdateLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1512', '23', 'org.jbpm.context.log.variableinstance.DateUpdateLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1513', '23', 'org.jbpm.context.log.VariableLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1514', '23', 'org.jbpm.context.log.VariableLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1515', '23', 'org.jbpm.context.log.VariableLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1516', '23', 'org.jbpm.context.log.VariableLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1517', '23', 'org.jbpm.context.log.VariableDeleteLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1518', '23', 'org.jbpm.context.log.VariableDeleteLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1519', '23', 'org.jbpm.context.log.VariableDeleteLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1520', '23', 'org.jbpm.context.log.VariableDeleteLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1521', '23', 'org.jbpm.context.log.VariableUpdateLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1522', '23', 'org.jbpm.context.log.VariableUpdateLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1523', '23', 'org.jbpm.context.log.VariableUpdateLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1524', '23', 'org.jbpm.context.log.VariableUpdateLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1525', '23', 'org.jbpm.context.log.VariableCreateLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1526', '23', 'org.jbpm.context.log.VariableCreateLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1527', '23', 'org.jbpm.context.log.VariableCreateLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1528', '23', 'org.jbpm.context.log.VariableCreateLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1529', '23', 'org.jbpm.context.exe.variableinstance.HibernateStringInstance', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1530', '23', 'org.jbpm.context.exe.variableinstance.HibernateStringInstance', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1531', '23', 'org.jbpm.context.exe.variableinstance.HibernateStringInstance', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1532', '23', 'org.jbpm.context.exe.variableinstance.HibernateStringInstance', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1533', '23', 'org.jbpm.context.exe.variableinstance.HibernateLongInstance', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1534', '23', 'org.jbpm.context.exe.variableinstance.HibernateLongInstance', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1535', '23', 'org.jbpm.context.exe.variableinstance.HibernateLongInstance', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1536', '23', 'org.jbpm.context.exe.variableinstance.HibernateLongInstance', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1537', '23', 'org.jbpm.context.exe.variableinstance.DoubleInstance', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1538', '23', 'org.jbpm.context.exe.variableinstance.DoubleInstance', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1539', '23', 'org.jbpm.context.exe.variableinstance.DoubleInstance', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1540', '23', 'org.jbpm.context.exe.variableinstance.DoubleInstance', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1541', '23', 'org.jbpm.context.exe.variableinstance.LongInstance', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1542', '23', 'org.jbpm.context.exe.variableinstance.LongInstance', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1543', '23', 'org.jbpm.context.exe.variableinstance.LongInstance', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1544', '23', 'org.jbpm.context.exe.variableinstance.LongInstance', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1545', '23', 'org.jbpm.context.exe.variableinstance.NullInstance', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1546', '23', 'org.jbpm.context.exe.variableinstance.NullInstance', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1547', '23', 'org.jbpm.context.exe.variableinstance.NullInstance', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1548', '23', 'org.jbpm.context.exe.variableinstance.NullInstance', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1549', '23', 'org.jbpm.context.exe.variableinstance.ByteArrayInstance', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1550', '23', 'org.jbpm.context.exe.variableinstance.ByteArrayInstance', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1551', '23', 'org.jbpm.context.exe.variableinstance.ByteArrayInstance', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1552', '23', 'org.jbpm.context.exe.variableinstance.ByteArrayInstance', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1553', '23', 'org.jbpm.context.exe.variableinstance.JcrNodeInstance', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1554', '23', 'org.jbpm.context.exe.variableinstance.JcrNodeInstance', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1555', '23', 'org.jbpm.context.exe.variableinstance.JcrNodeInstance', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1556', '23', 'org.jbpm.context.exe.variableinstance.JcrNodeInstance', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1557', '23', 'org.jbpm.context.exe.variableinstance.StringInstance', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1558', '23', 'org.jbpm.context.exe.variableinstance.StringInstance', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1559', '23', 'org.jbpm.context.exe.variableinstance.StringInstance', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1560', '23', 'org.jbpm.context.exe.variableinstance.StringInstance', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1561', '23', 'org.jbpm.context.exe.variableinstance.DateInstance', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1562', '23', 'org.jbpm.context.exe.variableinstance.DateInstance', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1563', '23', 'org.jbpm.context.exe.variableinstance.DateInstance', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1564', '23', 'org.jbpm.context.exe.variableinstance.DateInstance', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1565', '23', 'org.jbpm.context.exe.ContextInstance', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1566', '23', 'org.jbpm.context.exe.ContextInstance', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1567', '23', 'org.jbpm.context.exe.ContextInstance', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1568', '23', 'org.jbpm.context.exe.ContextInstance', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1569', '23', 'org.jbpm.context.exe.VariableInstance', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1570', '23', 'org.jbpm.context.exe.VariableInstance', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1571', '23', 'org.jbpm.context.exe.VariableInstance', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1572', '23', 'org.jbpm.context.exe.VariableInstance', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1573', '23', 'org.jbpm.context.exe.TokenVariableMap', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1574', '23', 'org.jbpm.context.exe.TokenVariableMap', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1575', '23', 'org.jbpm.context.exe.TokenVariableMap', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1576', '23', 'org.jbpm.context.exe.TokenVariableMap', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1577', '23', 'org.jbpm.context.def.ContextDefinition', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1578', '23', 'org.jbpm.context.def.ContextDefinition', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1579', '23', 'org.jbpm.context.def.ContextDefinition', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1580', '23', 'org.jbpm.context.def.ContextDefinition', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1581', '23', 'org.jbpm.context.def.VariableAccess', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1582', '23', 'org.jbpm.context.def.VariableAccess', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1583', '23', 'org.jbpm.context.def.VariableAccess', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1584', '23', 'org.jbpm.context.def.VariableAccess', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1586', '23', 'org.jbpm.instantiation.Delegation', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1587', '23', 'org.jbpm.instantiation.Delegation', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1588', '23', 'org.jbpm.instantiation.Delegation', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1589', '23', 'org.jbpm.taskmgmt.log.TaskCreateLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1590', '23', 'org.jbpm.taskmgmt.log.TaskCreateLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1591', '23', 'org.jbpm.taskmgmt.log.TaskCreateLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1592', '23', 'org.jbpm.taskmgmt.log.TaskCreateLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1593', '23', 'org.jbpm.taskmgmt.log.SwimlaneCreateLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1594', '23', 'org.jbpm.taskmgmt.log.SwimlaneCreateLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1595', '23', 'org.jbpm.taskmgmt.log.SwimlaneCreateLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1596', '23', 'org.jbpm.taskmgmt.log.SwimlaneCreateLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1597', '23', 'org.jbpm.taskmgmt.log.TaskEndLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1598', '23', 'org.jbpm.taskmgmt.log.TaskEndLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1599', '23', 'org.jbpm.taskmgmt.log.TaskEndLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1600', '23', 'org.jbpm.taskmgmt.log.TaskEndLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1601', '23', 'org.jbpm.taskmgmt.log.TaskLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1602', '23', 'org.jbpm.taskmgmt.log.TaskLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1603', '23', 'org.jbpm.taskmgmt.log.TaskLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1604', '23', 'org.jbpm.taskmgmt.log.TaskLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1605', '23', 'org.jbpm.taskmgmt.log.SwimlaneAssignLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1606', '23', 'org.jbpm.taskmgmt.log.SwimlaneAssignLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1607', '23', 'org.jbpm.taskmgmt.log.SwimlaneAssignLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1608', '23', 'org.jbpm.taskmgmt.log.SwimlaneAssignLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1609', '23', 'org.jbpm.taskmgmt.log.TaskAssignLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1610', '23', 'org.jbpm.taskmgmt.log.TaskAssignLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1611', '23', 'org.jbpm.taskmgmt.log.TaskAssignLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1612', '23', 'org.jbpm.taskmgmt.log.TaskAssignLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1613', '23', 'org.jbpm.taskmgmt.log.SwimlaneLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1614', '23', 'org.jbpm.taskmgmt.log.SwimlaneLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1615', '23', 'org.jbpm.taskmgmt.log.SwimlaneLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1616', '23', 'org.jbpm.taskmgmt.log.SwimlaneLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1617', '23', 'org.jbpm.taskmgmt.exe.PooledActor', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1618', '23', 'org.jbpm.taskmgmt.exe.PooledActor', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1619', '23', 'org.jbpm.taskmgmt.exe.PooledActor', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1620', '23', 'org.jbpm.taskmgmt.exe.PooledActor', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1621', '23', 'org.jbpm.taskmgmt.exe.TaskInstance', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1622', '23', 'org.jbpm.taskmgmt.exe.TaskInstance', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1623', '23', 'org.jbpm.taskmgmt.exe.TaskInstance', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1624', '23', 'org.jbpm.taskmgmt.exe.TaskInstance', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1625', '23', 'org.jbpm.taskmgmt.exe.TaskMgmtInstance', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1626', '23', 'org.jbpm.taskmgmt.exe.TaskMgmtInstance', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1627', '23', 'org.jbpm.taskmgmt.exe.TaskMgmtInstance', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1628', '23', 'org.jbpm.taskmgmt.exe.TaskMgmtInstance', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1629', '23', 'org.jbpm.taskmgmt.exe.SwimlaneInstance', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1630', '23', 'org.jbpm.taskmgmt.exe.SwimlaneInstance', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1631', '23', 'org.jbpm.taskmgmt.exe.SwimlaneInstance', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1632', '23', 'org.jbpm.taskmgmt.exe.SwimlaneInstance', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1633', '23', 'org.jbpm.taskmgmt.def.TaskMgmtDefinition', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1634', '23', 'org.jbpm.taskmgmt.def.TaskMgmtDefinition', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1635', '23', 'org.jbpm.taskmgmt.def.TaskMgmtDefinition', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1636', '23', 'org.jbpm.taskmgmt.def.TaskMgmtDefinition', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1637', '23', 'org.jbpm.taskmgmt.def.Task', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1638', '23', 'org.jbpm.taskmgmt.def.Task', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1639', '23', 'org.jbpm.taskmgmt.def.Task', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1640', '23', 'org.jbpm.taskmgmt.def.Task', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1641', '23', 'org.jbpm.taskmgmt.def.TaskController', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1642', '23', 'org.jbpm.taskmgmt.def.TaskController', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1643', '23', 'org.jbpm.taskmgmt.def.TaskController', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1644', '23', 'org.jbpm.taskmgmt.def.TaskController', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1645', '23', 'org.jbpm.taskmgmt.def.Swimlane', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1646', '23', 'org.jbpm.taskmgmt.def.Swimlane', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1647', '23', 'org.jbpm.taskmgmt.def.Swimlane', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1648', '23', 'org.jbpm.taskmgmt.def.Swimlane', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1649', '23', 'org.jbpm.scheduler.def.CreateTimerAction', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1650', '23', 'org.jbpm.scheduler.def.CreateTimerAction', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1651', '23', 'org.jbpm.scheduler.def.CreateTimerAction', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1652', '23', 'org.jbpm.scheduler.def.CreateTimerAction', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1653', '23', 'org.jbpm.scheduler.def.CancelTimerAction', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1654', '23', 'org.jbpm.scheduler.def.CancelTimerAction', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1655', '23', 'org.jbpm.scheduler.def.CancelTimerAction', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1656', '23', 'org.jbpm.scheduler.def.CancelTimerAction', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1657', '23', 'org.jbpm.module.def.ModuleDefinition', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1658', '23', 'org.jbpm.module.def.ModuleDefinition', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1659', '23', 'org.jbpm.module.def.ModuleDefinition', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1660', '23', 'org.jbpm.module.def.ModuleDefinition', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1661', '23', 'org.jbpm.module.exe.ModuleInstance', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1662', '23', 'org.jbpm.module.exe.ModuleInstance', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1663', '23', 'org.jbpm.module.exe.ModuleInstance', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1664', '23', 'org.jbpm.module.exe.ModuleInstance', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1665', '23', 'org.jbpm.logging.log.CompositeLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1666', '23', 'org.jbpm.logging.log.CompositeLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1667', '23', 'org.jbpm.logging.log.CompositeLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1668', '23', 'org.jbpm.logging.log.CompositeLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1669', '23', 'org.jbpm.logging.log.MessageLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1670', '23', 'org.jbpm.logging.log.MessageLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1671', '23', 'org.jbpm.logging.log.MessageLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1672', '23', 'org.jbpm.logging.log.MessageLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1673', '23', 'org.jbpm.logging.log.ProcessLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1674', '23', 'org.jbpm.logging.log.ProcessLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1675', '23', 'org.jbpm.logging.log.ProcessLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1676', '23', 'org.jbpm.logging.log.ProcessLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1677', '23', 'org.jbpm.job.Job', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1678', '23', 'org.jbpm.job.Job', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1679', '23', 'org.jbpm.job.Job', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1680', '23', 'org.jbpm.job.Job', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1681', '23', 'org.jbpm.job.Timer', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1682', '23', 'org.jbpm.job.Timer', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1683', '23', 'org.jbpm.job.Timer', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1684', '23', 'org.jbpm.job.Timer', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1685', '23', 'org.jbpm.job.ExecuteNodeJob', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1687', '23', 'org.jbpm.job.ExecuteNodeJob', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1688', '23', 'org.jbpm.job.ExecuteNodeJob', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1689', '23', 'org.jbpm.job.ExecuteActionJob', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1690', '23', 'org.jbpm.job.ExecuteActionJob', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1691', '23', 'org.jbpm.job.ExecuteActionJob', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1692', '23', 'org.jbpm.job.ExecuteActionJob', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1693', '23', 'org.jbpm.file.def.FileDefinition', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1694', '23', 'org.jbpm.file.def.FileDefinition', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1695', '23', 'org.jbpm.file.def.FileDefinition', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1696', '23', 'org.jbpm.file.def.FileDefinition', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1697', '23', 'org.jbpm.bytes.ByteArray', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1698', '23', 'org.jbpm.bytes.ByteArray', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1699', '23', 'org.jbpm.bytes.ByteArray', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1700', '23', 'org.jbpm.bytes.ByteArray', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1701', '23', 'com.travelsky.bravo.components.jbpm.task.domain.TaskVariable', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1702', '23', 'com.travelsky.bravo.components.jbpm.task.domain.TaskVariable', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1703', '23', 'com.travelsky.bravo.components.jbpm.task.domain.TaskVariable', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1704', '23', 'com.travelsky.bravo.components.jbpm.task.domain.TaskVariable', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1705', '23', 'com.travelsky.bravo.components.jbpm.domain.WorkFlowDiagram', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1706', '23', 'com.travelsky.bravo.components.jbpm.domain.WorkFlowDiagram', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1707', '23', 'com.travelsky.bravo.components.jbpm.domain.WorkFlowDiagram', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1708', '23', 'com.travelsky.bravo.components.jbpm.domain.WorkFlowDiagram', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1709', '23', 'com.travelsky.bravo.components.jbpm.domain.TaskBaseDomain', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1710', '23', 'com.travelsky.bravo.components.jbpm.domain.TaskBaseDomain', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1711', '23', 'com.travelsky.bravo.components.jbpm.domain.TaskBaseDomain', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1712', '23', 'com.travelsky.bravo.components.jbpm.domain.TaskBaseDomain', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1713', '23', 'com.travelsky.bravo.components.jbpm.domain.WorkFlowBaseDomain', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1714', '23', 'com.travelsky.bravo.components.jbpm.domain.WorkFlowBaseDomain', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1715', '23', 'com.travelsky.bravo.components.jbpm.domain.WorkFlowBaseDomain', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1716', '23', 'com.travelsky.bravo.components.jbpm.domain.WorkFlowBaseDomain', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1717', '23', 'com.travelsky.bravo.components.common.domain.RequestForLeaveList', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1718', '23', 'com.travelsky.bravo.components.common.domain.RequestForLeaveList', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1719', '23', 'com.travelsky.bravo.components.common.domain.RequestForLeaveList', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1720', '23', 'com.travelsky.bravo.components.common.domain.RequestForLeaveList', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1721', '23', 'com.travelsky.bravo.components.common.domain.News', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1722', '23', 'com.travelsky.bravo.components.common.domain.News', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1723', '23', 'com.travelsky.bravo.components.common.domain.News', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1724', '23', 'com.travelsky.bravo.components.common.domain.News', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1725', '23', 'com.travelsky.bravo.components.common.domain.MenuFunction', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1726', '23', 'com.travelsky.bravo.components.common.domain.MenuFunction', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1727', '23', 'com.travelsky.bravo.components.common.domain.MenuFunction', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1728', '23', 'com.travelsky.bravo.components.common.domain.MenuFunction', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1729', '23', 'com.travelsky.bravo.components.common.domain.EnumerationType', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1730', '23', 'com.travelsky.bravo.components.common.domain.EnumerationType', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1731', '23', 'com.travelsky.bravo.components.common.domain.EnumerationType', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1732', '23', 'com.travelsky.bravo.components.common.domain.EnumerationType', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1733', '23', 'com.travelsky.bravo.components.common.domain.Notice', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1734', '23', 'com.travelsky.bravo.components.common.domain.Notice', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1735', '23', 'com.travelsky.bravo.components.common.domain.Notice', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1736', '23', 'com.travelsky.bravo.components.common.domain.Notice', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1737', '23', 'com.travelsky.bravo.components.common.domain.Department', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1738', '23', 'com.travelsky.bravo.components.common.domain.Department', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1739', '23', 'com.travelsky.bravo.components.common.domain.Department', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1740', '23', 'com.travelsky.bravo.components.common.domain.Department', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1741', '23', 'com.travelsky.bravo.components.common.domain.ProfileMenu', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1742', '23', 'com.travelsky.bravo.components.common.domain.ProfileMenu', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1743', '23', 'com.travelsky.bravo.components.common.domain.ProfileMenu', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1744', '23', 'com.travelsky.bravo.components.common.domain.ProfileMenu', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1745', '23', 'com.travelsky.bravo.components.common.domain.UserCookie', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1746', '23', 'com.travelsky.bravo.components.common.domain.UserCookie', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1747', '23', 'com.travelsky.bravo.components.common.domain.UserCookie', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1748', '23', 'com.travelsky.bravo.components.common.domain.UserCookie', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1749', '23', 'com.travelsky.bravo.components.common.domain.Attachment', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1750', '23', 'com.travelsky.bravo.components.common.domain.Attachment', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1751', '23', 'com.travelsky.bravo.components.common.domain.Attachment', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1752', '23', 'com.travelsky.bravo.components.common.domain.Attachment', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1753', '23', 'com.travelsky.bravo.components.common.domain.AttachmentRelation', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1754', '23', 'com.travelsky.bravo.components.common.domain.AttachmentRelation', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1755', '23', 'com.travelsky.bravo.components.common.domain.AttachmentRelation', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1756', '23', 'com.travelsky.bravo.components.common.domain.AttachmentRelation', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1757', '23', 'com.travelsky.bravo.components.common.domain.Enumeration', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1758', '23', 'com.travelsky.bravo.components.common.domain.Enumeration', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1759', '23', 'com.travelsky.bravo.components.common.domain.Enumeration', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1760', '23', 'com.travelsky.bravo.components.common.domain.Enumeration', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1761', '23', 'com.travelsky.bravo.components.common.domain.SystemInformation', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1762', '23', 'com.travelsky.bravo.components.common.domain.SystemInformation', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1763', '23', 'com.travelsky.bravo.components.common.domain.SystemInformation', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1764', '23', 'com.travelsky.bravo.components.common.domain.SystemInformation', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1765', '23', 'com.travelsky.bravo.components.demo.domain.Student', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1766', '23', 'com.travelsky.bravo.components.demo.domain.Student', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1767', '23', 'com.travelsky.bravo.components.demo.domain.Student', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1768', '23', 'com.travelsky.bravo.components.demo.domain.Student', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1769', '23', 'com.travelsky.bravo.components.demo.domain.Teacher', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1770', '23', 'com.travelsky.bravo.components.demo.domain.Teacher', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1771', '23', 'com.travelsky.bravo.components.demo.domain.Teacher', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1772', '23', 'com.travelsky.bravo.components.demo.domain.Teacher', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1773', '23', 'com.travelsky.bravo.core.security.domain.Permission', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1774', '23', 'com.travelsky.bravo.core.security.domain.Permission', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1775', '23', 'com.travelsky.bravo.core.security.domain.Permission', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1776', '23', 'com.travelsky.bravo.core.security.domain.Permission', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1777', '23', 'com.travelsky.bravo.core.security.domain.AuditHistory', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1778', '23', 'com.travelsky.bravo.core.security.domain.AuditHistory', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1779', '23', 'com.travelsky.bravo.core.security.domain.AuditHistory', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1780', '23', 'com.travelsky.bravo.core.security.domain.AuditHistory', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1781', '23', 'com.travelsky.bravo.core.security.domain.Role', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1782', '23', 'com.travelsky.bravo.core.security.domain.Role', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1783', '23', 'com.travelsky.bravo.core.security.domain.Role', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1784', '23', 'com.travelsky.bravo.core.security.domain.Role', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1785', '23', 'com.travelsky.bravo.core.security.domain.User', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1786', '23', 'com.travelsky.bravo.core.security.domain.User', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1787', '23', 'com.travelsky.bravo.core.security.domain.User', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1788', '23', 'com.travelsky.bravo.core.security.domain.User', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1789', '23', 'com.travelsky.bravo.core.security.domain.Module', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1790', '23', 'com.travelsky.bravo.core.security.domain.Module', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1791', '23', 'com.travelsky.bravo.core.security.domain.Module', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1792', '23', 'com.travelsky.bravo.core.security.domain.Module', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1793', '23', 'com.travelsky.bravo.core.security.domain.Resource', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1794', '23', 'com.travelsky.bravo.core.security.domain.Resource', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1795', '23', 'com.travelsky.bravo.core.security.domain.Resource', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1796', '23', 'com.travelsky.bravo.core.security.domain.Resource', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1797', '23', 'com.travelsky.bravo.core.security.domain.EntityOperatePermissionRelation', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1798', '23', 'com.travelsky.bravo.core.security.domain.EntityOperatePermissionRelation', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1799', '23', 'com.travelsky.bravo.core.security.domain.EntityOperatePermissionRelation', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1800', '23', 'com.travelsky.bravo.core.security.domain.EntityOperatePermissionRelation', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1801', '23', 'com.travelsky.bravo.core.security.domain.UserLoginLog', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1802', '23', 'com.travelsky.bravo.core.security.domain.UserLoginLog', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1803', '23', 'com.travelsky.bravo.core.security.domain.UserLoginLog', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1804', '23', 'com.travelsky.bravo.core.security.domain.UserLoginLog', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1805', '23', 'com.travelsky.bravo.core.security.domain.AuditHistoryDetail', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1806', '23', 'com.travelsky.bravo.core.security.domain.AuditHistoryDetail', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1807', '23', 'com.travelsky.bravo.core.security.domain.AuditHistoryDetail', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1808', '23', 'com.travelsky.bravo.core.security.domain.AuditHistoryDetail', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1809', '23', 'com.travelsky.bravo.core.security.domain.ButtonResource', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1810', '23', 'com.travelsky.bravo.core.security.domain.ButtonResource', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1811', '23', 'com.travelsky.bravo.core.security.domain.ButtonResource', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1812', '23', 'com.travelsky.bravo.core.security.domain.ButtonResource', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1813', '23', 'com.travelsky.bravo.core.domain.DataDictionaryDetail', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1814', '23', 'com.travelsky.bravo.core.domain.DataDictionaryDetail', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1815', '23', 'com.travelsky.bravo.core.domain.DataDictionaryDetail', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1816', '23', 'com.travelsky.bravo.core.domain.DataDictionaryDetail', 'View');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1817', '23', 'com.travelsky.bravo.core.domain.DataDictionary', 'Add');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1818', '23', 'com.travelsky.bravo.core.domain.DataDictionary', 'Delete');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1819', '23', 'com.travelsky.bravo.core.domain.DataDictionary', 'Save');
INSERT INTO `bravo_entity_oper_permis` VALUES ('1820', '23', 'com.travelsky.bravo.core.domain.DataDictionary', 'View');

-- ----------------------------
-- Table structure for `bravo_enumeration`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_enumeration`;
CREATE TABLE `bravo_enumeration` (
  `id` decimal(19,0) NOT NULL COMMENT '主键',
  `enum_type` decimal(19,0) NOT NULL COMMENT '枚举类型',
  `sequences` int(11) NOT NULL COMMENT '顺序号',
  `code` varchar(32) NOT NULL COMMENT '编码',
  `name` varchar(128) NOT NULL COMMENT '名称',
  `comments` varchar(1024) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`),
  KEY `FK_ENU_REF_TYPE` (`enum_type`),
  CONSTRAINT `FK_ENU_REF_TYPE` FOREIGN KEY (`enum_type`) REFERENCES `bravo_enumeration_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_enumeration
-- ----------------------------
INSERT INTO `bravo_enumeration` VALUES ('1', '1', '1', 'URL', 'URL', '页面访问连接资源');
INSERT INTO `bravo_enumeration` VALUES ('2', '1', '2', 'PARTITION', 'PARTITION', '数据分区资源s');
INSERT INTO `bravo_enumeration` VALUES ('6', '8', '4', 'BAOAN', '宝安', '宝安');
INSERT INTO `bravo_enumeration` VALUES ('7', '8', '3', 'NANSHAN', '南山', '南山');
INSERT INTO `bravo_enumeration` VALUES ('8', '8', '2', 'FUTIAN', '福田', '福田');
INSERT INTO `bravo_enumeration` VALUES ('9', '8', '1', 'LUOHU', '罗湖', '罗湖');
INSERT INTO `bravo_enumeration` VALUES ('10', '9', '4', 'DZ04ZM', 'DZ04ZM', 'DZ04ZM');
INSERT INTO `bravo_enumeration` VALUES ('11', '9', '3', 'DZ03ZM', 'DZ03ZM', 'DZ03ZM');
INSERT INTO `bravo_enumeration` VALUES ('12', '9', '2', 'DZ02ZM', 'DZ02ZM', 'DZ02ZM');
INSERT INTO `bravo_enumeration` VALUES ('13', '9', '1', 'DZ01ZM', 'DZ01ZM', 'DZ01ZM');
INSERT INTO `bravo_enumeration` VALUES ('14', '10', '4', 'CANCEL', '暂不修理', 'cancel');
INSERT INTO `bravo_enumeration` VALUES ('15', '10', '3', 'PENDING', '进行中', 'pending');
INSERT INTO `bravo_enumeration` VALUES ('16', '10', '2', 'UNFINSH', '未完成', 'unfinish');
INSERT INTO `bravo_enumeration` VALUES ('17', '10', '1', 'FINISH', '完成', 'finish');
INSERT INTO `bravo_enumeration` VALUES ('18', '12', '4', 'HUWAI', '户外', '');
INSERT INTO `bravo_enumeration` VALUES ('19', '12', '3', 'SHUIBA', '水吧', '');
INSERT INTO `bravo_enumeration` VALUES ('20', '12', '2', 'CHUFANG', '厨房', '');
INSERT INTO `bravo_enumeration` VALUES ('22', '11', '3', 'QITA', '其他', '');
INSERT INTO `bravo_enumeration` VALUES ('23', '11', '2', 'PINGCHANG', '平常', '');
INSERT INTO `bravo_enumeration` VALUES ('24', '11', '1', 'JINJI', '紧急', '');
INSERT INTO `bravo_enumeration` VALUES ('30', '3', '123', 'nntt', '销售', 'nnnnn');
INSERT INTO `bravo_enumeration` VALUES ('41', '3', '123', 'PM', '售后服务', 'fdasdas');
INSERT INTO `bravo_enumeration` VALUES ('45', '3', '7', '2334', '技术支持', 'asasasa');
INSERT INTO `bravo_enumeration` VALUES ('51', '3', '34', 'LJ', '出纳', 'lmm');
INSERT INTO `bravo_enumeration` VALUES ('302', '3', '2', 'GM', '总经理', '总经理');
INSERT INTO `bravo_enumeration` VALUES ('303', '3', '3', 'DM', '部门经理', '部门经理');
INSERT INTO `bravo_enumeration` VALUES ('304', '3', '4', 'STAFFER', '软件工程师', '职员');
INSERT INTO `bravo_enumeration` VALUES ('878', '3', '4325', 'ASDA', 'SDFSD', 'GFDLGJFDLKGLD');
INSERT INTO `bravo_enumeration` VALUES ('879', '50', '1', 'male', '男', '男');
INSERT INTO `bravo_enumeration` VALUES ('880', '50', '2', 'female', '女', '女');
INSERT INTO `bravo_enumeration` VALUES ('881', '54', '2', 'GS', '硕士', '硕士');
INSERT INTO `bravo_enumeration` VALUES ('882', '54', '1', 'BD', '本科', '本科');
INSERT INTO `bravo_enumeration` VALUES ('883', '53', '2', 'USA', '美国', '美国');
INSERT INTO `bravo_enumeration` VALUES ('884', '53', '1', 'CHN', '中国', '中国');
INSERT INTO `bravo_enumeration` VALUES ('885', '54', '3', 'PHD', '博士', '博士');
INSERT INTO `bravo_enumeration` VALUES ('886', '52', '3', 'GD', '广东', '广东省');
INSERT INTO `bravo_enumeration` VALUES ('887', '52', '2', 'HN', '湖南', '湖南省');
INSERT INTO `bravo_enumeration` VALUES ('888', '52', '1', 'HB', '湖北', '湖北省');
INSERT INTO `bravo_enumeration` VALUES ('889', '51', '1', 'WH', '武汉', '武汉');
INSERT INTO `bravo_enumeration` VALUES ('890', '51', '3', 'GD', '广州', '广州');
INSERT INTO `bravo_enumeration` VALUES ('891', '51', '2', 'CS', '长沙', '长沙');
INSERT INTO `bravo_enumeration` VALUES ('892', '51', '1', 'WH', '武汉', '武汉');
INSERT INTO `bravo_enumeration` VALUES ('893', '51', '3', 'GZ', '广州', '广州');
INSERT INTO `bravo_enumeration` VALUES ('894', '51', '2', 'CS', '长沙', '长沙');
INSERT INTO `bravo_enumeration` VALUES ('901', '63', '110', 'ddd', 'open', '公告状态');
INSERT INTO `bravo_enumeration` VALUES ('902', '63', '110', 'gg', 'close', '公告状态');
INSERT INTO `bravo_enumeration` VALUES ('903', '64', '111', 'gg', 'open', '新闻状态');
INSERT INTO `bravo_enumeration` VALUES ('904', '64', '111', 'gg', 'close', '新闻状态');
INSERT INTO `bravo_enumeration` VALUES ('906', '53', '12341243', 'test11', 'test', 'cccccc');
INSERT INTO `bravo_enumeration` VALUES ('6386', '70', '4', 'SC04', 'shortcut04.png', 'shortcut04.png');
INSERT INTO `bravo_enumeration` VALUES ('6387', '70', '3', 'SC03', 'shortcut03.png', 'shortcut03.png');
INSERT INTO `bravo_enumeration` VALUES ('6389', '70', '1', 'SC01', 'shortcut01.png', 'shortcut01.png');
INSERT INTO `bravo_enumeration` VALUES ('6391', '70', '2', 'SC02', 'shortcut02.png', 'shortcut02.png');
INSERT INTO `bravo_enumeration` VALUES ('6392', '70', '5', 'SC05', 'shortcut05.png', 'shortcut05.png');
INSERT INTO `bravo_enumeration` VALUES ('6393', '70', '6', 'SC06', 'shortcut06.png', 'shortcut06.png');
INSERT INTO `bravo_enumeration` VALUES ('6394', '70', '7', 'SC07', 'shortcut07.png', 'shortcut07.png');
INSERT INTO `bravo_enumeration` VALUES ('6395', '70', '8', 'SC08', 'shortcut08.png', 'shortcut08.png');
INSERT INTO `bravo_enumeration` VALUES ('6396', '70', '9', 'SC09', 'shortcut09.png', 'shortcut09.png');
INSERT INTO `bravo_enumeration` VALUES ('6397', '70', '10', 'SC10', 'shortcut10.png', 'shortcut10.png');
INSERT INTO `bravo_enumeration` VALUES ('6398', '52', '4', 'JX', '江西', '江西省');
INSERT INTO `bravo_enumeration` VALUES ('6399', '51', '4', 'NC', '南昌', '南昌市');
INSERT INTO `bravo_enumeration` VALUES ('6404', '3', '333333', 'sc023', 'sss', '22222');
INSERT INTO `bravo_enumeration` VALUES ('6406', '52', '22', '032511', '032511', '032511');
INSERT INTO `bravo_enumeration` VALUES ('6409', '3', '22', '032511', '032511', '032511');
INSERT INTO `bravo_enumeration` VALUES ('6621', '70', '3232', 'dwqd', 'dwd', '322332');
INSERT INTO `bravo_enumeration` VALUES ('6622', '70', '32', 'sada', 'sadas', 'dsffds');
INSERT INTO `bravo_enumeration` VALUES ('6623', '1', '3232', '3232', 'ewewfw', '3232332');
INSERT INTO `bravo_enumeration` VALUES ('6625', '85', '0', 'DESK-THEME', '桌面程序主题', '桌面程序主题');
INSERT INTO `bravo_enumeration` VALUES ('6626', '85', '0', 'DESK-WALLPAPER', '桌面程序背景', '桌面程序背景');

-- ----------------------------
-- Table structure for `bravo_enumeration_type`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_enumeration_type`;
CREATE TABLE `bravo_enumeration_type` (
  `id` decimal(19,0) NOT NULL COMMENT '主键',
  `name` varchar(128) NOT NULL COMMENT '名称',
  `comments` varchar(1024) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_enumeration_type
-- ----------------------------
INSERT INTO `bravo_enumeration_type` VALUES ('1', '资源类型', '描述各种资源类型');
INSERT INTO `bravo_enumeration_type` VALUES ('3', '职务', '人员职务');
INSERT INTO `bravo_enumeration_type` VALUES ('8', '门店地址', '门店地址');
INSERT INTO `bravo_enumeration_type` VALUES ('9', '门店', '门店');
INSERT INTO `bravo_enumeration_type` VALUES ('10', '报call状态', '报call状态');
INSERT INTO `bravo_enumeration_type` VALUES ('11', '优先次序', '优先次序');
INSERT INTO `bravo_enumeration_type` VALUES ('12', '故障位置', '故障位置');
INSERT INTO `bravo_enumeration_type` VALUES ('50', '性别', '男女性别');
INSERT INTO `bravo_enumeration_type` VALUES ('51', '城市', '城市');
INSERT INTO `bravo_enumeration_type` VALUES ('52', '省份', '省份');
INSERT INTO `bravo_enumeration_type` VALUES ('53', '国家', '国家');
INSERT INTO `bravo_enumeration_type` VALUES ('54', '教育程度', '教育程度');
INSERT INTO `bravo_enumeration_type` VALUES ('63', '公告状态', '公告状态');
INSERT INTO `bravo_enumeration_type` VALUES ('64', '新闻状态', '新闻状态');
INSERT INTO `bravo_enumeration_type` VALUES ('70', '快捷图标', '标识用户的快捷键');
INSERT INTO `bravo_enumeration_type` VALUES ('85', '桌面程序资源类型', '桌面程序资源类型');

-- ----------------------------
-- Table structure for `bravo_fun_permis`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_fun_permis`;
CREATE TABLE `bravo_fun_permis` (
  `sys_id` decimal(19,0) NOT NULL COMMENT 'sys_id',
  `per_id` decimal(19,0) NOT NULL COMMENT 'per_id',
  PRIMARY KEY  (`sys_id`,`per_id`),
  KEY `FK_PER_SYS` (`per_id`),
  CONSTRAINT `FK_PER_SYS` FOREIGN KEY (`per_id`) REFERENCES `bravo_permission` (`id`),
  CONSTRAINT `FK_SYS_PER` FOREIGN KEY (`sys_id`) REFERENCES `bravo_menu_function` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_fun_permis
-- ----------------------------
INSERT INTO `bravo_fun_permis` VALUES ('10001', '23');
INSERT INTO `bravo_fun_permis` VALUES ('10002', '23');
INSERT INTO `bravo_fun_permis` VALUES ('10003', '23');
INSERT INTO `bravo_fun_permis` VALUES ('10004', '23');
INSERT INTO `bravo_fun_permis` VALUES ('10005', '23');
INSERT INTO `bravo_fun_permis` VALUES ('10006', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000101', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000102', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000110', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000111', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000112', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000113', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000114', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000118', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000119', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000120', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000121', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000122', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000123', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000124', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000125', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000126', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000127', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000201', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000202', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000203', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000204', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000401', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000402', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000501', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000502', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000503', '23');
INSERT INTO `bravo_fun_permis` VALUES ('1000601', '23');
INSERT INTO `bravo_fun_permis` VALUES ('2000101', '23');
INSERT INTO `bravo_fun_permis` VALUES ('2000102', '23');
INSERT INTO `bravo_fun_permis` VALUES ('2000103', '23');
INSERT INTO `bravo_fun_permis` VALUES ('2000104', '23');
INSERT INTO `bravo_fun_permis` VALUES ('10002', '25');
INSERT INTO `bravo_fun_permis` VALUES ('1000201', '25');
INSERT INTO `bravo_fun_permis` VALUES ('1000202', '25');
INSERT INTO `bravo_fun_permis` VALUES ('1000203', '25');
INSERT INTO `bravo_fun_permis` VALUES ('1000204', '25');
INSERT INTO `bravo_fun_permis` VALUES ('10002', '26');
INSERT INTO `bravo_fun_permis` VALUES ('1000201', '26');
INSERT INTO `bravo_fun_permis` VALUES ('10003', '27');
INSERT INTO `bravo_fun_permis` VALUES ('2000101', '27');
INSERT INTO `bravo_fun_permis` VALUES ('2000102', '27');
INSERT INTO `bravo_fun_permis` VALUES ('2000103', '27');
INSERT INTO `bravo_fun_permis` VALUES ('2000104', '27');
INSERT INTO `bravo_fun_permis` VALUES ('10003', '28');
INSERT INTO `bravo_fun_permis` VALUES ('2000101', '28');

-- ----------------------------
-- Table structure for `bravo_menu_function`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_menu_function`;
CREATE TABLE `bravo_menu_function` (
  `id` decimal(19,0) NOT NULL COMMENT '主键',
  `parent_fun` decimal(19,0) default NULL COMMENT '上级功能',
  `sequences` int(11) NOT NULL COMMENT '顺序号',
  `name` varchar(128) NOT NULL COMMENT '名称',
  `comments` varchar(512) default NULL COMMENT '备注',
  `action` varchar(2014) default NULL COMMENT '点击动作',
  `icon_src` varchar(128) default NULL COMMENT '图标',
  `ifcount` varchar(2) default NULL COMMENT '是否统计(Y/N)',
  PRIMARY KEY  (`id`),
  KEY `FK_SYSFUN_REFC_PARENT` (`parent_fun`),
  CONSTRAINT `FK_SYSFUN_REFC_PARENT` FOREIGN KEY (`parent_fun`) REFERENCES `bravo_menu_function` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_menu_function
-- ----------------------------
INSERT INTO `bravo_menu_function` VALUES ('0', null, '1', '菜单顶层根节点', '菜单顶层根节点', 'javascript:alert(\'菜单顶层根节点\')', null, null);
INSERT INTO `bravo_menu_function` VALUES ('10001', '0', '1', '系统管理', '系统管理', null, null, null);
INSERT INTO `bravo_menu_function` VALUES ('10002', '0', '1', '流程管理', '流程管理', null, null, null);
INSERT INTO `bravo_menu_function` VALUES ('10003', '0', '1', '工作导航', '工作导航', null, null, null);
INSERT INTO `bravo_menu_function` VALUES ('10004', '0', '1', '数据统计', '数据统计', null, null, null);
INSERT INTO `bravo_menu_function` VALUES ('10005', '0', '1', '系统演示', '系统演示', null, null, null);
INSERT INTO `bravo_menu_function` VALUES ('10006', '0', '1', '帮       助', '帮       助', null, null, null);
INSERT INTO `bravo_menu_function` VALUES ('1000101', '10001', '1', '基础数据类型管理', '基础数据类型管理', 'javascript:newTabForDeskPanelWithIcon(\'../common/enumerationType!query.action\',\'基础数据类型管理\',\'baseedit\')', 'baseedit', null);
INSERT INTO `bravo_menu_function` VALUES ('1000102', '10001', '2', '基础数据管理', '基础数据管理', 'javascript:newTabForDeskPanelWithIcon(\'../common/enumeration!query.action\',\'基础数据管理\',\'dataedit\')', 'dataedit', null);
INSERT INTO `bravo_menu_function` VALUES ('1000110', '10001', '110', '系统模块管理', '系统模块管理', 'javascript:newTabForDeskPanelWithIcon(\'../security/module!query.action\',\'系统模块管理\',\'mode\')', 'mode', null);
INSERT INTO `bravo_menu_function` VALUES ('1000111', '10001', '111', '系统资源管理', '系统资源管理', 'javascript:newTabForDeskPanelWithIcon(\'../security/resource!query.action\',\'系统资源管理\',\'resource\')', 'resource', null);
INSERT INTO `bravo_menu_function` VALUES ('1000112', '10001', '112', '系统权限管理', '系统权限管理', 'javascript:newTabForDeskPanelWithIcon(\'../security/permission!query.action\',\'系统权限管理\',\'perm\')', 'perm', null);
INSERT INTO `bravo_menu_function` VALUES ('1000113', '10001', '113', '系统角色管理', '系统角色管理', 'javascript:newTabForDeskPanelWithIcon(\'../security/role!query.action\',\'系统角色管理\',\'role\')', 'role', null);
INSERT INTO `bravo_menu_function` VALUES ('1000114', '10001', '114', '实体修改记录查询', '实体修改记录查询', 'javascript:newTabForDeskPanelWithIcon(\'../security/auditHistory!query.action\',\'实体修改记录查询\',\'daoedit\')', 'daoedit', null);
INSERT INTO `bravo_menu_function` VALUES ('1000118', '10001', '118', '人员管理', '人员管理', 'javascript:newTabForDeskPanelWithIcon(\'../security/user!query.action\',\'人员管理\',\'users\')', 'users', null);
INSERT INTO `bravo_menu_function` VALUES ('1000119', '10001', '119', '部门管理', '部门管理', 'javascript:newTabForDeskPanelWithIcon(\'../common/department!query.action\',\'部门管理\',\'dept\')', 'dept', null);
INSERT INTO `bravo_menu_function` VALUES ('1000120', '10001', '120', '新闻管理', '新闻管理', 'javascript:newTabForDeskPanelWithIcon(\'../common/news!query.action\',\'新闻管理\',\'new\')', 'new', null);
INSERT INTO `bravo_menu_function` VALUES ('1000121', '10001', '121', '公告管理', '公告管理', 'javascript:newTabForDeskPanelWithIcon(\'../common/notice!query.action\',\'公告管理\',\'notice\')', 'notice', null);
INSERT INTO `bravo_menu_function` VALUES ('1000122', '10001', '122', '请假条管理', '请假条管理', 'javascript:newTabForDeskPanelWithIcon(\'../common/requestForLeaveList!query.action\',\'请假条管理\',\'request\')', 'request', null);
INSERT INTO `bravo_menu_function` VALUES ('1000123', '10001', '123', '系统性能监控', '系统性能监控', 'javascript:newTabForDeskPanelWithIcon(\'../report/performanceInspect!monitor.action\',\'系统性能监控\',\'eye\')', 'eye', null);
INSERT INTO `bravo_menu_function` VALUES ('1000124', '10001', '124', '用户登录日志', '用户登陆日志', 'javascript:newTabForDeskPanelWithIcon(\'../security/userLoginLog!query.action\',\'用户登录信息\',\'log\')', 'log', null);
INSERT INTO `bravo_menu_function` VALUES ('1000125', '10001', '125', '当前在线用户', '当前在线用户', 'javascript:newTabForDeskPanelWithIcon(\'../security/onLineUser!query.action\',\'当前在线用户\',\'onusers\')', 'onusers', null);
INSERT INTO `bravo_menu_function` VALUES ('1000126', '10001', '126', '系统信息管理', '系统信息管理', 'javascript:newTabForDeskPanelWithIcon(\'../common/systemInformation!query.action\',\'系统信息管理\',\'info\')', 'info', null);
INSERT INTO `bravo_menu_function` VALUES ('1000127', '10001', '127', '系统按钮管理', '系统按钮管理', 'javascript:newTabForDeskPanelWithIcon(\'../security/buttonResource!query.action\',\'系统按钮管理\',\'button\')', 'button', null);
INSERT INTO `bravo_menu_function` VALUES ('1000201', '10002', '1', '流程定义管理', '流程定义管理', 'javascript:newTabForDeskPanelWithIcon(\'../widgets/workflow/editors/processDefinition!query.action\',\'流程定义管理\',\'dotask\')', 'dotask', null);
INSERT INTO `bravo_menu_function` VALUES ('1000202', '10002', '2', '未完成流程', '未完成流程', 'javascript:newTabForDeskPanelWithIcon(\'../workflow/processInstance!workingProcess.action\',\'未完成流程\',\'task\')', 'task', null);
INSERT INTO `bravo_menu_function` VALUES ('1000203', '10002', '3', '已完成流程118', '已完成流程118', 'javascript:newTabForDeskPanelWithIcon(\'../workflow/processInstance!finishedProcess.action\',\'已完成流程\',\'endtask\')', 'endtask', null);
INSERT INTO `bravo_menu_function` VALUES ('1000204', '10002', '4', '已中止流程', '已中止流程', 'javascript:newTabForDeskPanelWithIcon(\'../workflow/processInstance!suspendedProcess.action\',\'已中止流程\',\'aftask\')', 'aftask', null);
INSERT INTO `bravo_menu_function` VALUES ('1000401', '10004', '1', '系统用户报表', '系统用户报表', 'javascript:newTabForDeskPanelWithIcon(\'../ReportServer?reportlet=user.cpt\',\'系统用户报表\',\'chart\')', 'chart', null);
INSERT INTO `bravo_menu_function` VALUES ('1000402', '10004', '2', '部门报表', '部门报表', 'javascript:newTabForDeskPanelWithIcon(\'../report/report!queryForm.action?queryForm=departmentquery\',\'部门报表\',\'chart\')', 'chart', null);
INSERT INTO `bravo_menu_function` VALUES ('1000501', '10005', '1', '学生管理Demo', '学生管理Demo', 'javascript:newTabForDeskPanelWithIcon(\'../common/student!query.action\',\'系统演示学生管理Demo\',\'users\')', 'users', null);
INSERT INTO `bravo_menu_function` VALUES ('1000502', '10005', '2', '教师管理Demo', '教师管理Demo', 'javascript:newTabForDeskPanelWithIcon(\'../common/teacher!query.action\',\'系统演示教师管理Demo\',\'users\')', 'users', null);
INSERT INTO `bravo_menu_function` VALUES ('1000503', '10005', '3', 'Call项目', 'Call项目', 'javascript:newTabForDeskPanelWithIcon(\'../maintain/call!query.action\',\'报call项目\',\'request\')', 'request', null);
INSERT INTO `bravo_menu_function` VALUES ('1000601', '10006', '1', '关       于', '关       于', 'javascript:opentDeskTopModalWin(\'About\',\'../common/systemInformation!view.action\',\'关于本系统\',500,310)', null, null);
INSERT INTO `bravo_menu_function` VALUES ('2000101', '10003', '1', '待接收任务', '待接收任务', 'javascript:newTabForDeskPanelWithIcon(\'../workflow/task!assignPooledTask.action\',\'待接收任务\',\'betask\')', 'betask', null);
INSERT INTO `bravo_menu_function` VALUES ('2000102', '10003', '2', '处理中任务', '处理中任务', 'javascript:newTabForDeskPanelWithIcon(\'../workflow/task!assignTask.action\',\'处理中任务\',\'dotask\')', 'dotask', null);
INSERT INTO `bravo_menu_function` VALUES ('2000103', '10003', '3', '已处理任务', '已处理任务', 'javascript:newTabForDeskPanelWithIcon(\'../workflow/task!finishedTask.action\',\'已处理任务\',\'aftask\')', 'aftask', null);
INSERT INTO `bravo_menu_function` VALUES ('2000104', '10003', '4', '已结束任务', '已结束任务', 'javascript:newTabForDeskPanelWithIcon(\'../workflow/task!closedTask.action\',\'已结束任务\',\'endtask\')', 'endtask', null);

-- ----------------------------
-- Table structure for `bravo_module`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_module`;
CREATE TABLE `bravo_module` (
  `id` decimal(19,0) NOT NULL COMMENT 'id',
  `parent` decimal(19,0) default NULL COMMENT '父模块',
  `title` varchar(128) default NULL COMMENT '模块名称',
  `comments` varchar(1024) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`),
  KEY `FK_PARENT_MODULE` (`parent`),
  CONSTRAINT `FK_PARENT_MODULE` FOREIGN KEY (`parent`) REFERENCES `bravo_module` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_module
-- ----------------------------
INSERT INTO `bravo_module` VALUES ('15', null, '系统管理', '系统管理模块主要负责管理系统数据');
INSERT INTO `bravo_module` VALUES ('16', null, '流程管理', '流程管理模块主要管理工作流的设计与发布发起');
INSERT INTO `bravo_module` VALUES ('17', null, '工作导航', '工作导航模块主要管理用户的任务');
INSERT INTO `bravo_module` VALUES ('18', null, '数据统计', '数据统计模块主要负责系统数据的报表生成');
INSERT INTO `bravo_module` VALUES ('19', null, '快捷菜单', '快捷菜单模块主要负责显示用户的快捷菜单');
INSERT INTO `bravo_module` VALUES ('20', null, '系统桌面', '系统桌面模块主要负责给用户一个交互主界面');

-- ----------------------------
-- Table structure for `bravo_news`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_news`;
CREATE TABLE `bravo_news` (
  `id` decimal(19,0) NOT NULL COMMENT '主键',
  `title` varchar(256) default NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `publisher` decimal(19,0) default NULL COMMENT '发布人',
  `create_dt` date default NULL COMMENT '发布时间',
  `status` decimal(19,0) default NULL COMMENT '状态',
  PRIMARY KEY  (`id`),
  KEY `FK_NEWS_REF_PUBLISHER` (`publisher`),
  KEY `FK_NEW_REF_STATUS` (`status`),
  CONSTRAINT `FK_NEWS_REF_PUBLISHER` FOREIGN KEY (`publisher`) REFERENCES `bravo_user` (`id`),
  CONSTRAINT `FK_NEW_REF_STATUS` FOREIGN KEY (`status`) REFERENCES `bravo_enumeration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_news
-- ----------------------------
INSERT INTO `bravo_news` VALUES ('0', 'XIN WEN', '', '1', null, null);
INSERT INTO `bravo_news` VALUES ('1', 'asdsa', '&nbsp;asdsadas', '1', null, null);
INSERT INTO `bravo_news` VALUES ('2', '中文测试', '&nbsp;中文测试', '1', null, null);
INSERT INTO `bravo_news` VALUES ('3', 'sadsad', '&nbsp;asdsadsa', '1', null, '904');

-- ----------------------------
-- Table structure for `bravo_notice`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_notice`;
CREATE TABLE `bravo_notice` (
  `id` decimal(19,0) NOT NULL COMMENT '主键',
  `title` varchar(256) default NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `publisher` decimal(19,0) default NULL COMMENT '发布人',
  `create_dt` date default NULL COMMENT '创建时间',
  `publish_start_dt` date default NULL COMMENT '开始发布时间',
  `publish_end_dt` date default NULL COMMENT '结束发布时间',
  `status` decimal(19,0) default NULL COMMENT '状态',
  PRIMARY KEY  (`id`),
  KEY `FK_Reference_29` (`publisher`),
  KEY `FK_NOTICE_REF_STATUS` (`status`),
  CONSTRAINT `FK_NOTICE_REF_STATUS` FOREIGN KEY (`status`) REFERENCES `bravo_enumeration` (`id`),
  CONSTRAINT `FK_Reference_29` FOREIGN KEY (`publisher`) REFERENCES `bravo_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_notice
-- ----------------------------
INSERT INTO `bravo_notice` VALUES ('0', 'sdfds', '&nbsp;adasdsadsa', '1', null, '2011-06-01', '2011-06-30', null);

-- ----------------------------
-- Table structure for `bravo_notice_receiver`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_notice_receiver`;
CREATE TABLE `bravo_notice_receiver` (
  `receiver_id` decimal(19,0) NOT NULL COMMENT '接收人主键',
  `notice_id` decimal(19,0) NOT NULL COMMENT '公告主键',
  PRIMARY KEY  (`receiver_id`,`notice_id`),
  KEY `FK_NOTICE_REFERENCE_RECIVER` (`notice_id`),
  CONSTRAINT `FK_BRAVO_NO_REF_USS` FOREIGN KEY (`receiver_id`) REFERENCES `bravo_user` (`id`),
  CONSTRAINT `FK_NOTICE_REFERENCE_RECIVER` FOREIGN KEY (`notice_id`) REFERENCES `bravo_notice` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_notice_receiver
-- ----------------------------

-- ----------------------------
-- Table structure for `bravo_permission`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_permission`;
CREATE TABLE `bravo_permission` (
  `id` decimal(19,0) NOT NULL COMMENT 'id',
  `name` varchar(128) NOT NULL COMMENT '名称',
  `comments` varchar(512) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_permission
-- ----------------------------
INSERT INTO `bravo_permission` VALUES ('23', '系统管理', '该权限可以使用系统全部功能，访问系统所有资源');
INSERT INTO `bravo_permission` VALUES ('24', '系统登陆', '该权限无任何功能，仅能通过登陆界面');
INSERT INTO `bravo_permission` VALUES ('25', '流程定义', '该权限可以使用流程定义管理功能，访问流程定义管理资源');
INSERT INTO `bravo_permission` VALUES ('26', '匿名登陆', '该权限无任何功能，可以访问系统桌面资源');
INSERT INTO `bravo_permission` VALUES ('27', '人事管理', '该权限可以访问工作导航功能，访问工作导航相关资源');
INSERT INTO `bravo_permission` VALUES ('28', '工作导航', '该权限仅能使用工作导航功能。');
INSERT INTO `bravo_permission` VALUES ('29', '学生实体增删改查', '该权限可以对实体数据进行增删该操作');
INSERT INTO `bravo_permission` VALUES ('30', '按钮权限', '该权限用于表示受保护的实体按钮');

-- ----------------------------
-- Table structure for `bravo_permis_resc`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_permis_resc`;
CREATE TABLE `bravo_permis_resc` (
  `permis_id` decimal(19,0) NOT NULL COMMENT '权限项ID',
  `resc_id` decimal(19,0) NOT NULL COMMENT '系统资源ID',
  PRIMARY KEY  (`permis_id`,`resc_id`),
  KEY `FK_PER_RES` (`resc_id`),
  CONSTRAINT `FK_PER_RES` FOREIGN KEY (`resc_id`) REFERENCES `bravo_resource` (`id`),
  CONSTRAINT `FK_RES_PER` FOREIGN KEY (`permis_id`) REFERENCES `bravo_permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_permis_resc
-- ----------------------------
INSERT INTO `bravo_permis_resc` VALUES ('23', '2');
INSERT INTO `bravo_permis_resc` VALUES ('27', '2');
INSERT INTO `bravo_permis_resc` VALUES ('23', '114');
INSERT INTO `bravo_permis_resc` VALUES ('25', '114');
INSERT INTO `bravo_permis_resc` VALUES ('27', '114');
INSERT INTO `bravo_permis_resc` VALUES ('23', '115');
INSERT INTO `bravo_permis_resc` VALUES ('27', '115');
INSERT INTO `bravo_permis_resc` VALUES ('28', '115');
INSERT INTO `bravo_permis_resc` VALUES ('23', '116');
INSERT INTO `bravo_permis_resc` VALUES ('27', '116');
INSERT INTO `bravo_permis_resc` VALUES ('28', '116');

-- ----------------------------
-- Table structure for `bravo_profile_menu`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_profile_menu`;
CREATE TABLE `bravo_profile_menu` (
  `id` decimal(19,0) NOT NULL COMMENT '主键',
  `user_id` decimal(19,0) default NULL COMMENT '用户',
  `menu_id` decimal(19,0) NOT NULL COMMENT '菜单',
  `icon` varchar(128) default NULL COMMENT '图标',
  `sequence` int(11) default NULL COMMENT '顺序号',
  PRIMARY KEY  (`id`),
  KEY `FK_PROFILE_REF_MENU` (`menu_id`),
  KEY `FK_PROFILE_REF_USER` (`user_id`),
  CONSTRAINT `FK_PROFILE_REF_MENU` FOREIGN KEY (`menu_id`) REFERENCES `bravo_menu_function` (`id`),
  CONSTRAINT `FK_PROFILE_REF_USER` FOREIGN KEY (`user_id`) REFERENCES `bravo_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_profile_menu
-- ----------------------------
INSERT INTO `bravo_profile_menu` VALUES ('0', '1', '1000122', 'shortcut05.png', '4343');
INSERT INTO `bravo_profile_menu` VALUES ('6', '1', '1000201', 'shortcut01.png', '434343');
INSERT INTO `bravo_profile_menu` VALUES ('7', '1', '1000202', 'shortcut02.png', '334');
INSERT INTO `bravo_profile_menu` VALUES ('9', '1', '1000502', 'shortcut03.png', '1');
INSERT INTO `bravo_profile_menu` VALUES ('11', '1', '1000501', 'shortcut04.png', '3');
INSERT INTO `bravo_profile_menu` VALUES ('14', '40', '2000101', 'shortcut08.png', null);
INSERT INTO `bravo_profile_menu` VALUES ('15', '1', '1000101', 'shortcut10.png', '3');
INSERT INTO `bravo_profile_menu` VALUES ('16', '1', '1000123', 'shortcut07.png', '555');
INSERT INTO `bravo_profile_menu` VALUES ('17', '1', '1000402', 'shortcut10.png', '0');
INSERT INTO `bravo_profile_menu` VALUES ('18', '1', '1000122', 'shortcut09.png', '34');

-- ----------------------------
-- Table structure for `bravo_request_for_leave`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_request_for_leave`;
CREATE TABLE `bravo_request_for_leave` (
  `ID` bigint(20) NOT NULL,
  `TITLE` text NOT NULL,
  `PROPOSER` bigint(20) NOT NULL,
  `REASON` longtext NOT NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_request_for_leave
-- ----------------------------
INSERT INTO `bravo_request_for_leave` VALUES ('1', 'asdsads', '1', '&nbsp;sadsad<BR>asdasjdsakdkas<BR>asjdksajda<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; wfsadkjsandwq<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; wefrjewojfoiwjf<BR><BR>woeoiweor<BR><BR><BR><BR>qroewqoi&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; qwrewqoowq');
INSERT INTO `bravo_request_for_leave` VALUES ('2', '阿斯顿撒', '1', '&nbsp;阿斯顿撒旦aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');
INSERT INTO `bravo_request_for_leave` VALUES ('3', 'sas', '1', '&nbsp;sadsadsadas');

-- ----------------------------
-- Table structure for `bravo_resource`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_resource`;
CREATE TABLE `bravo_resource` (
  `id` decimal(19,0) NOT NULL COMMENT 'id',
  `name` varchar(128) default NULL COMMENT '名称',
  `res_type` decimal(19,0) default NULL COMMENT '资源类型',
  `res_string` varchar(256) default NULL COMMENT '资源字段',
  `comments` varchar(512) default NULL COMMENT '备注',
  `module` decimal(19,0) default NULL COMMENT '所属模块',
  PRIMARY KEY  (`id`),
  KEY `FK_RES_MODULE` (`module`),
  KEY `FK_RESTYPE_ENU` (`res_type`),
  CONSTRAINT `FK_RESTYPE_ENU` FOREIGN KEY (`res_type`) REFERENCES `bravo_enumeration` (`id`),
  CONSTRAINT `FK_RES_MODULE` FOREIGN KEY (`module`) REFERENCES `bravo_module` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_resource
-- ----------------------------
INSERT INTO `bravo_resource` VALUES ('2', 'dp1', '2', 'com.travelsky.bravo.components.common.domain.MenuFunction:[sequences = 2]', 'dp1', '16');
INSERT INTO `bravo_resource` VALUES ('114', 'Design_Work_Flow', '1', '/widgets/workflow/editors/processDefinition!query.action', '流程定义管理', '16');
INSERT INTO `bravo_resource` VALUES ('115', 'desktop', '1', '/common/desktop!index.action', null, '20');
INSERT INTO `bravo_resource` VALUES ('116', 'assignPooledtask', '1', '/workflow/task!assignPooledTask.action', null, '17');

-- ----------------------------
-- Table structure for `bravo_role`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_role`;
CREATE TABLE `bravo_role` (
  `id` decimal(19,0) NOT NULL COMMENT 'id',
  `name` varchar(128) NOT NULL COMMENT '名称',
  `comments` varchar(1024) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_role
-- ----------------------------
INSERT INTO `bravo_role` VALUES ('1', '系统管理员', '该角色拥有系统最高权限');
INSERT INTO `bravo_role` VALUES ('34', '匿名角色', '该角色无任何功能，但可以登陆桌面');
INSERT INTO `bravo_role` VALUES ('35', '工作流设计人员', '该角色仅拥有流程设计功能');
INSERT INTO `bravo_role` VALUES ('36', '未授权人员', '该角色无任何访问功能');
INSERT INTO `bravo_role` VALUES ('37', '人事管理人员', '该角色可以访问工作导航功能');
INSERT INTO `bravo_role` VALUES ('38', '普通员工', '该角色仅能访问工作导航功能');

-- ----------------------------
-- Table structure for `bravo_role_permis`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_role_permis`;
CREATE TABLE `bravo_role_permis` (
  `role_id` decimal(19,0) NOT NULL COMMENT '角色ID',
  `permis_id` decimal(19,0) NOT NULL COMMENT '权限项ID',
  PRIMARY KEY  (`role_id`,`permis_id`),
  KEY `FK_ROLE_PER` (`permis_id`),
  CONSTRAINT `FK_PER_ROLE` FOREIGN KEY (`role_id`) REFERENCES `bravo_role` (`id`),
  CONSTRAINT `FK_ROLE_PER` FOREIGN KEY (`permis_id`) REFERENCES `bravo_permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_role_permis
-- ----------------------------
INSERT INTO `bravo_role_permis` VALUES ('1', '23');
INSERT INTO `bravo_role_permis` VALUES ('34', '23');
INSERT INTO `bravo_role_permis` VALUES ('35', '23');
INSERT INTO `bravo_role_permis` VALUES ('36', '23');
INSERT INTO `bravo_role_permis` VALUES ('37', '23');
INSERT INTO `bravo_role_permis` VALUES ('38', '23');
INSERT INTO `bravo_role_permis` VALUES ('36', '24');
INSERT INTO `bravo_role_permis` VALUES ('35', '25');
INSERT INTO `bravo_role_permis` VALUES ('34', '26');
INSERT INTO `bravo_role_permis` VALUES ('37', '27');
INSERT INTO `bravo_role_permis` VALUES ('38', '28');

-- ----------------------------
-- Table structure for `bravo_saas_group`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_saas_group`;
CREATE TABLE `bravo_saas_group` (
  `code` varchar(64) NOT NULL COMMENT '编码',
  `name` varchar(64) NOT NULL COMMENT '组名',
  `database_name` varchar(64) NOT NULL COMMENT '数据库名',
  `driver_className` varchar(256) NOT NULL COMMENT '驱动名',
  `database_url` varchar(256) NOT NULL COMMENT '数据库连接',
  `database_username` varchar(64) NOT NULL COMMENT '用户名',
  `database_password` varchar(64) NOT NULL COMMENT '密码',
  PRIMARY KEY  (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_saas_group
-- ----------------------------
INSERT INTO `bravo_saas_group` VALUES ('saas1', 'saas1', 'saas1', 'com.mysql.jdbc.Driver', 'jdbc:mysql://localhost/saas1?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=utf-8', 'root', 'bravo');
INSERT INTO `bravo_saas_group` VALUES ('saas2', 'saas2', 'saas2', 'com.mysql.jdbc.Driver', 'jdbc:mysql://localhost/saas2?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=utf-8', 'root', 'bravo');
INSERT INTO `bravo_saas_group` VALUES ('saas3', 'saas3', 'saas3', 'com.mysql.jdbc.Driver', 'jdbc:mysql://localhost/saas3?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=utf-8', 'root', 'bravo');
INSERT INTO `bravo_saas_group` VALUES ('saas4', 'saas4', 'saas4', 'com.mysql.jdbc.Driver', 'jdbc:mysql://localhost/saas4?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=utf-8', 'root', 'bravo');

-- ----------------------------
-- Table structure for `bravo_seq_record`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_seq_record`;
CREATE TABLE `bravo_seq_record` (
  `ID` varchar(128) NOT NULL,
  `SEQUENCES` bigint(20) NOT NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_seq_record
-- ----------------------------
INSERT INTO `bravo_seq_record` VALUES ('com.cutty.bravo.components.common.domain.Enumeration', '26');
INSERT INTO `bravo_seq_record` VALUES ('com.cutty.bravo.components.common.domain.EnumerationType', '15');
INSERT INTO `bravo_seq_record` VALUES ('com.cutty.bravo.components.common.domain.News', '4');
INSERT INTO `bravo_seq_record` VALUES ('com.cutty.bravo.components.common.domain.Notice', '1');
INSERT INTO `bravo_seq_record` VALUES ('com.cutty.bravo.components.common.domain.SystemInformation', '1');
INSERT INTO `bravo_seq_record` VALUES ('com.cutty.bravo.components.common.domain.UserCookie', '15');
INSERT INTO `bravo_seq_record` VALUES ('com.cutty.bravo.components.demo.domain.Student', '16');
INSERT INTO `bravo_seq_record` VALUES ('com.cutty.bravo.components.demo.domain.Teacher', '7');
INSERT INTO `bravo_seq_record` VALUES ('com.cutty.bravo.components.jbpm.domain.WorkFlowBaseDomain', '4');
INSERT INTO `bravo_seq_record` VALUES ('com.cutty.bravo.components.jbpm.domain.WorkFlowDiagram', '5');
INSERT INTO `bravo_seq_record` VALUES ('com.cutty.bravo.components.jbpm.task.domain.TaskVariable', '17');
INSERT INTO `bravo_seq_record` VALUES ('com.cutty.bravo.core.security.domain.User', '3');
INSERT INTO `bravo_seq_record` VALUES ('com.cutty.bravo.core.security.domain.UserLoginLog', '26');
INSERT INTO `bravo_seq_record` VALUES ('com.cutty.bravo.maintain.domain.Call', '61');
INSERT INTO `bravo_seq_record` VALUES ('com.cutty.bravo.maintain.domain.CallDetail', '18');

-- ----------------------------
-- Table structure for `bravo_student`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_student`;
CREATE TABLE `bravo_student` (
  `ID` decimal(19,0) NOT NULL,
  `NAME` varchar(20) default NULL,
  `GENDER` varchar(20) default NULL,
  `HEAD_TEACHER` decimal(19,0) default NULL,
  `AGE` double(3,0) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_student
-- ----------------------------
INSERT INTO `bravo_student` VALUES ('2', 'wda', '男', '3', '33');

-- ----------------------------
-- Table structure for `bravo_system_info`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_system_info`;
CREATE TABLE `bravo_system_info` (
  `ID` decimal(19,0) NOT NULL COMMENT 'ID',
  `SYSTEM_NAME` varchar(128) default NULL COMMENT '系统名称',
  `VERSION` varchar(64) default NULL COMMENT '版本号',
  `RELEASE_DATE` date default NULL COMMENT '发布日期',
  `COMMENTS` varchar(1024) default NULL COMMENT '备注信息',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_system_info
-- ----------------------------
INSERT INTO `bravo_system_info` VALUES ('1', 'Bravo', '1.1.2', '2011-06-01', '');

-- ----------------------------
-- Table structure for `bravo_teacher`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_teacher`;
CREATE TABLE `bravo_teacher` (
  `ID` decimal(19,0) NOT NULL,
  `NAME` varchar(20) default NULL,
  `GENDER` varchar(20) default NULL,
  `AGE` decimal(19,0) default NULL,
  `WORK_AGE` decimal(19,0) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_teacher
-- ----------------------------
INSERT INTO `bravo_teacher` VALUES ('0', '贺老师', '男', '28', '2233111');
INSERT INTO `bravo_teacher` VALUES ('1', '胡符', '男', '30', '8');
INSERT INTO `bravo_teacher` VALUES ('2', '宿云', '女', '27', '5');
INSERT INTO `bravo_teacher` VALUES ('3', '李小多', '女', '18', '1');
INSERT INTO `bravo_teacher` VALUES ('4', 'aSAs', '1123', '3231', '3232');
INSERT INTO `bravo_teacher` VALUES ('5', 'asdas', '3232', '32', '32');
INSERT INTO `bravo_teacher` VALUES ('6', '测试SAAS', '2332', '3232', '232');
INSERT INTO `bravo_teacher` VALUES ('11', '卢秀梅', '女', '40', '12');
INSERT INTO `bravo_teacher` VALUES ('21', '马旭', '男', '38', '19');
INSERT INTO `bravo_teacher` VALUES ('22', '李玉兰', '女', '40', '20');
INSERT INTO `bravo_teacher` VALUES ('24', '李云飞', '男', '32', '12');

-- ----------------------------
-- Table structure for `bravo_teacher_student`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_teacher_student`;
CREATE TABLE `bravo_teacher_student` (
  `STUDENT_ID` decimal(19,0) NOT NULL,
  `TEACHER_ID` decimal(19,0) NOT NULL,
  PRIMARY KEY  (`STUDENT_ID`,`TEACHER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_teacher_student
-- ----------------------------

-- ----------------------------
-- Table structure for `bravo_user`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_user`;
CREATE TABLE `bravo_user` (
  `id` decimal(19,0) NOT NULL COMMENT '主键',
  `loginid` varchar(128) NOT NULL COMMENT '登陆用户',
  `passwd` varchar(256) NOT NULL COMMENT '登陆密码',
  `user_name` varchar(128) default NULL COMMENT '用户名称',
  `eng_name` varchar(64) default NULL COMMENT '英文名称',
  `gender` decimal(19,0) NOT NULL COMMENT '性别',
  `duty` decimal(19,0) default NULL COMMENT '职务',
  `labour` varchar(64) default NULL COMMENT '工号',
  `accession_dt` date default NULL COMMENT '入职时间',
  `department` decimal(19,0) default NULL COMMENT '所属部门',
  `education` decimal(19,0) default NULL COMMENT '学历',
  `country` decimal(19,0) default NULL COMMENT '国家',
  `province` decimal(19,0) NOT NULL COMMENT '省份',
  `city` decimal(19,0) default NULL COMMENT '城市',
  `address` varchar(512) default NULL COMMENT '住址',
  `postalcode` varchar(8) default NULL COMMENT '邮政编码',
  `telephone` varchar(32) default NULL COMMENT '联系电话',
  `mobilephone` varchar(32) default NULL COMMENT '移动电话',
  `email` varchar(256) default NULL COMMENT '电子邮件',
  `fax` varchar(32) default NULL COMMENT '传真号码',
  `comments` varchar(512) default NULL COMMENT '备注',
  `last_updter` decimal(19,0) default NULL COMMENT '最新维护人',
  `last_updt` date default NULL COMMENT '最新维护时间',
  `STATUS` decimal(19,0) default NULL COMMENT '是否启用',
  `photo` longblob COMMENT '个人照片',
  PRIMARY KEY  (`id`),
  KEY `FK_USER_DEPARTMENT` (`department`),
  KEY `FK_GENDER_ENU` (`gender`),
  KEY `FK_EDUCATION_ENU` (`education`),
  KEY `FK_CITY_ENU` (`city`),
  KEY `FK_COUNTRY_ENU` (`country`),
  KEY `FK_PROVINCE_ENU` (`province`),
  KEY `FK_DUTY_ENU` (`duty`),
  CONSTRAINT `FK_CITY_ENU` FOREIGN KEY (`city`) REFERENCES `bravo_enumeration` (`id`),
  CONSTRAINT `FK_COUNTRY_ENU` FOREIGN KEY (`country`) REFERENCES `bravo_enumeration` (`id`),
  CONSTRAINT `FK_DUTY_ENU` FOREIGN KEY (`duty`) REFERENCES `bravo_enumeration` (`id`),
  CONSTRAINT `FK_EDUCATION_ENU` FOREIGN KEY (`education`) REFERENCES `bravo_enumeration` (`id`),
  CONSTRAINT `FK_GENDER_ENU` FOREIGN KEY (`gender`) REFERENCES `bravo_enumeration` (`id`),
  CONSTRAINT `FK_PROVINCE_ENU` FOREIGN KEY (`province`) REFERENCES `bravo_enumeration` (`id`),
  CONSTRAINT `FK_USER_DEPARTMENT` FOREIGN KEY (`department`) REFERENCES `bravo_department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_user
-- ----------------------------
INSERT INTO `bravo_user` VALUES ('1', 'admin', '21232f297a57a5a743894a0e4a801fc3', '吴佳兴', 'jason', '879', '51', '1', '2011-06-08', '0', '885', '884', '887', '892', '3232dsfdsfdsfds', '510280', '121212177', '23424', 'wujx14cn@gmail.com', '3322332', 's', '1', '2009-02-04', '1', 0xFFD8FFE000104A46494600010101004800480000FFDB00430006040506050406060506070706080A100A0A09090A140E0F0C1017141818171416161A1D251F1A1B231C1616202C20232627292A29191F2D302D283025282928FFDB0043010707070A080A130A0A13281A161A2828282828282828282828282828282828282828282828282828282828282828282828282828282828282828282828282828FFC00011080258018F03012200021101031101FFC4001C0000020203010100000000000000000000030402050106070008FFC400431000020103030205020404040503030403010203000411051221314106132251611471073281A12391B1C1154252D124334362F01672E12534F1081793A2538292FFC4001A010002030101000000000000000000000000020103040506FFC40029110101000202030001050002030101000000010211032104123141051322325123334261711424FFDA000C03010002110311003F00D10254D17069CFA72ABCF14368B69AECFAB9FEC195CFDAA4A302A61702A48A49E953A406B19639E68E9195E79A660838CF7A6045D38CD4C88F62F0C6CC69A0368A2A47C8ED59688E6A6A36164F63448E3CD49632051E3438A548457029BB45C30343F2CB353D6F0F1CF4A980F46D85A5E790638AF3023DE8663269802D963915156393CD30C840C5616125BA501000F5CD1539344109C60D1638A80C2A9CD5A697726370334985C018A2C4B86CF7A0373B4D406C1CD124BF1EF5A9C7332F4CD371177E6A34176D7C7B1FDEB02E99FBD57471313CD3F6B6E735291630CEDCD58C110F6AF43081DA9C8D3155E59049138A9815E4C54CE00AAC20682D4476A093934D117E339A76C1BCBB98DBE6921D69880E181F6E68CE6F1A59755B80E9545E238B3E5C9FA55D5B3EE850E7A8A57598C3DA371D39AE6F15F4CDBB967B60E75E21B42F6AF81DAB85EB9018B50954FBD7D1DA9C1E65AB7DAB83F8DADFC9D518E3F357473BB61C3E35E0D85AC452112823B1A14AD8152817BD573E9AFCD3E91FC35F195A6A1A4436B772AC7751285F51EB5BADCEAB656F19925B88D540CF2D5F235BDC3C4D9462AC3B834C4FA9DD4A9B649E461EC58D557831CAECF8F3678CD3A6FE29F8FD6FED9F4DD29CF927892407A8F6AE4F0C9853CF7A88901077726969250AC76F4AB71C663350965BDD3324EC3355D70E5C9CD49E6C8EB4BBB8A2D131065C01494C7AD3133F149C8D49955B8979695714CC9406AACF896614071CD32F40614AB1DCB50D2F621E2B5C9A1D921C8E2BAA6A9660C478AD07538364EDC57518D549167A8A622B7E451618F776A7638B18E280C450003F4A612D41A62DE2DD8C8AB05806DE94056ADB81F7A849173D2ACDA2A8B439ED40560407B5163881ED4E0B5E69A8ED70BC50092C1C8A6A288E29858F0C38A32A0A3409B424F6A90870B4E8406BC52A6021E4E4D49631D29C74C2F4A1A27AA800BA71D39A82210DCD3FE57BD7BCA1402C31B79ACA13BB8A9BAE2B09F6A807204CE322AD2D62F4F4A56C90362AE604551CD46D2CC310C8E2AC608F1418F6AE0647DAAAF57F15695A3C891DDDE4513336D0339C7DE9686CD1AF1D28A4151C026B9947F8ADA51BD9A147CAA9DA8DB7F37CD27E21FC5BB7B3465B0D93C98EBB7005556C4EABAB9900193C7DF8A14B70AAB9CD7CECDF8B7AB124B985FE0AF15E8BF15F506189846CA3B5133C53E95F409BB465C823DBAD08DCA93D6BE7EBEFC4DBD78C0B3509CE4EEE69CF0F7E24DC2B88EFD7727620D37EE628B85D3BBFD4AFBD1E1B95CD68DA6EB916A3009A090303DB3D2AD60BC3EF4FF55FC751D1A7135A0C751C537729BE2653DC56AFE0BBCF35A589BDB35B6372B5CBE5C7D392B771DF6C1AACF16E81D7E0D712FC4BB6D970AE077C5773B8C2DCCD19AE57F89F6A0DBBB8EC735B71BB8C7AD5D38DCC7D64537081E552573FF3BE29EB7E62A21A861B0C79A9336450A4E1EBCDD2A294291C8EF4B3B934594D2EE694F11673EF42773EF5E7341634B4FA62439A03D4D8D0D8F1509D00F416A3BD05A94E0B8A0B0A3B50DAA3467D6B7F16633C0AE7DE20840989C77AE97789FC335A0F88A3C3938AE96D92A8AD50669F4407148DBF0D5611738C5497672DE3029DCE169480E3AD36395A9484466B007344C01D6B2003404E2009E688C401C541062B257353032A01E6A4A467A54A34E3A561C63E28A1218A96E140DC6B00927AD1102C8722A287158CFBD4777B515264B8C505E5C6686EE4F7A5A62C2A026D292698B7049E4502DA362C370C8A6AE678ED6C6E2E1C810C49B9DCF402A285940E1785650ABF99D8F02949FC61A358B159B5BB7057AAC6371AE11E26F16EA9AFDC3C76CCF058063B514E323E7DEA8E1B12C99DC58838C633FBD65CFC892EA2EC78B7F5D6BC71F8A87CA9ACB426DCE703EA4718F7C0AE45732DE5ECCD2CECEECC4E59CE49346F2C460871823A13464933FF00508F6C3567CF96E6BA612138ECAE392AC8C3BFAB06979E2B94C9653B7DC1C8AB4178D6EFF983AE30C0906A3737D0B60C6369C723B557ED4DEB149E61EE48358573CF35632FD25C211B82BF5E9483DAC9171D47B8A3D8693594F049A62198F1492823F4A2C479C9F6A6F647ABA178075F6B2D4A38266FE04BE93EC0F6AEC96E738C73F35F36DB93BE3F249DC00E47BD76EF02EAEDA869D1C73FFF007108DAD9EE077AD5C59ED9B931D574DF075CF95AB443380FE9AE96304571FD365305D4320E36B0E7F5AEBB03878D587423359FCCC7F94AB3C6CBEE35AD6BDFC0D451BB3D685E3E51369F28E3A5741F19C645AC730EAAD5CDFC51296B27FB568E1EF8F6A3966B370ABB389CFDE9EB4394A06A16ECB3C87B66A566FC62A254D7AE061F358CE56A77233CD054F14540520A5A4A6A4A565A53CA03505A8CD417A4BF4E1374A113C515BA509A8306D4171466A1375A1213541A8CC284D4A67D8B769FC335CFBC5070E6B7DBE994446B9D789E40D2E3E6BA123264A580E58E2ACEDD7D22ABAD87AAAE204C20A62A68B83C74A6E338C5062033467153A4B0E7AD615BDEB05722A0ABCD1A06E3208A983414071C75A2A2E454C09EFC0A831CF5A932D471CD4863195E942248E28EA78A14A33D284501DCFBD78393DE86F91D6B2A32296882A364D1E38C3B0C8A0C319DC300926A3A9EAD6FA541E65CB039E02A724D45BA49BBB748212A5D538CB37B28AE49E35F11BEA3786049E41A7AF02DD0FA588EF8FF007A0F897C557BAD4B3C30B3456FBB040C64E3B7CD5047008D72BC330FCC467F45F9F9AC5CDCDBEB168E3E2D77513733326D8225441C92C335E372C8A42B3337ED9A34614F98A9808ABDCF24FF007A0C90CAF1EF0BB33D00E7359979396477F53B0C76A06F6249009A73CB489099092C4718EB588E2327A8AEC8CF200EA7F5A8D8D2BA672CD858F1F142F29C9FCB57915879A3F8711F7CD48E9F283CAEDC546828C44DEC2B397031CE3B60D58CB68EA4E7713EC41A4A55C1219307ED8A0096FE597C4D9192067E29992D54ABC91F038C0279A440231FD8D376A59C18C1E4F38F7A9D835A72B24E0632C3903E7DEBAF7816CE4B652E47E64CE7B5726D1AE96CEFD5A58C328201CF35D97C357F1DD5B07471E5F415AB819B97EB6F80FA41E4F7AEB1E1D9C5C6916CFF00F6E0FE95C9219430013B5745F015C6FD39E227FE5B537958EF0D9386EB35B788A0F3F4B9D71920645729D6A2F32023E2BB25C26F85D4F3B948AE4D7EA44D3C6DD5588AABC5CAFA587F226B2DB936BF66B12C990335AA5BB15931DAB72F1D3B2CBB13853F9AB4A5FCD4F36497A3D21CA5017AD30885A30683B70F4DA230E2959053AE295907351A3624DC6282FCD3120A03525FAB407A111467A111506098540D15850DA8486D4361CD19A86C294DB7D2FA8EA8361E6B4BD4EE0CB216A8CD78CE39A49E4DD5D261B766ACD89619ABF89C6C15AED913B855FC43F863DEA523C4D96A60AE6948F834CAB13D2A769659702859E688C4915844068FA058BB13461814355E2A58C51F024CE2A3D68720C9CD1108C54EC207AD6072689B79CF6AC05E6A282D380A39AF4299E4F4A3C9196231DA8B1444464F1EF8A80084C0DC7007B9EC077AE4BE3CD71AF2792D6D0FF055B963FE6F9FB56E1E3FD6BE82C9ACAD9B3732FA48CFE55F7FD6B92445E572F339DA4E4FCE2B2791C9FF008C5FC787E68968DB1488FD79E49EC4FF00B5582A3E04B2E497E1001F98FDBD85296AD1802575F403D077FF00CE2AC4F98EBE746A4B824267FCA3DEB1B406B6ED10695D4B13C2E3FA0A567F37612645DC0E1803D699B9952D80F31CC848C827A0FD3DEAB10ABBA281EAECBD80F73409363C56BE710AB92C7F53C7F4A74F976C36E0EF3C1257FA512DDA354F2FCC738EAA4700FDBF972698B7B07B9563BDD53E0F23F9D25B27D3CC6DF8A39350313E55B2A78CA9C1FE54033CF7721D8CE71D46EAD964F0B34F992374120E76B26D0E33EFEF53D3F458A393F890BAC83E33B4FBFBD2FB9FF6EB517B8B9C9059B03FD55179677386E6BA4C9E1C86E8B9911A3919793EE691FF00D24C92FAB1B1C02323A52FEE41FB55CFE3C86191C679E2988B7472ABAE148FD6B6FB8F0CFA771251C9230DDB1EF55575A7F94EA222AD9F6E829F1CA52E58E88DEC68C1654F413C91561E1DD666D3A756566299F5007A8A46F51D41CB11C6303A5271FA4D5DC76C67CF17D17E1DD460D42C564B738CF506B7FF00015C14D424849E1D78FB8AE05F85BA96267B373C3F2BCD766F0ECFF4DAC5B3E782429FD6B6DD67C56327F5CB6EAFFE5AE59E2685A1D6EE571F98EE1F6AEA4BC8E3DAB43F1E45E4EA104D8E245DA4FCD61F1EEB2D34F9137258E27E3988ACED91D4645684EDB64E6BAAF8E6DFCD8564519C0C1AE517685643F06B45ACF8D956B68C1A3E2A122E1E85A737A714D4CBC834E5B4271C52B28C5398E296945453634938A5DC536EB4BB8A4AB652CC284C28EE284694DB048A811446A835090C8A1B0A29150614A975271C714227068CCA715848B7119AE9310B6BC107357B6EC4A819AA9821E41C559DB823A5498DE3BD163F9A06E268F1631CD4E929B71DEB31D424391528F8A340CA10073517F8AC019A984E28A01C9CD4A3C8A308AA622F4D1022A01AF6CEB9E2A71C64B8A69202402DD28A0A4713CA495055474EF9A62778ACED24927C20452CC4F4C7DE9CB480A3956EFC8F9F8AD47F18B514D2FC233448F996E584793DC1E4D579E5A86C66EE9C57C47ABBEAFADDD4F2B108EF9183D1470A2AB163926984684E187AB9E80556BC87048EFCD382EDA0848438793A93D48AE5E595B5B64E96B1796EC4125638F9623803A74A2CD780AED4C1761852BC607F4AA21724A11B8727257B53D6508DE1A42C578041E339A5953A117272DC31C704F4FDE998632803C4092C428623934B3B99E6555036838551C7EB5B678074B6D435A859955E181379F6DC7A7F2A5CF2D4DACE3C7DAE97BE16F08CB718B8D49F2CFCF967B0F9F7ADEEDF47B38230AB0A71DF038AB3B6B608815471DFE69910291CE3ED58F2CEE4E86184C5AC4BA4AC45BE9F2613C18BB7E9ED43874D5F2C2BE483F957B2F3C75EF5B4BC1C63048E8001D6A69627782474E828ECDA8A1169FC323823185E2979AD10C9115418CF35B4496BC0000E94B3DA2EC231C7F4A54F4D76EF4E59776E50772F7AD6356D1BD2DB06D71C8C74FDEBA1B45C024751822AB6FAD03C6411F39AB70CB4A3970994715D574E9D643C31079E98AA81160E1861BEF5D5F5CD359A12428C0EF8E82B9EEA76851DCB83B81EF5A70C98B3C349F86E492D757B574CAFF1141FE75F4242E5248E41C60835F375B3BC4EA4390739033DEBBE787F515D4B47B6BA5EAC9861EC4707F7AE8F0DDCD39DCB34EEBA7CC27B38641DD41AD77F106DBCDD296551EA89C1CFC1A77C1971F51A2C5EE9E9A77C416FF53A4DCC40649438AC33F872B4DBEDC4E5775A75BDCE9E44D86247F2AE2BE29B24B7D424588E54135DBADF46BFBEB76588B05F8AE69E38F0CDDE95299270C549EA6B765963AD391E3FBFBDB97C6996036BE2ACE45CAD21021593A559EC263E69656ABF4B01C629598538ABD68332D14D8D57B8A5A414DCBDE956A5B16C2EE284D469284690C038A811456A19A0C81A1B75A29A837DE952EC1F4B91D09A343647157F1E9EC47E5A2AD894ED5D4D32694B1DB6DEA28EB163B55AB5A1AC0B7C751468695A22E7A51E38C9E9D69EF22A71C383D2A524FC93DC51521C0E9560B08353583340249164D3290669B8EDFE28EB162976098B61ED596831DAAC02715878C50084517ABF5C53D1C6A467DB815009EAFB529A85D8894471FE620927D874FF006A2D1A66FE71B5BCA708CBCEEEC3EF5C2FF1AF543737D696CDB96444DEEB9FCBDB1FB66B78F1DF8A22D16D96DA21E7DFC9F96256384F93EF8AE0BAD5EDC6A17D2DCDDCBE64CED966CE79F61F15979F3EB517F1E1DEC886E727B579A426A1C5490A67D5DBAFCD606A5858C19C4920C29E1703935648CCC0B328DA9C0FFCFE555D6CEC70CDCFFA47651F156510F426E3C756E3A1A018B1894CA49209C10B9FF2F19FE95DB7C03A3AD868F03140B2CA3CD94FB67F2AFE82B93F84F4FF00F10D721836E467D43D86726BE80B48D6350381F159F9B2D3578F8F569C8A3DDF953F53C53B15B920648A0C4E06053F6EF8233C7DEA88D5BD23F49C6EEC2BCB183C11D0D3DE60C741F6A136D2D9500559A29592118E94B3C4B822AD1B18A527C2F4A8B12A768BF30CF34A4D174DDDAACE6FCE7DB148CE339A582CDA8EE6104488DD0F20D683E25D290F98AAA411EA5238FD2BA4DC29191C6472335ADEBD107872323AD5D8DD3372E2E40CBE5CA41EA2BACFE18CE64D1A58F70CA49D33C815CC754857CC665CAB82770F63575E00D427B4D6E048C96121DAEBD783DF15D1E0CB55CBE7C771F517E1D5CFA2E2DC91C10C2B75740EA54F42315C83C3BA91D3B528E6E761386FB575BB7992689644605586462ABF2B0B8E7EC3C6CE59EB4BE996AB6D06D030727FAD69FF008C3630CDE10B999946F4C106B7DE05734FC6CD5E2B7F0F8B10C3CD9DC71EC055385B9650DCB8E3861A8E0704037E69F308D94BC2E01A3B4B85ADF187B232AED634A4FD2989E405E9699BD3514F891B819A51853729E4D2B21A4B5762038A09A2B74A0B1E694E1B541BA54DA86683226A06A66A0DD2952FAD858A81D2A0F663DAAF8C40D0A58C60F15BE64CED6A7802F4149B2F3D2AE6F500355CCA3356CA52C88734511D1940A2000D480D13914F5BDBEE3D2871A0E2ADAC6306A32BA80216981D2852C3B4F4ABCF286CE955F76A01E2AAC73D820171DAA2CB9EB46C5608AB125993D400E33DCD6A7E2ED523D2527BE9D72891191571F9981C28FE6456E337A549EA4E45727FC6F3B2C3485624C5E700E17A91D481FC8526775138FD722D6AEEF2E6F66B89E406EE75324AC4FE4527A0AD6DCE72718F6FB5317772D23CEC4EE321C93F14A3F0707AE3AD7373CADADB3A889391CD6500C8A8E3AD4947427A552687609479AAA32547156F0AB87588101BAB7B0AA4B72036EEFDAAE34F6119DDB732104E4F38A9D8D76E9DF86568A93DC5D9C00BE804FB9EB5D4AD24DC171EDD7DEB99E837B068FA5430B90662371507AB7CD59C7E28B8541E4C1E673D3A62B2E58DCAB7F1E730C74E97071D79AB0886718E4D73EB1F16B44333DA165E376D6FED5BA685E22D1F5040A27F2A4E987E39FBD4CE2C85E5C569DB9522A0CD86E2AD618629170ACAC7DF3C52535BEC9CA63233904545C747994A1E3752F3275AB00813834A5ECF1448CD232A851CE4D1AB7E262A671EA39348CC383CF22AAB5DF1958DBC9E55B2BDCC9EC838AD6AE7C51AACD930E9E14F400B64D4CE3A5CB9B19D366B9936CA01AAED4A312C2463F4AD764D7EF61955752B6288DC6F51C0AD8A19D6E2DD645390C3834F26945CBD9C97C4700B7D41873C9CD5D7E18A467C4F0A3E37C8A52324F1B8F6FDA83E3D880B88DF68E78A53C302EADAF6DAFEDA22C2DE556DD8E060D6CE3BA9B60E696EF4FA362F07DFCC8705500F7EFF006A3DA6A7A8F879CC170BBA207853D3F4AE83A6CDF53616F31FFA91ABFF003154BE38B24B8D1A6931FC48C6E069B1E7F7CBD73F8CF9707AE3BC6B4AF117E291B184AC36999718C935C835BD6EF35EBF6BABE72CC7A0EC07C543C4B3992F8A93C034940302ADB8E38DD62A3DADFA228C56246E2B123E280F27B54ED30293AE69799B228B2BE452923545A7804ADCD2F21A3486977A4AB206D416A9B9A19A83226A04D4CD09A8330454181A9935026952FB5378C52F3CA3155E6F7D3D6979EF383CD74261A67DB17B20355CEDCD4A69B777A033E6AD90A22B73474614879A077A3452E6A42C51B156B6320CD5087E3AD37692907AD2E5370367F306CAADBB604D6167CAF5A5A7932D556186AA586AC67DE8464AC6FAB6C408EB95E6B9D7E2B5A3CFA24574ABB9ACAE04C571F981E3FAE07EB5D0C3034B6A56115FDAC90CAA191D4AB03DC7FE629329B9A3637B7C7DAE5BA457F2F93931B9DCB9EC08CFF7AAE901DF82B823B5745FC43F085E68B75968D9ADF9F2DFA8233D335A2C9B99704671DFB8AE6E78597B6BC7296145038C8AF7DA9B31C2155B764FFA7A11406C67D23154EB4B225190AA31827BD6CFE07D35F56D6634EA91FF0011FDB83C0FE7FD2B578D4B38503249C0AED9F869A0BE9D6025953F8D27ADFDC7B0AAB3CB516F16172C9BA787FC356167189268FCFB83CB3C9C935B2430D8A95530C43B7E515A66B9AE9D323DDC838EA41C569916ABAEF8AB537B6D21FCA84752EFB703DCF7CD5384B5AB92CC3F0EE92693A7CF19530C21B009C2E09CFB56BD7FE19B67CB5B3ED1FCC571BD4751D674CD46EA14D4A49A4B5C6F9436DE840CF27ED5BBF80BC4DA9AC28DABB79B64C446B311CA37607E2AFD5FC55332DFD8DBF476BFD32411991A4881E031E95BCD94BE7AF98D9CE3DEA8808E689645C12734F6957382CA3D8D5572BBD568C67B4DC6352BA31A92B9C8ED5A26AB06A1AC5C3C21C888F181D07FBD6D3AB4C5588FF51A1C9770699A789E552D239023403D4E7B0C5363FE0CFA8A2D33C216D64865BA06460392DF15609736309314691865CE703A62B42FC44D635F86EE18249CC3F511338B788E368E7AB7BF15A4E9506B1A947712D8DDC8648D771467E5867DCF7A9B85BF951FBB27E1DB2F7E9AFE230BC68F1B0E4718AD6EC2CFE85A7B74CF94AC5907B0F6AD3343D7751B46449E37209DBBB1C022B7C597CE2B2118665CD4C9AE9395DCDB41F1D10F7223F6E456D1F859A24B77FF000F306F2E5914EDF7CF0691BED1E4D5358C02026464FC7B575CF0BDAA699A96943CB0AA1B69EDF9855973D74A271DB2E4EA16B1A410470463091A8503D80AABF17CEB0F87EF5988FF0096715597DAF1B5D5E68C0251462B40FC4CF17BC962F6C9E90C0D5D87065BF672F3F330B7F6E7D71ED4EE44B7F29E300D4E19011818AA17B82D2B37B9CD3F652E48AD36ECB71D2CE5C04CD21BBD66999DFF008749A9C9350891897E29490E0D32E69597AD15648139E2977A331A03D57B5811A81A99A81A0DA45A86D443436A0C89A8374A976A8B014A1F4A7F880FF55464BCCF7AD58DDE07E6AC1BF38EB5D5DB2366FA91EF5192E7E6B59FAF39EB5217F93D68DA36BC371CD1A1B9F9AD7FEB0377A24771EC681B6CEB743DE9DB5B8DC78AD4E3BACD6CBA22F9857346F498BB8D8EDE696B89B69EB572B6C3CAE9DAB5CD6731E6971BB4D4BEA3E6A69313DEB5C37877E33CD3B6D7391D698BB5DACC3DEA42E40EF8AA67B82B4137649EB46930FEA5E4DE44F15C469246DD5594106B99EAFF00875A3DFDF116A64B26383E83B979F835BF24A5CE4E692B989CDE28048461EA3F6E959F9F1DE37516F15FE723976B5F84D7F67398ECEFADAE71D060A9AD62EFC0FACDBA33CB6E8101DB9DD5F469876F91340DB27561953CE7E7F6A4B5585AEFCD1361A48F2CE00F7E95C2FDDB3EBB37867E1C93C09E0D44952F6F555987E55EA07CD75AD2E254560071553A4A04B44880C6CE3157B6836E29393792EE29319D0B2E9B15CC455D0107E2B57BBF06A2DC99230C0E77061C329F822B798391F34C2923865047CD44B61EE32B98EA1E06FACB869A5B991DDB1B99C8278EFD3AD3BA6787F5282D45A25E94B76CEE511AB139EB9C8AE8A9E5B7FD2E4548A12A4F0A29A6555FA28ACE396CEDED6D43B388B8666EA78C66ACAC5C824F439ACBC6B1927FCC6B3129C702A37BBD9E4D425A93191C7CD06E6C64D41219A1BC96DE78576AED008F9C834CDEC59048EBD6B36A8170CA480473F14FF097B6A3E2BF0B5FEB3127D45C2CCD1210AE54023BE38EB5AC41E10D4608DA069A411B81954F4E40EC4D766F20B007871416B557FF0027353ED44C2344F0FE802CEDC4729DCA0E767606ACAFE2552BB1401D38AD827B711A900553DEAF045461774678FF00127E1C891F5821F20019C8F7ADF6E19523B5931EA12A1FDEB45D1E166B99DE223285493F18E6B6E2FE6490AAB168E33BB9F81934F8F7C9229B96B8B21AFA68CDD5D4CF8C96279AE39F88DA84524AC232091C56FBE23BFF0026CE66CE322B8A788EEBCE2704E49E6BAFEFAC6C79CC3C69793F7151BF9EB56DA70CE0D512F2C2AF74C042D538B5F27D3774D8005052A77072C0563185A7AAF10643C52B29A3B9A59CF3496AC9033426E688C684C694E8371433536E6A068322D436A21A1B50960F4A811533D2A06943A54971C633CD0D6663D7A5065049ACC791D6BA4C42B4C7DEB29293D0F3422BBAA04153C5483C2660073468EE7DCD57A82451635239A02E2DA7CE2B79F0C480EDC9AE6F0B6DEA6B67F0F6A22270338A131D655879239ED5A87891F86C53D16AAA60EA2B57D7AFC48C403DE964D5DA6D5348C77E69DB37C8EF55C1F71E29EB6381CD3C29D99F2BF34A2EE2FC134C292FDA9AB6B6DCD9C514D19B443DFA5580B61206DC28B05B600C559450F5C8E0D2DEFAA9EFEC6BEF14D15DC0876AC7C90FFDBF7A35BDAB3B48D08249FCE5BFCD57E6D925431C80E3B1A8A583A0C2CCF8F6C5723C8F0B2B96F0BD3ABC3E663EBACFEB4311186EDC6303762ADEDF96150F1069F25A5C24ACDB965F8E86A767D1735979B8F2C3AC9AF839267F1716A39AB786D7CC41EFF6AAAB7602AD61BB11A5262BEC60C015B69C57A758D140691695B8BC6770A9F98D575E9922237927FD47DAA745D51AE258F77A4E4E689067DBAD251F94595830EB5B0595A24F092AC3814A2A8AE090C722B16051C9C360E7A5586AB02403D4C3EF544836CA190F24F4AB3F049F5B3DB464600E9469E01B772D55A4AD185604D36B75BE3E4D2CF8B3446F39040ED54376B8735B04E77735517A9939C0FBD3613B472DD624BC31BCC778D1B001A5653FA56D0B0B5B7865EF65189273E5C63E01E4FEBFDAAD7C35E0A8AD6D91E6B969125FE2950BB792338A278EDE3823B3B540AA8BC851DAB47063BE48E5F93C9AE3D4722F1A195AC19403926B90DEEF329DC7BD777F11AC4D64D900F1915C3F550A7509157A06ADB9E3D3171DD744E28C97157B62BB63A4608BA559C636C74B8CE919DDD0A46CB9A913E9A167321A9B9C5323180494B375A3B9A59FAD2D59116A09A2134334A941AA06A4D502683306A04548D44D0960D41BAD4CD40D2874A4873D6BCF1E074AB16B6D86B1E413DABA6C4AC58C93D0D13E973CF35631DB907A51FC9E3A51A4298C3B4F4A2AC676D59FD36E3D2A5F4BB474A349557964D1A0DD13020F4AB14B6C9E945167FF006F153204E2D42458F19A52795A53939A712D33D057A4B5C0345052D892C2AD22527A5296F6F89055DD9DB67071444C4ECA0CE2AE6DADCE457ACEDB1DAAD218700516E8CC431614714CC6BDAA6898159230696DD889A8C8A98071D6A28722A7DAAA4A8FC51079BA7336326321AA86DC1D808ADC2F62F3EDA58B19DCA456A766B9055B823835CEF3B1F95D3F032EAC3304A4E3A53124E40006771E29244DB332E78ED472844AADD78E2B06DD33300F2F963973C9A2CAC245C1C5535CEA90C0DB646F59FF0028EB514D58381B21620F434DA0B0FA35C10800A34125D5AA10012A7BD290EA6072F1305EF91C5590D6AD5EDC800E47B60D1A47AD2578D71778F33D29D877A8430AC6D9C0CFBD665BF8DB9C6D1EE78A466D5218F258FA4752066A7D6D1EBEAB80C31C9A1E4A3E33C1E955F65AADBDC3611FD58DD83D715661448BBC52E53425DA264C0A4E45334AA8A325982803E78FEF46978073569E14B1377ADDBEE19487F8ADFA741FCF1FCAADE251CD9EA69D2A04F2A0441D15428AE5DE3DB979BC42D1A7E58942D753270A49FBD724D71C4FAA5D49DCB9AD9E2E3ED96DC8F26FC8D2FC53318AC9C93D16B8D34A65BB638EAD9AEA9E3C976D9C83E315CCEDADC07CD68E5EAE94E07AD9738CD3737A52A100C115EBA6E29626831FBD7A43598FF2D0E5A9102734BB9A239A131A4AB1026864D658D409A80C13503593D2A24F34191351359358A12C1A89A91A813CD299DEA7B407A0AC0B3F4FE5ABD36D9ED5216DC74AEB31283E8F1D2B22D0FB55EFD381DABDE40A02916D71DA886D7230455A18467A54D621ED4055C769F14616FCF4AB3584564438340222D78E9419AD8FB55CAA8E86B0D102280A682DBD7D2AEACEDF819A8C507ABA0AB3B68F18A8106823029B45A128C5143D575299E2A04F35E7618A5D9F9EB440690D141E2948DB34C8385CD2D08B92781D4D6A9AA2FD36A8C470927ABF5F6ADB42F3BAABB52B05BDB5910101C36E46F63FFCD55CD87BE3A5DC19FEDE5B51960CCAE3AD3F105962182323A0AA48DDD1CC728DB229C303EF563A7DC6D9304F06B87C9C7E95DDE3CFDE07AC69513B25CA205B85C10EBD4E3B1F8A85B3DA4F27FC743E54BCE593815797386897DAAA2E6D03E71F9A8C7236A65F57E740B53B9A2918234608C9CE0D64E948B6713908F346385651B7FF009AD6E292EE061B669236036F07B7EB4EB6A7A8C718462AC3A64A73FCEACF62DE1E4FC5D9CB9D0600916D7C4848DC4A8C7CE2A835582D2DA3F2D009262EC46EE76E3A714C5DEA17D3FA1A40A3FED1CD230DA9794BBE73DC9E726A7DA23F6B3FFCEE92D0F4E51299E7C3C8785E3000FF007AD82701131D875A5AD54210BEDCD4B54946F11A9F935559BA9DEBFA962CB92CDF947635BF7812D447A734E47F1266C93F02B9A6F33CC117FE5A9E7E4D760F0FC22DF49B78C765AD5FB7EB8EEB9DC9CBEFC9AC47D52410D84EFF00E9426B90DE4BB55E46EE49AE8BE3EBE163A04CDDDB0A315C2F57D79DA070887BD6EF0E6B0B583C8BBCF4D47C75AB096E7E9D4E7079AA1B55DD834AEA723CFA8BC9229193DE9CB370145465775326A1F45C2D2B7072D4D1701292739722A4A9AF0B4190F345CFA714096A29A177A11A2BD058D21D06A09EB4427AD0DE84C60D44D4BB541BA9A0CC66A27AD64D47BD28789E287DEA6D51C5067D6096E0D79A0C0AB18A31B6B12A802BA9B635348801A8EC14CDC019A5C9E78A60194E7A5636D13766B05A8430BD68BB450C75A203C71425EDB530991515EBCD19480280CC51814DC63141414C254014578D794579A9007236052ACF96A34C78A53AB5483903669C4E70294B65AB04029320F638A1907381DE8C41AC6DE6912A5D6749FA9432DB8C4EBFFF006FBFCD6B90CB86E786079ADF178FE75A26B309835CBA45E15889547DC64FEF9AC3E5F1CD7B37787CB65F55EDB4E25B6C64E454954499F7AA2D3EE8C726D3C7C55D5BB02723A1AE659EAEAE376288770F5264D09ED14E41538A7617746F4E4F3C5332CAECBBB039ED8A686F6B3E29D2D238F9DA0FDEB12A824600E29A9998E7002D2921C727147B26CFF500DB3273CD526A97DB3CC6C92EC7000EE7DA99D4EE962424B638E7E2AB61B677FF00899C61B1E843FE51EFF734F8EB1B36CF9EFF000BBD260DF716F1283CB018FDEBB2C08121451C60015CBBC1B0F9FABDB64642807F6AEABDAB6F9194B248E570CB2DB5A4FE2332CB0DBDBB7424B115CE2EF4DB71131DA3A56E7E3AB8F3758D80F08B8AD4752936DBB1CD6DE1C7D78E462E4BED9DAE55E22B68C5F90800AAF8EDF9E2ACF556F32FA42685128155DFABA7C2CE855696EAE7DE9FBA3814922E4D417F2F374A0499A624C0A5E4A8AB600C682D446A13522433503536A19A1318CE2866A66A0683304D44D78F5AF1A50C1AC1F8AF1E95EA0CFB163C6CA05C36051A3076D02E149AEA463555CBF34A993278A6EE623CE4522DE938A645194E6B0E6A21862B0DC9A944163E68AA714341D2A5D283084F3458C66809C9A321C1A806A31465A0C6722984A5A045AC39ACF6A1499A5004C78A5D796A349518D7D54C0E5B0C53CBD29580714C835564912BD50CD6734A1EC56ADE2F8765F59DC8FF0030319FD391FD6B69078AD7FC6E0FF84C720EB1CCA7FB551CF3DB0ABB832F5E48A49ED4BC4248CFA873C5674FD43637972F18EF4EE984344327823149EA1A78FA82D1F04FB57137FEBBBAFF001B1D95DC5B41EA29B9665931B5801D6B4D892788E32CBF7A604975FEA153328355B14F242B939C9AA3D52FA3890F233D87BD2EC9752F1BCE0FB57A1B0559559FD4C0E72DCD4FB41AA158E9925C49F557C3D3D5223FD4D1EE87A8D5AC8711556CDC9CD2CCADA35A8D9BF0DD035DDC331E63518FD6BA1B1C213EC2B8F683AAB68FAA25C60B447D1201DC574DB8D56DE4D0E5BEB7956487CB2C1D4F1D2B66FDEC72F3C7D375CCB5FB8FA8D62E5F391BC815AF6B926DB66FB5464D52396577DC32C49AA9D7AF03C0403DABAFB93172ECEF6D2E73BEE1CFCD150719A0E3D793DE8F9016B36D6FE095E3638A020C0A9DD1CBD433C516A60729A5DCD16434BC8696AC8139A1139A9B1A1B7150688B1A19A993506A0C835449ACB74AF1A5081EB5163CD498D0CF5A3634976AC1AC0E95EA533ECF8E3F4D61E107B51A13E9A2E47C5742E563229EEADBD24E2B5ABF051CD6E9758D86B4DD648121C55DC796E12968E4269A8F9AAF85B34FC67814E8D985E2A4C28633444049C506DBD1D1947358098A98A123C478A62334AA9A3C4696830A6B0E335953C567B52028EB93528528ACB598860D1681E34C5180A82F6A99AAD2F1E958AF66BC2A0323AD52F8D307409BFF0072FF005ABB15ACF8EE7D9A6C70823323838CF615572FF4AB38A6F39A29A3FF00C94FB0AB2990360D5568D2030AFD855B93E8E6B817A77F1A34302C8B875E7B51D6CD07618A0DACA0019EA29C1203CF0451D0D812408ABE91498841909C5393499E063150894F39EB5292772A76902AAE63818357772300D515C825AA20BF0B4AB95E955934F796F1CF6F05CC91C17036C880FA4FE95788995C6292BBB7DC738AB27258CF9F14C9A25D5BDDD93E4E5A3EC45297172D2A905ABA0790B2AED600D52EABE194994B5B9F2DFF00635B30F2B7D64C5CBE1EBBC5A69EB519A5DA9D6A7A8E9F7B60C7CE898A7FA94645535C4E4E40AD132966E325E3B2EAA4F2EE93AD4CB0C524808E49A9B4953B122721A039AF33E686ED51B3C88B1A1B548B66A06A5283540D4DAA069766D204D60D64D458D1B1A60F4A113CD11BA1A013CD2A4515EAC29CD7B3407D8D0DC8DB446BA03BD6BD1DC951509AE8E320D75AE1B62DADAF2F8043CF35A7EA977BE5E2A5A8DE373CD51B485E4E4D349216F6B4B6638AB281B38AA9B504815650E7352165190451569589B14C06E284C13754D4D08119A20E284EC64A3A52F1914C474B52613A54B3504A981496063AD4A31CD7B1521C5094FA54EA19C8ACE692CD07AB39C555EADAEE9DA4C323DF5DC311519DA5B9E9ED5AB5878ECEAF6F1BE9F6A524493F8BE672BB71D8FEA2932CE61374F8E172F8DCF50D420D3E0696E9F6A8E83B9FB0AE7BA9EA336AD77E74C36C632B1A7FA454EF9E6BC9FCDB990BB74C761F14054DE76C639FCA2B97CFE57BF58FC74383C7984DDFAB0D0E6CC4A07DAB648C33274CF15A9E8E0C1398CF415B7D8B6E400F158326FC500195B9C8A38248EA69931E4D78C78E9402C371EC68F121C7A8E3E314455C03531C8EA6A4D085DAF1915532A6E6E2AE2EF241A4235CB9A0501232057A4881EDD69FF27750E4888E7DA82AACC3B5F815355078A3C8ADED43C63BD08D95B9B0495482A08EE2B9A78D23D3B43D421964B68E5DCA58C2495DDEDD2BAA9955109660A31C93DAB85F8B758FF17F14CB3C18D919D8995DCA71C720F1CF35A7C7F6F6ED97C9F5F5553CBF4F74E97284BB1DCCA8C085CFB63B0143F344AEEF167C9CFA3775C7CD28AD2C88C182AC7196631BB71CF3C0ADDBF0E3401AC4F25CBC48F6B07F0D415E0B1E4E7DF1D2B6E794C7B63C30B7A6A649F9A1BB1AEE8BE0BD3DB05EC6DFFF00F8A97FE8FD2D07FF00656DFF00F18ACD7C8C5A678B9382EE3520C715DD64F0AE94A3FF00B2B6FF00F8C554EA7E0ED266460B6C9137FAA3F4E289CF28BE365238F161432E2B61F10F856F34D2CF0667807703D43F4AD5DC9079E2AD994AA2E171BAA296158241A5CB1F7AF07A646867A0B75AC993350273404D0E2A75055353E9407D2E1C81D6A2F2122861C11512735D973C95F1C8AAE400C956773196A4C46435034B2B251B6AC114003155F67C2D58A50045A2AF1505E2B2A79A0C3A29EB46A8A1C815E6E0D024123A6E3E94A43C9A6E3E95146C65A2A73400C00E4E07BD2D36B1616FC4B7716EF65393FB55596527D3638DBF22CB15863DAA866F14DA8C8B786798FBE368FDEAAEEBC4B7EF9F26D5221EEDC9AA72F238F1FB5763C19DFC37353C573AFC4EFC408F4346D3B4D62DA89DADE6230223E7A11F6AD7BC6FE2ED434EB2C4D3B89A6CAC6ABE9FB9FD38AE3F733B4E43BC8CC589CB39FE7FCEABFF00F44CE7F134E1F5BDAC359D6AEF57BA9EFB51B8334B237738FDBB5764F0A5BA41A059ED006E8C31E319C8AE1BA7DAFF00896AD6F6887D048DDF0A2BBB5900B0C71A90A880281F02B9FE4E7EDD56DF1F0D766A525F2B1825BA559E9D62B0A6E7E58D06D84608DB8EBC9A7E49D510B6E181CF5AC9F1A955B76DEB11D335B169CF9C0AD623B84795B07AB56C160DC8229323E2BE4CF7E68BB334381810334632000D4009BD2BD6A3BC6D38A04B2658F3409253F946683E98B8954F522944951493BD4FEB5E96DDA5CE4902971A4873D18FEB46D361E4BB8C0FCC01FBD0E6D461518EA7E0566DF435C8C8AB38743C1C15029A76AEF4D764BC77FF0097131FBF150F2EEA6ECB18F8E4D6D4DA6C51FB7142789147A4038A690BBEDCFBC71FFD3BC2F7D72EECF26D11A2E7196638EDD3A9AE1D3F9B35C4A2DE28E3121C22A02171D80CD75BFC66D54DBAE9B67036C7329989C6700703F73FB56836BE1E9AE341B8D685FD9A086511F92EF9918B770BFBD6EE09AC76C5E45DE5A6BB7859A5669808581E625E883DB939CFDEBE83FC2CD3574EF06D9975C49366661F73C7ED8AE0290FD5DFDBDBC6589770A73DC93D49FE55F4CDA45F4D6305BA748D020C7C01FED55F917F07F1E199EE5547005575C5D1C93DA9868491939A17D2E4F22B1F4DBBAAD92491CFA734330BB7249ABE8ECC63F2D64D9F5F4D4ED1BAD5A7B42EA4115A8EBDE0EB5BDDCE89E54BFEB51FD477AEA46CF3D852D358F07238A6C792C26584C9F37EB5A2DD6932EDB84CC67A483A1FF6355278AFA0F5DD221BAB778A58C3A3704115C4BC4BA3C9A3EA0D0B7AA261BA36F715B30CE65187938EE154E5AB2AD83506AC53ED5E8DA366B34B46D834C2B0229B687D1719E28CA6811FE5A99F8AECB9C23608A118466A59C5114E684BD0A05A6871411C74A986C8A00EA7345514088F3461418689B068C4E6955353320505988000E49F6A289766E3E293D5B5BB7D3936B1F3273D235FEFEC2A8351D7E7B82D06980AA74698F53F6FF7AAD874D7772F21258F249E49AE7791E64C7F8E0DDC3E25CBF9646E7BCB8D49F75D4A761E91A9C28FD29CB48204030AB408AC0AF414CA40CBEF5C9CF932CAEED74F0C71C26A43D1A47C600A6440ACBD05270A3679CE2ACA05231ED55DB7E44DB5C8FF001934C2FAA698DB82C6B0484FCE18640F9C115CBBCA2C77EC0C00392071F635DB3F1BAD4CBA3E9F3A9E6298A9CE070CB9FED5C6E7B8B8BD964C948D18EE650368E381C0E2B6F165B8C5C93F93A0FE1F787ADD8DE5CA7F1839508C7D8A83FDEB794D10AFE42CBF635ACFE0C4A4E93751919D928C71DB1C574F8F1C138ACDCB77934F0F58B5F4D2EE87E59D80F91597D2E57C89A6723E38AD9B82380280CA09C1AA76BA29ACF4C48082055EDB4440CA8E95054007344494AAED1517B3C342E362E7BD0E4BB27B67F5A0805DB8CD156024F7C54274F2BBB7238A6628C05DCDD7E2A0A81466B20E4F14C07540DD46076A22463A6318A1A647BD155C03CD4168D03ED6E78A6BEA40C8CFEB55CCC3A834196539C0356624B367A46071920D2EE53071C1E9914B990F404D53F89753FF000BD12F6EC9CF951B328F76C714D26E96CD470CFC4FD5C6ABE2EB931B662B7FE047CF185EBFCC935531D841268CF7D16A119B84931F4C7866503F383559791C9B5AE5DD24DDEB255B2793DE8504A225DD08C070558B0CE7DC0ADB8751CFCFBBB6C9F87964DA878C34E5DBE957F39BEC066BE8D58C1603BF7AE01E00BB9F4FBD927B48FCEBF9C054411EFC460FACFDF815DFECA4696D229658CC723282C846369FD2B2F36F6D7C3AD3257B54A388161C51117273468136E49E6B3B4C89245D38A21846391444C7719A275A01278475A52E221CF156D227148DC00148A0D1AF6A11020F4AE71F883A38BCD2656451E6C3FC44C7EE3F97F4AE9F76BC56B7AC42AD1302320F5E3B559C796AA9E6C778BE717150EF565ADDA9B3D4EEADC8E11C81F6AAE22B739C8934685B8A0B54A26A368D3E9057C0A989452458F3538D8935DB734F2B0278A3AE314A270051D5B8C5369230ACE706841AA40E6A340747E6980DC52B1F079A3039A00C87238AA1D56E1EFAE4DAC2D8850FAC8FF31F6AB4D427FA6B195D7F3E36AFDCF155FA25B6D8949E49E4E7DEB99E7F3DC24C71FCBA3E0F0CCEDCA99B2B04540028E3E2AC62B65C7028F1443B034D44141DA71B8F6AE23AF6F5A26907C517E987B53C21DDD2A422C505D1116E31468D30BC8E2982A1467DA94BCBCB6B4456BA9E38558ED05D80C9F6A27D2D68FF008C49049E161E7F0AB3A6DE33C9040FEB5C1A55925526157D99E7683B702BBAFE28DD5ADE783259E19566844AA54C64303CE33FA67F6AE0AFF9C9462A98031CAF6E6B6F0FC63E5FECEB3F835227D25F44846F5914E3E318FED5D49586DC8AE17F8437DF4FAE1809F4DC4447EA3047F7AED913E54015472CEF6BF87BC4E2C83BD6783CE297CD4C1CD56BE0A793590809EB585E9445504E694F3A1625C11D38A69702975231C75ACEEF9A1233B0618C62A51A803A5011F8E4D1049C76A00A40A8BF0286641CD0DA43F3F7A0A9B4B918EF5066A1E0939EF4455C8A9951F100CD9C62B977E366AED0DA5AE9B1BEDF34F9B2E0F25470A3F9E7F957572A029CE303AD7CCFE3CD5BFC67C537F72712421FCB8947FA17818FBE33FAD5BC53776A39B3D4D46B619039F2D372B0C1E7229CB32D73776F0DD228850ED0ABE92FF1F7349AF9A626541B1570D8031FA535A75BB5CB95FE1A9552ED231E001EE3BD6D61ADAF459A4B09D752D283469190B22372C4AE33FCFDBE2B72F0EF8E352BDF1DADA18DEE2CEE42AC51C78DB10EACE4F5E31DFB5687A86A825D1EDB4BB5F2956562D2C9C96C1E0E79C63DAAE7F0E61BCB3F1758FF00865DC7B26252E6177196881E783D4E7DBDAABE5C778ECFC795974EFC9CF029845C0A5E30734C29CD73B6E9CF89A9C31A28208E682324F4A22F5A9D8B196391C525703834EB006929FBD4A62A6E1739CD526A518286AF6E38AA9BE5CA9A27D465F1C1BF112DBC9D799F1812A06FEC6B536AE8BF8A96E375A4D8E8CD193F719AE76D5BF0BBC5CBCE6B242BC879AF1A883834E57D0E641EF448DF8AA45BAE7934D477400EB5DB72F6B812670289BFED550B740F43468AE726A769DAD54F193464E2928A5CF5A7223BAA6A4656E6988FA52B839A3C7D29411D75F725BC43FCCFB8FE829FD3942A00474AABD53D57F02FFA549AB9B25C28CD79FF00D42EF97FF8EEF838FAF12CA10314C2C485D5CA8DC0601A043C5349CE3B5626B19060605659411511C8C83C57B78A116307001CD718FC6AD45E3D5ED2DA5D3CC8B1C41E19CC8D8DC490CB8E87803BF15D8A79A28B6192454DCC157730193EC33D4D706FC54BE7D4FC5173691C90CD1DAA22A2F9810C64FE7EB8CF4C67B55DC13F928E7B3D74D6B4EBABA1E18BDB15B191C4AD82CCEC7CBC1C9C2F41C715ADBC8658943ED5038381D6B68F0DCE2096E105E428D2305789FD6F2738E0FB0E39E95AD6A31ADBEA37318C79714ADF040CF07FA56A93FC633DE18BBFA0D6ACE7527114CB923DB38AFA4AD883182BD08E2BE5D478C36232C2463CEEF8AFA3BC197CBA97872C6E03659A30ADF71C1FE9547363D34F065AE973CF7A9AE73C56425192307A566DB4B0A7F9D141E79159F2A8A90E79341831BBB56429A3A4391C03531181D01A13B002138A2F97C0C0CD30B18A344808E6845A48C448FCBD6BCB076C71EC2ACF62E2BC514723153A2ED5E2103B7159D98E829C603DA82C393C51A16B4FFC4DD63FC17C2975246DB669C7931E0F393D48FD335F35F98D28FE18D983F989E3FA5746FC6ED665BEF10C7A7DB92D05A2E1B6827D67AF4FD2B9F6A167796AB1DC5C452410360025703A74ADBC58EA30F2E7BCBA2A51A45562EC581F573DAAD620B388ADE3B37292B0491A32771E78FD7AF18AAEB5883B858B2DBC8007EB5D2BC292E9FA6F8B638005785408A436E465B0396DC7A1CE455D228B5A7C569696CD032F99223EE0430CED017825474E491C9FD2B70F03B4761E20D32EE5F292CF2551B6AEE19CA8E9EF9E80FF002A6BC54BA7CB7B717117F0DE5B860F1853BD54E70CD8E320015AFC4974B6F3790A42479914B28418009C2E7BFC53678FF1B20C72D57D1C809C51738E9C568FF86BE256D56CFE9AE24692645F315D8E72A4F43F22B7349048322B8D94B8E5A7630D5C76614FB5157819A0A3628AAC315312F3F634BCC3238A69B914B499DA6982AAE54006AAEF17D26ADA7EA7355B7632A6A250E5BF89D6E1B4567C7FCB915BEDCE3FBD7236AEE3E3B83CED0AFD00C9F2891F71CFF6AE1EF5BB8EFF0017379E7F20CD40F5A99A81AB54BAFBB157A3AC876D272BE1F06BC1F8E4D76DCA34662298B6B8F5019AAC639E94CDB1C1E680D8ED64DD8E6AEADCFA456B56726DABAB6949C7353B3C5B2E0D1140F7A5E1248068E84E6886555C7AB5861D9540ABDB31E915456E7CDD46E1874DD8FE55B05B0E05798F272F6E5B5E83C79AC21D84734C6E34BC59A28CD50B85078C57B8CD0F1839A81930292D4B90FE33DE5CD9F88F4C9A1B81B614170B03E42EE539073EE7A62B9E6B13A4D66E93B5D45A95C48B70F14EA155998962E3D860803DEB7CFC42D4AF755D5751B06B52638B0918580B49E58E58838FD79AE6BAA473DDBB7D5EF596008A2595892D1ED1B1318C0C0ADFC735258E7725EE9DD1ECEFE48E7B992F4C1E73180B0624B0DB93FA7039AAAD4C3477430EEC24453B9FEDCF1DC714D7D638D36382F5A677470D1CC99CC43382A0743900F27352D5203368563765B3B5CC6C723207500FEF577C8A76445D2082354B71BD490CF9E58D75EFC16BEF334EBDB073EB8641281FF6B7FF0023F7AE3EB29F336468B8014018E49F7FBF35B77E19EA12E95E2CB433E634BA2609013DCF4FDC0FE755F24DE2B38EEB27D06AA08C51A318C0A0C5CE0E29843DB1CD61AE80CA07719A3AA0C74A1459C51D413D7A509DB2170323353DA304D495323152650075A1012F1C1A206C74E943279AF67341A8DE663815E660697CED24E6B21B70C77A3654C9E6AA3C51ABC5A2E89797F3FE5853207FA9BA01FA9C55A3719CD719FC73D75A492DB46B571FC32279867AB7F957F4E4D3F1E372A4E4CBD63955D5C5DDF6A12CF75215699CBC8E7E793406B8952292CE696530960C067D21B1D40F918A8CA8AC8DE74B89173E95E77511AE85D410830A6F886C0471D3FBD7435A73FEF63E9104AEB325BCAB164056DFD08271CFB63AD340DBACB6F6F7504A2141E6619B039F7C7E868FA722C2B12EC6B8574769A28DB633A0C7A01239391F3DF151FF0011B6D4243E7DA2C7F500A468AB90878C024E32298A63C3D711C3ABCBB9649449C6F66F49EE327AFEB5D17593A75DE8B676D0D81B7BB8A324BBF3B98F208CF6AD02E112EC59E99A3DAAC73C25BCC0EFB3CC07A31E40CAFAB927BD58DF6A57D797371A76B76F3BDD448A0BA3F38180083EDC8E4536FAD11BB7E13470451CD752DCAA5DCC360B7F33391D7763DEBA1E9B37F0159BBFFBD702F0E6B16BE1EF114067956E258DDE3DE108DCA40C11F39F71EF5DA343BC8EEAD20646F49C9FDCD727C8C6E396DD6F1F2DE3A6D31F3DA8BD29489F3F96994CEDE6922E140C7EB409860530A73429B1423F2ABB819CD56DC8201AB69BD59C8AADBB1C513EA5A7F8821135B4C87A3232FF00315F3DC80A920F51C57D1BAB2601FBD7CF7AA4622D42E93BACAC3F735BB8BE307913B246A352350357333AB5C1DAF407971DA9BBA873D339A57E99F1C8AEEE9CBDC7925C8A3C371EAA5442C09E2A51AB2B0CD4A57F6AF90335736D305C56BD6CDC0E69FB7662DF14B0ADA2DA7CA8A6D64E09F8FF00CFE95536409C5583FA6DDDBD949FDAA33EB1AB30EEC2DA3A9625FF00D4C4FEF5B2DBAE1455068CB8823FD2B618B15E5792EF2AF4784D630C2006880800D4506403D054DB00726ABA745CFBF1485ECDE52EE1D3AD373300BFA56B5E30B9961D02F9E04596411E154F7CD27DBA36F536E41E36D66E2E7C4D34FA3DD7A46E3246AF865DA30DBBB6DE38AD54C91EAF72D777371234ABB77AB71B9B3D87B01C7C5585F3ACD65656A2DED628DDB73DC4796239E493FA8E3B553AAB7D1BDB0BBDD6DB8800463D6324020E3FA9AE9E18EB51CACEEF2AF5F5E5D5BBFD432B29790856DC48001E99F7CD10DC5CC914F6861DD24CBE63E4E3A73B87B9233D295B8B0482EA5B7DC582361549D9B495C0C8F7AB282DA5D1EF6D4CC913DEECCC28BEAC83D88ED4E88A9B591A2747B7CFD40E49CF1C771F14EB2DD41746E2E65CCC0ABE776492790411F6A675DD3EDB4A4B980999751337A401E8F20AE473EF93FB551AA0665024C773D80A8BF04FAFAABC29A82EABA0D8DE06C99631BB1FEAE86AFA25AE4FF81BAB09ECAEF4D6705A16F363E7A83D47F3AEBD121CFC561CF1D64E871E5BC538D47EB4C45193DE86A003C5329C0CB5218558F1C77A1CABC51636150979078A946CA30C1AF019358941FD2BC9818F6A830C1011D2A25703A5643638AC939E2805351BB8AC6C67B9B820451217627E2BE51F105E3EADA9DDEA334D83339761D71CF007E95D93F1D3C40969A6C3A3C52ED96EBD72E3A88C76FB93FB570B22CE24DB26F94C8028C70073C7DEB5F061A9BAC5CF9FB5D06D2246C648919C8CE73D3F953D6086290DCCF0B3456EA256403048CF1FA66856767746EBE902796646DBCF4CFF00B56C36DE11BD6BB6D93AC924476AC6B27E7049C0073D3AF1F35AB5B67DE81D2D65D62392282DA19279B2517780EBCE4E0672297BA99A682236A86C5E1C8750D9C00305B3F269B6B227C4A92ADA4A5638FCCF308DBE59039E3FAD05A29551AE6E6DA3500978CB1F56DF707BFDCD3FADFC977B41488523BED3F25DD549F35800792BC74CF41FBD6D3E17B9974AD58EA779FC79A4B761E4AA8CBF4DC39EA38AD65AEE7905AD9E9D0C725CBEE70AE33B464E3AF43C1A068EFA9AEBB0B32DD497B6EC87693D30E3233FD0F3CD2F62FC3DAED8AC7AA48D245B6EC3176864608C9DFB9CF04E315D3FC13AA41768A2D24DD1ED18FEFF00BE6B99F8C6E603E2ABE9AEE09E2791039F34FAC975CF1FA9FD68DE0DD5AFB4CF155ADB6E76B095C222840410DC80A7B75C9ACBE4F14B8EDABC6E4B8D7D1B68DE9069E0DD38AABB23951562B9CD608E8985A1C9D6B3C81586F7A01394706AB2EFF2D5B4C320E3AD555E0CA1A90D6B575E185700F13A797AFDFAE31FC627F9F35F40EABC86AE0FE374DBE25BCF9607F615B383E30F93F5AF9A81A99A81AD0CAEDDE46E18A99B5F4F34DC238E9462991D2BBCE57AA95AD46E34A5CC3B1B22AF658F07E2939E30C33420AD9F38C8ABCB184362AA214DB20F6AD874C1D2961A2CADA1DA05317836D84E7D90D653F20A1EA0DFF01703FEC34BCBFD2ADE3FED04D2571047F6ABB84719EF54DA3FFCA4ABB8FB5794CBEBD1E1F0CAE31CF1596E45431E9F9ACE0F7A5AB0BDC640AE45E35F1988F51D534658629364636B364E5B237061D863915D7E6C62B97FE2178406AD7D0DF598863B9883B4818EC32E178F57B8C631DF3471EBDBB55CBBF5E9CD7C47AA4336956C12DA185F905A3E467B7DC7B8FB52B6BABA3583C66DAD92ED1B7C68011DBA71C7504E0FBD0EF239ECE292DEE2D278CA48AE8AC01527B83EC7AD4747821B8B89A3BB9551E57DCA9B71E6019C107B0FEB5D2C76E6D41EFE4D5AD2CEDAD6CC35CC33995B6A0C1663C927AF61D491C1E95E927B7B86448EE6492E8AEC597273BD9B9233D060F4A3B4D15A5D7D269D0A426E1846F7133FA48CF51CF4E339AAF9C456FA9496F72ED349E684478880A33DFA73D69BF3DA16BE2083EA2489A39E476B58446CF31E5B68E8BEE2B5B1B086326E52586DC73FAD5D2C2D6DA8C4A92ADCC3B8C4DE710303A1EFDBAF1557346A933C7C32A31191DF151926362F01EBC9A0788ECEEE36631676CE318CA9E0FF002EB5F545AC8B2C31BA3064650CAC0F041E86BE382556059E280F276EE3D09F615F41FE0A7885B51D07FC32EC9FAAB20366EEAD176FE5D2B372E3BEDA3873D5D3A62F2DC0A20C9EB4143CF068A08CF5354340D16738A2BA92A7E2831C986A316CA75A50465FCD8AF20C9A15D3E1F834484E53FBD09B532BC9E94B6A77F0E99A7DC5E5CB6D86042EC7EDDBEE7A5364818E41AE2BF8D9E2B0F3AE87665A4543BA709DDBA81FA77AB30C3DE973CF58B9C78A75A1AEEB973A85E2B492BB160AA4E028E028F81D3F4CF7AAA05AEA3FF878554A1C01D703DC66BCB298E55410FA5BD418839E7E6A62D1A370D2DC212E085553939CD6F9F3A73EDDDDAC34F82EB111B98EE0CC482981DB3D7AF15B1EB579A97876EED05B5B44D2CCAAE4CA377A73E9DA3F4383559A7C452F215FA80C3665B8F71D0F35ED4EDDB50BA5904ECD71046238B7300A1793CFB63AD59094A0BDD445D5E1BBB82AF2C6DBA2FF003C819BD58F6E39FD2BD6D74BA8DBF977135C7936EA15246946D03AED03B773DFAD370684D169D75AB5C2BCA2D82AC843E3786383B7DFBF4ED54DACDCC52C9BA2B116F148A1228C21C11DC86E84E6A76883CB135B5F24FA6CED7377264FA981DCA472C4E739273DBA739A225CDD69B7D25CC9248F230F4B28C2AEE076E73D30DD477C715593AE674FA689DE70DE50287904671FC871EDC537A85C79D7521D41E57BBB9DABEBE06DC0018E38E3918FD6A2E46D6DB16ABA8DBEA7A5ADFDDA4715F4682198489BBCE23FCA467838C01834D7822E6C93C471C32DC0675E625D870DB80C904F70463A76AA0778F51BD8DECE2945B429EB51EA0CC3AE073DBBD39A05DBA78A2D4461005C469BA32ADB727248F7EFFCAB3F2CDE3B5DC375969F455864AAE3EF56ABCAE6AA34D3E95EBFAD5AC67D231D2B9AEA417FCA2A2FF97A564E71F358268004A485CF155974739AB29FA726AAEF3A71521AE6AA3AD70CF1FA95F124FD39553FB5775D50654F35C3FF0011863C44FF0031AFF7AD7C1F18FC96A86A0D446A19AD2C6EF76EC08A6D700555C326DA9497240E2BBCE51B9D971D6919581CF4A565B93CF5A5DAE0B1A019471E68AD874C71B456AB09CC82B61B06C20A131B0C6F902A3A87FF00613FFEC34BDBB9E944BE39B19BFF006D57CBFD32FF00E2DE3FEF0CE907F86B57F08DC39AD7F49FF96B57F01C0AF275E930F86B3D2B0C075CF35153C5648CFBD07065E6B49FC48599340B87B56C30FCDEAC7A71FEF8ADDA6C8EDC7B5683F8AD1CB2F85EE1A3F202C4CB2132A938E71C63BF3DE8C7FB457CBFD5C822B8D9E5DDDE5BC53C2602D3FF0011B0E73C0E0F0C3AD56EB365629AC288AE25546038C13B477018FC73FAD5DDDC53C3E1E65B9B5896DE47506478F90320100E3823AE71FCEA8FCBF32DEE959BCF8DD3D077ED2841C6E0B8CB71EFCF35BF1BF8733286750B5D36ED6DDB4C8673B372B2CA47A86382187F4C555081EDD6455F38F9B84C6DF531F9F6A734A2D6F1CAF1DC86451B555139DC71EAE7EDFBD3325B5E09E0BD7769401B8A9F4315071DFF00955B27B16DD01A7D802CB25D35C08E31B412B803DB9FE746D62D2C23489ACDA4C818986EDC4939C1F61ED5B1789F57544B3B7D2D505940AAEE8A4BAB4C3AE588E473DC62B5769105D12E9184B85C1546CB609FCE40C8FD07B519493A442AF19B51079644E5C06E5739F83F6AB2F0E6BDA8E89AE26A36A479B1E4957185607B1F834846B2585E4BB8AEE8D4AA316E037DBE697769E494EECAEFCB001B38FF00E296CDC3E2EA4DF8C5AE9FCB6B60BFFF00AB1FEF426FC5CF10B7E55B25CFB444FF007AE660ED6C161F3CF7A96E3DC81FAD57E90DEF5D1FFF00DD8F12E0E25B45FB41FF00CD41BF15BC52CB81776EBF6B75FEF5CF41C0FCDFBD6438C7E614DE988F7ADCE7FC46F12CC72DA8E0FF00DB120FED50FF00F703C4C41C6B13A8F8551FDAB4FDE3B115343B9C2A91B9B80051E907BD6D2FE38F12EC3236B57C42FF00A5F193ED5AE49A85C5CB4970C9BA62DBCC8DC9C9EA72687742E04E2DE146655C741F9BDC9A8B8759F6CEFB53DBDC1ED4D319F82FB1C8545FC7FF00133049114838E87D8F1FCAB729BC3FA6ADA5AC56324B24D284F35DD011EE76F715A542D140E8B12B99368CEEE41F9FFCF6AEB96BE2BD0EEAD2D00D2C7D4DB4016442485925230A40F83CD5D8EBF2AB26A5AAF87EFEC6DDE6B3454054159BA8083E6B5AB1B44786036B731C72CD201248233BA3F7271CE33CD74FBFF170BCD20DB5CC71433C70622CAEDCC59E8C3A7EB5CF74F7BC83CDD66DEFEDA1BD8660D1C446411D0E0F2318F8A352262D3C53A4DB45A44199E437D672005571B26181EA03A83D39F9AD524B99EF65B584C7B63F30089771FE19C81C0F73FD6B6AD6EE2E7598A49E6992E6621648F6B0642464637600C0CFF4AACD134317F617135D5DA432C303BC68704485790B91DCF6C77A8EBF01E96D1E18C9863FA768243E6386D8CEBDD88EC7B63A554595CABA3C370B7124B712FA6566C6548C63F9E0F15656B6170DA55CDD5F2449905096CF9A4F40187B0C7FE668F75A813A7585958012881C31BB8E3C0185F501DF001391F7C54E877F857E9F67716D72AE802AE4B6E284285E990739E94E693A803AD5ACEB236D59C8424F010B73D79FDE8777A88BAD2AE23820494850E25524F97CF2306830BDB456D1402079679CA333E30533D87273D73FB551C967E1761F775F4E69243C51B2B6411D6AE1015079AA3D053CBB3B74208C228E7ED57C80126B9B94D57525DC609E39351278AF38C93500452A439581EB55B767AD5848460D574FCE735312A2D440286B897E252EDF1003EF12FF00535DBF50E8462B89FE2763FC7938FF00A43FA9AD5C2C5E4FC69AFD6866A6D4335A98DDB1BD238A03C839AF3B134B31EA3BD779C90E7979E295327ABAD312264734B794CC720714261AB67CB0AD82CA5C28CD51DA42411C55F58C19C5017369EA231DE98BFE2C25FB01FBD42CA2D8054F5427E888C7E6651FBD53CDFF005E4BB867F386F4BFC8B8F6ABD88553696B841575157957A69064193CF18A2026A2871D2B26848374578C569DE3CBF6D3F44B89D52092361B4A4DD324718E0E79ADBAE0F15CCBF15EF9174CB5B28D81BAB99C0519E7D3EAFD3903F9D4E33755F25FE2E5DACCC44ACF316B9F3711A26F00640E430FB9FDA96FF0B885BC01E395A62BBDE20766C181FE63DCE73F6A96B57313AC691A34B0C3212016DAC4B72463E338CD674BB0D36F746BABCBABC686F236610C39C8FF00B7AFB0ADF8F4E6E492595D23CF6B630A2DD4718BB999C72B1E00C0F71965FE7493DEDF441C4D1A088601320CF1D4533A8ACBB56E629FCFBC7550F2747E983F18E9CFC562E248EE20572D24326CF2E470E4AB6D1D0FEBD3B0AB0A85E24CD671168D8C528591B60C0391CE7DF18A52EA28D254161994C43FE629CF1FA7418ACC3A803736D24A0B43805DC0F5A81C103B678EB567034706937132221B5B9DA238D0E1DB07924FF5140565CD8160EDE66472558F25F18CE4761F34A2C6925CB1790A20C67E3B56CB9BD874582F60B6B38A39494DA7059907BFB0AA0BA8ADE359248198DB3A8E718E7D87EB51510298409B561919B393B88E2B192BC4831838CD4CC291CD046AA5B3838CFBFBD15905E9983B229527A75FE551A483907DB06A4BE9FF007A66CEC15936B3B07CE393C54C69CEB769133A80C09DC78C01402F123C8E1517731A7A0B59E1B769F199092A800CFEBF7A3DBA5BC0CD226533C0627A81EDF7A4024AD74FBA52A0762DFD05481237BCB78D9FCD09BBAEEE720F04114D58B58496ACB22492CC30AAE38071D327B5434AD326BEB3BC96249258EDC61DF1D33F3EF44BA7886D8EFE17F2D87ADC37ABE0B7B9A929A92CAEAD2ECBC0109017CC7F30375EBC761FED567A5DDE748B98352DAB0A49B6362B8200E4F38C93C9FE74BF862C9A29EECC8BB3CC60119C1DCCBF3F1F1563E25B179A5945BA497113B8655007002FBFB839A790B4A5D5D34E922B5ADD3C4C8D0C53B2E13B1C06E9FBF7A4ECB437BB8008EE224820C1991B9C1CF233EF8C714DDCC5A9CFA4DBE9D3457021F2CCDB5972100EFF00FE6B65D03C342FECA5BA9F51F2919020013D24AAF4C8F703AFCD4E85BD28A32D6BA3182485228965DDBCC806E523DFB502EB4EB169A17B385842B1866FE26559B9E801FB543C417BE7DDC5147A7AC76E85B6A125F690A46727B63DEAB2D24B27BD558927803C05090FF988EFF04547F5EE23EAEAD5A29F4F91AFEE0C97BE60CC5C9760303181DFE7AF5A5AFE4B2B097E9E721E169849242C36CA8AA49C1F63DE81612ACEB19BBBC2918CC71C8C8BBF70CF42393D075A4ADEF6D24D42E64BAC10CA53CD95724E78CEDF7FF6A6F6DF753F128222D235DDBDB482DA57936B673BF3F61DB23351B39121B6B6958B457F136D0C4918C1EE3DF9FD31F7A3E9739B2B6937332DB4C708800738EC49FB806AD2F25693477315BA8B8C8937336E59559BF39E3F3027F91C5559C91763BBA77CD025F3ACE0727394183FA55FAFE5E6B59F08A11A15836E27312F5EBD2B678F8E0D72F2FAEA63F23C462823A9A339C8C9A03FED4A72F236722929C8C53727BD57DC1382288151A96306B89FE271FF00EBD1E071E48FEB5DA7523E935C53F129B3E2003B08947F5AD7C2C9E4FC69C6A0688D4335A585DACA029F34B884EEA7E250C714CADB679AEF392A57871EF448EDF232055BFD264F4CD351D8E17A50159696D961C55ED9DB15C7151B7B7DAFD2ADE0501450688C31E281AC604502FF00AA51FB0356017DAABB57E6F2D53D8331FDAB379797AF156AF1A6F922CB4E53B47DAAD50F18AAEB20420C558A75AF2F1E845462B81526C8AC0EA2A64161C548A4EE1B2181AE1DF8AAC20D7F7EC2ACC85E275504970001D7A7DC1FD2BB7DCAE3245714F1DDDB4DAA98EEE3844F687CC0872432E0F23BF7008F9156717D53CDFD5CF62BFB955FA69A440A3382D182407FCC739CF03E6AFBC0FE187D735C82DE0BAFA58656666B8917D2B12824B63AE4E3A7EF55F7FA7DBD9DBF9F7D0BA8B98835BF96490A437A8B13D481ED43D165BC588A5B48CC0021F270BB4E3823AE7F6ADB27FAE7DA26AB6292EBB77696DAA7D512E6DD18465449DBB9E8686D65711DB2C32C72C0B1E633249F91C64E780391914DEB7A7C51DAC12B4662707D7247193BB00919FB9FE9577AAF8C229F4D8A2983A491A8D904A318C0E3E0022AC9A2352686F1A29A38CC4CA08550005C8C647A7AD590903AC712C63CA8A26531A273C8196C76E7FA5420D5E2BE4024B451781C9DABF9881DCD58696F0CA2DE39B5036C2381E62A899791830DA3DB1D7F9544814F2ADDDADB448ACD25B3928565CFA87C01DBE6AC67B1D3AD74357B8695E32A02EDC9C13EC3FDEA124E765DDA1924958389559D76951DD73D4139C62AB45E9B4F2246B9262446844417E7E7BF7A9B524A595608D5403E6023616EBB48EF469E40278C5B928187AB8F4918EB9EB9ADD7F0DF456D6B52D4A5934182EAD218433BCCE5447B8E1769CF739E7E2B51D5B4B7B0D625827DD1CB1CAC9E51EA076F8351104A20D140676972036DDBDC7CFDA9D94CEB1C33B291110423374F9A4628D105C2382CE83F2B0F9AB448E6BCD3AD05F0315914668DF923683C91EE7B54C90C634ED2CEA61AFA5953E82D86C772C576FC802AB66444D419E20F28CF9836F2B83DCD377F259C365E56997173B3389030DBBB3D7A76344D2247431490C2BCFF000D43E4923EDDEA7A2BD07D6DB99E2B34451282D2618E368EA7AD1DB5569E77823B58A2425436577E428C8E3FDAA5AC437525C1F2D42386F2BC989C311C64E053FA76892DB44D74DE6B5E0725531C818E33F07BD49773F28D96A3AA79D6DA8C05D2256C6194618AE467E78E3EE0D747F06D9A78834C89E24617EF2B298F6E71C7043678EFDAB9DCD6D73246E44F3184FA914A15119CF208F6CE47E95B7F8326BED2FCDBBB29842F12A8903B02D920FA80FD08FB9A785B45F11EA975A4DF369D3215566DB2BAED017B1078E9C735437D79325A472E8D9B8B3762BBB6B0504633C7D8D5F3E9377AEDECEB70A913CF1B3ACD3C8028C72771EBFCAA163A65BDDE90F65797765024126C64858AF98C40F528239C8032C7814D51B256DE1F9FFF004BDD4B62E9797134B18746059A35C16CA73D323049F7148EA3A3D9ACA915824EB6A899F3198165931EA18F639E39AD874FD6A5F075D6A76D0624411181CB63D418F51DB8F71F1555AD9D4B4ED3EDAEE4D38C769764345329DC8CA464E71C83F1490D1AE6B1669650695A823420C8CE7283D711183CA8C020E460FC76A4960FAE33DCB400432068DB6A124BB1CF392318EA08E697BA94C3A8490CE246958AB0C0C2A82327A672306AEBCA918796932490E449B4120061FE61EE71C1FB54CBF80427B84490D95B28962882897C95C1217A9E7201EA685ABDEC936916B0C4AAB6A3D08A92F39C962EC33F3FED4D58B5C5BDE5F158E1BBFA888B9DBFF4F8C7A97A8E3DE919227B15FA6F425C40AC9B360F52B739F9EF59B97EB463BD69F45781E4CF87EC3070042A79FB56D487233CD6A3E080E341B1DC3FE8AFF4ADB62C639AE765F5D2C3E32DF96812F00D1CF5E7A52B2120F3D2956C2B3702ABE763835613E7AE6ABAE5BB5111A52EA64ED35C47F109B7788E41FE9451FB576AD45BF3035C2FC6F2F99E24BCE721485FE4056CE0F8C5E535F6A81A93546B431BBCA261B81561026450A340DF029D8805AEF3942C300F6A712118A5E29314F40E1A83EA0061A9A0229D0808A8F95CD034C26715517E776AEA3FD310FDCD5D2AE2A9263BF5C9F1D1703F6AC1FA85D713678337CABDB3C6D14F039C6291B4E314EC79CE7B579E778C4433D6A6C0F3505CE7E2B2ED8068452376E02903AD723FC48855AE8CCA1D99626EFE9DC3951FB93DB245753BD703775AE41F8973335D8F3A1325A8512314246D2A79CE3B63DFBD59C7D64A39AFF16B524705D5969B15C3492DEC65B72CCA4C5E5E010146718F7F7AA5D79A76BF66748D37164760703391955E721402052E93DDAEA3F516CE8A1A43144B29CED527B03DAACF5753A8451F9B6A12E8E374A8BFF34741B7A72715B77B73EC0B4AD4EF2D41B70775B2AB0599816E73F987B8EBC7C54F54B18A4D2AD2E2182137934ACB82A55881CE40CE31C77AB3D16DE48EEA40EC89801A581981DA581DB803B8C1C9F9F9AA7BCD62EA7BD48EEADD6282390F991B264633D73D738AB3734566CED9F50D5711A2117084AB95C1723F36DF6C1CFF002AB11A4F9F716AB2A4B08CB27D4A26E6C670320F4EB9CD236732A2FD5F91E41B8C436E50EE2871C9E7EDFBD6E7A9F86B5DF0FE8FA5DF4F3DB49692C812460492CCD9201CF7C6718F6A2208E9F6B656A459C1A91BE08A5A45DC0A31CF271ED9FDEA99F51D3ED7579418668EDD15991E2938624750318E69FBAD0E4B28A4BDD3A7511CC84326013B39FCA31CE4F7AD74417096D2E9F1C5E5911E183E092C06480477F8A9BB83A6DBE1EF144FA41B95D204B716F347B9A19932AE579FE60678AA9F105DDE6B3736D797762629A52CCD274E0F4FD2AA0DADE59D9C5F58E2DA2915B66D3961C765CF7200CF6ADBB44B0D5BC653A5B4B68C2086DD7CD7848DDC2E036DE8DDB38A3E8693197B5926DAAECE41C8278A2DA3CB3596E9D894525510938E7AF1DBFBD58C8BE7DC47692409673451811BB37E603202BFC9C75A51D5E398C73294653EA0C7A52A4E697A44B368B717A628638226D854B61893F1DFEF47D3AD4CFA829BC905A98182C41860363A7F4AB5D234F32E9D3DCDF4922C712B48B1AFF9401CB11F1FD69DB2D0F44D7A1B9BDB5D6DA37B78576C32C454B9CF4EF827DC714F242EEB695D0B4D83C149AD69C166BE5693CD901C15273B413D307A57383ACDF4571F532CBB5236DAD0291B5CFB0A7750D6913474D26D354924B0C8DD021257CD0F9DCD9C6463A7C8E9CD2B7366F6975234B6913807CF6241DB230E76AE38C107ED4DD08E811EBFA7DFF826C967B6861BA8D890EE32E496CEDCFB726B58B4D65C5E1FA68BE89519A465C73201DFFAD2F73A96937DAAAA5B3DD4104A8A42643AA8C6719201C8C63F4153B4D2EE35AD56E469504AACF68F2BA4920198C719E471FF00CD1058E8571E386D4960165A7AA48B088563588309F3C6307DEB9A5F6BB72D3AC89008DA49090B1E0326386FDBFA77A9DDD9ADBDB4B1B3CE088D640B19C98CE71BB8E833FAD0DB479658A65BC89E1B78BD51956FE23138239E87A9CF4E4D4DFF00D2269B3DD6AD169DA5F98D339866554D8BD65048E31D0FBD6BF71AB5FEAD14164CAF6E5A6244793FC42AB91BBF4E3F5A86B5A7CD158D888B1328CFD3A636B051D73EDC8C67E09AA496FEEF50BD468D144E1408944815D71C124F718EC7A8A8DFAC4C3DA73D92CA259A19242B33CAF6A798C0071853F61459B5492694456E2194609D82321A3E7AFDB1DBDE996D3B5448447A7DB42B6FB4CBE62CDE61DD901BF300793F1DA90B5865D46F232F3DCDB6D56113CC51533C64E491DC0E3F5A8B7E6CDFFB12182DECAF6FA09A666964CA0DCC417E32707DFB50676448209A20AD244A502B7A98024E33FEA3CF5A6AF2C8BF87F75E859AED589CC870E99C1DC31D79E3AD257B94D4ACEC6D23CC4B0FE7906DDFDF3F18CF00FC553CBF6ADE37D01E0E1E5E8F66B8C15894741EC2B6A8DF2062B59F0E47B74F813392A8056C910DA06457372FAE961F0538E69490673ED4E3F4E05292E7D42956CA4651DB355B78DB118D594E704E0556DD30DB93D7A62889DA87526C03F6AE0BE286F335EBE6CFFD661FDABBBEA8491C7435C03566F3351BA7FF0054AC7F7ADBC33A73BC9BBA44D409A99343357B2BBEDBCC724D3A1CE01A4E18F6B6714E478AEF3946627CE335616AC2AA4B018229CB4909CD065D46DC51323149C4E70051C1CD074FED5AF4399355BA61FF00F92B6351DFB56B5A59DD348FFEA727F7AE5FEA796B091BFC0C779DAD8EDCF14EA741485BF1D69E888C570DD990CAF415198E16A68063E685338C510B54FA83ED0C6B9278E98CB712AA2EE758CE1802707AF2075C9E2BAB6A8D846FB5733D4ADA4BDFF1668997708C2287E15BB907F4AD1C385CF2E99B9F298E3DB9CDC69734B19976C819084756E36BEEE021EFC76ED4DE97E22582E238AE4196D7CC0D1CB32966518E7A7520E454F4DBC7B1D3AEECDAD9D85CB830C47D4A87FD43BF4AA92D7112A821162801C4AE0E64424E06DCF4CE79AD1188DDC5EB5BEA2D71645D215CC9E583B9E401F9DDEDC73FA7CD2B24571797ED6C87CB1712928B23EEC03C9CE2AF7C3DAD18520B3B881618D2DD9CC8CC7D7CFF0097EFFF00E2ABEEAEE38754FAEB0B7304699760EBB48620E148F6A744E9649A1CEF22DAC73DB292C599549C41CFFA7BE783584BDBC6B9B2D2F5279C049CF96C6427B6036DE98E9CFDEB5795A5679AFA676B79E4FF0032120939E7BF1EF576DAB2DCE996F15E2798D18593CE600EF5070723AF5A9951A6D70B6A5AA5BAD8E9B398ACF4C61E68318677C9F71CE3FA56BB2FD6C17B2DDDC4010C7299385244A3760B0F6C0A969BE20BFD165DFA36A0D6F81E56E8872F19E72DEE3B7C54EC6E2C6D669AC7518E48EEEE1D65FA8763B94FB67A75C9CD35A890184E9F7BADCDFE29A819ED02B4D184524AB7185C63DB3D3DA9EF0FEB7269961E6C77924B1CD2EC48E2421933D541F6E452BAB68C207FF10B394CD19DC4C8BFC472C470063DF919A66D2C960D2EF31E4C97C9123DBC2873E511D4907BD444D9B0F49D42D6E757B64F15868E07987F18215C46724E47B1F4F3D6A3E266B56BD64B692474DDE58D89B81527A83F1C63DFA66AB3408AE26D62D1EE198852C660EBCA28EE73D3A71561A9A436F25B5DD95C2DC49E695405B70033CED4EFC9A9DC4165974F4BCBDDF7132318C2C65090AC3186539A05B1BC36B7B1E992CBF4CA4382A4027B127E29B834C4BAD32670F1B84213D4779DFF00E6C63A64E31F7A13DDC16B21826D24C8A6058A5DAD824A9C7403AF1CE79A8A9D2B258D9EDA3900958B9264DC0AFAB9C60E319C7356A35131D97D2C4649AF0B08E0E8E02E3041C74E7A52A4FD7978ED96490CAE4C500E0A9C8CB11D00C74C7CD6638A2D320D42DEEADE58EF723CAF2D77003B9C83F6A3617DA85B417D7B05AF875668644856491DD71B09032BB87F9739E4F5AD97C317B751CF7725F5A4D3C4D69C4F1B286508410071D0F39FBD6B9E0DBD974C0B169F79756E2E46DB9450A37363F213EC4638F7AB86F13DAE97A94516E930461A488E02A9C82B91F1C1A6DE915E8669A0B8B8BDBA36D0C4C1D1A3660460F2146781CD7878F2E2E34A8B4BBDB28BD1366308BEA518E99EFCE38AA8D416CB56B2DD15E48D0BCD850131B7009C9F7EBCD4E2FACFF027D3D5E14792ED24F3914EF74E40C3765C8C9A7DFE4B22C2D2E6D2EEE6E5F56D36E2E5A48772244C02A1CE379EFB46791F3494E7468EE7E921F2BEAD392A5325B1CEDCFB1A7AF34F867D16D6DEE1649352964F2CCFBB091A927D59CE4F503ED9AD4F57B136371369DB9AF26B69CA968589DE98E769F8F7A8B7BE8D22C6CFEAECAE565B09B6C13111658FE51805873D3938CF4E0D3979E75BCA679CF930AE5A28DC655B2080BB7AE73DEB1A75A69D12A18E692E0C1B99637396F2F6FE5EB83CE7A7B5075DD427BDB49EED171A7B48B6C642327711B80C76E9FB54DB2C13E8B757B3DD49A6DCDC0105B48CA8A3B3303D2AB0C77D2EBB1C4D0049239F634CC719F6CE7E07B76A6E3B6325A486ED36DBAC1E96CE2352467701EE703A5575BCE975AADB4B35D493B318C00F1F2C461704F7E9D7E2B2E776BB1E9F48787C8102E3B802B6088F15AE68C311463E2B6284FA47CD73AFD74F1F83B52931E0D32C703E6969882A7B541E42371CA9355775CA9C5584E08FB556DC3601F6A99135AFEAEC163739E003FD2BE7CB96DD2BB7BB135DEBC43214B2B8627F2C6C7F9035C09F80056EE2EA39DE4DEF411A81A99A877AB599F40AB0C715343CF5E7DA91B7933CE689E76D6E2BBCE56CF8524F34EDBFA45236D26F51EF4D1256834A7D1F1DE9A85F355314C0F1DE9FB66C91424EDD49E5D9CCFF00E9427F6AA1D1D31125596B0F8D2A7F95C7F3A574A50225FB5713F55BDC8EB7E9F3AB5756E33814FC2A01E693B71C0C53F08F7AE4BA8305C0EB49DCF1DE9D6184A46E8F19EB5246BFAD36226AD1EE34F37FA1DE2464896590BA11D720F1FD08FD6B71D7E4C40E7DC5504334765A289A4C6D442E73C67AF7FD6BA9FA7E1ED6D60F36EA48E377D7F711C2EB79223CD1390102952BB78EC00E4669AB88AC8E9F6503936F7BE4067654DE3072406C1E323078E9D2A3AD4906A0F3A90D0F98CCF193CA824E49E074C673DE93B2BDFA248E0BA8E3F244A4B129BCBAF4CE7DB038A5B356C500FD34B98A49EE4236E1996456022183853DBB0E9D2B64B793499A59E02239EE0466567639F31B1C9C9EF4CEB56FFF00A83E834E8234B5BEBA6564888E48C10A09E838AAAB9D165D2AFD368496482321D0672C4E415DBD7233D3E29A6350AABF8A2961F39166B8B83B5E77439405B9DA78FCC0601AB2D3751864BB961BDB6B58AD2E136A92BB58F6038EBFF868C9A25ECAF6B2C72AC1C79CC09E14F50A57BD54DE3F9BAC2DC6A036DB79D80C060803B63FB545EA8A6AEEC4C376E78D92659731E408C0E0F5A5A1B1B8F35A39435C4821DC3737085B9186E73EFD866B79BCD4B4FD70A5BB35B9658C0291AEC257AF3EFDAAA6D2FAD345B7BC531492800C7344D1E483B8E1830E38C7EF4DA88F6B1AB472CD058A451CB35BE1FCC00B6470392303DC559BC1776E6D2F6DE522FAEBFEAE01C83DC76CD585A5DE99ABDCADA8B7923213D132F191D0827DB9EF4A096D34ABF82ECDB98A0898ADB83C92C0F56FB1A27FA97AFA6601EE61883A5C332C91608667651BCE41CE33C81DB38A62E34EB21E1CFAAB1112EA31B2462D83E5D8939276E7B6093DBA513FF52B5BE0DF69FB6627CC8C2F0A5893D7D8534DAC69B1C72DF9B060F92920B77DC8B21E7729F61FDEA7735B476D3A476B3B459609E58EEA472CFB1B0A00FB1F7CD5C462EB4CB159637569F5152D3B4982C833D47B139C9FBD39A2D9E91AAEAC2DECD192554DCDE603FC45EAC4FB10334C6AD71A65B08E468E69894F29E33C32F3D79EDC0E95127436A4B3967168E44ED6F71E5BB99D90FF00CB1F6E839E3E6B170D71A7DC4178B289AFA55CF2A48DB8186F9C8F7AD92C1AC6FE5BBFF0FF002E29576A4A66E7742464955F6CE39E9C502EA1D12EB63DE34314C9160416B26E55DBC9DA7DCF2719A2762D4A78F52BFD36DEE56C522F5170D0FF009A43D0364F3C03CE3A5543C22DAE25B4DB17952C7E506232C24DCAC7D47A723A8ED9ABAD2A0865686F74C91BE92DC9668E404B8C2E3683D31DFF00534A26AD797D05C7916D6E8E928753B3D4C0F5001EF537744CB46B4CB3BF9B4989B645E4DB332905423303C6F27BF240CF6158F176A905E45A7C38BB84AAFF00123C2C63F30C82475CF507AF3D2ADF4BD72D8468F7513DCA487E9E68E15CB64F3800F5CE3F9D29AC7D2DFCB75A9E963CBB6B9708B0DD3731A83B4804FF0098014D6F40A788A7B0B5F125A4FA348F3DBCF12A88A390B2A36002093D7DE9FB3B0BB9ADE0BA9DE482D64632018C1073C8C9EDF22A8AD626D23EA9638F041568C81B8E4BE02B1FF2E41EB569771EA0DA65EBDCBCAD1470E228C8F5367AF1CE31FDAA22296BF9F4D648E3D2EE196E228DC03B4B23264965C9E9F98D0B44B39AEA392DCAA456AAC3CC8DA52A40C10243D7A6ECE38CD575EA997CB6B6B2B98D142348C4E170D838CF4EE003EC726AF0DF4F7D765B4FD3BCA854913794DBCA95FCA33EFC77EB518FDED349DFCB7575622D6492D9E0B1552B2463D328CE03633FB51A7FF86D7ADA2DA628DDE20A160037860AE4B1C9C1048E0763D7DD5D127B4B9B85335D8B4B9771941186000F7CD38D73BB55B4BC23EAA16BA31A391E95518553EDBB0067E3154E73EAFC7BD3BE690BFC14CE7357F0676823071541A44ACF6E818E703357D0B60573F2FAE963F0C49D00A526E54D34C72B4B4DED4AB22BAE7BE2A9EEDB8E6AE2ECED1F7AA2BE2704FB53482B55F17CBE5E8D7CF9FF00A4DFD31FDEB86BF4E6BB1F8FA411F87EF39C16013F9915C6DCE79F7ADDC73F8B97E45DE681A19A99A81AB14BB4DB3955E78A9BCC3775A499CAF4A1072CE38AEF392D8AD26C28A6CDCA91826A9E07C2815EF31B77078A132ADD24F57078AB5B2901C56B904A78AB8B27E450694FEBADFF00D348EECCA3F7A9E9ABE95E3B6294D62406DE05EB9907EC2AC34F5E00AE07EA77FE4D3B9FA74FF8F6B6817A558423B52902F434F210141C735CE6FACC8404E955979C038A7A57CF0781485D1014F39144234FF12C9B6DA4CFB114AC283E9563FF00B369FE58FEF44F13B8F4A7FA9C0FEF4B4F318E091A35DEEAB90B9C671DABB5FA663FC72AE5F9D9772392EB76D2C725EDABE328C191C2F0DCF56EBCE3ED4BAB48D12A4FB18DCB001A3E44214E0107FB7B55EF8A6FA09C4D77A636E8997128F2891BBFD24FF6EDD6A8F4BFA40AD2DCBBA5AC4A8890CBCB173EDB4F23AF519AA793ACEC2E3778ECDEA370B6FAC40358B8F38093D6F11C6001E9E9CE3AF5F9A9EB1E5697AA1BDB302433AB3C60A962DBB92D9CF5A0DD49A75AFD579BA7DE5C5F4C844B2B63CA0D9CA84039E839C9AACD392E0C0B821C4AAC220CA4B0047385CF1F06A3BFC216763E20BA6BBB05BB4658E4CFE518F341E01C7B55D78A2C2D974DD2AF2DBE8CDBA48E2E6377C3E4367201F81D47C56B69622F2ED04904CAEDB437A8008A0004FBF27FAD5FEA1A7DA6A11C76D14D1A5D447697E19C479CE319E0839E7E69B1FF00D8D2B353D723D4F5DB6B8D2B4C8208D544318C7238EE41E4F3D4D0E1B884D93E9B776EF1DA9970254FE1904E79C75ADAFC4FA9E8DA76948FA558470DF28589958804B01CEDC0E573CF3DEB5EB1D6AC750961FABB10F7610B042BB81C6780739E71516760945A5C777AE5A43F57FF0005948229321597B82477E4F5ABCF12E957363E2096CE502466F4C92DB8571B36FE60FF00EACD534BA8CB6C267B5B30D7630ED293E6084765FF00CE94FE93E219E796EE0BC5105C24659542FA54852793D73F144B3E021AC5A5CDABD85BDCDC12891858C11CB2B1CEDC74E3F6CD5D69BE158AFE608F18776CB01036C551F03F4E6A16F71A878A354B7D3B5495FC9758E25927602484119E0E0704F4CF38EB50D6ED1FC2FAC43690CB218C29596DA420B3027071CF5EE3F4A79242EEEF46B59F0F3693A85ACB697921B89972DF4EE395C72081C7C62959B4C11E8F15D6A967B660E5D1F92C493FB81C71F154A6296492F6F4DC0B492D9888CC921677278F6E48AD9EFEEA79FC356C970CF24CC0301190DEA033903A738ED46E27B0FC25A09BEB0D5AFEE6E419237315C40817055949071D8718E28FA468B649E2353ABB9B5B4680BC7218F7056032158551E9BAD358491CF15AADB5802B1DCAB292ECF824F3DCF1D3B7B56E1A8F8BEDB52B6B796FA2825B4922281962DAEA718DC71DC7BD4CD16EF605BC9A6FF845EDD59ADE1D452593680DB612A7FECF7CE4F1EF5533EA7FFA6AEA1BFD1C5B5B9BA8C46CB7004CEA71EA6E795E7BD5A683ACCB65E1EBD363A44DA868AAEA9737063195CF380E46549CFF004AD7F5982C2E3C5D19696E2D34AB87054CA8656823C71BB1DC723A74E68DA62E75C9B49B8F0F598B29E5935079899023EE1113C865EF9249AD3B4EB35B9D375279664DB6AA1C24EE57249C1DBCF5A0C0852F2E2E2D2E23682294E3B19403C103DB1EF5B5DAA5A6B3E1D5FAAD3678E680949258807EA0ED6C63D8724FB7069676652DC4F771C503C92305455098C1889539556E7E4904F34C8D6B546481E6923895C946FE1F0C48E838EBCD21AAA40B38B692463FC5562B0F20A28C1639E8DDB1D0F7AB0D0E337732C32CA658526055A6425557041E878206289DD1F17BA8E8568344B1BFD3E57512AB79CAA0A08D95573D786393CFE95AEF87750B9D0C5CEC91E059E2250C8372124F53F1FDEAFA4FA7FF00058A0BAB9548A47630202C0A9C0C92093F039EB8AA68FCE8E378676D96F01284BA060DB7D58C7707D8126A32EAEC4834915E241631A01717718370B3BB0DB20C700375C03D8F14925CDDCF706EA6076349149318D42C6C3A06DA3BE7DBDE9649E18A54649FCA0F120022CB21E79C82781DF144D4E389EFA4903878227CAF92C02C8A7804276E993F6AAEDDAC9FC5F45E8D206B58F6FF0099455F40DC015A7F85AE7CED1ED24047280F07E2B63B59B793835CFCE6ABA785E970A73CD027248E2B3112473D2A321014D56B62B2EF3839FE75AF5CCBBF79231835797CC76F15AEDEBE09156608CBA68FF8972EDD176E7F3C8A3FAD7296EB5D1BF1365FF84B68F3F99D9BF90C7F7AE74FDEB7E1351CAE6BBCB619A19A21A89A6D2B7568DF7B7269858C9E455740DB5EAE60E505779C9BD205594508CDB720D3926029AA7BBCEF38A107A0B9258006AF6C2EB1B726B4D8E431919AB8B19F777A3466CD772F9D2DAA83DC9AD8F4F4C2A9AD46C0F9B7D18ECAB5B9D900116BCDFEA177CD5E87C19FF1458C5C0A997047CD04300BC501E4C1CF6AC2DA719BD38EB55D7CC550D371B823E290D4DFD0714C5B1A46BD2EEBD8549CE0EEA49E50E8CAC010C3041A96A6DE66A6D8FF002AFF007A5A525119B6B3103F2AF53F15E93C0C663C1BAE17997DB95A1F8874DB883EAC46628635CB8C3EDDE99E140EFF00A66B5FBB502D2D4FD3A248918663BB9707F29FE55747546B7D46E3EA6DBCDB8883470249CF96E4E318F61D6AB351CCDE7ADC2C7F56C73B816C8EFDFA60718AE7677795ABA7F586A3B7960D2AE2ED5591C48B13DB61B231DD8918E8738FBD1B4ED5EEA0D4ADD2ECA5AC0B868DBCBC1DBDB07DB3CE6A69BEDE07B48C3CD6B32A3CD045871BB03D796E4B673CD522C665BA0F785894462AB23F2F8FCBEAF8FED517AEE05FF8D1EF279A6D464919DAE240AED08DB8C0E012063279247CD53C71BAEA16E2CE4686F193F88C7920939E7DB8ADC6D758B7B8F0D5A68914E60BA7944B3306DFE63ED2338238183CF3DAA993549349BBBA4820B5BB696230EF65DB863D5A361CE463EC467229E96ED68D657DADA5EDBAC50FD3DA8FAB32B28F5055C139EB81EC3EF541A558DE4DAC472C17102091B6B48B8D883B1047C7EB563A5EA1E624B63F4ED088A2DC093FB1F6E692D6648350B968ECC379335CF3334801CE396C003D3927B7434593F099575A9E90F6C2576BA56C4C44D8FE2218C0EBFF00765AA8AC254B7BD6B88AC4C8F1AC9B5555B72E07F9B3C1FB5063796D3589ECED2FA4944E3CB0E9C86627B7F4CFB51355B9B9B3FF000F85679E19BF3480FF0098E7A93516CA85A59C2FF4534A7D0176B177523CC523A018C9C67EC3B54FC37A6DBDFC92DCDE9F3311958B9CB6F0782C0F6EB5516D7F35ADF5E5B095AED6572A1A4721460E739FDE822EA6293DB2DD325B4AA66F4C9FC4240EE7DCD4FB235DED770DBDA3F88E6B6D620C279322AC4ADB705861393D79C1A64E91730A47369ECF6F73128DABBC19576F031D864E33F6AA0482FEE7417D4432A411111FD5364BB11D14376EBD29FD6637F0F6A705E4174B7578C8B224A8FC3A95C06E0F3F23DC54CA3F25755B3D4CDA41F5819E6926DE18AFAA52DC9DDEE7B55FD8C08DA532EA16EF0BC510314B09DAA720E5594FB1AA4B8D62FECADA6B491DC4B2812A360B0563C9C03FF99A4C6B17F6D22C52BC723B8CE655CEDCF3D3A546E44D957FA5EB9A969BA65C68EB6EB28D471216887E5CF19F6E801ADB747D0AC469B1AAEA41AF9519A449C0851060E7D44E0F6FDEB4D8B5C8EDA29E49D23130511C522A1C38031FD474AAEB4D4F569D6D9448424ACD928833C751CF4153332EA9B8ACA56D5B65ACCCA64872E55015507AE3E3FBD5E4F0CBE1EF06A2586AA127BC474B9B6859B7950DC1624609C1EC7B8A4EFEF7E9B4CB49EC6CE0F3D079336242D273D083DB18AD65279148D979224770F97DB93820FE5FE9FB51B913314CF952D9C704104BE748464E3D5B7A1CFB0CF39ADCA4974FD3F471736932CD22A832451F41B400074EBC726AB6F82DF08EE123FA5B18E22CF3039690F0086E077E692D75E1F104D67F416F696E608123C34A4B4EFD0B73CF3FA71529B36622D4535F3E543125BDD48BB5A671B94019F4FF004C546F34D274537D6C52568016977FA9B7AB6372F38CFBFC55AEB1A71D3DEE9AED21BF1E547BE36C05893603C0E72C0F19EBC56AB2EA57714535ABADB5F592FA991D777A78E878E9C7355DDFE4D13D1605BB45510C655E4DAF31427AF4C01FD6A7AC5C69E6480D9BDD5ACF1831CB184001C64139F7E3F5A9DAC2D73A46F6411AC44178E13CC9193CE40EBF19F6A4F575568F7C513246F3398D9C1CEDE38FDB3FAD2535768FC39BAF3FC2768FBF7E14827EC6B6DD326DCC715CEFF0BDCC3E122AC41C3BE08EE335BA68B2EE40DCF3D8561CE76E8F15DE31B5C4C76F3509DF820506390851C56246C024D57A5F085F37A78AD6AFE43927E2AFEF0EEDDDAB59D51B6AF15671CEC9C9751CDFF11E5DF756A99E4213FCCD694C2B67F1D49BF59DBFE98D40FEB5ACB0AE849A72392EE864542884543152875080066AB6808DA2AAAD41CD3EAFB45779CAC8799874A426C31C91539E5E38E6946978C1342201720678A6F4C0C0F3D29095B2DD69FB12405A0CDA743F55D31F6C0ADDAD82ECC935A3F875CB163EEC6B6E85F6A824D794F2AEF9B27A6F1E6B8E43B349B57D24934934FB9883D6A1712F1906AAAE6E191831FDAB3AF95B242FE8EDC5576AD36D43C512D2757B7DCA7AD556AD3FA1C6699197C6BF696CD7525CCF8C8DFB41FB0AA5F135FA69A26B6D9209FCB0D1B2305E49C77E8475ADDBC37103A4A3E3976763FCCD73BFC549E25D4E18437A98AA9C0E411CFF0071D2BD14BFB7E3C91C1CFF00972DAD2B59B9F30CD0B5C24D20C492CC54E64949C90327391D3E683656DEA9E108F7572492D1156E39E5891C91F158BCFA8BCBAFAD60E656473974DDBB1C0C1EFD47346D22E6E1350B9BC4BA16F768A55A35201901CAB60F2318EB5CEBF57262FD6CAE6E534E8E433BA848D587A10E7EF92783CD4E2BA82F6DEDA6D411408DCC723850A58B74C0EF8A4EF6078EEADD4C724122A1CEE6CAB30E4B0C71DC7F2A5EC27B749D64B9937C79248639E3DC0FF57C54DA990D4C5618EDE7B40558101A51C391D038520606063E719ABFD634FB9D4F4E5D6CBC730128458E1554DC472CC1324EDC907E493D294D4ACE4B258A4B542F6BE56D0A465E20141E73DF9E3F5AA98AEA5B29EC267BA7116E2C42F23693DF1C1EDDFBD4FC46C34B3B83A9A3DD32A992453B598FA8139E46738C56D367A535B95F29E358D4167558F2CC39E013CE39FEF5AFEBB721EF9E4B4710AB80A59948DF9EB823B63DA81A1DE5CD85EDA88265996651180E72B93CED07B1E9513AA28E64834FBC8E792D1A38B709C2C3F98724007278C77AB5BEBFB3D7888208E533C59758F852E31EFF001ED419ED525BDBB92FD244B84F52C982522C8C61BDF39181EF4E699A46AD647FF51D907912C265F3E48B1B5181F4E54F638FD69C744E49F4AB3F0D08ADA39DF56131132B00405239EBC8208C7CFC504C7A7C1A64B7324B14B7A08E546D68C37F971D3B9A9DF33CD7F73A849A5C9898EE96594E36C8F920E3B0EA47DA93D3ACDC58CF2CB6E3CF6DAF1BC8C087C3679F6E78FDAA3B1D19D0EEE1532E9EF7125BE9B249EB8DE4389FB2823A6475C81575ACD9586A3ACE9F65A069AA92AC6034026322B15C93D46573939C935496A9A84B7CEEFA7E5510C936E1B5635C8E73EDD3A7BD34B7CD6D3DD5F4718DB8C22A00143B73807AB01CF353AD17A6754B1696E0C50CD0B4BE61326E6DAD08231B7A90429F619FBD557D20B645924097320057C9720903A1208FB75ED9A35AE9E2EF47D4F502228BC870F2EE994161C1DA149DC79C1E9FD2ACB489D974A8214D27EAA6865FA90D20C36CFF00493ED8E6A120C5796AE4C4D6ED6D3F9184573BA2438E47DF8CE7DCD3ADA5A69DA1DBDC490DCDC4D712AF911880ED70719CB67B138E3BFC54AE258F53D5A0B94B41A6DAC9130481897F302E73C9EBCE4562F6F752D4ED6DEE8DD25BC563930AAF0AA1B1E9F839FDCD323E85A85D4D3CCF0DF69C6CED957CC48B2C0BB1E09604FABBD59F8734EB069FFF00AAC4C64B59131140400F091D73DB8181C7C9AA3B9D5EE352920D42FE3637108F2E2D9CAF03A3639CF5AA5B35B8B98EE5EDE5291742CA7009EA071CE7DAA2E5DA74DBBC5D1496CF34BA4C62D34920A345BCCAE549185391EE055208A2F36C668AD257564E483821F9C11DB04E3B7EB4FD85CCC34189A633AED7237B36581CE437DB822AC5534BBCD26D9ACE4BA5BE2195DA4036019E063DA8D6FB1BD2BE0DF7D0EF9EF16DD4B2E0B93BC606186738F9EF5ED5EC63B53117885C97C4491468446C739E4F504F3C0E2948A3B36BBBDB6BC93CA82162F19DA580C756C0E71D298B692CACB4F86692E8DCCCCCCEB03E41DD8CAE3DBA75F9A5B766F8859190DFB2DA4CB1A8055E6F2F62E33D8678357B696B68FA5186F5BE96420A46CDF95181DDB9727F36077EB9FE7A95C5D1BBD66658A0296B264BA381B4315E71DB39AB1BBD423B8F0FDBC2CED70F34993128F5458E073F23031D734BF84D744F0DC315AF86A0B7B59CCE66918890000BF3C9C56F1A442B1C4A00E82B53F08D96DB1B68C2951128400FB7FF009CD6F36A9B540C561E4BDBA5C58FF18701F4F4C50A56E08CD4B247CD2F7240535545DF15D78E0F7AD6B567CB800F7ABBBD936839E2B5CBC2646DFEC6AEE39DED5E7DB99F8C41FF001D98FC2FF4AA0615B2F8C971AC337BA0FE95AEB0AE849D3939FD008A8E28AC2A247353A43A8438031457C81C7348DAB9279A7242767A6BB8E6645A77E719AAE958824E6AC1A22D934BCF6E4AF4A110BC19908ABCB08BF2E6A96DD0A374C0AD874D07033DEA2FC4C5CF87805C81FEA3FD6B6789FD3F15AAE8A719F927FAD6CD01CA8AF25CFF00F65AF4FC1FD22536190FBD55DE03B6ADA41C7155B7401269173D632AAC67A0A4EFCFA18F5150DE50E0F143B897727C510B97C3BE1DB91FE1310F62C3FF00EC6B937E214D21F17CF25B34466660B80A186D03BE78CF1FB56F3A65D18E39101FCB230C7EF5CCF5FBC8AE7C4D753C9132812ED9103F12B0FCB93D867AD77F972FFF009F170E4FF968974B6C21F3E48C0B7B5F593C012BF521463DF839A46E669AE8CF791C659B1BDCBB676A91D3F7A565825B89EEE6BF2013279792DB5773743D3A0C738A868F76FA5DC2DC4E9E6C2414F4F278EBC9FBD605EC60A4A8662C21960F4853BB8E7F738A05F5BADBDB71010091861C71C75EE2AD6EE291A70D685ED54465489986338C88F70E09C13CF7FD2A7A54F13DB07B9B8B78E60488804DC79FCCC79E4D08A9E9AAB7C22DF2A799132ABBCAC7D4AA30001FA77A3EA90DB49A9A0B4B5779B632AF99811C9B57F36DEDDF8AD799ED960624CCD3F99CAE3D3B7DF23BF6E94F8BD171035E4BBE29957644635C29F70C4F3D29A5468D5B4B6E24BB875298AB2A825C6771F71F6CFB62833C715B4B347711F9916164090B90064E42804633EF55091A1B4692547657076C9ECDD79CFF002AB2F22EBFC205CDCA5CE388E26DCCA226EB8E47391F6E08A8EEA746ACF53B432A5ABDA85D38E58BCAE4B83D707DF9ED5B658CAD25E4367A24F7173288D7745EA51339E840C90D8F7E38AD4B54B1BC9FC3DA7EA07488ED6CB6889A6427133F3EA3EC485E83DB3C513C2DACEA1E1FBD5D674E0B2F96BE579732165391D08E9EFDE9F1BFE975FE2DE5FAD8E29A1D46DE4B2BC864CB47BB9604F3C1FD7AFBD626D624D1B58B9DF0A4D7D1E0ABEC5D8C38C03FE5E99CD252EB8DAD7886FF0055D46037A5C337965C2609CE08207627A63154B6325C5C3BCD189196153942B95C10475A9B7FC1A5BEA1E2017970D7D7A9E5C8C4068E2C050A060A81FCB8A5DB58D4615324CB0DCC2EC5510C78273FE9FDBF6AAFD3963D93DB318DA66C6C63C0FE9CD4E52D732C56B73788E234FE110982BFF69C546E8D4164865B3BE88DADBB477120CABBAEF186E3183C7538E6AFACEE6FEDF515D26FD0C8CE7D526762AE7AF1D31540D737BFE1EF65F54E90CCE8EF08C0DC57F2F3D7A91FBD1BE94C97725C7D4B398C71E7B60C808C1E9D0F7A250D8BC6AD3CD7D6D6F0CB033C10A40B7103866E464E48E5B8C727918C553C3F5D71BF4D9DE39234500BCAD8DA7390C49EC31ED50B2BAFF0DD46DAF12EA5D3DA3606378406208E338E87FD8D6755B896FB55BCD427956E6790F3311819F7C0C0E7918F9A068C5DCC74898C727972CEC9FC39108655247C76AA98E7B7945B45756E05DAB13BE052370273EA1EFEC47B53062B49BCB4F542DE6677EFC839ED80B9EBF07AD2D34731BD5BB5DAADB0B85C1F514E082074E31F147C3374B5D067BDD064D52DEFE368AD8348F0799FC4403856E7AF3D483C568AF1AC50B5D4533C7732484222365B6F739AB88F55BEBF905BDA888F0C665DA532B824AE3AFEA3DAAB2CCFD4EAF34A96919DBEA58438503078C1E99F8EF4A16F6F786EC46B6D6E765C010DD4BB7D528183923B0C8EA6AEA5586559ADB88EE859005598B473007F39F7231FA551EA16A2D662F6F298D988654C804FBF4C0C67F4AACB99E59EE4C334AED047950586D7C37B77C6719F8CD1B0461F30C5B18C8210D9519C0DD8F715B47842DCEA93416F1C3198770762C3D71953D41F6639E0F4A868DA558EA5035BC77D243295E5147A59875E0F7ADFBF0F741FF0CB3DC7995CEE638E7E2AAE4CA6316F0E1EF9378D2AD8411220C0C7C55C45DF9A56DD7001EE69B5CF6AC36EDD5C66A26CE156949981EB4491B1919E693793A8268905566A7CA9154B22E226AB6BD3BDB03AD56DEE228496E06326AFC66FA5195FCB9BF8D30753500722219FE75AD30AB9D6AE3EAAFA5946482703EC2AA9C574719D38F9E5BCA9661512B472B5958C9A9F51ECE870C581C0A3291D3BD611B31E33CD4403BB3D2BB4E7E42B0E38A1B464D10371567A6E9375A82B18633B421704FF009B1C607CE6A2D93EA24DAA22B7F50C8AB6810220C76AD80783DB75AA798C3D25A793A8078C2A8FE7FF009C556DEE99369E88D300824276231F5E3DC8ED55FEE6394D4A6F5B2F65B47619201C1DC7FAD6CF6C73802B54D20E6794740246FEB5B5DA9E01AF2DCDFDEBD3F07FD70C31E30693B95CD3BDBA5067008AAD6A8AEC3038C557CF26DC83DAAE2F57D2702A82F91864D4C459B56C4E1DEE067866FB76AE61AAEC4D49E3B685F744C4EF7FCC4E7B8F6ADF61B8F26E2EF2ACD820E07EBD6B40D4AE5EE6F6F25809DB2E4939CE173CF3FA57679357830DB8DAD72D4ECA69E3BD5792281D6152CCAEBC312A78F9341BA769C2C6130AA71B7703B18F5E9D067B50199D162631B0E46DDDCE6BCFB8CD897ACD9CF5E3E7EF598E900E5C6E918460EEC839E9FF00E286C91C6CADFF00355F818E0E0FC54FE9D43E19BF86A795E73F7F6C7FF358545CC9BC649E464E7F51ED528A96E78F1088C7978C6F0BC919A6EEDA416D0DA4533CF11CFA76F238CF5F6CE463E29246096E5005E78C8E7FAF7A94733FD324402E13A1518C7CFDEA65D0596B36F736561A7452B23DB4F08995C64F19E99E9C7B53FAEBDC5CE976474F4B96D0224452ECA4A79FB007258F738E2B5D672555599B00607C0F8FD69A5B9BAFF0DFA112CEF6ACFBFCBDC4AB301D71D33CD46C18BE12B69F6EB6B2C8DA593EA4270A1F183E9CE4F5EBF3429EE67B8B68D6D23D961063CCDB9C13EEDF34BC32B2DB4901924F249CB03C8CFF006A3433BC7A6DCD8C4FFC1B8652F95C9623A60D37B06666DF610FD02B206C89369C06E73CD3169AACB0E9B7161A742B89B1E73E707008FD8F4A66C2EF4BB6F0CCF646D036A4F32B0B96E8AB8F50C7BD57D9AA4264F2B0C5C609EE075A9975F03C248A38E59ECF2D704E318C841F0284BB4442E9706EF86F2B238F9C75A2594096AEEFB897230054A2B78D6EDA7331C7242E39FD69760294CB731ADDC8AABB0E4851826BD286D4019576C663FCCA5BFA7CD19ED5DEF3EA04A823039049E98E98A85DC123DD46F6A4471039001C63FDE808965D4F62B965F24023FCD91D31F7E2A4ADF5B6A6D625F2D930C097FCC3A533AA19AEE08D6DB62887390B852C3E7142B912BD8C71C18490105F1C16FD68D81ED24B78E1B8B22A7EA150949436DE475152D2ECEE4D8DD4C648834084491348158A1F607AF6E9EF42B39A7B14925B455FACC05693A900F7FBD4ACEC679ED26D5E688CA913664DABD49EE454EF608DBB5E4C7CF81A7DEA9E5E620771078C71D739C539A5DCFD25A3CB6FE5A94203E47A89CE7A9F8AF693AA340D3A6F9238252BFF002C850BDCF3D41E06318A1E9B2DBFF12397CA0A4E5018F7649CF39E94A06D52ED2F27FAB85C88C155F5903DB8C6791C1E94A99DAEAE24F48F294893D232555724E0FB63341B5103C72C330F58002367BFF714216E0C61067CC3CAFF00F151449B6E3E0EB01A9EA91CA87708598BBE30589E71FCF35DB74CB5F2A3518E315A5FE1EE8DFE1DA54418012B7AA4FF00DC7B5741B6421179E9583972F6AE970F1FACD9A44038E8288E303835841CE45626E06691A4ACCD93CD2333601CD313F0D91C52174DE93CD3633B2E45646C92735ABF8D751FA6B128A7D727A07F7ABD964DB924F4AE5FE2CD40DE6A2C41CC71E547F7AD9C186EED8BC9CFD71B156C722865726A51E48A901CFBD6ED3941AC79A6EDED4B7414CE976325E5D450C2A5A49080A00EA6BE9AFC3AFC30D3F4CD3E39F54852E2F245C90E3217E31536E38CDE44B72CAEB1704867C0A677EE5C8AA976DA320D1ADE72700F15D551A3C0926BA2ADC496ED61A7DAB3C76BF4BE699625DCF2607E55F9EFFAD54F87740B59B475BE96237733E76C224D8060E3AFBD5B59C7E65DC5631DBCD64234695E3908957A80BB49C8EE7A63A551C99CCAEBFC3618D8F5F4F79A7EB5690D85CCF3F9A0178656DF8E7F6FED4A78DA789F54856320C891ED7C76E781FF009EF56362634B5D42EE57616B1C8C8BE4AAA1703009C8009C9E3AD29A8D8E9F73E1E37B6B6B2452B1C463712CC7763DCE73498D92CB7FF86D6E357D20E6797FF79ADAED4FA454B44D2EDA19923B8D24A1760A64927F531C727683C77A76F2D61835636D6C3D1E9CA96C609ED935E7FC8EF932AF41C197F190D5BE9D733C45A38982EDDC0907D5F0280FA65D3163E51E0E0EE2179FD7EF57D1A882D607F257747B820370A78F7E9C9CD2B7F0DBC88E2469945B00180C1CB31C919EE7FDAA9D1FDAB5F7D3A492E0DB80864C640DE083C6719F7AAB3A35D5EB88E281D031237BA90A31EF578B6C268EE1628A52FBD443329C156CF00D6754B445F585B478A3020591E6901E0743838CF53449DA6DFC5723D4F4FBCB2B9BB17514B6A268F08EEB8E6B503A06A0F0E9A16480C9783F81106F5E37103231D0B723ED5D0BC68A64F1C68DA5C31A7930C7BDD771232DEA6619C9270063E454355D6AE239E09A746859E5334517D2990C4806D8D0EDC1C9CEFCE7B0AE84CFDB8E4FF1CECF19EF6C6A11F83AFE5B911AEA7A64B73F91218EE95893DD7DC11EF54A348BA97526B1B1DD78DEAFE2440796C57A9563D57E78ADEAFA68B4CBEBA4274F82765D925CC3A5BA884B0033BC37CF5AA8B2BCB49BC2FA846D7725AC91E219658ED9DD5222DD88E9B8E3F414A5575BF85F505799E7B3536E10C6BE6DD4719DE4707F37BF355FA95B2E93776A87C99BCB8B7BB23AC837E7183B49E39AD9EFB43D3ED74AB3D345F385B6437D74E2CDD892E41566C7E5C0E307DEB47D523481DD61B9FA98246004BB0A76E98340662690AA41105DD29DC4920E7F4FE75992E774338555F36620171C28008CE07F2A04D2795705A15D8889B064F7C73FB9AC06545873E9080312DCE4FC54815AEDD89C9DE638FCB504038A35ADCDE59CD6E0E1C44ACE8A791EA1F14AC6D1BC42253E5F992641CE07C7F5A2BB9826B978407C0D9D3231D3FF000D0105698DA37F008591F1B8F03206719A619AF0DE5B26F5DD081B7070477EF8A033CF70B6D0AB05CF4E40E49F7A9B16175379CE81E304E58FE6ED81EE698270BDD1B4BC7680BB3BAEE9083C1249EB8C55847742DB5A88DE01E9876FE50304A63FBF5AA84698E9EB1A97C492703046401FCBAD32AF35B5EDD8F28B62364C9E76E401DBEF4079249E4D389F2E3546907F11979E9D33539DEDA3B8B308A240234322AB1193DFF5A5AE2258ECA1DD39937E58A0EC7A51E1676D4C3D9C61BCA4CE719C605007B5293C3A84E72A9101B10B1CE4B6063DFBD0E69D0C168A9E62C8F9DE4FDF8A01990E9ACDB834D248BC7C0CD788371716F14C5531850C3A0FE540353936EF7EAD1BB846DB1B6FE9CF5F9EF4398EC7B504BA79A15890DBBAE727E295924658EEA3560E1DFDBB026A76EA23BF804CEA63D809CE703D26A283104BE5C57370A65023608182E14FDCFDA9D8D2E9A2FA650DE5DC44B298C746503AE7B74AA5F2A51A6070E3CA790E1777538EB8A7A399A09E56B766602DC2927E40CE3DBA9A89748B0F6957EBA7DA4F0C504444CC8CB23AEE2854F60783FAD2B610DA08A54B890007D48CCBC13D769C1C807E29CF3267F0A5BEF854C51CE403C13EF81F14ADC4EF3C37304102AA3B79E176FE53F1ED5368908411A79722C83121076119E09ED575E08D34DF6B312BAE5217F308CE71EC33F7A4967221691640E674DB2060383FDABA1FE19E9FF4FA61B868F12CEC4924638ED55F265AC56F161ED93A0E9D1054007B55D5BF41ED55B66BC0FDEADE08FD231DAB13A53AE841C50A6A31E33EF4B48FB183601DA41C1E868FA290BA7001CD555DC8029C9AEBB6B359DC47A2996C2D145F07CE507040E3151B2D2F4FB5D29D756B3814AC734B20651B8203D7F955D8E3AACF97374F9EBC4BA90B4B17DA7F88FE9515CEA61B9892793D6BEB2D4FC33A740B149A5E9D67A86A50DBC51A24A9B8794CD86936FF0098E2A56DE08D16C5EFA04D1AD1BEAA72EEED17991A831E769EF1807FAD6DC339874C1CD7F72F6F93635E38A24687777AFA12EEDACF5AF0C6856B67A169915CEAF772593CB047FF002913259D4FD94F356FA2F86ACA6BF9B5AD4348D36DEDA2B69A1648B6CD1AEC6CAB1C719C751D78AB3F7547A342FC04D05750F11B5DCC998ED5770FFDC6BE9A18451EDD2B5EF0B699A6DB8FAED26DE18A2BB8637DD12ED5718E0EDEDC5587882E859E9B2485B18207EE2A9E4CFF00732911C78FED4B93E2C127A70298B7273CD2109C9029C899548CD77D9B26FB69AFE990B24D16992C532E388E72AA71EF8C03FCAADF4ED61E6B4D5354982A305586151D8F3FCF939FD2B9CC3283C0EB4FC5732FD3F93E61F2B7EFDBFF0076319FE555DE294B33AD96E75846D061D3A284A05C1772D9DDDCF1F7E69A5F122AC5610C36D84B62090CDF9F0B8F6F926B56128200CD3300CF34DFB78A3DAB624F11DB8D6E69E0D3C0BC28079AF2960063B0AB1D32F80BF6B8BA3977C9DFB73B5BB102B467263D5D4FFAD056C56EF80B9AF35E5E3E9C963D078B7DB8E3735D5E30640819D54288838FF30CFA8FEA7349CB741ED2389589767324A4F73D07EDFD6AAE12180CFED46DB8359B6D331917705EDB45244AA5C430A96181CBC84633FA76FB5561D4EDED19E35864FA76E4B6E05C37FA8646334261819CF6AABBFE84E3AD4ECB718E6FE2DD56CAC7C4B25E379F737C8435AF9B9DA09E8EE73938FF0048FE749EA5E2A860B892F2CDEE67BD990209398E2126D00B88F272DF278F8AA8F1B91278964566C7A554313F7AA3062DC6474C24636C687BB7CF39F9CFBD6BE3F8C3C9FD9B26BBAB5BDFAC5726EAFD5D8A8BCB6593F84718C94F627DB151FF0015D1EEBE9ACEEC5D5A6970B0996CED5431981E773B123D5F3FCB15A9108D1FFCCCB3B60AE393F35337023B89B6A074552819874ED4E46C16FE28687C477DABCC85AD6F3744F6C4E37444636FCE140FE5544AC56E54C01BCB4CBAAB1DDB475C66B02DBFE1ED18112B4AC7D07231C81CE6B33E77DD194A4413D0110F53D284030FA9015909699F0571DB39FEF5395BCC9AEA59DC138DA30B8C9CF1C74153B44795ADD2265540AD20DC7A7DFF00950594476677392EF2636E0E081F3D3BD481E389647894948B6465B3C8CF7C50966962B5DBB1423C99C91DC7CF5EF4621449765A5562B1809B415DC78E98E9FAD6602F70D696EFB4C1BB3B4B050074EA471407A3492E6F15DE555D83D249038033FDAA113C696F71E68249C01924679F6A22AC7E75CB249E5054600125B39E319C52AB3130795B0619B224C5303663468AD629A57D8C4B81C80B93DBEF51D9225A5D112016F2384273F9B9CD4D6D57EBC4734B8F262C838DF8C2E7A0ED4B08192DA179594452392303AE31D2940C5638E6B70A777A47BF7FBD172D25CDD3449E950DD09E39C67DCD655228F5611DB8F4A9CE5E3DD80067A0A8C5E64F6D75701963DF80C15060E4F61DA9807235BFD2C0122CCECEDB8F4E3B532C56E6EE24B975000C0C8C6303E29432B42B68A8AA581CEE56DC7AFB7634469A2B8BCB86BB63D1994EDC1FD71C500031C91DA965DDE5B311BB3E9228BB045744C44CB1843B8639E9F35858C25827F13285C93EA078C0ED5331C7E7DC4F6B392A884631EE3151600C1B7FA08B68904A589209C8F8C51DE4992E6EC42A6252813690338E31FD2826576B7B7528495C939E01C9F6A94AD25C5E5ECA576F059801F9466A01A3133686034E465CB08F70038233DFE686AF706683048575F2C9CE38E951B5B2B69ADE2F3AEB606591B8049047418F9A54C84425573856F6FEF40AB1B11E9FA458CCB319036F0DC28E8463E4F35DCBC3D682DECA088000281D05726F0758AC9E24899242C36091B1EC474E7E6BB658478502B2F2E5AE9B3C6C3F2B4B48970315628A1452B6A981D29B3FBD52D68C8DDC66ABAE9FAE3A535330C1C66ABEE64A988A5269E42832EDB57A7ABA7DAB4FF166AF2C304AED71293B76E7CC3923DBAF4AD86F6E02AB633F7AE57E32D484F7A2DD1BD2872D5AB830DDDB27919FAE3A5625EDD97DED7571BF18C89581C7B75E95237B75961F533FAB83FC56E7EFCF34A466B24FAABA3A729636D733A04093CC027E502460173D71CF1FA559D9C93AC61229A554279557201CFB8AA484F02B64F0E41F577D6F07779157F7A7C71539DD3EAEFC34B3365E0DD3626249F2C13939AAAFC63D4C69FE19CE705E45C7F3ADCF48805BE9D6F101809181FB571DFF00F5177FE5DBD95A838CB6E358F0FE5CAB73EB8A47CED1CDCE41A379B915EAF57671554DDBCC477AB2867E2BD5EAB22AC84591CB8C74AB3B47381B8F15EAF50543516DB736B2671D54D5FDAB6E8D4FB57ABD5E6FF50FFBABBFFA7FFD516F6AC08E29846F575AF57AB0B709BB8AABD44800F406BD5EA988AE21E37703C4539C02C630003DBAF22AABEB04F245F54C1A0B65C90075F7FE67FA57ABD5B78FE39D9FF6A51A4B7692378E3604132119FD4512262BA6CB296DA1E40BB71D7BE6BD5EAB15A7733486EE08550948231B9739C7049E9FCE958C8FA493D4D9924C6CC641FEFDEBD5EA01C9736F3CAB78815A38B60455C1071C13CFCD25163FE1517D659B2707F2F3EDEE6BD5EA02537966399D321DA4E013D07FBD361E6B7BD86367F5471819618C1C671CFC9AF57A88108258D6CAE8CB932B10A011EE739CFE95E48525860E5111B037027839F9AF57A9A80B6C625BBDCC4E010186081F7FFE28DE44012CC19490796DBCE32DD87F2AF57A940ABB52F2E7C84DCA81F9D98C76E99E2968C8FA091B7AEE6906003CF00F6F6E6BD5EA9A06699E7BBB78E08B0768E8307819EF418C844B83708598AF5F6C9AF57AA204DA10A96E62DB2263712BD4E4F208F8E95391EDE46BCD88C9263D038C673DEBD5EA9A1991EE58D946222080029638079EB9A14CB3C92DF4B280A4677E08C0E7F7AF57AA00B0C96D12D833124F258320F7ED52F37CD6B8B7B54DCA4707193C1AF57A84B7CFC29B0CACB76E0EF2767DB15D76C62C81F7AF57AB172FD74787FA2D9000B5824804F4AF57A916949DF6F4EB55B7326339AF57A9B0FA5AD4FC457CB6F6B2C878C026B914F2B5C5CC92BF5639AF57AB7F139FE57D1A338158FF00357ABD5AA7C73E9B83A8AE89F84FA7B5F78B6C5719447DE7F4AF57A9FF000A793E3EB741B5001ED5F327FF00A82D44BF8A2388E76C498AF57AB2F8FF00D9A39FE62FFFD9);
INSERT INTO `bravo_user` VALUES ('2', 'lj', '21232f297a57a5a743894a0e4a801fc3', 'cathy', 'cathy', '880', '304', '1434343', '2008-10-22', null, '882', '884', '887', '891', 's', '510281', '121212178', '23425', 'wujx15cn@gmail.com', '', 'ewwewwedfwefwe', '1', '2008-11-05', null, null);
INSERT INTO `bravo_user` VALUES ('31', 'hhl', 'hhl', 'sasa', 'sasa', '880', '303', 'wewewe', '2008-11-07', null, '885', '884', '888', '891', 's', '510282', '121212179', '23426', 'wujx16cn@gmail.com', '3233', 'sdcdscsd', '1', '2009-02-27', null, null);
INSERT INTO `bravo_user` VALUES ('32', 'hw', 'hw', 'kukuxia', 'kukuxia', '879', '302', '007', '2008-08-01', null, '881', '884', '888', '890', 'SZ SHEKOU', '510283', '121212180', '23427', 'wujx17cn@gmail.com', '88777925', 'sadsad', '1', '2009-02-27', null, null);
INSERT INTO `bravo_user` VALUES ('34', 'bravo', 'bravo', 'kukuxia', 'kukuxia', '879', '302', '007', '2008-08-01', null, '881', '884', '888', '889', 'SZ SHEKOU', '510286', '121212183', '23430', 'wujx20cn@gmail.com', '88227925', '3223', '1', '2008-11-05', null, null);
INSERT INTO `bravo_user` VALUES ('35', 'asdsad', 'sadsa', 'sadsad', 'sadsad', '880', '302', 'asdsad', '2008-11-26', null, '881', '883', '887', '890', 'SZ SHEKOU', '510287', '121212184', '23431', 'wujx21cn@gmail.com', '43543', 'dsfdsf', '1', '2008-11-05', null, null);
INSERT INTO `bravo_user` VALUES ('36', 'bravo', 'bravo', 'kukuxia', 'kukuxia', '880', '302', '007', '2008-08-01', null, '881', '884', '887', '891', 'SZ SHEKOU', '510288', '121212185', '23432', 'wujx22cn@gmail.com', '88877925', 'everyday is a new begining', '1', '2009-02-27', null, null);
INSERT INTO `bravo_user` VALUES ('37', 'sadsda', 'asdsa', '3232', '3232', '879', '302', '323223', '2008-11-20', null, '881', '884', '887', '890', 'SZ SHEKOU', '510289', '121212186', '23433', 'wujx23cn@gmail.com', '345435', '43443', '1', '2008-11-05', null, null);
INSERT INTO `bravo_user` VALUES ('38', 'dsfdsf', 'dfsdf', 'dsfds', 'dsfds', '879', '303', 'dsfdsf', '2008-11-14', null, '885', '884', '888', '894', 'SZ SHEKOU', '510290', '121212187', '23434', 'wujx24cn@gmail.com', '45645654', '45654645', '1', '2008-11-05', null, null);
INSERT INTO `bravo_user` VALUES ('39', 'wyh', 'wyh', 'kukuxia', 'kukuxia', '879', '302', '008', '2008-08-01', null, '881', '884', '887', '891', 'SZ SHEKOU', '510291', '121212188', '23435', 'wujx25cn@gmail.com', '87787925', 'everyday is everyday,nice again', '1', '2008-12-17', null, null);
INSERT INTO `bravo_user` VALUES ('40', 'lxy', 'lxy', 'kukuxia', 'kukuxia', '879', '302', '007', '2008-08-01', null, '881', '884', '887', '891', 'SZ SHEKOU', '510292', '121212189', '23436', 'wujx26cn@gmail.com', '882227925', 'everyday', '1', '2008-12-17', null, null);
INSERT INTO `bravo_user` VALUES ('41', 'ljlj3', 'ljlj', 'kukuxia', 'kukuxia', '879', '878', '007', '2008-08-01', null, '881', '884', '6398', '889', 'SZ SHEKOU', '510293', '121212190', '23437', 'wujx27cn@gmail.com', '4324324324', 'fsfewfdsfdsffsfs', '1', '2009-01-05', null, null);
INSERT INTO `bravo_user` VALUES ('42', 'gt', 'gt', 'test', 'test', '879', '302', '343', '2008-08-01', null, '882', '884', '888', '890', 'SZ SHEKOU', '510294', '121212191', '23438', 'wujx28cn@gmail.com', '324324324', 'rewqrewrewrew', '1', '2008-12-18', null, null);
INSERT INTO `bravo_user` VALUES ('43', 'test1105', 'test1105', 'test1105', 'test1105', '880', '304', '123456', '2008-08-01', null, '882', '884', '887', '890', 'SZ SHEKOU', '510295', '121212192', '23439', 'wujx29cn@gmail.com', '88344565', 'every day is a new begining.', '1', '2008-11-06', null, null);
INSERT INTO `bravo_user` VALUES ('45', 'erew3a', 'rewrew', 'rewrews', 'rewrews', '879', '302', 'rewrew', '2008-11-06', null, '881', '884', '887', '890', 'SZ SHEKOU', '510296', '121212193', '23440', 'wujx30cn@gmail.com', '432432432', 's', '1', '2009-03-04', null, null);
INSERT INTO `bravo_user` VALUES ('46', 'yeon', 'yeon', 'yeon', 'yeon', '879', '303', '333', '2008-08-01', null, '881', '884', '887', '890', 'SZ SHEKOU', '510284', '121212181', '23428', 'wujx18cn@gmail.com', '38267935', 'j3j3', '5', '2008-12-11', null, null);
INSERT INTO `bravo_user` VALUES ('47', 'liangg', 'liangg', 'dddd', 'dddd', '879', '30', 'ddddd', '2008-11-08', null, '882', '884', '886', '890', 'SZ SHEKOU', '510285', '121212182', '23429', 'wujx19cn@gmail.com', 'dd', 'dfdsf', '1', '2008-11-10', null, null);
INSERT INTO `bravo_user` VALUES ('1583', 'sadasd', 'asdas', 'asdsa', 'asdsa', '879', '2', 'asdsad', '2009-04-15', null, null, '884', '887', '889', '23423432', '434334', '121212176', '23423', 'wujx13cn@gmail.com', '322323', 'sadsad', '1', null, null, null);

-- ----------------------------
-- Table structure for `bravo_user_cookie`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_user_cookie`;
CREATE TABLE `bravo_user_cookie` (
  `id` decimal(19,0) NOT NULL COMMENT 'id',
  `userid` decimal(19,0) NOT NULL COMMENT 'userid',
  `name` varchar(512) default NULL COMMENT 'name',
  `value` varchar(2048) default NULL COMMENT 'value',
  `domain` varchar(512) default NULL COMMENT 'domain',
  `maxAge` int(11) default NULL COMMENT 'maxAge',
  `path` varchar(512) default NULL COMMENT 'path',
  `secure` varchar(10) default NULL COMMENT 'secure',
  `version` int(11) default NULL COMMENT 'version',
  PRIMARY KEY  (`id`),
  KEY `FK_USER_REF_COOKIE` (`userid`),
  CONSTRAINT `FK_USER_REF_COOKIE` FOREIGN KEY (`userid`) REFERENCES `bravo_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_user_cookie
-- ----------------------------
INSERT INTO `bravo_user_cookie` VALUES ('7', '1', 'css', '', null, null, null, null, null);
INSERT INTO `bravo_user_cookie` VALUES ('8', '1', 'ys-Portlet19086511', 'null', null, null, null, null, null);
INSERT INTO `bravo_user_cookie` VALUES ('9', '1', 'ys-newsDetailWin', 'o%3Awidth%3Dn%253A620%5Eheight%3Dn%253A504%5Ex%3Dn%253A227%5Ey%3Dn%253A66', null, null, null, null, null);
INSERT INTO `bravo_user_cookie` VALUES ('10', '1', 'ys-Teacher_grid', 'o%3Acolumns%3Da%253Ao%25253Aid%25253Ds%2525253Anumberer%25255Ewidth%25253Dn%2525253A23%255Eo%25253Aid%25253Ds%2525253Achecker%25255Ewidth%25253Dn%2525253A20%255Eo%25253Aid%25253Dn%2525253A2%25255Ewidth%25253Dn%2525253A0%25255Ehidden%25253Db%2525253A1%255Eo%25253Aid%25253Dn%2525253A3%25255Ewidth%25253Dn%2525253A175%255Eo%25253Aid%25253Dn%2525253A4%25255Ewidth%25253Dn%2525253A175%255Eo%25253Aid%25253Dn%2525253A5%25255Ewidth%25253Dn%2525253A175%255Eo%25253Aid%25253Dn%2525253A6%25255Ewidth%25253Dn%2525253A175%255Eo%25253Aid%25253Dn%2525253A7%25255Ewidth%25253Dn%2525253A175%5Eheight%3Dn%253A364', null, null, null, null, null);
INSERT INTO `bravo_user_cookie` VALUES ('11', '1', 'isCookieLoaded', 'bgbb53fh7nj0', null, null, null, null, null);
INSERT INTO `bravo_user_cookie` VALUES ('12', '1', 'ys-processDefinition_grid', 'o%3Acolumns%3Da%253Ao%25253Aid%25253Ds%2525253Anumberer%25255Ewidth%25253Dn%2525253A23%255Eo%25253Aid%25253Ds%2525253Achecker%25255Ewidth%25253Dn%2525253A20%255Eo%25253Aid%25253Dn%2525253A2%25255Ewidth%25253Dn%2525253A175%25255Ehidden%25253Db%2525253A1%255Eo%25253Aid%25253Dn%2525253A3%25255Ewidth%25253Dn%2525253A189%255Eo%25253Aid%25253Dn%2525253A4%25255Ewidth%25253Dn%2525253A228%255Eo%25253Aid%25253Dn%2525253A5%25255Ewidth%25253Dn%2525253A175', null, null, null, null, null);
INSERT INTO `bravo_user_cookie` VALUES ('13', '1', 'ys-Permission_Grid', 'o%3Acolumns%3Da%253Ao%25253Aid%25253Ds%2525253Anumberer%25255Ewidth%25253Dn%2525253A23%255Eo%25253Aid%25253Ds%2525253Achecker%25255Ewidth%25253Dn%2525253A20%255Eo%25253Aid%25253Dn%2525253A2%25255Ewidth%25253Dn%2525253A176%25255Ehidden%25253Db%2525253A1%255Eo%25253Aid%25253Dn%2525253A3%25255Ewidth%25253Dn%2525253A175%255Eo%25253Aid%25253Dn%2525253A4%25255Ewidth%25253Dn%2525253A354%255Eo%25253Aid%25253Dn%2525253A5%25255Ewidth%25253Dn%2525253A175', null, null, null, null, null);
INSERT INTO `bravo_user_cookie` VALUES ('14', '1', 'ys-pWest', 'o%3Awidth%3Dn%253A284', null, null, null, null, null);
INSERT INTO `bravo_user_cookie` VALUES ('135', '39', 'isCookieLoaded', '11mznhqy1oqcv', null, null, null, null, null);
INSERT INTO `bravo_user_cookie` VALUES ('136', '39', 'css', 'xtheme-peppermint.css', null, null, null, null, null);

-- ----------------------------
-- Table structure for `bravo_user_desktop`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_user_desktop`;
CREATE TABLE `bravo_user_desktop` (
  `id` decimal(19,0) NOT NULL,
  `user_id` decimal(19,0) default NULL,
  `shortcuts` varchar(1024) default NULL,
  `auto_run_apps` varchar(256) default NULL,
  `quick_start_apps` varchar(256) default NULL,
  `taskbar_transparency` int(11) default NULL,
  `theme` decimal(19,0) default NULL,
  `wallpaper` decimal(19,0) default NULL,
  `wallpaper_layout` varchar(32) default NULL,
  `background_color` varchar(64) default NULL,
  `font_color` varchar(64) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_DESK_BRAVO_USER` (`user_id`),
  KEY `FK_DESK_REF_THEME` (`theme`),
  KEY `FK_DESK_REF_WALLPAPER` (`wallpaper`),
  CONSTRAINT `FK_DESK_BRAVO_USER` FOREIGN KEY (`user_id`) REFERENCES `bravo_user` (`id`),
  CONSTRAINT `FK_DESK_REF_THEME` FOREIGN KEY (`theme`) REFERENCES `bravo_user_desktop_resource` (`id`),
  CONSTRAINT `FK_DESK_REF_WALLPAPER` FOREIGN KEY (`wallpaper`) REFERENCES `bravo_user_desktop_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_user_desktop
-- ----------------------------
INSERT INTO `bravo_user_desktop` VALUES ('2', '1', '[\"moduleId1000112\",\"moduleId1000110\",\"moduleId1000113\",\"moduleId1000111\",\"moduleId1000122\",\"moduleId1000119\",\"moduleId2000102\",\"moduleId2000103\",\"moduleId2000104\",\"moduleId2000101\",\"qo-preferences\",\"moduleId1000126\",\"moduleId1000402\",\"moduleId1000401\"]', '[]', '[\"demo-acc\",\"moduleId2000103\",\"moduleId2000104\",\"moduleId2000101\"]', '85', '3', '6', 'tile', '390A0A', 'FFFFFF');

-- ----------------------------
-- Table structure for `bravo_user_desktop_resource`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_user_desktop_resource`;
CREATE TABLE `bravo_user_desktop_resource` (
  `id` decimal(19,0) NOT NULL,
  `name` varchar(32) default NULL,
  `url` varchar(255) default NULL,
  `path_to_thumbnail` varchar(255) default NULL,
  `path_to_file` varchar(255) default NULL,
  `type` decimal(19,0) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_DES_RES_REF_ENU` (`type`),
  CONSTRAINT `FK_DES_RES_REF_ENU` FOREIGN KEY (`type`) REFERENCES `bravo_enumeration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_user_desktop_resource
-- ----------------------------
INSERT INTO `bravo_user_desktop_resource` VALUES ('1', 'Blue Psychedelic', '1', '/widgets/desktop/resources/wallpapers/thumbnails/blue-psychedelic.jpg', '/widgets/desktop/resources/wallpapers/blue-psychedelic.jpg', '6626');
INSERT INTO `bravo_user_desktop_resource` VALUES ('2', 'Vista Glass', '2', '/widgets/desktop/resources/themes/xtheme-vistaglass/xtheme-vistaglass.png', '/widgets/desktop/resources/themes/xtheme-vistaglass/css/xtheme-vistaglass.css', '6625');
INSERT INTO `bravo_user_desktop_resource` VALUES ('3', 'Vista Black', '2', '/widgets/desktop/resources/themes/xtheme-vistablack/xtheme-vistablack.png', '/widgets/desktop/resources/themes/xtheme-vistablack/css/xtheme-vistablack.css', '6625');
INSERT INTO `bravo_user_desktop_resource` VALUES ('4', 'Vista Blue', '2', '/widgets/desktop/resources/themes/xtheme-vistablue/xtheme-vistablue.png', '/widgets/desktop/resources/themes/xtheme-vistablue/css/xtheme-vistablue.css', '6625');
INSERT INTO `bravo_user_desktop_resource` VALUES ('5', 'qWikiOffice', '1', '/widgets/desktop/resources/wallpapers/thumbnails/qwikioffice.jpg', '/widgets/desktop/resources/wallpapers/qwikioffice.jpg', '6626');
INSERT INTO `bravo_user_desktop_resource` VALUES ('6', 'Colorado Farm', '1', '/widgets/desktop/resources/wallpapers/thumbnails/colorado-farm.jpg', '/widgets/desktop/resources/wallpapers/colorado-farm.jpg', '6626');
INSERT INTO `bravo_user_desktop_resource` VALUES ('7', 'Curls On Green', '1', '/widgets/desktop/resources/wallpapers/thumbnails/curls-on-green.jpg', '/widgets/desktop/resources/wallpapers/curls-on-green.jpg', '6626');
INSERT INTO `bravo_user_desktop_resource` VALUES ('8', 'Emotion', '1', '/widgets/desktop/resources/wallpapers/thumbnails/emotion.jpg', '/widgets/desktop/resources/wallpapers/emotion.jpg', '6626');
INSERT INTO `bravo_user_desktop_resource` VALUES ('9', 'Eos', '1', '/widgets/desktop/resources/wallpapers/thumbnails/eos.jpg', '/widgets/desktop/resources/wallpapers/eos.jpg', '6626');
INSERT INTO `bravo_user_desktop_resource` VALUES ('10', 'Fields of Peace', '1', '/widgets/desktop/resources/wallpapers/thumbnails/fields-of-peace.jpg', '/widgets/desktop/resources/wallpapers/fields-of-peace.jpg', '6626');
INSERT INTO `bravo_user_desktop_resource` VALUES ('11', 'Fresh Morning', '1', '/widgets/desktop/resources/wallpapers/thumbnails/fresh-morning.jpg', '/widgets/desktop/resources/wallpapers/fresh-morning.jpg', '6626');
INSERT INTO `bravo_user_desktop_resource` VALUES ('12', 'Ladybuggin', '1', '/widgets/desktop/resources/wallpapers/thumbnails/ladybuggin.jpg', '/widgets/desktop/resources/wallpapers/ladybuggin.jpg', '6626');
INSERT INTO `bravo_user_desktop_resource` VALUES ('13', 'Summer', '1', '/widgets/desktop/resources/wallpapers/thumbnails/summer.jpg', '/widgets/desktop/resources/wallpapers/summer.jpg', '6626');
INSERT INTO `bravo_user_desktop_resource` VALUES ('14', 'Blue Swirl', '1', '/widgets/desktop/resources/wallpapers/thumbnails/blue-swirl.jpg', '/widgets/desktop/resources/wallpapers/blue-swirl.jpg', '6626');
INSERT INTO `bravo_user_desktop_resource` VALUES ('15', 'Blue Curtain', '1', '/widgets/desktop/resources/wallpapers/thumbnails/blue-curtain.jpg', '/widgets/desktop/resources/wallpapers/blue-curtain.jpg', '6626');
INSERT INTO `bravo_user_desktop_resource` VALUES ('16', 'Blank', '1', '/widgets/desktop/resources/wallpapers/thumbnails/blank.gif', '/widgets/desktop/resources/wallpapers/blank.gif', '6626');

-- ----------------------------
-- Table structure for `bravo_user_login_log`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_user_login_log`;
CREATE TABLE `bravo_user_login_log` (
  `ID` decimal(19,0) NOT NULL,
  `LOGIN_ID` varchar(128) NOT NULL,
  `LOGIN_IP` varchar(15) default NULL,
  `LOGIN_TIME` date default NULL,
  `LOGOUT_TIME` date default NULL,
  `CHN_NAME` varchar(128) default NULL,
  `DEPARTMENT` varchar(128) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_user_login_log
-- ----------------------------
INSERT INTO `bravo_user_login_log` VALUES ('4', 'admin', '127.0.0.1', '2011-06-26', '2011-06-26', '吴佳兴', '中国xxx');
INSERT INTO `bravo_user_login_log` VALUES ('5', 'admin', '127.0.0.1', '2011-06-28', '2011-06-28', '吴佳兴', '中国xxx');
INSERT INTO `bravo_user_login_log` VALUES ('6', 'admin', '127.0.0.1', '2011-07-15', '2011-07-15', '吴佳兴', '中国xxx');
INSERT INTO `bravo_user_login_log` VALUES ('7', 'admin', '127.0.0.1', '2011-07-15', '2011-07-15', '吴佳兴', '中国xxx');
INSERT INTO `bravo_user_login_log` VALUES ('8', 'admin', '127.0.0.1', '2011-08-03', '2011-08-03', '吴佳兴', '中国xxx');
INSERT INTO `bravo_user_login_log` VALUES ('9', 'admin', '127.0.0.1', '2011-09-06', '2011-09-06', '吴佳兴', '中国xxx');
INSERT INTO `bravo_user_login_log` VALUES ('10', 'admin', '127.0.0.1', '2011-09-14', '2011-09-14', '吴佳兴', '中国xxx');
INSERT INTO `bravo_user_login_log` VALUES ('11', 'admin', '127.0.0.1', '2011-09-14', '2011-09-14', '吴佳兴', '中国xxx');
INSERT INTO `bravo_user_login_log` VALUES ('12', 'admin', '127.0.0.1', '2011-09-14', '2011-09-14', '吴佳兴', '中国xxx');
INSERT INTO `bravo_user_login_log` VALUES ('13', 'admin', '127.0.0.1', '2011-09-14', '2011-09-14', '吴佳兴', '中国xxx');
INSERT INTO `bravo_user_login_log` VALUES ('14', 'admin', '127.0.0.1', '2011-09-14', '2011-09-14', '吴佳兴', '中国xxx');
INSERT INTO `bravo_user_login_log` VALUES ('15', 'admin', '127.0.0.1', '2011-09-14', '2011-09-14', '吴佳兴', '中国xxx');
INSERT INTO `bravo_user_login_log` VALUES ('16', 'admin', '127.0.0.1', '2011-09-14', '2011-09-14', '吴佳兴', '中国xxx');
INSERT INTO `bravo_user_login_log` VALUES ('17', 'admin', '127.0.0.1', '2011-09-14', '2011-09-14', '吴佳兴', '中国xxx');
INSERT INTO `bravo_user_login_log` VALUES ('18', 'admin', '127.0.0.1', '2011-09-14', '2011-09-14', '吴佳兴', '中国xxx');
INSERT INTO `bravo_user_login_log` VALUES ('19', 'admin', '127.0.0.1', '2011-09-14', '2011-09-14', '吴佳兴', '中国xxx');
INSERT INTO `bravo_user_login_log` VALUES ('20', 'admin', '127.0.0.1', '2011-09-14', '2011-09-14', '吴佳兴', '中国xxx');
INSERT INTO `bravo_user_login_log` VALUES ('21', 'admin', '127.0.0.1', '2011-09-14', '2011-09-14', '吴佳兴', '中国xxx');
INSERT INTO `bravo_user_login_log` VALUES ('23', 'admin', '127.0.0.1', '2011-09-19', '2011-09-19', '吴佳兴', '中国xxx');
INSERT INTO `bravo_user_login_log` VALUES ('24', 'admin', '127.0.0.1', '2011-09-28', '2011-09-28', '吴佳兴', '中国xxx');
INSERT INTO `bravo_user_login_log` VALUES ('25', 'admin', '127.0.0.1', '2013-05-17', '2013-05-17', '???', '??xxx');

-- ----------------------------
-- Table structure for `bravo_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_user_role`;
CREATE TABLE `bravo_user_role` (
  `user_id` decimal(19,0) NOT NULL COMMENT 'user_id',
  `role_id` decimal(19,0) NOT NULL COMMENT 'role_id',
  PRIMARY KEY  (`user_id`,`role_id`),
  KEY `FK_USER_ROLE` (`role_id`),
  CONSTRAINT `FK_ROLE_USER` FOREIGN KEY (`user_id`) REFERENCES `bravo_user` (`id`),
  CONSTRAINT `FK_USER_ROLE` FOREIGN KEY (`role_id`) REFERENCES `bravo_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_user_role
-- ----------------------------
INSERT INTO `bravo_user_role` VALUES ('2', '1');
INSERT INTO `bravo_user_role` VALUES ('31', '1');
INSERT INTO `bravo_user_role` VALUES ('32', '1');
INSERT INTO `bravo_user_role` VALUES ('1', '34');
INSERT INTO `bravo_user_role` VALUES ('1', '35');
INSERT INTO `bravo_user_role` VALUES ('2', '35');
INSERT INTO `bravo_user_role` VALUES ('31', '35');
INSERT INTO `bravo_user_role` VALUES ('32', '35');
INSERT INTO `bravo_user_role` VALUES ('34', '35');
INSERT INTO `bravo_user_role` VALUES ('35', '35');
INSERT INTO `bravo_user_role` VALUES ('36', '35');
INSERT INTO `bravo_user_role` VALUES ('37', '35');
INSERT INTO `bravo_user_role` VALUES ('38', '35');
INSERT INTO `bravo_user_role` VALUES ('39', '35');
INSERT INTO `bravo_user_role` VALUES ('40', '35');
INSERT INTO `bravo_user_role` VALUES ('41', '35');
INSERT INTO `bravo_user_role` VALUES ('42', '35');
INSERT INTO `bravo_user_role` VALUES ('43', '35');
INSERT INTO `bravo_user_role` VALUES ('45', '35');
INSERT INTO `bravo_user_role` VALUES ('46', '35');
INSERT INTO `bravo_user_role` VALUES ('47', '35');
INSERT INTO `bravo_user_role` VALUES ('1', '36');
INSERT INTO `bravo_user_role` VALUES ('34', '36');
INSERT INTO `bravo_user_role` VALUES ('35', '36');
INSERT INTO `bravo_user_role` VALUES ('36', '36');
INSERT INTO `bravo_user_role` VALUES ('37', '36');
INSERT INTO `bravo_user_role` VALUES ('38', '36');
INSERT INTO `bravo_user_role` VALUES ('41', '36');
INSERT INTO `bravo_user_role` VALUES ('43', '36');
INSERT INTO `bravo_user_role` VALUES ('45', '36');
INSERT INTO `bravo_user_role` VALUES ('46', '36');
INSERT INTO `bravo_user_role` VALUES ('47', '36');
INSERT INTO `bravo_user_role` VALUES ('1', '37');
INSERT INTO `bravo_user_role` VALUES ('39', '37');
INSERT INTO `bravo_user_role` VALUES ('40', '37');
INSERT INTO `bravo_user_role` VALUES ('42', '37');
INSERT INTO `bravo_user_role` VALUES ('1', '38');

-- ----------------------------
-- Table structure for `bravo_wf_base`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_wf_base`;
CREATE TABLE `bravo_wf_base` (
  `id` decimal(19,0) NOT NULL COMMENT '主键',
  `processInstance` decimal(19,0) default NULL COMMENT '流程实体',
  `process_starter` decimal(19,0) default NULL COMMENT '流程发起人',
  `process_start_dt` date default NULL COMMENT '流程发起时间',
  `process_entity_class` varchar(256) default NULL COMMENT '流程实体类',
  PRIMARY KEY  (`id`),
  KEY `FK_WO_REF_STARTER` (`process_starter`),
  CONSTRAINT `FK_WO_REF_STARTER` FOREIGN KEY (`process_starter`) REFERENCES `bravo_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_wf_base
-- ----------------------------
INSERT INTO `bravo_wf_base` VALUES ('1', null, null, null, null);
INSERT INTO `bravo_wf_base` VALUES ('2', null, null, null, null);
INSERT INTO `bravo_wf_base` VALUES ('3', null, null, null, null);

-- ----------------------------
-- Table structure for `bravo_wf_base_task`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_wf_base_task`;
CREATE TABLE `bravo_wf_base_task` (
  `id` decimal(19,0) NOT NULL COMMENT '主键',
  `workflow_base` decimal(19,0) default NULL COMMENT 'workflow_base',
  `task` decimal(19,0) default NULL COMMENT 'task',
  PRIMARY KEY  (`id`),
  KEY `FK_WF_REF_WO` (`workflow_base`),
  CONSTRAINT `FK_WF_REF_WO` FOREIGN KEY (`workflow_base`) REFERENCES `bravo_wf_base` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_wf_base_task
-- ----------------------------

-- ----------------------------
-- Table structure for `bravo_wf_diagram`
-- ----------------------------
DROP TABLE IF EXISTS `bravo_wf_diagram`;
CREATE TABLE `bravo_wf_diagram` (
  `id` decimal(19,0) NOT NULL COMMENT '主键',
  `process_definition` decimal(19,0) default NULL COMMENT '相关流程定义',
  `content` text COMMENT '内容',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bravo_wf_diagram
-- ----------------------------
INSERT INTO `bravo_wf_diagram` VALUES ('0', '593701', '<mxGraphModel><root><Workflow label=\"MyWorkflow\" description=\"\" href=\"\" id=\"0\"><mxCell/></Workflow>\r\n		<Layer label=\"Default Layer\" id=\"1\">\r\n			<mxCell parent=\"0\"/></Layer>\r\n		<Start label=\"Start\" description=\"\" id=\"2\">\r\n			<mxCell style=\"colorRect;fontColor=red;strokeColor=red\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"130\" y=\"120\" width=\"80\" height=\"40\" as=\"geometry\"/></mxCell></Start>\r\n		<Task_Node label=\"Task_Node1\" description=\"\" task=\"&lt;task name=&quot;Task_Node1&quot;&gt;&lt;variable name=&quot;workflow_task_url&quot;&gt;1&lt;/variable&gt;&lt;assignment actor-id=&quot;1&quot; actor-name=&quot;吴佳兴&quot;/&gt;&lt;/task&gt;\" event=\"&lt;event type=&quot;&quot;/&gt;\" id=\"3\">\r\n			<mxCell style=\"colorRect;fontColor=#3300FF;\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"130\" y=\"220\" width=\"90\" height=\"40\" as=\"geometry\"/></mxCell></Task_Node>\r\n		<Edge label=\"\" description=\"\" condition=\"\" id=\"4\">\r\n			<mxCell edge=\"1\" parent=\"1\" source=\"2\" target=\"3\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge>\r\n		<Task_Node label=\"Task_Node2\" description=\"\" task=\"&lt;task name=&quot;Task_Node2&quot;&gt;&lt;variable name=&quot;workflow_task_url&quot;&gt;1&lt;/variable&gt;&lt;assignment actor-id=&quot;1&quot; actor-name=&quot;吴佳兴&quot;/&gt;&lt;/task&gt;\" event=\"&lt;event type=&quot;&quot;/&gt;\" id=\"5\">\r\n			<mxCell style=\"colorRect;fontColor=#3300FF;\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"130\" y=\"300\" width=\"90\" height=\"40\" as=\"geometry\"/></mxCell></Task_Node>\r\n		<Task_Node label=\"Task_Node3\" description=\"\" task=\"&lt;task name=&quot;Task_Node3&quot;&gt;&lt;variable name=&quot;workflow_task_url&quot;&gt;1&lt;/variable&gt;&lt;assignment actor-id=&quot;1,38&quot; actor-name=&quot;吴佳兴,刘昌盛&quot;/&gt;&lt;/task&gt;\" event=\"&lt;event type=&quot;&quot;/&gt;\" id=\"7\">\r\n			<mxCell style=\"colorRect;fontColor=#3300FF;\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"290\" y=\"320\" width=\"90\" height=\"40\" as=\"geometry\"/></mxCell></Task_Node>\r\n		<End label=\"end\" description=\"\" id=\"8\">\r\n			<mxCell style=\"colorRect;fontColor=red;strokeColor=red\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"160\" y=\"420\" width=\"80\" height=\"40\" as=\"geometry\"/></mxCell></End>\r\n		<Edge label=\"\" description=\"\" condition=\"\" id=\"9\">\r\n			<mxCell style=\"defaultEdge\" edge=\"1\" parent=\"1\" source=\"5\" target=\"7\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge>\r\n		<Edge label=\"\" description=\"\" condition=\"\" id=\"10\">\r\n			<mxCell style=\"defaultEdge\" edge=\"1\" parent=\"1\" source=\"7\" target=\"8\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge>\r\n		<Edge label=\"\" description=\"\" condition=\"\" id=\"11\">\r\n			<mxCell style=\"defaultEdge\" edge=\"1\" parent=\"1\" source=\"3\" target=\"5\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge>\r\n	</root></mxGraphModel>');
INSERT INTO `bravo_wf_diagram` VALUES ('1', '593702', '<mxGraphModel><root><Workflow label=\"请假示例流程\" description=\"../../../common/requestForLeaveList!add.action\" href=\"\" id=\"0\"><mxCell/></Workflow>\r\n		<Layer label=\"Default Layer\" id=\"1\">\r\n			<mxCell parent=\"0\"/></Layer>\r\n		<Start label=\"Start\" description=\"\" id=\"2\">\r\n			<mxCell style=\"colorRect;fontColor=green;strokeColor=#33FF66\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"20\" y=\"20\" width=\"85\" height=\"32\" as=\"geometry\"/></mxCell></Start>\r\n		<Task_Node label=\"组长审批\" description=\"\" task=\"&lt;task name=&quot;组长审批&quot;&gt;&lt;variable name=&quot;workflow_task_url&quot;&gt;${bravoHome}/common/requestForLeaveList!view.action?id=${formValue.id?c}&lt;/variable&gt;&lt;assignment actor-id=&quot;1&quot; actor-name=&quot;吴佳兴&quot;/&gt;&lt;/task&gt;\" event=\"&lt;event type=&quot;&quot;/&gt;\" id=\"3\">\r\n			<mxCell style=\"colorRect;fontColor=#3300FF;\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"20\" y=\"120\" width=\"85\" height=\"32\" as=\"geometry\"/></mxCell></Task_Node>\r\n		<Task_Node label=\"项目经理审批\" description=\"\" task=\"&lt;task name=&quot;项目经理审批&quot;&gt;&lt;variable name=&quot;workflow_task_url&quot;&gt;${bravoHome}/common/requestForLeaveList!view.action?id=${formValue.id?c}&lt;/variable&gt;&lt;assignment actor-id=&quot;57&quot; actor-name=&quot;刘征辉&quot;/&gt;&lt;/task&gt;\" event=\"&lt;event type=&quot;&quot;/&gt;\" id=\"4\">\r\n			<mxCell style=\"colorRect;fontColor=#3300FF;\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"20\" y=\"210\" width=\"85\" height=\"32\" as=\"geometry\"/></mxCell></Task_Node>\r\n		<Task_Node label=\"人事甲审批\" description=\"\" task=\"&lt;task name=&quot;人事甲审批&quot;&gt;&lt;variable name=&quot;workflow_task_url&quot;&gt;${bravoHome}/common/requestForLeaveList!view.action?id=${formValue.id?c}&lt;/variable&gt;&lt;assignment actor-id=&quot;39&quot; actor-name=&quot;王宇航&quot;/&gt;&lt;/task&gt;\" event=\"&lt;event type=&quot;&quot;/&gt;\" id=\"5\">\r\n			<mxCell style=\"colorRect;fontColor=#3300FF;\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"260\" y=\"220\" width=\"85\" height=\"32\" as=\"geometry\"/></mxCell></Task_Node>\r\n		<Task_Node label=\"人事乙审批\" description=\"\" task=\"&lt;task name=&quot;人事乙审批&quot;&gt;&lt;variable name=&quot;workflow_task_url&quot;&gt;${bravoHome}/common/requestForLeaveList!view.action?id=${formValue.id?c}&lt;/variable&gt;&lt;assignment actor-id=&quot;40&quot; actor-name=&quot;林小云&quot;/&gt;&lt;/task&gt;\" event=\"&lt;event type=&quot;&quot;/&gt;\" id=\"6\">\r\n			<mxCell style=\"colorRect;fontColor=#3300FF;\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"260\" y=\"290\" width=\"85\" height=\"32\" as=\"geometry\"/></mxCell></Task_Node>\r\n		<Task_Node label=\"人事丙审批\" description=\"\" task=\"&lt;task name=&quot;人事丙审批&quot;&gt;&lt;variable name=&quot;workflow_task_url&quot;&gt;${bravoHome}/common/requestForLeaveList!view.action?id=${formValue.id?c}&lt;/variable&gt;&lt;assignment actor-id=&quot;56&quot; actor-name=&quot;刘杨&quot;/&gt;&lt;/task&gt;\" event=\"&lt;event type=&quot;&quot;/&gt;\" id=\"7\">\r\n			<mxCell style=\"colorRect;fontColor=#3300FF;\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"260\" y=\"360\" width=\"85\" height=\"32\" as=\"geometry\"/></mxCell></Task_Node>\r\n		<Task_Node label=\"总经理审批\" description=\"\" task=\"&lt;task name=&quot;总经理审批&quot;&gt;&lt;variable name=&quot;workflow_task_url&quot;&gt;${bravoHome}/common/requestForLeaveList!view.action?id=${formValue.id?c}&lt;/variable&gt;&lt;assignment actor-id=&quot;42&quot; actor-name=&quot;郭天&quot;/&gt;&lt;/task&gt;\" event=\"&lt;event type=&quot;&quot;/&gt;\" id=\"8\">\r\n			<mxCell style=\"colorRect;fontColor=#3300FF;\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"520\" y=\"360\" width=\"85\" height=\"32\" as=\"geometry\"/></mxCell></Task_Node>\r\n		<Fork label=\"Fork1\" description=\"\" id=\"9\">\r\n			<mxCell style=\"symbol;image=images/symbols/fork.png\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"100\" y=\"290\" width=\"32\" height=\"32\" as=\"geometry\"/></mxCell></Fork>\r\n		<Edge label=\"组长审批\" description=\"\" condition=\"\" id=\"10\">\r\n			<mxCell style=\"straightEdge\" parent=\"1\" source=\"2\" target=\"3\" edge=\"1\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge>\r\n		<Edge label=\"项目经理审批\" description=\"\" condition=\"\" id=\"11\">\r\n			<mxCell style=\"straightEdge\" parent=\"1\" source=\"3\" target=\"4\" edge=\"1\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge>\r\n		<Edge label=\"人事审批\" description=\"\" condition=\"\" id=\"12\">\r\n			<mxCell style=\"defaultEdge\" parent=\"1\" source=\"4\" target=\"9\" edge=\"1\"><mxGeometry x=\"-0.404255319148936\" relative=\"1\" as=\"geometry\"><Array as=\"points\"><mxPoint x=\"60\" y=\"210\"/></Array><mxPoint as=\"offset\"/></mxGeometry></mxCell></Edge>\r\n		<Edge label=\"人事甲审批\" description=\"\" condition=\"\" id=\"13\">\r\n			<mxCell style=\"defaultEdge\" parent=\"1\" source=\"9\" target=\"5\" edge=\"1\"><mxGeometry width=\"32\" height=\"32\" as=\"geometry\"><mxPoint x=\"-1\" y=\"-41\" as=\"offset\"/></mxGeometry></mxCell></Edge>\r\n		<Edge label=\"人事乙审批\" description=\"\" condition=\"\" id=\"14\">\r\n			<mxCell style=\"defaultEdge\" parent=\"1\" source=\"9\" target=\"6\" edge=\"1\"><mxGeometry width=\"32\" height=\"32\" as=\"geometry\"><mxPoint x=\"-1\" y=\"-16.5\" as=\"offset\"/></mxGeometry></mxCell></Edge>\r\n		<Edge label=\"人事丙审批\" description=\"\" condition=\"\" id=\"15\">\r\n			<mxCell style=\"defaultEdge\" parent=\"1\" source=\"9\" target=\"7\" edge=\"1\"><mxGeometry width=\"32\" height=\"32\" as=\"geometry\"><mxPoint x=\"-1\" y=\"19\" as=\"offset\"/></mxGeometry></mxCell></Edge>\r\n		<Join label=\"Join1\" description=\"\" id=\"16\">\r\n			<mxCell style=\"symbol;image=images/symbols/merge.png\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"480\" y=\"290\" width=\"32\" height=\"32\" as=\"geometry\"/></mxCell></Join>\r\n		<Edge label=\"\" description=\"\" condition=\"\" id=\"17\">\r\n			<mxCell style=\"defaultEdge\" parent=\"1\" source=\"5\" target=\"16\" edge=\"1\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge>\r\n		<Edge label=\"\" description=\"\" condition=\"\" id=\"18\">\r\n			<mxCell style=\"defaultEdge\" parent=\"1\" source=\"6\" target=\"16\" edge=\"1\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge>\r\n		<Edge label=\"\" description=\"\" condition=\"\" id=\"19\">\r\n			<mxCell style=\"defaultEdge\" parent=\"1\" source=\"7\" target=\"16\" edge=\"1\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge>\r\n		<End label=\"end\" description=\"\" id=\"20\">\r\n			<mxCell style=\"colorRect;fontColor=#330000;strokeColor=#FF3300\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"520\" y=\"470\" width=\"85\" height=\"32\" as=\"geometry\"/></mxCell></End>\r\n		<Edge label=\"总经理审批\" description=\"\" condition=\"\" id=\"21\">\r\n			<mxCell style=\"defaultEdge\" parent=\"1\" source=\"16\" target=\"8\" edge=\"1\"><mxGeometry width=\"32\" height=\"32\" as=\"geometry\"><mxPoint x=\"29\" y=\"-43\" as=\"offset\"/><Array as=\"points\"><mxPoint x=\"560\" y=\"340\"/></Array><mxPoint x=\"391\" y=\"300\" as=\"targetPoint\"/><mxPoint x=\"391\" y=\"262\" as=\"sourcePoint\"/></mxGeometry></mxCell></Edge>\r\n		<Edge label=\"审批结束\" description=\"\" condition=\"\" id=\"22\">\r\n			<mxCell style=\"straightEdge\" parent=\"1\" source=\"8\" target=\"20\" edge=\"1\"><mxGeometry x=\"350\" y=\"300\" width=\"85\" height=\"32\" as=\"geometry\"/></mxCell></Edge>\r\n	</root></mxGraphModel>');
INSERT INTO `bravo_wf_diagram` VALUES ('2', '593703', '<mxGraphModel><root><Workflow label=\"MyWorkflow\" description=\"\" href=\"\" id=\"0\"><mxCell/></Workflow>\r\n		<Layer label=\"Default Layer\" id=\"1\">\r\n			<mxCell parent=\"0\"/></Layer>\r\n		<Start label=\"Start\" description=\"\" id=\"2\">\r\n			<mxCell style=\"colorRect;fontColor=red;strokeColor=red\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"160\" y=\"130\" width=\"80\" height=\"40\" as=\"geometry\"/></mxCell></Start>\r\n		<Task_Node label=\"测试节点1\" description=\"\" task=\"&lt;task name=&quot;测试节点1&quot;&gt;&lt;variable name=&quot;sadas&quot;&gt;asdasd&lt;/variable&gt;&lt;variable name=&quot;workflow_task_url&quot;&gt;111&lt;/variable&gt;&lt;assignment pooled-actors=&quot;35,37&quot; pooled-actorsName=&quot;工作流设计人员,人事管理人员&quot;/&gt;&lt;/task&gt;\" event=\"&lt;event type=&quot;&quot;/&gt;\" id=\"3\">\r\n			<mxCell style=\"colorRect;fontColor=#3300FF;\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"150\" y=\"230\" width=\"90\" height=\"40\" as=\"geometry\"/></mxCell></Task_Node>\r\n		<Task_Node label=\"测试节点2\" description=\"\" task=\"&lt;task name=&quot;测试节点2&quot;&gt;&lt;variable name=&quot;workflow_task_url&quot;&gt;435435&lt;/variable&gt;&lt;assignment pooled-actors=&quot;36&quot; pooled-actorsName=&quot;未授权人员&quot;/&gt;&lt;/task&gt;\" event=\"&lt;event type=&quot;&quot;/&gt;\" id=\"4\">\r\n			<mxCell style=\"colorRect;fontColor=#3300FF;\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"180\" y=\"330\" width=\"90\" height=\"40\" as=\"geometry\"/></mxCell></Task_Node>\r\n		<Task_Node label=\"测试节点3\" description=\"\" task=\"&lt;task name=&quot;测试节点3&quot;&gt;&lt;variable name=&quot;workflow_task_url&quot;&gt;23423423&lt;/variable&gt;&lt;assignment pooled-actors=&quot;35&quot; pooled-actorsName=&quot;工作流设计人员&quot;/&gt;&lt;/task&gt;\" event=\"&lt;event type=&quot;&quot;/&gt;\" id=\"5\">\r\n			<mxCell style=\"colorRect;fontColor=#3300FF;\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"380\" y=\"330\" width=\"90\" height=\"40\" as=\"geometry\"/></mxCell></Task_Node>\r\n		<End label=\"end\" description=\"\" id=\"6\">\r\n			<mxCell style=\"colorRect;fontColor=red;strokeColor=red\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"250\" y=\"460\" width=\"80\" height=\"40\" as=\"geometry\"/></mxCell></End>\r\n		<Edge label=\"\" description=\"\" condition=\"\" id=\"7\">\r\n			<mxCell style=\"defaultEdge\" edge=\"1\" parent=\"1\" source=\"2\" target=\"3\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge>\r\n		<Edge label=\"\" description=\"\" condition=\"\" id=\"8\">\r\n			<mxCell style=\"defaultEdge\" edge=\"1\" parent=\"1\" source=\"3\" target=\"4\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge>\r\n		<Edge label=\"\" description=\"\" condition=\"\" id=\"9\">\r\n			<mxCell style=\"defaultEdge\" edge=\"1\" parent=\"1\" source=\"4\" target=\"5\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge>\r\n		<Edge label=\"\" description=\"\" condition=\"\" id=\"10\">\r\n			<mxCell style=\"defaultEdge\" edge=\"1\" parent=\"1\" source=\"5\" target=\"6\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge>\r\n	</root></mxGraphModel>');
INSERT INTO `bravo_wf_diagram` VALUES ('3', '593704', '<mxGraphModel><root><Workflow label=\"MyWorkflow\" description=\"\" href=\"\" id=\"0\"><mxCell/></Workflow>\r\n		<Layer label=\"Default Layer\" id=\"1\">\r\n			<mxCell parent=\"0\"/></Layer>\r\n		<Start label=\"Start\" description=\"\" id=\"2\">\r\n			<mxCell style=\"colorRect;fontColor=red;strokeColor=red\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"160\" y=\"130\" width=\"80\" height=\"40\" as=\"geometry\"/></mxCell></Start>\r\n		<Task_Node label=\"测试节点1\" description=\"\" task=\"&lt;task name=&quot;测试节点1&quot;&gt;&lt;variable name=&quot;sadas&quot;&gt;asdasd&lt;/variable&gt;&lt;variable name=&quot;workflow_task_url&quot;&gt;111&lt;/variable&gt;&lt;assignment pooled-actors=&quot;35,37&quot; pooled-actorsName=&quot;工作流设计人员,人事管理人员&quot;/&gt;&lt;/task&gt;\" event=\"&lt;event type=&quot;&quot;/&gt;\" id=\"3\">\r\n			<mxCell style=\"colorRect;fontColor=#3300FF;\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"150\" y=\"230\" width=\"90\" height=\"40\" as=\"geometry\"/></mxCell></Task_Node>\r\n		<Task_Node label=\"测试节点2\" description=\"\" task=\"&lt;task name=&quot;测试节点2&quot;&gt;&lt;variable name=&quot;workflow_task_url&quot;&gt;435435&lt;/variable&gt;&lt;assignment pooled-actors=&quot;36&quot; pooled-actorsName=&quot;未授权人员&quot;/&gt;&lt;/task&gt;\" event=\"&lt;event type=&quot;&quot;/&gt;\" id=\"4\">\r\n			<mxCell style=\"colorRect;fontColor=#3300FF;\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"180\" y=\"330\" width=\"90\" height=\"40\" as=\"geometry\"/></mxCell></Task_Node>\r\n		<Task_Node label=\"测试节点3\" description=\"\" task=\"&lt;task name=&quot;测试节点3&quot;&gt;&lt;variable name=&quot;workflow_task_url&quot;&gt;23423423&lt;/variable&gt;&lt;assignment pooled-actors=&quot;35&quot; pooled-actorsName=&quot;工作流设计人员&quot;/&gt;&lt;/task&gt;\" event=\"&lt;event type=&quot;&quot;/&gt;\" id=\"5\">\r\n			<mxCell style=\"colorRect;fontColor=#3300FF;\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"380\" y=\"330\" width=\"90\" height=\"40\" as=\"geometry\"/></mxCell></Task_Node>\r\n		<End label=\"end\" description=\"\" id=\"6\">\r\n			<mxCell style=\"colorRect;fontColor=red;strokeColor=red\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"250\" y=\"460\" width=\"80\" height=\"40\" as=\"geometry\"/></mxCell></End>\r\n		<Edge label=\"\" description=\"\" condition=\"\" id=\"7\">\r\n			<mxCell style=\"defaultEdge\" edge=\"1\" parent=\"1\" source=\"2\" target=\"3\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge>\r\n		<Edge label=\"\" description=\"\" condition=\"\" id=\"8\">\r\n			<mxCell style=\"defaultEdge\" edge=\"1\" parent=\"1\" source=\"3\" target=\"4\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge>\r\n		<Edge label=\"\" description=\"\" condition=\"\" id=\"9\">\r\n			<mxCell style=\"defaultEdge\" edge=\"1\" parent=\"1\" source=\"4\" target=\"5\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge>\r\n		<Edge label=\"\" description=\"\" condition=\"\" id=\"10\">\r\n			<mxCell style=\"defaultEdge\" edge=\"1\" parent=\"1\" source=\"5\" target=\"6\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge>\r\n	</root></mxGraphModel>');
INSERT INTO `bravo_wf_diagram` VALUES ('4', '593705', '<mxGraphModel><root><Workflow label=\"MyWorkflow\" description=\"\" href=\"\" id=\"0\"><mxCell/></Workflow>\r\n		<Layer label=\"Default Layer\" id=\"1\">\r\n			<mxCell parent=\"0\"/></Layer>\r\n	</root></mxGraphModel>');
INSERT INTO `bravo_wf_diagram` VALUES ('117', '53770', '<mxGraphModel><root><Workflow label=\"MyWorkflow_test\" description=\"\" href=\"\" id=\"0\"><mxCell><mxGeometry x=\"230\" width=\"80\" height=\"40\" as=\"geometry\"/></mxCell></Workflow>\r\n		<Layer label=\"Default Layer\" id=\"1\">\r\n			<mxCell parent=\"0\"/></Layer>\r\n		<Start label=\"Start\" description=\"create new  web sale work\" id=\"2\">\r\n			<mxCell style=\"colorRect;fontColor=red;strokeColor=red\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"230\" width=\"80\" height=\"40\" as=\"geometry\"/></mxCell></Start>\r\n	</root></mxGraphModel>');
INSERT INTO `bravo_wf_diagram` VALUES ('119', '593613', '<mxGraphModel><root><Workflow label=\"请假示例流程\" description=\"../../../common/requestForLeaveList!add.action\" href=\"\" id=\"0\"><mxCell/></Workflow>\r\n		<Layer label=\"Default Layer\" id=\"1\">\r\n			<mxCell parent=\"0\"/></Layer>\r\n		<Start label=\"Start\" description=\"\" id=\"2\">\r\n			<mxCell style=\"colorRect;fontColor=green;strokeColor=#33FF66\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"20\" y=\"20\" width=\"85\" height=\"32\" as=\"geometry\"/></mxCell></Start>\r\n		<Task_Node label=\"组长审批\" description=\"\" task=\"&lt;task name=&quot;组长审批&quot;&gt;&lt;variable name=&quot;workflow_task_url&quot;&gt;${bravoHome}/common/requestForLeaveList!view.action?id=${formValue.id?c}&lt;/variable&gt;&lt;assignment actor-id=&quot;1&quot; actor-name=&quot;吴佳兴&quot;/&gt;&lt;/task&gt;\" event=\"&lt;event type=&quot;&quot;/&gt;\" id=\"3\">\r\n			<mxCell style=\"colorRect;fontColor=#3300FF;\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"20\" y=\"120\" width=\"85\" height=\"32\" as=\"geometry\"/></mxCell></Task_Node>\r\n		<Task_Node label=\"项目经理审批\" description=\"\" task=\"&lt;task name=&quot;项目经理审批&quot;&gt;&lt;variable name=&quot;workflow_task_url&quot;&gt;${bravoHome}/common/requestForLeaveList!view.action?id=${formValue.id?c}&lt;/variable&gt;&lt;assignment actor-id=&quot;57&quot; actor-name=&quot;刘征辉&quot;/&gt;&lt;/task&gt;\" event=\"&lt;event type=&quot;&quot;/&gt;\" id=\"4\">\r\n			<mxCell style=\"colorRect;fontColor=#3300FF;\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"20\" y=\"210\" width=\"85\" height=\"32\" as=\"geometry\"/></mxCell></Task_Node>\r\n		<Task_Node label=\"人事甲审批\" description=\"\" task=\"&lt;task name=&quot;人事甲审批&quot;&gt;&lt;variable name=&quot;workflow_task_url&quot;&gt;${bravoHome}/common/requestForLeaveList!view.action?id=${formValue.id?c}&lt;/variable&gt;&lt;assignment actor-id=&quot;39&quot; actor-name=&quot;王宇航&quot;/&gt;&lt;/task&gt;\" event=\"&lt;event type=&quot;&quot;/&gt;\" id=\"5\">\r\n			<mxCell style=\"colorRect;fontColor=#3300FF;\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"260\" y=\"220\" width=\"85\" height=\"32\" as=\"geometry\"/></mxCell></Task_Node>\r\n		<Task_Node label=\"人事乙审批\" description=\"\" task=\"&lt;task name=&quot;人事乙审批&quot;&gt;&lt;variable name=&quot;workflow_task_url&quot;&gt;${bravoHome}/common/requestForLeaveList!view.action?id=${formValue.id?c}&lt;/variable&gt;&lt;assignment actor-id=&quot;40&quot; actor-name=&quot;林小云&quot;/&gt;&lt;/task&gt;\" event=\"&lt;event type=&quot;&quot;/&gt;\" id=\"6\">\r\n			<mxCell style=\"colorRect;fontColor=#3300FF;\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"260\" y=\"290\" width=\"85\" height=\"32\" as=\"geometry\"/></mxCell></Task_Node>\r\n		<Task_Node label=\"人事丙审批\" description=\"\" task=\"&lt;task name=&quot;人事丙审批&quot;&gt;&lt;variable name=&quot;workflow_task_url&quot;&gt;${bravoHome}/common/requestForLeaveList!view.action?id=${formValue.id?c}&lt;/variable&gt;&lt;assignment actor-id=&quot;56&quot; actor-name=&quot;刘杨&quot;/&gt;&lt;/task&gt;\" event=\"&lt;event type=&quot;&quot;/&gt;\" id=\"7\">\r\n			<mxCell style=\"colorRect;fontColor=#3300FF;\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"260\" y=\"360\" width=\"85\" height=\"32\" as=\"geometry\"/></mxCell></Task_Node>\r\n		<Task_Node label=\"总经理审批\" description=\"\" task=\"&lt;task name=&quot;总经理审批&quot;&gt;&lt;variable name=&quot;workflow_task_url&quot;&gt;${bravoHome}/common/requestForLeaveList!view.action?id=${formValue.id?c}&lt;/variable&gt;&lt;assignment actor-id=&quot;42&quot; actor-name=&quot;郭天&quot;/&gt;&lt;/task&gt;\" event=\"&lt;event type=&quot;&quot;/&gt;\" id=\"8\">\r\n			<mxCell style=\"colorRect;fontColor=#3300FF;\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"520\" y=\"360\" width=\"85\" height=\"32\" as=\"geometry\"/></mxCell></Task_Node>\r\n		<Fork label=\"Fork1\" description=\"\" id=\"9\">\r\n			<mxCell style=\"symbol;image=images/symbols/fork.png\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"100\" y=\"290\" width=\"32\" height=\"32\" as=\"geometry\"/></mxCell></Fork>\r\n		<Edge label=\"组长审批\" description=\"\" condition=\"\" id=\"10\">\r\n			<mxCell style=\"straightEdge\" parent=\"1\" source=\"2\" target=\"3\" edge=\"1\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge>\r\n		<Edge label=\"项目经理审批\" description=\"\" condition=\"\" id=\"11\">\r\n			<mxCell style=\"straightEdge\" parent=\"1\" source=\"3\" target=\"4\" edge=\"1\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge>\r\n		<Edge label=\"人事审批\" description=\"\" condition=\"\" id=\"12\">\r\n			<mxCell style=\"defaultEdge\" parent=\"1\" source=\"4\" target=\"9\" edge=\"1\"><mxGeometry x=\"-0.404255319148936\" relative=\"1\" as=\"geometry\"><Array as=\"points\"><mxPoint x=\"60\" y=\"210\"/></Array><mxPoint as=\"offset\"/></mxGeometry></mxCell></Edge>\r\n		<Edge label=\"人事甲审批\" description=\"\" condition=\"\" id=\"13\">\r\n			<mxCell style=\"defaultEdge\" parent=\"1\" source=\"9\" target=\"5\" edge=\"1\"><mxGeometry width=\"32\" height=\"32\" as=\"geometry\"><mxPoint x=\"-1\" y=\"-41\" as=\"offset\"/></mxGeometry></mxCell></Edge>\r\n		<Edge label=\"人事乙审批\" description=\"\" condition=\"\" id=\"14\">\r\n			<mxCell style=\"defaultEdge\" parent=\"1\" source=\"9\" target=\"6\" edge=\"1\"><mxGeometry width=\"32\" height=\"32\" as=\"geometry\"><mxPoint x=\"-1\" y=\"-16.5\" as=\"offset\"/></mxGeometry></mxCell></Edge>\r\n		<Edge label=\"人事丙审批\" description=\"\" condition=\"\" id=\"15\">\r\n			<mxCell style=\"defaultEdge\" parent=\"1\" source=\"9\" target=\"7\" edge=\"1\"><mxGeometry width=\"32\" height=\"32\" as=\"geometry\"><mxPoint x=\"-1\" y=\"19\" as=\"offset\"/></mxGeometry></mxCell></Edge>\r\n		<Join label=\"Join1\" description=\"\" id=\"16\">\r\n			<mxCell style=\"symbol;image=images/symbols/merge.png\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"480\" y=\"290\" width=\"32\" height=\"32\" as=\"geometry\"/></mxCell></Join>\r\n		<Edge label=\"\" description=\"\" condition=\"\" id=\"17\">\r\n			<mxCell style=\"defaultEdge\" parent=\"1\" source=\"5\" target=\"16\" edge=\"1\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge>\r\n		<Edge label=\"\" description=\"\" condition=\"\" id=\"18\">\r\n			<mxCell style=\"defaultEdge\" parent=\"1\" source=\"6\" target=\"16\" edge=\"1\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge>\r\n		<Edge label=\"\" description=\"\" condition=\"\" id=\"19\">\r\n			<mxCell style=\"defaultEdge\" parent=\"1\" source=\"7\" target=\"16\" edge=\"1\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge>\r\n		<End label=\"end\" description=\"\" id=\"20\">\r\n			<mxCell style=\"colorRect;fontColor=#330000;strokeColor=#FF3300\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"520\" y=\"470\" width=\"85\" height=\"32\" as=\"geometry\"/></mxCell></End>\r\n		<Edge label=\"总经理审批\" description=\"\" condition=\"\" id=\"21\">\r\n			<mxCell style=\"defaultEdge\" parent=\"1\" source=\"16\" target=\"8\" edge=\"1\"><mxGeometry width=\"32\" height=\"32\" as=\"geometry\"><mxPoint x=\"29\" y=\"-43\" as=\"offset\"/><Array as=\"points\"><mxPoint x=\"560\" y=\"340\"/></Array><mxPoint x=\"391\" y=\"300\" as=\"targetPoint\"/><mxPoint x=\"391\" y=\"262\" as=\"sourcePoint\"/></mxGeometry></mxCell></Edge>\r\n		<Edge label=\"审批结束\" description=\"\" condition=\"\" id=\"22\">\r\n			<mxCell style=\"straightEdge\" parent=\"1\" source=\"8\" target=\"20\" edge=\"1\"><mxGeometry x=\"350\" y=\"300\" width=\"85\" height=\"32\" as=\"geometry\"/></mxCell></Edge>\r\n	</root></mxGraphModel>');

-- ----------------------------
-- Table structure for `call_detail`
-- ----------------------------
DROP TABLE IF EXISTS `call_detail`;
CREATE TABLE `call_detail` (
  `id` decimal(19,0) NOT NULL,
  `sequences` int(5) default NULL,
  `callProject` decimal(19,0) NOT NULL,
  `incident` varchar(256) NOT NULL,
  `censor` decimal(19,0) default NULL,
  `occur_dt` date default NULL,
  `priority` decimal(19,0) default NULL,
  `displace` tinyint(1) default NULL,
  `accessory` decimal(19,0) default NULL,
  `position` decimal(19,0) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of call_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `call_project`
-- ----------------------------
DROP TABLE IF EXISTS `call_project`;
CREATE TABLE `call_project` (
  `id` decimal(19,0) NOT NULL,
  `location` decimal(19,0) NOT NULL,
  `store_front` decimal(19,0) NOT NULL,
  `comments` varchar(1024) default NULL,
  `censor` decimal(19,0) default NULL,
  `resquter` decimal(19,0) default NULL,
  `maintenancer` varchar(256) default NULL,
  `plan_fin_dt` date default NULL,
  `act_fin_dt` date default NULL,
  `solution` varchar(1024) default NULL,
  `status` decimal(19,0) default NULL,
  `process_status` varchar(32) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of call_project
-- ----------------------------
INSERT INTO `call_project` VALUES ('20', '7', '12', 'sdsadsadsaasdsda', null, null, null, null, null, null, '15', null);
INSERT INTO `call_project` VALUES ('21', '7', '12', 'werewrewr', null, '1', null, null, null, null, '15', null);
INSERT INTO `call_project` VALUES ('22', '7', '10', 'asdsdsa', null, '1', null, null, null, null, '15', null);
INSERT INTO `call_project` VALUES ('25', '7', '12', 'sdsadsadsaasdsda', null, null, null, null, null, null, '15', null);
INSERT INTO `call_project` VALUES ('26', '7', '12', 'sdsadsadsaasdsda', null, null, null, null, null, null, '15', null);
INSERT INTO `call_project` VALUES ('27', '7', '12', 'sdsadsadsaasdsda', null, null, null, null, null, null, '9', null);
INSERT INTO `call_project` VALUES ('28', '9', '13', 'asdsadsa', null, '1', null, null, null, null, null, 'INIT');
INSERT INTO `call_project` VALUES ('29', '9', '13', 'asdsadsa', null, null, null, null, null, null, null, 'PENDING');
INSERT INTO `call_project` VALUES ('31', '9', '13', 'asdsadsa', null, null, null, null, null, null, null, 'DISPLACE');
INSERT INTO `call_project` VALUES ('33', '9', '13', 'asdsadsa', null, null, null, null, null, null, null, 'SUMMARY');
INSERT INTO `call_project` VALUES ('35', '7', '12', 'sadsada', null, '1', null, null, null, null, null, 'INIT');
INSERT INTO `call_project` VALUES ('36', '7', '12', 'sadsada', null, null, null, null, null, null, null, 'PENDING');
INSERT INTO `call_project` VALUES ('38', '7', '12', 'sadsada', null, null, '222', '2011-06-10', null, null, '15', 'DISPLACE');
INSERT INTO `call_project` VALUES ('40', '7', '11', 'sdsad', null, '1', null, null, null, null, null, 'INIT');
INSERT INTO `call_project` VALUES ('41', '7', '12', 'sadsada', null, null, '222', '2011-06-10', null, null, '15', 'SUMMARY');
INSERT INTO `call_project` VALUES ('42', '7', '12', 'sadsada', null, null, '222', '2011-06-10', null, 'sadlsadlsa', '15', 'SUMMARY');
INSERT INTO `call_project` VALUES ('43', '7', '12', 'sadsadaasds', null, null, '222', '2011-06-10', null, 'sadlsadlsa', '15', 'FINISH');
INSERT INTO `call_project` VALUES ('44', '7', '12', 'asdsadsadsa', null, '1', null, null, null, null, null, 'INIT');
INSERT INTO `call_project` VALUES ('45', '7', '12', 'asdsadsadsa', null, null, null, null, null, null, null, 'PENDING');
INSERT INTO `call_project` VALUES ('47', '7', '12', 'asdsadsadsa', null, null, '三上吊', '2011-06-06', '2011-06-23', null, '15', 'DISPLACE');
INSERT INTO `call_project` VALUES ('49', '7', '12', 'asdsadsadsa', null, null, '三上吊', '2011-06-06', '2011-06-23', '萨拉萨蒂拉萨雷', '15', 'SUMMARY');
INSERT INTO `call_project` VALUES ('51', '7', '12', 'asdsadsadsa', null, null, '三上吊', '2011-06-06', '2011-06-23', '萨拉萨蒂拉萨雷', '15', 'FINISH');
INSERT INTO `call_project` VALUES ('53', '7', '12', 'asdsadsadsa', null, null, '三上吊', '2011-06-06', '2011-06-23', null, '15', 'SUMMARY');
INSERT INTO `call_project` VALUES ('55', '7', '12', 'asdsadsadsa', null, null, '三上吊', '2011-06-06', '2011-06-23', '撒旦萨迪斯', '15', 'FINISH');
INSERT INTO `call_project` VALUES ('57', '7', '12', 'asdsadsadsa', null, null, '三上吊', '2011-06-06', '2011-06-23', null, '15', 'SUMMARY');
INSERT INTO `call_project` VALUES ('59', '7', '12', 'asdsadsadsa', null, null, '三上吊', '2011-06-06', '2011-06-23', '', '15', 'FINISH');

-- ----------------------------
-- Table structure for `jbpm_action`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_action`;
CREATE TABLE `jbpm_action` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `class` char(1) NOT NULL,
  `NAME_` varchar(255) default NULL,
  `ISPROPAGATIONALLOWED_` bit(1) default NULL,
  `ACTIONEXPRESSION_` varchar(255) default NULL,
  `ISASYNC_` bit(1) default NULL,
  `REFERENCEDACTION_` bigint(20) default NULL,
  `ACTIONDELEGATION_` bigint(20) default NULL,
  `EVENT_` bigint(20) default NULL,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `TIMERNAME_` varchar(255) default NULL,
  `DUEDATE_` varchar(255) default NULL,
  `REPEAT_` varchar(255) default NULL,
  `TRANSITIONNAME_` varchar(255) default NULL,
  `TIMERACTION_` bigint(20) default NULL,
  `EXPRESSION_` text,
  `EVENTINDEX_` int(11) default NULL,
  `EXCEPTIONHANDLER_` bigint(20) default NULL,
  `EXCEPTIONHANDLERINDEX_` int(11) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_ACTION_EVENT` (`EVENT_`),
  KEY `IDX_ACTION_ACTNDL` (`ACTIONDELEGATION_`),
  KEY `IDX_ACTION_PROCDF` (`PROCESSDEFINITION_`),
  KEY `FK_ACTION_EVENT` (`EVENT_`),
  KEY `FK_ACTION_EXPTHDL` (`EXCEPTIONHANDLER_`),
  KEY `FK_ACTION_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `FK_CRTETIMERACT_TA` (`TIMERACTION_`),
  KEY `FK_ACTION_ACTNDEL` (`ACTIONDELEGATION_`),
  KEY `FK_ACTION_REFACT` (`REFERENCEDACTION_`),
  CONSTRAINT `FK_ACTION_ACTNDEL` FOREIGN KEY (`ACTIONDELEGATION_`) REFERENCES `jbpm_delegation` (`ID_`),
  CONSTRAINT `FK_ACTION_EVENT` FOREIGN KEY (`EVENT_`) REFERENCES `jbpm_event` (`ID_`),
  CONSTRAINT `FK_ACTION_EXPTHDL` FOREIGN KEY (`EXCEPTIONHANDLER_`) REFERENCES `jbpm_exceptionhandler` (`ID_`),
  CONSTRAINT `FK_ACTION_PROCDEF` FOREIGN KEY (`PROCESSDEFINITION_`) REFERENCES `jbpm_processdefinition` (`ID_`),
  CONSTRAINT `FK_ACTION_REFACT` FOREIGN KEY (`REFERENCEDACTION_`) REFERENCES `jbpm_action` (`ID_`),
  CONSTRAINT `FK_CRTETIMERACT_TA` FOREIGN KEY (`TIMERACTION_`) REFERENCES `jbpm_action` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_action
-- ----------------------------
INSERT INTO `jbpm_action` VALUES ('1', 'A', null, '', null, '', null, '2', '1', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('2', 'A', null, '', null, '', null, '3', '2', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('3', 'A', null, '', null, '', null, '5', '3', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('4', 'A', null, '', null, '', null, '6', '4', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('5', 'A', null, '', null, '', null, '8', '5', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('6', 'A', null, '', null, '', null, '9', '6', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('7', 'A', null, '', null, '', null, '11', '7', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('8', 'A', null, '', null, '', null, '12', '8', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('9', 'A', null, '', null, '', null, '14', '9', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('10', 'A', null, '', null, '', null, '15', '10', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('11', 'A', null, '', null, '', null, '17', '11', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('12', 'A', null, '', null, '', null, '18', '12', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('13', 'A', null, '', null, '', null, '20', '13', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('14', 'A', null, '', null, '', null, '21', '14', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('15', 'A', null, '', null, '', null, '23', '15', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('16', 'A', null, '', null, '', null, '24', '16', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('17', 'A', null, '', null, '', null, '26', '17', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('18', 'A', null, '', null, '', null, '27', '18', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('19', 'A', null, '', null, '', null, '29', '19', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('20', 'A', null, '', null, '', null, '30', '20', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('21', 'A', null, '', null, '', null, '32', '21', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('22', 'A', null, '', null, '', null, '33', '22', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('23', 'A', null, '', null, '', null, '35', '23', null, null, null, null, null, null, null, '0', null, null);
INSERT INTO `jbpm_action` VALUES ('24', 'A', null, '', null, '', null, '36', '24', null, null, null, null, null, null, null, '0', null, null);

-- ----------------------------
-- Table structure for `jbpm_bytearray`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_bytearray`;
CREATE TABLE `jbpm_bytearray` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `NAME_` varchar(255) default NULL,
  `FILEDEFINITION_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_BYTEARR_FILDEF` (`FILEDEFINITION_`),
  CONSTRAINT `FK_BYTEARR_FILDEF` FOREIGN KEY (`FILEDEFINITION_`) REFERENCES `jbpm_moduledefinition` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_bytearray
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm_byteblock`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_byteblock`;
CREATE TABLE `jbpm_byteblock` (
  `PROCESSFILE_` bigint(20) NOT NULL,
  `BYTES_` blob,
  `INDEX_` int(11) NOT NULL,
  PRIMARY KEY  (`PROCESSFILE_`,`INDEX_`),
  KEY `FK_BYTEBLOCK_FILE` (`PROCESSFILE_`),
  CONSTRAINT `FK_BYTEBLOCK_FILE` FOREIGN KEY (`PROCESSFILE_`) REFERENCES `jbpm_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_byteblock
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm_comment`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_comment`;
CREATE TABLE `jbpm_comment` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `VERSION_` int(11) NOT NULL,
  `ACTORID_` varchar(255) default NULL,
  `TIME_` datetime default NULL,
  `MESSAGE_` text,
  `TOKEN_` bigint(20) default NULL,
  `TASKINSTANCE_` bigint(20) default NULL,
  `TOKENINDEX_` int(11) default NULL,
  `TASKINSTANCEINDEX_` int(11) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_COMMENT_TOKEN` (`TOKEN_`),
  KEY `IDX_COMMENT_TSK` (`TASKINSTANCE_`),
  KEY `FK_COMMENT_TOKEN` (`TOKEN_`),
  KEY `FK_COMMENT_TSK` (`TASKINSTANCE_`),
  CONSTRAINT `FK_COMMENT_TOKEN` FOREIGN KEY (`TOKEN_`) REFERENCES `jbpm_token` (`ID_`),
  CONSTRAINT `FK_COMMENT_TSK` FOREIGN KEY (`TASKINSTANCE_`) REFERENCES `jbpm_taskinstance` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm_decisionconditions`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_decisionconditions`;
CREATE TABLE `jbpm_decisionconditions` (
  `DECISION_` bigint(20) NOT NULL,
  `TRANSITIONNAME_` varchar(255) default NULL,
  `EXPRESSION_` varchar(255) default NULL,
  `INDEX_` int(11) NOT NULL,
  PRIMARY KEY  (`DECISION_`,`INDEX_`),
  KEY `FK_DECCOND_DEC` (`DECISION_`),
  CONSTRAINT `FK_DECCOND_DEC` FOREIGN KEY (`DECISION_`) REFERENCES `jbpm_node` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_decisionconditions
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm_delegation`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_delegation`;
CREATE TABLE `jbpm_delegation` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASSNAME_` text,
  `CONFIGURATION_` text,
  `CONFIGTYPE_` varchar(255) default NULL,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_DELEG_PRCD` (`PROCESSDEFINITION_`),
  KEY `FK_DELEGATION_PRCD` (`PROCESSDEFINITION_`),
  CONSTRAINT `FK_DELEGATION_PRCD` FOREIGN KEY (`PROCESSDEFINITION_`) REFERENCES `jbpm_processdefinition` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_delegation
-- ----------------------------
INSERT INTO `jbpm_delegation` VALUES ('1', 'com.cutty.bravo.components.jbpm.handler.SS2AssignmentHandler', '<actorId>1</actorId>', null, '593702');
INSERT INTO `jbpm_delegation` VALUES ('2', 'com.cutty.bravo.components.jbpm.task.action.TaskStartAction', null, null, '593702');
INSERT INTO `jbpm_delegation` VALUES ('3', 'com.cutty.bravo.components.jbpm.task.action.TaskEndAction', null, null, '593702');
INSERT INTO `jbpm_delegation` VALUES ('4', 'com.cutty.bravo.components.jbpm.handler.SS2AssignmentHandler', '<actorId>57</actorId>', null, '593702');
INSERT INTO `jbpm_delegation` VALUES ('5', 'com.cutty.bravo.components.jbpm.task.action.TaskStartAction', null, null, '593702');
INSERT INTO `jbpm_delegation` VALUES ('6', 'com.cutty.bravo.components.jbpm.task.action.TaskEndAction', null, null, '593702');
INSERT INTO `jbpm_delegation` VALUES ('7', 'com.cutty.bravo.components.jbpm.handler.SS2AssignmentHandler', '<actorId>39</actorId>', null, '593702');
INSERT INTO `jbpm_delegation` VALUES ('8', 'com.cutty.bravo.components.jbpm.task.action.TaskStartAction', null, null, '593702');
INSERT INTO `jbpm_delegation` VALUES ('9', 'com.cutty.bravo.components.jbpm.task.action.TaskEndAction', null, null, '593702');
INSERT INTO `jbpm_delegation` VALUES ('10', 'com.cutty.bravo.components.jbpm.handler.SS2AssignmentHandler', '<actorId>40</actorId>', null, '593702');
INSERT INTO `jbpm_delegation` VALUES ('11', 'com.cutty.bravo.components.jbpm.task.action.TaskStartAction', null, null, '593702');
INSERT INTO `jbpm_delegation` VALUES ('12', 'com.cutty.bravo.components.jbpm.task.action.TaskEndAction', null, null, '593702');
INSERT INTO `jbpm_delegation` VALUES ('13', 'com.cutty.bravo.components.jbpm.handler.SS2AssignmentHandler', '<actorId>56</actorId>', null, '593702');
INSERT INTO `jbpm_delegation` VALUES ('14', 'com.cutty.bravo.components.jbpm.task.action.TaskStartAction', null, null, '593702');
INSERT INTO `jbpm_delegation` VALUES ('15', 'com.cutty.bravo.components.jbpm.task.action.TaskEndAction', null, null, '593702');
INSERT INTO `jbpm_delegation` VALUES ('16', 'com.cutty.bravo.components.jbpm.handler.SS2AssignmentHandler', '<actorId>42</actorId>', null, '593702');
INSERT INTO `jbpm_delegation` VALUES ('17', 'com.cutty.bravo.components.jbpm.task.action.TaskStartAction', null, null, '593702');
INSERT INTO `jbpm_delegation` VALUES ('18', 'com.cutty.bravo.components.jbpm.task.action.TaskEndAction', null, null, '593702');
INSERT INTO `jbpm_delegation` VALUES ('19', 'com.cutty.bravo.components.jbpm.handler.SS2AssignmentHandler', '<roleId>35,37</roleId>', null, '593703');
INSERT INTO `jbpm_delegation` VALUES ('20', 'com.cutty.bravo.components.jbpm.task.action.TaskStartAction', null, null, '593703');
INSERT INTO `jbpm_delegation` VALUES ('21', 'com.cutty.bravo.components.jbpm.task.action.TaskEndAction', null, null, '593703');
INSERT INTO `jbpm_delegation` VALUES ('22', 'com.cutty.bravo.components.jbpm.handler.SS2AssignmentHandler', '<roleId>36</roleId>', null, '593703');
INSERT INTO `jbpm_delegation` VALUES ('23', 'com.cutty.bravo.components.jbpm.task.action.TaskStartAction', null, null, '593703');
INSERT INTO `jbpm_delegation` VALUES ('24', 'com.cutty.bravo.components.jbpm.task.action.TaskEndAction', null, null, '593703');
INSERT INTO `jbpm_delegation` VALUES ('25', 'com.cutty.bravo.components.jbpm.handler.SS2AssignmentHandler', '<roleId>35</roleId>', null, '593703');
INSERT INTO `jbpm_delegation` VALUES ('26', 'com.cutty.bravo.components.jbpm.task.action.TaskStartAction', null, null, '593703');
INSERT INTO `jbpm_delegation` VALUES ('27', 'com.cutty.bravo.components.jbpm.task.action.TaskEndAction', null, null, '593703');
INSERT INTO `jbpm_delegation` VALUES ('28', 'com.cutty.bravo.components.jbpm.handler.SS2AssignmentHandler', '<roleId>35,37</roleId>', null, '593704');
INSERT INTO `jbpm_delegation` VALUES ('29', 'com.cutty.bravo.components.jbpm.task.action.TaskStartAction', null, null, '593704');
INSERT INTO `jbpm_delegation` VALUES ('30', 'com.cutty.bravo.components.jbpm.task.action.TaskEndAction', null, null, '593704');
INSERT INTO `jbpm_delegation` VALUES ('31', 'com.cutty.bravo.components.jbpm.handler.SS2AssignmentHandler', '<roleId>36</roleId>', null, '593704');
INSERT INTO `jbpm_delegation` VALUES ('32', 'com.cutty.bravo.components.jbpm.task.action.TaskStartAction', null, null, '593704');
INSERT INTO `jbpm_delegation` VALUES ('33', 'com.cutty.bravo.components.jbpm.task.action.TaskEndAction', null, null, '593704');
INSERT INTO `jbpm_delegation` VALUES ('34', 'com.cutty.bravo.components.jbpm.handler.SS2AssignmentHandler', '<roleId>35</roleId>', null, '593704');
INSERT INTO `jbpm_delegation` VALUES ('35', 'com.cutty.bravo.components.jbpm.task.action.TaskStartAction', null, null, '593704');
INSERT INTO `jbpm_delegation` VALUES ('36', 'com.cutty.bravo.components.jbpm.task.action.TaskEndAction', null, null, '593704');

-- ----------------------------
-- Table structure for `jbpm_event`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_event`;
CREATE TABLE `jbpm_event` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `EVENTTYPE_` varchar(255) default NULL,
  `TYPE_` char(1) default NULL,
  `GRAPHELEMENT_` bigint(20) default NULL,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `NODE_` bigint(20) default NULL,
  `TRANSITION_` bigint(20) default NULL,
  `TASK_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_EVENT_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `FK_EVENT_NODE` (`NODE_`),
  KEY `FK_EVENT_TRANS` (`TRANSITION_`),
  KEY `FK_EVENT_TASK` (`TASK_`),
  CONSTRAINT `FK_EVENT_NODE` FOREIGN KEY (`NODE_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_EVENT_PROCDEF` FOREIGN KEY (`PROCESSDEFINITION_`) REFERENCES `jbpm_processdefinition` (`ID_`),
  CONSTRAINT `FK_EVENT_TASK` FOREIGN KEY (`TASK_`) REFERENCES `jbpm_task` (`ID_`),
  CONSTRAINT `FK_EVENT_TRANS` FOREIGN KEY (`TRANSITION_`) REFERENCES `jbpm_transition` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_event
-- ----------------------------
INSERT INTO `jbpm_event` VALUES ('1', 'task-create', 'A', '1', null, null, null, '1');
INSERT INTO `jbpm_event` VALUES ('2', 'task-end', 'A', '1', null, null, null, '1');
INSERT INTO `jbpm_event` VALUES ('3', 'task-create', 'A', '2', null, null, null, '2');
INSERT INTO `jbpm_event` VALUES ('4', 'task-end', 'A', '2', null, null, null, '2');
INSERT INTO `jbpm_event` VALUES ('5', 'task-create', 'A', '3', null, null, null, '3');
INSERT INTO `jbpm_event` VALUES ('6', 'task-end', 'A', '3', null, null, null, '3');
INSERT INTO `jbpm_event` VALUES ('7', 'task-create', 'A', '4', null, null, null, '4');
INSERT INTO `jbpm_event` VALUES ('8', 'task-end', 'A', '4', null, null, null, '4');
INSERT INTO `jbpm_event` VALUES ('9', 'task-create', 'A', '5', null, null, null, '5');
INSERT INTO `jbpm_event` VALUES ('10', 'task-end', 'A', '5', null, null, null, '5');
INSERT INTO `jbpm_event` VALUES ('11', 'task-create', 'A', '6', null, null, null, '6');
INSERT INTO `jbpm_event` VALUES ('12', 'task-end', 'A', '6', null, null, null, '6');
INSERT INTO `jbpm_event` VALUES ('13', 'task-create', 'A', '7', null, null, null, '7');
INSERT INTO `jbpm_event` VALUES ('14', 'task-end', 'A', '7', null, null, null, '7');
INSERT INTO `jbpm_event` VALUES ('15', 'task-create', 'A', '8', null, null, null, '8');
INSERT INTO `jbpm_event` VALUES ('16', 'task-end', 'A', '8', null, null, null, '8');
INSERT INTO `jbpm_event` VALUES ('17', 'task-create', 'A', '9', null, null, null, '9');
INSERT INTO `jbpm_event` VALUES ('18', 'task-end', 'A', '9', null, null, null, '9');
INSERT INTO `jbpm_event` VALUES ('19', 'task-create', 'A', '10', null, null, null, '10');
INSERT INTO `jbpm_event` VALUES ('20', 'task-end', 'A', '10', null, null, null, '10');
INSERT INTO `jbpm_event` VALUES ('21', 'task-create', 'A', '11', null, null, null, '11');
INSERT INTO `jbpm_event` VALUES ('22', 'task-end', 'A', '11', null, null, null, '11');
INSERT INTO `jbpm_event` VALUES ('23', 'task-create', 'A', '12', null, null, null, '12');
INSERT INTO `jbpm_event` VALUES ('24', 'task-end', 'A', '12', null, null, null, '12');

-- ----------------------------
-- Table structure for `jbpm_exceptionhandler`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_exceptionhandler`;
CREATE TABLE `jbpm_exceptionhandler` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `EXCEPTIONCLASSNAME_` text,
  `TYPE_` char(1) default NULL,
  `GRAPHELEMENT_` bigint(20) default NULL,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `GRAPHELEMENTINDEX_` int(11) default NULL,
  `NODE_` bigint(20) default NULL,
  `TRANSITION_` bigint(20) default NULL,
  `TASK_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_exceptionhandler
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm_job`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_job`;
CREATE TABLE `jbpm_job` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `DUEDATE_` datetime default NULL,
  `PROCESSINSTANCE_` bigint(20) default NULL,
  `TOKEN_` bigint(20) default NULL,
  `TASKINSTANCE_` bigint(20) default NULL,
  `ISSUSPENDED_` bit(1) default NULL,
  `ISEXCLUSIVE_` bit(1) default NULL,
  `LOCKOWNER_` varchar(255) default NULL,
  `LOCKTIME_` datetime default NULL,
  `EXCEPTION_` text,
  `RETRIES_` int(11) default NULL,
  `NAME_` varchar(255) default NULL,
  `REPEAT_` varchar(255) default NULL,
  `TRANSITIONNAME_` varchar(255) default NULL,
  `ACTION_` bigint(20) default NULL,
  `GRAPHELEMENTTYPE_` varchar(255) default NULL,
  `GRAPHELEMENT_` bigint(20) default NULL,
  `NODE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_JOB_TSKINST` (`TASKINSTANCE_`),
  KEY `IDX_JOB_PRINST` (`PROCESSINSTANCE_`),
  KEY `IDX_JOB_TOKEN` (`TOKEN_`),
  KEY `FK_JOB_TOKEN` (`TOKEN_`),
  KEY `FK_JOB_NODE` (`NODE_`),
  KEY `FK_JOB_PRINST` (`PROCESSINSTANCE_`),
  KEY `FK_JOB_ACTION` (`ACTION_`),
  KEY `FK_JOB_TSKINST` (`TASKINSTANCE_`),
  CONSTRAINT `FK_JOB_ACTION` FOREIGN KEY (`ACTION_`) REFERENCES `jbpm_action` (`ID_`),
  CONSTRAINT `FK_JOB_NODE` FOREIGN KEY (`NODE_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_JOB_PRINST` FOREIGN KEY (`PROCESSINSTANCE_`) REFERENCES `jbpm_processinstance` (`ID_`),
  CONSTRAINT `FK_JOB_TOKEN` FOREIGN KEY (`TOKEN_`) REFERENCES `jbpm_token` (`ID_`),
  CONSTRAINT `FK_JOB_TSKINST` FOREIGN KEY (`TASKINSTANCE_`) REFERENCES `jbpm_taskinstance` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_job
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm_log`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_log`;
CREATE TABLE `jbpm_log` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `INDEX_` int(11) default NULL,
  `DATE_` datetime default NULL,
  `TOKEN_` bigint(20) default NULL,
  `PARENT_` bigint(20) default NULL,
  `MESSAGE_` text,
  `EXCEPTION_` text,
  `ACTION_` bigint(20) default NULL,
  `NODE_` bigint(20) default NULL,
  `ENTER_` datetime default NULL,
  `LEAVE_` datetime default NULL,
  `DURATION_` bigint(20) default NULL,
  `NEWLONGVALUE_` bigint(20) default NULL,
  `TRANSITION_` bigint(20) default NULL,
  `CHILD_` bigint(20) default NULL,
  `SOURCENODE_` bigint(20) default NULL,
  `DESTINATIONNODE_` bigint(20) default NULL,
  `VARIABLEINSTANCE_` bigint(20) default NULL,
  `OLDBYTEARRAY_` bigint(20) default NULL,
  `NEWBYTEARRAY_` bigint(20) default NULL,
  `OLDDATEVALUE_` datetime default NULL,
  `NEWDATEVALUE_` datetime default NULL,
  `OLDDOUBLEVALUE_` double default NULL,
  `NEWDOUBLEVALUE_` double default NULL,
  `OLDLONGIDCLASS_` varchar(255) default NULL,
  `OLDLONGIDVALUE_` bigint(20) default NULL,
  `NEWLONGIDCLASS_` varchar(255) default NULL,
  `NEWLONGIDVALUE_` bigint(20) default NULL,
  `OLDSTRINGIDCLASS_` varchar(255) default NULL,
  `OLDSTRINGIDVALUE_` varchar(255) default NULL,
  `NEWSTRINGIDCLASS_` varchar(255) default NULL,
  `NEWSTRINGIDVALUE_` varchar(255) default NULL,
  `OLDLONGVALUE_` bigint(20) default NULL,
  `OLDSTRINGVALUE_` text,
  `NEWSTRINGVALUE_` text,
  `TASKINSTANCE_` bigint(20) default NULL,
  `TASKACTORID_` varchar(255) default NULL,
  `TASKOLDACTORID_` varchar(255) default NULL,
  `SWIMLANEINSTANCE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_LOG_SOURCENODE` (`SOURCENODE_`),
  KEY `FK_LOG_TOKEN` (`TOKEN_`),
  KEY `FK_LOG_OLDBYTES` (`OLDBYTEARRAY_`),
  KEY `FK_LOG_NEWBYTES` (`NEWBYTEARRAY_`),
  KEY `FK_LOG_CHILDTOKEN` (`CHILD_`),
  KEY `FK_LOG_DESTNODE` (`DESTINATIONNODE_`),
  KEY `FK_LOG_TASKINST` (`TASKINSTANCE_`),
  KEY `FK_LOG_SWIMINST` (`SWIMLANEINSTANCE_`),
  KEY `FK_LOG_PARENT` (`PARENT_`),
  KEY `FK_LOG_NODE` (`NODE_`),
  KEY `FK_LOG_ACTION` (`ACTION_`),
  KEY `FK_LOG_VARINST` (`VARIABLEINSTANCE_`),
  KEY `FK_LOG_TRANSITION` (`TRANSITION_`),
  CONSTRAINT `FK_LOG_ACTION` FOREIGN KEY (`ACTION_`) REFERENCES `jbpm_action` (`ID_`),
  CONSTRAINT `FK_LOG_CHILDTOKEN` FOREIGN KEY (`CHILD_`) REFERENCES `jbpm_token` (`ID_`),
  CONSTRAINT `FK_LOG_DESTNODE` FOREIGN KEY (`DESTINATIONNODE_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_LOG_NEWBYTES` FOREIGN KEY (`NEWBYTEARRAY_`) REFERENCES `jbpm_bytearray` (`ID_`),
  CONSTRAINT `FK_LOG_NODE` FOREIGN KEY (`NODE_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_LOG_OLDBYTES` FOREIGN KEY (`OLDBYTEARRAY_`) REFERENCES `jbpm_bytearray` (`ID_`),
  CONSTRAINT `FK_LOG_PARENT` FOREIGN KEY (`PARENT_`) REFERENCES `jbpm_log` (`ID_`),
  CONSTRAINT `FK_LOG_SOURCENODE` FOREIGN KEY (`SOURCENODE_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_LOG_SWIMINST` FOREIGN KEY (`SWIMLANEINSTANCE_`) REFERENCES `jbpm_swimlaneinstance` (`ID_`),
  CONSTRAINT `FK_LOG_TASKINST` FOREIGN KEY (`TASKINSTANCE_`) REFERENCES `jbpm_taskinstance` (`ID_`),
  CONSTRAINT `FK_LOG_TOKEN` FOREIGN KEY (`TOKEN_`) REFERENCES `jbpm_token` (`ID_`),
  CONSTRAINT `FK_LOG_TRANSITION` FOREIGN KEY (`TRANSITION_`) REFERENCES `jbpm_transition` (`ID_`),
  CONSTRAINT `FK_LOG_VARINST` FOREIGN KEY (`VARIABLEINSTANCE_`) REFERENCES `jbpm_variableinstance` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_log
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm_moduledefinition`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_moduledefinition`;
CREATE TABLE `jbpm_moduledefinition` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `NAME_` text,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `STARTTASK_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_MODDEF_PROCDF` (`PROCESSDEFINITION_`),
  KEY `FK_TSKDEF_START` (`STARTTASK_`),
  KEY `FK_MODDEF_PROCDEF` (`PROCESSDEFINITION_`),
  CONSTRAINT `FK_MODDEF_PROCDEF` FOREIGN KEY (`PROCESSDEFINITION_`) REFERENCES `jbpm_processdefinition` (`ID_`),
  CONSTRAINT `FK_TSKDEF_START` FOREIGN KEY (`STARTTASK_`) REFERENCES `jbpm_task` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_moduledefinition
-- ----------------------------
INSERT INTO `jbpm_moduledefinition` VALUES ('3', 'C', 'org.jbpm.context.def.ContextDefinition', '593702', null);
INSERT INTO `jbpm_moduledefinition` VALUES ('4', 'T', 'org.jbpm.taskmgmt.def.TaskMgmtDefinition', '593702', null);
INSERT INTO `jbpm_moduledefinition` VALUES ('5', 'C', 'org.jbpm.context.def.ContextDefinition', '593703', null);
INSERT INTO `jbpm_moduledefinition` VALUES ('6', 'T', 'org.jbpm.taskmgmt.def.TaskMgmtDefinition', '593703', null);
INSERT INTO `jbpm_moduledefinition` VALUES ('7', 'C', 'org.jbpm.context.def.ContextDefinition', '593704', null);
INSERT INTO `jbpm_moduledefinition` VALUES ('8', 'T', 'org.jbpm.taskmgmt.def.TaskMgmtDefinition', '593704', null);
INSERT INTO `jbpm_moduledefinition` VALUES ('9', 'C', 'org.jbpm.context.def.ContextDefinition', '593705', null);
INSERT INTO `jbpm_moduledefinition` VALUES ('10', 'T', 'org.jbpm.taskmgmt.def.TaskMgmtDefinition', '593705', null);

-- ----------------------------
-- Table structure for `jbpm_moduleinstance`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_moduleinstance`;
CREATE TABLE `jbpm_moduleinstance` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `PROCESSINSTANCE_` bigint(20) default NULL,
  `TASKMGMTDEFINITION_` bigint(20) default NULL,
  `NAME_` varchar(255) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_MODINST_PRINST` (`PROCESSINSTANCE_`),
  KEY `FK_TASKMGTINST_TMD` (`TASKMGMTDEFINITION_`),
  KEY `FK_MODINST_PRCINST` (`PROCESSINSTANCE_`),
  CONSTRAINT `FK_MODINST_PRCINST` FOREIGN KEY (`PROCESSINSTANCE_`) REFERENCES `jbpm_processinstance` (`ID_`),
  CONSTRAINT `FK_TASKMGTINST_TMD` FOREIGN KEY (`TASKMGMTDEFINITION_`) REFERENCES `jbpm_moduledefinition` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_moduleinstance
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm_node`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_node`;
CREATE TABLE `jbpm_node` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `NAME_` varchar(255) default NULL,
  `DESCRIPTION_` text,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `ISASYNC_` bit(1) default NULL,
  `ISASYNCEXCL_` bit(1) default NULL,
  `ACTION_` bigint(20) default NULL,
  `SUPERSTATE_` bigint(20) default NULL,
  `SUBPROCNAME_` varchar(255) default NULL,
  `SUBPROCESSDEFINITION_` bigint(20) default NULL,
  `DECISIONEXPRESSION_` varchar(255) default NULL,
  `DECISIONDELEGATION` bigint(20) default NULL,
  `SCRIPT_` bigint(20) default NULL,
  `SIGNAL_` int(11) default NULL,
  `CREATETASKS_` bit(1) default NULL,
  `ENDTASKS_` bit(1) default NULL,
  `NODECOLLECTIONINDEX_` int(11) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_PSTATE_SBPRCDEF` (`SUBPROCESSDEFINITION_`),
  KEY `IDX_NODE_SUPRSTATE` (`SUPERSTATE_`),
  KEY `IDX_NODE_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `IDX_NODE_ACTION` (`ACTION_`),
  KEY `FK_PROCST_SBPRCDEF` (`SUBPROCESSDEFINITION_`),
  KEY `FK_NODE_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `FK_NODE_SCRIPT` (`SCRIPT_`),
  KEY `FK_NODE_ACTION` (`ACTION_`),
  KEY `FK_DECISION_DELEG` (`DECISIONDELEGATION`),
  KEY `FK_NODE_SUPERSTATE` (`SUPERSTATE_`),
  CONSTRAINT `FK_DECISION_DELEG` FOREIGN KEY (`DECISIONDELEGATION`) REFERENCES `jbpm_delegation` (`ID_`),
  CONSTRAINT `FK_NODE_ACTION` FOREIGN KEY (`ACTION_`) REFERENCES `jbpm_action` (`ID_`),
  CONSTRAINT `FK_NODE_PROCDEF` FOREIGN KEY (`PROCESSDEFINITION_`) REFERENCES `jbpm_processdefinition` (`ID_`),
  CONSTRAINT `FK_NODE_SCRIPT` FOREIGN KEY (`SCRIPT_`) REFERENCES `jbpm_action` (`ID_`),
  CONSTRAINT `FK_NODE_SUPERSTATE` FOREIGN KEY (`SUPERSTATE_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_PROCST_SBPRCDEF` FOREIGN KEY (`SUBPROCESSDEFINITION_`) REFERENCES `jbpm_processdefinition` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_node
-- ----------------------------
INSERT INTO `jbpm_node` VALUES ('3', 'R', 'Start', null, '593702', '', '', null, null, null, null, null, null, null, null, null, null, '0');
INSERT INTO `jbpm_node` VALUES ('4', 'K', '组长审批', null, '593702', '', '', null, null, null, null, null, null, null, '4', '', '', '1');
INSERT INTO `jbpm_node` VALUES ('5', 'K', '项目经理审批', null, '593702', '', '', null, null, null, null, null, null, null, '4', '', '', '2');
INSERT INTO `jbpm_node` VALUES ('6', 'K', '人事甲审批', null, '593702', '', '', null, null, null, null, null, null, null, '4', '', '', '3');
INSERT INTO `jbpm_node` VALUES ('7', 'K', '人事乙审批', null, '593702', '', '', null, null, null, null, null, null, null, '4', '', '', '4');
INSERT INTO `jbpm_node` VALUES ('8', 'K', '人事丙审批', null, '593702', '', '', null, null, null, null, null, null, null, '4', '', '', '5');
INSERT INTO `jbpm_node` VALUES ('9', 'K', '总经理审批', null, '593702', '', '', null, null, null, null, null, null, null, '4', '', '', '6');
INSERT INTO `jbpm_node` VALUES ('10', 'F', 'Fork1', null, '593702', '', '', null, null, null, null, null, null, null, null, null, null, '7');
INSERT INTO `jbpm_node` VALUES ('11', 'J', 'Join1', null, '593702', '', '', null, null, null, null, null, null, null, null, null, null, '8');
INSERT INTO `jbpm_node` VALUES ('12', 'E', 'end', null, '593702', '', '', null, null, null, null, null, null, null, null, null, null, '9');
INSERT INTO `jbpm_node` VALUES ('13', 'R', 'Start', null, '593703', '', '', null, null, null, null, null, null, null, null, null, null, '0');
INSERT INTO `jbpm_node` VALUES ('14', 'K', '测试节点1', null, '593703', '', '', null, null, null, null, null, null, null, '4', '', '', '1');
INSERT INTO `jbpm_node` VALUES ('15', 'K', '测试节点2', null, '593703', '', '', null, null, null, null, null, null, null, '4', '', '', '2');
INSERT INTO `jbpm_node` VALUES ('16', 'K', '测试节点3', null, '593703', '', '', null, null, null, null, null, null, null, '4', '', '', '3');
INSERT INTO `jbpm_node` VALUES ('17', 'E', 'end', null, '593703', '', '', null, null, null, null, null, null, null, null, null, null, '4');
INSERT INTO `jbpm_node` VALUES ('18', 'R', 'Start', null, '593704', '', '', null, null, null, null, null, null, null, null, null, null, '0');
INSERT INTO `jbpm_node` VALUES ('19', 'K', '测试节点1', null, '593704', '', '', null, null, null, null, null, null, null, '4', '', '', '1');
INSERT INTO `jbpm_node` VALUES ('20', 'K', '测试节点2', null, '593704', '', '', null, null, null, null, null, null, null, '4', '', '', '2');
INSERT INTO `jbpm_node` VALUES ('21', 'K', '测试节点3', null, '593704', '', '', null, null, null, null, null, null, null, '4', '', '', '3');
INSERT INTO `jbpm_node` VALUES ('22', 'E', 'end', null, '593704', '', '', null, null, null, null, null, null, null, null, null, null, '4');

-- ----------------------------
-- Table structure for `jbpm_pooledactor`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_pooledactor`;
CREATE TABLE `jbpm_pooledactor` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `VERSION_` int(11) NOT NULL,
  `ACTORID_` varchar(255) default NULL,
  `SWIMLANEINSTANCE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_PLDACTR_ACTID` (`ACTORID_`),
  KEY `IDX_TSKINST_SWLANE` (`SWIMLANEINSTANCE_`),
  KEY `FK_POOLEDACTOR_SLI` (`SWIMLANEINSTANCE_`),
  CONSTRAINT `FK_POOLEDACTOR_SLI` FOREIGN KEY (`SWIMLANEINSTANCE_`) REFERENCES `jbpm_swimlaneinstance` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_pooledactor
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm_processdefinition`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_processdefinition`;
CREATE TABLE `jbpm_processdefinition` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `NAME_` varchar(255) default NULL,
  `DESCRIPTION_` text,
  `VERSION_` int(11) default NULL,
  `ISTERMINATIONIMPLICIT_` bit(1) default NULL,
  `STARTSTATE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_PROCDEF_STRTST` (`STARTSTATE_`),
  KEY `FK_PROCDEF_STRTSTA` (`STARTSTATE_`),
  CONSTRAINT `FK_PROCDEF_STRTSTA` FOREIGN KEY (`STARTSTATE_`) REFERENCES `jbpm_node` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_processdefinition
-- ----------------------------
INSERT INTO `jbpm_processdefinition` VALUES ('53770', 'P', 'MyWorkflow_test', null, '1', '', null);
INSERT INTO `jbpm_processdefinition` VALUES ('593613', 'P', '请假示例流程', '../../../common/requestForLeaveList!add.action', '13', '', null);
INSERT INTO `jbpm_processdefinition` VALUES ('593701', 'P', 'MyWorkflow', null, '29', '', null);
INSERT INTO `jbpm_processdefinition` VALUES ('593702', 'P', '请假示例流程', '../../../common/requestForLeaveList!add.action', '14', '', '3');
INSERT INTO `jbpm_processdefinition` VALUES ('593703', 'P', 'MyWorkflow', '', '30', '', '13');
INSERT INTO `jbpm_processdefinition` VALUES ('593704', 'P', 'MyWorkflow', '', '31', '', '18');
INSERT INTO `jbpm_processdefinition` VALUES ('593705', 'P', 'MyWorkflow', '', '32', '', null);

-- ----------------------------
-- Table structure for `jbpm_processinstance`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_processinstance`;
CREATE TABLE `jbpm_processinstance` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `VERSION_` int(11) NOT NULL,
  `KEY_` varchar(255) default NULL,
  `START_` datetime default NULL,
  `END_` datetime default NULL,
  `ISSUSPENDED_` bit(1) default NULL,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `ROOTTOKEN_` bigint(20) default NULL,
  `SUPERPROCESSTOKEN_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  UNIQUE KEY `KEY_` (`KEY_`,`PROCESSDEFINITION_`),
  KEY `IDX_PROCIN_ROOTTK` (`ROOTTOKEN_`),
  KEY `IDX_PROCIN_SPROCTK` (`SUPERPROCESSTOKEN_`),
  KEY `IDX_PROCIN_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `FK_PROCIN_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `FK_PROCIN_ROOTTKN` (`ROOTTOKEN_`),
  KEY `FK_PROCIN_SPROCTKN` (`SUPERPROCESSTOKEN_`),
  CONSTRAINT `FK_PROCIN_PROCDEF` FOREIGN KEY (`PROCESSDEFINITION_`) REFERENCES `jbpm_processdefinition` (`ID_`),
  CONSTRAINT `FK_PROCIN_ROOTTKN` FOREIGN KEY (`ROOTTOKEN_`) REFERENCES `jbpm_token` (`ID_`),
  CONSTRAINT `FK_PROCIN_SPROCTKN` FOREIGN KEY (`SUPERPROCESSTOKEN_`) REFERENCES `jbpm_token` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_processinstance
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm_runtimeaction`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_runtimeaction`;
CREATE TABLE `jbpm_runtimeaction` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `VERSION_` int(11) NOT NULL,
  `EVENTTYPE_` varchar(255) default NULL,
  `TYPE_` char(1) default NULL,
  `GRAPHELEMENT_` bigint(20) default NULL,
  `PROCESSINSTANCE_` bigint(20) default NULL,
  `ACTION_` bigint(20) default NULL,
  `PROCESSINSTANCEINDEX_` int(11) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_RTACTN_PRCINST` (`PROCESSINSTANCE_`),
  KEY `IDX_RTACTN_ACTION` (`ACTION_`),
  KEY `FK_RTACTN_PROCINST` (`PROCESSINSTANCE_`),
  KEY `FK_RTACTN_ACTION` (`ACTION_`),
  CONSTRAINT `FK_RTACTN_ACTION` FOREIGN KEY (`ACTION_`) REFERENCES `jbpm_action` (`ID_`),
  CONSTRAINT `FK_RTACTN_PROCINST` FOREIGN KEY (`PROCESSINSTANCE_`) REFERENCES `jbpm_processinstance` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_runtimeaction
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm_swimlane`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_swimlane`;
CREATE TABLE `jbpm_swimlane` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `NAME_` varchar(255) default NULL,
  `ACTORIDEXPRESSION_` varchar(255) default NULL,
  `POOLEDACTORSEXPRESSION_` varchar(255) default NULL,
  `ASSIGNMENTDELEGATION_` bigint(20) default NULL,
  `TASKMGMTDEFINITION_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_SWL_ASSDEL` (`ASSIGNMENTDELEGATION_`),
  KEY `FK_SWL_TSKMGMTDEF` (`TASKMGMTDEFINITION_`),
  CONSTRAINT `FK_SWL_ASSDEL` FOREIGN KEY (`ASSIGNMENTDELEGATION_`) REFERENCES `jbpm_delegation` (`ID_`),
  CONSTRAINT `FK_SWL_TSKMGMTDEF` FOREIGN KEY (`TASKMGMTDEFINITION_`) REFERENCES `jbpm_moduledefinition` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_swimlane
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm_swimlaneinstance`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_swimlaneinstance`;
CREATE TABLE `jbpm_swimlaneinstance` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `VERSION_` int(11) NOT NULL,
  `NAME_` varchar(255) default NULL,
  `ACTORID_` varchar(255) default NULL,
  `SWIMLANE_` bigint(20) default NULL,
  `TASKMGMTINSTANCE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_SWIMLINST_SL` (`SWIMLANE_`),
  KEY `FK_SWIMLANEINST_TM` (`TASKMGMTINSTANCE_`),
  KEY `FK_SWIMLANEINST_SL` (`SWIMLANE_`),
  CONSTRAINT `FK_SWIMLANEINST_SL` FOREIGN KEY (`SWIMLANE_`) REFERENCES `jbpm_swimlane` (`ID_`),
  CONSTRAINT `FK_SWIMLANEINST_TM` FOREIGN KEY (`TASKMGMTINSTANCE_`) REFERENCES `jbpm_moduleinstance` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_swimlaneinstance
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm_task`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_task`;
CREATE TABLE `jbpm_task` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `NAME_` varchar(255) default NULL,
  `DESCRIPTION_` text,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `ISBLOCKING_` bit(1) default NULL,
  `ISSIGNALLING_` bit(1) default NULL,
  `CONDITION_` varchar(255) default NULL,
  `DUEDATE_` varchar(255) default NULL,
  `PRIORITY_` int(11) default NULL,
  `ACTORIDEXPRESSION_` varchar(255) default NULL,
  `POOLEDACTORSEXPRESSION_` varchar(255) default NULL,
  `TASKMGMTDEFINITION_` bigint(20) default NULL,
  `TASKNODE_` bigint(20) default NULL,
  `STARTSTATE_` bigint(20) default NULL,
  `ASSIGNMENTDELEGATION_` bigint(20) default NULL,
  `SWIMLANE_` bigint(20) default NULL,
  `TASKCONTROLLER_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_TASK_TSKNODE` (`TASKNODE_`),
  KEY `IDX_TASK_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `IDX_TASK_TASKMGTDF` (`TASKMGMTDEFINITION_`),
  KEY `FK_TSK_TSKCTRL` (`TASKCONTROLLER_`),
  KEY `FK_TASK_ASSDEL` (`ASSIGNMENTDELEGATION_`),
  KEY `FK_TASK_TASKNODE` (`TASKNODE_`),
  KEY `FK_TASK_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `FK_TASK_STARTST` (`STARTSTATE_`),
  KEY `FK_TASK_TASKMGTDEF` (`TASKMGMTDEFINITION_`),
  KEY `FK_TASK_SWIMLANE` (`SWIMLANE_`),
  CONSTRAINT `FK_TASK_ASSDEL` FOREIGN KEY (`ASSIGNMENTDELEGATION_`) REFERENCES `jbpm_delegation` (`ID_`),
  CONSTRAINT `FK_TASK_PROCDEF` FOREIGN KEY (`PROCESSDEFINITION_`) REFERENCES `jbpm_processdefinition` (`ID_`),
  CONSTRAINT `FK_TASK_STARTST` FOREIGN KEY (`STARTSTATE_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_TASK_SWIMLANE` FOREIGN KEY (`SWIMLANE_`) REFERENCES `jbpm_swimlane` (`ID_`),
  CONSTRAINT `FK_TASK_TASKMGTDEF` FOREIGN KEY (`TASKMGMTDEFINITION_`) REFERENCES `jbpm_moduledefinition` (`ID_`),
  CONSTRAINT `FK_TASK_TASKNODE` FOREIGN KEY (`TASKNODE_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_TSK_TSKCTRL` FOREIGN KEY (`TASKCONTROLLER_`) REFERENCES `jbpm_taskcontroller` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_task
-- ----------------------------
INSERT INTO `jbpm_task` VALUES ('1', '组长审批', null, '593702', '', '', null, null, '3', null, null, '4', '4', null, '1', null, null);
INSERT INTO `jbpm_task` VALUES ('2', '项目经理审批', null, '593702', '', '', null, null, '3', null, null, '4', '5', null, '4', null, null);
INSERT INTO `jbpm_task` VALUES ('3', '人事甲审批', null, '593702', '', '', null, null, '3', null, null, '4', '6', null, '7', null, null);
INSERT INTO `jbpm_task` VALUES ('4', '人事乙审批', null, '593702', '', '', null, null, '3', null, null, '4', '7', null, '10', null, null);
INSERT INTO `jbpm_task` VALUES ('5', '人事丙审批', null, '593702', '', '', null, null, '3', null, null, '4', '8', null, '13', null, null);
INSERT INTO `jbpm_task` VALUES ('6', '总经理审批', null, '593702', '', '', null, null, '3', null, null, '4', '9', null, '16', null, null);
INSERT INTO `jbpm_task` VALUES ('7', '测试节点1', null, '593703', '', '', null, null, '3', null, null, '6', '14', null, '19', null, null);
INSERT INTO `jbpm_task` VALUES ('8', '测试节点2', null, '593703', '', '', null, null, '3', null, null, '6', '15', null, '22', null, null);
INSERT INTO `jbpm_task` VALUES ('9', '测试节点3', null, '593703', '', '', null, null, '3', null, null, '6', '16', null, '25', null, null);
INSERT INTO `jbpm_task` VALUES ('10', '测试节点1', null, '593704', '', '', null, null, '3', null, null, '8', '19', null, '28', null, null);
INSERT INTO `jbpm_task` VALUES ('11', '测试节点2', null, '593704', '', '', null, null, '3', null, null, '8', '20', null, '31', null, null);
INSERT INTO `jbpm_task` VALUES ('12', '测试节点3', null, '593704', '', '', null, null, '3', null, null, '8', '21', null, '34', null, null);

-- ----------------------------
-- Table structure for `jbpm_taskactorpool`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_taskactorpool`;
CREATE TABLE `jbpm_taskactorpool` (
  `TASKINSTANCE_` bigint(20) NOT NULL,
  `POOLEDACTOR_` bigint(20) NOT NULL,
  PRIMARY KEY  (`TASKINSTANCE_`,`POOLEDACTOR_`),
  KEY `FK_TSKACTPOL_PLACT` (`POOLEDACTOR_`),
  KEY `FK_TASKACTPL_TSKI` (`TASKINSTANCE_`),
  CONSTRAINT `FK_TASKACTPL_TSKI` FOREIGN KEY (`TASKINSTANCE_`) REFERENCES `jbpm_taskinstance` (`ID_`),
  CONSTRAINT `FK_TSKACTPOL_PLACT` FOREIGN KEY (`POOLEDACTOR_`) REFERENCES `jbpm_pooledactor` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_taskactorpool
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm_taskcontroller`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_taskcontroller`;
CREATE TABLE `jbpm_taskcontroller` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `TASKCONTROLLERDELEGATION_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_TSKCTRL_DELEG` (`TASKCONTROLLERDELEGATION_`),
  CONSTRAINT `FK_TSKCTRL_DELEG` FOREIGN KEY (`TASKCONTROLLERDELEGATION_`) REFERENCES `jbpm_delegation` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_taskcontroller
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm_taskinstance`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_taskinstance`;
CREATE TABLE `jbpm_taskinstance` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `NAME_` varchar(255) default NULL,
  `DESCRIPTION_` text,
  `ACTORID_` varchar(255) default NULL,
  `CREATE_` datetime default NULL,
  `START_` datetime default NULL,
  `END_` datetime default NULL,
  `DUEDATE_` datetime default NULL,
  `PRIORITY_` int(11) default NULL,
  `ISCANCELLED_` bit(1) default NULL,
  `ISSUSPENDED_` bit(1) default NULL,
  `ISOPEN_` bit(1) default NULL,
  `ISSIGNALLING_` bit(1) default NULL,
  `ISBLOCKING_` bit(1) default NULL,
  `TASK_` bigint(20) default NULL,
  `TOKEN_` bigint(20) default NULL,
  `PROCINST_` bigint(20) default NULL,
  `SWIMLANINSTANCE_` bigint(20) default NULL,
  `TASKMGMTINSTANCE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_TASKINST_TOKN` (`TOKEN_`),
  KEY `IDX_TASKINST_TSK` (`TASK_`,`PROCINST_`),
  KEY `IDX_TSKINST_TMINST` (`TASKMGMTINSTANCE_`),
  KEY `IDX_TSKINST_SLINST` (`SWIMLANINSTANCE_`),
  KEY `IDX_TASK_ACTORID` (`ACTORID_`),
  KEY `FK_TSKINS_PRCINS` (`PROCINST_`),
  KEY `FK_TASKINST_TMINST` (`TASKMGMTINSTANCE_`),
  KEY `FK_TASKINST_TOKEN` (`TOKEN_`),
  KEY `FK_TASKINST_SLINST` (`SWIMLANINSTANCE_`),
  KEY `FK_TASKINST_TASK` (`TASK_`),
  CONSTRAINT `FK_TASKINST_SLINST` FOREIGN KEY (`SWIMLANINSTANCE_`) REFERENCES `jbpm_swimlaneinstance` (`ID_`),
  CONSTRAINT `FK_TASKINST_TASK` FOREIGN KEY (`TASK_`) REFERENCES `jbpm_task` (`ID_`),
  CONSTRAINT `FK_TASKINST_TMINST` FOREIGN KEY (`TASKMGMTINSTANCE_`) REFERENCES `jbpm_moduleinstance` (`ID_`),
  CONSTRAINT `FK_TASKINST_TOKEN` FOREIGN KEY (`TOKEN_`) REFERENCES `jbpm_token` (`ID_`),
  CONSTRAINT `FK_TSKINS_PRCINS` FOREIGN KEY (`PROCINST_`) REFERENCES `jbpm_processinstance` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_taskinstance
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm_taskvariable`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_taskvariable`;
CREATE TABLE `jbpm_taskvariable` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `TASKNODEID_` bigint(20) NOT NULL,
  `TASKID_` bigint(20) NOT NULL,
  `NAME_` varchar(64) default NULL,
  `VALUE_` varchar(512) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_TASKVARIABLE_NODE` (`TASKNODEID_`),
  KEY `FK_TASKVARIABLE_TASK` (`TASKID_`),
  CONSTRAINT `FK_TASKVARIABLE_NODE` FOREIGN KEY (`TASKNODEID_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_TASKVARIABLE_TASK` FOREIGN KEY (`TASKID_`) REFERENCES `jbpm_task` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_taskvariable
-- ----------------------------
INSERT INTO `jbpm_taskvariable` VALUES ('3', '4', '1', 'workflow_task_url', '${bravoHome}/common/requestForLeaveList!view.action?id=${formValue.id?c}');
INSERT INTO `jbpm_taskvariable` VALUES ('4', '5', '2', 'workflow_task_url', '${bravoHome}/common/requestForLeaveList!view.action?id=${formValue.id?c}');
INSERT INTO `jbpm_taskvariable` VALUES ('5', '6', '3', 'workflow_task_url', '${bravoHome}/common/requestForLeaveList!view.action?id=${formValue.id?c}');
INSERT INTO `jbpm_taskvariable` VALUES ('6', '7', '4', 'workflow_task_url', '${bravoHome}/common/requestForLeaveList!view.action?id=${formValue.id?c}');
INSERT INTO `jbpm_taskvariable` VALUES ('7', '8', '5', 'workflow_task_url', '${bravoHome}/common/requestForLeaveList!view.action?id=${formValue.id?c}');
INSERT INTO `jbpm_taskvariable` VALUES ('8', '9', '6', 'workflow_task_url', '${bravoHome}/common/requestForLeaveList!view.action?id=${formValue.id?c}');
INSERT INTO `jbpm_taskvariable` VALUES ('9', '14', '7', 'sadas', 'asdasd');
INSERT INTO `jbpm_taskvariable` VALUES ('10', '14', '7', 'workflow_task_url', '111');
INSERT INTO `jbpm_taskvariable` VALUES ('11', '15', '8', 'workflow_task_url', '435435');
INSERT INTO `jbpm_taskvariable` VALUES ('12', '16', '9', 'workflow_task_url', '23423423');
INSERT INTO `jbpm_taskvariable` VALUES ('13', '19', '10', 'sadas', 'asdasd');
INSERT INTO `jbpm_taskvariable` VALUES ('14', '19', '10', 'workflow_task_url', '111');
INSERT INTO `jbpm_taskvariable` VALUES ('15', '20', '11', 'workflow_task_url', '435435');
INSERT INTO `jbpm_taskvariable` VALUES ('16', '21', '12', 'workflow_task_url', '23423423');

-- ----------------------------
-- Table structure for `jbpm_token`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_token`;
CREATE TABLE `jbpm_token` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `VERSION_` int(11) NOT NULL,
  `NAME_` varchar(255) default NULL,
  `START_` datetime default NULL,
  `END_` datetime default NULL,
  `NODEENTER_` datetime default NULL,
  `NEXTLOGINDEX_` int(11) default NULL,
  `ISABLETOREACTIVATEPARENT_` bit(1) default NULL,
  `ISTERMINATIONIMPLICIT_` bit(1) default NULL,
  `ISSUSPENDED_` bit(1) default NULL,
  `LOCK_` varchar(255) default NULL,
  `NODE_` bigint(20) default NULL,
  `PROCESSINSTANCE_` bigint(20) default NULL,
  `PARENT_` bigint(20) default NULL,
  `SUBPROCESSINSTANCE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_TOKEN_PROCIN` (`PROCESSINSTANCE_`),
  KEY `IDX_TOKEN_SUBPI` (`SUBPROCESSINSTANCE_`),
  KEY `IDX_TOKEN_NODE` (`NODE_`),
  KEY `IDX_TOKEN_PARENT` (`PARENT_`),
  KEY `FK_TOKEN_PARENT` (`PARENT_`),
  KEY `FK_TOKEN_NODE` (`NODE_`),
  KEY `FK_TOKEN_PROCINST` (`PROCESSINSTANCE_`),
  KEY `FK_TOKEN_SUBPI` (`SUBPROCESSINSTANCE_`),
  CONSTRAINT `FK_TOKEN_NODE` FOREIGN KEY (`NODE_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_TOKEN_PARENT` FOREIGN KEY (`PARENT_`) REFERENCES `jbpm_token` (`ID_`),
  CONSTRAINT `FK_TOKEN_PROCINST` FOREIGN KEY (`PROCESSINSTANCE_`) REFERENCES `jbpm_processinstance` (`ID_`),
  CONSTRAINT `FK_TOKEN_SUBPI` FOREIGN KEY (`SUBPROCESSINSTANCE_`) REFERENCES `jbpm_processinstance` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_token
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm_tokenvariablemap`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_tokenvariablemap`;
CREATE TABLE `jbpm_tokenvariablemap` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `VERSION_` int(11) NOT NULL,
  `TOKEN_` bigint(20) default NULL,
  `CONTEXTINSTANCE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_TKVARMAP_CTXT` (`CONTEXTINSTANCE_`),
  KEY `IDX_TKVVARMP_TOKEN` (`TOKEN_`),
  KEY `FK_TKVARMAP_CTXT` (`CONTEXTINSTANCE_`),
  KEY `FK_TKVARMAP_TOKEN` (`TOKEN_`),
  CONSTRAINT `FK_TKVARMAP_CTXT` FOREIGN KEY (`CONTEXTINSTANCE_`) REFERENCES `jbpm_moduleinstance` (`ID_`),
  CONSTRAINT `FK_TKVARMAP_TOKEN` FOREIGN KEY (`TOKEN_`) REFERENCES `jbpm_token` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_tokenvariablemap
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm_transition`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_transition`;
CREATE TABLE `jbpm_transition` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `NAME_` varchar(255) default NULL,
  `DESCRIPTION_` text,
  `PROCESSDEFINITION_` bigint(20) default NULL,
  `FROM_` bigint(20) default NULL,
  `TO_` bigint(20) default NULL,
  `CONDITION_` varchar(255) default NULL,
  `FROMINDEX_` int(11) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_TRANSIT_TO` (`TO_`),
  KEY `IDX_TRANSIT_FROM` (`FROM_`),
  KEY `IDX_TRANS_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `FK_TRANSITION_TO` (`TO_`),
  KEY `FK_TRANS_PROCDEF` (`PROCESSDEFINITION_`),
  KEY `FK_TRANSITION_FROM` (`FROM_`),
  CONSTRAINT `FK_TRANSITION_FROM` FOREIGN KEY (`FROM_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_TRANSITION_TO` FOREIGN KEY (`TO_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_TRANS_PROCDEF` FOREIGN KEY (`PROCESSDEFINITION_`) REFERENCES `jbpm_processdefinition` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_transition
-- ----------------------------
INSERT INTO `jbpm_transition` VALUES ('2', '组长审批', null, '593702', '3', '4', null, '0');
INSERT INTO `jbpm_transition` VALUES ('3', '项目经理审批', null, '593702', '4', '5', null, '0');
INSERT INTO `jbpm_transition` VALUES ('4', '人事审批', null, '593702', '5', '10', null, '0');
INSERT INTO `jbpm_transition` VALUES ('5', '', null, '593702', '6', '11', null, '0');
INSERT INTO `jbpm_transition` VALUES ('6', '', null, '593702', '7', '11', null, '0');
INSERT INTO `jbpm_transition` VALUES ('7', '', null, '593702', '8', '11', null, '0');
INSERT INTO `jbpm_transition` VALUES ('8', '审批结束', null, '593702', '9', '12', null, '0');
INSERT INTO `jbpm_transition` VALUES ('9', '人事甲审批', null, '593702', '10', '6', null, '0');
INSERT INTO `jbpm_transition` VALUES ('10', '人事乙审批', null, '593702', '10', '7', null, '1');
INSERT INTO `jbpm_transition` VALUES ('11', '人事丙审批', null, '593702', '10', '8', null, '2');
INSERT INTO `jbpm_transition` VALUES ('12', '总经理审批', null, '593702', '11', '9', null, '0');
INSERT INTO `jbpm_transition` VALUES ('13', '', null, '593703', '13', '14', null, '0');
INSERT INTO `jbpm_transition` VALUES ('14', '', null, '593703', '14', '15', null, '0');
INSERT INTO `jbpm_transition` VALUES ('15', '', null, '593703', '15', '16', null, '0');
INSERT INTO `jbpm_transition` VALUES ('16', '', null, '593703', '16', '17', null, '0');
INSERT INTO `jbpm_transition` VALUES ('17', '', null, '593704', '18', '19', null, '0');
INSERT INTO `jbpm_transition` VALUES ('18', '', null, '593704', '19', '20', null, '0');
INSERT INTO `jbpm_transition` VALUES ('19', '', null, '593704', '20', '21', null, '0');
INSERT INTO `jbpm_transition` VALUES ('20', '', null, '593704', '21', '22', null, '0');

-- ----------------------------
-- Table structure for `jbpm_variableaccess`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_variableaccess`;
CREATE TABLE `jbpm_variableaccess` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `VARIABLENAME_` varchar(255) default NULL,
  `ACCESS_` varchar(255) default NULL,
  `MAPPEDNAME_` varchar(255) default NULL,
  `PROCESSSTATE_` bigint(20) default NULL,
  `TASKCONTROLLER_` bigint(20) default NULL,
  `INDEX_` int(11) default NULL,
  `SCRIPT_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `FK_VARACC_TSKCTRL` (`TASKCONTROLLER_`),
  KEY `FK_VARACC_SCRIPT` (`SCRIPT_`),
  KEY `FK_VARACC_PROCST` (`PROCESSSTATE_`),
  CONSTRAINT `FK_VARACC_PROCST` FOREIGN KEY (`PROCESSSTATE_`) REFERENCES `jbpm_node` (`ID_`),
  CONSTRAINT `FK_VARACC_SCRIPT` FOREIGN KEY (`SCRIPT_`) REFERENCES `jbpm_action` (`ID_`),
  CONSTRAINT `FK_VARACC_TSKCTRL` FOREIGN KEY (`TASKCONTROLLER_`) REFERENCES `jbpm_taskcontroller` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_variableaccess
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm_variableinstance`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm_variableinstance`;
CREATE TABLE `jbpm_variableinstance` (
  `ID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `NAME_` varchar(255) default NULL,
  `CONVERTER_` char(1) default NULL,
  `TOKEN_` bigint(20) default NULL,
  `TOKENVARIABLEMAP_` bigint(20) default NULL,
  `PROCESSINSTANCE_` bigint(20) default NULL,
  `BYTEARRAYVALUE_` bigint(20) default NULL,
  `DATEVALUE_` datetime default NULL,
  `DOUBLEVALUE_` double default NULL,
  `LONGIDCLASS_` varchar(255) default NULL,
  `LONGVALUE_` bigint(20) default NULL,
  `STRINGIDCLASS_` varchar(255) default NULL,
  `STRINGVALUE_` varchar(255) default NULL,
  `TASKINSTANCE_` bigint(20) default NULL,
  PRIMARY KEY  (`ID_`),
  KEY `IDX_VARINST_TKVARMP` (`TOKENVARIABLEMAP_`),
  KEY `IDX_VARINST_PRCINS` (`PROCESSINSTANCE_`),
  KEY `IDX_VARINST_TK` (`TOKEN_`),
  KEY `FK_VARINST_TK` (`TOKEN_`),
  KEY `FK_VARINST_TKVARMP` (`TOKENVARIABLEMAP_`),
  KEY `FK_VARINST_PRCINST` (`PROCESSINSTANCE_`),
  KEY `FK_VAR_TSKINST` (`TASKINSTANCE_`),
  KEY `FK_BYTEINST_ARRAY` (`BYTEARRAYVALUE_`),
  CONSTRAINT `FK_BYTEINST_ARRAY` FOREIGN KEY (`BYTEARRAYVALUE_`) REFERENCES `jbpm_bytearray` (`ID_`),
  CONSTRAINT `FK_VARINST_PRCINST` FOREIGN KEY (`PROCESSINSTANCE_`) REFERENCES `jbpm_processinstance` (`ID_`),
  CONSTRAINT `FK_VARINST_TK` FOREIGN KEY (`TOKEN_`) REFERENCES `jbpm_token` (`ID_`),
  CONSTRAINT `FK_VARINST_TKVARMP` FOREIGN KEY (`TOKENVARIABLEMAP_`) REFERENCES `jbpm_tokenvariablemap` (`ID_`),
  CONSTRAINT `FK_VAR_TSKINST` FOREIGN KEY (`TASKINSTANCE_`) REFERENCES `jbpm_taskinstance` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm_variableinstance
-- ----------------------------
