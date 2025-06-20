package me.natsumeraku.moviewebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import me.natsumeraku.moviewebsite.entity.MoviePlayLog;
import me.natsumeraku.moviewebsite.mapper.MoviePlayLogMapper;
import me.natsumeraku.moviewebsite.service.MoviePlayLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 电影播放记录服务实现类
 */
@Service
public class MoviePlayLogServiceImpl implements MoviePlayLogService {
    
    @Resource
    private MoviePlayLogMapper moviePlayLogMapper;
    
    @Override
    public boolean addPlayLog(MoviePlayLog playLog) {
        playLog.setPlayTime(LocalDateTime.now());
        return moviePlayLogMapper.insert(playLog) > 0;
    }
    
    @Override
    public boolean updatePlayLog(MoviePlayLog playLog) {
        return moviePlayLogMapper.updateById(playLog) > 0;
    }
    
    @Override
    public boolean deletePlayLog(Long id) {
        return moviePlayLogMapper.deleteById(id) > 0;
    }
    
    @Override
    public MoviePlayLog findById(Long id) {
        return moviePlayLogMapper.selectById(id);
    }
    
    @Override
    public MoviePlayLog findLatestByUserAndMovie(Long userId, Long movieId) {
        QueryWrapper<MoviePlayLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .eq("movie_id", movieId)
                   .orderByDesc("play_time")
                   .last("LIMIT 1");
        return moviePlayLogMapper.selectOne(queryWrapper);
    }
    
    @Override
    public IPage<MoviePlayLog> getWatchHistory(Page<MoviePlayLog> page, Long userId) {
        return moviePlayLogMapper.selectWatchHistoryByUserId(page, userId);
    }
    
    @Override
    public List<MoviePlayLog> getRecentWatchHistory(Long userId, int limit) {
        return moviePlayLogMapper.selectRecentWatchHistoryByUserId(userId, limit);
    }
    
    @Override
    public long getPlayCount(Long movieId) {
        QueryWrapper<MoviePlayLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("movie_id", movieId);
        return moviePlayLogMapper.selectCount(queryWrapper);
    }
    
    @Override
    public long getTotalWatchDuration(Long userId) {
        return moviePlayLogMapper.selectTotalWatchDurationByUserId(userId);
    }
    
    @Override
    public long getWatchedMovieCount(Long userId) {
        return moviePlayLogMapper.selectWatchedMovieCountByUserId(userId);
    }
    
    @Override
    public boolean recordPlayProgress(Long userId, Long movieId, Integer playDuration, Integer playProgress) {
        // 查找是否已有播放记录
        MoviePlayLog existingLog = findLatestByUserAndMovie(userId, movieId);
        
        if (existingLog != null) {
            // 更新现有记录
            existingLog.setPlayDuration(playDuration);
            existingLog.setPlayProgress(playProgress);
            existingLog.setPlayTime(LocalDateTime.now());
            return updatePlayLog(existingLog);
        } else {
            // 创建新记录
            MoviePlayLog newLog = new MoviePlayLog();
            newLog.setUserId(userId);
            newLog.setMovieId(movieId);
            newLog.setPlayDuration(playDuration);
            newLog.setPlayProgress(playProgress);
            return addPlayLog(newLog);
        }
    }
    
    @Override
    public List<MoviePlayLog> getContinueWatchingList(Long userId, int limit) {
        return moviePlayLogMapper.selectContinueWatchingByUserId(userId, limit);
    }
    
    @Override
    public boolean clearWatchHistory(Long userId) {
        QueryWrapper<MoviePlayLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return moviePlayLogMapper.delete(queryWrapper) >= 0;
    }
    
    @Override
    public boolean deleteWatchRecord(Long userId, Long movieId) {
        QueryWrapper<MoviePlayLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .eq("movie_id", movieId);
        return moviePlayLogMapper.delete(queryWrapper) > 0;
    }
}