package me.natsumeraku.moviewebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import me.natsumeraku.moviewebsite.dto.MovieRankingDTO;
import me.natsumeraku.moviewebsite.entity.Movie;
import me.natsumeraku.moviewebsite.mapper.MovieMapper;
import me.natsumeraku.moviewebsite.service.MovieService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

/**
 * 电影服务实现类
 */
@Service
@Primary
public class MovieServiceImpl implements MovieService {
    
    @Resource
    private MovieMapper movieMapper;
    
    @Override
    public boolean addMovie(Movie movie) {
        movie.setCreateTime(LocalDateTime.now());
        movie.setUpdateTime(LocalDateTime.now());
        movie.setPlayCount(0L); // 初始播放次数为0
        return movieMapper.insert(movie) > 0;
    }
    
    @Override
    public Movie findById(Long id) {
        return movieMapper.selectById(id);
    }
    
    @Override
    public boolean updateMovie(Movie movie) {
        movie.setUpdateTime(LocalDateTime.now());
        return movieMapper.updateById(movie) > 0;
    }
    
    @Override
    public boolean deleteMovie(Long id) {
        return movieMapper.deleteById(id) > 0;
    }
    
    @Override
    public IPage<Movie> getMovieList(Page<Movie> page) {
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        return movieMapper.selectPage(page, queryWrapper);
    }
    
    @Override
    public IPage<Movie> getMoviesByType(Page<Movie> page, String type) {
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type)
                   .orderByDesc("create_time");
        return movieMapper.selectPage(page, queryWrapper);
    }
    
    @Override
    public IPage<Movie> searchMovies(Page<Movie> page, String keyword) {
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", keyword)
                   .or()
                   .like("description", keyword)
                   .or()
                   .like("type", keyword)
                   .orderByDesc("create_time");
        return movieMapper.selectPage(page, queryWrapper);
    }
    
    @Override
    public List<Movie> getHotMovies(int limit) {
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("play_count")
                   .last("LIMIT " + limit);
        return movieMapper.selectList(queryWrapper);
    }
    
    @Override
    public List<Movie> getLatestMovies(int limit) {
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("release_date")
                   .last("LIMIT " + limit);
        return movieMapper.selectList(queryWrapper);
    }
    
    @Override
    public List<Movie> getRecommendedMovies(int limit) {
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("score")  // 修改为score
                   .last("LIMIT " + limit);
        return movieMapper.selectList(queryWrapper);
    }
    
    @Override
    public boolean incrementPlayCount(Long movieId) {
        Movie movie = movieMapper.selectById(movieId);
        if (movie != null) {
            movie.setPlayCount(movie.getPlayCount() + 1);
            movie.setUpdateTime(LocalDateTime.now());
            return movieMapper.updateById(movie) > 0;
        }
        return false;
    }
    
    @Override
    public long getTotalCount() {
        return movieMapper.selectCount(null);
    }
    
    @Override
    public List<Movie> getMoviesByDirector(Long directorId) {
        return movieMapper.selectMoviesByDirector(directorId);
    }
    
    @Override
    public List<Movie> getMoviesByActor(Long actorId) {
        return movieMapper.selectMoviesByActor(actorId);
    }
    
    @Override
    public List<Movie> getWeeklyHotMovies(int limit) {
        LocalDate weekAgo = LocalDate.now().minusDays(7);
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("release_date", weekAgo)
                   .orderByDesc("play_count")
                   .last("LIMIT " + limit);
        return movieMapper.selectList(queryWrapper);
    }
    
    @Override
    public List<Movie> getMonthlyHotMovies(int limit) {
        LocalDate monthAgo = LocalDate.now().minusMonths(1);
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("release_date", monthAgo)
                   .orderByDesc("play_count")
                   .last("LIMIT " + limit);
        return movieMapper.selectList(queryWrapper);
    }
    
    @Override
    public List<Movie> getAllTimeHotMovies(int limit) {
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("play_count")
                   .last("LIMIT " + limit);
        return movieMapper.selectList(queryWrapper);
    }
    
    @Override
    public List<Movie> getTopRatedMovies(int limit) {
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("score")
                   .last("LIMIT " + limit);
        return movieMapper.selectList(queryWrapper);
    }
    
    @Override
    public List<Movie> getMovieRanking(String rankType, int limit) {
        return switch (rankType.toLowerCase()) {
            case "week" -> getWeeklyHotMovies(limit);
            case "month" -> getMonthlyHotMovies(limit);
            case "all" -> getAllTimeHotMovies(limit);
            case "rating" -> getTopRatedMovies(limit);
            default -> getAllTimeHotMovies(limit);
        };
    }
    
    @Override
    public List<MovieRankingDTO> getMovieTypeDistribution() {
        return movieMapper.selectMovieTypeDistribution();
    }
    
    @Override
    public List<MovieRankingDTO> getMonthlyPlayTrend() {
        return movieMapper.selectMonthlyPlayTrend();
    }
    
    @Override
    public List<MovieRankingDTO> getMovieRegionDistribution() {
        return movieMapper.selectMovieRegionDistribution();
    }
    
    @Override
    public List<Movie> getAllMovies() {
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        return movieMapper.selectList(queryWrapper);
    }
    
    @Override
    public List<Movie> searchMoviesByKeyword(String keyword) {
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", keyword)
                   .or()
                   .like("director", keyword)
                   .or()
                   .like("actors", keyword)
                   .or()
                   .like("description", keyword);
        queryWrapper.orderByDesc("create_time");
        return movieMapper.selectList(queryWrapper);
    }
    
    @Override
    public List<Movie> getMoviesByType(String type) {
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type);
        queryWrapper.orderByDesc("create_time");
        return movieMapper.selectList(queryWrapper);
    }
}