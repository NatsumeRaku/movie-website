package me.natsumeraku.moviewebsite.config;

import me.natsumeraku.moviewebsite.service.impl.UserDetailsServiceImpl;
import me.natsumeraku.moviewebsite.service.UserService;
import me.natsumeraku.moviewebsite.entity.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Resource
    private UserDetailsServiceImpl userDetailsService;
    
    @Resource
    private BCryptPasswordEncoder passwordEncoder;
    
    @Resource
    private UserService userService;
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
                                              Authentication authentication) throws IOException {
                // 获取用户名
                String username = authentication.getName();
                // 从数据库获取完整的用户信息
                User user = userService.findByUsername(username);
                if (user != null) {
                    // 将用户信息存储到session中
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                }
                // 重定向到首页
                response.sendRedirect("/");
            }
        };
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/").permitAll() //放行根页面，不用认证可以直接访问。
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/userLogin").permitAll()
                        .requestMatchers("/register").permitAll() //注册页面允许所有用户访问
                        .requestMatchers("/api/auth/**").permitAll() //注册API允许所有用户访问
                        .requestMatchers("/ranking").permitAll() //排行榜页面允许所有用户访问
                        .requestMatchers("/api/ranking/**").permitAll() //排行榜API允许所有用户访问
                        .requestMatchers("/actor/**").permitAll()
                        .requestMatchers("/director/**").permitAll()
                        .requestMatchers("/api/creator/**").permitAll()
                        .requestMatchers("/detail/common/**").hasRole("common")
                        .requestMatchers("/detail/vip/**").hasRole("vip")
                        .anyRequest().authenticated()  //所有请求都需要认证
                )
                .formLogin(form -> form  //开启表单登录
                        .loginPage("/userLogin")
                        .loginProcessingUrl("/userLogin") //处理登录的URL
                        .usernameParameter("name")
                        .passwordParameter("pwd")
                        .successHandler(customAuthenticationSuccessHandler())
                        .failureUrl("/userLogin?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/mylogout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable);
        
        return http.build();
    }
}