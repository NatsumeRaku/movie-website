package me.natsumeraku.moviewebsite.util;

import jakarta.servlet.http.HttpSession;
import me.natsumeraku.moviewebsite.entity.User;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class SessionTokenUtil {
    
    private static final String SESSION_TOKEN_KEY = "sessionToken";
    private static final String USER_KEY = "user";
    private static final long SESSION_TIMEOUT = 30 * 60 * 1000; // 30分钟
    
    private final ConcurrentHashMap<String, SessionInfo> tokenStore = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    public SessionTokenUtil() {
        scheduler.scheduleAtFixedRate(this::cleanExpiredTokens, 5, 5, TimeUnit.MINUTES);
    }
    
    public String generateToken(HttpSession session, User user) {
        String token = UUID.randomUUID().toString().replace("-", "");
        long expireTime = System.currentTimeMillis() + SESSION_TIMEOUT;
        
        SessionInfo sessionInfo = new SessionInfo(user, expireTime, session.getId());
        tokenStore.put(token, sessionInfo);
        
        session.setAttribute(SESSION_TOKEN_KEY, token);
        session.setAttribute(USER_KEY, user);
        session.setMaxInactiveInterval((int) (SESSION_TIMEOUT / 1000));
        
        return token;
    }
    
    public User getUserByToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            return null;
        }
        
        SessionInfo sessionInfo = tokenStore.get(token);
        if (sessionInfo == null) {
            return null;
        }
        
        if (System.currentTimeMillis() > sessionInfo.getExpireTime()) {
            tokenStore.remove(token);
            return null;
        }
        
        sessionInfo.setExpireTime(System.currentTimeMillis() + SESSION_TIMEOUT);
        return sessionInfo.getUser();
    }
    
    public boolean isValidToken(String token) {
        return getUserByToken(token) != null;
    }
    
    public void removeToken(String token) {
        if (token != null) {
            tokenStore.remove(token);
        }
    }
    
    public void removeTokenBySession(HttpSession session) {
        String token = (String) session.getAttribute(SESSION_TOKEN_KEY);
        if (token != null) {
            tokenStore.remove(token);
        }
        session.removeAttribute(SESSION_TOKEN_KEY);
        session.removeAttribute(USER_KEY);
    }
    
    private void cleanExpiredTokens() {
        long currentTime = System.currentTimeMillis();
        tokenStore.entrySet().removeIf(entry -> 
            currentTime > entry.getValue().getExpireTime());
    }
    
    private static class SessionInfo {
        private final User user;
        private long expireTime;
        private final String sessionId;
        
        public SessionInfo(User user, long expireTime, String sessionId) {
            this.user = user;
            this.expireTime = expireTime;
            this.sessionId = sessionId;
        }
        
        public User getUser() {
            return user;
        }
        
        public long getExpireTime() {
            return expireTime;
        }
        
        public void setExpireTime(long expireTime) {
            this.expireTime = expireTime;
        }
        
        public String getSessionId() {
            return sessionId;
        }
    }
}