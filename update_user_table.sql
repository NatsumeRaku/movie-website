-- 更新用户表，添加缺失的字段
USE movie_db;

-- 添加email字段
ALTER TABLE `user` ADD COLUMN `email` varchar(100) DEFAULT NULL COMMENT '邮箱' AFTER `password`;

-- 添加last_login_time字段
ALTER TABLE `user` ADD COLUMN `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间' AFTER `status`;

-- 为email字段添加唯一索引
ALTER TABLE `user` ADD UNIQUE KEY `idx_email` (`email`);

-- 查看更新后的表结构
DESC `user`;