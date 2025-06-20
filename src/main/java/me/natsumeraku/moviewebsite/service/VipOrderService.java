package me.natsumeraku.moviewebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.natsumeraku.moviewebsite.entity.VipOrder;

import java.math.BigDecimal;

/**
 * VIP订单服务接口
 */
public interface VipOrderService {
    
    /**
     * 创建VIP订单
     * @param vipOrder 订单信息
     * @return 创建结果
     */
    boolean createOrder(VipOrder vipOrder);
    
    /**
     * 更新订单信息
     * @param vipOrder 订单信息
     * @return 更新结果
     */
    boolean updateOrder(VipOrder vipOrder);
    
    /**
     * 根据ID查询订单
     * @param id 订单ID
     * @return 订单信息
     */
    VipOrder findById(Long id);
    
    /**
     * 根据订单号查询订单
     * @param orderNumber 订单号
     * @return 订单信息
     */
    VipOrder findByOrderNumber(String orderNumber);
    
    /**
     * 分页查询用户的订单列表
     * @param page 分页参数
     * @param userId 用户ID
     * @return 分页结果
     */
    IPage<VipOrder> getOrdersByUser(Page<VipOrder> page, Long userId);
    
    /**
     * 分页查询所有订单（管理员用）
     * @param page 分页参数
     * @return 分页结果
     */
    IPage<VipOrder> getAllOrders(Page<VipOrder> page);
    
    /**
     * 支付订单
     * @param orderNumber 订单号
     * @param paymentType 支付方式
     * @return 支付结果
     */
    boolean payOrder(String orderNumber, Integer paymentType);
    
    /**
     * 取消订单
     * @param orderNumber 订单号
     * @return 取消结果
     */
    boolean cancelOrder(String orderNumber);
    
    /**
     * 退款订单
     * @param orderNumber 订单号
     * @return 退款结果
     */
    boolean refundOrder(String orderNumber);
    
    /**
     * 获取用户的有效VIP订单
     * @param userId 用户ID
     * @return VIP订单信息
     */
    VipOrder getActiveVipOrder(Long userId);
    
    /**
     * 检查用户是否为VIP
     * @param userId 用户ID
     * @return 是否为VIP
     */
    boolean isVipUser(Long userId);
    
    /**
     * 获取订单统计信息
     * @return 统计数据
     */
    long getTotalOrderCount();
    
    /**
     * 获取总收入
     * @return 总收入金额
     */
    BigDecimal getTotalRevenue();
    
    /**
     * 获取今日订单数
     * @return 今日订单数
     */
    long getTodayOrderCount();
    
    /**
     * 获取今日收入
     * @return 今日收入金额
     */
    BigDecimal getTodayRevenue();
    
    /**
     * 生成订单号
     * @return 订单号
     */
    String generateOrderNumber();
    
    /**
     * 根据状态查询订单
     * @param page 分页参数
     * @param status 订单状态
     * @return 分页结果
     */
    IPage<VipOrder> getOrdersByStatus(Page<VipOrder> page, Integer status);
    
    /**
     * 获取用户的订单统计
     * @param userId 用户ID
     * @return 订单总数
     */
    long getUserOrderCount(Long userId);
    
    /**
     * 获取用户的消费总额
     * @param userId 用户ID
     * @return 消费总额
     */
    BigDecimal getUserTotalSpent(Long userId);
    
    /**
     * 根据订单号查询订单（支持orderNo字段）
     * @param orderNo 订单号
     * @return 订单信息
     */
    VipOrder findByOrderNo(String orderNo);
}