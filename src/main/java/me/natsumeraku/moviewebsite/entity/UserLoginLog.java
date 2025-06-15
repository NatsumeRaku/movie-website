package me.natsumeraku.moviewebsite.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;

/**
 * 用户登录记录表
 */
@TableName("user_login_log")
public class UserLoginLog {
    
    /**
     * 日志ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;
    
    /**
     * 登录时间
     */
    @TableField(value = "login_time", fill = FieldFill.INSERT)
    private LocalDateTime loginTime;
    
    /**
     * 登录IP
     */
    @TableField("login_ip")
    private String loginIp;
    
    /**
     * 用户代理
     */
    @TableField("user_agent")
    private String userAgent;
    
    // 构造函数
    public UserLoginLog() {}
    
    public UserLoginLog(Long userId, String loginIp, String userAgent) {
        this.userId = userId;
        this.loginIp = loginIp;
        this.userAgent = userAgent;
    }
    
    // Getter和Setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public LocalDateTime getLoginTime() {
        return loginTime;
    }
    
    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }
    
    public String getLoginIp() {
        return loginIp;
    }
    
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
    
    public String getUserAgent() {
        return userAgent;
    }
    
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    
    @Override
    public String toString() {
        return "UserLoginLog{" +
                "id=" + id +
                ", userId=" + userId +
                ", loginTime=" + loginTime +
                ", loginIp='" + loginIp + '\'' +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }
}