package me.natsumeraku.moviewebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.natsumeraku.moviewebsite.entity.MovieDirector;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 电影-导演关联表 Mapper 接口
 */
@Mapper
public interface MovieDirectorMapper extends BaseMapper<MovieDirector> {
    
    /**
     * 根据电影ID查询导演关联信息
     * @param movieId 电影ID
     * @return 关联信息列表
     */
    @Select("SELECT * FROM movie_director WHERE movie_id = #{movieId}")
    List<MovieDirector> selectByMovieId(@Param("movieId") Long movieId);
    
    /**
     * 根据导演ID查询电影关联信息
     * @param directorId 导演ID
     * @return 关联信息列表
     */
    @Select("SELECT * FROM movie_director WHERE director_id = #{directorId}")
    List<MovieDirector> selectByDirectorId(@Param("directorId") Long directorId);
    
    /**
     * 根据电影ID和导演ID查询关联信息
     * @param movieId 电影ID
     * @param directorId 导演ID
     * @return 关联信息
     */
    @Select("SELECT * FROM movie_director WHERE movie_id = #{movieId} AND director_id = #{directorId}")
    MovieDirector selectByMovieIdAndDirectorId(@Param("movieId") Long movieId, @Param("directorId") Long directorId);
    
    /**
     * 根据电影ID删除所有导演关联
     * @param movieId 电影ID
     * @return 影响行数
     */
    @Delete("DELETE FROM movie_director WHERE movie_id = #{movieId}")
    int deleteByMovieId(@Param("movieId") Long movieId);
    
    /**
     * 根据导演ID删除所有电影关联
     * @param directorId 导演ID
     * @return 影响行数
     */
    @Delete("DELETE FROM movie_director WHERE director_id = #{directorId}")
    int deleteByDirectorId(@Param("directorId") Long directorId);
    
    /**
     * 根据电影ID和导演ID删除关联
     * @param movieId 电影ID
     * @param directorId 导演ID
     * @return 影响行数
     */
    @Delete("DELETE FROM movie_director WHERE movie_id = #{movieId} AND director_id = #{directorId}")
    int deleteByMovieIdAndDirectorId(@Param("movieId") Long movieId, @Param("directorId") Long directorId);
    
    /**
     * 统计导演执导的电影数量
     * @param directorId 导演ID
     * @return 电影数量
     */
    @Select("SELECT COUNT(*) FROM movie_director WHERE director_id = #{directorId}")
    Long countMoviesByDirectorId(@Param("directorId") Long directorId);
    
    /**
     * 统计电影的导演数量
     * @param movieId 电影ID
     * @return 导演数量
     */
    @Select("SELECT COUNT(*) FROM movie_director WHERE movie_id = #{movieId}")
    Long countDirectorsByMovieId(@Param("movieId") Long movieId);
}