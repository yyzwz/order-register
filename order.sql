-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.26 - MySQL Community Server - GPL
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 order 的数据库结构
DROP DATABASE IF EXISTS `order`;
CREATE DATABASE IF NOT EXISTS `order` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_croatian_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `order`;

-- 导出  表 order.t_department 结构
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE IF NOT EXISTS `t_department` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `parent_id` bigint unsigned DEFAULT NULL,
  `sort_order` decimal(10,2) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `is_parent` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 正在导出表  order.t_department 的数据：~15 rows (大约)
DELETE FROM `t_department`;
/*!40000 ALTER TABLE `t_department` DISABLE KEYS */;
INSERT INTO `t_department` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `parent_id`, `sort_order`, `status`, `title`, `is_parent`) VALUES
	(40652270295060480, '', '2018-08-11 18:29:57', 0, '', '2018-08-12 18:45:01', 0, 3.00, 0, '人事部', b'1'),
	(40652338142121984, NULL, '2018-08-11 18:30:13', 0, NULL, '2018-08-11 18:30:13', 40652270295060480, 1.00, 0, '游客', b'0'),
	(40681289119961088, '', '2018-08-11 20:25:16', 0, '', '2018-08-11 22:47:48', 40652270295060480, 2.00, 0, 'VIP', b'0'),
	(1464487288363945985, 'admin', '2021-11-27 14:52:33', 0, 'admin', '2021-11-27 14:53:29', 0, 1.00, 0, '人力资源部', b'1'),
	(1464487332064399361, 'admin', '2021-11-27 14:52:43', 0, 'admin', '2021-11-27 14:53:53', 0, 2.00, 0, '行政综合部', b'1'),
	(1464487379074158593, 'admin', '2021-11-27 14:52:55', 0, 'admin', '2021-11-27 14:54:19', 0, 3.00, 0, '设计研发部', b'1'),
	(1464487406328745985, 'admin', '2021-11-27 14:53:01', 0, 'admin', '2021-11-27 14:53:01', 0, 4.00, 0, '财务部', b'0'),
	(1464487432169852929, 'admin', '2021-11-27 14:53:07', 0, 'admin', '2021-11-27 14:53:07', 0, 5.00, 0, '综合发展部', b'0'),
	(1464487524662644737, 'admin', '2021-11-27 14:53:29', 0, 'admin', '2021-11-27 14:53:41', 1464487288363945985, 1.00, 0, 'HRBP部', b'0'),
	(1464487559647334401, 'admin', '2021-11-27 14:53:38', 0, 'admin', '2021-11-27 14:53:38', 1464487288363945985, 2.00, 0, '招聘部', b'0'),
	(1464487623933431809, 'admin', '2021-11-27 14:53:53', 0, 'admin', '2021-11-27 14:53:53', 1464487332064399361, 1.00, 0, '安保部', b'0'),
	(1464487656678363137, 'admin', '2021-11-27 14:54:01', 0, 'admin', '2021-11-27 14:54:01', 1464487332064399361, 2.00, 0, '保洁部', b'0'),
	(1464487691780493313, 'admin', '2021-11-27 14:54:09', 0, 'admin', '2021-11-27 14:54:09', 1464487332064399361, 3.00, 0, '设备维修部', b'0'),
	(1464487733895499777, 'admin', '2021-11-27 14:54:19', 0, 'admin', '2021-11-27 14:54:19', 1464487379074158593, 1.00, 0, '视频剪辑部', b'0'),
	(1464487807572643841, 'admin', '2021-11-27 14:54:37', 0, 'admin', '2021-11-27 14:54:37', 1464487379074158593, 2.00, 0, '产品应用部', b'0');
/*!40000 ALTER TABLE `t_department` ENABLE KEYS */;

-- 导出  表 order.t_department_header 结构
DROP TABLE IF EXISTS `t_department_header`;
CREATE TABLE IF NOT EXISTS `t_department_header` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `department_id` bigint unsigned DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL,
  `user_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 正在导出表  order.t_department_header 的数据：~0 rows (大约)
DELETE FROM `t_department_header`;
/*!40000 ALTER TABLE `t_department_header` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_department_header` ENABLE KEYS */;

-- 导出  表 order.t_dict 结构
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE IF NOT EXISTS `t_dict` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `sort_order` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 正在导出表  order.t_dict 的数据：~6 rows (大约)
DELETE FROM `t_dict`;
/*!40000 ALTER TABLE `t_dict` DISABLE KEYS */;
INSERT INTO `t_dict` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `description`, `title`, `type`, `sort_order`) VALUES
	(75135930788220928, 'admin', '2018-11-14 22:15:43', 0, 'admin', '2018-11-27 01:39:06', '', '性别', 'sex', 0.00),
	(75383353938808832, 'admin', '2018-11-15 14:38:53', 0, 'admin', '2018-11-27 01:39:15', '', '消息类型', 'message_type', 1.00),
	(75388696739713024, 'admin', '2018-11-15 15:00:07', 0, 'admin', '2018-11-27 01:39:22', '', '按钮权限类型', 'permission_type', 2.00),
	(81843858882695168, 'admin', '2018-12-03 10:30:38', 0, 'admin', '2018-12-03 10:30:49', '', '优先级', 'priority', 5.00),
	(1473944891246645249, 'admin', '2021-12-23 17:13:41', 0, 'admin', '2021-12-23 17:13:41', '', '学历', 'education', 1.00),
	(1473946082609336321, 'admin', '2021-12-23 17:18:25', 0, 'admin', '2021-12-23 17:18:25', '', '职称', 'postLevel', 2.00);
/*!40000 ALTER TABLE `t_dict` ENABLE KEYS */;

