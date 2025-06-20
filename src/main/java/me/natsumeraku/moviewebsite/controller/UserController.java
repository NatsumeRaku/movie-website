package me.natsumeraku.moviewebsite.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import me.natsumeraku.moviewebsite.common.Result;
import me.natsumeraku.moviewebsite.entity.User;
import me.natsumeraku.moviewebsite.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Resource
    private UserService userService;

    @PutMapping("/profile")
    public Result<User> updateProfile(@RequestBody User user, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return Result.unauthorized("用户未登录");
        }
        
        user.setId(currentUser.getId());
        boolean success = userService.updateUser(user);
        
        if (success) {
            User updatedUser = userService.findById(currentUser.getId());
            session.setAttribute("user", updatedUser);
            updatedUser.setPassword(null);
            return Result.success("更新成功", updatedUser);
        } else {
            return Result.error("更新失败");
        }
    }
    
    @GetMapping("/list")
    public Result<IPage<User>> getUserList(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "10") int size,
            HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return Result.unauthorized("用户未登录");
        }
        
        Page<User> page = new Page<>(current, size);
        IPage<User> userPage = userService.getUserList(page);
        
        userPage.getRecords().forEach(user -> user.setPassword(null));
        
        return Result.success(userPage);
    }
}