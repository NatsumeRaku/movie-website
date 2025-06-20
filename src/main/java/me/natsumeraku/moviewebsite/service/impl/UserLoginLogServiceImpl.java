package me.natsumeraku.moviewebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import me.natsumeraku.moviewebsite.entity.UserLoginLog;
import me.natsumeraku.moviewebsite.mapper.UserLoginLogMapper;
import me.natsumeraku.moviewebsite.service.UserLoginLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户登录日志服务实现类
 */
@Service
public class UserLoginLogServiceImpl implements UserLoginLogService {
    
    @Resource
    private UserLoginLogMapper userLoginLogMapper;
    
    @Override
    public boolean recordLoginLog(UserLoginLog loginLog) {
        loginLog.setLoginTime(LocalDateTime.now());
        return userLoginLogMapper.insert(loginLog) > 0;
    }
    
    @Override
    public boolean recordSuccessLogin(Long userId, String ipAddress, String deviceInfo) {
        UserLoginLog loginLog = new UserLoginLog();
        loginLog.setUserId(userId);
        loginLog.setIpAddress(ipAddress);
        loginLog.setDeviceInfo(deviceInfo);
        loginLog.setStatus(1); // 成功
        return recordLoginLog(loginLog);
    }
    
    @Override
    public boolean recordFailedLogin(Long userId, String ipAddress, String deviceInfo) {
        UserLoginLog loginLog = new UserLoginLog();
        loginLog.setUserId(userId);
        loginLog.setIpAddress(ipAddress);
        loginLog.setDeviceInfo(deviceInfo);
        loginLog.setStatus(0); // 失败
        return recordLoginLog(loginLog);
    }
    
    @Override
    public UserLoginLog findById(Long id) {
        return userLoginLogMapper.selectById(id);
    }
    
    @Override
    public IPage<UserLoginLog> getLoginLogsByUser(Page<UserLoginLog> page, Long userId) {
        QueryWrapper<UserLoginLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .orderByDesc("login_time");
        return userLoginLogMapper.selectPage(page, queryWrapper);
    }
    
    @Override
    public IPage<UserLoginLog> getAllLoginLogs(Page<UserLoginLog> page) {
        QueryWrapper<UserLoginLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("login_time");
        return userLoginLogMapper.selectPage(page, queryWrapper);
    }
    
    @Override
    public List<UserLoginLog> getRecentLoginLogs(Long userId, int limit) {
        QueryWrapper<UserLoginLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .orderByDesc("login_time")
                   .last("LIMIT " + limit);
        return userLoginLogMapper.selectList(queryWrapper);
    }
    
    @Override
    public long getUserLoginCount(Long userId) {
        QueryWrapper<UserLoginLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return userLoginLogMapper.selectCount(queryWrapper);
    }
    
    @Override
    public long getUserSuccessLoginCount(Long userId) {
        QueryWrapper<UserLoginLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .eq("status", 1);
        return userLoginLogMapper.selectCount(queryWrapper);
    }
    
    @Override
    public long getUserFailedLoginCount(Long userId) {
        QueryWrapper<UserLoginLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .eq("status", 0);
        return userLoginLogMapper.selectCount(queryWrapper);
    }
    
    @Override
    public long getTodayLoginUserCount() {
        LocalDate today = LocalDate.now();
        QueryWrapper<UserLoginLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("login_time", today.atStartOfDay())
                   .lt("login_time", today.plusDays(1).atStartOfDay())
                   .eq("status", 1)
                   .groupBy("user_id");
        // 这里需要使用自定义查询方法来统计不重复的用户数
        return userLoginLogMapper.selectCount(queryWrapper);
    }
    
    @Override
    public long getTodayLoginCount() {
        LocalDate today = LocalDate.now();
        QueryWrapper<UserLoginLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("login_time", today.atStartOfDay())
                   .lt("login_time", today.plusDays(1).atStartOfDay());
        return userLoginLogMapper.selectCount(queryWrapper);
    }
    
    @Override
    public IPage<UserLoginLog> getLoginLogsByIp(Page<UserLoginLog> page, String ipAddress) {
        QueryWrapper<UserLoginLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ip_address", ipAddress)
                   .orderByDesc("login_time");
        return userLoginLogMapper.selectPage(page, queryWrapper);
    }
    
    @Override
    public boolean isAbnormalLogin(String ipAddress, int timeMinutes, int maxAttempts) {
        LocalDateTime startTime = LocalDateTime.now().minusMinutes(timeMinutes);
        QueryWrapper<UserLoginLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ip_address", ipAddress)
                   .ge("login_time", startTime)
                   .eq("status", 0); // 失败的登录
        
        long failedAttempts = userLoginLogMapper.selectCount(queryWrapper);
        return failedAttempts >= maxAttempts;
    }
    
    @Override
    public UserLoginLog getLastSuccessLogin(Long userId) {
        QueryWrapper<UserLoginLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .eq("status", 1)
                   .orderByDesc("login_time")
                   .last("LIMIT 1");
        return userLoginLogMapper.selectOne(queryWrapper);
    }
    
    @Override
    public long deleteExpiredLogs(int days) {
        LocalDateTime expireTime = LocalDateTime.now().minusDays(days);
        QueryWrapper<UserLoginLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("login_time", expireTime);
        return userLoginLogMapper.delete(queryWrapper);
    }
    
    @Override
    public IPage<UserLoginLog> getLoginLogsByStatus(Page<UserLoginLog> page, Integer status) {
        QueryWrapper<UserLoginLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", status)
                   .orderByDesc("login_time");
        return userLoginLogMapper.selectPage(page, queryWrapper);
    }
}