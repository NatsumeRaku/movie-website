package me.natsumeraku.moviewebsite.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import me.natsumeraku.moviewebsite.common.Result;
import me.natsumeraku.moviewebsite.entity.User;
import me.natsumeraku.moviewebsite.entity.VipOrder;
import me.natsumeraku.moviewebsite.service.UserService;
import me.natsumeraku.moviewebsite.service.VipOrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 支付控制器 - 支付宝沙箱支付
 */
@Controller
@RequestMapping("/payment")
public class PaymentController {
    
    @Resource
    private VipOrderService vipOrderService;
    
    @Resource
    private UserService userService;
    
    @Value("${alipay.app-id:2021000122671431}")
    private String appId;
    
    @Value("${alipay.gateway-url:https://openapi.alipaydev.com/gateway.do}")
    private String gatewayUrl;
    
    @Value("${alipay.merchant-private-key:}")
    private String merchantPrivateKey;
    
    @Value("${alipay.alipay-public-key:}")
    private String alipayPublicKey;
    
    @Value("${alipay.notify-url:http://localhost:8080/payment/notify}")
    private String notifyUrl;
    
    @Value("${alipay.return-url:http://localhost:8080/payment/return}")
    private String returnUrl;
    
    /**
     * VIP充值页面
     */
    @GetMapping("/vip")
    public String vipPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/userLogin";
        }
        model.addAttribute("user", user);
        return "payment/vip";
    }
    
    /**
     * 创建VIP订单
     */
    @PostMapping("/create-order")
    @ResponseBody
    public Result<Map<String, Object>> createOrder(
            @RequestParam String packageType,
            @RequestParam BigDecimal amount,
            HttpSession session) {
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Result.error("请先登录");
        }
        
        try {
            // 创建订单
            VipOrder order = new VipOrder();
            order.setOrderNo(generateOrderNo());
            order.setUserId(user.getId());
            order.setPackageType(packageType);
            order.setAmount(amount);
            order.setStatus(0); // 待支付
            order.setCreateTime(LocalDateTime.now());
            
            boolean created = vipOrderService.createOrder(order);
            if (!created) {
                return Result.error("订单创建失败");
            }
            
            // 生成支付宝支付表单（模拟）
            String payForm = generateAlipayForm(order);
            
            Map<String, Object> result = new HashMap<>();
            result.put("orderNo", order.getOrderNo());
            result.put("payForm", payForm);
            result.put("payUrl", "/payment/pay/" + order.getOrderNo());
            
            return Result.success(result);
            
        } catch (Exception e) {
            return Result.error("订单创建失败：" + e.getMessage());
        }
    }
    
    /**
     * 支付页面
     */
    @GetMapping("/pay/{orderNo}")
    public String payPage(@PathVariable String orderNo, Model model) {
        VipOrder order = vipOrderService.findByOrderNo(orderNo);
        if (order == null) {
            return "redirect:/payment/vip?error=订单不存在";
        }
        
        model.addAttribute("order", order);
        return "payment/pay";
    }
    
    /**
     * 模拟支付宝支付
     */
    @PostMapping("/simulate-pay")
    @ResponseBody
    public Result<String> simulatePay(@RequestParam String orderNo) {
        try {
            VipOrder order = vipOrderService.findByOrderNo(orderNo);
            if (order == null) {
                return Result.error("订单不存在");
            }
            
            if (order.getStatus() != 0) {
                return Result.error("订单状态异常");
            }
            
            // 模拟支付成功
            order.setStatus(1); // 已支付
            order.setPayTime(LocalDateTime.now());
            order.setTradeNo("ALIPAY_" + System.currentTimeMillis());
            
            boolean updated = vipOrderService.updateOrder(order);
            if (!updated) {
                return Result.error("支付处理失败");
            }
            
            // 更新用户VIP状态
            User user = userService.findById(order.getUserId());
            if (user != null) {
                user.setRole(1); // 1表示VIP用户
                userService.updateUser(user);
            }
            
            return Result.success("支付成功");
            
        } catch (Exception e) {
            return Result.error("支付失败：" + e.getMessage());
        }
    }
    
    /**
     * 支付成功回调页面
     */
    @GetMapping("/return")
    public String payReturn(@RequestParam(required = false) String out_trade_no, Model model) {
        if (out_trade_no != null) {
            VipOrder order = vipOrderService.findByOrderNo(out_trade_no);
            model.addAttribute("order", order);
        }
        return "payment/success";
    }
    
    /**
     * 支付宝异步通知（模拟）
     */
    @PostMapping("/notify")
    @ResponseBody
    public String payNotify(HttpServletRequest request) {
        // 在实际项目中，这里需要验证支付宝的签名
        String outTradeNo = request.getParameter("out_trade_no");
        String tradeStatus = request.getParameter("trade_status");
        
        if ("TRADE_SUCCESS".equals(tradeStatus)) {
            VipOrder order = vipOrderService.findByOrderNo(outTradeNo);
            if (order != null && order.getStatus() == 0) {
                order.setStatus(1);
                order.setPayTime(LocalDateTime.now());
                order.setTradeNo(request.getParameter("trade_no"));
                vipOrderService.updateOrder(order);
                
                // 更新用户VIP状态
                User user = userService.findById(order.getUserId());
                if (user != null) {
                    user.setRole(1); // 1表示VIP用户
                    userService.updateUser(user);
                }
            }
        }
        
        return "success";
    }
    
    /**
     * 订单查询
     */
    @GetMapping("/order/{orderNo}")
    @ResponseBody
    public Result<VipOrder> queryOrder(@PathVariable String orderNo) {
        VipOrder order = vipOrderService.findByOrderNo(orderNo);
        if (order == null) {
            return Result.error("订单不存在");
        }
        return Result.success(order);
    }
    
    /**
     * 生成订单号
     */
    private String generateOrderNo() {
        return "VIP" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    /**
     * 生成支付宝支付表单（模拟）
     */
    private String generateAlipayForm(VipOrder order) {
        // 在实际项目中，这里需要使用支付宝SDK生成真实的支付表单
        return String.format(
            "<form id='alipayForm' action='%s' method='post'>" +
            "<input type='hidden' name='app_id' value='%s'>" +
            "<input type='hidden' name='method' value='alipay.trade.page.pay'>" +
            "<input type='hidden' name='charset' value='utf-8'>" +
            "<input type='hidden' name='sign_type' value='RSA2'>" +
            "<input type='hidden' name='timestamp' value='%s'>" +
            "<input type='hidden' name='version' value='1.0'>" +
            "<input type='hidden' name='notify_url' value='%s'>" +
            "<input type='hidden' name='return_url' value='%s'>" +
            "<input type='hidden' name='biz_content' value='{\"out_trade_no\":\"%s\",\"product_code\":\"FAST_INSTANT_TRADE_PAY\",\"total_amount\":\"%s\",\"subject\":\"VIP会员-%s\"}'>"+
            "</form>",
            gatewayUrl, appId, LocalDateTime.now(), notifyUrl, returnUrl, 
            order.getOrderNo(), order.getAmount(), order.getPackageType()
        );
    }
    
    /**
     * 支付成功页面
     */
    @GetMapping("/success")
    public String paymentSuccessPage() {
        return "payment/success";
    }
    
    /**
     * 支付失败页面
     */
    @GetMapping("/fail")
    public String paymentFailPage() {
        return "payment/fail";
    }
    
    /**
     * 计算VIP到期时间
     */
    private LocalDateTime calculateVipExpireTime(String packageType) {
        LocalDateTime now = LocalDateTime.now();
        return switch (packageType) {
            case "月卡" -> now.plusMonths(1);
            case "季卡" -> now.plusMonths(3);
            case "年卡" -> now.plusYears(1);
            default -> now.plusMonths(1);
        };
    }
}