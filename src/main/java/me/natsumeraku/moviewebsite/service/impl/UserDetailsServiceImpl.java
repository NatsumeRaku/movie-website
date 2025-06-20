package me.natsumeraku.moviewebsite.service.impl;

import me.natsumeraku.moviewebsite.entity.User;
import jakarta.annotation.Resource;
import me.natsumeraku.moviewebsite.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserService userService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("当前用户不存在！");
        }
        
        // 根据用户角色设置权限
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (user.getRole() != null) {
            switch (user.getRole()) {
                case 0: // 普通用户
                    authorities.add(new SimpleGrantedAuthority("ROLE_common"));
                    break;
                case 1: // VIP用户
                    authorities.add(new SimpleGrantedAuthority("ROLE_vip"));
                    authorities.add(new SimpleGrantedAuthority("ROLE_common"));
                    break;
                case 2: // 管理员
                    authorities.add(new SimpleGrantedAuthority("ROLE_admin"));
                    authorities.add(new SimpleGrantedAuthority("ROLE_vip"));
                    authorities.add(new SimpleGrantedAuthority("ROLE_common"));
                    break;
                default:
                    authorities.add(new SimpleGrantedAuthority("ROLE_common"));
                    break;
            }
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_common"));
        }
        
        // 返回封装的UserDetails用户详情类
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getStatus() == 1, // enabled
                true, // accountNonExpired
                true, // credentialsNonExpired
                true, // accountNonLocked
                authorities
        );
    }
}