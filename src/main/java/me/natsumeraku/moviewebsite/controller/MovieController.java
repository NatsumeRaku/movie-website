package me.natsumeraku.moviewebsite.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import me.natsumeraku.moviewebsite.common.Result;
import me.natsumeraku.moviewebsite.entity.Movie;
import me.natsumeraku.moviewebsite.entity.User;
import me.natsumeraku.moviewebsite.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
@CrossOrigin(origins = "*")
public class MovieController {

    @Resource
    private MovieService movieService;
    
    @GetMapping("/list")
    public Result<IPage<Movie>> getMovieList(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String keyword) {
        
        Page<Movie> page = new Page<>(current, size);
        IPage<Movie> moviePage;
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            moviePage = movieService.searchMovies(page, keyword.trim());
        } else if (type != null && !type.trim().isEmpty()) {
            moviePage = movieService.getMoviesByType(page, type.trim());
        } else {
            moviePage = movieService.getMovieList(page);
        }
        
        return Result.success(moviePage);
    }
    
    @GetMapping("/{id}")
    public Result<Movie> getMovieById(@PathVariable Long id) {
        Movie movie = movieService.findById(id);
        if (movie != null) {
            return Result.success(movie);
        } else {
            return Result.notFound("电影不存在");
        }
    }
    
    @GetMapping("/hot")
    public Result<List<Movie>> getHotMovies(@RequestParam(defaultValue = "10") int limit) {
        List<Movie> movies = movieService.getHotMovies(limit);
        return Result.success(movies);
    }
    
    @GetMapping("/latest")
    public Result<List<Movie>> getLatestMovies(@RequestParam(defaultValue = "10") int limit) {
        List<Movie> movies = movieService.getLatestMovies(limit);
        return Result.success(movies);
    }
    
    @GetMapping("/recommended")
    public Result<List<Movie>> getRecommendedMovies(@RequestParam(defaultValue = "10") int limit) {
        List<Movie> movies = movieService.getRecommendedMovies(limit);
        return Result.success(movies);
    }
    
    @PostMapping("/play/{id}")
    public Result<Movie> playMovie(@PathVariable Long id, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return Result.unauthorized("请先登录");
        }
        
        Movie movie = movieService.findById(id);
        if (movie == null) {
            return Result.notFound("电影不存在");
        }
        
        if (movie.getVipFlag() == 1 && currentUser.getRole() == 0) {
            return Result.forbidden("该电影需要VIP权限");
        }
        
        boolean success = movieService.incrementPlayCount(id);
        if (success) {
            return Result.success("播放成功", movie);
        } else {
            return Result.error("播放失败");
        }
    }
    
    @PostMapping("/add")
    public Result<String> addMovie(@RequestBody Movie movie, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return Result.unauthorized("请先登录");
        }
        
        if (movie.getTitle() == null || movie.getTitle().trim().isEmpty()) {
            return Result.badRequest("电影标题不能为空");
        }
        
        boolean success = movieService.addMovie(movie);
        if (success) {
            return Result.success("添加成功");
        } else {
            return Result.error("添加失败");
        }
    }
    
    @PutMapping("/update")
    public Result<String> updateMovie(@RequestBody Movie movie, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return Result.unauthorized("请先登录");
        }
        
        boolean success = movieService.updateMovie(movie);
        if (success) {
            return Result.success("更新成功");
        } else {
            return Result.error("更新失败");
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteMovie(@PathVariable Long id, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return Result.unauthorized("请先登录");
        }
        
        boolean success = movieService.deleteMovie(id);
        if (success) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }
}