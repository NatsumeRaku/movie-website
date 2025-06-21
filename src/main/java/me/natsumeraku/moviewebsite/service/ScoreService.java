package me.natsumeraku.moviewebsite.service;

import me.natsumeraku.moviewebsite.entity.Score;

/**
 * 评分服务接口
 */
public interface ScoreService {
    
    /**
     * 添加或更新评分
     * @param score 评分信息
     * @return 是否成功
     */
    boolean addScore(Score score);
    
    /**
     * 删除评分
     * @param id 评分ID
     * @return 是否成功
     */
    boolean deleteScore(Long id);
    
    /**
     * 根据用户ID和电影ID查找评分
     * @param userId 用户ID
     * @param movieId 电影ID
     * @return 评分信息
     */
    Score findByUserAndMovie(Long userId, Long movieId);
    
    /**
     * 获取电影平均评分
     * @param movieId 电影ID
     * @return 平均评分
     */
    Double getAverageScore(Long movieId);
    
    /**
     * 根据电影ID查找所有评分
     * @param movieId 电影ID
     * @return 评分列表
     */
    java.util.List<Score> findByMovieId(Long movieId);
}