package me.natsumeraku.moviewebsite.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import me.natsumeraku.moviewebsite.common.Result;
import me.natsumeraku.moviewebsite.entity.User;
import me.natsumeraku.moviewebsite.service.UserService;
import me.natsumeraku.moviewebsite.util.SessionTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private SessionTokenUtil sessionTokenUtil;
    
    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return Result.badRequest("用户名不能为空");
        }
        
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return Result.badRequest("密码不能为空");
        }
        
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return Result.badRequest("邮箱不能为空");
        }
        
        boolean success = userService.register(user);
        if (success) {
            User registeredUser = userService.findByUsername(user.getUsername());
            registeredUser.setPassword(null);
            return Result.success("注册成功", registeredUser);
        } else {
            return Result.error("用户名或邮箱已存在");
        }
    }
    
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody User loginUser, HttpSession session) {
        if (loginUser.getUsername() == null || loginUser.getUsername().trim().isEmpty()) {
            return Result.badRequest("用户名不能为空");
        }
        if (loginUser.getPassword() == null || loginUser.getPassword().trim().isEmpty()) {
            return Result.badRequest("密码不能为空");
        }
        
        User user = userService.login(loginUser.getUsername(), loginUser.getPassword());
        if (user != null) {
            String token = sessionTokenUtil.generateToken(session, user);
            
            Map<String, Object> data = new HashMap<>();
            data.put("user", user);
            data.put("token", token);
            data.put("sessionId", session.getId());
            
            return Result.success("登录成功", data);
        } else {
            return Result.unauthorized("用户名或密码错误");
        }
    }
    
    @PostMapping("/logout")
    public Result<String> logout(HttpSession session) {
        sessionTokenUtil.removeTokenBySession(session);
        session.invalidate();
        return Result.success("退出登录成功");
    }
    
    @GetMapping("/current")
    public Result<User> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            user.setPassword(null);
            return Result.success(user);
        } else {
            return Result.unauthorized("用户未登录");
        }
    }
    
    @PostMapping("/check-username")
    public Result<Boolean> checkUsername(@RequestBody Map<String, String> data) {
        String username = data.get("username");
        if (username == null || username.trim().isEmpty()) {
            return Result.badRequest("用户名不能为空");
        }
        
        boolean exists = userService.existsByUsername(username);
        return Result.success(!exists);
    }
    
    @PostMapping("/check-email")
    public Result<Boolean> checkEmail(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        if (email == null || email.trim().isEmpty()) {
            return Result.badRequest("邮箱不能为空");
        }
        
        boolean exists = userService.existsByEmail(email);
        return Result.success(!exists);
    }
}