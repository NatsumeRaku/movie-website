package me.natsumeraku.moviewebsite.service;

import me.natsumeraku.moviewebsite.entity.MovieScore;

/**
 * 电影评分服务接口
 */
public interface MovieScoreService {
    
    /**
     * 添加或更新评分
     * @param movieScore 评分信息
     * @return 是否成功
     */
    boolean addScore(MovieScore movieScore);
    
    /**
     * 删除评分
     * @param id 评分ID
     * @return 是否成功
     */
    boolean deleteScore(Long id);
    
    /**
     * 根据用户和电影查询评分
     * @param userId 用户ID
     * @param movieId 电影ID
     * @return 评分信息
     */
    MovieScore findByUserAndMovie(Long userId, Long movieId);
    
    /**
     * 获取电影平均评分
     * @param movieId 电影ID
     * @return 平均评分
     */
    Double getAverageScore(Long movieId);
}