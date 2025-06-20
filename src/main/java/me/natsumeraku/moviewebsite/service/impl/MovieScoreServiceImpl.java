package me.natsumeraku.moviewebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import me.natsumeraku.moviewebsite.entity.MovieScore;
import me.natsumeraku.moviewebsite.mapper.MovieScoreMapper;
import me.natsumeraku.moviewebsite.service.MovieScoreService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 电影评分服务实现类
 */
@Service
public class MovieScoreServiceImpl implements MovieScoreService {
    
    @Resource
    private MovieScoreMapper movieScoreMapper;
    
    @Override
    public boolean addScore(MovieScore movieScore) {
        // 检查用户是否已经对该电影评分
        MovieScore existingScore = findByUserAndMovie(movieScore.getUserId(), movieScore.getMovieId());
        if (existingScore != null) {
            // 如果已存在，则更新评分
            existingScore.setScore(movieScore.getScore());
            existingScore.setComment(movieScore.getComment());
            existingScore.setUpdateTime(LocalDateTime.now());
            return movieScoreMapper.updateById(existingScore) > 0;
        } else {
            // 如果不存在，则新增评分
            movieScore.setCreateTime(LocalDateTime.now());
            movieScore.setUpdateTime(LocalDateTime.now());
            return movieScoreMapper.insert(movieScore) > 0;
        }
    }
    
    @Override
    public boolean deleteScore(Long id) {
        return movieScoreMapper.deleteById(id) > 0;
    }
    
    @Override
    public MovieScore findByUserAndMovie(Long userId, Long movieId) {
        QueryWrapper<MovieScore> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .eq("movie_id", movieId);
        return movieScoreMapper.selectOne(queryWrapper);
    }
    
    @Override
    public Double getAverageScore(Long movieId) {
        return movieScoreMapper.selectAverageScoreByMovieId(movieId);
    }
}