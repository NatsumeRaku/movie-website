package me.natsumeraku.moviewebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import me.natsumeraku.moviewebsite.entity.VipOrder;
import me.natsumeraku.moviewebsite.mapper.VipOrderMapper;
import me.natsumeraku.moviewebsite.service.VipOrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

/**
 * VIP订单服务实现类
 */
@Service
public class VipOrderServiceImpl implements VipOrderService {
    
    @Resource
    private VipOrderMapper vipOrderMapper;
    
    @Override
    public boolean createOrder(VipOrder vipOrder) {
        vipOrder.setOrderNumber(generateOrderNumber());
        vipOrder.setStatus(0); // 待支付
        vipOrder.setCreateTime(LocalDateTime.now());
        vipOrder.setUpdateTime(LocalDateTime.now());
        return vipOrderMapper.insert(vipOrder) > 0;
    }
    
    @Override
    public boolean updateOrder(VipOrder vipOrder) {
        vipOrder.setUpdateTime(LocalDateTime.now());
        return vipOrderMapper.updateById(vipOrder) > 0;
    }
    
    @Override
    public VipOrder findById(Long id) {
        return vipOrderMapper.selectById(id);
    }
    
    @Override
    public VipOrder findByOrderNumber(String orderNumber) {
        QueryWrapper<VipOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_number", orderNumber);
        return vipOrderMapper.selectOne(queryWrapper);
    }
    
    @Override
    public IPage<VipOrder> getOrdersByUser(Page<VipOrder> page, Long userId) {
        QueryWrapper<VipOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .orderByDesc("create_time");
        return vipOrderMapper.selectPage(page, queryWrapper);
    }
    
    @Override
    public IPage<VipOrder> getAllOrders(Page<VipOrder> page) {
        QueryWrapper<VipOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        return vipOrderMapper.selectPage(page, queryWrapper);
    }
    
    @Override
    public boolean payOrder(String orderNumber, Integer paymentType) {
        VipOrder order = findByOrderNumber(orderNumber);
        if (order != null && order.getStatus() == 0) {
            order.setStatus(1); // 已支付
            order.setPaymentType(paymentType);
            order.setPaymentTime(LocalDateTime.now());
            order.setUpdateTime(LocalDateTime.now());
            return vipOrderMapper.updateById(order) > 0;
        }
        return false;
    }
    
    @Override
    public boolean cancelOrder(String orderNumber) {
        VipOrder order = findByOrderNumber(orderNumber);
        if (order != null && order.getStatus() == 0) {
            order.setStatus(2); // 已取消
            order.setUpdateTime(LocalDateTime.now());
            return vipOrderMapper.updateById(order) > 0;
        }
        return false;
    }
    
    @Override
    public boolean refundOrder(String orderNumber) {
        VipOrder order = findByOrderNumber(orderNumber);
        if (order != null && order.getStatus() == 1) {
            order.setStatus(3); // 已退款
            order.setUpdateTime(LocalDateTime.now());
            return vipOrderMapper.updateById(order) > 0;
        }
        return false;
    }
    
    @Override
    public VipOrder getActiveVipOrder(Long userId) {
        QueryWrapper<VipOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .eq("status", 1) // 已支付
                   .orderByDesc("payment_time")
                   .last("LIMIT 1");
        return vipOrderMapper.selectOne(queryWrapper);
    }
    
    @Override
    public boolean isVipUser(Long userId) {
        VipOrder activeOrder = getActiveVipOrder(userId);
        if (activeOrder == null) {
            return false;
        }
        
        // 检查VIP是否过期（这里简化处理，实际应该根据VIP类型计算过期时间）
        LocalDateTime paymentTime = activeOrder.getPaymentTime();
        LocalDateTime expireTime;
        
        switch (activeOrder.getVipType()) {
            case 1: // 月卡
                expireTime = paymentTime.plusMonths(1);
                break;
            case 2: // 季卡
                expireTime = paymentTime.plusMonths(3);
                break;
            case 3: // 年卡
                expireTime = paymentTime.plusYears(1);
                break;
            default:
                return false;
        }
        
        return LocalDateTime.now().isBefore(expireTime);
    }
    
    @Override
    public long getTotalOrderCount() {
        return vipOrderMapper.selectCount(null);
    }
    
    @Override
    public BigDecimal getTotalRevenue() {
        return vipOrderMapper.selectTotalRevenue();
    }
    
    @Override
    public long getTodayOrderCount() {
        QueryWrapper<VipOrder> queryWrapper = new QueryWrapper<>();
        LocalDate today = LocalDate.now();
        queryWrapper.ge("create_time", today.atStartOfDay())
                   .lt("create_time", today.plusDays(1).atStartOfDay());
        return vipOrderMapper.selectCount(queryWrapper);
    }
    
    @Override
    public BigDecimal getTodayRevenue() {
        LocalDate today = LocalDate.now();
        LocalDateTime startTime = today.atStartOfDay();
        LocalDateTime endTime = today.plusDays(1).atStartOfDay();
        return vipOrderMapper.selectTodayRevenue(startTime, endTime);
    }
    
    @Override
    public String generateOrderNumber() {
        // 生成订单号：VIP + 时间戳 + 随机数
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String randomNum = String.format("%04d", new Random().nextInt(10000));
        return "VIP" + timestamp + randomNum;
    }
    
    @Override
    public IPage<VipOrder> getOrdersByStatus(Page<VipOrder> page, Integer status) {
        QueryWrapper<VipOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", status)
                   .orderByDesc("create_time");
        return vipOrderMapper.selectPage(page, queryWrapper);
    }
    
    @Override
    public long getUserOrderCount(Long userId) {
        QueryWrapper<VipOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return vipOrderMapper.selectCount(queryWrapper);
    }
    
    @Override
    public BigDecimal getUserTotalSpent(Long userId) {
        return vipOrderMapper.selectUserTotalSpent(userId);
    }
}