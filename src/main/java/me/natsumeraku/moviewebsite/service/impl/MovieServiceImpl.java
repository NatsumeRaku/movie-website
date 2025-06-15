package me.natsumeraku.moviewebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.natsumeraku.moviewebsite.entity.Movie;
import me.natsumeraku.moviewebsite.mapper.MovieMapper;
import me.natsumeraku.moviewebsite.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 电影服务实现类
 */
@Service
public class MovieServiceImpl implements MovieService {
    
    @Autowired
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
}