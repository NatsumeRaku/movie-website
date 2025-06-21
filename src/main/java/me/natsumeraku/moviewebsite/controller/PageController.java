package me.natsumeraku.moviewebsite.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import me.natsumeraku.moviewebsite.entity.Movie;
import me.natsumeraku.moviewebsite.entity.User;
import me.natsumeraku.moviewebsite.service.MovieService;
import me.natsumeraku.moviewebsite.util.SessionTokenUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
        
        // 加载首页电影数据
        List<Movie> hotMovies = movieService.getHotMovies(8);
        List<Movie> latestMovies = movieService.getLatestMovies(8);
        List<Movie> recommendedMovies = movieService.getRecommendedMovies(8);
        
        model.addAttribute("hotMovies", hotMovies);
        model.addAttribute("latestMovies", latestMovies);
        model.addAttribute("recommendedMovies", recommendedMovies);
        
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
        
        model.addAttribute("movie", movie);
        model.addAttribute("user", user);
        
        // 根据电影类型和用户权限决定显示哪个页面
        if (movie.getVipFlag() == 1) {
            if (user != null && user.getRole() == 1) {
                return "detail/vip/1";
            } else {
                // 非VIP用户访问VIP电影，重定向到VIP购买页面
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
            @org.springframework.web.bind.annotation.RequestParam(required = false) String keyword,
            HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        
        List<Movie> movies;
        if (keyword != null && !keyword.trim().isEmpty()) {
            movies = movieService.searchMoviesByKeyword(keyword.trim());
            model.addAttribute("keyword", keyword);
        } else if (type != null && !type.trim().isEmpty()) {
            movies = movieService.getMoviesByType(type.trim());
            model.addAttribute("type", type);
        } else {
            movies = movieService.getAllMovies();
        }
        
        model.addAttribute("movies", movies);
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
        return "report";
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
            model.addAttribute("movies", movies);
        }
        
        return "search";
    }

}