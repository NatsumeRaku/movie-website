package me.natsumeraku.moviewebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.natsumeraku.moviewebsite.entity.UserLoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户登录记录表 Mapper 接口
 */
@Mapper
public interface UserLoginLogMapper extends BaseMapper<UserLoginLog> {
}