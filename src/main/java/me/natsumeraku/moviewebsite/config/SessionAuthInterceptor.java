package me.natsumeraku.moviewebsite.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import me.natsumeraku.moviewebsite.entity.User;
import me.natsumeraku.moviewebsite.util.SessionTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionAuthInterceptor implements HandlerInterceptor {
    
    @Autowired
    private SessionTokenUtil sessionTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        
        if (isExcludedPath(requestURI)) {
            return true;
        }
        
        String token = request.getHeader("Authorization");
        if (token == null || token.trim().isEmpty()) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                token = (String) session.getAttribute("sessionToken");
            }
        } else if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        User user = sessionTokenUtil.getUserByToken(token);
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"请先登录\",\"success\":false}");
            return false;
        }
        
        request.setAttribute("currentUser", user);
        return true;
    }
    
    private boolean isExcludedPath(String path) {
        return path.startsWith("/api/auth/") ||
               path.startsWith("/static/") ||
               path.startsWith("/css/") ||
               path.startsWith("/js/") ||
               path.startsWith("/images/") ||
               path.equals("/") ||
               path.equals("/index.html") ||
               path.startsWith("/api/movie/list") ||
               path.startsWith("/api/movie/hot") ||
               path.startsWith("/api/movie/latest") ||
               path.startsWith("/api/movie/recommended") ||
               path.startsWith("/api/index") ||
               path.startsWith("/api/health") ||
               path.startsWith("/api/info");
    }
}