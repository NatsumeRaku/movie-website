package me.natsumeraku.moviewebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.natsumeraku.moviewebsite.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表 Mapper 接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}