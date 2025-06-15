package me.natsumeraku.moviewebsite.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;

/**
 * 电影评分表
 */
@TableName("movie_score")
public class MovieScore {
    
    /**
     * ID
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
     * 评分(1-10)
     */
    @TableField("score")
    private Integer score;
    
    /**
     * 评论内容
     */
    @TableField("comment")
    private String comment;
    
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    // 构造函数
    public MovieScore() {}
    
    public MovieScore(Long userId, Long movieId, Integer score, String comment) {
        this.userId = userId;
        this.movieId = movieId;
        this.score = score;
        this.comment = comment;
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
    
    public Integer getScore() {
        return score;
    }
    
    public void setScore(Integer score) {
        this.score = score;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    
    @Override
    public String toString() {
        return "MovieScore{" +
                "id=" + id +
                ", userId=" + userId +
                ", movieId=" + movieId +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}