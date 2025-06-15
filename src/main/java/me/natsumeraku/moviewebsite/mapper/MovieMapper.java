package me.natsumeraku.moviewebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.natsumeraku.moviewebsite.entity.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 电影表 Mapper 接口
 */
@Mapper
public interface MovieMapper extends BaseMapper<Movie> {
    
    /**
     * 根据电影类型分页查询电影
     * @param page 分页参数
     * @param type 电影类型
     * @return 电影列表
     */
    @Select("SELECT * FROM movie WHERE type = #{type} ORDER BY play_count DESC")
    IPage<Movie> selectByType(Page<Movie> page, @Param("type") String type);
    
    /**
     * 根据地区分页查询电影
     * @param page 分页参数
     * @param region 地区
     * @return 电影列表
     */
    @Select("SELECT * FROM movie WHERE region = #{region} ORDER BY play_count DESC")
    IPage<Movie> selectByRegion(Page<Movie> page, @Param("region") String region);
    
    /**
     * 根据VIP标识查询电影
     * @param page 分页参数
     * @param vipFlag VIP标识
     * @return 电影列表
     */
    @Select("SELECT * FROM movie WHERE vip_flag = #{vipFlag} ORDER BY play_count DESC")
    IPage<Movie> selectByVipFlag(Page<Movie> page, @Param("vipFlag") Integer vipFlag);
    
    /**
     * 根据播放次数排序查询热门电影
     * @param limit 限制数量
     * @return 电影列表
     */
    @Select("SELECT * FROM movie ORDER BY play_count DESC LIMIT #{limit}")
    List<Movie> selectHotMovies(@Param("limit") Integer limit);
    
    /**
     * 根据评分排序查询高分电影
     * @param limit 限制数量
     * @return 电影列表
     */
    @Select("SELECT * FROM movie ORDER BY score DESC LIMIT #{limit}")
    List<Movie> selectTopRatedMovies(@Param("limit") Integer limit);
    
    /**
     * 模糊搜索电影
     * @param page 分页参数
     * @param keyword 关键词
     * @return 电影列表
     */
    @Select("SELECT * FROM movie WHERE title LIKE CONCAT('%', #{keyword}, '%') OR original_title LIKE CONCAT('%', #{keyword}, '%') ORDER BY play_count DESC")
    IPage<Movie> searchMovies(Page<Movie> page, @Param("keyword") String keyword);
    
    /**
     * 增加电影播放次数
     * @param movieId 电影ID
     * @param increment 增加数量
     * @return 影响行数
     */
    @Update("UPDATE movie SET play_count = play_count + #{increment}, update_time = NOW() WHERE id = #{movieId}")
    int incrementPlayCount(@Param("movieId") Long movieId, @Param("increment") Long increment);
    
    /**
     * 更新电影评分
     * @param movieId 电影ID
     * @param score 新评分
     * @return 影响行数
     */
    @Update("UPDATE movie SET score = #{score}, update_time = NOW() WHERE id = #{movieId}")
    int updateScore(@Param("movieId") Long movieId, @Param("score") java.math.BigDecimal score);
    
    /**
     * 统计指定类型的电影数量
     * @param type 电影类型
     * @return 电影数量
     */
    @Select("SELECT COUNT(*) FROM movie WHERE type = #{type}")
    Long countByType(@Param("type") String type);
    
    /**
     * 统计VIP电影数量
     * @param vipFlag VIP标识
     * @return 电影数量
     */
    @Select("SELECT COUNT(*) FROM movie WHERE vip_flag = #{vipFlag}")
    Long countByVipFlag(@Param("vipFlag") Integer vipFlag);
}