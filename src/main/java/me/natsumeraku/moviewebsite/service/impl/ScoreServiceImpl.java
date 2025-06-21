package me.natsumeraku.moviewebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import me.natsumeraku.moviewebsite.entity.Score;
import me.natsumeraku.moviewebsite.mapper.ScoreMapper;
import me.natsumeraku.moviewebsite.service.ScoreService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 评分服务实现类
 */
@Service
public class ScoreServiceImpl implements ScoreService {
    
    @Resource
    private ScoreMapper scoreMapper;
    
    @Override
    public boolean addScore(Score score) {
        // 检查用户是否已经对该电影评分
        Score existingScore = findByUserAndMovie(score.getUserId(), score.getMovieId());
        if (existingScore != null) {
            // 更新现有评分
            existingScore.setScore(score.getScore());
            existingScore.setComment(score.getComment());
            existingScore.setUpdateTime(LocalDateTime.now());
            return scoreMapper.updateById(existingScore) > 0;
        } else {
            // 添加新评分
            score.setCreateTime(LocalDateTime.now());
            score.setUpdateTime(LocalDateTime.now());
            return scoreMapper.insert(score) > 0;
        }
    }
    
    @Override
    public boolean deleteScore(Long id) {
        return scoreMapper.deleteById(id) > 0;
    }
    
    @Override
    public Score findByUserAndMovie(Long userId, Long movieId) {
        QueryWrapper<Score> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .eq("movie_id", movieId);
        return scoreMapper.selectOne(queryWrapper);
    }
    
    @Override
    public Double getAverageScore(Long movieId) {
        return scoreMapper.selectAverageScoreByMovieId(movieId);
    }
    
    @Override
    public java.util.List<Score> findByMovieId(Long movieId) {
        QueryWrapper<Score> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("movie_id", movieId)
                   .orderByDesc("create_time");
        return scoreMapper.selectList(queryWrapper);
    }
}