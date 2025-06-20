package me.natsumeraku.moviewebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.natsumeraku.moviewebsite.entity.MoviePlayLog;

import java.util.List;

/**
 * 电影播放记录服务接口
 */
public interface MoviePlayLogService {
    
    /**
     * 添加播放记录
     * @param playLog 播放记录
     * @return 添加结果
     */
    boolean addPlayLog(MoviePlayLog playLog);
    
    /**
     * 更新播放记录
     * @param playLog 播放记录
     * @return 更新结果
     */
    boolean updatePlayLog(MoviePlayLog playLog);
    
    /**
     * 删除播放记录
     * @param id 记录ID
     * @return 删除结果
     */
    boolean deletePlayLog(Long id);
    
    /**
     * 根据ID查询播放记录
     * @param id 记录ID
     * @return 播放记录
     */
    MoviePlayLog findById(Long id);
    
    /**
     * 查询用户对某电影的最新播放记录
     * @param userId 用户ID
     * @param movieId 电影ID
     * @return 播放记录
     */
    MoviePlayLog findLatestByUserAndMovie(Long userId, Long movieId);
    
    /**
     * 分页查询用户的观看历史
     * @param page 分页参数
     * @param userId 用户ID
     * @return 分页结果
     */
    IPage<MoviePlayLog> getWatchHistory(Page<MoviePlayLog> page, Long userId);
    
    /**
     * 获取用户最近观看的电影
     * @param userId 用户ID
     * @param limit 数量限制
     * @return 最近观看记录列表
     */
    List<MoviePlayLog> getRecentWatchHistory(Long userId, int limit);
    
    /**
     * 获取电影的播放统计
     * @param movieId 电影ID
     * @return 播放次数
     */
    long getPlayCount(Long movieId);
    
    /**
     * 获取用户的观看总时长
     * @param userId 用户ID
     * @return 总观看时长（秒）
     */
    long getTotalWatchDuration(Long userId);
    
    /**
     * 获取用户观看的电影总数
     * @param userId 用户ID
     * @return 观看电影总数
     */
    long getWatchedMovieCount(Long userId);
    
    /**
     * 记录或更新播放进度
     * @param userId 用户ID
     * @param movieId 电影ID
     * @param playDuration 播放时长
     * @param playProgress 播放进度
     * @return 操作结果
     */
    boolean recordPlayProgress(Long userId, Long movieId, Integer playDuration, Integer playProgress);
    
    /**
     * 获取用户继续观看的电影列表（未看完的电影）
     * @param userId 用户ID
     * @param limit 数量限制
     * @return 继续观看列表
     */
    List<MoviePlayLog> getContinueWatchingList(Long userId, int limit);
    
    /**
     * 清空用户的观看历史
     * @param userId 用户ID
     * @return 清空结果
     */
    boolean clearWatchHistory(Long userId);
    
    /**
     * 删除用户的特定观看记录
     * @param userId 用户ID
     * @param movieId 电影ID
     * @return 删除结果
     */
    boolean deleteWatchRecord(Long userId, Long movieId);
}