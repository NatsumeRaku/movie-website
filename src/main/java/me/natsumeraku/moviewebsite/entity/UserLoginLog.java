package me.natsumeraku.moviewebsite.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 用户登录日志表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
     * 登录IP地址
     */
    @TableField("ip_address")
    private String ipAddress;
    
    /**
     * 登录设备信息
     */
    @TableField("device_info")
    private String deviceInfo;
    
    /**
     * 登录状态(0-失败,1-成功)
     */
    @TableField("status")
    private Integer status;
    
    /**
     * 登录时间
     */
    @TableField(value = "login_time", fill = FieldFill.INSERT)
    private LocalDateTime loginTime;
    
}