-- 导出  表 order.t_dict_data 结构
DROP TABLE IF EXISTS `t_dict_data`;
CREATE TABLE IF NOT EXISTS `t_dict_data` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `dict_id` bigint unsigned DEFAULT NULL,
  `sort_order` decimal(10,2) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sort_order` (`sort_order`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 正在导出表  order.t_dict_data 的数据：~35 rows (大约)
DELETE FROM `t_dict_data`;
/*!40000 ALTER TABLE `t_dict_data` DISABLE KEYS */;
INSERT INTO `t_dict_data` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `description`, `dict_id`, `sort_order`, `status`, `title`, `value`) VALUES
	(75158227712479232, 'admin', '2018-11-14 23:44:19', 0, 'admin', '2019-04-01 23:53:13', '', 75135930788220928, 0.00, 0, '男', '男'),
	(75159254272577536, 'admin', '2018-11-14 23:48:24', 0, 'admin', '2019-04-01 23:53:17', '', 75135930788220928, 1.00, 0, '女', '女'),
	(75159898425397248, 'admin', '2018-11-14 23:50:57', 0, 'admin', '2019-04-01 23:53:22', '', 75135930788220928, 2.00, -1, '保密', '保密'),
	(75385648017575936, 'admin', '2018-11-15 14:48:00', 0, 'admin', '2019-04-02 00:10:36', '', 75383353938808832, 0.00, 0, '系统公告', '系统公告'),
	(75385706913992704, 'admin', '2018-11-15 14:48:14', 0, 'admin', '2019-04-02 00:10:32', '', 75383353938808832, 1.00, 0, '提醒', '提醒'),
	(75385774274514944, 'admin', '2018-11-15 14:48:30', 0, 'admin', '2019-04-02 00:10:28', '', 75383353938808832, 2.00, 0, '私信', '私信'),
	(75390787835138048, 'admin', '2018-11-15 15:08:26', 0, 'admin', '2018-11-15 15:08:26', '', 75388696739713024, 0.00, 0, '查看操作(view)', 'view'),
	(75390886501945344, 'admin', '2018-11-15 15:08:49', 0, 'admin', '2018-11-15 15:08:57', '', 75388696739713024, 1.00, 0, '添加操作(add)', 'add'),
	(75390993939042304, 'admin', '2018-11-15 15:09:15', 0, 'admin', '2018-11-15 15:09:15', '', 75388696739713024, 2.00, 0, '编辑操作(edit)', 'edit'),
	(75391067532300288, 'admin', '2018-11-15 15:09:32', 0, 'admin', '2018-11-15 15:09:32', '', 75388696739713024, 3.00, 0, '删除操作(delete)', 'delete'),
	(75391126902673408, 'admin', '2018-11-15 15:09:46', 0, 'admin', '2018-11-15 15:09:46', '', 75388696739713024, 4.00, 0, '清空操作(clear)', 'clear'),
	(75391192883269632, 'admin', '2018-11-15 15:10:02', 0, 'admin', '2018-11-15 15:10:02', '', 75388696739713024, 5.00, 0, '启用操作(enable)', 'enable'),
	(75391251024711680, 'admin', '2018-11-15 15:10:16', 0, 'admin', '2018-11-15 15:10:16', '', 75388696739713024, 6.00, 0, '禁用操作(disable)', 'disable'),
	(75391297124306944, 'admin', '2018-11-15 15:10:27', 0, 'admin', '2018-11-15 15:10:27', '', 75388696739713024, 7.00, 0, '搜索操作(search)', 'search'),
	(75391343379091456, 'admin', '2018-11-15 15:10:38', 0, 'admin', '2018-11-15 15:10:38', '', 75388696739713024, 8.00, 0, '上传文件(upload)', 'upload'),
	(75391407526776832, 'admin', '2018-11-15 15:10:53', 0, 'admin', '2018-11-15 15:10:53', '', 75388696739713024, 9.00, 0, '导出操作(output)', 'output'),
	(75391475042488320, 'admin', '2018-11-15 15:11:09', 0, 'admin', '2018-11-15 15:11:09', '', 75388696739713024, 10.00, 0, '导入操作(input)', 'input'),
	(75391522182270976, 'admin', '2018-11-15 15:11:21', 0, 'admin', '2018-11-15 15:11:21', '', 75388696739713024, 11.00, 0, '分配权限(editPerm)', 'editPerm'),
	(75391576364290048, 'admin', '2018-11-15 15:11:34', 0, 'admin', '2018-11-15 15:11:34', '', 75388696739713024, 12.00, 0, '设为默认(setDefault)', 'setDefault'),
	(75391798033256448, 'admin', '2018-11-15 15:12:26', 0, 'admin', '2018-11-15 15:12:26', '', 75388696739713024, 13.00, 0, '其他操作(other)', 'other'),
	(81843987719131136, 'admin', '2018-12-03 10:31:08', 0, 'admin', '2018-12-03 10:31:08', '', 81843858882695168, 0.00, 0, '普通', '0'),
	(81844044015079424, 'admin', '2018-12-03 10:31:22', 0, 'admin', '2018-12-03 10:31:22', '', 81843858882695168, 1.00, 0, '重要', '1'),
	(81844100705292288, 'admin', '2018-12-03 10:31:35', 0, 'admin', '2018-12-03 10:31:35', '', 81843858882695168, 2.00, 0, '紧急', '2'),
	(1473945054337961985, 'admin', '2021-12-23 17:14:20', 0, 'admin', '2021-12-23 17:14:38', '', 1473944891246645249, 1.00, 0, '博士研究生', '博士研究生'),
	(1473945077247250432, 'admin', '2021-12-23 17:14:26', 0, 'admin', '2021-12-23 17:14:44', '', 1473944891246645249, 2.00, 0, '硕士研究生', '硕士研究生'),
	(1473945192867434496, 'admin', '2021-12-23 17:14:53', 0, 'admin', '2021-12-23 17:14:53', '', 1473944891246645249, 3.00, 0, '本科学士', '本科学士'),
	(1473945217332809729, 'admin', '2021-12-23 17:14:59', 0, 'admin', '2021-12-23 17:14:59', '', 1473944891246645249, 4.00, 0, '大专', '大专'),
	(1473945248639094784, 'admin', '2021-12-23 17:15:07', 0, 'admin', '2021-12-23 17:15:07', '', 1473944891246645249, 5.00, 0, '普通高中', '普通高中'),
	(1473945284613640193, 'admin', '2021-12-23 17:15:15', 0, 'admin', '2021-12-23 17:15:15', '', 1473944891246645249, 6.00, 0, '职业高中及以下', '职业高中及以下'),
	(1473946166856126465, 'admin', '2021-12-23 17:18:45', 0, 'admin', '2021-12-23 17:18:45', '', 1473946082609336321, 1.00, 0, '主任医师', '主任医师'),
	(1473946191564771329, 'admin', '2021-12-23 17:18:51', 0, 'admin', '2021-12-23 17:18:51', '', 1473946082609336321, 2.00, 0, '副主任医师', '副主任医师'),
	(1473946219842768896, 'admin', '2021-12-23 17:18:58', 0, 'admin', '2021-12-23 17:18:58', '', 1473946082609336321, 3.00, 0, '主治医师', '主治医师'),
	(1473946258426171392, 'admin', '2021-12-23 17:19:07', 0, 'admin', '2021-12-23 17:19:07', '', 1473946082609336321, 4.00, 0, '住院医师', '住院医师'),
	(1473946289728262145, 'admin', '2021-12-23 17:19:15', 0, 'admin', '2021-12-23 17:19:15', '', 1473946082609336321, 5.00, 0, '助理医师', '助理医师'),
	(1474566040326377473, 'admin', '2021-12-25 10:21:55', 0, 'admin', '2021-12-25 10:21:55', '', 75388696739713024, 11.00, 0, '置顶', 'setDynamicNewTop');
/*!40000 ALTER TABLE `t_dict_data` ENABLE KEYS */;

-- 导出  表 order.t_doctor 结构
DROP TABLE IF EXISTS `t_doctor`;
CREATE TABLE IF NOT EXISTS `t_doctor` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci NOT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `about` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `age` int NOT NULL,
  `doctor_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `education` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `major` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `post_level` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `subject_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `subject_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `university` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `work_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `order_money` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_croatian_ci;

-- 正在导出表  order.t_doctor 的数据：~10 rows (大约)
DELETE FROM `t_doctor`;
/*!40000 ALTER TABLE `t_doctor` DISABLE KEYS */;
INSERT INTO `t_doctor` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `about`, `age`, `doctor_name`, `education`, `major`, `post_level`, `subject_id`, `subject_name`, `university`, `work_date`, `photo`, `order_money`) VALUES
	('1474207434229813248', 'admin', '2021-12-24 10:36:56.000000', 0, 'admin', '2021-12-24 10:49:23.919000', '科副主任，主任医师。1987年毕业于浙江医科大学口腔医学专业，曾赴浙一医院进修，北京BITC口腔种植中心深造。担任中华口腔医学会口腔修复及口腔种植会员，浙江省口腔修复委员会委员，绍兴市口腔质控中心委员，绍兴市口腔中西医结合副主任委员。 ', 33, '王海花', '本科学士', '口腔医学专业', '主任医师', '1473916965973266433', '口腔科', '浙江医科大学', '2012-11-14', 'https://www.312000.net/Upfile/Face/face_69.JPG', 15.00),
	('1474209128925761537', 'admin', '2021-12-24 10:43:40.000000', 0, 'admin', '2021-12-24 10:49:11.230000', '主任医师。1984年参加工作，2002年毕业于温州医科大学，本科学历，1996年赴浙一医院进修口腔颌面外科，2016年赴上海参加Straumann种植高级培训。', 38, '阮土耿', '本科学士', '口腔医学', '主任医师', '1473916965973266433', '口腔科', '温州医科大学', '2015-11-30', 'https://www.312000.net/Upfile/Face/face_68.JPG', 15.00),
	('1474209351492308993', 'admin', '2021-12-24 10:44:33.000000', 0, 'admin', '2021-12-24 10:48:53.544000', '科主任，绍兴市医学重点学科带头人，主任医师，硕士生导师。1992年毕业于浙江医科大学，2004年获浙江大学医学院眼科学硕士学位。浙江省医师协会眼科分会常务委员、浙江省超声医学工程学会眼科专业委员会常务委员、浙江省医学会眼科分会委员、浙江省角膜病诊治技术指导中心委员会委员、绍兴市医学会眼科专业委员会主任委员、绍兴市医师协会眼科医师分会会长、绍兴市中西医结合学会眼科专业委员会副主任委员。曾在德国弗莱堡大学附属圣文森特医院、英国皇家自由医院研修。', 31, '陈伟', '硕士研究生', '眼科学', '主任医师', '1473917353262714880', '眼科', '浙江医科大学', '2014-12-02', 'https://www.312000.net/Upfile/Face/face_86.JPG', 15.00),
	('1474209523051925505', 'admin', '2021-12-24 10:45:14.000000', 0, 'admin', '2021-12-24 10:48:38.268000', '副主任医师，2005年获温州医科大学眼视光学士学位，2009年获温州医科大学眼科硕士学位，从事眼科临床工作十多年，有丰富的临床经验，曾赴上海复旦大学附属眼耳鼻喉科医院进修学习眼科各亚专业疾病的诊断和治疗。', 41, '王琼', '本科学士', '眼视光', '副主任医师', '1473917353262714880', '眼科', '温州医科大学', '2016-12-07', 'https://www.312000.net/upload/dept/5066af47-a4fc-4416-94f0-643c5ccaf21a/image/20200507/390cbc01-c1f4-40e9-a4a4-78a497cdbdc9.jpg', 15.00),
	('1474210972439810049', 'admin', '2021-12-24 10:51:00.020000', 0, NULL, NULL, '科室学科带头人。大学本科，主任医师，中国中西结合学会鼻-鼻窦炎专家委员会委员，浙江省医师协会耳鼻咽喉科医学专委会委员，绍兴市医学会耳鼻咽喉科专业委员会副主任委员，曾先后在中山医科大第三附属医院、浙江大学附属第二医院、中国医学科学院肿瘤医院进修学习，先后发表国内外论文10余篇，主持参与市级课题研究数项。', 44, '刘平', '本科学士', '肿瘤专业', '主任医师', '1473918021696360449', '耳鼻咽喉科', '浙江大学', '2015-12-08', 'https://www.312000.net/Upfile/Face/face_89.JPG', 15.00),
	('1474211316829917185', 'admin', '2021-12-24 10:52:22.119000', 0, NULL, NULL, '副院长兼骨科学科带头人，绍兴文理学院医学院副院长，教授、主任医师，浙江大学医学院博士生导师。1994年毕业于浙江医科大学，2009年获澳大利亚西澳大学博士学位。中华医学会骨科分会青年委员，浙江省医学会骨科分会常委、浙江省医师协会骨科分会常委，省康复学会脊柱脊髓损伤委员会常委，绍兴市医学会骨科分会主任委员，绍兴市医师协会骨科分会会长。', 39, '钱宇', '博士研究生', '骨科学', '主任医师', '1473914763531653120', '骨科', '浙江大学医学院', '2014-12-10', 'https://www.312000.net/Upfile/Face/face_119.JPG', 15.00),
	('1474211559671730177', 'admin', '2021-12-24 10:53:20.011000', 0, NULL, NULL, '浙江省康复医学会脊柱微创学组委员，绍兴市医学会骨科分会脊柱组委员。先后赴英国格罗斯特皇家医院，德国纽伦堡大学附属圣玛利安医院，荷兰埃文斯大学访问学习，主要从事各种颈肩痛腰腿痛及四肢骨折创伤的诊断治疗，尤其擅长椎间孔镜下腰椎间盘突出症的微创治疗以及老年性骨质疏松引起的胸腰椎骨折和髋部骨折的微创化手术治疗。', 36, '何磊', '硕士研究生', '骨科', '副主任医师', '1473914763531653120', '骨科', '温州医科大学', '2015-12-01', 'https://www.312000.net/upload/dept/e58f5a7c-820b-4527-814b-d35b6d9fd7b4/image/20180117/48d2473b-2d11-46bf-a069-e1600292d806.jpg', 15.00),
	('1474211803641810945', 'admin', '2021-12-24 10:54:18.179000', 0, NULL, NULL, '主任医师。1985年毕业于上海第二军医大学，获学士学位，2002年赴上海长海医院进修消化内镜治疗及ERCP术。现任绍兴市消化专业委员会委员。', 45, '张凯杰', '本科学士', '消化内科', '主任医师', '1473917781656342528', '消化内科', '上海第二军医大学', '2013-12-09', 'https://www.312000.net/Upfile/Face/face_52.JPG', 15.00),
	('1474212105635893249', 'admin', '2021-12-24 10:55:30.183000', 0, NULL, NULL, '中国中西医结合学会疼痛专业委员会癌痛专委会委员；长期从事临床消化科工作，曾先后赴上海长海医院、浙江大学附属第一医院消化科进修；', 37, '秦月花', '硕士研究生', '消化内科医学', '主任医师', '1473917781656342528', '消化内科', '上海长海大学', '2016-12-07', 'https://www.312000.net/Upfile/Face/face_48.JPG', 15.00),
	('1474212301312757761', 'admin', '2021-12-24 10:56:16.835000', 0, NULL, NULL, '副主任医师，毕业于浙江大学医学院。', 38, '罗文文', '本科学士', '医学', '副主任医师', '1473917781656342528', '消化内科', '浙江大学', '2015-12-09', 'https://www.312000.net/Upfile/Face/face_249.JPG', 15.00);
/*!40000 ALTER TABLE `t_doctor` ENABLE KEYS */;

-- 导出  表 order.t_doctor_scheduling 结构
DROP TABLE IF EXISTS `t_doctor_scheduling`;
CREATE TABLE IF NOT EXISTS `t_doctor_scheduling` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci NOT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `date` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `doctor_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `doctor_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `number` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `step` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `order_flag` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_croatian_ci;

-- 正在导出表  order.t_doctor_scheduling 的数据：~15 rows (大约)
DELETE FROM `t_doctor_scheduling`;
/*!40000 ALTER TABLE `t_doctor_scheduling` DISABLE KEYS */;
INSERT INTO `t_doctor_scheduling` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `date`, `doctor_id`, `doctor_name`, `number`, `step`, `order_flag`) VALUES
	('1474252424544915456', 'admin', '2021-12-24 13:35:42.965000', 0, 'admin', '2021-12-24 15:39:27.698000', '2021-12-24', '1474212301312757761', '罗文文', '1', '0', 1),
	('1474252424599441408', 'admin', '2021-12-24 13:35:42.968000', 0, 'admin', '2021-12-25 08:56:39.541000', '2021-12-24', '1474212301312757761', '罗文文', '2', '0', 1),
	('1474252424612024320', 'admin', '2021-12-24 13:35:42.972000', 0, NULL, NULL, '2021-12-24', '1474212301312757761', '罗文文', '3', '0', 0),
	('1474252424624607232', 'admin', '2021-12-24 13:35:42.974000', 0, 'admin', '2022-01-18 11:53:34.730000', '2021-12-24', '1474212301312757761', '罗文文', '4', '0', 1),
	('1474252424632995840', 'admin', '2021-12-24 13:35:42.977000', 0, NULL, NULL, '2021-12-24', '1474212301312757761', '罗文文', '5', '0', 0),
	('1474252424645578752', 'admin', '2021-12-24 13:35:42.979000', 0, NULL, NULL, '2021-12-24', '1474212301312757761', '罗文文', '6', '0', 0),
	('1474252424653967360', 'admin', '2021-12-24 13:35:42.982000', 0, NULL, NULL, '2021-12-24', '1474212301312757761', '罗文文', '7', '0', 0),
	('1474252424666550272', 'admin', '2021-12-24 13:35:42.984000', 0, NULL, NULL, '2021-12-24', '1474212301312757761', '罗文文', '8', '0', 0),
	('1474252424679133184', 'admin', '2021-12-24 13:35:42.987000', 0, NULL, NULL, '2021-12-24', '1474212301312757761', '罗文文', '9', '0', 0),
	('1474252424687521792', 'admin', '2021-12-24 13:35:42.990000', 0, NULL, NULL, '2021-12-24', '1474212301312757761', '罗文文', '10', '0', 0),
	('1474274354501128193', 'admin', '2021-12-24 15:02:51.468000', 0, NULL, NULL, '2021-12-24', '1474212105635893249', '秦月花', '1', '1', 0),
	('1474274354534682624', 'admin', '2021-12-24 15:02:51.472000', 0, NULL, NULL, '2021-12-24', '1474212105635893249', '秦月花', '2', '1', 0),
	('1474274354543071232', 'admin', '2021-12-24 15:02:51.474000', 0, NULL, NULL, '2021-12-24', '1474212105635893249', '秦月花', '3', '1', 0),
	('1474274354551459840', 'admin', '2021-12-24 15:02:51.476000', 0, NULL, NULL, '2021-12-24', '1474212105635893249', '秦月花', '4', '1', 0),
	('1474274354559848448', 'admin', '2021-12-24 15:02:51.479000', 0, NULL, NULL, '2021-12-24', '1474212105635893249', '秦月花', '5', '1', 0),
	('1483283178180448256', 'admin', '2022-01-18 11:40:42.426000', 0, NULL, NULL, '2022-01-18', '1474212301312757761', '罗文文', '1', '0', 0),
	('1483283178289500160', 'admin', '2022-01-18 11:40:42.433000', 0, NULL, NULL, '2022-01-18', '1474212301312757761', '罗文文', '2', '0', 0),
	('1483283178314665984', 'admin', '2022-01-18 11:40:42.439000', 0, NULL, NULL, '2022-01-18', '1474212301312757761', '罗文文', '3', '0', 0),
	('1483283178335637504', 'admin', '2022-01-18 11:40:42.444000', 0, NULL, NULL, '2022-01-18', '1474212301312757761', '罗文文', '4', '0', 0),
	('1483283178360803328', 'admin', '2022-01-18 11:40:42.450000', 0, NULL, NULL, '2022-01-18', '1474212301312757761', '罗文文', '5', '0', 0);
/*!40000 ALTER TABLE `t_doctor_scheduling` ENABLE KEYS */;

-- 导出  表 order.t_file 结构
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE IF NOT EXISTS `t_file` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `size` bigint unsigned DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `f_key` varchar(255) DEFAULT NULL,
  `location` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 正在导出表  order.t_file 的数据：~0 rows (大约)
DELETE FROM `t_file`;
/*!40000 ALTER TABLE `t_file` DISABLE KEYS */;
INSERT INTO `t_file` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `name`, `size`, `type`, `url`, `f_key`, `location`) VALUES
	(1464483838750232576, 'admin', '2021-11-27 14:38:51', 0, 'admin', '2021-11-27 14:38:51', '微信截图_20211127143841.png', 21552, 'image/png', 'C:\\oa-file/20211127/1b74e525e0544a788045f69426d6b093.png', '1b74e525e0544a788045f69426d6b093.png', 0);
/*!40000 ALTER TABLE `t_file` ENABLE KEYS */;

-- 导出  表 order.t_hospital_news 结构
DROP TABLE IF EXISTS `t_hospital_news`;
CREATE TABLE IF NOT EXISTS `t_hospital_news` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci NOT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `department_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `department_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `is_public` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `is_top` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `n_sort` int DEFAULT NULL,
  `new_describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `new_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `nursing_organization_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `nursing_organization_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `period_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `relate_service_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `relate_service_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_croatian_ci;

