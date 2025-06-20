package me.natsumeraku.moviewebsite.controller;

import jakarta.annotation.Resource;
import me.natsumeraku.moviewebsite.common.Result;
import me.natsumeraku.moviewebsite.entity.Movie;
import me.natsumeraku.moviewebsite.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 电影排行榜控制器
 */
@RestController
@RequestMapping("/api/ranking")
@CrossOrigin
public class MovieRankingController {
    
    @Resource
    private MovieService movieService;
    
    /**
     * 获取电影排行榜
     * @param type 排行榜类型：week-本周, month-本月, all-全部, rating-好评
     * @param limit 数量限制，默认10
     * @return 排行榜数据
     */
    @GetMapping("/movies")
    public Result<List<Movie>> getMovieRanking(
            @RequestParam(defaultValue = "all") String type,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            List<Movie> movies = movieService.getMovieRanking(type, limit);
            return Result.success(movies);
        } catch (Exception e) {
            return Result.error("获取排行榜失败：" + e.getMessage());
        }
    }
    
    /**
     * 获取本周热门电影排行榜
     * @param limit 数量限制，默认10
     * @return 本周热门电影列表
     */
    @GetMapping("/weekly")
    public Result<List<Movie>> getWeeklyHotMovies(@RequestParam(defaultValue = "10") int limit) {
        try {
            List<Movie> movies = movieService.getWeeklyHotMovies(limit);
            return Result.success(movies);
        } catch (Exception e) {
            return Result.error("获取本周热门电影失败：" + e.getMessage());
        }
    }
    
    /**
     * 获取本月热门电影排行榜
     * @param limit 数量限制，默认10
     * @return 本月热门电影列表
     */
    @GetMapping("/monthly")
    public Result<List<Movie>> getMonthlyHotMovies(@RequestParam(defaultValue = "10") int limit) {
        try {
            List<Movie> movies = movieService.getMonthlyHotMovies(limit);
            return Result.success(movies);
        } catch (Exception e) {
            return Result.error("获取本月热门电影失败：" + e.getMessage());
        }
    }
    
    /**
     * 获取全部时间热门电影排行榜
     * @param limit 数量限制，默认10
     * @return 全部时间热门电影列表
     */
    @GetMapping("/all-time")
    public Result<List<Movie>> getAllTimeHotMovies(@RequestParam(defaultValue = "10") int limit) {
        try {
            List<Movie> movies = movieService.getAllTimeHotMovies(limit);
            return Result.success(movies);
        } catch (Exception e) {
            return Result.error("获取全部时间热门电影失败：" + e.getMessage());
        }
    }
    
    /**
     * 获取好评电影排行榜
     * @param limit 数量限制，默认10
     * @return 好评电影列表
     */
    @GetMapping("/top-rated")
    public Result<List<Movie>> getTopRatedMovies(@RequestParam(defaultValue = "10") int limit) {
        try {
            List<Movie> movies = movieService.getTopRatedMovies(limit);
            return Result.success(movies);
        } catch (Exception e) {
            return Result.error("获取好评电影失败：" + e.getMessage());
        }
    }
}