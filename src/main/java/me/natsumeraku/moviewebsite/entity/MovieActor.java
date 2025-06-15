package me.natsumeraku.moviewebsite.entity;

import com.baomidou.mybatisplus.annotation.*;

/**
 * 电影-演员关联表(多对多)
 */
@TableName("movie_actor")
public class MovieActor {
    
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
     * 演员ID
     */
    @TableField("actor_id")
    private Long actorId;
    
    /**
     * 角色名
     */
    @TableField("role_name")
    private String roleName;
    
    /**
     * 排序(主演顺序)
     */
    @TableField("sort")
    private Integer sort;
    
    // 构造函数
    public MovieActor() {}
    
    public MovieActor(Long movieId, Long actorId) {
        this.movieId = movieId;
        this.actorId = actorId;
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
    
    public Long getActorId() {
        return actorId;
    }
    
    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }
    
    public String getRoleName() {
        return roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    public Integer getSort() {
        return sort;
    }
    
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    
    @Override
    public String toString() {
        return "MovieActor{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", actorId=" + actorId +
                ", roleName='" + roleName + '\'' +
                ", sort=" + sort +
                '}';
    }
}