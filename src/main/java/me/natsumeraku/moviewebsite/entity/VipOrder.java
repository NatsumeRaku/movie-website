package me.natsumeraku.moviewebsite.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * VIP订单
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @TableField("order_number")
    private String orderNumber;
    
    /**
     * 订单号（用于支付）
     */
    @TableField(exist = false)
    private String orderNo;
    
    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;
    
    /**
     * VIP类型(1-月卡,2-季卡,3-年卡)
     */
    @TableField("vip_type")
    private Integer vipType;
    
    /**
     * 订单金额
     */
    @TableField("amount")
    private BigDecimal amount;
    
    /**
     * 支付方式(1-支付宝,2-微信,3-银行卡)
     */
    @TableField("payment_type")
    private Integer paymentType;
    
    /**
     * 订单状态(0-待支付,1-已支付,2-已取消,3-已退款)
     */
    @TableField("status")
    private Integer status;
    
    /**
     * 支付时间
     */
    @TableField("payment_time")
    private LocalDateTime paymentTime;
    
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    /**
     * 套餐类型（用于显示）
     */
    @TableField(exist = false)
    private String packageType;
    
    /**
     * 支付时间（别名）
     */
    @TableField(exist = false)
    private LocalDateTime payTime;
    
    /**
     * 交易号
     */
    @TableField("trade_no")
    private String tradeNo;
    
    // Getter和Setter方法
    public String getOrderNo() {
        return this.orderNumber;
    }
    
    public void setOrderNo(String orderNo) {
        this.orderNumber = orderNo;
    }
    
    public LocalDateTime getPayTime() {
        return this.paymentTime;
    }
    
    public void setPayTime(LocalDateTime payTime) {
        this.paymentTime = payTime;
    }
    
    public String getPackageType() {
        if (this.vipType != null) {
            return switch (this.vipType) {
                case 1 -> "月卡";
                case 2 -> "季卡";
                case 3 -> "年卡";
                default -> "未知";
            };
        }
        return this.packageType;
    }
    
    public void setPackageType(String packageType) {
        this.packageType = packageType;
        // 同时设置vipType
        if (packageType != null) {
            this.vipType = switch (packageType) {
                case "月卡" -> 1;
                case "季卡" -> 2;
                case "年卡" -> 3;
                default -> 1;
            };
        }
    }
}