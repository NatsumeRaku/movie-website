package me.natsumeraku.moviewebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.natsumeraku.moviewebsite.entity.Actor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 演员表 Mapper 接口
 */
@Mapper
public interface ActorMapper extends BaseMapper<Actor> {
    
    /**
     * 查询热门演员（根据参演电影数量排序）
     * @param limit 限制数量
     * @return 演员列表
     */
    @Select("SELECT a.*, COUNT(ma.movie_id) as movie_count FROM actor a LEFT JOIN movie_actor ma ON a.id = ma.actor_id GROUP BY a.id ORDER BY movie_count DESC LIMIT #{limit}")
    List<Actor> selectPopularActors(@Param("limit") Integer limit);
}