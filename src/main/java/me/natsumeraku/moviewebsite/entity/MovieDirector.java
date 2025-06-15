package me.natsumeraku.moviewebsite.entity;

import com.baomidou.mybatisplus.annotation.*;

/**
 * 电影-导演关联表(多对多)
 */
@TableName("movie_director")
public class MovieDirector {
    
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 电影ID
     */
    @TableField("movie_id")
    private Long movieId;
    
    /**
     * 导演ID
     */
    @TableField("director_id")
    private Long directorId;
    
    // 构造函数
    public MovieDirector() {}
    
    public MovieDirector(Long movieId, Long directorId) {
        this.movieId = movieId;
        this.directorId = directorId;
    }
    
    // Getter和Setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getMovieId() {
        return movieId;
    }
    
    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
    
    public Long getDirectorId() {
        return directorId;
    }
    
    public void setDirectorId(Long directorId) {
        this.directorId = directorId;
    }
    
    @Override
    public String toString() {
        return "MovieDirector{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", directorId=" + directorId +
                '}';
    }
}