package me.natsumeraku.moviewebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.natsumeraku.moviewebsite.entity.UserLoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户登录记录表 Mapper 接口
 */
@Mapper
public interface UserLoginLogMapper extends BaseMapper<UserLoginLog> {
    
    /**
     * 根据用户ID查询登录记录
     * @param page 分页参数
     * @param userId 用户ID
     * @return 登录记录列表
     */
    @Select("SELECT * FROM user_login_log WHERE user_id = #{userId} ORDER BY login_time DESC")
    IPage<UserLoginLog> selectByUserId(Page<UserLoginLog> page, @Param("userId") Long userId);
    
    /**
     * 根据时间范围查询登录记录
     * @param page 分页参数
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 登录记录列表
     */
    @Select("SELECT * FROM user_login_log WHERE login_time BETWEEN #{startTime} AND #{endTime} ORDER BY login_time DESC")
    IPage<UserLoginLog> selectByTimeRange(Page<UserLoginLog> page, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
    
    /**
     * 根据IP地址查询登录记录
     * @param page 分页参数
     * @param loginIp 登录IP
     * @return 登录记录列表
     */
    @Select("SELECT * FROM user_login_log WHERE login_ip = #{loginIp} ORDER BY login_time DESC")
    IPage<UserLoginLog> selectByLoginIp(Page<UserLoginLog> page, @Param("loginIp") String loginIp);
    
    /**
     * 查询用户最近的登录记录
     * @param userId 用户ID
     * @param limit 限制数量
     * @return 登录记录列表
     */
    @Select("SELECT * FROM user_login_log WHERE user_id = #{userId} ORDER BY login_time DESC LIMIT #{limit}")
    List<UserLoginLog> selectRecentLoginsByUserId(@Param("userId") Long userId, @Param("limit") Integer limit);
    
    /**
     * 统计用户登录次数
     * @param userId 用户ID
     * @return 登录次数
     */
    @Select("SELECT COUNT(*) FROM user_login_log WHERE user_id = #{userId}")
    Long countByUserId(@Param("userId") Long userId);
    
    /**
     * 统计指定时间范围内的登录次数
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 登录次数
     */
    @Select("SELECT COUNT(*) FROM user_login_log WHERE login_time BETWEEN #{startTime} AND #{endTime}")
    Long countByTimeRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计今日登录用户数
     * @param today 今日日期
     * @return 用户数
     */
    @Select("SELECT COUNT(DISTINCT user_id) FROM user_login_log WHERE DATE(login_time) = DATE(#{today})")
    Long countTodayActiveUsers(@Param("today") LocalDateTime today);
    
    /**
     * 查询用户最后一次登录记录
     * @param userId 用户ID
     * @return 登录记录
     */
    @Select("SELECT * FROM user_login_log WHERE user_id = #{userId} ORDER BY login_time DESC LIMIT 1")
    UserLoginLog selectLastLoginByUserId(@Param("userId") Long userId);
}