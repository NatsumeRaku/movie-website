package me.natsumeraku.moviewebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.natsumeraku.moviewebsite.entity.MoviePlayLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 电影播放记录表 Mapper 接口
 */
@Mapper
public interface MoviePlayLogMapper extends BaseMapper<MoviePlayLog> {
    
    /**
     * 根据用户ID查询播放记录
     * @param page 分页参数
     * @param userId 用户ID
     * @return 播放记录列表
     */
    @Select("SELECT * FROM movie_play_log WHERE user_id = #{userId} ORDER BY play_time DESC")
    IPage<MoviePlayLog> selectByUserId(Page<MoviePlayLog> page, @Param("userId") Long userId);
    
    /**
     * 根据电影ID查询播放记录
     * @param page 分页参数
     * @param movieId 电影ID
     * @return 播放记录列表
     */
    @Select("SELECT * FROM movie_play_log WHERE movie_id = #{movieId} ORDER BY play_time DESC")
    IPage<MoviePlayLog> selectByMovieId(Page<MoviePlayLog> page, @Param("movieId") Long movieId);
    
    /**
     * 根据用户ID和电影ID查询播放记录
     * @param page 分页参数
     * @param userId 用户ID
     * @param movieId 电影ID
     * @return 播放记录列表
     */
    @Select("SELECT * FROM movie_play_log WHERE user_id = #{userId} AND movie_id = #{movieId} ORDER BY play_time DESC")
    IPage<MoviePlayLog> selectByUserIdAndMovieId(Page<MoviePlayLog> page, @Param("userId") Long userId, @Param("movieId") Long movieId);
    
    /**
     * 根据时间范围查询播放记录
     * @param page 分页参数
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 播放记录列表
     */
    @Select("SELECT * FROM movie_play_log WHERE play_time BETWEEN #{startTime} AND #{endTime} ORDER BY play_time DESC")
    IPage<MoviePlayLog> selectByTimeRange(Page<MoviePlayLog> page, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询用户最近观看的电影
     * @param userId 用户ID
     * @param limit 限制数量
     * @return 播放记录列表
     */
    @Select("SELECT * FROM movie_play_log WHERE user_id = #{userId} ORDER BY play_time DESC LIMIT #{limit}")
    List<MoviePlayLog> selectRecentWatchedByUserId(@Param("userId") Long userId, @Param("limit") Integer limit);
    
    /**
     * 查询用户观看历史（去重）
     * @param page 分页参数
     * @param userId 用户ID
     * @return 播放记录列表
     */
    @Select("SELECT * FROM movie_play_log WHERE user_id = #{userId} GROUP BY movie_id ORDER BY MAX(play_time) DESC")
    IPage<MoviePlayLog> selectWatchHistoryByUserId(Page<MoviePlayLog> page, @Param("userId") Long userId);
    
    /**
     * 统计用户观看次数
     * @param userId 用户ID
     * @return 观看次数
     */
    @Select("SELECT COUNT(*) FROM movie_play_log WHERE user_id = #{userId}")
    Long countByUserId(@Param("userId") Long userId);
    
    /**
     * 统计电影播放次数
     * @param movieId 电影ID
     * @return 播放次数
     */
    @Select("SELECT COUNT(*) FROM movie_play_log WHERE movie_id = #{movieId}")
    Long countByMovieId(@Param("movieId") Long movieId);
    
    /**
     * 统计用户观看的不同电影数量
     * @param userId 用户ID
     * @return 电影数量
     */
    @Select("SELECT COUNT(DISTINCT movie_id) FROM movie_play_log WHERE user_id = #{userId}")
    Long countDistinctMoviesByUserId(@Param("userId") Long userId);
    
    /**
     * 统计指定时间范围内的播放次数
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 播放次数
     */
    @Select("SELECT COUNT(*) FROM movie_play_log WHERE play_time BETWEEN #{startTime} AND #{endTime}")
    Long countByTimeRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计用户总观看时长
     * @param userId 用户ID
     * @return 总时长（秒）
     */
    @Select("SELECT COALESCE(SUM(play_duration), 0) FROM movie_play_log WHERE user_id = #{userId}")
    Long sumPlayDurationByUserId(@Param("userId") Long userId);
    
    /**
     * 统计电影总播放时长
     * @param movieId 电影ID
     * @return 总时长（秒）
     */
    @Select("SELECT COALESCE(SUM(play_duration), 0) FROM movie_play_log WHERE movie_id = #{movieId}")
    Long sumPlayDurationByMovieId(@Param("movieId") Long movieId);
}