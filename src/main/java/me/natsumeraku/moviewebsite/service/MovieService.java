package me.natsumeraku.moviewebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.natsumeraku.moviewebsite.entity.Movie;

import java.util.List;

/**
 * 电影服务接口
 */
public interface MovieService {
    
    /**
     * 添加电影
     * @param movie 电影信息
     * @return 添加结果
     */
    boolean addMovie(Movie movie);
    
    /**
     * 根据ID查询电影
     * @param id 电影ID
     * @return 电影信息
     */
    Movie findById(Long id);
    
    /**
     * 更新电影信息
     * @param movie 电影信息
     * @return 更新结果
     */
    boolean updateMovie(Movie movie);
    
    /**
     * 删除电影
     * @param id 电影ID
     * @return 删除结果
     */
    boolean deleteMovie(Long id);
    
    /**
     * 分页查询电影列表
     * @param page 分页参数
     * @return 分页结果
     */
    IPage<Movie> getMovieList(Page<Movie> page);
    
    /**
     * 根据类型分页查询电影
     * @param page 分页参数
     * @param type 电影类型
     * @return 分页结果
     */
    IPage<Movie> getMoviesByType(Page<Movie> page, String type);
    
    /**
     * 根据关键词搜索电影
     * @param page 分页参数
     * @param keyword 关键词
     * @return 分页结果
     */
    IPage<Movie> searchMovies(Page<Movie> page, String keyword);
    
    /**
     * 获取热门电影（按播放量排序）
     * @param limit 数量限制
     * @return 热门电影列表
     */
    List<Movie> getHotMovies(int limit);
    
    /**
     * 获取最新电影（按发布时间排序）
     * @param limit 数量限制
     * @return 最新电影列表
     */
    List<Movie> getLatestMovies(int limit);
    
    /**
     * 获取推荐电影（按评分排序）
     * @param limit 数量限制
     * @return 推荐电影列表
     */
    List<Movie> getRecommendedMovies(int limit);
    
    /**
     * 增加电影播放次数
     * @param movieId 电影ID
     * @return 更新结果
     */
    boolean incrementPlayCount(Long movieId);
    
    /**
     * 获取电影总数
     * @return 电影总数
     */
    long getTotalCount();
    
    /**
     * 根据导演ID查询电影
     * @param directorId 导演ID
     * @return 电影列表
     */
    List<Movie> getMoviesByDirector(Long directorId);
    
    /**
     * 根据演员ID查询电影
     * @param actorId 演员ID
     * @return 电影列表
     */
    List<Movie> getMoviesByActor(Long actorId);
}