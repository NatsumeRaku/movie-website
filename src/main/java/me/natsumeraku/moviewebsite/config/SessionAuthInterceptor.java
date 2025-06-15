package me.natsumeraku.moviewebsite.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        
        if (isExcludedPath(requestURI)) {
            return true;
        }
        
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            return true;
        }
        
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":401,\"message\":\"请先登录\",\"success\":false}");
        return false;
    }
    
    private boolean isExcludedPath(String path) {
        return path.startsWith("/api/auth/") ||
               path.startsWith("/static/") ||
               path.startsWith("/css/") ||
               path.startsWith("/js/") ||
               path.startsWith("/images/") ||
               path.equals("/") ||
               path.equals("/index.html") ||
               path.startsWith("/api/movies/list") ||
               path.startsWith("/api/movies/search") ||
               path.startsWith("/api/movies/hot") ||
               path.startsWith("/api/movies/latest") ||
               path.startsWith("/api/movies/recommended");
    }
}