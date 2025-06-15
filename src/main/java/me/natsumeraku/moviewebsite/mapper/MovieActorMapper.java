package me.natsumeraku.moviewebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.natsumeraku.moviewebsite.entity.MovieActor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 电影-演员关联表 Mapper 接口
 */
@Mapper
public interface MovieActorMapper extends BaseMapper<MovieActor> {
    
    /**
     * 根据电影ID查询演员关联信息
     * @param movieId 电影ID
     * @return 关联信息列表
     */
    @Select("SELECT * FROM movie_actor WHERE movie_id = #{movieId} ORDER BY sort")
    List<MovieActor> selectByMovieId(@Param("movieId") Long movieId);
    
    /**
     * 根据演员ID查询电影关联信息
     * @param actorId 演员ID
     * @return 关联信息列表
     */
    @Select("SELECT * FROM movie_actor WHERE actor_id = #{actorId}")
    List<MovieActor> selectByActorId(@Param("actorId") Long actorId);
    
    /**
     * 根据电影ID和演员ID查询关联信息
     * @param movieId 电影ID
     * @param actorId 演员ID
     * @return 关联信息
     */
    @Select("SELECT * FROM movie_actor WHERE movie_id = #{movieId} AND actor_id = #{actorId}")
    MovieActor selectByMovieIdAndActorId(@Param("movieId") Long movieId, @Param("actorId") Long actorId);
    
    /**
     * 根据电影ID删除所有演员关联
     * @param movieId 电影ID
     * @return 影响行数
     */
    @Delete("DELETE FROM movie_actor WHERE movie_id = #{movieId}")
    int deleteByMovieId(@Param("movieId") Long movieId);
    
    /**
     * 根据演员ID删除所有电影关联
     * @param actorId 演员ID
     * @return 影响行数
     */
    @Delete("DELETE FROM movie_actor WHERE actor_id = #{actorId}")
    int deleteByActorId(@Param("actorId") Long actorId);
    
    /**
     * 根据电影ID和演员ID删除关联
     * @param movieId 电影ID
     * @param actorId 演员ID
     * @return 影响行数
     */
    @Delete("DELETE FROM movie_actor WHERE movie_id = #{movieId} AND actor_id = #{actorId}")
    int deleteByMovieIdAndActorId(@Param("movieId") Long movieId, @Param("actorId") Long actorId);
    
    /**
     * 统计演员参演的电影数量
     * @param actorId 演员ID
     * @return 电影数量
     */
    @Select("SELECT COUNT(*) FROM movie_actor WHERE actor_id = #{actorId}")
    Long countMoviesByActorId(@Param("actorId") Long actorId);
    
    /**
     * 统计电影的演员数量
     * @param movieId 电影ID
     * @return 演员数量
     */
    @Select("SELECT COUNT(*) FROM movie_actor WHERE movie_id = #{movieId}")
    Long countActorsByMovieId(@Param("movieId") Long movieId);
    
    /**
     * 查询电影的主演信息（按排序）
     * @param movieId 电影ID
     * @param limit 限制数量
     * @return 关联信息列表
     */
    @Select("SELECT * FROM movie_actor WHERE movie_id = #{movieId} ORDER BY sort LIMIT #{limit}")
    List<MovieActor> selectMainActorsByMovieId(@Param("movieId") Long movieId, @Param("limit") Integer limit);
}