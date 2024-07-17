DROP TABLE IF EXISTS `merchants`;
CREATE TABLE `merchants` (
                             `merchant_id` int unsigned NOT NULL AUTO_INCREMENT,
                             `merchant_name` varchar(30) NOT NULL,
                             `balance` decimal(18,4) unsigned NOT NULL,
                             `gmt_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                             `gmt_modified` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                             PRIMARY KEY (`merchant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商戶餘額表';
INSERT INTO `merchants` (`merchant_id`, `merchant_name`, `balance`, `gmt_created`, `gmt_modified`)
VALUES
    (1, 'weiwei商戶', 2000.0000, '2024-07-16 21:19:55.156', '2024-07-17 21:10:25.982'),
    (2, 'oo商戶', 800.0000, '2024-07-16 22:43:52.665', '2024-07-17 21:13:38.183'),
    (3, 'test商戶', 20660.0000, '2024-07-17 22:51:09.283', '2024-07-18 01:44:19.501');
DROP TABLE IF EXISTS `merchants_balance_log`;
CREATE TABLE `merchants_balance_log` (
                                         `id` int unsigned NOT NULL AUTO_INCREMENT,
                                         `merchant_id` int unsigned NOT NULL COMMENT '用戶id',
                                         `record_code` char(20) NOT NULL COMMENT '單號唯一值',
                                         `amount` decimal(18,4) NOT NULL,
                                         `after_balance` decimal(18,4) unsigned NOT NULL,
                                         `gmt_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                         `gmt_modified` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `uk_recordcode` (`record_code`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商戶餘額日誌表';
INSERT INTO `merchants_balance_log` (`id`, `merchant_id`, `record_code`, `amount`, `after_balance`, `gmt_created`, `gmt_modified`)
VALUES
    (1, 1, 'PO470449710016863066', 500.0000, 2000.0000, '2024-07-17 21:10:25.962', '2024-07-17 21:10:25.984'),
    (2, 2, 'PO470601780026231657', 500.0000, 800.0000, '2024-07-17 21:13:38.169', '2024-07-17 21:13:38.183'),
    (7, 3, 'PO480375720038728685', 300.0000, 20360.0000, '2024-07-18 01:25:11.566', '2024-07-18 01:25:11.579'),
    (8, 3, 'PO480644840032622569', 300.0000, 20660.0000, '2024-07-18 01:44:19.474', '2024-07-18 01:44:19.502');
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
                          `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'pk',
                          `record_code` char(20) NOT NULL COMMENT '單號唯一值',
                          `user_id` int unsigned NOT NULL DEFAULT '0' COMMENT '用戶id',
                          `total_price` decimal(18,5) NOT NULL DEFAULT '0.00000' COMMENT '訂單價格',
                          `sku` varchar(255) NOT NULL DEFAULT '' COMMENT '商品名稱',
                          `quantity` int unsigned NOT NULL DEFAULT '0' COMMENT '購買數量',
                          `gmt_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '創建時間',
                          `gmt_modified` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新時間',
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `uk_recordcode` (`record_code`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='訂單表';
INSERT INTO `orders` (`id`, `record_code`, `user_id`, `total_price`, `sku`, `quantity`, `gmt_created`, `gmt_modified`)
VALUES
    (1, 'PO470449710016863066', 1, 500.00000, '0000-0-1', 5, '2024-07-17 21:10:25.962', '2024-07-17 21:10:25.978'),
    (2, 'PO470601780026231657', 2, 500.00000, '0000-0-2', 5, '2024-07-17 21:13:38.169', '2024-07-17 21:13:38.179'),
    (12, 'PO480375720038728685', 3, 300.00000, '0000-0-3', 3, '2024-07-18 01:25:11.566', '2024-07-18 01:25:11.576'),
    (13, 'PO480644840032622569', 3, 300.00000, '0000-0-3', 3, '2024-07-18 01:44:19.474', '2024-07-18 01:44:19.495');

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
                           `sku` varchar(255) NOT NULL DEFAULT '' COMMENT '庫存單位 pk',
                           `name` varchar(255) NOT NULL DEFAULT '' COMMENT '商品名稱',
                           `price` decimal(18,5) NOT NULL DEFAULT '0.00000' COMMENT '商品價格',
                           `quantity` int unsigned NOT NULL DEFAULT '0' COMMENT '當前商品數量',
                           `quantity_all` int unsigned NOT NULL DEFAULT '0' COMMENT '商品總數',
                           `merchant_id` int unsigned NOT NULL COMMENT '商戶Id',
                           `gmt_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '創建時間',
                           `gmt_modified` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新時間',
                           PRIMARY KEY (`sku`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品表';
INSERT INTO `product` (`sku`, `name`, `price`, `quantity`, `quantity_all`, `merchant_id`, `gmt_created`, `gmt_modified`)
VALUES
    ('0000-0-1', '外套大衣-藍色', 100.00000, 195, 200, 1, '2024-07-16 21:20:34.749', '2024-07-17 21:10:25.971'),
    ('0000-0-2', '外套大衣-紅色', 100.00000, 196, 201, 2, '2024-07-16 22:40:16.814', '2024-07-17 21:13:38.177'),
    ('0000-0-3', '外套大衣-測試', 100.00000, 94, 100, 3, '2024-07-17 23:00:55.838', '2024-07-18 01:44:19.482');

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                         `user_id` int unsigned NOT NULL AUTO_INCREMENT,
                         `user_name` varchar(30) NOT NULL,
                         `balance` decimal(18,4) unsigned NOT NULL,
                         `gmt_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                         `gmt_modified` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                         PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用戶餘額表';
INSERT INTO `users` (`user_id`, `user_name`, `balance`, `gmt_created`, `gmt_modified`)
VALUES
    (1, 'weiwei顧客', 102000.0000, '2024-07-16 21:20:19.853', '2024-07-17 21:10:25.970'),
    (2, 'haha顧客', 102000.0000, '2024-07-16 22:42:12.370', '2024-07-17 21:13:38.177'),
    (3, 'test顧客', 200.0000, '2024-07-18 00:32:56.146', '2024-07-18 01:44:19.479');

DROP TABLE IF EXISTS `users_balance_log`;
CREATE TABLE `users_balance_log` (
                                     `id` int unsigned NOT NULL AUTO_INCREMENT,
                                     `user_id` int unsigned NOT NULL COMMENT '用戶id',
                                     `record_code` char(20) NOT NULL COMMENT '加扣錢款項code orderid也放這',
                                     `amount` decimal(18,4) NOT NULL,
                                     `gmt_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                     `gmt_modified` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                     PRIMARY KEY (`id`),
                                     UNIQUE KEY `uk_recordcode` (`record_code`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用戶餘額日誌表';
INSERT INTO `users_balance_log` (`id`, `user_id`, `record_code`, `amount`, `gmt_created`, `gmt_modified`)
VALUES
    (1, 1, 'UR471072110015877210', 1000.0000, '2024-07-17 20:58:41.220', '2024-07-17 20:58:41.220'),
    (2, 2, 'UR471126700024458559', 1000.0000, '2024-07-17 20:58:46.675', '2024-07-17 20:58:46.675'),
    (3, 2, 'UR470584590026119514', 1000.0000, '2024-07-17 21:09:40.469', '2024-07-17 21:09:40.469'),
    (4, 1, 'UR470618610012242240', 1000.0000, '2024-07-17 21:09:43.865', '2024-07-17 21:09:43.865'),
    (5, 1, 'PO470449710016863066', -500.0000, '2024-07-17 21:10:25.980', '2024-07-17 21:10:25.980'),
    (6, 2, 'PO470601780026231657', -500.0000, '2024-07-17 21:13:38.181', '2024-07-17 21:13:38.181'),
    (7, 3, 'UR480804180032577934', 100.0000, '2024-07-18 00:41:39.425', '2024-07-18 00:41:39.425'),
    (8, 3, 'UR480662410035882524', 100.0000, '2024-07-18 00:42:24.246', '2024-07-18 00:42:24.246'),
    (9, 3, 'UR480865720031437630', 100.0000, '2024-07-18 00:42:44.576', '2024-07-18 00:42:44.576'),
    (10, 3, 'UR480915760035606690', 100.0000, '2024-07-18 00:56:35.581', '2024-07-18 00:56:35.581'),
    (15, 3, 'PO480375720038728685', -300.0000, '2024-07-18 01:25:11.577', '2024-07-18 01:25:11.577'),
    (16, 3, 'PO480644840032622569', -300.0000, '2024-07-18 01:44:19.497', '2024-07-18 01:44:19.497');
