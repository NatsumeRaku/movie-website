package me.natsumeraku.moviewebsite.controller;

import jakarta.servlet.http.HttpSession;
import me.natsumeraku.moviewebsite.common.Result;
import me.natsumeraku.moviewebsite.entity.MovieScore;
import me.natsumeraku.moviewebsite.entity.User;
import me.natsumeraku.moviewebsite.service.MovieScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 电影评分控制器
 */
@RestController
@RequestMapping("/api/score")
@CrossOrigin(origins = "*")
public class MovieScoreController {
    
    @Autowired
    private MovieScoreService movieScoreService;
    
    /**
     * 添加或更新评分
     */
    @PostMapping("/add")
    public Result<String> addScore(@RequestBody MovieScore movieScore, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return Result.unauthorized("请先登录");
        }
        
        movieScore.setUserId(currentUser.getId());
        
        if (movieScore.getScore() < 1 || movieScore.getScore() > 10) {
            return Result.badRequest("评分必须在1-10之间");
        }
        
        boolean success = movieScoreService.addScore(movieScore);
        if (success) {
            return Result.success("评分成功");
        } else {
            return Result.error("评分失败");
        }
    }
    
    /**
     * 删除评分
     */
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteScore(@PathVariable Long id, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return Result.unauthorized("请先登录");
        }
        
        boolean success = movieScoreService.deleteScore(id);
        if (success) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }
    
    /**
     * 获取用户对电影的评分
     */
    @GetMapping("/user/{movieId}")
    public Result<MovieScore> getUserScore(@PathVariable Long movieId, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return Result.unauthorized("请先登录");
        }
        
        MovieScore score = movieScoreService.findByUserAndMovie(currentUser.getId(), movieId);
        return Result.success(score);
    }
    
    /**
     * 获取电影平均评分
     */
    @GetMapping("/average/{movieId}")
    public ResponseEntity<Double> getAverageScore(@PathVariable Long movieId) {
        Double averageScore = movieScoreService.getAverageScore(movieId);
        return ResponseEntity.ok(averageScore);
    }
}