package me.natsumeraku.moviewebsite.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import me.natsumeraku.moviewebsite.common.Result;
import me.natsumeraku.moviewebsite.entity.User;
import me.natsumeraku.moviewebsite.entity.Score;
import me.natsumeraku.moviewebsite.service.ScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 评分控制器
 */
@RestController
@RequestMapping("/api/score")
@CrossOrigin(origins = "*")
public class ScoreController {
    
    @Resource
    private ScoreService scoreService;
    
    /**
     * 添加或更新评分
     */
    @PostMapping("/add")
    public Result<String> addScore(@RequestBody Score score, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return Result.error("请先登录");
        }
        
        score.setUserId(currentUser.getId());
        
        if (score.getScore() < 1 || score.getScore() > 10) {
            return Result.error("评分必须在1-10之间");
        }
        
        boolean success = scoreService.addScore(score);
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
            return Result.error("请先登录");
        }
        
        boolean success = scoreService.deleteScore(id);
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
    public Result<Score> getUserScore(@PathVariable Long movieId, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return Result.error("请先登录");
        }
        
        Score score = scoreService.findByUserAndMovie(currentUser.getId(), movieId);
        return Result.success(score);
    }
    
    /**
     * 获取电影平均评分
     */
    @GetMapping("/average/{movieId}")
    public ResponseEntity<Double> getAverageScore(@PathVariable Long movieId) {
        Double averageScore = scoreService.getAverageScore(movieId);
        return ResponseEntity.ok(averageScore);
    }
}