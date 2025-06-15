package me.natsumeraku.moviewebsite.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 电影表
 */
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
    
    // 构造函数
    public Movie() {}
    
    // Getter和Setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getOriginalTitle() {
        return originalTitle;
    }
    
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    public LocalDate getReleaseDate() {
        return releaseDate;
    }
    
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    
    public Integer getRuntime() {
        return runtime;
    }
    
    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }
    
    public BigDecimal getScore() {
        return score;
    }
    
    public void setScore(BigDecimal score) {
        this.score = score;
    }
    
    public Long getPlayCount() {
        return playCount;
    }
    
    public void setPlayCount(Long playCount) {
        this.playCount = playCount;
    }
    
    public Integer getVipFlag() {
        return vipFlag;
    }
    
    public void setVipFlag(Integer vipFlag) {
        this.vipFlag = vipFlag;
    }
    
    public String getCoverUrl() {
        return coverUrl;
    }
    
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    
    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", type='" + type + '\'' +
                ", region='" + region + '\'' +
                ", releaseDate=" + releaseDate +
                ", runtime=" + runtime +
                ", score=" + score +
                ", playCount=" + playCount +
                ", vipFlag=" + vipFlag +
                ", coverUrl='" + coverUrl + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}