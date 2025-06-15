package me.natsumeraku.moviewebsite.controller;

import me.natsumeraku.moviewebsite.common.Result;
import me.natsumeraku.moviewebsite.entity.Movie;
import me.natsumeraku.moviewebsite.service.MovieService;
import me.natsumeraku.moviewebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页控制器
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class IndexController {
    
    @Autowired
    private MovieService movieService;
    
    @Autowired
    private UserService userService;
    
    /**
     * 首页数据
     */
    @GetMapping("/index")
    public Result<Map<String, Object>> getIndexData() {
        try {
            Map<String, Object> data = new HashMap<>();
            
            // 获取热门电影（前8部）
            List<Movie> hotMovies = movieService.getHotMovies(8);
            data.put("hotMovies", hotMovies);
            
            // 获取最新电影（前8部）
            List<Movie> latestMovies = movieService.getLatestMovies(8);
            data.put("latestMovies", latestMovies);
            
            // 获取推荐电影（前8部）
            List<Movie> recommendedMovies = movieService.getRecommendedMovies(8);
            data.put("recommendedMovies", recommendedMovies);
            
            // 获取统计数据
            long totalMovies = movieService.getTotalCount();
            data.put("totalMovies", totalMovies);
            
            return Result.success("获取首页数据成功", data);
        } catch (Exception e) {
            return Result.error("获取首页数据失败：" + e.getMessage());
        }
    }
    
    /**
     * 系统状态检查
     */
    @GetMapping("/health")
    public Result<String> healthCheck() {
        return Result.success("系统运行正常", "OK");
    }
    
    /**
     * 获取系统信息
     */
    @GetMapping("/info")
    public Result<Map<String, Object>> getSystemInfo() {
        try {
            Map<String, Object> info = new HashMap<>();
            info.put("name", "电影网站系统");
            info.put("version", "1.0.0");
            info.put("description", "基于Spring Boot + MyBatis Plus的电影网站");
            info.put("author", "Natsume");
            
            return Result.success("获取系统信息成功", info);
        } catch (Exception e) {
            return Result.error("获取系统信息失败：" + e.getMessage());
        }
    }
}