package me.natsumeraku.moviewebsite.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;

/**
 * 电影播放记录表
 */
@TableName("movie_play_log")
public class MoviePlayLog {
    
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
     * 电影ID
     */
    @TableField("movie_id")
    private Long movieId;
    
    /**
     * 播放时间
     */
    @TableField(value = "play_time", fill = FieldFill.INSERT)
    private LocalDateTime playTime;
    
    /**
     * 播放时长(秒)
     */
    @TableField("play_duration")
    private Integer playDuration;
    
    /**
     * IP地址
     */
    @TableField("ip_address")
    private String ipAddress;
    
    // 构造函数
    public MoviePlayLog() {}
    
    public MoviePlayLog(Long userId, Long movieId, Integer playDuration, String ipAddress) {
        this.userId = userId;
        this.movieId = movieId;
        this.playDuration = playDuration;
        this.ipAddress = ipAddress;
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
    
    public Long getMovieId() {
        return movieId;
    }
    
    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
    
    public LocalDateTime getPlayTime() {
        return playTime;
    }
    
    public void setPlayTime(LocalDateTime playTime) {
        this.playTime = playTime;
    }
    
    public Integer getPlayDuration() {
        return playDuration;
    }
    
    public void setPlayDuration(Integer playDuration) {
        this.playDuration = playDuration;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    @Override
    public String toString() {
        return "MoviePlayLog{" +
                "id=" + id +
                ", userId=" + userId +
                ", movieId=" + movieId +
                ", playTime=" + playTime +
                ", playDuration=" + playDuration +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }
}