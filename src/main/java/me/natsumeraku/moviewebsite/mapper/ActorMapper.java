package me.natsumeraku.moviewebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
     * 根据演员姓名查询演员
     * @param name 演员姓名
     * @return 演员信息
     */
    @Select("SELECT * FROM actor WHERE name = #{name}")
    Actor selectByName(@Param("name") String name);
    
    /**
     * 根据性别查询演员
     * @param page 分页参数
     * @param gender 性别
     * @return 演员列表
     */
    @Select("SELECT * FROM actor WHERE gender = #{gender}")
    IPage<Actor> selectByGender(Page<Actor> page, @Param("gender") Integer gender);
    
    /**
     * 模糊搜索演员
     * @param page 分页参数
     * @param keyword 关键词
     * @return 演员列表
     */
    @Select("SELECT * FROM actor WHERE name LIKE CONCAT('%', #{keyword}, '%') OR english_name LIKE CONCAT('%', #{keyword}, '%')")
    IPage<Actor> searchActors(Page<Actor> page, @Param("keyword") String keyword);
    
    /**
     * 根据电影ID查询演员列表
     * @param movieId 电影ID
     * @return 演员列表
     */
    @Select("SELECT a.* FROM actor a INNER JOIN movie_actor ma ON a.id = ma.actor_id WHERE ma.movie_id = #{movieId} ORDER BY ma.sort")
    List<Actor> selectByMovieId(@Param("movieId") Long movieId);
    
    /**
     * 根据电影ID查询主演列表（前N位）
     * @param movieId 电影ID
     * @param limit 限制数量
     * @return 演员列表
     */
    @Select("SELECT a.* FROM actor a INNER JOIN movie_actor ma ON a.id = ma.actor_id WHERE ma.movie_id = #{movieId} ORDER BY ma.sort LIMIT #{limit}")
    List<Actor> selectMainActorsByMovieId(@Param("movieId") Long movieId, @Param("limit") Integer limit);
    
    /**
     * 统计指定性别的演员数量
     * @param gender 性别
     * @return 演员数量
     */
    @Select("SELECT COUNT(*) FROM actor WHERE gender = #{gender}")
    Long countByGender(@Param("gender") Integer gender);
    
    /**
     * 查询参演电影最多的演员
     * @param limit 限制数量
     * @return 演员列表
     */
    @Select("SELECT a.*, COUNT(ma.movie_id) as movie_count FROM actor a LEFT JOIN movie_actor ma ON a.id = ma.actor_id GROUP BY a.id ORDER BY movie_count DESC LIMIT #{limit}")
    List<Actor> selectMostActiveActors(@Param("limit") Integer limit);
}