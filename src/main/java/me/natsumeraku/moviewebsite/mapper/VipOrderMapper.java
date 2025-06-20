package me.natsumeraku.moviewebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.natsumeraku.moviewebsite.entity.VipOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * VIP购买订单表 Mapper 接口
 */
@Mapper
public interface VipOrderMapper extends BaseMapper<VipOrder> {
    
    /**
     * 计算总收入（已支付订单）
     * @return 总收入
     */
    @Select("SELECT IFNULL(SUM(amount), 0) FROM vip_order WHERE status = 1")
    BigDecimal selectTotalRevenue();
    
    /**
     * 计算今日收入
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 今日收入
     */
    @Select("SELECT IFNULL(SUM(amount), 0) FROM vip_order WHERE status = 1 AND create_time >= #{startTime} AND create_time < #{endTime}")
    BigDecimal selectTodayRevenue(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
    
    /**
     * 计算用户总消费
     * @param userId 用户ID
     * @return 用户总消费
     */
    @Select("SELECT IFNULL(SUM(amount), 0) FROM vip_order WHERE user_id = #{userId} AND status = 1")
    BigDecimal selectUserTotalSpent(@Param("userId") Long userId);
}