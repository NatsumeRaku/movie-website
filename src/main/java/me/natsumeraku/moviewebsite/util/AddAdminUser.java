package me.natsumeraku.moviewebsite.util;

import me.natsumeraku.moviewebsite.entity.User;
import me.natsumeraku.moviewebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 添加admin用户的工具类
 * 运行一次后请删除此文件或注释掉@Component注解
 */
// @Component // 注释掉以避免自动执行
public class AddAdminUser implements CommandLineRunner {
    
    @Autowired
    private UserService userService;
    
    @Override
    public void run(String... args) throws Exception {
        // 检查admin用户是否已存在
        if (!userService.existsByUsername("admin")) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword("123456"); // 原始密码，会被自动加密

            adminUser.setRole(1); // 设置为VIP用户
            adminUser.setStatus(1); // 正常状态
            
            boolean success = userService.register(adminUser);
            if (success) {
                System.out.println("✅ Admin用户创建成功！");
                System.out.println("用户名: admin");
                System.out.println("密码: 123456");

                System.out.println("角色: VIP用户");
            } else {
                System.out.println("❌ Admin用户创建失败！");
            }
        } else {
            System.out.println("ℹ️ Admin用户已存在，跳过创建。");
        }
    }
}