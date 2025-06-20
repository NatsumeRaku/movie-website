package me.natsumeraku.moviewebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.natsumeraku.moviewebsite.dto.MovieRankingDTO;
import me.natsumeraku.moviewebsite.entity.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 电影Mapper接口
 */
@Mapper
public interface MovieMapper extends BaseMapper<Movie> {
    
    /**
     * 根据导演ID查询电影
     */
    @Select("SELECT m.* FROM movie m " +
            "INNER JOIN movie_director md ON m.id = md.movie_id " +
            "WHERE md.director_id = #{directorId}")
    List<Movie> selectMoviesByDirector(@Param("directorId") Long directorId);
    
    /**
     * 根据演员ID查询电影
     */
    @Select("SELECT m.* FROM movie m " +
            "INNER JOIN movie_actor ma ON m.id = ma.movie_id " +
            "WHERE ma.actor_id = #{actorId}")
    List<Movie> selectMoviesByActor(@Param("actorId") Long actorId);
    
    /**
     * 查询电影类型分布统计
     */
    @Select("SELECT type as name, COUNT(*) as value " +
            "FROM movie " +
            "GROUP BY type " +
            "ORDER BY value DESC")
    List<MovieRankingDTO> selectMovieTypeDistribution();
    
    /**
     * 查询月度播放趋势统计
     */
    @Select("SELECT DATE_FORMAT(mpl.play_time, '%Y-%m') as name, " +
            "COUNT(*) as value " +
            "FROM movie_play_log mpl " +
            "WHERE mpl.play_time >= DATE_SUB(NOW(), INTERVAL 12 MONTH) " +
            "GROUP BY DATE_FORMAT(mpl.play_time, '%Y-%m') " +
            "ORDER BY name")
    List<MovieRankingDTO> selectMonthlyPlayTrend();
    
    /**
     * 查询电影地区分布统计
     */
    @Select("SELECT region as name, COUNT(*) as value " +
            "FROM movie " +
            "GROUP BY region " +
            "ORDER BY value DESC")
    List<MovieRankingDTO> selectMovieRegionDistribution();
}