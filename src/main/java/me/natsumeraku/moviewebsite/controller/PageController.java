package me.natsumeraku.moviewebsite.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import me.natsumeraku.moviewebsite.entity.Actor;
import me.natsumeraku.moviewebsite.entity.Director;
import me.natsumeraku.moviewebsite.entity.Movie;
import me.natsumeraku.moviewebsite.entity.User;
import me.natsumeraku.moviewebsite.service.MovieService;
import me.natsumeraku.moviewebsite.util.SessionTokenUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PageController {
    
    @Resource
    private MovieService movieService;
    
    @Resource
    private SessionTokenUtil sessionTokenUtil;
    
    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        
        // 获取热门电影（前8部）
        List<Movie> hotMovies = movieService.getHotMovies(8);
        model.addAttribute("hotMovies", hotMovies);
        
        // 获取最新电影（前8部）
        List<Movie> latestMovies = movieService.getLatestMovies(8);
        model.addAttribute("latestMovies", latestMovies);
        
        // 获取高分电影（前8部）
        List<Movie> topRatedMovies = movieService.getTopRatedMovies(8);
        model.addAttribute("topRatedMovies", topRatedMovies);
        
        // 为所有电影添加主创信息
        Map<Long, List<Director>> movieDirectors = new HashMap<>();
        Map<Long, List<Actor>> movieActors = new HashMap<>();
        
        for (Movie movie : hotMovies) {
            movieDirectors.put(movie.getId(), movieService.getDirectorsByMovieId(movie.getId()));
            movieActors.put(movie.getId(), movieService.getActorsByMovieId(movie.getId()));
        }
        for (Movie movie : latestMovies) {
            movieDirectors.put(movie.getId(), movieService.getDirectorsByMovieId(movie.getId()));
            movieActors.put(movie.getId(), movieService.getActorsByMovieId(movie.getId()));
        }
        for (Movie movie : topRatedMovies) {
            movieDirectors.put(movie.getId(), movieService.getDirectorsByMovieId(movie.getId()));
            movieActors.put(movie.getId(), movieService.getActorsByMovieId(movie.getId()));
        }
        
        model.addAttribute("movieDirectors", movieDirectors);
        model.addAttribute("movieActors", movieActors);
        
        return "index";
    }
    
    @GetMapping("/userLogin")
    public String loginPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "login/login";
    }
    
    @GetMapping("/login")
    public String login(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "login/login";
    }
    
    @GetMapping("/register")
    public String registerPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "login/register";
    }
    
    @GetMapping("/movie/{id}")
    public String movieDetail(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        Movie movie = movieService.findById(id);
        
        if (movie == null) {
            return "redirect:/";
        }
        
        // 获取电影的导演和演员信息
        List<Director> directors = movieService.getDirectorsByMovieId(id);
        List<Actor> actors = movieService.getActorsByMovieId(id);
        
        // 增加播放次数
        movieService.incrementPlayCount(id);
        
        model.addAttribute("movie", movie);
        model.addAttribute("directors", directors);
        model.addAttribute("actors", actors);
        model.addAttribute("user", user);
        
        // 根据电影类型和用户权限决定显示哪个页面
        if (movie.getVipFlag() == 1) {
            if (user != null && user.getRole() == 1) {
                return "detail/vip/1";
            } else {
                return "redirect:/payment/vip";
            }
        } else {
            return "detail/common/1";
        }
    }
    
    @GetMapping("/detail/common/{id}")
    public String commonDetail(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        Movie movie = movieService.findById(id);
        model.addAttribute("movie", movie);
        return "detail/common/1";
    }
    
    @GetMapping("/detail/vip/{id}")
    public String vipDetail(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        Movie movie = movieService.findById(id);
        model.addAttribute("movie", movie);
        return "detail/vip/1";
    }
    
    @GetMapping("/ranking")
    public String ranking(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "ranking";
    }
    
    @GetMapping("/movies")
    public String movies(
            @org.springframework.web.bind.annotation.RequestParam(required = false) String type,
            @org.springframework.web.bind.annotation.RequestParam(required = false) String region,
            @org.springframework.web.bind.annotation.RequestParam(required = false) String vipFlag,
            @org.springframework.web.bind.annotation.RequestParam(required = false) String sortBy,
            HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        
        // 转换vipFlag参数
        Integer vipFlagInt = null;
        if ("VIP".equals(vipFlag)) {
            vipFlagInt = 1;
        } else if ("免费".equals(vipFlag)) {
            vipFlagInt = 0;
        }
        
        // 设置默认排序方式
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "release_date";
        }
        
        // 使用新的筛选方法（移除keyword参数）
        List<Movie> movies = movieService.getMoviesWithFilters(type, region, vipFlagInt, sortBy, null);
        
        // 为每个电影添加主创信息
        Map<Long, List<Director>> movieDirectors = new HashMap<>();
        Map<Long, List<Actor>> movieActors = new HashMap<>();
        for (Movie movie : movies) {
            movieDirectors.put(movie.getId(), movieService.getDirectorsByMovieId(movie.getId()));
            movieActors.put(movie.getId(), movieService.getActorsByMovieId(movie.getId()));
        }
        
        // 添加筛选参数到模型
        model.addAttribute("movies", movies);
        model.addAttribute("movieDirectors", movieDirectors);
        model.addAttribute("movieActors", movieActors);
        model.addAttribute("type", type);
        model.addAttribute("region", region);
        model.addAttribute("vipFlag", vipFlag);
        model.addAttribute("sortBy", sortBy);
        
        return "movies";
    }
    
    /**
     * 演员详情页面
     */
    @GetMapping("/actor/{actorId}")
    public String actorDetail(@PathVariable Long actorId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("actorId", actorId);
        return "creator/actor-detail";
    }
    
    /**
     * 导演详情页面
     */
    @GetMapping("/director/{directorId}")
    public String directorDetail(@PathVariable Long directorId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("directorId", directorId);
        return "creator/director-detail";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        sessionTokenUtil.removeTokenBySession(session);
        session.invalidate();
        return "redirect:/";
    }
    
    /**
     * 数据报表页面
     */
    @GetMapping("/report")
    public String report(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "report/index";
    }
    
    /**
     * 搜索页面
     */
    @GetMapping("/search")
    public String search(@RequestParam(value = "keyword", required = false) String keyword,
                        HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("keyword", keyword);
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            List<Movie> movies = movieService.searchMoviesByKeyword(keyword.trim());
            
            // 为每个电影添加主创信息
            Map<Long, List<Director>> movieDirectors = new HashMap<>();
            Map<Long, List<Actor>> movieActors = new HashMap<>();
            for (Movie movie : movies) {
                movieDirectors.put(movie.getId(), movieService.getDirectorsByMovieId(movie.getId()));
                movieActors.put(movie.getId(), movieService.getActorsByMovieId(movie.getId()));
            }
            
            model.addAttribute("movies", movies);
            model.addAttribute("movieDirectors", movieDirectors);
            model.addAttribute("movieActors", movieActors);
        }
        
        return "search";
    }

}