-- 正在导出表  order.t_hospital_news 的数据：~10 rows (大约)
DELETE FROM `t_hospital_news`;
/*!40000 ALTER TABLE `t_hospital_news` DISABLE KEYS */;
INSERT INTO `t_hospital_news` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `department_id`, `department_title`, `is_public`, `is_top`, `n_sort`, `new_describe`, `new_title`, `nursing_organization_id`, `nursing_organization_name`, `period_time`, `photo`, `relate_service_id`, `relate_service_name`) VALUES
	('1471384104523141121', 'lc_lgj', '2021-12-16 15:38:02.181000', 0, NULL, NULL, '', '', 'yes', '', 300, '内科-动态咨询', '内科-动态咨询', '1451359273400537089', '鹿城区', '2021-12-08 00:00:00 - 2022-01-22 00:00:00', 'https://data.wzcable.com:9004/app/file/view/1464488676275916800', '', ''),
	('1471393441362612225', 'test_lo', '2021-12-16 16:15:08.265000', 0, NULL, NULL, '', '', 'yes', '', 100, '口腔科-动态', '口腔科-动态', '1434525343615684609', '菱藕社区', '2021-12-06 00:00:00 - 2022-01-20 00:00:00', 'https://data.wzcable.com:9004/app/file/view/1464490047234510849', '', ''),
	('1471398754098745345', 'test_nf', '2021-12-16 16:36:14.949000', 0, 'admin', '2021-12-24 23:32:36.806000', '', '', 'yes', 'no', 100, '外科-动态', '外科-动态', '1470021528195305472', '松台街道_第三方养老机构', '2021-12-07 00:00:00 - 2022-01-21 00:00:00', 'https://data.wzcable.com:9004/app/file/view/1464500645229891584', '', ''),
	('1471442629454598144', 'test_gg', '2021-12-16 19:30:35.623000', 0, 'test_gg', '2021-12-17 08:51:30.365000', '', '', 'yes', 'yes', 100, '眼科-动态', '眼科-动态', '1434525517255675905', '桂柑社区', '2021-12-16 00:00:00 - 2022-01-21 00:00:00', 'https://data.wzcable.com:9004/app/file/view/1465143010978304000', '', ''),
	('1471645556723027968', 'test_qnf', '2021-12-17 08:56:57.000000', 0, 'test_nf', '2021-12-17 11:24:00.081000', '', '', 'yes', 'yes', 100, '骨科-动态', '骨科-动态', '1434525684193169409', '庆年坊社区', '2021-12-17 00:00:00 - 2022-01-21 00:00:00', 'https://data.wzcable.com:9004/app/file/view/1471682488416997377', '', ''),
	('1471645650692214785', 'test_qnf', '2021-12-17 08:57:19.000000', 0, 'admin', '2021-12-18 14:01:14.417000', '', '', 'yes', 'yes', 100, '口腔预防', '口腔预防', '1434525684193169409', '庆年坊社区', '2021-12-06 00:00:00 - 2022-01-28 00:00:00', 'https://data.wzcable.com:9004/app/file/view/1471682314084945920', '', ''),
	('1471723157265190912', 'test_lo_lrst', '2021-12-17 14:05:18.659000', 0, 'test_lo_lrst', '2021-12-17 14:08:15.025000', '', '', 'yes', 'yes', 100, '消化内科-动态', '消化内科-动态', '1471689451628531713', '菱藕社区-老人食堂', '2021-12-17 00:00:00 - 2022-01-21 00:00:00', 'https://data.wzcable.com:9004/app/file/view/1471723151699349505', '', ''),
	('1472087542533853185', 'test_laj', '2021-12-18 14:13:14.887000', 0, 'admin', '2021-12-24 23:32:41.337000', '', '', 'yes', 'yes', 100, '神经外科-动态', '神经外科-动态', '1472086307860779009', '鹿城区老干局', '2021-12-18 00:00:00 - 2022-01-29 00:00:00', 'https://data.wzcable.com:9004/app/file/view/1472087485180940289', '', ''),
	('1474551902455861249', 'qmm', '2021-12-25 09:25:44.089000', 0, NULL, NULL, '', '', 'yes', '', 300, '<p><strong>11111</strong></p><p><strong><img src="http://img.baidu.com/hi/jx2/j_0017.gif"/></strong></p>', '111', '40322777781112832', '浙江省', '2021-12-25 00:00:00 - 2022-01-21 00:00:00', '', '', ''),
	('1474565620946309120', 'admin', '2021-12-25 10:20:14.000000', 0, 'admin', '2021-12-25 10:20:31.703000', NULL, NULL, 'yes', '', NULL, '<p><strong>测试啊啊啊</strong></p>', '测试新闻', NULL, NULL, '2021-12-17 00:00:00 - 2022-01-14 00:00:00', 'https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png', '', '');
/*!40000 ALTER TABLE `t_hospital_news` ENABLE KEYS */;

-- 导出  表 order.t_hospital_order 结构
DROP TABLE IF EXISTS `t_hospital_order`;
CREATE TABLE IF NOT EXISTS `t_hospital_order` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci NOT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `date_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `doctor_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `doctor_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `number` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `order_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `step` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `money_data` decimal(19,2) DEFAULT NULL,
  `money_flag` int NOT NULL,
  `status` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_croatian_ci;

-- 正在导出表  order.t_hospital_order 的数据：~2 rows (大约)
DELETE FROM `t_hospital_order`;
/*!40000 ALTER TABLE `t_hospital_order` DISABLE KEYS */;
INSERT INTO `t_hospital_order` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `date_time`, `user_id`, `user_name`, `doctor_id`, `doctor_name`, `number`, `order_id`, `step`, `money_data`, `money_flag`, `status`) VALUES
	('1474283566186762240', 'admin', '2021-12-24 15:39:27.706000', 0, 'admin', '2021-12-24 16:58:34.086000', '2021-12-24', '682265633886208', '管理员', '1474212301312757761', '罗文文', '1', '1474252424544915456', '0', 15.00, 1, 1),
	('1474544585471561728', 'admin', '2021-12-25 08:56:39.559000', 0, NULL, NULL, '2021-12-24', '682265633886208', '管理员', '1474212301312757761', '罗文文', '2', '1474252424599441408', '0', 15.00, 0, 0),
	('1483286417563062272', 'admin', '2022-01-18 11:53:34.749000', 0, NULL, NULL, '2021-12-24', '682265633886208', '管理员', '1474212301312757761', '罗文文', '4', '1474252424624607232', '0', 15.00, 0, 0);
/*!40000 ALTER TABLE `t_hospital_order` ENABLE KEYS */;

