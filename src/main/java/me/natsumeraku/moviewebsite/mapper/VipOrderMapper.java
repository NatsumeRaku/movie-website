package me.natsumeraku.moviewebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.natsumeraku.moviewebsite.entity.VipOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * VIP购买订单表 Mapper 接口
 */
@Mapper
public interface VipOrderMapper extends BaseMapper<VipOrder> {
    
    /**
     * 根据订单号查询订单
     * @param orderNo 订单号
     * @return 订单信息
     */
    @Select("SELECT * FROM vip_order WHERE order_no = #{orderNo}")
    VipOrder selectByOrderNo(@Param("orderNo") String orderNo);
    
    /**
     * 根据用户ID查询订单
     * @param page 分页参数
     * @param userId 用户ID
     * @return 订单列表
     */
    @Select("SELECT * FROM vip_order WHERE user_id = #{userId} ORDER BY create_time DESC")
    IPage<VipOrder> selectByUserId(Page<VipOrder> page, @Param("userId") Long userId);
    
    /**
     * 根据订单状态查询订单
     * @param page 分页参数
     * @param status 订单状态
     * @return 订单列表
     */
    @Select("SELECT * FROM vip_order WHERE status = #{status} ORDER BY create_time DESC")
    IPage<VipOrder> selectByStatus(Page<VipOrder> page, @Param("status") Integer status);
    
    /**
     * 根据支付方式查询订单
     * @param page 分页参数
     * @param paymentType 支付方式
     * @return 订单列表
     */
    @Select("SELECT * FROM vip_order WHERE payment_type = #{paymentType} ORDER BY create_time DESC")
    IPage<VipOrder> selectByPaymentType(Page<VipOrder> page, @Param("paymentType") Integer paymentType);
    
    /**
     * 根据时间范围查询订单
     * @param page 分页参数
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 订单列表
     */
    @Select("SELECT * FROM vip_order WHERE create_time BETWEEN #{startTime} AND #{endTime} ORDER BY create_time DESC")
    IPage<VipOrder> selectByTimeRange(Page<VipOrder> page, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询用户最近的订单
     * @param userId 用户ID
     * @param limit 限制数量
     * @return 订单列表
     */
    @Select("SELECT * FROM vip_order WHERE user_id = #{userId} ORDER BY create_time DESC LIMIT #{limit}")
    List<VipOrder> selectRecentOrdersByUserId(@Param("userId") Long userId, @Param("limit") Integer limit);
    
    /**
     * 查询用户有效的VIP订单
     * @param userId 用户ID
     * @return 订单信息
     */
    @Select("SELECT * FROM vip_order WHERE user_id = #{userId} AND status = 1 AND (expire_time IS NULL OR expire_time > NOW()) ORDER BY payment_time DESC LIMIT 1")
    VipOrder selectValidVipOrderByUserId(@Param("userId") Long userId);
    
    /**
     * 查询过期的订单
     * @param page 分页参数
     * @param currentTime 当前时间
     * @return 订单列表
     */
    @Select("SELECT * FROM vip_order WHERE status = 0 AND expire_time < #{currentTime} ORDER BY expire_time")
    IPage<VipOrder> selectExpiredOrders(Page<VipOrder> page, @Param("currentTime") LocalDateTime currentTime);
    
    /**
     * 更新订单状态
     * @param orderId 订单ID
     * @param status 新状态
     * @return 影响行数
     */
    @Update("UPDATE vip_order SET status = #{status} WHERE id = #{orderId}")
    int updateOrderStatus(@Param("orderId") Long orderId, @Param("status") Integer status);
    
    /**
     * 更新订单支付信息
     * @param orderId 订单ID
     * @param paymentType 支付方式
     * @param transactionId 交易流水号
     * @param paymentTime 支付时间
     * @return 影响行数
     */
    @Update("UPDATE vip_order SET payment_type = #{paymentType}, transaction_id = #{transactionId}, payment_time = #{paymentTime}, status = 1 WHERE id = #{orderId}")
    int updatePaymentInfo(@Param("orderId") Long orderId, @Param("paymentType") Integer paymentType, @Param("transactionId") String transactionId, @Param("paymentTime") LocalDateTime paymentTime);
    
    /**
     * 统计用户订单数量
     * @param userId 用户ID
     * @return 订单数量
     */
    @Select("SELECT COUNT(*) FROM vip_order WHERE user_id = #{userId}")
    Long countByUserId(@Param("userId") Long userId);
    
    /**
     * 统计指定状态的订单数量
     * @param status 订单状态
     * @return 订单数量
     */
    @Select("SELECT COUNT(*) FROM vip_order WHERE status = #{status}")
    Long countByStatus(@Param("status") Integer status);
    
    /**
     * 统计指定时间范围内的订单金额
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param status 订单状态
     * @return 订单金额
     */
    @Select("SELECT COALESCE(SUM(amount), 0) FROM vip_order WHERE create_time BETWEEN #{startTime} AND #{endTime} AND status = #{status}")
    BigDecimal sumAmountByTimeRangeAndStatus(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime, @Param("status") Integer status);
    
    /**
     * 统计用户支付总金额
     * @param userId 用户ID
     * @return 支付总金额
     */
    @Select("SELECT COALESCE(SUM(amount), 0) FROM vip_order WHERE user_id = #{userId} AND status = 1")
    BigDecimal sumPaidAmountByUserId(@Param("userId") Long userId);
}