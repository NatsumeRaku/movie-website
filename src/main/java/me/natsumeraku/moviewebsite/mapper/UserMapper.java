package me.natsumeraku.moviewebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.natsumeraku.moviewebsite.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 用户表 Mapper 接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    @Select("SELECT * FROM user WHERE username = #{username}")
    User selectByUsername(@Param("username") String username);
    
    /**
     * 根据用户名和状态查询用户
     * @param username 用户名
     * @param status 用户状态
     * @return 用户信息
     */
    @Select("SELECT * FROM user WHERE username = #{username} AND status = #{status}")
    User selectByUsernameAndStatus(@Param("username") String username, @Param("status") Integer status);
    
    /**
     * 更新用户角色
     * @param userId 用户ID
     * @param role 新角色
     * @return 影响行数
     */
    @Update("UPDATE user SET role = #{role}, update_time = NOW() WHERE id = #{userId}")
    int updateUserRole(@Param("userId") Long userId, @Param("role") Integer role);
    
    /**
     * 更新用户状态
     * @param userId 用户ID
     * @param status 新状态
     * @return 影响行数
     */
    @Update("UPDATE user SET status = #{status}, update_time = NOW() WHERE id = #{userId}")
    int updateUserStatus(@Param("userId") Long userId, @Param("status") Integer status);
    
    /**
     * 统计指定角色的用户数量
     * @param role 用户角色
     * @return 用户数量
     */
    @Select("SELECT COUNT(*) FROM user WHERE role = #{role}")
    Long countByRole(@Param("role") Integer role);
    
    /**
     * 统计指定状态的用户数量
     * @param status 用户状态
     * @return 用户数量
     */
    @Select("SELECT COUNT(*) FROM user WHERE status = #{status}")
    Long countByStatus(@Param("status") Integer status);
}