-- 添加admin用户的SQL脚本
-- 密码123456使用BCrypt加密

USE movie_db;

-- 插入admin用户
-- 密码123456的BCrypt哈希值: $2a$10$N.zmdr9k7uOCQb0bMs1XXe.H6JQ0Z9K6dVWiQqyJmTNoCiXzusDLu
INSERT INTO user (
    username, 
    password, 
    email, 
    role, 
    status, 
    create_time, 
    update_time
) VALUES (
    'admin', 
    '$2a$10$N.zmdr9k7uOCQb0bMs1XXe.H6JQ0Z9K6dVWiQqyJmTNoCiXzusDLu', 
    'admin@moviewebsite.com', 
    1, 
    1, 
    NOW(), 
    NOW()
);

-- 验证插入结果
SELECT id, username, email, role, status, create_time FROM user WHERE username = 'admin';