package me.natsumeraku.moviewebsite.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 电影表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("movie")
public class Movie {
    
    /**
     * 电影ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 电影名称
     */
    @TableField("title")
    private String title;
    
    /**
     * 原片名
     */
    @TableField("original_title")
    private String originalTitle;
    
    /**
     * 电影类型(动作/喜剧/科幻等)
     */
    @TableField("type")
    private String type;
    
    /**
     * 地区
     */
    @TableField("region")
    private String region;
    
    /**
     * 上映日期
     */
    @TableField("release_date")
    private LocalDate releaseDate;
    
    /**
     * 片长(分钟)
     */
    @TableField("runtime")
    private Integer runtime;
    
    /**
     * 评分
     */
    @TableField("score")
    private BigDecimal score;
    
    /**
     * 播放次数
     */
    @TableField("play_count")
    private Long playCount;
    
    /**
     * 是否VIP影片(0-否,1-是)
     */
    @TableField("vip_flag")
    private Integer vipFlag;
    
    /**
     * 封面图URL
     */
    @TableField("cover_url")
    private String coverUrl;
    
    /**
     * 电影描述
     */
    @TableField("description")
    private String description;
    
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    

}