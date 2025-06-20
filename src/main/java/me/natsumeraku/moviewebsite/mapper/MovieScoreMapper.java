package me.natsumeraku.moviewebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.natsumeraku.moviewebsite.entity.MovieScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 电影评分Mapper接口
 */
@Mapper
public interface MovieScoreMapper extends BaseMapper<MovieScore> {
    
    /**
     * 计算电影平均评分
     * @param movieId 电影ID
     * @return 平均评分
     */
    @Select("SELECT AVG(score) FROM movie_score WHERE movie_id = #{movieId}")
    Double selectAverageScoreByMovieId(@Param("movieId") Long movieId);
}