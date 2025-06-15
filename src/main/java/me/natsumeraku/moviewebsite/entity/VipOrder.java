package me.natsumeraku.moviewebsite.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * VIP购买订单表
 */
@TableName("vip_order")
public class VipOrder {
    
    /**
     * 订单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;
    
    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;
    
    /**
     * 金额
     */
    @TableField("amount")
    private BigDecimal amount;
    
    /**
     * 订单状态(0-未支付,1-已支付,2-已取消)
     */
    @TableField("status")
    private Integer status;
    
    /**
     * 支付方式(1-支付宝,2-微信)
     */
    @TableField("payment_type")
    private Integer paymentType;
    
    /**
     * 交易流水号
     */
    @TableField("transaction_id")
    private String transactionId;
    
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 支付时间
     */
    @TableField("payment_time")
    private LocalDateTime paymentTime;
    
    /**
     * 过期时间
     */
    @TableField("expire_time")
    private LocalDateTime expireTime;
    
    // 构造函数
    public VipOrder() {}
    
    public VipOrder(String orderNo, Long userId, BigDecimal amount) {
        this.orderNo = orderNo;
        this.userId = userId;
        this.amount = amount;
    }
    
    // Getter和Setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getOrderNo() {
        return orderNo;
    }
    
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Integer getPaymentType() {
        return paymentType;
    }
    
    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    
    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }
    
    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }
    
    public LocalDateTime getExpireTime() {
        return expireTime;
    }
    
    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
    
    @Override
    public String toString() {
        return "VipOrder{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", userId=" + userId +
                ", amount=" + amount +
                ", status=" + status +
                ", paymentType=" + paymentType +
                ", transactionId='" + transactionId + '\'' +
                ", createTime=" + createTime +
                ", paymentTime=" + paymentTime +
                ", expireTime=" + expireTime +
                '}';
    }
}