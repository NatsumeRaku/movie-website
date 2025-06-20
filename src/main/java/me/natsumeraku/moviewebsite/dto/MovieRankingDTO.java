package me.natsumeraku.moviewebsite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 电影排行榜数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRankingDTO {
    
    /**
     * 排名
     */
    private Integer rank;
    
    /**
     * 电影ID
     */
    private Long id;
    
    /**
     * 电影名称
     */
    private String title;
    
    /**
     * 电影类型
     */
    private String type;
    
    /**
     * 地区
     */
    private String region;
    
    /**
     * 上映日期
     */
    private LocalDate releaseDate;
    
    /**
     * 评分
     */
    private BigDecimal score;
    
    /**
     * 播放次数
     */
    private Long playCount;
    
    /**
     * 是否VIP影片
     */
    private Integer vipFlag;
    
    /**
     * 封面图URL
     */
    private String coverUrl;
    
    /**
     * 排行榜类型
     */
    private String rankType;
    
    /**
     * 排行榜统计值（播放次数或评分）
     */
    private String rankValue;
    
    /**
     * 排行榜统计时间范围
     */
    private String timeRange;
}