package me.natsumeraku.moviewebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import me.natsumeraku.moviewebsite.entity.User;
import me.natsumeraku.moviewebsite.mapper.UserMapper;
import me.natsumeraku.moviewebsite.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    
    @Resource
    private UserMapper userMapper;
    
    @Resource
    private BCryptPasswordEncoder passwordEncoder;
    
    @Override
    public boolean register(User user) {
        if (existsByUsername(user.getUsername())) {
            return false;
        }
        
        if (existsByEmail(user.getEmail())) {
            return false;
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setRole(0);
        user.setStatus(1);
        
        return userMapper.insert(user) > 0;
    }
    
    @Override
    public User login(String username, String password) {
        User user = findByUsername(username);
        if (user != null && user.getStatus() == 1 && passwordEncoder.matches(password, user.getPassword())) {
            user.setLastLoginTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            userMapper.updateById(user);
            return user;
        }
        return null;
    }
    
    @Override
    public User findByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }
    
    @Override
    public User findById(Long id) {
        return userMapper.selectById(id);
    }
    
    @Override
    public boolean updateUser(User user) {
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.updateById(user) > 0;
    }
    
    @Override
    public IPage<User> getUserList(Page<User> page) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        return userMapper.selectPage(page, queryWrapper);
    }
    
    @Override
    public boolean deleteUser(Long id) {
        return userMapper.deleteById(id) > 0;
    }
    
    @Override
    public boolean existsByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userMapper.selectCount(queryWrapper) > 0;
    }
    
    @Override
    public boolean existsByEmail(String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        return userMapper.selectCount(queryWrapper) > 0;
    }

    private String encryptPassword(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}