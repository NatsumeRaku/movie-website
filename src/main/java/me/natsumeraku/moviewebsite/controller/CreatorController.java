package me.natsumeraku.moviewebsite.controller;

import jakarta.annotation.Resource;
import me.natsumeraku.moviewebsite.entity.Actor;
import me.natsumeraku.moviewebsite.entity.Director;
import me.natsumeraku.moviewebsite.entity.Movie;
import me.natsumeraku.moviewebsite.service.ActorService;
import me.natsumeraku.moviewebsite.service.DirectorService;
import me.natsumeraku.moviewebsite.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主创人员控制器
 */
@RestController
@RequestMapping("/api/creator")
public class CreatorController {
    
    @Resource
    private ActorService actorService;
    
    @Resource
    private DirectorService directorService;
    
    @Resource
    private MovieService movieService;
    
    /**
     * 获取演员详情信息
     * @param actorId 演员ID
     * @return 演员详情和统计信息
     */
    @GetMapping("/actor/{actorId}")
    public ResponseEntity<Map<String, Object>> getActorDetail(@PathVariable Long actorId) {
        Actor actor = actorService.findById(actorId);
        if (actor == null) {
            return ResponseEntity.notFound().build();
        }
        
        // 获取演员参演的电影列表
        List<Movie> movies = movieService.getMoviesByActor(actorId);
        
        // 获取统计信息
        int movieCount = actorService.getMovieCountByActor(actorId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("actor", actor);
        result.put("movies", movies);
        result.put("movieCount", movieCount);
        
        return ResponseEntity.ok(result);
    }
    
    /**
     * 获取导演详情信息
     * @param directorId 导演ID
     * @return 导演详情和统计信息
     */
    @GetMapping("/director/{directorId}")
    public ResponseEntity<Map<String, Object>> getDirectorDetail(@PathVariable Long directorId) {
        Director director = directorService.findById(directorId);
        if (director == null) {
            return ResponseEntity.notFound().build();
        }
        
        // 获取导演执导的电影列表
        List<Movie> movies = movieService.getMoviesByDirector(directorId);
        
        // 获取统计信息
        int movieCount = directorService.getMovieCountByDirector(directorId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("director", director);
        result.put("movies", movies);
        result.put("movieCount", movieCount);
        
        return ResponseEntity.ok(result);
    }
    
    /**
     * 搜索演员
     * @param name 演员姓名
     * @return 演员列表
     */
    @GetMapping("/actor/search")
    public ResponseEntity<List<Actor>> searchActors(@RequestParam String name) {
        List<Actor> actors = actorService.searchByName(name);
        return ResponseEntity.ok(actors);
    }
    
    /**
     * 搜索导演
     * @param name 导演姓名
     * @return 导演列表
     */
    @GetMapping("/director/search")
    public ResponseEntity<List<Director>> searchDirectors(@RequestParam String name) {
        List<Director> directors = directorService.searchByName(name);
        return ResponseEntity.ok(directors);
    }
    
    /**
     * 获取热门演员
     * @param limit 数量限制，默认10
     * @return 热门演员列表
     */
    @GetMapping("/actor/popular")
    public ResponseEntity<List<Actor>> getPopularActors(@RequestParam(defaultValue = "10") int limit) {
        List<Actor> actors = actorService.getPopularActors(limit);
        return ResponseEntity.ok(actors);
    }
    
    /**
     * 获取热门导演
     * @param limit 数量限制，默认10
     * @return 热门导演列表
     */
    @GetMapping("/director/popular")
    public ResponseEntity<List<Director>> getPopularDirectors(@RequestParam(defaultValue = "10") int limit) {
        List<Director> directors = directorService.getPopularDirectors(limit);
        return ResponseEntity.ok(directors);
    }
}