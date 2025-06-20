package me.natsumeraku.moviewebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.natsumeraku.moviewebsite.entity.UserLoginLog;

import java.util.List;

/**
 * 用户登录日志服务接口
 */
public interface UserLoginLogService {
    
    /**
     * 记录登录日志
     * @param loginLog 登录日志
     * @return 记录结果
     */
    boolean recordLoginLog(UserLoginLog loginLog);
    
    /**
     * 记录登录成功日志
     * @param userId 用户ID
     * @param ipAddress IP地址
     * @param deviceInfo 设备信息
     * @return 记录结果
     */
    boolean recordSuccessLogin(Long userId, String ipAddress, String deviceInfo);
    
    /**
     * 记录登录失败日志
     * @param userId 用户ID（可能为null）
     * @param ipAddress IP地址
     * @param deviceInfo 设备信息
     * @return 记录结果
     */
    boolean recordFailedLogin(Long userId, String ipAddress, String deviceInfo);
    
    /**
     * 根据ID查询登录日志
     * @param id 日志ID
     * @return 登录日志
     */
    UserLoginLog findById(Long id);
    
    /**
     * 分页查询用户的登录日志
     * @param page 分页参数
     * @param userId 用户ID
     * @return 分页结果
     */
    IPage<UserLoginLog> getLoginLogsByUser(Page<UserLoginLog> page, Long userId);
    
    /**
     * 分页查询所有登录日志（管理员用）
     * @param page 分页参数
     * @return 分页结果
     */
    IPage<UserLoginLog> getAllLoginLogs(Page<UserLoginLog> page);
    
    /**
     * 获取用户最近的登录记录
     * @param userId 用户ID
     * @param limit 数量限制
     * @return 最近登录记录列表
     */
    List<UserLoginLog> getRecentLoginLogs(Long userId, int limit);
    
    /**
     * 获取用户的登录次数统计
     * @param userId 用户ID
     * @return 登录总次数
     */
    long getUserLoginCount(Long userId);
    
    /**
     * 获取用户的成功登录次数
     * @param userId 用户ID
     * @return 成功登录次数
     */
    long getUserSuccessLoginCount(Long userId);
    
    /**
     * 获取用户的失败登录次数
     * @param userId 用户ID
     * @return 失败登录次数
     */
    long getUserFailedLoginCount(Long userId);
    
    /**
     * 获取今日登录用户数
     * @return 今日登录用户数
     */
    long getTodayLoginUserCount();
    
    /**
     * 获取今日登录次数
     * @return 今日登录次数
     */
    long getTodayLoginCount();
    
    /**
     * 获取指定IP的登录记录
     * @param page 分页参数
     * @param ipAddress IP地址
     * @return 分页结果
     */
    IPage<UserLoginLog> getLoginLogsByIp(Page<UserLoginLog> page, String ipAddress);
    
    /**
     * 检查IP是否存在异常登录行为
     * @param ipAddress IP地址
     * @param timeMinutes 时间范围（分钟）
     * @param maxAttempts 最大尝试次数
     * @return 是否异常
     */
    boolean isAbnormalLogin(String ipAddress, int timeMinutes, int maxAttempts);
    
    /**
     * 获取用户最后一次成功登录记录
     * @param userId 用户ID
     * @return 最后一次成功登录记录
     */
    UserLoginLog getLastSuccessLogin(Long userId);
    
    /**
     * 删除过期的登录日志
     * @param days 保留天数
     * @return 删除数量
     */
    long deleteExpiredLogs(int days);
    
    /**
     * 根据状态查询登录日志
     * @param page 分页参数
     * @param status 登录状态
     * @return 分页结果
     */
    IPage<UserLoginLog> getLoginLogsByStatus(Page<UserLoginLog> page, Integer status);
}