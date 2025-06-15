package me.natsumeraku.moviewebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.natsumeraku.moviewebsite.entity.MovieScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * 电影评分表 Mapper 接口
 */
@Mapper
public interface MovieScoreMapper extends BaseMapper<MovieScore> {
    
    /**
     * 根据用户ID查询评分记录
     * @param page 分页参数
     * @param userId 用户ID
     * @return 评分记录列表
     */
    @Select("SELECT * FROM movie_score WHERE user_id = #{userId} ORDER BY create_time DESC")
    IPage<MovieScore> selectByUserId(Page<MovieScore> page, @Param("userId") Long userId);
    
    /**
     * 根据电影ID查询评分记录
     * @param page 分页参数
     * @param movieId 电影ID
     * @return 评分记录列表
     */
    @Select("SELECT * FROM movie_score WHERE movie_id = #{movieId} ORDER BY create_time DESC")
    IPage<MovieScore> selectByMovieId(Page<MovieScore> page, @Param("movieId") Long movieId);
    
    /**
     * 根据用户ID和电影ID查询评分记录
     * @param userId 用户ID
     * @param movieId 电影ID
     * @return 评分记录
     */
    @Select("SELECT * FROM movie_score WHERE user_id = #{userId} AND movie_id = #{movieId}")
    MovieScore selectByUserIdAndMovieId(@Param("userId") Long userId, @Param("movieId") Long movieId);
    
    /**
     * 根据评分范围查询评分记录
     * @param page 分页参数
     * @param minScore 最低评分
     * @param maxScore 最高评分
     * @return 评分记录列表
     */
    @Select("SELECT * FROM movie_score WHERE score BETWEEN #{minScore} AND #{maxScore} ORDER BY create_time DESC")
    IPage<MovieScore> selectByScoreRange(Page<MovieScore> page, @Param("minScore") Integer minScore, @Param("maxScore") Integer maxScore);
    
    /**
     * 查询电影的最新评论
     * @param movieId 电影ID
     * @param limit 限制数量
     * @return 评分记录列表
     */
    @Select("SELECT * FROM movie_score WHERE movie_id = #{movieId} AND comment IS NOT NULL AND comment != '' ORDER BY create_time DESC LIMIT #{limit}")
    List<MovieScore> selectLatestCommentsByMovieId(@Param("movieId") Long movieId, @Param("limit") Integer limit);
    
    /**
     * 查询用户最近的评分记录
     * @param userId 用户ID
     * @param limit 限制数量
     * @return 评分记录列表
     */
    @Select("SELECT * FROM movie_score WHERE user_id = #{userId} ORDER BY create_time DESC LIMIT #{limit}")
    List<MovieScore> selectRecentScoresByUserId(@Param("userId") Long userId, @Param("limit") Integer limit);
    
    /**
     * 计算电影平均评分
     * @param movieId 电影ID
     * @return 平均评分
     */
    @Select("SELECT AVG(score) FROM movie_score WHERE movie_id = #{movieId}")
    BigDecimal calculateAverageScore(@Param("movieId") Long movieId);
    
    /**
     * 统计电影评分数量
     * @param movieId 电影ID
     * @return 评分数量
     */
    @Select("SELECT COUNT(*) FROM movie_score WHERE movie_id = #{movieId}")
    Long countByMovieId(@Param("movieId") Long movieId);
    
    /**
     * 统计用户评分数量
     * @param userId 用户ID
     * @return 评分数量
     */
    @Select("SELECT COUNT(*) FROM movie_score WHERE user_id = #{userId}")
    Long countByUserId(@Param("userId") Long userId);
    
    /**
     * 统计指定评分的数量
     * @param movieId 电影ID
     * @param score 评分
     * @return 评分数量
     */
    @Select("SELECT COUNT(*) FROM movie_score WHERE movie_id = #{movieId} AND score = #{score}")
    Long countByMovieIdAndScore(@Param("movieId") Long movieId, @Param("score") Integer score);
    
    /**
     * 查询电影评分分布
     * @param movieId 电影ID
     * @return 评分分布列表
     */
    @Select("SELECT score, COUNT(*) as count FROM movie_score WHERE movie_id = #{movieId} GROUP BY score ORDER BY score DESC")
    List<Object> selectScoreDistributionByMovieId(@Param("movieId") Long movieId);
    
    /**
     * 查询有评论的评分记录
     * @param page 分页参数
     * @param movieId 电影ID
     * @return 评分记录列表
     */
    @Select("SELECT * FROM movie_score WHERE movie_id = #{movieId} AND comment IS NOT NULL AND comment != '' ORDER BY create_time DESC")
    IPage<MovieScore> selectWithCommentsByMovieId(Page<MovieScore> page, @Param("movieId") Long movieId);
}