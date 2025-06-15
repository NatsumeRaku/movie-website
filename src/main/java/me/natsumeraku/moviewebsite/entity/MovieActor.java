package me.natsumeraku.moviewebsite.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 电影演员关联表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("movie_actor")
public class MovieActor {
    
    /**
     * 关联ID
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
     * 是否主演(0-否,1-是)
     */
    @TableField("is_main")
    private Integer isMain;
    
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
}