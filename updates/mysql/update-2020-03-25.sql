--新增表
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for union_data_group
-- ----------------------------
DROP TABLE IF EXISTS `union_data_group`;
CREATE TABLE `union_data_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(50) NOT NULL COMMENT '数据项分组',
  `text` varchar(50) NOT NULL COMMENT '数据分组名称',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `sort_id` int(11) DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_key_data_name` (`key`),
  KEY `i_rf_app_id` (`app_id`),
  CONSTRAINT `i_rf_app_id` FOREIGN KEY (`app_id`) REFERENCES `union_applications` (`app_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
INSERT INTO `union_role`.`union_data_group`(`id`, `key`, `text`, `create_time`, `modify_time`, `sort_id`, `app_id`) VALUES (1, 'business_type', '业务线', '2020-02-17 21:44:09', '2020-02-17 21:44:14', 1, 1);
SET FOREIGN_KEY_CHECKS = 1;


SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for union_data_items
-- ----------------------------
DROP TABLE IF EXISTS `union_data_items`;
CREATE TABLE `union_data_items` (
  `item_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `value` varchar(50) NOT NULL COMMENT '权益项值',
  `text` varchar(50) NOT NULL COMMENT '说明',
  `sort_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`item_id`) USING BTREE,
  KEY `rf_group_id` (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

INSERT INTO `union_role`.`union_data_items`(`item_id`, `group_id`, `value`, `text`, `sort_id`, `create_time`, `modify_time`) VALUES (1, 1, '0', '影视', 0, '2020-02-17 21:45:13', '2020-02-17 21:45:17');
INSERT INTO `union_role`.`union_data_items`(`item_id`, `group_id`, `value`, `text`, `sort_id`, `create_time`, `modify_time`) VALUES (2, 1, '1', '教育', 1, '2020-02-17 21:45:45', '2020-02-17 21:45:50');
INSERT INTO `union_role`.`union_data_items`(`item_id`, `group_id`, `value`, `text`, `sort_id`, `create_time`, `modify_time`) VALUES (3, 1, '2', 'IPTV业务线', 2, '2020-02-17 21:45:45', '2020-02-17 21:45:50');
INSERT INTO `union_role`.`union_data_items`(`item_id`, `group_id`, `value`, `text`, `sort_id`, `create_time`, `modify_time`) VALUES (4, 1, '3', '体育业务线', 3, '2020-02-17 21:45:45', '2020-02-17 21:45:50');
INSERT INTO `union_role`.`union_data_items`(`item_id`, `group_id`, `value`, `text`, `sort_id`, `create_time`, `modify_time`) VALUES (5, 1, '4', '宽带提速', 4, '2020-02-17 21:45:45', '2020-02-17 21:45:50');
INSERT INTO `union_role`.`union_data_items`(`item_id`, `group_id`, `value`, `text`, `sort_id`, `create_time`, `modify_time`) VALUES (6, 1, '5', '江苏时移回看', 5, '2020-02-17 21:45:45', '2020-02-17 21:45:50');
INSERT INTO `union_role`.`union_data_items`(`item_id`, `group_id`, `value`, `text`, `sort_id`, `create_time`, `modify_time`) VALUES (7, 1, '6', '游戏视频', 6, '2020-02-17 21:45:45', '2020-02-17 21:45:50');
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `union_account_data_item` (
  `account_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  PRIMARY KEY (`account_id`,`item_id`),
  KEY `rf_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 新增账号类型，记录从域账号过来的记录。
ALTER TABLE `union_role`.`union_accounts`
ADD COLUMN `type` int(11) not null default 1 AFTER `user_name`;
ALTER TABLE `union_role`.`union_accounts`
ADD COLUMN `apply_new_role` int(11) NOT NULL DEFAULT 0 AFTER `type`

update `union_role`.`union_accounts` set type = 1;
update `union_role`.`union_accounts` set apply_new_role = 0;

-- 权限申请表
CREATE TABLE `union_account_data_item_apply` (
  `account_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  PRIMARY KEY (`account_id`,`item_id`),
  KEY `rf_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `union_account_role_apply` (
  `account_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`account_id`,`role_id`) USING BTREE,
  KEY `FKkibnipnecv4gvc24o08enhvby` (`role_id`) USING BTREE,
  KEY `account_id` (`account_id`) USING BTREE,
  CONSTRAINT `union_account_role_apply_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `union_accounts` (`account_id`),
  CONSTRAINT `union_account_role_apply_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `union_roles` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
