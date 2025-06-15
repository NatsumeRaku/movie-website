package me.natsumeraku.moviewebsite.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 电影导演关联表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("movie_director")
public class MovieDirector {
    
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
     * 导演ID
     */
    @TableField("director_id")
    private Long directorId;
    
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
}