-- 导出  表 order.t_hospital_subject 结构
DROP TABLE IF EXISTS `t_hospital_subject`;
CREATE TABLE IF NOT EXISTS `t_hospital_subject` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci NOT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `about` varchar(1024) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `duty_doctor` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `start_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `sub_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `sub_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `sub_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `super_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_croatian_ci;

-- 正在导出表  order.t_hospital_subject 的数据：~20 rows (大约)
DELETE FROM `t_hospital_subject`;
/*!40000 ALTER TABLE `t_hospital_subject` DISABLE KEYS */;
INSERT INTO `t_hospital_subject` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `about`, `duty_doctor`, `remark`, `start_date`, `sub_code`, `sub_name`, `sub_number`, `super_number`) VALUES
	('1473914763531653120', 'admin', '2021-12-23 15:13:58.000000', 0, 'admin', '2021-12-23 15:17:24.510000', '集预防、治疗、康复、保健及教学于一体，致力于治疗骨科常见病、多发病及各种骨科疑难杂症；重视手术治疗、药物治疗、物理治疗、康复锻炼等治疗方法的规范化组合；针对不同个体、不同年龄、不同疾病、不同病程、不同要求，选择个性化治疗方案，充分体现治疗的个体化，其诊疗水平在全市同行业中处于领先地位。', '陈雪荣', '近5年来，本学科承担省、市科研项目 7 项；获省、市科技进步奖 7 项；在专业杂志发表论文45篇，其中SCI 3篇、中华级杂志8 篇。', '2021-12-08', 'A01', '骨科', '', ''),
	('1473916291751481344', 'admin', '2021-12-23 15:20:02.715000', 0, NULL, NULL, '成立1989年，当时仅有麻醉医师8人，以椎管内麻醉和神经阻滞为主，全麻比例大约10%左右，2000年以后，麻醉科先后引进了纤维支气管镜、多台Datex麻醉机、自体血回收机等。至2010年，全科有麻醉医师28人，其中主任医师1人，副主任医师5人，主治医师14人。有硕士研究生4人，在读硕士研究生8人，麻醉护士6人，手术床位21张，麻醉恢复室床位12张。全麻比例超过70%。学科在疑难危重病人麻醉管理以及血液保护方面在地区甚至省内处于领先地位。在疼痛治疗方面如肩周炎、腰腿痛以及神经病理性疼痛如带状疱疹的治疗取得了一定的疗效', '陈宗华', '疼痛治疗、危重病人的麻醉及心脏大血管手术的麻醉。', '2019-12-03', 'A02', '麻醉科', NULL, NULL),
	('1473916468134547456', 'admin', '2021-12-23 15:20:44.708000', 0, NULL, NULL, '始建于20世纪60年代，80年代初组建呼吸科，经过多年不断的发展，成为绍兴市重点学科，浙江省浙东地区区域专病中心，中国肺癌防治联盟肺结节诊治基层分中心，国家GMP临床药物试验认证机构呼吸专业组，是一个以呼吸病临床医学为主，医疗、教学、科研相结合的专业性学科。开设呼吸专家门诊、专科门诊，哮喘、慢性咳嗽、肺结节、介入、戒烟等专病门诊。学科目前拥有两个病区，70张床位，主任医师五名，博士研究生一名，硕士研究生10余名。在绍兴最早开展及推广标准化的临床肺功能测定、呼吸危重症诊治、阻塞性睡眠呼吸障碍诊治，率先开展肺癌的早期诊断及多学科综合诊治，慢性气道疾病及肺部感染性疾病的规范化诊治，纤维支气管镜、胸腔镜的介入诊疗。拥有规模齐全的肺功能室，呼吸内镜室，睡眠呼吸监测室，配置高端的奥林巴斯电子支气管镜，超声电子支气管镜，冷冻仪，虚拟导航，内科胸腔镜及各种镜下介入诊治设备，配备综合肺功能诊断系统，纳库伦呼出气NO、CO检测仪，呼吸机，高流量吸氧机以及智能化的多导睡眠监测仪，可全面开展呼吸专科领域的各种诊断、治疗技术。', '沈巨信', '开展了多项基础研究和临床工作，完成省卫生厅重点推广项目及绍兴市多个科研项目，绍兴市科学技术进步一、二、三等奖多次。在国内外各级学术杂志发表学术论文数十篇，近年来已在中华、国家级等核心期刊发表论文40余篇，获得省、市自然科学优秀论文奖一、二等奖多次。', '2017-12-06', 'A04', '呼吸内科', NULL, NULL),
	('1473916641472548864', 'admin', '2021-12-23 15:21:26.035000', 0, NULL, NULL, '绍兴市人民医院放射科是绍兴地区实力最雄厚的放射影像中心，是集医疗、教学、科研于一体的专业性临床学科，省市医学重点学科，市级医学领先学科，是绍兴市医学影像会诊中心所在科室。放射科是浙江大学和绍兴文理学院硕士研究生培养基地，浙江大学医学院、温州医科大学、绍兴文理学院医学院的临床教育基地，也是国家级住院医师规范化培训放射专业基地和核医学基地，承担医学影像专业的临床教学、培训任务。', '赵振华', '开展了多项基础研究和临床工作，近年来已在SCI、中华医学系列杂志等核心期刊发表论文79篇，获得省、市自然科学优秀论文奖多项。承担、完成卫生部、绍兴市科技局科研项目11项，省卫生厅、绍兴市重点推广项目3项目，获浙江省医药卫生科技创新二、三等奖多次', '2016-12-07', 'A05', '放射科', NULL, NULL),
	('1473916784284405760', 'admin', '2021-12-23 15:22:00.084000', 0, NULL, NULL, '输血科是集供血、教学及科研为一体的医技科室，同时具备业务和管理科室的特点，主要承担储血、配血、发血、临床输血指导、培训、咨询与会诊、参与用血不良事件调查、开展无偿献血的宣传与教育等各项医疗任务，保证临床医疗用血安全。科室拥有全自动血型分析仪、智能化全自动溶浆机、各型各类离心机、水浴箱和多台血液贮存专用冰箱、血小板恒温箱、温度监控系统、专用冷链取血箱、输血信息管理系统等现代化医疗仪器设备。开展疑难血型鉴定及交叉配血、不规则抗体筛查和鉴定、血小板抗体检测、Rh分型检测、新生儿溶血病检测等，推广术中自身输血，全面保障了临床安全、有效、科学用血。', '杨国灿', '科室现有在职专业技术人员12人，其中高级职称4人，具硕士学历3人，多人曾在上级医院进修，硕士研究生导师1名。每年接受相关专业规培生、进修生、实习生20多名；主办国家、省、市级继教项目多项。所有人员均具备输血、检验、医疗等专业知识，并接受输血相关理论和实践技能的培训和考核。', '2014-12-03', 'A06', '输血科', NULL, NULL),
	('1473916965973266433', 'admin', '2021-12-23 15:22:43.402000', 0, NULL, NULL, '我院1946年即设立牙科，至1957年改名为口腔科，是一个以口腔临床医学为主，医疗、教学、科研相结合的专业性学科。目前拥有牙科综合治疗椅22台，并配置有口腔CT、数字牙科X线机、口腔全景机、牙科种植系统、超声骨刀、根管显微镜、微波治疗仪、冷光美白仪、牙周治疗仪、全自动根管治疗仪、水激光治疗仪等先进设备。门诊诊疗环境整洁，设有独立的清洗消毒室和口腔种植手术室。科室开展各种口腔内科、口腔颌面外科、口腔修复、正畸、种植及各种牙周病、粘膜病的诊治，年门诊病人约5万人次，住院病人约500人次。现有口腔临床医师17名，其中主任医师4名，副主任医师4名，硕士9名。', '王海花', '开展多项基础研究和临床研究，已在省级及以上核心期刊发表论文多篇，完成绍兴市多个科研项目。', '2014-12-17', 'A07', '口腔科', NULL, NULL),
	('1473917353262714880', 'admin', '2021-12-23 15:24:15.752000', 0, NULL, NULL, '成立于20世纪40年代，80年代初起独立设置眼科，是一个以眼科临床为主，医疗、教学、科研相结合的专业性学科。为绍兴市首批临床医学重点学科。目前年门急诊病人5万余人次，住院病人千余人次，收治白内障、青光眼、视网膜脱离、斜视、上睑下垂、眼外伤、眼眶肿瘤、慢性泪囊炎、泪道阻塞、视神经疾病、眼底血管性疾病等眼病，开展白内障超声乳化摘除术、青光眼复合式小梁切除术，青光眼引流装置植入术、青光眼白内障联合术、23G微创玻璃体视网膜手术、眼眶肿瘤摘除术、眼眶骨折修复术、鼻内窥镜下鼻腔泪囊造口术、泪道置管术、泪小管断裂吻合术、眼睑成形术、上睑下垂矫正术、斜视矫正术、活动性义眼座植入术、羊膜移植术、角膜缘干细胞移植术、板层角膜移植术、重睑成形术、眼袋去除术、内眦赘皮矫正术、提眉术等手术。目前临床医师14名；其中主任医师2名，副主任医师6名，硕士研究生4名。拥有进口白内障超声乳化机、前、后段玻璃体切割机、眼科手术显微镜，眼底激光机，YAG激光机，眼科OCT、超声生物显微镜（UBM）、眼底血管造影机，免散瞳眼底照相机，综合验光仪，全自动视野仪，眼电生理检查仪，眼科A/B超，角膜内皮细胞检查仪，角膜测厚仪，角膜地形图仪，非接触眼压计等众多先进的医疗仪器，为学科开展各项业务打下了坚实的基础。', '陈伟', '开展了多项基础研究和临床工作，近年来在中华级、国家级及省级学术期刊发表论文数十篇，承担及完成省卫生厅、市级科技项目9项；获浙江省医药卫生科技创新奖、绍兴市科学技术奖共5项，开展新技术新项目十余项。 科内有1人未浙江省医学会眼科学分会委员及候任主委，2人为绍兴市医学会眼科学分会委员。有1人为温州医科大学和浙江省中医药大学硕士生导师。 ', '2015-12-16', 'A08', '眼科', NULL, NULL),
	('1473917476944351232', 'admin', '2021-12-23 15:24:45.000000', 0, 'admin', '2021-12-23 15:24:57.138000', '儿科为绍兴市临床医学重点学科，浙江省儿科住院医师规范化培训基地。学科团队业务水平高，技术力量雄厚，队伍结构合理，共有医生34名，护士14名，其中高级职称18名，硕士研究生导师5名，现任科主任为姚欢迎主任医师，任浙江省医学会儿科学分会委员，绍兴市医学会儿科学分会候任主委，绍兴市医师协会儿科学分会副会长，浙江省医学会儿科学分会急救学组委员。\n\n学科开放床位51张，年门急诊20余万人次，出院患儿4000余人次。目前有小儿呼吸、小儿消化、小儿内分泌、小儿神经、小儿风湿免疫、生长发育等亚专业。小儿内镜技术作为本科特色，目前小儿支气管镜无论数量及技术在省内达到领先，小儿胃镜是绍兴市内各医院唯一能开展检查及其治疗的技术；儿童哮喘作为全国儿童哮喘协作组成员，规范的诊疗及最现代的小儿雾化中心，为绍兴及周围县市的患儿提供优质的服务；其他如小儿癫痫的合理用药、儿童矮小症和早发育等诊疗技术达国内先进水平。近5年学科承担省、市级课题7项，在各类专业杂志发表论文20余篇，其中SCI 1篇，获得市级科技进步奖3项。是国家级规培基地、绍兴文理学院2.5+2.5临床教学基地。', '陈啸洪', '我科各个专业学组有的放矢地开展了多项基础研究和临床工作，取得了大量可喜的成果。近年来已在《中华流行病学杂志》、《中华临床感染病杂志》、《中华医学杂志》、《中华全科医学》等核心期刊及《临床儿科杂志》、《实用儿科临床杂志》、《中国实用儿科杂志》、《当代儿科杂志》、《儿科急救医学》、《儿科药学》等专业杂志及其它杂志上发表论文近100篇', '2013-12-03', 'A09', '儿科', '', ''),
	('1473917669743923200', 'admin', '2021-12-23 15:25:31.193000', 0, NULL, NULL, '放疗学科自1997年5月组建开展肿瘤放射治疗，先后历属放射科、肿瘤（内）科，2018年9月独立成科。现有病区1个，开放床位26张，医师、护士和物理技术人员30余人，其中正高1人，副高5人，硕士学位7人，绍兴市文理学院硕士生导师1人。学科现拥有美国瓦里安Vital Beam和德国西门子直线加速器各一台，飞利浦Brilliance 大孔径CT定位机一台，Eclipse 15.5和Pinnacle 8.0计划验证系统，以及ORFIT放疗一体板、立体定向放疗体架和科莱瑞迪乳腺定位托架等放疗辅助设备。\n\n学科在绍兴地区首先开展图像引导放疗（IGRT）、体部立体定向放疗（SBRT）、容积旋转调强放疗（VMAT）技术。年收治放疗病人近900例、放疗2万人次，常规开展适形、调强放射治疗及SBRT等精准放疗技术，对鼻咽癌、头颈部肿瘤、食管癌、直肠癌、肺癌、乳腺癌、胰腺癌的诊治有丰富的临床经验。定期开展疑难病例MDT，为患者制定最佳治疗方案。经过20余年的发展，科室的影响力、病人收治数、新技术新项目开展、科研教育等方面在本地区保持领先。', '吴东平', '学科近三年主持科研项目6项，开展各类新技术3项。在国内外杂志上已发表学术论文10余篇，SCI收录6篇。学科内省级学术团体担任委员6人，市级学术团体担任委员10余人。', '2019-12-18', 'A10', '放疗科', NULL, NULL),
	('1473917781656342528', 'admin', '2021-12-23 15:25:57.874000', 0, NULL, NULL, '成立于1978年，是一个以消化系疾病临床诊治及内镜诊治为主，医疗、教学、科研相结合的专业性学科。目前拥有一个病区，46张床位，年住院病人3千余人次，年门诊量10万余人次，年胃镜检查约3万余人次，肠镜检查大约1万人次，ERCP检查800余人次，年开展无痛胃肠镜2万余人次，同时开展多种内镜下治疗，如息肉摘除，内镜下止血、消化道狭窄扩张及支架置入，胃造瘘术及营养管置入，EMR,,ESD,EUS,等技术。收治各种消化道疾病如消化道大出血、肝硬化腹水、肝性脑病、消化道肿瘤、黄疸，胰腺炎等患者，系绍兴市消化病学临床、教学与科研中心。国家级规培基地之 消化专业亚基地。在编临床医师22名；其中主任医师6名，副主任医师5名，其中硕士、博士研究生14名。拥有先进的诊疗设备，开展各种疑难危重消化道疾病的诊治及内镜下治疗。目前正在创建绍兴消化内镜学创新学科。目前在读硕士研究生三人。', '马阿火', ' 开展了多项基础研究和临床工作,近年来我科已完成市级科研项目3项，在各级杂志上发表论文30篇，SCI一篇。引进多项新技术，在市内领先开展胶囊内镜、胃肠动力、CTA，FMT等在胃肠疾病诊治中应用。现有绍兴市级学组主委、副主任5名，委员10名，浙江省消化病学分会常务委员1名，浙江省中西医结合学会消化病学常委一名。', '2014-12-18', 'A11', '消化内科', NULL, NULL),
	('1473917898186690561', 'admin', '2021-12-23 15:26:25.658000', 0, NULL, NULL, '肿瘤内科及介入治疗科为绍兴市医学重点学科，是一个以集肿瘤化疗、肿瘤放疗、肿瘤介入、精准靶向治疗、生物免疫治疗及肿瘤姑息康复治疗于一体的肿瘤综合性治疗临床学科。学科自2008年成立以来，坚持走科学、规范、多学科合作的治疗之路，开展鼻咽癌、肺癌、食管癌、乳腺癌、脑瘤、胃癌、肠癌、泌尿系肿瘤、妇科肿瘤等常见肿瘤的综合治疗。2012年荣获“浙江省卫生厅癌痛规范化治疗示范病房”。2017年通过国家食药监总局的GCP认证，已开展多项肿瘤药物治疗的Ⅲ、Ⅳ期临床试验。学科团队成员中有主任医师4名，副主任医师2名，硕士研究生8名，在读博士研究生1名。', '王建芳', '近年来开展多项基础研究和临床研究工作，主持各级各类科研项目10余项，其中省级项目2项；获浙江省医药卫生三等奖1项、绍兴市科学技术二、三等奖各1项。发表各类学术论文40余篇，其中SCI收录9篇，中华级3篇。', '2014-12-09', 'A12', '肿瘤内科', NULL, NULL),
	('1473918021696360449', 'admin', '2021-12-23 15:26:55.106000', 0, NULL, NULL, ' 医院成立初时本科与眼科合为一个科室，经过多年努力，于20世纪90年代成为独立学科，是一个以耳鼻咽喉临床医学为主，医疗、教学、科研相结合的专业性学科。目前拥有一个病区，40张床位，年住院病人1900余人次。在编临床医师14名；其中主任医师2名，副主任医师4名，其中硕士研究生6名。拥有各种先进的检查和手术设备。主要收治鼻息肉、鼻窦炎、腺样体肥大、扁桃体炎、鼻眼相关疾病、鼻腔鼻窦肿瘤、咽喉部肿瘤、声带息肉、各肿类型的中耳炎、突发性耳聋以及各种耳鼻咽喉科急诊：如鼻出血、气管、食道异物、头颈部外伤等。系绍兴市耳鼻咽喉学科临床、教学与科研中心。 ', '刘平', '开展了多项基础研究和临床工作，近年来已在《中华耳鼻咽喉头颈外科杂志》、《临床耳鼻咽喉头颈外科杂志》等核心期刊发表论文多篇。完成省卫生厅重点推广项目及绍兴市多个科研项目，获绍兴市科学技术进步二等奖1次。', '2021-12-07', 'A13', '耳鼻咽喉科', NULL, NULL),
	('1473918123596976129', 'admin', '2021-12-23 15:27:19.400000', 0, NULL, NULL, '省市共建医学重点学科、绍兴市医学领先学科、绍兴地区规模最大的泌尿疾病诊疗中心之一。与上海交通大学附属第一医院、浙江大学附属第一医院、浙江大学附属邵逸夫医院、浙江省人民医院均建立合作关系。与上海交通大学附属第一医院开展合作，成立上海公济泌尿外科集团远程医疗平台绍兴分中心和夏术阶国家级名医工作站。我院首批获国家GCP认证专业。绍兴市医学会泌尿外科专业委员会、绍兴市中西医结合学会男科专业委员会、绍兴市康复医学会泌尿男科康复专科委员会主委单位。学科现有医师22人，其中主任医师9人，副主任医师10人，博士3人、硕士13人。学科成员担任国家级、省级、市级等各级各类学术职务40余人次。', '阎家骏', '致力于泌尿系肿瘤早期诊断及发病机制的研究，部分成果达国际先进、国内领先。近五年，承担省、厅、市级课题10余项，国家级继续教育项目10余项；获厅市级成果奖励5项；发表学术论文60余篇，其中SCI论文10余篇、中华级论文10余篇，获实用新型专利4项。', '2016-12-09', 'A14', '泌尿外科', NULL, NULL),
	('1473918239561093120', 'admin', '2021-12-23 15:27:47.048000', 0, NULL, NULL, '是省卫生厅扶植重点学科、市医学重点领先学科，浙东地区神经外科专病中心建设单位，在省内具有较高知名度，技术力量雄厚。现有专业人员20人，其中正高职称7人，副高职称4人，中级职称6人，博士3人，硕士12人。固定床位88张，其中神经科监护病床（NICU）16张。主要诊治范围为：颅脑创伤、颅内肿瘤、脑血管疾病、脊髓疾病、功能性疾病、先天性疾病等，集临床、教学、科研为一体。', '俞学斌', '在市内处于学术技术领先地位，在省内有较高的声誉。是绍兴市医学重点领先学科、浙江省医学扶植重点学科和浙东地区（绍兴、宁波、舟山）神经外科专病中心建设单位。', '2016-12-07', 'A15', '神经外科', NULL, NULL),
	('1473918377142652928', 'admin', '2021-12-23 15:28:19.849000', 0, NULL, NULL, '2005年开设风湿病门诊，2012年在内分泌代谢科成立风湿免疫组，2018年9月成立独立风湿免疫科。是绍兴地区首家独立的风湿免疫科，主要承担绍兴地区风湿病诊治、抢救、会诊，同时集教育、科研、预防于一体的专业性临床学科。目前科室医生9人，其中主任医师2名，主治医师4人，住院医师3人，其中8位硕士研究生。有6位医师在省、市学术团体中兼任学术职务，其中俞钟明主任任浙江省医学会风湿病分会常委，浙江省医师协会风湿免疫科医师分会常委，绍兴市医学会和绍兴市中西医结合学会风湿病专业委员会主任委员。赵书山主任任绍兴市医学会风湿病专业委员会副主任委员，浙江省医师协会风湿病相关肺血管间质病学组委员，浙江省医学会风湿病学分会骨内科学组和影像学组委员。\n\n科室现有14张病床，开设风湿免疫科普通门诊、专家门诊、专病门诊、特需专家门诊。目前年门诊量2万余人次，年出院病人1000人次以上，在绍兴及周边地区享有盛誉。本科室有上海仁济医院鲍春德教授“国内名医专家工作室”，鲍春德教授每月来一次特需门诊、教学、查房。', '俞钟明', '近年来，该学科承担、参与省市级课题5项，院级课题2项，发病学术论文40余篇，其中SCI收录4篇。承担绍兴文理学院本科生研究生教学培养任务，是国家级医师规范化培训基地。多次参加国家、省内风湿病年会等学术会议，与省、市多家专科医院交流、学习。诊疗、学术水平在地市级处于先进水平。', '2014-12-10', 'A16', '风湿免疫科', NULL, NULL),
	('1473918511486210048', 'admin', '2021-12-23 15:28:51.882000', 0, NULL, NULL, '成立于2003年，是全市最早成立的专业医疗康复部门，2005年创建成绍兴市重点学科，2007年成为浙江省综合性医院中医名科(中医示范科)，2009年成为浙江省二家工伤康复试点医院之一，2010年绍兴市政府与荷兰政府签定了总投入230万欧元的中-荷康复医学学科建设项目，2012年获批浙江省住院医师规范化培训康复基地，2014年获批国家级住院医师规范化培训康复基地，同年与时任国际物理医学与康复医学学会主席励建安教授专家团队接轨，建立励建安(院士)专家团队工作站，2018年获批浙江省中医药创新类重点学科（中西医结合），同年底创建成为绍兴市领先学科，2019年入选“十三五”省中医药重点专科建设项目，2020年牵头成立了绍兴市康复医学质量控制中心。', '龚剑秋', '学科成员担任包括国家级委员、省级常务委员、常务理事、市级康复医学会秘书长、主委等各级各类学术职务80余人次，近年主持国家级继教8项，省市级10余项，获省部级立项2项，厅市级立项10余项，多次获厅市级科学技术二、三等奖，主持新技术新项目10余项，获国家实用新型专利5项，多项新技术填补市内空白，推广应用至省内外20多家医院', '2014-12-17', 'A17', '康复医学科', NULL, NULL),
	('1473918648560259072', 'admin', '2021-12-23 15:29:24.561000', 0, NULL, NULL, ' 创建于1962年，经过50多年，三代病理人的不懈努力，从一间房一个人，简陋的设备， 300例年检量发展到现在拥有2500平方米面积，16名工作人员、高级职称比例达到30%，博士、硕士研究生比例达到20%以上。拥有全套高质量专科仪器设备、主攻方向明确、管理制度完善、为具有亚专业（专科）特色的三级甲等综合性医院病理科。是绍兴市第三批重点学科，省市共建医学扶植重点建设学科，硕士带教点，病理质控中心挂靠单位与指导中心。承担着临床诊疗、教学、科研三方面的重要任务。\n      现在，常规年活检量超过3万例，冷冻快速病理会诊6千余例，细胞病理学（液基细胞学/穿刺病理学）8000例，常规开展外科组织病理学检查，术中快速冷冻病理会诊，细胞病理学（液基、细针穿刺）检查，15项组织化学检查，100余项免疫组织化学检查、免疫荧光检测，原位分子杂交技术等多项病理检查和辅助检查项目。', '王诚', '开展了多项基础研究和临床工作，近年来已在SCI、中华级、国家级杂志等核心期刊发表论文多篇，获得省、市自然科学优秀论文奖一、二等奖多次。完成及协助完成多个省、市科研项目，或国家发明专利2项，浙江省医药卫生科技创新二、三等奖多次，绍兴市科学技术进步一、二、三等奖多次。', '2015-12-02', 'A18', '病理科', NULL, NULL),
	('1473918750154690560', 'admin', '2021-12-23 15:29:48.783000', 0, NULL, NULL, ' 是集肾脏病临床诊断、治疗、血液净化为一体的临床学科。创建于二十世纪八十年代，是绍兴市率先成立的独立科室建制的肾脏病学科，目前设置有2个病区，病床41张、透析床位49张。拥有国际先进水平的血液透析机44台、血液透析滤过机5台，水处理设备2套。2001年在绍兴地区率先开展经皮肾活检术，2010年成立腹膜透析治疗中心。目前全科有医护人员50名、血透室专职工程师1名，其中在编临床医师16名，其中主任医师4名，副主任医师3名，硕士研究生7名，在读硕士研究生3名，在读博士研究生1名。学科在诊治急性肾炎、慢性肾炎、肾病综合征、狼疮性肾炎、糖尿病肾病、尿路感染、泌尿系结核、肾移植状态等常见疾病的基础上，着重解决绍兴地区急、慢性肾功能衰竭的诊断治疗及危重病人的抢救，其诊疗水平处于绍兴地区领先地位。', '沈水娟', '开展了多项基础研究和临床工作，已完成市级科技项目3项，现承担市级课题1项，浙江省卫生厅课题1项，浙江省医学会课题1项，引进新技术项目多项，近十年在中华级、国家级、省级杂志发表论文50余篇，SCI论文1篇，获浙江省医药卫生科技创新三等奖1次。', '2014-12-24', 'A19', '肾内科', NULL, NULL),
	('1473918895923531776', 'admin', '2021-12-23 15:30:23.536000', 0, NULL, NULL, '成立于20世纪60年代，80年代初组建血液科，是一个以血液病临床医学为主，医疗、教学、科研相结合的专业性学科。目前拥有一个病区，48张床位，包括百级层流病房2间，目前年住院病人2000余人次，年门诊病人15000余人次。收治急慢性白血病、淋巴瘤、多发性骨髓瘤、骨髓增生异常综合征以及各种贫血、出血等良、恶性血液病，系绍兴市血液病学临床、教学与科研中心。在编临床医师16名：其中主任医师4名，副主任医师2名，其中博士1名，硕士12名。拥有设备先进的血液病实验室，可以进行细胞学、免疫学、分子生物学及基因水平的研究。', '封蔚莹', '开展了多项基础研究和临床研究工作，迄今已承担各类厅局级科技项目10余项，获浙江省医药卫生科技创新奖、绍兴市科学技术奖10余项；共发表科研论文100余篇，其中SCI 收录5篇，中华级论文10余篇；开展新技术新项目十余项。', '2015-12-09', 'A21', '血液内科', NULL, NULL),
	('1473919098827182080', 'admin', '2021-12-23 15:31:11.912000', 0, NULL, NULL, '创建于20世纪80年代，是一个从事皮肤病、性传播疾病、皮肤美容等业务的临床一线科室，年门诊量达5万余人次。皮肤科技术力量雄厚，专业人才队伍结构合理，现有在编临床医师5名，其中副主任医师1名，硕士研究生2名，3名医师具有浙江省美容主诊医师资格证书。学科在诊治湿疹、银屑病、过敏性皮炎、白癜风等常见疾病的基础上，相继开展了性病、皮肤外科、皮肤美容亚专业，着重解决重症药疹、皮肤肿瘤、红斑狼疮、大疱性皮肤病等危重疑难性皮肤病的诊疗。', '陈宏', '开展多项临床新项目和基础研究，近年来发表于《中华皮肤科杂志》、《中国皮肤病麻风病学杂志》等核心期刊论文多篇，获得省医学会论文一等奖二次。报道了多个非常少见的病例。', '2015-12-09', 'A23', '皮肤科', NULL, NULL);
/*!40000 ALTER TABLE `t_hospital_subject` ENABLE KEYS */;

-- 导出  表 order.t_log 结构
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE IF NOT EXISTS `t_log` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `cost_time` int unsigned DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `ip_info` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `request_param` longtext,
  `request_type` varchar(255) DEFAULT NULL,
  `request_url` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `log_type` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 正在导出表  order.t_log 的数据：~51 rows (大约)
DELETE FROM `t_log`;
/*!40000 ALTER TABLE `t_log` DISABLE KEYS */;
INSERT INTO `t_log` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `cost_time`, `ip`, `ip_info`, `name`, `request_param`, `request_type`, `request_url`, `username`, `log_type`) VALUES
	(1337311281962684416, NULL, '2020-12-11 16:20:31', 0, NULL, '2020-12-11 16:20:31', 453, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"wqsv","saveLogin":"true","captchaId":"ec504bd47f2644b2952c697fc39cbb35","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1337311546707152896, NULL, '2020-12-11 16:21:34', 0, NULL, '2020-12-11 16:21:34', 421, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"gs34","saveLogin":"true","captchaId":"5ea68daeacd34c61ae2c38b90792e4dd","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1337550219369385984, NULL, '2020-12-12 08:09:58', 0, NULL, '2020-12-12 08:09:58', 376, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"h9my","saveLogin":"true","captchaId":"bae6c0767a56494a9c0e28c0cf00331b","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1337697718230978560, NULL, '2020-12-12 17:56:04', 0, NULL, '2020-12-12 17:56:04', 393, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"39Ga","saveLogin":"true","captchaId":"ecbf9e66c5d54c9eb2cafcd8af008dda","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1337706410254929920, NULL, '2020-12-12 18:30:37', 0, NULL, '2020-12-12 18:30:37', 304, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"vrrg","saveLogin":"true","captchaId":"d56a7fb6fc59486aaadf51ef6b0dfd06","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1337748746032779264, NULL, '2020-12-12 21:18:50', 0, NULL, '2020-12-12 21:18:50', 101, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"pctt","saveLogin":"true","captchaId":"727df872b6ef4e76872127c6714a6a85","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1337759827593662464, NULL, '2020-12-12 22:02:52', 0, NULL, '2020-12-12 22:02:52', 90, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"mhfb","saveLogin":"true","captchaId":"224cd89ef641460b8c93782079ac2791","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1337762190274465792, NULL, '2020-12-12 22:12:15', 0, NULL, '2020-12-12 22:12:15', 85, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"35kp","saveLogin":"true","captchaId":"463a5adb0613404698aa743fdfe17562","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1337919204048900096, NULL, '2020-12-13 08:36:11', 0, NULL, '2020-12-13 08:36:11', 557, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"tm46","saveLogin":"true","captchaId":"d60af9eda81f41299a3409e498e4a0d8","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1337922224795029504, NULL, '2020-12-13 08:48:11', 0, NULL, '2020-12-13 08:48:11', 273, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"xl6z","saveLogin":"true","captchaId":"796b06527a314567a5b5c29e8a990b1b","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1337927477359677440, NULL, '2020-12-13 09:09:03', 0, NULL, '2020-12-13 09:09:03', 79, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"psu5","saveLogin":"true","captchaId":"157632a0dbd240ddbf91a4c709afe135","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1340928955515015168, NULL, '2020-12-21 15:55:51', 0, NULL, '2020-12-21 15:55:51', 225, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"vfvm","saveLogin":"true","captchaId":"30819a32fef64e02b7c125d553f46fb6","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1350603974109171712, NULL, '2021-01-17 08:40:55', 0, NULL, '2021-01-17 08:40:55', 236, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"wxdm","saveLogin":"true","captchaId":"b11c720c740b4451a0a2699bba67caf5","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1350609327995621376, NULL, '2021-01-17 09:02:12', 0, NULL, '2021-01-17 09:02:12', 86, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"5oxc","saveLogin":"true","captchaId":"187e612f6b484475ad78a32660e89e1e","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1355731771634880512, NULL, '2021-01-31 12:16:57', 0, NULL, '2021-01-31 12:16:57', 71, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"TIO6","saveLogin":"true","captchaId":"1143b9c8114449bc8e6d1c591aaed6e9","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1355731946143092736, NULL, '2021-01-31 12:17:39', 0, NULL, '2021-01-31 12:17:39', 107, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"ghf5","saveLogin":"true","captchaId":"8376fb3295bc466cb7cc526108b0da8c","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1355732778704048128, NULL, '2021-01-31 12:20:58', 0, NULL, '2021-01-31 12:20:58', 465, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"ep9m","saveLogin":"true","captchaId":"64271e9c4b83490789639141e537a35b","username":"company"}', 'POST', '/xboot/login', 'company', 1),
	(1355732845221515264, NULL, '2021-01-31 12:21:13', 0, NULL, '2021-01-31 12:21:13', 74, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"gxsc","saveLogin":"true","captchaId":"4317339ba1414b1b8d7bd3fda768f6f8","username":"doctor"}', 'POST', '/xboot/login', 'doctor', 1),
	(1355732928340037632, NULL, '2021-01-31 12:21:33', 0, NULL, '2021-01-31 12:21:33', 60, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"due8","saveLogin":"true","captchaId":"648c07f55a2b463eb814768b25211c53","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1355751708294254592, NULL, '2021-01-31 13:36:11', 0, NULL, '2021-01-31 13:36:11', 75, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"uwxd","saveLogin":"true","captchaId":"ffe6067e7b3740f3aeb837111b79486b","username":"company"}', 'POST', '/xboot/login', 'company', 1),
	(1355751794889854976, NULL, '2021-01-31 13:36:31', 0, NULL, '2021-01-31 13:36:31', 70, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"dekd","saveLogin":"true","captchaId":"e2e4c16b08bb49508e8ae345a5387141","username":"doctor"}', 'POST', '/xboot/login', 'doctor', 1),
	(1355751908521938944, NULL, '2021-01-31 13:36:58', 0, NULL, '2021-01-31 13:36:58', 91, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"jpss","saveLogin":"true","captchaId":"00b0a0f70492445fa1ba3c7d5892aa8d","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1463375568652341248, NULL, '2021-11-24 13:14:59', 0, NULL, '2021-11-24 13:14:59', 204, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"OLUR","saveLogin":"true","captchaId":"c9bc133822c6438e8c9499cb461a27d2","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1463375637669613568, NULL, '2021-11-24 13:15:15', 0, NULL, '2021-11-24 13:15:15', 79, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"SKUE","saveLogin":"true","captchaId":"77e6552ca17840dbacd9c8727f0573de","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1463384643616247808, NULL, '2021-11-24 13:51:02', 0, NULL, '2021-11-24 13:51:02', 69, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"IY7Q","saveLogin":"true","captchaId":"a2287d7f99f64fc98ad6316a2dbc8bf2","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1463390317393874944, NULL, '2021-11-24 14:13:35', 0, NULL, '2021-11-24 14:13:35', 81, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"9RNO","saveLogin":"true","captchaId":"e2ee9fed43db435095eed265a832df38","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1463396744564969472, NULL, '2021-11-24 14:39:07', 0, NULL, '2021-11-24 14:39:07', 92, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"avvh","saveLogin":"true","captchaId":"265ab32faada425f9e30adcacd27b263","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1463396965332160512, NULL, '2021-11-24 14:40:00', 0, NULL, '2021-11-24 14:40:00', 84, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"GER9","saveLogin":"true","captchaId":"8e5ed6a725c24ce586f85a828525bcbc","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1463397001549975552, NULL, '2021-11-24 14:40:09', 0, NULL, '2021-11-24 14:40:09', 83, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"YZHZ","saveLogin":"true","captchaId":"43b642dd04a84ff0b598c7e45dd8cbae","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1463745354230009856, NULL, '2021-11-25 13:44:22', 0, NULL, '2021-11-25 13:44:22', 199, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"7ptg","saveLogin":"true","captchaId":"b1e2e425f57145579c5bea5d5ed76ad2","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1463751360750161920, NULL, '2021-11-25 14:08:15', 0, NULL, '2021-11-25 14:08:15', 247, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"JQN5","saveLogin":"true","captchaId":"6a2975c9452e4243931b0d7b380325c6","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1463770014329999360, NULL, '2021-11-25 15:22:22', 0, NULL, '2021-11-25 15:22:22', 319, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"RZDZ","saveLogin":"true","captchaId":"f9b38f92cd224fe093bfde7948d9da04","username":"admin"}', 'POST', '/zwz/login', 'admin', 1),
	(1463778047030333440, NULL, '2021-11-25 15:54:17', 0, NULL, '2021-11-25 15:54:17', 143, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"R9FZ","saveLogin":"true","captchaId":"ea065e931bb34e3583f1ec6238799060","username":"admin"}', 'POST', '/zwz/login', 'admin', 1),
	(1464410444235149312, NULL, '2021-11-27 09:47:12', 0, NULL, '2021-11-27 09:47:12', 288, '127.0.0.1', '未知', '登录系统', '{"password":"密码已被隐藏","code":"BPQZ","saveLogin":"true","captchaId":"bce096c9b0d0430181d142f55f027ae7","username":"admin"}', 'POST', '/zwz/login', 'admin', 1),
	(1464472984524820480, NULL, '2021-11-27 13:55:43', 0, NULL, '2021-11-27 13:55:43', 88, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"GYLZ","saveLogin":"true","captchaId":"f97540bd28d44909a104e0e766968212","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1464475941274259456, NULL, '2021-11-27 14:07:28', 0, NULL, '2021-11-27 14:07:28', 72, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"1tft","saveLogin":"true","captchaId":"51c210f224304398a94d427b25b52a6b","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1464476031363715072, NULL, '2021-11-27 14:07:49', 0, NULL, '2021-11-27 14:07:49', 66, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"r8ss","saveLogin":"true","captchaId":"d07f6a8d78c34019acf01c21bb020000","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1464476281532977152, NULL, '2021-11-27 14:08:49', 0, NULL, '2021-11-27 14:08:49', 76, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"x6up","saveLogin":"true","captchaId":"ead79090003c4789bcfc9bb0137a21b0","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1464476769737379840, NULL, '2021-11-27 14:10:45', 0, NULL, '2021-11-27 14:10:45', 68, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"tquc","saveLogin":"true","captchaId":"27413d33831c45b8aa1cbb4dc875e574","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1464486461029093376, NULL, '2021-11-27 14:49:16', 0, NULL, '2021-11-27 14:49:16', 84, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"MPOP","saveLogin":"true","captchaId":"0fa393c45e8f48bf88e67952654ef708","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1464492802787381248, NULL, '2021-11-27 15:14:28', 0, NULL, '2021-11-27 15:14:28', 258, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"T1XL","saveLogin":"true","captchaId":"51738d0a52884c67ac5b0de6f2767aa3","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1464510533729390592, NULL, '2021-11-27 16:24:55', 0, NULL, '2021-11-27 16:24:55', 71, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"JZ7X","saveLogin":"true","captchaId":"985dba7cc36a4ee394ff35c53c77512e","username":"admin"}', 'POST', '/xboot/login', 'admin', 1),
	(1464514105674174464, NULL, '2021-11-27 16:39:07', 0, NULL, '2021-11-27 16:39:07', 274, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"WZOV","saveLogin":"true","captchaId":"92cfa4717f0f4b3e90160d6483238e99","username":"admin"}', 'POST', '/zwz/login', 'admin', 1),
	(1473908038065852416, NULL, '2021-12-23 14:47:15', 0, NULL, '2021-12-23 14:47:15', 382, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"DONF","saveLogin":"true","captchaId":"97314811bf8a48b0aae4b7adacb12877","username":"admin"}', 'POST', '/zwz/login', 'admin', 1),
	(1474196108623351808, NULL, '2021-12-24 09:51:56', 0, NULL, '2021-12-24 09:51:56', 92, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"6JXW","saveLogin":"true","captchaId":"54ca642b71dd4a5fae426ca40e6de48d","username":"admin"}', 'POST', '/zwz/login', 'admin', 1),
	(1474196155746357248, NULL, '2021-12-24 09:52:07', 0, NULL, '2021-12-24 09:52:07', 67, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"DVVK","saveLogin":"true","captchaId":"2bd9adb1bce044e1824742433717e0cf","username":"admin"}', 'POST', '/zwz/login', 'admin', 1),
	(1474544551673860098, NULL, '2021-12-25 08:56:32', 0, NULL, '2021-12-25 08:56:32', 68, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"HBN8","saveLogin":"true","captchaId":"c698b27a6b2642c199f1395472f4ddd7","username":"admin"}', 'POST', '/zwz/login', 'admin', 1),
	(1474558980763291648, NULL, '2021-12-25 09:53:52', 0, NULL, '2021-12-25 09:53:52', 78, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"GMDL","saveLogin":"true","captchaId":"d62a2d46897144a8b80144ed8fb4c0de","username":"admin"}', 'POST', '/zwz/login', 'admin', 1),
	(1474585800845955072, NULL, '2021-12-25 11:40:26', 0, NULL, '2021-12-25 11:40:26', 81, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"QJU5","saveLogin":"true","captchaId":"2017c3e10636422aa9591a4b897a9656","username":"admin"}', 'POST', '/zwz/login', 'admin', 1),
	(1474587119795179520, NULL, '2021-12-25 11:45:41', 0, NULL, '2021-12-25 11:45:41', 69, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"yofw","saveLogin":"true","captchaId":"0e41d23cea9444358d2744702cff0eca","username":"user1"}', 'POST', '/zwz/login', 'user1', 1),
	(1482957074295033856, NULL, '2022-01-17 14:04:54', 0, NULL, '2022-01-17 14:04:54', 349, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"TBTR","saveLogin":"true","captchaId":"dadb3c9e82d1415281dd3da586ebfab4","username":"admin"}', 'POST', '/zwz/login', 'admin', 1),
	(1482974124124016640, NULL, '2022-01-17 15:12:38', 0, NULL, '2022-01-17 15:12:38', 271, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"7AB6","saveLogin":"true","captchaId":"891191bef7624a5f96b9380fa373916f","username":"admin"}', 'POST', '/zwz/login', 'admin', 1),
	(1482975879301828608, NULL, '2022-01-17 15:19:37', 0, NULL, '2022-01-17 15:19:37', 244, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"5888","saveLogin":"true","captchaId":"9bb2a67cc2f14d4898799c234b6997cb","username":"admin"}', 'POST', '/zwz/login', 'admin', 1),
	(1483007225701208064, NULL, '2022-01-17 17:24:10', 0, NULL, '2022-01-17 17:24:10', 106, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"9694","saveLogin":"true","captchaId":"afa4bc9f5a9e4262aaf12b0ce5070f47","username":"admin"}', 'POST', '/zwz/login', 'admin', 1),
	(1483270586183585792, NULL, '2022-01-18 10:50:40', 0, NULL, '2022-01-18 10:50:40', 243, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"1418","saveLogin":"true","captchaId":"e6a3639e11144e819ff7880af93b3d37","username":"admin"}', 'POST', '/zwz/login', 'admin', 1),
	(1483271269842554880, NULL, '2022-01-18 10:53:23', 0, NULL, '2022-01-18 10:53:23', 70, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"9636","saveLogin":"true","captchaId":"e28271720bbd45048cf885ed4bfbadae","username":"admin"}', 'POST', '/zwz/login', 'admin', 1),
	(1483274635188506624, NULL, '2022-01-18 11:06:46', 0, NULL, '2022-01-18 11:06:46', 113, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"1216","saveLogin":"true","captchaId":"8ef2f30bdc8e4a1cb260cc1cede43f57","username":"admin"}', 'POST', '/zwz/login', 'admin', 1),
	(1483275694745194496, NULL, '2022-01-18 11:10:58', 0, NULL, '2022-01-18 11:10:58', 62, '127.0.0.1', '未知', '登录系统', '{"password":"你是看不见我的","code":"8392","saveLogin":"true","captchaId":"e0bfdb2ae1e041458073ec85815e54fc","username":"admin"}', 'POST', '/zwz/login', 'admin', 1);
/*!40000 ALTER TABLE `t_log` ENABLE KEYS */;

-- 导出  表 order.t_message_board 结构
DROP TABLE IF EXISTS `t_message_board`;
CREATE TABLE IF NOT EXISTS `t_message_board` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci NOT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `content` varchar(1024) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `date` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `reply_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_croatian_ci;

