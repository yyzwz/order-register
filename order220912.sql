/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DROP DATABASE IF EXISTS `order`;
CREATE DATABASE IF NOT EXISTS `order` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_croatian_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `order`;

DROP TABLE IF EXISTS `a_department`;
CREATE TABLE IF NOT EXISTS `a_department` (
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

DELETE FROM `a_department`;
INSERT INTO `a_department` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `parent_id`, `sort_order`, `status`, `title`, `is_parent`) VALUES
	(1464487288363945985, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 0, 1.00, 0, '人力资源部', b'1'),
	(1464487332064399361, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 0, 2.00, 0, '行政综合部', b'1'),
	(1464487379074158593, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 0, 3.00, 0, '设计研发部', b'1'),
	(1464487406328745985, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 0, 4.00, 0, '财务部', b'0'),
	(1464487432169852929, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 0, 5.00, 0, '综合发展部', b'0'),
	(1464487524662644737, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 1464487288363945985, 1.00, 0, 'HRBP部', b'0'),
	(1464487559647334401, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 1464487288363945985, 2.00, 0, '招聘部', b'0'),
	(1464487623933431809, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 1464487332064399361, 1.00, 0, '安保部', b'0'),
	(1464487656678363137, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 1464487332064399361, 2.00, 0, '保洁部', b'0'),
	(1464487691780493313, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 1464487332064399361, 3.00, 0, '设备维修部', b'0'),
	(1464487733895499777, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 1464487379074158593, 1.00, 0, '视频剪辑部', b'0'),
	(1464487807572643841, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 1464487379074158593, 2.00, 0, '产品应用部', b'0'),
	(1527834362656198656, 'admin', '2022-05-21 10:11:13', 0, NULL, NULL, 0, 6.00, 0, '客服部', b'0');

