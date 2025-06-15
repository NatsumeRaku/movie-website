package me.natsumeraku.moviewebsite.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 电影播放记录表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("movie_play_log")
public class MoviePlayLog {
    
    /**
     * 记录ID
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
     * 播放时长(秒)
     */
    @TableField("play_duration")
    private Integer playDuration;
    
    /**
     * 播放进度(百分比)
     */
    @TableField("play_progress")
    private Integer playProgress;
    
    /**
     * 播放时间
     */
    @TableField(value = "play_time", fill = FieldFill.INSERT)
    private LocalDateTime playTime;
    
}