-- 正在导出表  order.t_message_board 的数据：~2 rows (大约)
DELETE FROM `t_message_board`;
/*!40000 ALTER TABLE `t_message_board` DISABLE KEYS */;
INSERT INTO `t_message_board` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `content`, `date`, `reply_id`, `user_id`, `user_name`) VALUES
	('1474578672475312129', 'admin', '2021-12-25 11:12:06.000000', 0, 'admin', '2021-12-25 11:32:44.627000', '你好呀', '2021-12-25 11:12:06', '', '682265633886208', '管理员'),
	('1474581367550513153', 'admin', '2021-12-25 11:22:49.080000', 0, NULL, NULL, '你也好', '2021-12-25 11:22:49', '1474578672475312129', '682265633886208', '管理员'),
	('1483304372770312193', 'admin', '2022-01-18 13:04:55.607000', 0, NULL, NULL, '这是我的留言，啦啦啦~', '2022-01-18 13:04:55', '', '682265633886208', '管理员'),
	('1483305179439828993', 'admin', '2022-01-18 13:08:07.916000', 0, NULL, NULL, '这是我的回复哦，祝您生活愉快！', '2022-01-18 13:08:07', '1483304372770312193', '682265633886208', '管理员');
/*!40000 ALTER TABLE `t_message_board` ENABLE KEYS */;

