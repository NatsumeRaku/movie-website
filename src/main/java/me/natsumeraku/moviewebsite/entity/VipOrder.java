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
    
}