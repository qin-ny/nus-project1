SET character_set_client='utf8';
SET character_set_connection='utf8';
SET character_set_database='utf8';
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `User` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `NRIC_FIN` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `phone_number` int(20) DEFAULT NULL,
  `gender` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


CREATE TABLE IF NOT EXISTS `Customer`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `reward_points` int(10) NOT NULL DEFAULT 0,
  `is_member` tinyint(1) NOT NULL DEFAULT 0,
  `user_id` int(10) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `customer_user_id_1eaea1b2_fk_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `customer_user_id_1eaea1b2_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

CREATE TABLE `CanteenType` (
   `id` int(10) NOT NULL AUTO_INCREMENT,
   `type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
   PRIMARY KEY (`id`) USING BTREE,
   UNIQUE KEY `type` (`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `Canteen` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `star` decimal(2,1) DEFAULT NULL,
  `description` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_id` int(10) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE,
  KEY `canteen_user_id_1edawgewe_fk_user_id` (`user_id`) USING BTREE,
  CONSTRAINT `canteen_user_id_1edawgewe_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `Canteen_CanteenType` (
   `canteen_id` int(10) NOT NULL,
   `canteen_type_id` int(10) NOT NULL,
   PRIMARY KEY (canteen_id, canteen_type_id) USING BTREE,
   KEY `Canteen_CanteenType_askjh298_fk_canteen_id` (`canteen_id`) USING BTREE,
   CONSTRAINT `Canteen_CanteenType_askjh298_fk_canteen_id` FOREIGN KEY (`canteen_id`) REFERENCES `Canteen` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
   KEY `Canteen_CanteenType_kjas2asd_fk_canteen_type_id` (`canteen_type_id`) USING BTREE,
   CONSTRAINT `Canteen_CanteenType_kjas2asd_fk_canteen_type_id` FOREIGN KEY (`canteen_type_id`) REFERENCES `CanteenType` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS 'Comment' (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `comment` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `star` float(2) NOT NULL,
  `user_id` int(10) NOT NULL,
  `canteen_id` int(10) NOT NULL,
  `order_id` int(10) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `comment_user_id_1sgwerw_fk_user_id`(`user_id` ASC) USING BTREE,
  INDEX `comment_canteen_id_1ehsf3dfg_fk_canteen_id`(`canteen_id` ASC) USING BTREE,
  INDEX `comment_order_id_1ashdjknxs_fk_order_id`(`order_id` ASC) USING BTREE,
  CONSTRAINT `comment_canteen_id_1ehsf3dfg_fk_canteen_id` FOREIGN KEY (`canteen_id`) REFERENCES `canteen` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `comment_user_id_1sgwerw_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `comment_order_id_1ashdjknxs_fk_order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS `Order`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `order_time` datetime NOT NULL,
  `total_fee` double(20, 3) NOT NULL,
  `status` int(1) NOT NULL DEFAULT 0,
  `user_id` int(10) NOT NULL,
  `canteen_id` int(10) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_user_id_13kasdiu_fk_user_id`(`user_id`) USING BTREE,
  INDEX `order_canteen_id_13kasdiu_fk_canteen_id`(`canteen_id`) USING BTREE,
  CONSTRAINT `order_user_id_13kasdiu_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `order_canteen_id_13kasdiu_fk_canteen_id` FOREIGN KEY (`canteen_id`) REFERENCES `Canteen` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

CREATE TABLE `DishType` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `canteen_id` int(10) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `DishType_canteen_id_asdasjh123kas_fk_canteen_id` (`canteen_id`) USING BTREE,
  CONSTRAINT `DishType_canteen_id_asdasjh123kas_fk_canteen_id` FOREIGN KEY (`canteen_id`) REFERENCES `canteen` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `Dish` (
    `id` int(10) NOT NULL AUTO_INCREMENT,
    `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `price` double(20,3) NOT NULL,
    `stock` int(10) NOT NULL DEFAULT '0',
    `description` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `availability` tinyint(1) NOT NULL DEFAULT '1',
    `sales_num_thirty` int(10) NOT NULL DEFAULT '0',
    `canteen_id` int(10) NOT NULL,
    `dish_type_id` int(10) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    KEY `Dish_canteen_id_asdl232kasd_fk_canteen_id` (`canteen_id`) USING BTREE,
    KEY `Dish_dish_type_id_alsk213lhsd_fk_dish_type_id` (`dish_type_id`) USING BTREE,
    CONSTRAINT `Dish_canteen_id_asdl232kasd_fk_canteen_id` FOREIGN KEY (`canteen_id`) REFERENCES `canteen` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `Dish_dish_type_id_alsk213lhsd_fk_dish_type_id` FOREIGN KEY (`dish_type_id`) REFERENCES `DishType` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `OrderItem`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `number` int(10) NOT NULL,
  `fee` double(20, 3) NOT NULL,
  `order_id` int(10) NOT NULL,
  `dish_id` int(10),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `OrderItem_order_id_as2jasdk_fk_order_id`(`order_id`) USING BTREE,
  INDEX `OrderItem_dish_id_as2jasdk_fk_dish_id`(`dish_id`) USING BTREE,
  CONSTRAINT `OrderItem_order_id_as2jasdk_fk_order_id` FOREIGN KEY (`order_id`) REFERENCES `Order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `OrderItem_dish_id_as2jasdk_fk_dish_id` FOREIGN KEY (`dish_id`) REFERENCES `Dish` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS `Member`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `level` int(10) NOT NULL DEFAULT 0,
  `expired_time` datetime NOT NULL,
  `availability` tinyint(1) NOT NULL DEFAULT 1,
  `customer_id` int(10) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `Member_customer_id_asdkh4kjas_fk_customer_id`(`customer_id`) USING BTREE,
  CONSTRAINT `Member_customer_id_asdkh4kjas_fk_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