-- 导出  表 order.t_permission 结构
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE IF NOT EXISTS `t_permission` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` bigint unsigned DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL,
  `sort_order` decimal(10,2) DEFAULT NULL,
  `component` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `level` int unsigned DEFAULT NULL,
  `button_type` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `show_always` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 正在导出表  order.t_permission 的数据：~69 rows (大约)
DELETE FROM `t_permission`;
/*!40000 ALTER TABLE `t_permission` DISABLE KEYS */;
INSERT INTO `t_permission` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `description`, `name`, `parent_id`, `type`, `sort_order`, `component`, `path`, `title`, `icon`, `level`, `button_type`, `status`, `url`, `show_always`) VALUES
	(5129710648430592, '', '2018-06-04 19:02:29', 0, 'admin', '2021-11-27 14:46:19', '', 'sys', 125909152017944576, 0, 4.00, 'Main', '/sys', '权限管理', 'ios-settings', 1, '', 0, '', b'1'),
	(5129710648430593, '', '2018-06-04 19:02:32', 0, 'admin', '2021-11-27 14:43:19', '', 'user-admin', 1464484663442673664, 0, 1.10, 'sys/user-manage/userManage', 'user-admin', '用户管理', 'md-person', 2, '', 0, '', b'1'),
	(5129710648430594, '', '2018-06-04 19:02:35', 0, '', '2018-10-13 13:51:36', '', 'role-manage', 5129710648430592, 0, 1.60, 'sys/role-manage/roleManage', 'role-manage', '角色权限管理', 'md-contacts', 2, '', 0, '', b'1'),
	(5129710648430595, '', '2018-06-04 19:02:37', 0, '', '2018-09-23 23:32:02', '', 'menu-manage', 5129710648430592, 0, 1.70, 'sys/menu-manage/menuManage', 'menu-manage', '菜单权限管理', 'md-menu', 2, '', 0, '', b'1'),
	(15701400130424832, '', '2018-06-03 22:04:06', 0, '', '2018-09-19 22:16:44', '', '', 5129710648430593, 1, 1.11, '', '/xboot/user/admin/add*', '添加用户', '', 3, 'add', 0, '', b'1'),
	(15701915807518720, '', '2018-06-03 22:06:09', 0, '', '2018-06-06 14:46:51', '', '', 5129710648430593, 1, 1.13, '', '/xboot/user/admin/disable/**', '禁用用户', '', 3, 'disable', 0, NULL, b'1'),
	(15708892205944832, '', '2018-06-03 22:33:52', 0, '', '2018-06-28 16:44:48', '', '', 5129710648430593, 1, 1.14, '', '/xboot/user/admin/enable/**', '启用用户', '', 3, 'enable', 0, NULL, b'1'),
	(16678126574637056, '', '2018-06-06 14:45:16', 0, '', '2018-09-19 22:16:48', '', '', 5129710648430593, 1, 1.12, '', '/xboot/user/admin/edit*', '编辑用户', '', 3, 'edit', 0, '', b'1'),
	(16678447719911424, '', '2018-06-06 14:46:32', 0, 'admin', '2020-04-28 14:27:03', '', '', 5129710648430593, 1, 1.15, '', '/xboot/user/delByIds**', '删除用户', '', 3, 'delete', 0, '', b'1'),
	(16687383932047360, '', '2018-06-06 15:22:03', 0, '', '2018-09-19 22:07:34', '', '', 5129710648430594, 1, 1.21, '', '/xboot/role/save*', '添加角色', '', 3, 'add', 0, '', b'1'),
	(16689632049631232, '', '2018-06-06 15:30:59', 0, '', '2018-09-19 22:07:37', '', '', 5129710648430594, 1, 1.22, '', '/xboot/role/edit*', '编辑角色', '', 3, 'edit', 0, '', b'1'),
	(16689745006432256, '', '2018-06-06 15:31:26', 0, 'admin', '2020-04-28 14:29:02', '', '', 5129710648430594, 1, 1.23, '', '/xboot/role/delByIds**', '删除角色', '', 3, 'delete', 0, '', b'1'),
	(16689883993083904, NULL, '2018-06-06 15:31:59', 0, NULL, '2018-06-06 15:31:59', NULL, NULL, 5129710648430594, 1, 1.24, NULL, '/xboot/role/editRolePerm**', '分配权限', NULL, 3, 'editPerm', 0, NULL, b'1'),
	(16690313745666048, '', '2018-06-06 15:33:41', 0, '', '2018-09-19 22:07:46', '', '', 5129710648430594, 1, 1.25, '', '/xboot/role/setDefault*', '设为默认角色', '', 3, 'setDefault', 0, '', b'1'),
	(16694861252005888, '', '2018-06-06 15:51:46', 0, '', '2018-09-19 22:07:52', '', '', 5129710648430595, 1, 1.31, '', '/xboot/permission/add*', '添加菜单', '', 3, 'add', 0, '', b'1'),
	(16695107491205120, '', '2018-06-06 15:52:44', 0, '', '2018-09-19 22:07:57', '', '', 5129710648430595, 1, 1.32, '', '/xboot/permission/edit*', '编辑菜单', '', 3, 'edit', 0, '', b'1'),
	(16695243126607872, '', '2018-06-06 15:53:17', 0, 'admin', '2020-04-28 14:29:17', '', '', 5129710648430595, 1, 1.33, '', '/xboot/permission/delByIds**', '删除菜单', '', 3, 'delete', 0, '', b'1'),
	(25014528525733888, '', '2018-06-29 14:51:09', 0, '', '2018-10-08 11:13:27', '', '', 5129710648430593, 1, 1.16, '', '无', '上传图片', '', 3, 'upload', 0, '', b'1'),
	(39915540965232640, '', '2018-08-09 17:42:28', 0, 'admin', '2021-11-27 14:46:28', '', 'monitor', 125909152017944576, 0, 10.00, 'Main', '/monitor', '日志管理', 'ios-analytics', 1, '', 0, '', b'1'),
	(40238597734928384, '', '2018-08-10 15:06:10', 0, 'admin', '2021-11-27 14:44:36', '', 'department-admin', 1464485105081913345, 0, 1.20, 'sys/department-manage/departmentManage', 'department-admin', '部门管理', 'md-git-branch', 2, '', 0, '', b'1'),
	(41363147411427328, '', '2018-08-13 17:34:43', 0, 'admin', '2020-03-25 20:31:16', '', 'log-manage', 39915540965232640, 0, 2.20, 'sys/log-manage/logManage', 'log-manage', '日志管理', 'md-list-box', 2, '', 0, '', b'1'),
	(41363537456533504, '', '2018-08-13 17:36:16', 0, 'admin', '2020-04-28 14:31:54', '', '', 41363147411427328, 1, 2.11, '', '/xboot/log/delByIds**', '删除日志', '', 3, 'delete', 0, '', b'1'),
	(41364927394353152, '', '2018-08-13 17:41:48', 0, '', '2018-09-19 22:08:57', '', '', 41363147411427328, 1, 2.12, '', '/xboot/log/delAll*', '清空日志', '', 3, 'undefined', 0, '', b'1'),
	(45235621697949696, '', '2018-08-24 10:02:33', 0, '', '2018-09-19 22:06:57', '', '', 40238597734928384, 1, 1.21, '', '/xboot/department/add*', '添加部门', '', 3, 'add', 0, '', b'1'),
	(45235787867885568, '', '2018-08-24 10:03:13', 0, '', '2018-09-19 22:07:02', '', '', 40238597734928384, 1, 1.22, '', '/xboot/department/edit*', '编辑部门', '', 3, 'edit', 0, '', b'1'),
	(45235939278065664, '', '2018-08-24 10:03:49', 0, 'admin', '2020-04-28 14:27:35', '', '', 40238597734928384, 1, 1.23, '', '/xboot/department/delByIds*', '删除部门', '', 3, 'delete', 0, '', b'1'),
	(56309618086776832, '', '2018-09-23 23:26:40', 0, 'admin', '2021-11-27 14:47:48', '', 'file-admin', 1464485485316542465, 0, 1.00, 'sys/oss-manage/ossManage', 'file-admin', '文件对象存储', 'ios-folder', 2, '', 0, '', b'1'),
	(56898976661639168, '', '2018-09-25 14:28:34', 0, '', '2018-09-25 15:12:46', '', '', 5129710648430593, 1, 1.17, '', '/xboot/user/importData*', '导入用户', '', 3, 'input', 0, '', b'1'),
	(57212882168844288, '', '2018-09-26 11:15:55', 0, '', '2018-10-08 11:10:05', '', '', 56309618086776832, 1, 1.41, '', '无', '上传文件', '', 3, 'upload', 0, '', b'1'),
	(61560041605435392, NULL, '2018-10-08 11:09:58', 0, NULL, '2018-10-08 11:09:58', NULL, '', 56309618086776832, 1, 1.42, '', '/xboot/file/rename*', '重命名文件', '', 3, 'edit', 0, NULL, b'1'),
	(61560275261722624, NULL, '2018-10-08 11:10:54', 0, NULL, '2018-10-08 11:10:54', NULL, '', 56309618086776832, 1, 1.43, '', '/xboot/file/copy*', '复制文件', '', 3, 'edit', 0, NULL, b'1'),
	(61560480518377472, '', '2018-10-08 11:11:43', 0, 'admin', '2020-04-28 14:28:22', '', '', 56309618086776832, 1, 1.44, '', '/xboot/file/delete*', '删除文件', '', 3, 'delete', 0, '', b'1'),
	(75002207560273920, 'admin', '2018-11-14 13:24:21', 0, 'admin', '2021-11-27 14:47:55', '', 'dict-admin', 1464485485316542465, 0, 2.00, 'sys/dict-manage/dictManage', 'dict-admin', '数据字典管理', 'md-bookmarks', 2, '', 0, '', b'1'),
	(76215889006956544, 'admin', '2018-11-17 21:47:05', 0, 'admin', '2018-11-17 21:47:53', '', '', 75002207560273920, 1, 1.81, '', '/xboot/dict/add*', '添加字典', '', 3, 'add', 0, '', b'1'),
	(76216071333351424, 'admin', '2018-11-17 21:47:48', 0, 'admin', '2018-11-17 21:47:48', NULL, '', 75002207560273920, 1, 1.82, '', '/xboot/dict/edit*', '编辑字典', '', 3, 'edit', 0, NULL, b'1'),
	(76216264070008832, 'admin', '2018-11-17 21:48:34', 0, 'admin', '2020-04-28 14:29:30', '', '', 75002207560273920, 1, 1.83, '', '/xboot/dict/delByIds**', '删除字典', '', 3, 'delete', 0, '', b'1'),
	(76216459709124608, 'admin', '2018-11-17 21:49:21', 0, 'admin', '2018-11-17 21:49:21', NULL, '', 75002207560273920, 1, 1.84, '', '/xboot/dictData/add*', '添加字典数据', '', 3, 'add', 0, NULL, b'1'),
	(76216594207870976, 'admin', '2018-11-17 21:49:53', 0, 'admin', '2018-11-17 21:49:53', NULL, '', 75002207560273920, 1, 1.85, '', '/xboot/dictData/edit*', '编辑字典数据', '', 3, 'edit', 0, NULL, b'1'),
	(76216702639017984, 'admin', '2018-11-17 21:50:18', 0, 'admin', '2018-11-17 21:50:18', NULL, '', 75002207560273920, 1, 1.86, '', '/xboot/dictData/delByIds/**', '删除字典数据', '', 3, 'delete', 0, NULL, b'1'),
	(121426317022334976, 'admin', '2019-03-22 15:57:11', 0, 'admin', '2021-11-27 14:46:36', '', 'redis', 39915540965232640, 0, 2.21, 'sys/redis/redis', 'redis', 'Redis日志', 'md-barcode', 2, '', 0, '', b'1'),
	(125909152017944576, 'admin', '2019-04-04 00:50:22', 0, 'admin', '2021-11-27 14:40:00', '', 'xboot', 0, -1, 1.00, '', '', '数据中心', 'md-home', 0, '', 0, '', b'1'),
	(156365156580855808, 'admin', '2019-06-27 01:51:39', 0, 'admin', '2019-06-27 01:51:39', NULL, '', 5129710648430593, 1, 1.18, '', '/xboot/user/resetPass', '重置密码', '', 3, 'other', 0, NULL, b'1'),
	(1255336077691064320, 'admin', '2020-04-29 11:20:18', 0, 'admin', '2020-04-29 11:20:47', '', '', 121426317022334976, 1, 0.00, '', '/xboot/redis/getAllByPage**', '获取Redis', '', 3, 'view', 0, '', b'1'),
	(1255336361339260928, 'admin', '2020-04-29 11:21:26', 0, 'admin', '2020-04-29 11:21:26', NULL, '', 121426317022334976, 1, 0.00, '', '/xboot/redis/save', '添加Redis', '', 3, 'add', 0, NULL, b'1'),
	(1255336455472025601, 'admin', '2020-04-29 11:21:48', 0, 'admin', '2020-04-29 11:21:48', NULL, '', 121426317022334976, 1, 0.00, '', '/xboot/redis/delByKeys**', '删除Redis', '', 3, 'delete', 0, NULL, b'1'),
	(1255336553971060737, 'admin', '2020-04-29 11:22:12', 0, 'admin', '2020-04-29 11:22:12', NULL, '', 121426317022334976, 1, 0.00, '', '/xboot/redis/delAll', '清空Redis', '', 3, 'clear', 0, NULL, b'1'),
	(1464484663442673664, 'admin', '2021-11-27 14:42:07', 0, 'admin', '2021-11-27 14:42:37', '', 'userTwoMenu', 125909152017944576, 0, 1.00, 'Main', '/userTwoMenu', '用户管理', 'md-analytics', 1, '', 0, '', b'1'),
	(1464485105081913345, 'admin', '2021-11-27 14:43:53', 0, 'admin', '2021-11-27 14:43:53', NULL, 'depTwoMenu', 125909152017944576, 0, 2.00, 'Main', '/depTwoMenu', '部门管理', 'ios-apps', 1, '', 0, NULL, b'1'),
	(1464485485316542465, 'admin', '2021-11-27 14:45:23', 0, 'admin', '2021-11-27 14:47:05', '', 'fileAdmin', 125909152017944576, 0, 3.00, 'Main', '/fileAdmin', '资源管理', 'md-basketball', 1, '', 0, '', b'1'),
	(1464486736955576321, 'admin', '2021-11-27 14:50:22', 0, 'admin', '2021-11-27 14:50:22', NULL, 'file-setting', 1464485485316542465, 0, 3.00, 'sys/setting-manage/settingManage', 'file-setting', '文件存储配置', 'ios-settings-outline', 2, '', 0, NULL, b'1'),
	(1464514283202285568, 'admin', '2021-11-27 16:39:49', 0, 'admin', '2021-11-27 16:39:49', NULL, 'vue-code', 1464485485316542465, 0, 4.00, 'sys/oneJavaVue/tableGenerator', 'vue-code', 'Vue', 'md-analytics', 2, '', 0, NULL, b'1'),
	(1473912920739024896, 'admin', '2021-12-23 15:06:39', 0, 'admin', '2021-12-23 15:06:39', NULL, 'doctorAndSub', NULL, -1, 2.00, NULL, NULL, '科室医生', 'md-aperture', 0, NULL, 0, NULL, b'1'),
	(1473913059595653120, 'admin', '2021-12-23 15:07:12', 0, 'admin', '2021-12-23 15:07:12', NULL, 'hospitalSubjectTwo', 1473912920739024896, 0, 1.00, 'Main', '/hospitalSubjectTwo', '科室管理', 'md-analytics', 1, '', 0, NULL, b'1'),
	(1473913177623367681, 'admin', '2021-12-23 15:07:40', 0, 'admin', '2021-12-23 15:07:40', NULL, 'hospitalSubject', 1473913059595653120, 0, 1.00, 'doctor/hospitalSubject/index', 'hospitalSubject', '科室管理', 'ios-apps', 2, '', 0, NULL, b'1'),
	(1473921681071411200, 'admin', '2021-12-23 15:41:28', 0, 'admin', '2021-12-23 15:41:28', NULL, 'doctorTwo', 1473912920739024896, 0, 2.00, 'Main', '/doctorTwo', '医生管理', 'md-aperture', 1, '', 0, NULL, b'1'),
	(1473921969287204864, 'admin', '2021-12-23 15:42:36', 0, 'admin', '2021-12-23 15:42:36', NULL, 'zwzDoctor', 1473921681071411200, 0, 1.00, 'doctor/doctor/index', 'zwzDoctor', '医生管理', 'ios-basket', 2, '', 0, NULL, b'1'),
	(1474248113106587648, 'admin', '2021-12-24 13:18:35', 0, 'admin', '2021-12-24 13:18:35', NULL, 'pushOrder', 1473921681071411200, 0, 2.00, 'doctor/pushOrder/index', 'pushOrder', '医生放号', 'md-aperture', 2, '', 0, NULL, b'1'),
	(1474279208996966400, 'admin', '2021-12-24 15:22:09', 0, 'admin', '2021-12-24 15:22:09', NULL, 'orderHao', NULL, -1, 3.00, NULL, NULL, '预约挂号', 'md-aperture', 0, NULL, 0, NULL, b'1'),
	(1474279286738391041, 'admin', '2021-12-24 15:22:27', 0, 'admin', '2021-12-24 15:22:27', NULL, 'orderNumberTwo', 1474279208996966400, 0, 1.00, 'Main', '/orderNumberTwo', '预约挂号', 'md-aperture', 1, '', 0, NULL, b'1'),
	(1474279419504889857, 'admin', '2021-12-24 15:22:59', 0, 'admin', '2021-12-24 15:22:59', NULL, 'guaOrder', 1474279286738391041, 0, 1.00, 'doctor/guaOrder/index', 'guaOrder', '预约挂号', 'md-at', 2, '', 0, NULL, b'1'),
	(1474288862284091392, 'admin', '2021-12-24 16:00:30', 0, 'admin', '2021-12-24 16:00:30', NULL, 'myOrder', 1474279286738391041, 0, 2.00, 'doctor/myOrder/index', 'myOrder', '我的挂号', 'ios-apps', 2, '', 0, NULL, b'1'),
	(1474559425741197312, 'admin', '2021-12-25 09:55:38', 0, 'admin', '2021-12-25 09:55:38', NULL, 'hospNews', NULL, -1, 4.00, NULL, NULL, '医院时政', 'md-aperture', 0, NULL, 0, NULL, b'1'),
	(1474559508469649408, 'admin', '2021-12-25 09:55:57', 0, 'admin', '2021-12-25 09:55:57', NULL, 'hospNewTwo', 1474559425741197312, 0, 1.00, 'Main', '/hospNewTwo', '医院新闻', 'md-attach', 1, '', 0, NULL, b'1'),
	(1474559937995739137, 'admin', '2021-12-25 09:57:40', 0, 'admin', '2021-12-25 10:23:43', '', 'hospitalNews', 1474559508469649408, 0, 1.00, 'doctor/hospitalNews/index', 'hospitalNews', '医院新闻管理', 'md-bowtie', 2, '', 0, '', b'1'),
	(1474566156600872961, 'admin', '2021-12-25 10:22:23', 0, 'admin', '2021-12-25 10:22:37', '', '', 1474559937995739137, 1, 1.00, '', 'setDynamicNewTop', '置顶医院新闻', '', 3, 'setDynamicNewTop', 0, '', b'1'),
	(1474566614732115969, 'admin', '2021-12-25 10:24:12', 0, 'admin', '2021-12-25 10:24:12', NULL, 'userNews', 1474559508469649408, 0, 2.00, 'doctor/userNews/index', 'userNews', '医院新闻浏览', 'ios-apps', 2, '', 0, NULL, b'1'),
	(1474572846901628928, 'admin', '2021-12-25 10:48:58', 0, 'admin', '2021-12-25 10:48:58', NULL, 'replyMessage', 1474559425741197312, 0, 2.00, 'Main', '/replyMessage', '留言建议', 'md-aperture', 1, '', 0, NULL, b'1'),
	(1474574872549134336, 'admin', '2021-12-25 10:57:01', 0, 'admin', '2021-12-25 10:57:01', NULL, 'messageBoard', 1474572846901628928, 0, 1.00, 'doctor/messageBoard/index', 'messageBoard', '医院留言板', 'ios-brush', 2, '', 0, NULL, b'1'),
	(1474584028081426432, 'admin', '2021-12-25 11:33:23', 0, 'admin', '2021-12-25 11:33:23', NULL, 'userBoard', 1474572846901628928, 0, 2.00, 'doctor/userBoard/index', 'userBoard', '用户留言', 'md-brush', 2, '', 0, NULL, b'1');
/*!40000 ALTER TABLE `t_permission` ENABLE KEYS */;

-- 导出  表 order.t_role 结构
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE IF NOT EXISTS `t_role` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `default_role` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `data_type` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 正在导出表  order.t_role 的数据：~2 rows (大约)
DELETE FROM `t_role`;
/*!40000 ALTER TABLE `t_role` DISABLE KEYS */;
INSERT INTO `t_role` (`id`, `create_by`, `create_time`, `update_by`, `update_time`, `name`, `del_flag`, `default_role`, `description`, `data_type`) VALUES
	(496138616573952, '', '2018-04-22 23:03:49', 'admin', '2018-11-15 23:02:59', 'ROLE_ADMIN', 0, NULL, '超级管理员 拥有所有权限', 0),
	(496138616573953, '', '2018-05-02 21:40:03', 'admin', '2018-11-01 22:59:48', 'ROLE_USER', 0, b'1', '普通注册用户 路过看看', 0);
/*!40000 ALTER TABLE `t_role` ENABLE KEYS */;

-- 导出  表 order.t_role_permission 结构
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE IF NOT EXISTS `t_role_permission` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `permission_id` bigint unsigned DEFAULT NULL,
  `role_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 正在导出表  order.t_role_permission 的数据：~78 rows (大约)