DROP TABLE IF EXISTS `a_department_header`;
CREATE TABLE IF NOT EXISTS `a_department_header` (
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

DELETE FROM `a_department_header`;

DROP TABLE IF EXISTS `a_dict`;
CREATE TABLE IF NOT EXISTS `a_dict` (
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

DELETE FROM `a_dict`;
INSERT INTO `a_dict` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `description`, `title`, `type`, `sort_order`) VALUES
	(75135930788220928, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', '性别', 'sex', 0.00),
	(75388696739713024, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', '分权限', 'permission_type', 2.00),
	(1536612750841745408, 'admin', '2022-06-14 15:33:23', 0, NULL, NULL, '', '学历', 'education', 3.00),
	(1536613395229446144, 'admin', '2022-06-14 15:35:57', 0, NULL, NULL, '', '在职状态', 'occupationStatus', 4.00);

DROP TABLE IF EXISTS `a_dict_data`;
CREATE TABLE IF NOT EXISTS `a_dict_data` (
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

DELETE FROM `a_dict_data`;
INSERT INTO `a_dict_data` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `description`, `dict_id`, `sort_order`, `status`, `title`, `value`) VALUES
	(75158227712479232, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', 75135930788220928, 0.00, 0, '男', '男'),
	(75159254272577536, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', 75135930788220928, 1.00, 0, '女', '女'),
	(75159898425397248, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', 75135930788220928, 2.00, -1, '保密', '保密'),
	(75390787835138048, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', 75388696739713024, 0.00, 0, '查看操作(view)', 'view'),
	(75390886501945344, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', 75388696739713024, 1.00, 0, '添加操作(add)', 'add'),
	(75390993939042304, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', 75388696739713024, 2.00, 0, '编辑操作(edit)', 'edit'),
	(75391067532300288, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', 75388696739713024, 3.00, 0, '删除操作(delete)', 'delete'),
	(75391126902673408, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', 75388696739713024, 4.00, 0, '清空操作(clear)', 'clear'),
	(75391192883269632, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', 75388696739713024, 5.00, 0, '启用操作(enable)', 'enable'),
	(75391251024711680, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', 75388696739713024, 6.00, 0, '禁用操作(disable)', 'disable'),
	(75391297124306944, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', 75388696739713024, 7.00, 0, '搜索操作(search)', 'search'),
	(75391343379091456, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', 75388696739713024, 8.00, 0, '上传文件(upload)', 'upload'),
	(75391407526776832, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', 75388696739713024, 9.00, 0, '导出操作(output)', 'output'),
	(75391475042488320, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', 75388696739713024, 10.00, 0, '导入操作(input)', 'input'),
	(75391522182270976, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', 75388696739713024, 11.00, 0, '分配权限(editPerm)', 'editPerm'),
	(75391576364290048, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', 75388696739713024, 12.00, 0, '设为默认(setDefault)', 'setDefault'),
	(75391798033256448, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', 75388696739713024, 13.00, 0, '其他操作(other)', 'other'),
	(1536612796106674176, 'admin', '2022-06-14 15:33:34', 0, NULL, NULL, '', 1536612750841745408, 1.00, 0, '博士', '博士'),
	(1536612834962706432, 'admin', '2022-06-14 15:33:43', 0, NULL, NULL, '', 1536612750841745408, 2.00, 0, '硕士', '硕士'),
	(1536612883465637888, 'admin', '2022-06-14 15:33:55', 0, 'admin', '2022-06-14 15:34:13', '', 1536612750841745408, 3.00, 0, '本科', '本科'),
	(1536612930576060416, 'admin', '2022-06-14 15:34:06', 0, 'admin', '2022-06-14 15:34:11', '', 1536612750841745408, 4.00, 0, '专科', '专科'),
	(1536612993519980544, 'admin', '2022-06-14 15:34:21', 0, '', NULL, '', 1536612750841745408, 5.00, 0, '高中及以下', '高中及以下'),
	(1536613425902391296, 'admin', '2022-06-14 15:36:04', 0, NULL, NULL, '', 1536613395229446144, 1.00, 0, '在职', '在职'),
	(1536613447544999936, 'admin', '2022-06-14 15:36:09', 0, NULL, NULL, '', 1536613395229446144, 2.00, 0, '离职', '离职');

DROP TABLE IF EXISTS `a_doctor`;
CREATE TABLE IF NOT EXISTS `a_doctor` (
  `id` varchar(255) COLLATE utf8_croatian_ci NOT NULL,
  `create_by` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int NOT NULL,
  `update_by` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `about` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `age` int NOT NULL,
  `doctor_name` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `education` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `major` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `order_money` decimal(19,2) DEFAULT NULL,
  `photo` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `post_level` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `subject_id` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `subject_name` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `university` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `work_date` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_croatian_ci;

DELETE FROM `a_doctor`;
INSERT INTO `a_doctor` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `about`, `age`, `doctor_name`, `education`, `major`, `order_money`, `photo`, `post_level`, `subject_id`, `subject_name`, `university`, `work_date`) VALUES
	('1474207434229813248', 'admin', '2021-12-24 10:36:56.000000', 0, 'admin', '2021-12-24 10:49:23.919000', '科副主任，主任医师。1987年毕业于浙江医科大学口腔医学专业，曾赴浙一医院进修，北京BITC口腔种植中心深造。担任中华口腔医学会口腔修复及口腔种植会员，浙江省口腔修复委员会委员，绍兴市口腔质控中心委员，绍兴市口腔中西医结合副主任委员。 ', 33, '王海花', '本科学士', '口腔医学专业', 15.00, 'https://www.312000.net/Upfile/Face/face_69.JPG', '主任医师', '1473916965973266433', '口腔科', '浙江医科大学', '2012-11-14'),
	('1474209128925761537', 'admin', '2021-12-24 10:43:40.000000', 0, 'admin', '2021-12-24 10:49:11.230000', '主任医师。1984年参加工作，2002年毕业于温州医科大学，本科学历，1996年赴浙一医院进修口腔颌面外科，2016年赴上海参加Straumann种植高级培训。', 38, '阮土耿', '本科学士', '口腔医学', 15.00, 'https://www.312000.net/Upfile/Face/face_68.JPG', '主任医师', '1473916965973266433', '口腔科', '温州医科大学', '2015-11-30'),
	('1474209351492308993', 'admin', '2021-12-24 10:44:33.000000', 0, 'admin', '2021-12-24 10:48:53.544000', '科主任，绍兴市医学重点学科带头人，主任医师，硕士生导师。1992年毕业于浙江医科大学，2004年获浙江大学医学院眼科学硕士学位。浙江省医师协会眼科分会常务委员、浙江省超声医学工程学会眼科专业委员会常务委员、浙江省医学会眼科分会委员、浙江省角膜病诊治技术指导中心委员会委员、绍兴市医学会眼科专业委员会主任委员、绍兴市医师协会眼科医师分会会长、绍兴市中西医结合学会眼科专业委员会副主任委员。曾在德国弗莱堡大学附属圣文森特医院、英国皇家自由医院研修。', 31, '陈伟', '硕士研究生', '眼科学', 15.00, 'https://www.312000.net/Upfile/Face/face_86.JPG', '主任医师', '1473917353262714880', '眼科', '浙江医科大学', '2014-12-02'),
	('1474209523051925505', 'admin', '2021-12-24 10:45:14.000000', 0, 'admin', '2021-12-24 10:48:38.268000', '副主任医师，2005年获温州医科大学眼视光学士学位，2009年获温州医科大学眼科硕士学位，从事眼科临床工作十多年，有丰富的临床经验，曾赴上海复旦大学附属眼耳鼻喉科医院进修学习眼科各亚专业疾病的诊断和治疗。', 41, '王琼', '本科学士', '眼视光', 15.00, 'https://www.312000.net/upload/dept/5066af47-a4fc-4416-94f0-643c5ccaf21a/image/20200507/390cbc01-c1f4-40e9-a4a4-78a497cdbdc9.jpg', '副主任医师', '1473917353262714880', '眼科', '温州医科大学', '2016-12-07'),
	('1474210972439810049', 'admin', '2021-12-24 10:51:00.020000', 0, NULL, NULL, '科室学科带头人。大学本科，主任医师，中国中西结合学会鼻-鼻窦炎专家委员会委员，浙江省医师协会耳鼻咽喉科医学专委会委员，绍兴市医学会耳鼻咽喉科专业委员会副主任委员，曾先后在中山医科大第三附属医院、浙江大学附属第二医院、中国医学科学院肿瘤医院进修学习，先后发表国内外论文10余篇，主持参与市级课题研究数项。', 44, '刘平', '本科学士', '肿瘤专业', 15.00, 'https://www.312000.net/Upfile/Face/face_89.JPG', '主任医师', '1473918021696360449', '耳鼻咽喉科', '浙江大学', '2015-12-08'),
	('1474211316829917185', 'admin', '2021-12-24 10:52:22.119000', 0, NULL, NULL, '副院长兼骨科学科带头人，绍兴文理学院医学院副院长，教授、主任医师，浙江大学医学院博士生导师。1994年毕业于浙江医科大学，2009年获澳大利亚西澳大学博士学位。中华医学会骨科分会青年委员，浙江省医学会骨科分会常委、浙江省医师协会骨科分会常委，省康复学会脊柱脊髓损伤委员会常委，绍兴市医学会骨科分会主任委员，绍兴市医师协会骨科分会会长。', 39, '钱宇', '博士研究生', '骨科学', 15.00, 'https://www.312000.net/Upfile/Face/face_119.JPG', '主任医师', '1473914763531653120', '骨科', '浙江大学医学院', '2014-12-10'),
	('1474211559671730177', 'admin', '2021-12-24 10:53:20.011000', 0, NULL, NULL, '浙江省康复医学会脊柱微创学组委员，绍兴市医学会骨科分会脊柱组委员。先后赴英国格罗斯特皇家医院，德国纽伦堡大学附属圣玛利安医院，荷兰埃文斯大学访问学习，主要从事各种颈肩痛腰腿痛及四肢骨折创伤的诊断治疗，尤其擅长椎间孔镜下腰椎间盘突出症的微创治疗以及老年性骨质疏松引起的胸腰椎骨折和髋部骨折的微创化手术治疗。', 36, '何磊', '硕士研究生', '骨科', 15.00, 'https://www.312000.net/upload/dept/e58f5a7c-820b-4527-814b-d35b6d9fd7b4/image/20180117/48d2473b-2d11-46bf-a069-e1600292d806.jpg', '副主任医师', '1473914763531653120', '骨科', '温州医科大学', '2015-12-01'),
	('1474211803641810945', 'admin', '2021-12-24 10:54:18.179000', 0, NULL, NULL, '主任医师。1985年毕业于上海第二军医大学，获学士学位，2002年赴上海长海医院进修消化内镜治疗及ERCP术。现任绍兴市消化专业委员会委员。', 45, '张凯杰', '本科学士', '消化内科', 15.00, 'https://www.312000.net/Upfile/Face/face_52.JPG', '主任医师', '1473917781656342528', '消化内科', '上海第二军医大学', '2013-12-09'),
	('1474212105635893249', 'admin', '2021-12-24 10:55:30.183000', 0, NULL, NULL, '中国中西医结合学会疼痛专业委员会癌痛专委会委员；长期从事临床消化科工作，曾先后赴上海长海医院、浙江大学附属第一医院消化科进修；', 37, '秦月花', '硕士研究生', '消化内科医学', 15.00, 'https://www.312000.net/Upfile/Face/face_48.JPG', '主任医师', '1473917781656342528', '消化内科', '上海长海大学', '2016-12-07'),
	('1474212301312757761', 'admin', '2021-12-24 10:56:16.835000', 0, NULL, NULL, '副主任医师，毕业于浙江大学医学院。', 38, '罗文文', '本科学士', '医学', 15.00, 'https://www.312000.net/Upfile/Face/face_249.JPG', '副主任医师', '1473917781656342528', '消化内科', '浙江大学', '2015-12-09');

DROP TABLE IF EXISTS `a_doctor_scheduling`;
CREATE TABLE IF NOT EXISTS `a_doctor_scheduling` (
  `id` varchar(255) COLLATE utf8_croatian_ci NOT NULL,
  `create_by` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int NOT NULL,
  `update_by` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `date` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `doctor_id` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `doctor_name` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `number` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `order_flag` int NOT NULL,
  `step` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_croatian_ci;

DELETE FROM `a_doctor_scheduling`;
INSERT INTO `a_doctor_scheduling` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `date`, `doctor_id`, `doctor_name`, `number`, `order_flag`, `step`) VALUES
	('1474252424544915456', 'admin', '2021-12-24 13:35:42.965000', 0, 'admin', '2021-12-24 15:39:27.698000', '2021-12-24', '1474212301312757761', '罗文文', '1', 1, '0'),
	('1474252424599441408', 'admin', '2021-12-24 13:35:42.968000', 0, 'admin', '2021-12-25 08:56:39.541000', '2021-12-24', '1474212301312757761', '罗文文', '2', 1, '0'),
	('1474252424612024320', 'admin', '2021-12-24 13:35:42.972000', 0, NULL, NULL, '2021-12-24', '1474212301312757761', '罗文文', '3', 0, '0'),
	('1474252424624607232', 'admin', '2021-12-24 13:35:42.974000', 0, 'admin', '2022-01-18 11:53:34.730000', '2021-12-24', '1474212301312757761', '罗文文', '4', 1, '0'),
	('1474252424632995840', 'admin', '2021-12-24 13:35:42.977000', 0, NULL, NULL, '2021-12-24', '1474212301312757761', '罗文文', '5', 0, '0'),
	('1474252424645578752', 'admin', '2021-12-24 13:35:42.979000', 0, NULL, NULL, '2021-12-24', '1474212301312757761', '罗文文', '6', 0, '0'),
	('1474252424653967360', 'admin', '2021-12-24 13:35:42.982000', 0, NULL, NULL, '2021-12-24', '1474212301312757761', '罗文文', '7', 0, '0'),
	('1474252424666550272', 'admin', '2021-12-24 13:35:42.984000', 0, NULL, NULL, '2021-12-24', '1474212301312757761', '罗文文', '8', 0, '0'),
	('1474252424679133184', 'admin', '2021-12-24 13:35:42.987000', 0, NULL, NULL, '2021-12-24', '1474212301312757761', '罗文文', '9', 0, '0'),
	('1474252424687521792', 'admin', '2021-12-24 13:35:42.990000', 0, NULL, NULL, '2021-12-24', '1474212301312757761', '罗文文', '10', 0, '0'),
	('1474274354501128193', 'admin', '2021-12-24 15:02:51.468000', 0, NULL, NULL, '2021-12-24', '1474212105635893249', '秦月花', '1', 0, '1'),
	('1474274354534682624', 'admin', '2021-12-24 15:02:51.472000', 0, NULL, NULL, '2021-12-24', '1474212105635893249', '秦月花', '2', 0, '1'),
	('1474274354543071232', 'admin', '2021-12-24 15:02:51.474000', 0, NULL, NULL, '2021-12-24', '1474212105635893249', '秦月花', '3', 0, '1'),
	('1474274354551459840', 'admin', '2021-12-24 15:02:51.476000', 0, NULL, NULL, '2021-12-24', '1474212105635893249', '秦月花', '4', 0, '1'),
	('1474274354559848448', 'admin', '2021-12-24 15:02:51.479000', 0, NULL, NULL, '2021-12-24', '1474212105635893249', '秦月花', '5', 0, '1'),
	('1483283178180448256', 'admin', '2022-01-18 11:40:42.426000', 0, NULL, NULL, '2022-01-18', '1474212301312757761', '罗文文', '1', 0, '0'),
	('1483283178289500160', 'admin', '2022-01-18 11:40:42.433000', 0, NULL, NULL, '2022-01-18', '1474212301312757761', '罗文文', '2', 0, '0'),
	('1483283178314665984', 'admin', '2022-01-18 11:40:42.439000', 0, NULL, NULL, '2022-01-18', '1474212301312757761', '罗文文', '3', 0, '0'),
	('1483283178335637504', 'admin', '2022-01-18 11:40:42.444000', 0, NULL, NULL, '2022-01-18', '1474212301312757761', '罗文文', '4', 0, '0'),
	('1483283178360803328', 'admin', '2022-01-18 11:40:42.450000', 0, NULL, NULL, '2022-01-18', '1474212301312757761', '罗文文', '5', 0, '0'),
	('1569181545107951617', 'admin', '2022-09-12 12:30:08.999000', 0, NULL, NULL, '2022-09-12', '1474211559671730177', '何磊', '1', 0, '0'),
	('1569181545137311744', 'admin', '2022-09-12 12:30:09.002000', 0, NULL, NULL, '2022-09-12', '1474211559671730177', '何磊', '2', 0, '0'),
	('1569181545145700352', 'admin', '2022-09-12 12:30:09.004000', 0, NULL, NULL, '2022-09-12', '1474211559671730177', '何磊', '3', 0, '0'),
	('1569181545154088960', 'admin', '2022-09-12 12:30:09.006000', 0, NULL, NULL, '2022-09-12', '1474211559671730177', '何磊', '4', 0, '0'),
	('1569181545158283264', 'admin', '2022-09-12 12:30:09.007000', 0, NULL, NULL, '2022-09-12', '1474211559671730177', '何磊', '5', 0, '0');

DROP TABLE IF EXISTS `a_file`;
CREATE TABLE IF NOT EXISTS `a_file` (
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

DELETE FROM `a_file`;
INSERT INTO `a_file` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `name`, `size`, `type`, `url`, `f_key`, `location`) VALUES
	(1527897791781801984, 'admin', '2022-05-21 14:23:15', 0, NULL, NULL, 'gh_57ba35562a20_258.jpg', 43531, 'image/jpeg', 'C:\\oa-file/20220521/1e07b00ffc20427fbdf2c434231c1e1e.jpg', '1e07b00ffc20427fbdf2c434231c1e1e.jpg', 0),
	(1527897840372813824, 'admin', '2022-05-21 14:23:27', 0, NULL, NULL, '美术版权.xlsx', 3605123, 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', 'C:\\oa-file/20220521/4c1dadd45a8a4aca9a9ebd5f3256ba45.xlsx', '4c1dadd45a8a4aca9a9ebd5f3256ba45.xlsx', 0),
	(1527897921570344960, 'admin', '2022-05-21 14:23:46', 0, NULL, NULL, '公司大门通行设备使用说明-V1.pdf', 914877, 'application/pdf', 'C:\\oa-file/20220521/74e865aed9594ea9a9a0536161e40066.pdf', '74e865aed9594ea9a9a0536161e40066.pdf', 0),
	(1527898199690448896, 'admin', '2022-05-21 14:24:52', 0, NULL, NULL, '111.mp4', 180593, 'video/mp4', 'C:\\oa-file/20220521/113344a2389349ed8d02fb7dacfa058f.mp4', '113344a2389349ed8d02fb7dacfa058f.mp4', 0),
	(1527899386892390400, 'admin', '2022-05-21 14:29:36', 0, NULL, NULL, '【模版】需求文档.md.zip', 3104, 'application/x-zip-compressed', 'C:\\oa-file/20220521/dacc0bc61cf34bc18d1259b4ce60d52c.zip', 'dacc0bc61cf34bc18d1259b4ce60d52c.zip', 0),
	(1536614206311370752, 'admin', '2022-06-14 15:39:10', 0, NULL, NULL, '张三签名.png', 1686, 'image/png', 'C:\\\\oa-file/20220614/62a6f2fde5994e5fac6a78aa5ad070ad.png', '62a6f2fde5994e5fac6a78aa5ad070ad.png', 0),
	(1536614698903015424, 'admin', '2022-06-14 15:41:08', 0, NULL, NULL, '张三简历.pdf', 28091, 'application/pdf', 'C:\\\\oa-file/20220614/2a643018f08a4a4bbfacbb19218ab1e2.pdf', '2a643018f08a4a4bbfacbb19218ab1e2.pdf', 0);

DROP TABLE IF EXISTS `a_hospital_news`;
CREATE TABLE IF NOT EXISTS `a_hospital_news` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci NOT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `is_public` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `is_top` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `n_sort` int DEFAULT NULL,
  `new_describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `new_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `period_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `relate_service_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `relate_service_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_croatian_ci;

DELETE FROM `a_hospital_news`;
INSERT INTO `a_hospital_news` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `is_public`, `is_top`, `n_sort`, `new_describe`, `new_title`, `period_time`, `photo`, `relate_service_id`, `relate_service_name`) VALUES
	('1471384104523141121', 'lc_lgj', '2021-12-16 15:38:02.181000', 0, NULL, NULL, 'yes', 'no', 300, '<p><strong>内科-动态咨询</strong></p><p><strong><img src="https://jujia.lucheng.gov.cn/app/file/view/1569192463707541506"/></strong></p>', '内科-动态咨询', '2021-12-08 00:00:00 - 2022-01-22 00:00:00', 'https://jujia.lucheng.gov.cn/app/file/view/1569191594110881795', '', ''),
	('1471393441362612225', 'test_lo', '2021-12-16 16:15:08.265000', 0, NULL, NULL, 'yes', 'no', 100, '<p><strong>口腔科-动态</strong></p><p><strong><img src="https://jujia.lucheng.gov.cn/app/file/view/1569192463707541506"/></strong></p>', '口腔科-动态', '2021-12-06 00:00:00 - 2022-01-20 00:00:00', 'https://jujia.lucheng.gov.cn/app/file/view/1569191594110881795', '', ''),
	('1471398754098745345', 'test_nf', '2021-12-16 16:36:14.949000', 0, 'admin', '2021-12-24 23:32:36.806000', 'yes', 'no', 100, '<p><strong>外科-动态</strong></p><p><strong><img src="https://jujia.lucheng.gov.cn/app/file/view/1569192463707541506"/></strong></p>', '外科-动态', '2021-12-07 00:00:00 - 2022-01-21 00:00:00', 'https://jujia.lucheng.gov.cn/app/file/view/1569191594110881795', '', ''),
	('1471442629454598144', 'test_gg', '2021-12-16 19:30:35.623000', 0, 'test_gg', '2021-12-17 08:51:30.365000', 'yes', 'yes', 100, '<p><strong>眼科-动态</strong></p><p><strong><img src="https://jujia.lucheng.gov.cn/app/file/view/1569192463707541506"/></strong></p>', '眼科-动态', '2021-12-16 00:00:00 - 2022-01-21 00:00:00', 'https://jujia.lucheng.gov.cn/app/file/view/1569191594110881795', '', ''),
	('1471645556723027968', 'test_qnf', '2021-12-17 08:56:57.000000', 0, 'test_nf', '2021-12-17 11:24:00.081000', 'yes', 'yes', 100, '<p><strong>骨科-动态</strong></p><p><strong><img src="https://jujia.lucheng.gov.cn/app/file/view/1569192463707541506"/></strong></p>', '骨科-动态', '2021-12-17 00:00:00 - 2022-01-21 00:00:00', 'https://jujia.lucheng.gov.cn/app/file/view/1569191594110881795', '', ''),
	('1471645650692214785', 'test_qnf', '2021-12-17 08:57:19.000000', 0, 'admin', '2021-12-18 14:01:14.417000', 'yes', 'yes', 100, '<p><strong>口腔预防</strong></p><p><strong><img src="https://jujia.lucheng.gov.cn/app/file/view/1569192463707541506"/></strong></p>', '口腔预防', '2021-12-06 00:00:00 - 2022-01-28 00:00:00', 'https://jujia.lucheng.gov.cn/app/file/view/1569191594110881795', '', ''),
	('1471723157265190912', 'test_lo_lrst', '2021-12-17 14:05:18.659000', 0, 'test_lo_lrst', '2021-12-17 14:08:15.025000', 'yes', 'yes', 100, '<p><strong>消化内科-动态</strong></p><p><strong><img src="https://jujia.lucheng.gov.cn/app/file/view/1569192463707541506"/></strong></p>', '消化内科-动态', '2021-12-17 00:00:00 - 2022-01-21 00:00:00', 'https://jujia.lucheng.gov.cn/app/file/view/1569191594110881795', '', ''),
	('1472087542533853185', 'test_laj', '2021-12-18 14:13:14.887000', 0, 'admin', '2021-12-24 23:32:41.337000', 'yes', 'yes', 100, '<p><strong>神经外科-动态</strong></p><p><strong><img src="https://jujia.lucheng.gov.cn/app/file/view/1569192463707541506"/></strong></p>', '神经外科-动态', '2021-12-18 00:00:00 - 2022-01-29 00:00:00', 'https://jujia.lucheng.gov.cn/app/file/view/1569191594110881795', '', ''),
	('1474551902455861249', 'qmm', '2021-12-25 09:25:44.089000', 0, NULL, NULL, 'yes', 'no', 300, '<p><strong>11111</strong></p><p><strong><img src="https://jujia.lucheng.gov.cn/app/file/view/1569192463707541506"/></strong></p>', '111', '2021-12-25 00:00:00 - 2022-01-21 00:00:00', 'https://jujia.lucheng.gov.cn/app/file/view/1569191594110881795', '', ''),
	('1474565620946309120', 'admin', '2021-12-25 10:20:14.000000', 0, 'admin', '2021-12-25 10:20:31.703000', 'yes', 'no', 400, '<p><strong>内科-动态咨询</strong></p><p><strong><img src="https://jujia.lucheng.gov.cn/app/file/view/1569192463707541506"/></strong></p>', '测试新闻', '2021-12-17 00:00:00 - 2022-01-14 00:00:00', 'https://jujia.lucheng.gov.cn/app/file/view/1569191594110881795', '', '');

DROP TABLE IF EXISTS `a_hospital_order`;
CREATE TABLE IF NOT EXISTS `a_hospital_order` (
  `id` varchar(255) COLLATE utf8_croatian_ci NOT NULL,
  `create_by` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int NOT NULL,
  `update_by` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `date_time` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `doctor_id` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `doctor_name` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `money_data` decimal(19,2) DEFAULT NULL,
  `money_flag` int NOT NULL,
  `number` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `order_id` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `status` int NOT NULL,
  `step` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `user_id` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `user_name` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_croatian_ci;

DELETE FROM `a_hospital_order`;
INSERT INTO `a_hospital_order` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `date_time`, `doctor_id`, `doctor_name`, `money_data`, `money_flag`, `number`, `order_id`, `status`, `step`, `user_id`, `user_name`) VALUES
	('1474283566186762240', 'admin', '2021-12-24 15:39:27.706000', 0, 'admin', '2021-12-24 16:58:34.086000', '2021-12-24', '1474212301312757761', '罗文文', 15.00, 1, '1', '1474252424544915456', 1, '0', '682265633886208', '管理员'),
	('1474544585471561728', 'admin', '2021-12-25 08:56:39.559000', 0, NULL, NULL, '2021-12-24', '1474212301312757761', '罗文文', 15.00, 0, '2', '1474252424599441408', 0, '0', '682265633886208', '管理员'),
	('1483286417563062272', 'admin', '2022-01-18 11:53:34.749000', 0, NULL, NULL, '2021-12-24', '1474212301312757761', '罗文文', 15.00, 0, '4', '1474252424624607232', 0, '0', '682265633886208', '管理员');

DROP TABLE IF EXISTS `a_hospital_subject`;
CREATE TABLE IF NOT EXISTS `a_hospital_subject` (
  `id` varchar(255) COLLATE utf8_croatian_ci NOT NULL,
  `create_by` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int NOT NULL,
  `update_by` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `about` text CHARACTER SET utf8 COLLATE utf8_croatian_ci,
  `duty_doctor` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `start_date` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `sub_code` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `sub_name` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `sub_number` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `super_number` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_croatian_ci;

DELETE FROM `a_hospital_subject`;
INSERT INTO `a_hospital_subject` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `about`, `duty_doctor`, `remark`, `start_date`, `sub_code`, `sub_name`, `sub_number`, `super_number`) VALUES
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

DROP TABLE IF EXISTS `a_log`;
CREATE TABLE IF NOT EXISTS `a_log` (
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
  `code` varchar(255) DEFAULT NULL,
  `device` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DELETE FROM `a_log`;
INSERT INTO `a_log` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `cost_time`, `ip`, `ip_info`, `name`, `request_param`, `request_type`, `request_url`, `username`, `log_type`, `code`, `device`) VALUES
	(1536883393592168448, NULL, '2022-06-15 09:28:50', 0, NULL, '2022-06-15 09:28:50', 287, '127.0.0.1', '本地测试', '登录系统', '{"password":"密码隐藏","code":"8817","saveLogin":"true","captchaId":"ef9e5437ff544c8b91d9810febcb58e7","username":"admin"}', 'POST', '/zwz/login', 'admin', 1, '', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883393592168449, NULL, '2022-06-15 09:28:50', 0, NULL, '2022-06-15 09:28:50', 263, '127.0.0.1', '本地测试', '获取当前登录用户', '{}', 'GET', '/zwz/user/info', 'admin', 2, 'USER-02', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883394787545090, NULL, '2022-06-15 09:28:50', 0, NULL, '2022-06-15 09:28:50', 43, '127.0.0.1', '本地测试', '查询菜单', '{}', 'GET', '/zwz/permission/getMenuList', 'admin', 2, 'PERMISSION-02', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883395219558400, NULL, '2022-06-15 09:28:50', 0, NULL, '2022-06-15 09:28:50', 69, '127.0.0.1', '本地测试', '查询个人门户菜单B', '{}', 'POST', '/zwz/myDoor/getMyDoorList6', 'admin', 2, 'MY-DOOR-02', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883401452294144, NULL, '2022-06-15 09:28:52', 0, NULL, '2022-06-15 09:28:52', 76, '127.0.0.1', '本地测试', '查询日志', '{"logType":"1","pageNumber":"1","endDate":"","pageSize":"15","sort":"createTime","ipInfo":"","startDate":"","key":"","order":"desc"}', 'GET', '/zwz/log/getAllByPage', 'admin', 2, 'LOG-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883413934542848, NULL, '2022-06-15 09:28:54', 0, NULL, '2022-06-15 09:28:54', 40, '127.0.0.1', '本地测试', '查询子部门', '{}', 'GET', '/zwz/department/getByParentId', 'admin', 2, 'DEP-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883415226388480, NULL, '2022-06-15 09:28:55', 0, NULL, '2022-06-15 09:28:55', 39, '127.0.0.1', '本地测试', '查询全部角色', '{}', 'GET', '/zwz/role/getAllList', 'admin', 2, 'ROLE-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883415259942913, NULL, '2022-06-15 09:28:55', 0, NULL, '2022-06-15 09:28:55', 49, '127.0.0.1', '本地测试', '查询单个数据字典的值', '{}', 'GET', '/zwz/dictData/getByType/sex', 'admin', 2, 'DICT_DATA-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883415280914432, NULL, '2022-06-15 09:28:55', 0, NULL, '2022-06-15 09:28:55', 94, '127.0.0.1', '本地测试', '查询用户', '{"pageNumber":"1","departmentId":"","nickname":"","pageSize":"20","sort":"createTime","order":"desc"}', 'GET', '/zwz/user/getUserList', 'admin', 2, 'USER-07', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883415444492288, NULL, '2022-06-15 09:28:55', 0, NULL, '2022-06-15 09:28:55', 36, '127.0.0.1', '本地测试', '查询子部门', '{}', 'GET', '/zwz/department/getByParentId', 'admin', 2, 'DEP-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883420095975424, NULL, '2022-06-15 09:28:56', 0, NULL, '2022-06-15 09:28:56', 40, '127.0.0.1', '本地测试', '查询子部门', '{}', 'GET', '/zwz/department/getByParentId', 'admin', 2, 'DEP-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883420171472896, NULL, '2022-06-15 09:28:56', 0, NULL, '2022-06-15 09:28:56', 58, '127.0.0.1', '本地测试', '查询用户', '{"pageNumber":"1","departmentId":"","pageSize":"10","sort":"createTime","order":"desc"}', 'GET', '/zwz/myUser/getByPage', 'admin', 2, 'USER-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883421131968513, NULL, '2022-06-15 09:28:56', 0, NULL, '2022-06-15 09:28:56', 38, '127.0.0.1', '本地测试', '查询子部门', '{}', 'GET', '/zwz/department/getByParentId', 'admin', 2, 'DEP-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883423262674944, NULL, '2022-06-15 09:28:57', 0, NULL, '2022-06-15 09:28:57', 50, '127.0.0.1', '本地测试', '查询系统文件', '{"pageNumber":"1","endDate":"","fkey":"","name":"","pageSize":"15","sort":"createTime","type":"","startDate":"","order":"desc"}', 'GET', '/zwz/file/getByCondition', 'admin', 2, 'FILE-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883424516771840, NULL, '2022-06-15 09:28:57', 0, NULL, '2022-06-15 09:28:57', 41, '127.0.0.1', '本地测试', '查看单个配置', '{"id":"FILE_HTTP"}', 'GET', '/zwz/setting/getOne', 'admin', 2, 'SETTING-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883424516771841, NULL, '2022-06-15 09:28:57', 0, NULL, '2022-06-15 09:28:57', 34, '127.0.0.1', '本地测试', '查看单个配置', '{"id":"FILE_PATH"}', 'GET', '/zwz/setting/getOne', 'admin', 2, 'SETTING-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883424516771842, NULL, '2022-06-15 09:28:57', 0, NULL, '2022-06-15 09:28:57', 44, '127.0.0.1', '本地测试', '查看单个配置', '{"id":"FILE_VIEW"}', 'GET', '/zwz/setting/getOne', 'admin', 2, 'SETTING-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883426920108032, NULL, '2022-06-15 09:28:58', 0, NULL, '2022-06-15 09:28:58', 55, '127.0.0.1', '本地测试', '查询角色', '{"pageNumber":"1","pageSize":"15","sort":"createTime","order":"desc"}', 'GET', '/zwz/role/getAllByPage', 'admin', 2, 'ROLE-02', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883430367825920, NULL, '2022-06-15 09:28:58', 0, NULL, '2022-06-15 09:28:58', 45, '127.0.0.1', '本地测试', '查询单个数据字典的值', '{}', 'GET', '/zwz/dictData/getByType/permission_type', 'admin', 2, 'DICT_DATA-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883433186398208, NULL, '2022-06-15 09:28:59', 0, NULL, '2022-06-15 09:28:59', 43, '127.0.0.1', '本地测试', '查询日志', '{"logType":"1","pageNumber":"1","endDate":"","pageSize":"15","sort":"createTime","ipInfo":"","startDate":"","key":"","order":"desc"}', 'GET', '/zwz/log/getAllByPage', 'admin', 2, 'LOG-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883444859146240, NULL, '2022-06-15 09:29:02', 0, NULL, '2022-06-15 09:29:02', 50, '127.0.0.1', '本地测试', '查询所有数据字典', '{}', 'GET', '/zwz/dict/getAll', 'admin', 2, 'DICT-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883444947226624, NULL, '2022-06-15 09:29:02', 0, NULL, '2022-06-15 09:29:02', 59, '127.0.0.1', '本地测试', '查询数据字典值', '{"pageNumber":"1","name":"","pageSize":"10","sort":"sortOrder","status":"","order":"asc"}', 'GET', '/zwz/dictData/getByCondition', 'admin', 2, 'DICT_DATA-02', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883446968881152, NULL, '2022-06-15 09:29:02', 0, NULL, '2022-06-15 09:29:02', 43, '127.0.0.1', '本地测试', '查询个人门户菜单B', '{}', 'POST', '/zwz/myDoor/getMyDoorList6', 'admin', 2, 'MY-DOOR-02', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883451238682624, NULL, '2022-06-15 09:29:03', 0, NULL, '2022-06-15 09:29:03', 52, '127.0.0.1', '本地测试', '查询单个数据字典的值', '{}', 'GET', '/zwz/dictData/getByType/sex', 'admin', 2, 'DICT_DATA-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883451347734528, NULL, '2022-06-15 09:29:03', 0, NULL, '2022-06-15 09:29:03', 65, '127.0.0.1', '本地测试', '查询学生', '{"pageNumber":"1","pageSize":"15","sort":"createTime","order":"desc"}', 'GET', '/zwz/student/getByPage', 'admin', 3, 'STUDENT-04', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883452538916864, NULL, '2022-06-15 09:29:04', 0, NULL, '2022-06-15 09:29:04', 43, '127.0.0.1', '本地测试', '查询单个数据字典的值', '{}', 'GET', '/zwz/dictData/getByType/education', 'admin', 2, 'DICT_DATA-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883453881094144, NULL, '2022-06-15 09:29:04', 0, NULL, '2022-06-15 09:29:04', 48, '127.0.0.1', '本地测试', '查询教师', '{"pageNumber":"1","pageSize":"15","sort":"createTime","order":"desc"}', 'GET', '/zwz/teacher/getByPage', 'admin', 3, 'TEACHER-04', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883466526920704, NULL, '2022-06-15 09:29:07', 0, NULL, '2022-06-15 09:29:07', 42, '127.0.0.1', '本地测试', '查询图表数据', '{}', 'GET', '/zwz/teacher/getAntvVoList', 'admin', 4, 'CHART-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883470083690496, NULL, '2022-06-15 09:29:08', 0, NULL, '2022-06-15 09:29:08', 46, '127.0.0.1', '本地测试', '查询个人门户菜单B', '{}', 'POST', '/zwz/myDoor/getMyDoorList6', 'admin', 2, 'MY-DOOR-02', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883482377195520, NULL, '2022-06-15 09:29:11', 0, NULL, '2022-06-15 09:29:11', 41, '127.0.0.1', '本地测试', '查询日志', '{"logType":"1","pageNumber":"1","endDate":"","pageSize":"15","sort":"createTime","ipInfo":"","startDate":"","key":"","order":"desc"}', 'GET', '/zwz/log/getAllByPage', 'admin', 2, 'LOG-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883484109443072, NULL, '2022-06-15 09:29:11', 0, NULL, '2022-06-15 09:29:11', 50, '127.0.0.1', '本地测试', '查询日志', '{"logType":"2","pageNumber":"1","endDate":"","pageSize":"15","sort":"createTime","ipInfo":"","startDate":"","key":"","order":"desc"}', 'GET', '/zwz/log/getAllByPage', 'admin', 2, 'LOG-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883506003709952, NULL, '2022-06-15 09:29:16', 0, NULL, '2022-06-15 09:29:16', 86, '127.0.0.1', '本地测试', '查询日志', '{"logType":"3","pageNumber":"1","endDate":"","pageSize":"15","sort":"createTime","ipInfo":"","startDate":"","key":"","order":"desc"}', 'GET', '/zwz/log/getAllByPage', 'admin', 2, 'LOG-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883509954744320, NULL, '2022-06-15 09:29:17', 0, NULL, '2022-06-15 09:29:17', 38, '127.0.0.1', '本地测试', '查询日志', '{"logType":"4","pageNumber":"1","endDate":"","pageSize":"15","sort":"createTime","ipInfo":"","startDate":"","key":"","order":"desc"}', 'GET', '/zwz/log/getAllByPage', 'admin', 2, 'LOG-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1536883517961670656, NULL, '2022-06-15 09:29:19', 0, NULL, '2022-06-15 09:29:19', 39, '127.0.0.1', '本地测试', '查询日志', '{"logType":"1","pageNumber":"1","endDate":"","pageSize":"15","sort":"createTime","ipInfo":"","startDate":"","key":"","order":"desc"}', 'GET', '/zwz/log/getAllByPage', 'admin', 2, 'LOG-01', 'PC端 | Chrome 101.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180130029473792, NULL, '2022-09-12 12:24:32', 0, NULL, '2022-09-12 12:24:32', 118, '127.0.0.1', '本地测试', '登录系统', '{"password":"","code":"5071","saveLogin":"true","captchaId":"61762a58b8f346eb8a8e853e71192e5a","username":"admin"}', 'POST', '/zwz/login', 'admin', 1, '', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180131317125120, NULL, '2022-09-12 12:24:32', 0, NULL, '2022-09-12 12:24:32', 58, '127.0.0.1', '本地测试', '获取当前登录用户', '{}', 'GET', '/zwz/user/info', 'admin', 2, 'USER-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180132894183424, NULL, '2022-09-12 12:24:32', 0, NULL, '2022-09-12 12:24:32', 166, '127.0.0.1', '本地测试', '查询菜单', '{}', 'GET', '/zwz/permission/getMenuList', 'admin', 2, 'PERMISSION-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180132994846720, NULL, '2022-09-12 12:24:32', 0, NULL, '2022-09-12 12:24:32', 82, '127.0.0.1', '本地测试', '查询个人门户菜单B', '{}', 'POST', '/zwz/myDoor/getMyDoorList6', 'admin', 2, 'MY-DOOR-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180174665256960, NULL, '2022-09-12 12:24:42', 0, NULL, '2022-09-12 12:24:42', 61, '127.0.0.1', '本地测试', '登录系统', '{"password":"","code":"4261","saveLogin":"true","captchaId":"b371a0b885ef4a2a920e499716a31e2f","username":"admin"}', 'POST', '/zwz/login', 'admin', 1, '', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180175445397504, NULL, '2022-09-12 12:24:42', 0, NULL, '2022-09-12 12:24:42', 52, '127.0.0.1', '本地测试', '获取当前登录用户', '{}', 'GET', '/zwz/user/info', 'admin', 2, 'USER-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180176372338688, NULL, '2022-09-12 12:24:43', 0, NULL, '2022-09-12 12:24:43', 114, '127.0.0.1', '本地测试', '查询菜单', '{}', 'GET', '/zwz/permission/getMenuList', 'admin', 2, 'PERMISSION-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180177039233024, NULL, '2022-09-12 12:24:43', 0, NULL, '2022-09-12 12:24:43', 99, '127.0.0.1', '本地测试', '查询个人门户菜单B', '{}', 'POST', '/zwz/myDoor/getMyDoorList6', 'admin', 2, 'MY-DOOR-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180183355854848, NULL, '2022-09-12 12:24:44', 0, NULL, '2022-09-12 12:24:44', 56, '127.0.0.1', '本地测试', '查询单个数据字典的值', '{}', 'GET', '/zwz/dictData/getByType/sex', 'admin', 2, 'DICT_DATA-01', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180183599124480, NULL, '2022-09-12 12:24:44', 0, NULL, '2022-09-12 12:24:44', 99, '127.0.0.1', '本地测试', '查询学生', '{"pageNumber":"1","pageSize":"15","sort":"createTime","order":"desc"}', 'GET', '/zwz/student/getByPage', 'admin', 3, 'STUDENT-04', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180186342199296, NULL, '2022-09-12 12:24:45', 0, NULL, '2022-09-12 12:24:45', 50, '127.0.0.1', '本地测试', '查询个人门户菜单B', '{}', 'POST', '/zwz/myDoor/getMyDoorList6', 'admin', 2, 'MY-DOOR-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180191752851457, NULL, '2022-09-12 12:24:46', 0, NULL, '2022-09-12 12:24:46', 36, '127.0.0.1', '本地测试', '查询单个数据字典的值', '{}', 'GET', '/zwz/dictData/getByType/sex', 'admin', 2, 'DICT_DATA-01', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180191757045761, NULL, '2022-09-12 12:24:46', 0, NULL, '2022-09-12 12:24:46', 33, '127.0.0.1', '本地测试', '查询全部角色', '{}', 'GET', '/zwz/role/getAllList', 'admin', 2, 'ROLE-01', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180191849320448, NULL, '2022-09-12 12:24:46', 0, NULL, '2022-09-12 12:24:46', 76, '127.0.0.1', '本地测试', '查询用户', '{"pageNumber":"1","departmentId":"","nickname":"","pageSize":"20","sort":"createTime","order":"desc"}', 'GET', '/zwz/user/getUserList', 'admin', 2, 'USER-07', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180191920623616, NULL, '2022-09-12 12:24:46', 0, NULL, '2022-09-12 12:24:46', 74, '127.0.0.1', '本地测试', '查询子部门', '{}', 'GET', '/zwz/department/getByParentId', 'admin', 2, 'DEP-01', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180192885313536, NULL, '2022-09-12 12:24:47', 0, NULL, '2022-09-12 12:24:47', 41, '127.0.0.1', '本地测试', '查询子部门', '{}', 'GET', '/zwz/department/getByParentId', 'admin', 2, 'DEP-01', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180196349808640, NULL, '2022-09-12 12:24:47', 0, NULL, '2022-09-12 12:24:47', 41, '127.0.0.1', '本地测试', '查询子部门', '{}', 'GET', '/zwz/department/getByParentId', 'admin', 2, 'DEP-01', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180197650042880, NULL, '2022-09-12 12:24:48', 0, NULL, '2022-09-12 12:24:48', 32, '127.0.0.1', '本地测试', '查询用户', '{"pageNumber":"1","departmentId":"","pageSize":"10","sort":"createTime","order":"desc"}', 'GET', '/zwz/myUser/getByPage', 'admin', 2, 'USER-01', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180197838786560, NULL, '2022-09-12 12:24:48', 0, NULL, '2022-09-12 12:24:48', 41, '127.0.0.1', '本地测试', '查询子部门', '{}', 'GET', '/zwz/department/getByParentId', 'admin', 2, 'DEP-01', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180202087616512, NULL, '2022-09-12 12:24:49', 0, NULL, '2022-09-12 12:24:49', 52, '127.0.0.1', '本地测试', '查询个人门户菜单B', '{}', 'POST', '/zwz/myDoor/getMyDoorList6', 'admin', 2, 'MY-DOOR-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180207208861696, NULL, '2022-09-12 12:24:50', 0, NULL, '2022-09-12 12:24:50', 37, '127.0.0.1', '本地测试', '查询单个数据字典的值', '{}', 'GET', '/zwz/dictData/getByType/sex', 'admin', 2, 'DICT_DATA-01', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180208546844672, NULL, '2022-09-12 12:24:50', 0, NULL, '2022-09-12 12:24:50', 42, '127.0.0.1', '本地测试', '查询学生', '{"pageNumber":"1","pageSize":"15","sort":"createTime","order":"desc"}', 'GET', '/zwz/student/getByPage', 'admin', 3, 'STUDENT-04', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180211516411904, NULL, '2022-09-12 12:24:51', 0, NULL, '2022-09-12 12:24:51', 65, '127.0.0.1', '本地测试', '查询单个数据字典的值', '{}', 'GET', '/zwz/dictData/getByType/education', 'admin', 2, 'DICT_DATA-01', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180211633852416, NULL, '2022-09-12 12:24:51', 0, NULL, '2022-09-12 12:24:51', 53, '127.0.0.1', '本地测试', '查询教师', '{"pageNumber":"1","pageSize":"15","sort":"createTime","order":"desc"}', 'GET', '/zwz/teacher/getByPage', 'admin', 3, 'TEACHER-04', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180997164077056, NULL, '2022-09-12 12:27:58', 0, NULL, '2022-09-12 12:27:58', 60, '127.0.0.1', '本地测试', '登录系统', '{"password":"","code":"0073","saveLogin":"true","captchaId":"a9e88367a20848bf848a6f85010f387f","username":"admin"}', 'POST', '/zwz/login', 'admin', 1, '', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180997214408704, NULL, '2022-09-12 12:27:58', 0, NULL, '2022-09-12 12:27:58', 53, '127.0.0.1', '本地测试', '获取当前登录用户', '{}', 'GET', '/zwz/user/info', 'admin', 2, 'USER-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180997738696704, NULL, '2022-09-12 12:27:59', 0, NULL, '2022-09-12 12:27:59', 68, '127.0.0.1', '本地测试', '查询菜单', '{}', 'GET', '/zwz/permission/getMenuList', 'admin', 2, 'PERMISSION-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569180999139594240, NULL, '2022-09-12 12:27:59', 0, NULL, '2022-09-12 12:27:59', 33, '127.0.0.1', '本地测试', '查询个人门户菜单B', '{}', 'POST', '/zwz/myDoor/getMyDoorList6', 'admin', 2, 'MY-DOOR-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569181011898667008, NULL, '2022-09-12 12:28:02', 0, NULL, '2022-09-12 12:28:02', 23, '127.0.0.1', '本地测试', '查询角色', '{"pageNumber":"1","pageSize":"15","sort":"createTime","order":"desc"}', 'GET', '/zwz/role/getAllByPage', 'admin', 2, 'ROLE-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569181012091604992, NULL, '2022-09-12 12:28:02', 0, NULL, '2022-09-12 12:28:02', 67, '127.0.0.1', '本地测试', '查询全部菜单', '{}', 'GET', '/zwz/permission/getAllList', 'admin', 2, 'PERMISSION-04', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569181075387846656, NULL, '2022-09-12 12:28:17', 0, NULL, '2022-09-12 12:28:17', 91, '127.0.0.1', '本地测试', '修改菜单权限', '{"permIds":"125909152017944576,1464484663442673664,5129710648430593,1464485105081913345,40238597734928384,1464485485316542465,56309618086776832,5129710648430592,5129710648430594,5129710648430595,1530689520276738048,1530689602321518592,39915540965232640,41363147411427328,1535166254703316992,1535166438371889152,1473912920739024896,1473913059595653120,1473913177623367681,1473921681071411200,1473921969287204864,1474248113106587648,1536599942410407936,1536600125332393984,1536600268379131904,1536604417711804416,1536606273959759872,1536606372668510208,1536606464712511488,1536606550951596032,1536606637815631872,1536875505901506560,1474279208996966400,1474279286738391041,1474279419504889857,1474288862284091392,1474559425741197312,1474559508469649408,1474559937995739137,1474566156600872961,1474566614732115969,1474572846901628928,1474574872549134336,1474584028081426432","roleId":"496138616573952"}', 'POST', '/zwz/role/editRolePerm', 'admin', 2, 'ROLE-04', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569181076516114432, NULL, '2022-09-12 12:28:17', 0, NULL, '2022-09-12 12:28:17', 63, '127.0.0.1', '本地测试', '查询菜单', '{}', 'GET', '/zwz/permission/getMenuList', 'admin', 2, 'PERMISSION-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569181077023625216, NULL, '2022-09-12 12:28:17', 0, NULL, '2022-09-12 12:28:17', 27, '127.0.0.1', '本地测试', '查询角色', '{"pageNumber":"1","pageSize":"15","sort":"createTime","order":"desc"}', 'GET', '/zwz/role/getAllByPage', 'admin', 2, 'ROLE-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569181090202128384, NULL, '2022-09-12 12:28:21', 0, NULL, '2022-09-12 12:28:21', 27, '127.0.0.1', '本地测试', '查询个人门户菜单B', '{}', 'POST', '/zwz/myDoor/getMyDoorList6', 'admin', 2, 'MY-DOOR-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569181506910425088, NULL, '2022-09-12 12:30:00', 0, NULL, '2022-09-12 12:30:00', 64, '127.0.0.1', '本地测试', '查询菜单', '{}', 'GET', '/zwz/permission/getMenuList', 'admin', 2, 'PERMISSION-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569181569036455936, NULL, '2022-09-12 12:30:15', 0, NULL, '2022-09-12 12:30:15', 88, '127.0.0.1', '本地测试', '查询个人门户菜单B', '{}', 'POST', '/zwz/myDoor/getMyDoorList6', 'admin', 2, 'MY-DOOR-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569181595125026816, NULL, '2022-09-12 12:30:21', 0, NULL, '2022-09-12 12:30:21', 87, '127.0.0.1', '本地测试', '查询个人门户菜单B', '{}', 'POST', '/zwz/myDoor/getMyDoorList6', 'admin', 2, 'MY-DOOR-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569181919277617152, NULL, '2022-09-12 12:31:38', 0, NULL, '2022-09-12 12:31:38', 98, '127.0.0.1', '本地测试', '查询个人门户菜单B', '{}', 'POST', '/zwz/myDoor/getMyDoorList6', 'admin', 2, 'MY-DOOR-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569181923153154048, NULL, '2022-09-12 12:31:39', 0, NULL, '2022-09-12 12:31:39', 46, '127.0.0.1', '本地测试', '查询子部门', '{}', 'GET', '/zwz/department/getByParentId', 'admin', 2, 'DEP-01', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569181924457582592, NULL, '2022-09-12 12:31:39', 0, NULL, '2022-09-12 12:31:39', 26, '127.0.0.1', '本地测试', '查询全部角色', '{}', 'GET', '/zwz/role/getAllList', 'admin', 2, 'ROLE-01', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569181924507914242, NULL, '2022-09-12 12:31:39', 0, NULL, '2022-09-12 12:31:39', 46, '127.0.0.1', '本地测试', '查询单个数据字典的值', '{}', 'GET', '/zwz/dictData/getByType/sex', 'admin', 2, 'DICT_DATA-01', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569181924541468672, NULL, '2022-09-12 12:31:40', 0, NULL, '2022-09-12 12:31:40', 90, '127.0.0.1', '本地测试', '查询用户', '{"pageNumber":"1","departmentId":"","nickname":"","pageSize":"20","sort":"createTime","order":"desc"}', 'GET', '/zwz/user/getUserList', 'admin', 2, 'USER-07', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569181924625354753, NULL, '2022-09-12 12:31:40', 0, NULL, '2022-09-12 12:31:40', 32, '127.0.0.1', '本地测试', '查询子部门', '{}', 'GET', '/zwz/department/getByParentId', 'admin', 2, 'DEP-01', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569181928471531520, NULL, '2022-09-12 12:31:40', 0, NULL, '2022-09-12 12:31:40', 50, '127.0.0.1', '本地测试', '查询个人门户菜单B', '{}', 'POST', '/zwz/myDoor/getMyDoorList6', 'admin', 2, 'MY-DOOR-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569181946360238080, NULL, '2022-09-12 12:31:45', 0, NULL, '2022-09-12 12:31:45', 35, '127.0.0.1', '本地测试', '查询个人门户菜单B', '{}', 'POST', '/zwz/myDoor/getMyDoorList6', 'admin', 2, 'MY-DOOR-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569190332460044288, NULL, '2022-09-12 13:05:04', 0, NULL, '2022-09-12 13:05:04', 93, '127.0.0.1', '本地测试', '查询菜单', '{}', 'GET', '/zwz/permission/getMenuList', 'admin', 2, 'PERMISSION-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569190573301174272, NULL, '2022-09-12 13:06:02', 0, NULL, '2022-09-12 13:06:02', 100, '127.0.0.1', '本地测试', '查询菜单', '{}', 'GET', '/zwz/permission/getMenuList', 'admin', 2, 'PERMISSION-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569191092140773376, NULL, '2022-09-12 13:08:05', 0, NULL, '2022-09-12 13:08:05', 95, '127.0.0.1', '本地测试', '查询菜单', '{}', 'GET', '/zwz/permission/getMenuList', 'admin', 2, 'PERMISSION-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569191682900103168, NULL, '2022-09-12 13:10:26', 0, NULL, '2022-09-12 13:10:26', 95, '127.0.0.1', '本地测试', '查询菜单', '{}', 'GET', '/zwz/permission/getMenuList', 'admin', 2, 'PERMISSION-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569191908763373568, NULL, '2022-09-12 13:11:20', 0, NULL, '2022-09-12 13:11:20', 93, '127.0.0.1', '本地测试', '查询菜单', '{}', 'GET', '/zwz/permission/getMenuList', 'admin', 2, 'PERMISSION-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569192504060940288, NULL, '2022-09-12 13:13:42', 0, NULL, '2022-09-12 13:13:42', 67, '127.0.0.1', '本地测试', '查询菜单', '{}', 'GET', '/zwz/permission/getMenuList', 'admin', 2, 'PERMISSION-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569192568984571904, NULL, '2022-09-12 13:13:57', 0, NULL, '2022-09-12 13:13:57', 71, '127.0.0.1', '本地测试', '查询菜单', '{}', 'GET', '/zwz/permission/getMenuList', 'admin', 2, 'PERMISSION-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569192615054807040, NULL, '2022-09-12 13:14:08', 0, NULL, '2022-09-12 13:14:08', 74, '127.0.0.1', '本地测试', '查询菜单', '{}', 'GET', '/zwz/permission/getMenuList', 'admin', 2, 'PERMISSION-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569192635841777664, NULL, '2022-09-12 13:14:13', 0, NULL, '2022-09-12 13:14:13', 31, '127.0.0.1', '本地测试', '查询菜单', '{}', 'GET', '/zwz/permission/getMenuList', 'admin', 2, 'PERMISSION-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569192685816909824, NULL, '2022-09-12 13:14:25', 0, NULL, '2022-09-12 13:14:25', 122, '127.0.0.1', '本地测试', '查询菜单', '{}', 'GET', '/zwz/permission/getMenuList', 'admin', 2, 'PERMISSION-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569192739541749760, NULL, '2022-09-12 13:14:38', 0, NULL, '2022-09-12 13:14:38', 70, '127.0.0.1', '本地测试', '查询菜单', '{}', 'GET', '/zwz/permission/getMenuList', 'admin', 2, 'PERMISSION-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569192860501282816, NULL, '2022-09-12 13:15:07', 0, NULL, '2022-09-12 13:15:07', 93, '127.0.0.1', '本地测试', '查询菜单', '{}', 'GET', '/zwz/permission/getMenuList', 'admin', 2, 'PERMISSION-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569192897830588416, NULL, '2022-09-12 13:15:16', 0, NULL, '2022-09-12 13:15:16', 87, '127.0.0.1', '本地测试', '查询个人门户菜单B', '{}', 'POST', '/zwz/myDoor/getMyDoorList6', 'admin', 2, 'MY-DOOR-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569192926670622720, NULL, '2022-09-12 13:15:23', 0, NULL, '2022-09-12 13:15:23', 92, '127.0.0.1', '本地测试', '查询个人门户菜单B', '{}', 'POST', '/zwz/myDoor/getMyDoorList6', 'admin', 2, 'MY-DOOR-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569192937840054272, NULL, '2022-09-12 13:15:25', 0, NULL, '2022-09-12 13:15:25', 53, '127.0.0.1', '本地测试', '查询单个数据字典的值', '{}', 'GET', '/zwz/dictData/getByType/sex', 'admin', 2, 'DICT_DATA-01', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569193030358011904, NULL, '2022-09-12 13:15:47', 0, NULL, '2022-09-12 13:15:47', 105, '127.0.0.1', '本地测试', '修改个人门户菜单', '{"str":"hospitalSubjectZWZ666zwzDoctorZWZ666pushOrderZWZ666guaOrderZWZ666myOrderZWZ666hospitalNews"}', 'POST', '/zwz/myDoor/setMyDoorList', 'admin', 2, 'MY-DOOR-03', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569193037580603392, NULL, '2022-09-12 13:15:49', 0, NULL, '2022-09-12 13:15:49', 92, '127.0.0.1', '本地测试', '查询个人门户菜单B', '{}', 'POST', '/zwz/myDoor/getMyDoorList6', 'admin', 2, 'MY-DOOR-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569193060632498176, NULL, '2022-09-12 13:15:55', 0, NULL, '2022-09-12 13:15:55', 137, '127.0.0.1', '本地测试', '查询个人门户菜单B', '{}', 'POST', '/zwz/myDoor/getMyDoorList6', 'admin', 2, 'MY-DOOR-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569193072703705088, NULL, '2022-09-12 13:15:57', 0, NULL, '2022-09-12 13:15:57', 57, '127.0.0.1', '本地测试', '查询单个数据字典的值', '{}', 'GET', '/zwz/dictData/getByType/sex', 'admin', 2, 'DICT_DATA-01', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569193073970384896, NULL, '2022-09-12 13:15:58', 0, NULL, '2022-09-12 13:15:58', 50, '127.0.0.1', '本地测试', '查询学生', '{"pageNumber":"1","pageSize":"15","sort":"createTime","order":"desc"}', 'GET', '/zwz/student/getByPage', 'admin', 3, 'STUDENT-04', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569193077934002184, NULL, '2022-09-12 13:15:59', 0, NULL, '2022-09-12 13:15:59', 65, '127.0.0.1', '本地测试', '查询个人门户菜单B', '{}', 'POST', '/zwz/myDoor/getMyDoorList6', 'admin', 2, 'MY-DOOR-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016'),
	(1569193096422494208, NULL, '2022-09-12 13:16:03', 0, NULL, '2022-09-12 13:16:03', 63, '127.0.0.1', '本地测试', '查询个人门户菜单B', '{}', 'POST', '/zwz/myDoor/getMyDoorList6', 'admin', 2, 'MY-DOOR-02', 'PC端 | Chrome 104.0.0.0 | Windows Windows 10 or Windows Server 2016');

DROP TABLE IF EXISTS `a_message_board`;
CREATE TABLE IF NOT EXISTS `a_message_board` (
  `id` varchar(255) COLLATE utf8_croatian_ci NOT NULL,
  `create_by` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int NOT NULL,
  `update_by` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `content` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `date` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `reply_id` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `user_id` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  `user_name` varchar(255) COLLATE utf8_croatian_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_croatian_ci;

DELETE FROM `a_message_board`;
INSERT INTO `a_message_board` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `content`, `date`, `reply_id`, `user_id`, `user_name`) VALUES
	('1474578672475312129', 'admin', '2021-12-25 11:12:06.000000', 0, 'admin', '2021-12-25 11:32:44.627000', '你好呀', '2021-12-25 11:12:06', '', '682265633886208', '管理员'),
	('1474581367550513153', 'admin', '2021-12-25 11:22:49.080000', 0, NULL, NULL, '你也好', '2021-12-25 11:22:49', '1474578672475312129', '682265633886208', '管理员'),
	('1483304372770312193', 'admin', '2022-01-18 13:04:55.607000', 0, NULL, NULL, '这是我的留言，啦啦啦~', '2022-01-18 13:04:55', '', '682265633886208', '管理员'),
	('1483305179439828993', 'admin', '2022-01-18 13:08:07.916000', 0, NULL, NULL, '这是我的回复哦，祝您生活愉快！', '2022-01-18 13:08:07', '1483304372770312193', '682265633886208', '管理员');

DROP TABLE IF EXISTS `a_permission`;
CREATE TABLE IF NOT EXISTS `a_permission` (
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
  `show_always` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DELETE FROM `a_permission`;
INSERT INTO `a_permission` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `description`, `name`, `parent_id`, `type`, `sort_order`, `component`, `path`, `title`, `icon`, `level`, `button_type`, `status`, `show_always`) VALUES
	(5129710648430592, '', '2022-03-20 09:46:20', 0, 'admin', '2022-05-29 13:05:28', '', 'roleMenu', 125909152017944576, 0, 4.00, 'Main', '/roleMenu', '权限管理', 'md-desktop', 1, '', 0, b'1'),
	(5129710648430593, '', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', 'user-admin', 1464484663442673664, 0, 1.10, 'roster/user/user', 'user', '用户管理', 'md-person', 2, '', 0, b'1'),
	(5129710648430594, '', '2022-03-20 09:46:20', 0, 'admin', '2022-06-14 11:23:18', '', 'role-manage', 5129710648430592, 0, 1.00, 'role/role/index', 'role', '角色支持', 'md-contacts', 2, '', 0, b'1'),
	(5129710648430595, '', '2022-03-20 09:46:20', 0, 'admin', '2022-06-14 11:23:13', '', 'menu-manage', 5129710648430592, 0, 2.00, 'menu/menu/index', 'menu', '菜单支持', 'md-menu', 2, '', 0, b'1'),
	(39915540965232640, '', '2022-03-20 09:46:20', 0, 'admin', '2022-06-10 15:45:37', '', 'monitor', 125909152017944576, 0, 6.00, 'Main', '/monitor', '日志管理', 'ios-analytics', 1, '', 0, b'1'),
	(40238597734928384, '', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', 'department-admin', 1464485105081913345, 0, 1.20, 'roster/department/department', 'department-admin', '部门管理', 'md-git-branch', 2, '', 0, b'1'),
	(41363147411427328, '', '2022-03-20 09:46:20', 0, 'admin', '2022-05-21 14:15:44', '', 'log-manage', 39915540965232640, 0, 2.20, 'log/log/index', 'log', '日志管理', 'md-list-box', 2, '', 0, b'1'),
	(56309618086776832, '', '2022-03-20 09:46:20', 0, 'admin', '2022-05-29 07:18:02', '', 'file-admin', 1464485485316542465, 0, 1.00, 'file/file/index', 'file', '云盘', 'ios-folder', 2, '', 0, b'1'),
	(125909152017944576, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', 'zwz', 0, -1, 1.00, '', '', '数据中心', 'md-home', 0, '', 0, b'1'),
	(1464484663442673664, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '', 'userTwoMenu', 125909152017944576, 0, 1.00, 'Main', '/userTwoMenu', '用户管理', 'md-analytics', 1, '', 0, b'1'),
	(1464485105081913345, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', NULL, 'depTwoMenu', 125909152017944576, 0, 2.00, 'Main', '/depTwoMenu', '部门管理', 'ios-apps', 1, '', 0, b'1'),
	(1464485485316542465, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-05-21 14:13:42', '', 'fileAdmin', 125909152017944576, 0, 3.00, 'Main', '/fileAdmin', '文件管理', 'md-basketball', 1, '', 0, b'1'),
	(1473912920739024896, 'admin', '2021-12-23 15:06:39', 0, 'admin', '2021-12-23 15:06:39', NULL, 'doctorAndSub', NULL, -1, 2.00, NULL, NULL, '科室医生', 'md-aperture', 0, NULL, 0, b'1'),
	(1473913059595653120, 'admin', '2021-12-23 15:07:12', 0, 'admin', '2021-12-23 15:07:12', NULL, 'hospitalSubjectTwo', 1473912920739024896, 0, 1.00, 'Main', '/hospitalSubjectTwo', '科室管理', 'md-analytics', 1, '', 0, b'1'),
	(1473913177623367681, 'admin', '2021-12-23 15:07:40', 0, 'admin', '2021-12-23 15:07:40', NULL, 'hospitalSubject', 1473913059595653120, 0, 1.00, 'doctor/hospitalSubject/index', 'hospitalSubject', '科室管理', 'ios-apps', 2, '', 0, b'1'),
	(1473921681071411200, 'admin', '2021-12-23 15:41:28', 0, 'admin', '2021-12-23 15:41:28', NULL, 'doctorTwo', 1473912920739024896, 0, 2.00, 'Main', '/doctorTwo', '医生管理', 'md-aperture', 1, '', 0, b'1'),
	(1473921969287204864, 'admin', '2021-12-23 15:42:36', 0, 'admin', '2021-12-23 15:42:36', NULL, 'zwzDoctor', 1473921681071411200, 0, 1.00, 'doctor/doctor/index', 'zwzDoctor', '医生管理', 'ios-basket', 2, '', 0, b'1'),
	(1474248113106587648, 'admin', '2021-12-24 13:18:35', 0, 'admin', '2021-12-24 13:18:35', NULL, 'pushOrder', 1473921681071411200, 0, 2.00, 'doctor/pushOrder/index', 'pushOrder', '医生放号', 'md-aperture', 2, '', 0, b'1'),
	(1474279208996966400, 'admin', '2021-12-24 15:22:09', 0, 'admin', '2021-12-24 15:22:09', NULL, 'orderHao', NULL, -1, 3.00, NULL, NULL, '预约挂号', 'md-aperture', 0, NULL, 0, b'1'),
	(1474279286738391041, 'admin', '2021-12-24 15:22:27', 0, 'admin', '2021-12-24 15:22:27', NULL, 'orderNumberTwo', 1474279208996966400, 0, 1.00, 'Main', '/orderNumberTwo', '预约挂号', 'md-aperture', 1, '', 0, b'1'),
	(1474279419504889857, 'admin', '2021-12-24 15:22:59', 0, 'admin', '2021-12-24 15:22:59', NULL, 'guaOrder', 1474279286738391041, 0, 1.00, 'doctor/guaOrder/index', 'guaOrder', '预约挂号', 'md-at', 2, '', 0, b'1'),
	(1474288862284091392, 'admin', '2021-12-24 16:00:30', 0, 'admin', '2021-12-24 16:00:30', NULL, 'myOrder', 1474279286738391041, 0, 2.00, 'doctor/myOrder/index', 'myOrder', '我的挂号', 'ios-apps', 2, '', 0, b'1'),
	(1474559425741197312, 'admin', '2021-12-25 09:55:38', 0, 'admin', '2021-12-25 09:55:38', NULL, 'hospNews', NULL, -1, 4.00, NULL, NULL, '医院时政', 'md-aperture', 0, NULL, 0, b'1'),
	(1474559508469649408, 'admin', '2021-12-25 09:55:57', 0, 'admin', '2021-12-25 09:55:57', NULL, 'hospNewTwo', 1474559425741197312, 0, 1.00, 'Main', '/hospNewTwo', '医院新闻', 'md-attach', 1, '', 0, b'1'),
	(1474559937995739137, 'admin', '2021-12-25 09:57:40', 0, 'admin', '2021-12-25 10:23:43', '', 'hospitalNews', 1474559508469649408, 0, 1.00, 'doctor/hospitalNews/index', 'hospitalNews', '医院新闻管理', 'md-bowtie', 2, '', 0, b'1'),
	(1474566156600872961, 'admin', '2021-12-25 10:22:23', 0, 'admin', '2021-12-25 10:22:37', '', '', 1474559937995739137, 1, 1.00, '', 'setDynamicNewTop', '置顶医院新闻', '', 3, 'setDynamicNewTop', 0, b'1'),
	(1474566614732115969, 'admin', '2021-12-25 10:24:12', 0, 'admin', '2021-12-25 10:24:12', NULL, 'userNews', 1474559508469649408, 0, 2.00, 'doctor/userNews/index', 'userNews', '医院新闻浏览', 'ios-apps', 2, '', 0, b'1'),
	(1474572846901628928, 'admin', '2021-12-25 10:48:58', 0, 'admin', '2021-12-25 10:48:58', NULL, 'replyMessage', 1474559425741197312, 0, 2.00, 'Main', '/replyMessage', '留言建议', 'md-aperture', 1, '', 0, b'1'),
	(1474574872549134336, 'admin', '2021-12-25 10:57:01', 0, 'admin', '2021-12-25 10:57:01', NULL, 'messageBoard', 1474572846901628928, 0, 1.00, 'doctor/messageBoard/index', 'messageBoard', '医院留言板', 'ios-brush', 2, '', 0, b'1'),
	(1474584028081426432, 'admin', '2021-12-25 11:33:23', 0, 'admin', '2021-12-25 11:33:23', NULL, 'userBoard', 1474572846901628928, 0, 2.00, 'doctor/userBoard/index', 'userBoard', '用户留言', 'md-brush', 2, '', 0, b'1'),
	(1530689520276738048, 'admin', '2022-05-29 07:16:35', 0, NULL, NULL, NULL, 'dictMenu', 125909152017944576, 0, 5.00, 'Main', '/dictMenu', '数据字典', 'ios-apps', 1, '', 0, b'1'),
	(1530689602321518592, 'admin', '2022-05-29 07:16:55', 0, NULL, NULL, NULL, 'dict', 1530689520276738048, 0, 1.00, 'dict/dict/index', 'dict', '数据字典', 'ios-apps', 2, '', 0, b'1'),
	(1535166254703316992, 'admin', '2022-06-10 15:45:32', 0, NULL, NULL, NULL, 'codeCreate', 125909152017944576, 0, 7.00, 'Main', '/codeCreate', '代码生成', 'ios-brush', 1, '', 0, b'1'),
	(1535166438371889152, 'admin', '2022-06-10 15:46:16', 0, NULL, NULL, NULL, 'vue', 1535166254703316992, 0, 1.00, 'code/vue/index', 'vue', '前端表格', 'md-bug', 2, '', 0, b'1'),
	(1536599942410407936, 'admin', '2022-06-14 14:42:30', 0, NULL, NULL, NULL, 'demo', NULL, -1, 2.00, NULL, NULL, '样例演示', 'md-bulb', 0, NULL, 0, NULL),
	(1536600125332393984, 'admin', '2022-06-14 14:43:13', 0, 'admin', '2022-06-14 15:10:31', '', 'tableDemo', 1536599942410407936, 0, 1.00, 'Main', '/tableDemo', '数据展示', 'ios-apps', 1, '', 0, NULL),
	(1536600268379131904, 'admin', '2022-06-14 14:43:47', 0, NULL, NULL, NULL, 'demo1', 1536600125332393984, 0, 1.00, 'demo/demo1/index', 'demo1', '代码生成样例', 'md-finger-print', 2, '', 0, NULL),
	(1536604417711804416, 'admin', '2022-06-14 15:00:17', 0, 'admin', '2022-06-14 15:31:57', '', 'demo2', 1536600125332393984, 0, 2.00, 'demo/demo2/index', 'demo2', '复杂表格样例', 'md-archive', 2, '', 0, NULL),
	(1536606273959759872, 'admin', '2022-06-14 15:07:39', 0, 'admin', '2022-06-14 15:10:23', '', 'antv', 1536599942410407936, 0, 2.00, 'Main', '/antv', '图表展示', 'ios-pulse', 1, '', 0, NULL),
	(1536606372668510208, 'admin', '2022-06-14 15:08:03', 0, NULL, NULL, NULL, 'antv1', 1536606273959759872, 0, 1.00, 'demo/antv1/index', 'antv1', '条形图', 'md-pricetags', 2, '', 0, NULL),
	(1536606464712511488, 'admin', '2022-06-14 15:08:25', 0, NULL, NULL, NULL, 'antv2', 1536606273959759872, 0, 2.00, 'demo/antv2/index', 'antv2', '饼图', 'md-radio-button-off', 2, '', 0, NULL),
	(1536606550951596032, 'admin', '2022-06-14 15:08:45', 0, NULL, NULL, NULL, 'antv3', 1536606273959759872, 0, 3.00, 'demo/antv3/index', 'antv3', '折线图', 'ios-nutrition', 2, '', 0, NULL),
	(1536606637815631872, 'admin', '2022-06-14 15:09:06', 0, NULL, NULL, NULL, 'antv4', 1536606273959759872, 0, 4.00, 'demo/antv4/index', 'antv4', '柱状图', 'ios-phone-portrait', 2, '', 0, NULL),
	(1536875505901506560, 'admin', '2022-06-15 08:57:29', 0, 'admin', '2022-06-15 09:01:01', '', 'antvActive', 1536606273959759872, 0, 5.00, 'demo/antvActive/index', 'antvActive', '动态图表样例', 'ios-apps', 2, '', 0, NULL);

DROP TABLE IF EXISTS `a_role`;
CREATE TABLE IF NOT EXISTS `a_role` (
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

DELETE FROM `a_role`;
INSERT INTO `a_role` (`id`, `create_by`, `create_time`, `update_by`, `update_time`, `name`, `del_flag`, `default_role`, `description`, `data_type`) VALUES
	(496138616573952, '', '2022-03-20 09:46:20', 'admin', '2022-03-20 09:46:20', 'ROLE_ADMIN', 0, NULL, '超级管理员', 0),
	(496138616573953, '', '2022-03-20 09:46:20', 'admin', '2022-03-20 09:46:20', 'ROLE_USER', 0, b'1', '普通用户', 0);

DROP TABLE IF EXISTS `a_role_permission`;
CREATE TABLE IF NOT EXISTS `a_role_permission` (
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

DELETE FROM `a_role_permission`;
INSERT INTO `a_role_permission` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `permission_id`, `role_id`) VALUES
	(1464514325862551552, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 125909152017944576, 496138616573952),
	(1464514325862551553, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 1464484663442673664, 496138616573952),
	(1464514325862551554, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 5129710648430593, 496138616573952),
	(1464514325862551563, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 1464485105081913345, 496138616573952),
	(1464514325862551564, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 40238597734928384, 496138616573952),
	(1464514325862551568, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 1464485485316542465, 496138616573952),
	(1464514325862551569, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 56309618086776832, 496138616573952),
	(1464514325862551583, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 5129710648430592, 496138616573952),
	(1464514325862551584, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 5129710648430594, 496138616573952),
	(1464514325862551590, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 5129710648430595, 496138616573952),
	(1464514325862551594, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 39915540965232640, 496138616573952),
	(1464514325862551595, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 41363147411427328, 496138616573952),
	(1530689746903371801, 'admin', '2022-05-29 07:17:29', 0, NULL, NULL, 1530689520276738048, 496138616573952),
	(1530689747004035072, 'admin', '2022-05-29 07:17:29', 0, NULL, NULL, 1530689602321518592, 496138616573952),
	(1535166468671541252, 'admin', '2022-06-10 15:46:23', 0, NULL, NULL, 1535166254703316992, 496138616573952),
	(1535166468684124160, 'admin', '2022-06-10 15:46:23', 0, NULL, NULL, 1535166438371889152, 496138616573952),
	(1536600292156641281, 'admin', '2022-06-14 14:43:53', 0, NULL, NULL, 1536599942410407936, 496138616573952),
	(1536600292173418496, 'admin', '2022-06-14 14:43:53', 0, NULL, NULL, 1536600125332393984, 496138616573952),
	(1536600292186001408, 'admin', '2022-06-14 14:43:53', 0, NULL, NULL, 1536600268379131904, 496138616573952),
	(1536604439178252292, 'admin', '2022-06-14 15:00:22', 0, NULL, NULL, 1536604417711804416, 496138616573952),
	(1536606659718287364, 'admin', '2022-06-14 15:09:11', 0, NULL, NULL, 1536606273959759872, 496138616573952),
	(1536606659730870272, 'admin', '2022-06-14 15:09:11', 0, NULL, NULL, 1536606372668510208, 496138616573952),
	(1536606659735064576, 'admin', '2022-06-14 15:09:11', 0, NULL, NULL, 1536606464712511488, 496138616573952),
	(1536606659743453184, 'admin', '2022-06-14 15:09:11', 0, NULL, NULL, 1536606550951596032, 496138616573952),
	(1536606659751841792, 'admin', '2022-06-14 15:09:11', 0, NULL, NULL, 1536606637815631872, 496138616573952),
	(1536875525769924610, 'admin', '2022-06-15 08:57:34', 0, NULL, NULL, 1536875505901506560, 496138616573952),
	(1569181075241046039, 'admin', '2022-09-12 12:28:17', 0, NULL, NULL, 1473912920739024896, 496138616573952),
	(1569181075266211840, 'admin', '2022-09-12 12:28:17', 0, NULL, NULL, 1473913059595653120, 496138616573952),
	(1569181075274600448, 'admin', '2022-09-12 12:28:17', 0, NULL, NULL, 1473913177623367681, 496138616573952),
	(1569181075278794752, 'admin', '2022-09-12 12:28:17', 0, NULL, NULL, 1473921681071411200, 496138616573952),
	(1569181075287183360, 'admin', '2022-09-12 12:28:17', 0, NULL, NULL, 1473921969287204864, 496138616573952),
	(1569181075295571968, 'admin', '2022-09-12 12:28:17', 0, NULL, NULL, 1474248113106587648, 496138616573952),
	(1569181075303960576, 'admin', '2022-09-12 12:28:17', 0, NULL, NULL, 1474279208996966400, 496138616573952),
	(1569181075308154880, 'admin', '2022-09-12 12:28:17', 0, NULL, NULL, 1474279286738391041, 496138616573952),
	(1569181075312349184, 'admin', '2022-09-12 12:28:17', 0, NULL, NULL, 1474279419504889857, 496138616573952),
	(1569181075316543488, 'admin', '2022-09-12 12:28:17', 0, NULL, NULL, 1474288862284091392, 496138616573952),
	(1569181075324932096, 'admin', '2022-09-12 12:28:17', 0, NULL, NULL, 1474559425741197312, 496138616573952),
	(1569181075329126400, 'admin', '2022-09-12 12:28:17', 0, NULL, NULL, 1474559508469649408, 496138616573952),
	(1569181075333320704, 'admin', '2022-09-12 12:28:17', 0, NULL, NULL, 1474559937995739137, 496138616573952),
	(1569181075341709312, 'admin', '2022-09-12 12:28:17', 0, NULL, NULL, 1474566156600872961, 496138616573952),
	(1569181075345903616, 'admin', '2022-09-12 12:28:17', 0, NULL, NULL, 1474566614732115969, 496138616573952),
	(1569181075350097920, 'admin', '2022-09-12 12:28:17', 0, NULL, NULL, 1474572846901628928, 496138616573952),
	(1569181075358486528, 'admin', '2022-09-12 12:28:17', 0, NULL, NULL, 1474574872549134336, 496138616573952),
	(1569181075366875136, 'admin', '2022-09-12 12:28:17', 0, NULL, NULL, 1474584028081426432, 496138616573952);

DROP TABLE IF EXISTS `a_setting`;
CREATE TABLE IF NOT EXISTS `a_setting` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `value` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DELETE FROM `a_setting`;
INSERT INTO `a_setting` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `value`) VALUES
	('FILE_HTTP', NULL, NULL, 0, 'admin', '2022-05-28 15:57:20', 'http://'),
	('FILE_PATH', NULL, NULL, 0, 'admin', '2022-05-28 15:57:19', 'C:\\\\oa-file'),
	('FILE_VIEW', NULL, NULL, 0, 'admin', '2022-05-28 15:57:20', '127.0.0.1:8080/zwz/file/view'),
	('LOCAL_OSS', 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', '{"serviceName":"LOCAL_OSS","endpoint":"127.0.0.1:8080/zwz/file/view","http":"http://","filePath":"C:\\\\oa-file"}'),
	('OSS_USED', 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 'LOCAL_OSS');

DROP TABLE IF EXISTS `a_student`;
CREATE TABLE IF NOT EXISTS `a_student` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci NOT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int NOT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `number` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `school` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `age` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_croatian_ci;

DELETE FROM `a_student`;
INSERT INTO `a_student` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `name`, `number`, `school`, `sex`, `age`) VALUES
	('1536601255475023872', 'admin', '2022-06-14 14:47:42.637000', 0, NULL, NULL, '张三', '001', '北京大学', '男', 18.00),
	('1536601308352614400', 'admin', '2022-06-14 14:47:55.244000', 0, NULL, NULL, '李四', '002', '清华大学', '女', 19.00),
	('1536601388044390400', 'admin', '2022-06-14 14:48:14.243000', 0, NULL, NULL, '王五', '003', '浙江大学', '男', 20.00),
	('1536601469879455744', 'admin', '2022-06-14 14:48:33.753000', 0, NULL, NULL, '赵六', '004', '宁波大学', '保密', 21.00);

DROP TABLE IF EXISTS `a_teacher`;
CREATE TABLE IF NOT EXISTS `a_teacher` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci NOT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` int NOT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `age` decimal(19,2) DEFAULT NULL,
  `autograph` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `education` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `graduated` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  `wages` decimal(19,2) DEFAULT NULL,
  `resume` varchar(255) CHARACTER SET utf8 COLLATE utf8_croatian_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_croatian_ci;

DELETE FROM `a_teacher`;
INSERT INTO `a_teacher` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `age`, `autograph`, `education`, `graduated`, `name`, `remark`, `status`, `wages`, `resume`) VALUES
	('1536614275123122176', 'admin', '2022-06-14 15:39:26.000000', 0, 'admin', '2022-06-14 15:41:09.841000', 30.00, 'http://127.0.0.1:8080/zwz/file/view/1536614206311370752', '博士', '同济大学', '张三', '测试', '在职', 9960.00, 'http://127.0.0.1:8080/zwz/file/view/1536614698903015424'),
	('1536614275123122177', 'admin', '2022-06-14 15:39:26.000000', 0, 'admin', '2022-06-14 15:41:09.841000', 30.00, 'http://127.0.0.1:8080/zwz/file/view/1536614206311370752', '本科', '同济大学', '李四', '测试', '在职', 7778.00, 'http://127.0.0.1:8080/zwz/file/view/1536614698903015424'),
	('1536614275123122178', 'admin', '2022-06-14 15:39:26.000000', 0, 'admin', '2022-06-14 15:41:09.841000', 30.00, 'http://127.0.0.1:8080/zwz/file/view/1536614206311370752', '硕士', '同济大学', '王五', '测试', '在职', 8889.00, 'http://127.0.0.1:8080/zwz/file/view/1536614698903015424'),
	('1536614275123122179', 'admin', '2022-06-14 15:39:26.000000', 0, 'admin', '2022-06-14 15:41:09.841000', 30.00, 'http://127.0.0.1:8080/zwz/file/view/1536614206311370752', '硕士', '同济大学', '赵六', '测试', '在职', 4445.00, 'http://127.0.0.1:8080/zwz/file/view/1536614698903015424'),
	('1536614275123122180', 'admin', '2022-06-14 15:39:26.000000', 0, 'admin', '2022-06-14 15:41:09.841000', 30.00, 'http://127.0.0.1:8080/zwz/file/view/1536614206311370752', '专科', '同济大学', '郑七', '测试', '在职', 1118.00, 'http://127.0.0.1:8080/zwz/file/view/1536614698903015424'),
	('1536614275123122181', 'admin', '2022-06-14 15:39:26.000000', 0, 'admin', '2022-06-14 15:41:09.841000', 30.00, 'http://127.0.0.1:8080/zwz/file/view/1536614206311370752', '专科', '同济大学', '龚八', '测试', '在职', 3337.00, 'http://127.0.0.1:8080/zwz/file/view/1536614698903015424');

DROP TABLE IF EXISTS `a_user`;
CREATE TABLE IF NOT EXISTS `a_user` (
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
  `my_door` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `email` (`email`) USING BTREE,
  UNIQUE KEY `mobile` (`mobile`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DELETE FROM `a_user`;
INSERT INTO `a_user` (`id`, `create_by`, `create_time`, `update_by`, `update_time`, `address`, `avatar`, `description`, `email`, `mobile`, `nickname`, `password`, `sex`, `status`, `type`, `username`, `del_flag`, `department_id`, `street`, `pass_strength`, `department_title`, `birth`, `my_door`) VALUES
	(682265633886208, '', '2022-03-20 09:46:20', 'admin', '2022-09-12 13:15:47', '["330000","330600","330602"]', 'https://wx.qlogo.cn/mmhead/PiajxSqBRaEKjc639z2lRF4DqweNw2aDRLbAL7yLlSgrdZeKBCwLHDQ/0', '北大', '916077357@qq.com', '17857054388', '管理员', '$2a$10$PS04ecXfknNd3V8d.ymLTObQciapMU4xU8.GADBZZsuTZr7ymnagy', '男', 0, 1, 'admin', 0, 1464487379074158593, '东西街道2', '弱', '设计研发部', '2000-01-08 00:00:00.000000', 'hospitalSubjectZWZ666zwzDoctorZWZ666pushOrderZWZ666guaOrderZWZ666myOrderZWZ666hospitalNews'),
	(1464764315201572865, '', '2022-03-20 09:46:20', 'admin', '2022-05-21 09:11:48', '', 'https://wx.qlogo.cn/mmhead/PiajxSqBRaEKjc639z2lRF4DqweNw2aDRLbAL7yLlSgrdZeKBCwLHDQ/0', '', '17859654125@qq.com', '17859654125', '测试', '$2a$10$E59nactOiILAzQvfcs0JFOYuZp06d4bLhugEadyQuygpmiLc0W.ha', '', -1, 0, '17859654125', 0, 1464487332064399361, '', '', '行政综合部', NULL, NULL),
	(1464772465946398721, '', '2022-03-20 09:46:20', 'admin', '2022-05-21 09:53:33', '["110000","110100","110105"]', 'https://wx.qlogo.cn/mmhead/PiajxSqBRaEKjc639z2lRF4DqweNw2aDRLbAL7yLlSgrdZeKBCwLHDQ/0', '', '17859654121@qq.com', '17859654121', '测试2', '$2a$10$vJyLo1RhFORH/SAu3sc9aeb37I5JRy5UugaN7fIt/e2vvsz6JWJCm', '', 0, 0, '17859654121', 0, 1464487432169852929, '', '', '综合发展部', NULL, ''),
	(1527830053524738048, 'admin', '2022-05-21 09:54:05', NULL, NULL, '["110000","110100","110106"]', 'https://wx.qlogo.cn/mmhead/PiajxSqBRaEKjc639z2lRF4DqweNw2aDRLbAL7yLlSgrdZeKBCwLHDQ/0', NULL, 'user4@qq.com', '17896525487', 'user4', '$2a$10$oeP4aplYnswfQ44pK6lAO.Np9BuPYJGRwo17THO7CUNlIQoVGsPmy', '男', 0, 0, 'user4', 0, 1464487332064399361, NULL, '弱', '行政综合部', NULL, NULL);

DROP TABLE IF EXISTS `a_user_role`;
CREATE TABLE IF NOT EXISTS `a_user_role` (
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

DELETE FROM `a_user_role`;
INSERT INTO `a_user_role` (`id`, `create_by`, `create_time`, `del_flag`, `update_by`, `update_time`, `role_id`, `user_id`) VALUES
	(1464487931975700480, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 496138616573952, 682265633886208),
	(1486220872917323776, 'admin', '2022-03-20 09:46:20', 0, 'admin', '2022-03-20 09:46:20', 496138616573953, 1464764315201572865),
	(1526802492967489537, NULL, '2022-05-18 13:50:56', 0, NULL, NULL, 496138616573953, 1526802492443201536),
	(1526819095553642497, NULL, '2022-05-18 14:56:54', 0, NULL, NULL, 496138616573953, 1526819095159377920),
	(1527829917801254912, 'admin', '2022-05-21 09:53:33', 0, NULL, NULL, 496138616573953, 1464772465946398721),
	(1527830053889642496, 'admin', '2022-05-21 09:54:05', 0, NULL, NULL, 496138616573952, 1527830053524738048);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
