/*
 Navicat Premium Data Transfer

 Source Server         : localhost-windows
 Source Server Type    : MySQL
 Source Server Version : 50558
 Source Host           : localhost:3306
 Source Schema         : school_manager

 Target Server Type    : MySQL
 Target Server Version : 50558
 File Encoding         : 65001

 Date: 08/10/2020 00:56:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for login_menu
-- ----------------------------
DROP TABLE IF EXISTS `login_menu`;
CREATE TABLE `login_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单唯一标识',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名字',
  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标（icon）',
  `jump` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '跳转链接',
  `parent_id` bigint(20) NOT NULL COMMENT '父级菜单Id',
  `order_id` bigint(20) NOT NULL COMMENT '菜单的序号',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '删除标识',
  `last_modified_user_id` bigint(20) NOT NULL COMMENT '最后一个修改者的ID',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of login_menu
-- ----------------------------
INSERT INTO `login_menu` VALUES (1, 'main-page', '主页', 'layui-icon-home', '/', 0, 1, 0, 5, '2020-09-25 10:35:24', '2020-09-25 23:38:29');
INSERT INTO `login_menu` VALUES (2, 'controller', '控制台', NULL, '/', 1, 1, 0, 1, '2020-09-25 10:35:26', '2020-09-25 10:35:34');
INSERT INTO `login_menu` VALUES (3, 'security', '权限管理', 'layui-icon-auz', '/', 0, 1, 0, 5, '2020-09-25 10:35:27', '2020-09-26 01:01:27');
INSERT INTO `login_menu` VALUES (4, 'layui-icon-user', '网站用户', NULL, 'user/user/userlist', 14, 5, 0, 1, '2020-09-25 10:35:28', '2020-09-25 10:35:38');
INSERT INTO `login_menu` VALUES (5, 'administrators-rule', '角色管理', '', 'security/role/rolelist', 3, 4, 0, 5, '2020-09-25 10:35:29', '2020-09-25 18:27:03');
INSERT INTO `login_menu` VALUES (6, 'administrators-list', '后台管理员', NULL, 'user/administrators/list', 14, 99, 0, 1, '2020-09-25 10:35:28', '2020-09-25 16:01:13');
INSERT INTO `login_menu` VALUES (7, 'security-list', '后台接口管理', '', 'security/security/securitylist', 3, 1, 0, 5, '2020-09-25 10:35:30', '2020-09-25 21:35:35');
INSERT INTO `login_menu` VALUES (8, 'permission-list', '权限组-接口管理', '', 'security/permission/permissionlist', 3, 2, 0, 5, '2020-09-25 10:35:31', '2020-09-25 21:35:48');
INSERT INTO `login_menu` VALUES (11, 'menu-list', '菜单管理', '', 'security/menu/menulist', 3, 3, 0, 5, '2020-09-25 11:27:14', '2020-09-25 18:26:49');
INSERT INTO `login_menu` VALUES (12, 'setting', '设置', 'layui-icon-set', '1', 0, 100, 0, 5, '2020-09-25 16:20:27', '2020-09-26 16:34:09');
INSERT INTO `login_menu` VALUES (14, 'user', '用户相关', 'layui-icon-user', '/', 0, 5, 0, 5, '2020-09-25 16:21:35', '2020-09-26 23:10:05');
INSERT INTO `login_menu` VALUES (19, 'teacher-group', '教师管理', 'layui-icon-group', '/', 0, 3, 0, 5, '2020-09-26 23:08:39', '2020-09-26 23:08:39');
INSERT INTO `login_menu` VALUES (20, 'teacher-school', '学校资料设置', '', '/teacher/school/school', 19, 1, 0, 5, '2020-09-26 23:21:37', '2020-09-26 23:26:13');
INSERT INTO `login_menu` VALUES (21, 'teacher-grade', '年级管理', '', '/teacher/grade/gradelist', 19, 2, 0, 5, '2020-09-27 00:51:28', '2020-10-01 19:28:29');
INSERT INTO `login_menu` VALUES (22, 'teacher-classes', '班级管理', '', '/teacher/classes/classeslist', 19, 3, 0, 5, '2020-10-01 20:40:46', '2020-10-01 20:41:38');
INSERT INTO `login_menu` VALUES (23, 'teacher-course', '课程管理', '', '/teacher/course/courselist', 19, 4, 0, 5, '2020-10-06 10:07:56', '2020-10-06 10:07:56');
INSERT INTO `login_menu` VALUES (24, 'teacher-grade-course', '年级课程管理', '', '/teacher/gradecourse/gradecourselist', 19, 5, 0, 5, '2020-10-06 11:06:44', '2020-10-06 11:06:44');

-- ----------------------------
-- Table structure for login_permission
-- ----------------------------
DROP TABLE IF EXISTS `login_permission`;
CREATE TABLE `login_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '描述',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `login_permission_name_uindex`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10005 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限组表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of login_permission
-- ----------------------------
INSERT INTO `login_permission` VALUES (1, '权限组1', 0, 'sfasda', '2020-09-17 17:15:43', '2020-09-25 10:20:01');
INSERT INTO `login_permission` VALUES (2, '权限组2', 0, '权限组2', '2020-09-17 17:15:43', '2020-09-24 22:23:07');
INSERT INTO `login_permission` VALUES (3, '权限组3', 1, '权限组3', '2020-09-17 17:15:43', '2020-09-25 16:58:40');
INSERT INTO `login_permission` VALUES (4, '权限组4', 1, '权限组4-具体描述', '2020-09-17 17:17:08', '2020-09-25 17:10:10');
INSERT INTO `login_permission` VALUES (5, '权限组5', 0, '	 权限组5', '2020-09-17 17:17:08', '2020-09-25 20:46:29');
INSERT INTO `login_permission` VALUES (6, '权限组6', 0, 'sdafsdfasdfa', '2020-09-17 17:17:08', '2020-09-24 22:08:48');
INSERT INTO `login_permission` VALUES (7, '权限组7dsff', 0, 'sdfs', '2020-09-17 17:17:08', '2020-09-25 21:46:32');
INSERT INTO `login_permission` VALUES (8, '权限组8', 0, '权限组8', '2020-09-17 17:17:09', '2020-09-24 22:04:26');
INSERT INTO `login_permission` VALUES (9, '权限组9', 1, '权限组9', '2020-09-17 17:17:09', '2020-09-25 17:05:53');
INSERT INTO `login_permission` VALUES (10000, 'root', 0, 'root 权限组，这个是最高权限组', '2020-09-17 18:20:44', '2020-09-25 11:14:50');

-- ----------------------------
-- Table structure for login_permission_security
-- ----------------------------
DROP TABLE IF EXISTS `login_permission_security`;
CREATE TABLE `login_permission_security`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission_id` bigint(20) NOT NULL COMMENT '用户id',
  `security_id` bigint(20) NOT NULL COMMENT '角色id',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_permission_security`(`permission_id`, `security_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 79 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限-接口-关系表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of login_permission_security
-- ----------------------------
INSERT INTO `login_permission_security` VALUES (28, 8, 2, 0, '2020-09-24 22:04:26', '2020-09-24 22:04:26');
INSERT INTO `login_permission_security` VALUES (39, 6, 3, 0, '2020-09-24 22:08:48', '2020-09-24 22:08:48');
INSERT INTO `login_permission_security` VALUES (55, 2, 1, 0, '2020-09-24 22:23:07', '2020-09-24 22:23:07');
INSERT INTO `login_permission_security` VALUES (56, 1, 1, 0, '2020-09-25 10:20:01', '2020-09-25 10:20:01');
INSERT INTO `login_permission_security` VALUES (59, 10000, 1, 0, '2020-09-25 11:14:50', '2020-09-25 11:14:50');
INSERT INTO `login_permission_security` VALUES (60, 10000, 2, 0, '2020-09-25 11:14:50', '2020-09-25 11:14:50');
INSERT INTO `login_permission_security` VALUES (61, 10000, 3, 0, '2020-09-25 11:14:50', '2020-09-25 11:14:50');
INSERT INTO `login_permission_security` VALUES (64, 10001, 1, 0, '2020-09-25 17:17:22', '2020-09-25 17:17:22');
INSERT INTO `login_permission_security` VALUES (65, 10001, 3, 0, '2020-09-25 17:17:22', '2020-09-25 17:17:22');
INSERT INTO `login_permission_security` VALUES (66, 10001, 10001, 0, '2020-09-25 17:17:22', '2020-09-25 17:17:22');
INSERT INTO `login_permission_security` VALUES (67, 10001, 10003, 0, '2020-09-25 17:17:22', '2020-09-25 17:17:22');
INSERT INTO `login_permission_security` VALUES (68, 10001, 10005, 0, '2020-09-25 17:17:22', '2020-09-25 17:17:22');
INSERT INTO `login_permission_security` VALUES (69, 5, 1, 0, '2020-09-25 20:46:30', '2020-09-25 20:46:30');
INSERT INTO `login_permission_security` VALUES (70, 5, 2, 0, '2020-09-25 20:46:30', '2020-09-25 20:46:30');
INSERT INTO `login_permission_security` VALUES (71, 5, 10001, 0, '2020-09-25 20:46:30', '2020-09-25 20:46:30');
INSERT INTO `login_permission_security` VALUES (72, 5, 10003, 0, '2020-09-25 20:46:30', '2020-09-25 20:46:30');
INSERT INTO `login_permission_security` VALUES (73, 5, 10005, 0, '2020-09-25 20:46:30', '2020-09-25 20:46:30');
INSERT INTO `login_permission_security` VALUES (74, 7, 1, 0, '2020-09-25 21:46:32', '2020-09-25 21:46:32');
INSERT INTO `login_permission_security` VALUES (75, 7, 2, 0, '2020-09-25 21:46:32', '2020-09-25 21:46:32');
INSERT INTO `login_permission_security` VALUES (76, 7, 10001, 0, '2020-09-25 21:46:32', '2020-09-25 21:46:32');
INSERT INTO `login_permission_security` VALUES (77, 7, 10005, 0, '2020-09-25 21:46:32', '2020-09-25 21:46:32');
INSERT INTO `login_permission_security` VALUES (78, 7, 10037, 0, '2020-09-25 21:46:32', '2020-09-25 21:46:32');

-- ----------------------------
-- Table structure for login_role
-- ----------------------------
DROP TABLE IF EXISTS `login_role`;
CREATE TABLE `login_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名字',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '描述',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `login_role_name_uindex`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of login_role
-- ----------------------------
INSERT INTO `login_role` VALUES (1, 'root角色', 0, 'root角色具有所有权限，所有菜单', '2020-09-17 16:47:55', '2020-10-06 10:08:13');
INSERT INTO `login_role` VALUES (2, '角色2', 0, '角色2', '2020-09-17 16:48:35', '2020-09-24 20:52:02');
INSERT INTO `login_role` VALUES (3, '角色3', 0, '角色3', '1970-01-01 00:00:00', '2020-09-25 20:49:43');
INSERT INTO `login_role` VALUES (8, '测试角色', 1, '测试角色用来测试', '2020-09-23 01:57:07', '2020-09-23 20:37:04');
INSERT INTO `login_role` VALUES (9, 'sdf', 1, 'dafsdfs', '2020-09-23 02:14:46', '2020-09-24 10:36:08');
INSERT INTO `login_role` VALUES (10, 'asdfasdfa', 1, 'asdfasdfasdf', '2020-09-23 02:14:55', '2020-09-25 21:19:29');
INSERT INTO `login_role` VALUES (11, 'dsaf', 1, 'asdfasdf', '2020-09-23 02:22:58', '2020-09-25 21:49:22');
INSERT INTO `login_role` VALUES (12, 'sdaf', 0, 'asdfasdfa', '2020-09-23 02:23:09', '2020-09-23 20:35:46');
INSERT INTO `login_role` VALUES (13, 'asdfsdfasd', 1, 'asdfasdgadsf', '2020-09-23 02:23:34', '2020-09-25 21:49:29');
INSERT INTO `login_role` VALUES (14, 'dfgsgdf', 0, 'asdfasdf', '2020-09-23 02:25:43', '2020-09-23 19:04:27');
INSERT INTO `login_role` VALUES (15, '测试角色1-权限组1', 1, '测试角色1-权限组1', '2020-09-23 02:26:59', '2020-09-25 21:29:46');
INSERT INTO `login_role` VALUES (16, 'dfaddfgd', 1, 'dafd', '2020-09-23 14:16:27', '2020-09-25 22:05:47');

-- ----------------------------
-- Table structure for login_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `login_role_menu`;
CREATE TABLE `login_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单id',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_role_menu`(`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 112 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of login_role_menu
-- ----------------------------
INSERT INTO `login_role_menu` VALUES (14, 2, 1, 0, '2020-09-17 16:52:29', '2020-09-17 16:52:29');
INSERT INTO `login_role_menu` VALUES (15, 3, 1, 0, '2020-09-17 16:52:29', '2020-09-17 16:52:29');
INSERT INTO `login_role_menu` VALUES (17, 2, 2, 0, '2020-09-17 16:52:30', '2020-09-17 16:52:30');
INSERT INTO `login_role_menu` VALUES (18, 3, 2, 0, '2020-09-17 16:52:30', '2020-09-17 16:52:30');
INSERT INTO `login_role_menu` VALUES (20, 2, 3, 0, '2020-09-17 16:52:30', '2020-09-17 16:52:30');
INSERT INTO `login_role_menu` VALUES (21, 3, 3, 0, '2020-09-17 16:52:30', '2020-09-17 16:52:30');
INSERT INTO `login_role_menu` VALUES (28, 15, 1, 0, '2020-09-25 21:29:47', '2020-09-25 21:29:47');
INSERT INTO `login_role_menu` VALUES (29, 15, 5, 0, '2020-09-25 21:29:47', '2020-09-25 21:29:47');
INSERT INTO `login_role_menu` VALUES (30, 15, 6, 0, '2020-09-25 21:29:47', '2020-09-25 21:29:47');
INSERT INTO `login_role_menu` VALUES (31, 15, 7, 0, '2020-09-25 21:29:47', '2020-09-25 21:29:47');
INSERT INTO `login_role_menu` VALUES (47, 16, 1, 0, '2020-09-25 22:05:48', '2020-09-25 22:05:48');
INSERT INTO `login_role_menu` VALUES (96, 1, 1, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');
INSERT INTO `login_role_menu` VALUES (97, 1, 2, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');
INSERT INTO `login_role_menu` VALUES (98, 1, 3, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');
INSERT INTO `login_role_menu` VALUES (99, 1, 4, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');
INSERT INTO `login_role_menu` VALUES (100, 1, 5, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');
INSERT INTO `login_role_menu` VALUES (101, 1, 6, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');
INSERT INTO `login_role_menu` VALUES (102, 1, 7, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');
INSERT INTO `login_role_menu` VALUES (103, 1, 8, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');
INSERT INTO `login_role_menu` VALUES (104, 1, 11, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');
INSERT INTO `login_role_menu` VALUES (105, 1, 12, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');
INSERT INTO `login_role_menu` VALUES (106, 1, 14, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');
INSERT INTO `login_role_menu` VALUES (107, 1, 19, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');
INSERT INTO `login_role_menu` VALUES (108, 1, 20, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');
INSERT INTO `login_role_menu` VALUES (109, 1, 21, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');
INSERT INTO `login_role_menu` VALUES (110, 1, 22, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');
INSERT INTO `login_role_menu` VALUES (111, 1, 23, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');

-- ----------------------------
-- Table structure for login_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `login_role_permission`;
CREATE TABLE `login_role_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `permission_id` bigint(20) NOT NULL COMMENT '权限id',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_role_id_permission_id`(`role_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 358 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of login_role_permission
-- ----------------------------
INSERT INTO `login_role_permission` VALUES (16, 4, 4, 0, '2020-09-17 17:11:42', '2020-09-17 17:11:42');
INSERT INTO `login_role_permission` VALUES (63, 9, 2, 0, '2020-09-23 02:15:59', '2020-09-23 02:15:59');
INSERT INTO `login_role_permission` VALUES (64, 9, 3, 0, '2020-09-23 02:15:59', '2020-09-23 02:15:59');
INSERT INTO `login_role_permission` VALUES (65, 8, 1, 0, '2020-09-23 02:16:09', '2020-09-23 02:16:09');
INSERT INTO `login_role_permission` VALUES (66, 8, 4, 0, '2020-09-23 02:16:09', '2020-09-23 02:16:09');
INSERT INTO `login_role_permission` VALUES (67, 8, 7, 0, '2020-09-23 02:16:09', '2020-09-23 02:16:09');
INSERT INTO `login_role_permission` VALUES (68, 8, 8, 0, '2020-09-23 02:16:09', '2020-09-23 02:16:09');
INSERT INTO `login_role_permission` VALUES (69, 8, 9, 0, '2020-09-23 02:16:09', '2020-09-23 02:16:09');
INSERT INTO `login_role_permission` VALUES (70, 8, 10000, 0, '2020-09-23 02:16:09', '2020-09-23 02:16:09');
INSERT INTO `login_role_permission` VALUES (86, 14, 4, 0, '2020-09-23 02:51:50', '2020-09-23 02:51:50');
INSERT INTO `login_role_permission` VALUES (87, 14, 7, 0, '2020-09-23 02:51:50', '2020-09-23 02:51:50');
INSERT INTO `login_role_permission` VALUES (152, 12, 5, 0, '2020-09-23 20:35:46', '2020-09-23 20:35:46');
INSERT INTO `login_role_permission` VALUES (153, 12, 6, 0, '2020-09-23 20:35:46', '2020-09-23 20:35:46');
INSERT INTO `login_role_permission` VALUES (155, 18, 2, 0, '2020-09-23 20:38:05', '2020-09-23 20:38:05');
INSERT INTO `login_role_permission` VALUES (156, 18, 4, 0, '2020-09-23 20:38:05', '2020-09-23 20:38:05');
INSERT INTO `login_role_permission` VALUES (179, 20, 1, 0, '2020-09-24 18:02:41', '2020-09-24 18:02:41');
INSERT INTO `login_role_permission` VALUES (180, 20, 4, 0, '2020-09-24 18:02:41', '2020-09-24 18:02:41');
INSERT INTO `login_role_permission` VALUES (181, 20, 8, 0, '2020-09-24 18:02:41', '2020-09-24 18:02:41');
INSERT INTO `login_role_permission` VALUES (182, 24, 2, 0, '2020-09-24 18:02:46', '2020-09-24 18:02:46');
INSERT INTO `login_role_permission` VALUES (183, 24, 5, 0, '2020-09-24 18:02:46', '2020-09-24 18:02:46');
INSERT INTO `login_role_permission` VALUES (184, 21, 4, 0, '2020-09-24 18:02:51', '2020-09-24 18:02:51');
INSERT INTO `login_role_permission` VALUES (185, 21, 5, 0, '2020-09-24 18:02:51', '2020-09-24 18:02:51');
INSERT INTO `login_role_permission` VALUES (186, 21, 8, 0, '2020-09-24 18:02:51', '2020-09-24 18:02:51');
INSERT INTO `login_role_permission` VALUES (187, 23, 1, 0, '2020-09-24 18:02:56', '2020-09-24 18:02:56');
INSERT INTO `login_role_permission` VALUES (188, 23, 2, 0, '2020-09-24 18:02:56', '2020-09-24 18:02:56');
INSERT INTO `login_role_permission` VALUES (189, 2, 1, 0, '2020-09-24 20:52:02', '2020-09-24 20:52:02');
INSERT INTO `login_role_permission` VALUES (190, 2, 2, 0, '2020-09-24 20:52:02', '2020-09-24 20:52:02');
INSERT INTO `login_role_permission` VALUES (191, 2, 7, 0, '2020-09-24 20:52:02', '2020-09-24 20:52:02');
INSERT INTO `login_role_permission` VALUES (192, 19, 1, 0, '2020-09-24 20:52:08', '2020-09-24 20:52:08');
INSERT INTO `login_role_permission` VALUES (193, 19, 2, 0, '2020-09-24 20:52:08', '2020-09-24 20:52:08');
INSERT INTO `login_role_permission` VALUES (194, 19, 5, 0, '2020-09-24 20:52:08', '2020-09-24 20:52:08');
INSERT INTO `login_role_permission` VALUES (195, 19, 7, 0, '2020-09-24 20:52:08', '2020-09-24 20:52:08');
INSERT INTO `login_role_permission` VALUES (196, 19, 8, 0, '2020-09-24 20:52:08', '2020-09-24 20:52:08');
INSERT INTO `login_role_permission` VALUES (197, 17, 1, 0, '2020-09-24 20:52:16', '2020-09-24 20:52:16');
INSERT INTO `login_role_permission` VALUES (198, 17, 2, 0, '2020-09-24 20:52:16', '2020-09-24 20:52:16');
INSERT INTO `login_role_permission` VALUES (199, 17, 7, 0, '2020-09-24 20:52:16', '2020-09-24 20:52:16');
INSERT INTO `login_role_permission` VALUES (200, 17, 10000, 0, '2020-09-24 20:52:16', '2020-09-24 20:52:16');
INSERT INTO `login_role_permission` VALUES (233, 3, 1, 0, '2020-09-25 20:49:44', '2020-09-25 20:49:44');
INSERT INTO `login_role_permission` VALUES (234, 3, 2, 0, '2020-09-25 20:49:44', '2020-09-25 20:49:44');
INSERT INTO `login_role_permission` VALUES (277, 10, 1, 0, '2020-09-25 21:19:29', '2020-09-25 21:19:29');
INSERT INTO `login_role_permission` VALUES (278, 10, 2, 0, '2020-09-25 21:19:29', '2020-09-25 21:19:29');
INSERT INTO `login_role_permission` VALUES (279, 10, 5, 0, '2020-09-25 21:19:29', '2020-09-25 21:19:29');
INSERT INTO `login_role_permission` VALUES (280, 10, 6, 0, '2020-09-25 21:19:29', '2020-09-25 21:19:29');
INSERT INTO `login_role_permission` VALUES (281, 10, 7, 0, '2020-09-25 21:19:29', '2020-09-25 21:19:29');
INSERT INTO `login_role_permission` VALUES (300, 15, 6, 0, '2020-09-25 21:29:47', '2020-09-25 21:29:47');
INSERT INTO `login_role_permission` VALUES (301, 15, 8, 0, '2020-09-25 21:29:47', '2020-09-25 21:29:47');
INSERT INTO `login_role_permission` VALUES (302, 15, 10000, 0, '2020-09-25 21:29:47', '2020-09-25 21:29:47');
INSERT INTO `login_role_permission` VALUES (313, 16, 1, 0, '2020-09-25 22:05:48', '2020-09-25 22:05:48');
INSERT INTO `login_role_permission` VALUES (314, 16, 6, 0, '2020-09-25 22:05:48', '2020-09-25 22:05:48');
INSERT INTO `login_role_permission` VALUES (315, 16, 7, 0, '2020-09-25 22:05:48', '2020-09-25 22:05:48');
INSERT INTO `login_role_permission` VALUES (316, 16, 8, 0, '2020-09-25 22:05:48', '2020-09-25 22:05:48');
INSERT INTO `login_role_permission` VALUES (317, 16, 10000, 0, '2020-09-25 22:05:48', '2020-09-25 22:05:48');
INSERT INTO `login_role_permission` VALUES (318, 16, 10002, 0, '2020-09-25 22:05:48', '2020-09-25 22:05:48');
INSERT INTO `login_role_permission` VALUES (319, 16, 10003, 0, '2020-09-25 22:05:48', '2020-09-25 22:05:48');
INSERT INTO `login_role_permission` VALUES (320, 16, 10004, 0, '2020-09-25 22:05:48', '2020-09-25 22:05:48');
INSERT INTO `login_role_permission` VALUES (351, 1, 1, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');
INSERT INTO `login_role_permission` VALUES (352, 1, 2, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');
INSERT INTO `login_role_permission` VALUES (353, 1, 5, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');
INSERT INTO `login_role_permission` VALUES (354, 1, 6, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');
INSERT INTO `login_role_permission` VALUES (355, 1, 7, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');
INSERT INTO `login_role_permission` VALUES (356, 1, 8, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');
INSERT INTO `login_role_permission` VALUES (357, 1, 10000, 0, '2020-10-06 10:08:13', '2020-10-06 10:08:13');

-- ----------------------------
-- Table structure for login_role_user
-- ----------------------------
DROP TABLE IF EXISTS `login_role_user`;
CREATE TABLE `login_role_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `login_role_user_pk`(`role_id`, `user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of login_role_user
-- ----------------------------
INSERT INTO `login_role_user` VALUES (10, 5, 1, '2020-09-17 16:43:03', '2020-09-17 16:43:03', 0);
INSERT INTO `login_role_user` VALUES (11, 5, 2, '2020-09-17 16:43:14', '2020-09-17 16:43:14', 0);
INSERT INTO `login_role_user` VALUES (12, 5, 3, '2020-09-17 16:43:29', '2020-09-17 16:43:29', 0);

-- ----------------------------
-- Table structure for login_security
-- ----------------------------
DROP TABLE IF EXISTS `login_security`;
CREATE TABLE `login_security`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url_pattern` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'url',
  `description` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '描述',
  `order_id` bigint(20) NOT NULL COMMENT '排序',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_url_pattern`(`url_pattern`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10038 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of login_security
-- ----------------------------
INSERT INTO `login_security` VALUES (1, '/**', '这个是超级接口，不能变更', 10, 0, '2020-09-17 13:18:26', '2020-09-24 15:30:23');
INSERT INTO `login_security` VALUES (2, '/security/enable', '接口-启用', 9, 0, '2020-09-17 13:19:18', '2020-09-24 16:51:01');
INSERT INTO `login_security` VALUES (3, '/security/getAllSecurity', '接口-获取所有接口', 9, 0, '2020-09-17 13:20:11', '2020-09-25 22:56:00');
INSERT INTO `login_security` VALUES (10001, '/security/disable', '接口-禁用', 9, 0, '2020-09-24 12:34:30', '2020-09-24 18:06:51');
INSERT INTO `login_security` VALUES (10002, '用来测试接口', '用来测试接口', 1, 1, '2020-09-24 12:37:23', '2020-09-24 19:08:09');
INSERT INTO `login_security` VALUES (10003, '/security/listDisable', '接口-批量禁用', 9, 0, '2020-09-24 12:52:43', '2020-09-24 18:04:14');
INSERT INTO `login_security` VALUES (10005, '/security/securityAddOrUpdate', '接口-新增或者更新', 9, 0, '2020-09-24 16:20:47', '2020-09-24 19:16:29');

-- ----------------------------
-- Table structure for login_user
-- ----------------------------
DROP TABLE IF EXISTS `login_user`;
CREATE TABLE `login_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '删除标志',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of login_user
-- ----------------------------
INSERT INTO `login_user` VALUES (5, 'root', '$2a$10$ZicPyQOOjMOX2HLOSY5wFOWqUVtlPqMD7ccUjPeKLZ3b2hTdbXMxK', NULL, NULL, '2020-09-16 01:56:40', '2020-09-16 01:56:40', 0);
INSERT INTO `login_user` VALUES (6, 'admin', '$2a$10$D06nsexV30Ya0dT9Fii3j.zuu7HEDjebwmQO4HqOccIcf2azCCwhO', NULL, NULL, '2020-09-16 20:15:42', '2020-09-16 20:15:42', 0);

-- ----------------------------
-- Table structure for t_classes
-- ----------------------------
DROP TABLE IF EXISTS `t_classes`;
CREATE TABLE `t_classes`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `grade_id` bigint(20) NOT NULL COMMENT '年级Id',
  `classes_number` int(11) NOT NULL COMMENT '班级序号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '班级名字',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '删除标志',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '班级表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_classes
-- ----------------------------
INSERT INTO `t_classes` VALUES (1, 1, 1, '一班', '2020-10-02 21:19:11', '2020-10-02 21:21:41', 0);
INSERT INTO `t_classes` VALUES (2, 2, 2, '二班', '2020-10-06 09:24:22', '2020-10-06 14:11:30', 0);

-- ----------------------------
-- Table structure for t_course
-- ----------------------------
DROP TABLE IF EXISTS `t_course`;
CREATE TABLE `t_course`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '删除标志',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程名字',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_course
-- ----------------------------
INSERT INTO `t_course` VALUES (1, '2020-10-06 10:31:46', '2020-10-06 10:33:25', 0, '语文');
INSERT INTO `t_course` VALUES (2, '2020-10-06 10:32:27', '2020-10-06 10:32:27', 0, '数学');

-- ----------------------------
-- Table structure for t_grade
-- ----------------------------
DROP TABLE IF EXISTS `t_grade`;
CREATE TABLE `t_grade`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `grade_number` int(11) NOT NULL COMMENT '年级序号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '年级描述',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '删除标志',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_number`(`grade_number`) USING BTREE COMMENT '年级序号唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '年级表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_grade
-- ----------------------------
INSERT INTO `t_grade` VALUES (1, 7, '七年级', '2020-10-01 19:20:51', '2020-10-01 19:21:45', 0);
INSERT INTO `t_grade` VALUES (2, 8, '八年级', '2020-10-01 19:21:02', '2020-10-01 19:21:43', 0);
INSERT INTO `t_grade` VALUES (3, 9, '九年级', '2020-10-01 19:26:51', '2020-10-01 19:27:12', 0);

-- ----------------------------
-- Table structure for t_grade_course
-- ----------------------------
DROP TABLE IF EXISTS `t_grade_course`;
CREATE TABLE `t_grade_course`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '删除标志',
  `grade_id` bigint(20) NOT NULL COMMENT '年级Id',
  `course_id` bigint(20) NOT NULL COMMENT '课程Id',
  `course_count` int(11) NOT NULL COMMENT '课程的数目',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `grade_course`(`grade_id`, `course_id`) USING BTREE COMMENT '年级-课程'
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_grade_course
-- ----------------------------
INSERT INTO `t_grade_course` VALUES (1, '2020-10-06 11:37:29', '2020-10-06 11:52:03', 0, 1, 2, 6);
INSERT INTO `t_grade_course` VALUES (2, '2020-10-06 11:41:10', '2020-10-06 11:52:15', 0, 1, 1, 2);
INSERT INTO `t_grade_course` VALUES (3, '2020-10-06 11:42:21', '2020-10-06 11:52:10', 0, 2, 1, 3);
INSERT INTO `t_grade_course` VALUES (5, '2020-10-06 11:42:34', '2020-10-06 11:51:48', 0, 2, 2, 6);
INSERT INTO `t_grade_course` VALUES (6, '2020-10-06 11:42:39', '2020-10-06 11:51:42', 0, 3, 1, 6);
INSERT INTO `t_grade_course` VALUES (7, '2020-10-06 11:42:44', '2020-10-06 11:51:03', 0, 3, 2, 4);

-- ----------------------------
-- Table structure for t_school
-- ----------------------------
DROP TABLE IF EXISTS `t_school`;
CREATE TABLE `t_school`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(65) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学校名字',
  `description` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学校描述',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '学校表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_school
-- ----------------------------
INSERT INTO `t_school` VALUES (1, '开封市14中学', '544156156', '2020-09-30 15:07:02', '2020-10-01 01:34:39', 0);

-- ----------------------------
-- Table structure for t_weekly_course
-- ----------------------------
DROP TABLE IF EXISTS `t_weekly_course`;
CREATE TABLE `t_weekly_course`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `biz_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务类型',
  `biz_id` bigint(20) NULL DEFAULT NULL COMMENT '业务Id',
  `order_id` int(11) NOT NULL COMMENT '序号',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '说明',
  `one` tinyint(4) NULL DEFAULT NULL COMMENT '星期一',
  `two` tinyint(4) NULL DEFAULT NULL COMMENT '星期二',
  `three` tinyint(4) NULL DEFAULT NULL COMMENT '星期三',
  `four` tinyint(4) NULL DEFAULT NULL COMMENT '星期四',
  `five` tinyint(4) NULL DEFAULT NULL COMMENT '星期五',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_type_biz_id_order_id`(`biz_type`, `biz_id`, `order_id`) USING BTREE COMMENT '序号唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '一周的课程表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_weekly_course
-- ----------------------------
INSERT INTO `t_weekly_course` VALUES (1, 'SCHOOL', 1, 1, '上午第一节课', 1, 1, 1, 1, 1, '2020-09-30 14:48:04', '2020-10-01 01:32:06', 0);
INSERT INTO `t_weekly_course` VALUES (2, 'SCHOOL', 1, 2, '上午第二节课', 1, 1, 1, 1, 1, '2020-09-30 14:48:04', '2020-10-01 01:32:06', 0);
INSERT INTO `t_weekly_course` VALUES (3, 'SCHOOL', 1, 3, '大课间', 0, 0, 0, 0, 0, '2020-09-30 14:48:04', '2020-10-01 01:32:06', 0);
INSERT INTO `t_weekly_course` VALUES (4, 'SCHOOL', 1, 4, '上午第三节课', 1, 1, 1, 1, 1, '2020-09-30 14:48:04', '2020-10-01 01:32:06', 0);
INSERT INTO `t_weekly_course` VALUES (5, 'SCHOOL', 1, 5, '上午第四节课', 1, 1, 1, 1, 1, '2020-09-30 14:48:04', '2020-10-01 01:32:06', 0);
INSERT INTO `t_weekly_course` VALUES (6, 'SCHOOL', 1, 6, '午休', 0, 0, 0, 0, 0, '2020-09-30 14:48:04', '2020-10-01 01:32:06', 0);
INSERT INTO `t_weekly_course` VALUES (7, 'SCHOOL', 1, 7, '下午第一节课', 1, 1, 1, 1, 1, '2020-09-30 14:48:04', '2020-10-01 01:32:06', 0);
INSERT INTO `t_weekly_course` VALUES (8, 'SCHOOL', 1, 8, '下午第二节课', 1, 1, 1, 1, 1, '2020-09-30 14:48:04', '2020-10-01 01:32:06', 0);
INSERT INTO `t_weekly_course` VALUES (9, 'SCHOOL', 1, 9, '下午第三节课', 1, 1, 1, 1, 1, '2020-09-30 14:48:04', '2020-10-01 01:32:06', 0);
INSERT INTO `t_weekly_course` VALUES (10, 'SCHOOL', 1, 10, '下午第四节课', 0, 0, 0, 0, 0, '2020-09-30 14:48:04', '2020-10-01 01:32:06', 0);

SET FOREIGN_KEY_CHECKS = 1;