DELETE FROM `t_role_permission`;
/*!40000 ALTER TABLE `t_role_permission` DISABLE KEYS */;
INSERT INTO `t_role_permission` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `permission_id`, `role_id`) VALUES
	(1474584050063773696, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 125909152017944576, 496138616573952),
	(1474584050063773697, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1464484663442673664, 496138616573952),
	(1474584050063773698, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 5129710648430593, 496138616573952),
	(1474584050063773699, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 15701400130424832, 496138616573952),
	(1474584050063773700, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 16678126574637056, 496138616573952),
	(1474584050063773701, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 15701915807518720, 496138616573952),
	(1474584050063773702, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 15708892205944832, 496138616573952),
	(1474584050063773703, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 16678447719911424, 496138616573952),
	(1474584050063773704, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 25014528525733888, 496138616573952),
	(1474584050063773705, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 56898976661639168, 496138616573952),
	(1474584050063773706, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 156365156580855808, 496138616573952),
	(1474584050063773707, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1464485105081913345, 496138616573952),
	(1474584050063773708, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 40238597734928384, 496138616573952),
	(1474584050063773709, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 45235621697949696, 496138616573952),
	(1474584050063773710, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 45235787867885568, 496138616573952),
	(1474584050063773711, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 45235939278065664, 496138616573952),
	(1474584050063773712, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1464485485316542465, 496138616573952),
	(1474584050063773713, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 56309618086776832, 496138616573952),
	(1474584050063773714, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 57212882168844288, 496138616573952),
	(1474584050063773715, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 61560041605435392, 496138616573952),
	(1474584050063773716, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 61560275261722624, 496138616573952),
	(1474584050063773717, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 61560480518377472, 496138616573952),
	(1474584050063773718, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 75002207560273920, 496138616573952),
	(1474584050063773719, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 76215889006956544, 496138616573952),
	(1474584050063773720, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 76216071333351424, 496138616573952),
	(1474584050063773721, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 76216264070008832, 496138616573952),
	(1474584050063773722, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 76216459709124608, 496138616573952),
	(1474584050063773723, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 76216594207870976, 496138616573952),
	(1474584050063773724, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 76216702639017984, 496138616573952),
	(1474584050063773725, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1464486736955576321, 496138616573952),
	(1474584050063773726, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1464514283202285568, 496138616573952),
	(1474584050063773727, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 5129710648430592, 496138616573952),
	(1474584050063773728, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 5129710648430594, 496138616573952),
	(1474584050063773729, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 16687383932047360, 496138616573952),
	(1474584050063773730, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 16689632049631232, 496138616573952),
	(1474584050063773731, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 16689745006432256, 496138616573952),
	(1474584050063773732, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 16689883993083904, 496138616573952),
	(1474584050063773733, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 16690313745666048, 496138616573952),
	(1474584050063773734, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 5129710648430595, 496138616573952),
	(1474584050063773735, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 16694861252005888, 496138616573952),
	(1474584050063773736, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 16695107491205120, 496138616573952),
	(1474584050063773737, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 16695243126607872, 496138616573952),
	(1474584050063773738, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 39915540965232640, 496138616573952),
	(1474584050063773739, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 41363147411427328, 496138616573952),
	(1474584050063773740, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 41363537456533504, 496138616573952),
	(1474584050063773741, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 41364927394353152, 496138616573952),
	(1474584050063773742, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 121426317022334976, 496138616573952),
	(1474584050063773743, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1255336077691064320, 496138616573952),
	(1474584050063773744, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1255336361339260928, 496138616573952),
	(1474584050063773745, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1255336455472025601, 496138616573952),
	(1474584050063773746, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1255336553971060737, 496138616573952),
	(1474584050063773747, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1473912920739024896, 496138616573952),
	(1474584050063773748, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1473913059595653120, 496138616573952),
	(1474584050063773749, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1473913177623367681, 496138616573952),
	(1474584050063773750, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1473921681071411200, 496138616573952),
	(1474584050063773751, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1473921969287204864, 496138616573952),
	(1474584050063773752, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1474248113106587648, 496138616573952),
	(1474584050063773753, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1474279208996966400, 496138616573952),
	(1474584050063773754, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1474279286738391041, 496138616573952),
	(1474584050063773755, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1474279419504889857, 496138616573952),
	(1474584050063773756, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1474288862284091392, 496138616573952),
	(1474584050063773757, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1474559425741197312, 496138616573952),
	(1474584050063773758, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1474559508469649408, 496138616573952),
	(1474584050063773759, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1474559937995739137, 496138616573952),
	(1474584050063773760, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1474566156600872961, 496138616573952),
	(1474584050063773761, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1474566614732115969, 496138616573952),
	(1474584050063773762, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1474572846901628928, 496138616573952),
	(1474584050063773763, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1474574872549134336, 496138616573952),
	(1474584050063773764, 'admin', '2021-12-25 11:33:29', 0, 'admin', '2021-12-25 11:33:29', 1474584028081426432, 496138616573952),
	(1474586794673704960, 'admin', '2021-12-25 11:44:23', 0, 'admin', '2021-12-25 11:44:23', 1474279208996966400, 496138616573953),
	(1474586794673704961, 'admin', '2021-12-25 11:44:23', 0, 'admin', '2021-12-25 11:44:23', 1474279286738391041, 496138616573953),
	(1474586794673704962, 'admin', '2021-12-25 11:44:23', 0, 'admin', '2021-12-25 11:44:23', 1474279419504889857, 496138616573953),
	(1474586794673704963, 'admin', '2021-12-25 11:44:23', 0, 'admin', '2021-12-25 11:44:23', 1474288862284091392, 496138616573953),
	(1474586794673704964, 'admin', '2021-12-25 11:44:23', 0, 'admin', '2021-12-25 11:44:23', 1474559425741197312, 496138616573953),
	(1474586794673704965, 'admin', '2021-12-25 11:44:23', 0, 'admin', '2021-12-25 11:44:23', 1474559508469649408, 496138616573953),
	(1474586794673704966, 'admin', '2021-12-25 11:44:23', 0, 'admin', '2021-12-25 11:44:23', 1474566614732115969, 496138616573953),
	(1474586794673704967, 'admin', '2021-12-25 11:44:23', 0, 'admin', '2021-12-25 11:44:23', 1474572846901628928, 496138616573953),
	(1474586794673704968, 'admin', '2021-12-25 11:44:23', 0, 'admin', '2021-12-25 11:44:23', 1474584028081426432, 496138616573953);
