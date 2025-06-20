package me.natsumeraku.moviewebsite.controller;

import me.natsumeraku.moviewebsite.entity.User;
import me.natsumeraku.moviewebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员控制器
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 创建admin用户
     * 访问: http://localhost:8080/admin/create-admin
     */
    @GetMapping("/create-admin")
    public String createAdmin() {
        try {
            // 检查admin用户是否已存在
            if (userService.existsByUsername("admin")) {
                return "❌ Admin用户已存在，无需重复创建！";
            }
            
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword("123456"); // 原始密码，会被自动加密

            adminUser.setRole(1); // 设置为VIP用户
            adminUser.setStatus(1); // 正常状态
            
            boolean success = userService.register(adminUser);
            if (success) {
                return "✅ Admin用户创建成功！<br/>" +
                       "用户名: admin<br/>" +
                       "密码: 123456<br/>" +
                       "角色: VIP用户<br/>" +
                       "<br/>现在可以使用这些凭据登录系统了！";
            } else {
                return "❌ Admin用户创建失败！请检查数据库连接和配置。";
            }
        } catch (Exception e) {
            return "❌ 创建Admin用户时发生错误: " + e.getMessage();
        }
    }
    
    /**
     * 检查admin用户状态
     * 访问: http://localhost:8080/admin/check-admin
     */
    @GetMapping("/check-admin")
    public String checkAdmin() {
        try {
            if (userService.existsByUsername("admin")) {
                User admin = userService.findByUsername("admin");
                return "✅ Admin用户存在！<br/>" +
                       "用户ID: " + admin.getId() + "<br/>" +
                       "用户名: " + admin.getUsername() + "<br/>" +
                       "角色: " + (admin.getRole() == 1 ? "VIP用户" : "普通用户") + "<br/>" +
                       "状态: " + (admin.getStatus() == 1 ? "正常" : "禁用") + "<br/>" +
                       "创建时间: " + admin.getCreateTime();
            } else {
                return "❌ Admin用户不存在！请先创建admin用户。";
            }
        } catch (Exception e) {
            return "❌ 检查Admin用户时发生错误: " + e.getMessage();
        }
    }
}