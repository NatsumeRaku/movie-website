package me.natsumeraku.moviewebsite.controller;

import jakarta.servlet.http.HttpSession;
import me.natsumeraku.moviewebsite.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {
    
    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "index";
    }
    
    @GetMapping("/userLogin")
    public String loginPage() {
        return "login/login";
    }
    
    @GetMapping("/detail/common/{id}")
    public String commonDetail() {
        return "detail/common/1";
    }
    
    @GetMapping("/detail/vip/{id}")
    public String vipDetail() {
        return "detail/vip/1";
    }
    
    @GetMapping("/ranking")
    public String ranking() {
        return "ranking";
    }
    
    /**
     * 演员详情页面
     */
    @GetMapping("/actor/{actorId}")
    public String actorDetail(@PathVariable Long actorId, Model model) {
        model.addAttribute("actorId", actorId);
        return "creator/actor-detail";
    }
    
    /**
     * 导演详情页面
     */
    @GetMapping("/director/{directorId}")
    public String directorDetail(@PathVariable Long directorId, Model model) {
        model.addAttribute("directorId", directorId);
        return "creator/director-detail";
    }
}