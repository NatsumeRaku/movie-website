package me.natsumeraku.moviewebsite.config;

import me.natsumeraku.moviewebsite.service.impl.UserDetailsServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Resource
    private UserDetailsServiceImpl userDetailsService;
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/").permitAll() //放行根页面，不用认证可以直接访问。
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/userLogin").permitAll()
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
                        .defaultSuccessUrl("/")
                        .failureUrl("/userLogin?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/mylogout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());
        
        return http.build();
    }
}