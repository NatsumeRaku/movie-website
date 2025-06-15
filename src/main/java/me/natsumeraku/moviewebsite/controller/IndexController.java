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

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class IndexController {
    
    @Autowired
    private MovieService movieService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/index")
    public Result<Map<String, Object>> getIndexData() {
        Map<String, Object> data = new HashMap<>();
        
        List<Movie> hotMovies = movieService.getHotMovies(8);
        data.put("hotMovies", hotMovies);
        
        List<Movie> latestMovies = movieService.getLatestMovies(8);
        data.put("latestMovies", latestMovies);
        
        List<Movie> recommendedMovies = movieService.getRecommendedMovies(8);
        data.put("recommendedMovies", recommendedMovies);
        
        long totalMovies = movieService.getTotalCount();
        data.put("totalMovies", totalMovies);
        
        return Result.success(data);
    }
    
    @GetMapping("/health")
    public Result<String> healthCheck() {
        return Result.success("OK");
    }
    
    @GetMapping("/info")
    public Result<Map<String, Object>> getSystemInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("name", "电影网站系统");
        info.put("version", "1.0.0");
        info.put("description", "基于Spring Boot + MyBatis Plus的电影网站");
        info.put("author", "Natsume");
        
        return Result.success(info);
    }
}