/*!40000 ALTER TABLE `t_role_permission` ENABLE KEYS */;

-- 导出  表 order.t_setting 结构
DROP TABLE IF EXISTS `t_setting`;
CREATE TABLE IF NOT EXISTS `t_setting` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `value` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 正在导出表  order.t_setting 的数据：~2 rows (大约)
DELETE FROM `t_setting`;
/*!40000 ALTER TABLE `t_setting` DISABLE KEYS */;
INSERT INTO `t_setting` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `value`) VALUES
	('LOCAL_OSS', 'admin', '2021-11-27 14:23:13', 0, 'admin', '2021-11-27 14:51:42', '{"serviceName":"LOCAL_OSS","accessKey":"","secretKey":"**********","endpoint":"127.0.0.1:9999/xboot/file/view","bucket":"","http":"http://","bucketRegion":"","filePath":"C:\\\\oa-file"}'),
	('OSS_USED', 'admin', '2021-11-27 14:23:13', 0, 'admin', '2021-11-27 14:23:13', 'LOCAL_OSS');
/*!40000 ALTER TABLE `t_setting` ENABLE KEYS */;

-- 导出  表 order.t_user 结构
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE IF NOT EXISTS `t_user` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `type` int DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `department_id` bigint unsigned DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `pass_strength` varchar(2) DEFAULT NULL,
  `department_title` varchar(255) DEFAULT NULL,
  `birth` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `email` (`email`) USING BTREE,
  UNIQUE KEY `mobile` (`mobile`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 正在导出表  order.t_user 的数据：~2 rows (大约)
DELETE FROM `t_user`;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` (`id`, `create_by`, `create_time`, `update_by`, `update_time`, `address`, `avatar`, `description`, `email`, `mobile`, `nickname`, `password`, `sex`, `status`, `type`, `username`, `del_flag`, `department_id`, `street`, `pass_strength`, `department_title`, `birth`) VALUES
	(682265633886208, '', '2020-12-13 07:07:07', 'admin', '2021-11-27 14:55:06', '["330000","330600","330602"]', 'https://i.loli.net/2019/04/28/5cc5a71a6e3b6.png', '绍兴文理学院元培学院', '916077357@qq.com', '17857054385', '管理员', '$2a$10$PS04ecXfknNd3V8d.ymLTObQciapMU4xU8.GADBZZsuTZr7ymnagy', '男', 0, 1, 'admin', 0, 1464487379074158593, '东浦街道', '弱', '设计研发部', '2000-01-08 00:00:00.000000'),
	(1474586640742748160, 'admin', '2021-12-25 11:43:46', 'admin', '2021-12-25 11:43:46', NULL, 'https://i.loli.net/2019/04/28/5cc5a71a6e3b6.png', NULL, '17859654125@qq.com', '17859654125', '患者A', '$2a$10$PS04ecXfknNd3V8d.ymLTObQciapMU4xU8.GADBZZsuTZr7ymnagy', '女', 0, 0, 'user1', 0, 1464487406328745985, NULL, '弱', '财务部', NULL);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;

-- 导出  表 order.t_user_role 结构
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE IF NOT EXISTS `t_user_role` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `role_id` bigint unsigned DEFAULT NULL,
  `user_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- 正在导出表  order.t_user_role 的数据：~2 rows (大约)
DELETE FROM `t_user_role`;
/*!40000 ALTER TABLE `t_user_role` DISABLE KEYS */;
INSERT INTO `t_user_role` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `role_id`, `user_id`) VALUES
	(1464487931975700480, 'admin', '2021-11-27 14:55:07', 0, 'admin', '2021-11-27 14:55:07', 496138616573952, 682265633886208),
	(1474586641288007680, 'admin', '2021-12-25 11:43:46', 0, 'admin', '2021-12-25 11:43:46', 496138616573953, 1474586640742748160);
/*!40000 ALTER TABLE `t_user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
