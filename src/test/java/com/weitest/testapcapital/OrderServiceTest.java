package com.weitest.testapcapital;

import com.weitest.testapcapital.constant.CommonStaticData;
import com.weitest.testapcapital.model.users.OrderPo;
import com.weitest.testapcapital.model.users.UserBalanceLogPo;
import com.weitest.testapcapital.service.merchant.MerchantService;
import com.weitest.testapcapital.service.merchant.ProductService;
import com.weitest.testapcapital.service.users.OrderService;
import com.weitest.testapcapital.service.users.UserBalanceLogService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@SpringBootTest
public class OrderServiceTest {
    @Resource
    private OrderService orderService;
    @Resource
    private UserBalanceLogService userBalanceLogService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ProductService productService;
    @Resource
    private MerchantService merchantService;

    @Test
    public void testInsertOrderAndBalanceLog(){
        String userId = "3";
        String productSku ="0000-0-3";
        Integer quantity = 3;
        Integer price = 100;
        String recordCode = "te470601781126231658";
        BigDecimal totalPrice = new BigDecimal(quantity*price);
        LocalDateTime gmtCreated = LocalDateTime.now();
        orderService.insertOrderAndBalanceLog(userId,productSku,quantity,recordCode,totalPrice,gmtCreated);
        UserBalanceLogPo userBalanceLogPo = userBalanceLogService.findByRecordCode(recordCode);
        OrderPo orderPo = orderService.findByRecordCode(recordCode);
        Assertions.assertEquals(userId, userBalanceLogPo.getUserId());
        Assertions.assertEquals(recordCode, userBalanceLogPo.getRecordCode());
        Assertions.assertEquals(0, (totalPrice.multiply(CommonStaticData.MINUS_ONE_BIGDEC)).compareTo(userBalanceLogPo.getAmount()));
        Assertions.assertEquals(userId, orderPo.getUserId());
        Assertions.assertEquals(recordCode, orderPo.getRecordCode());
        Assertions.assertEquals(productSku, orderPo.getSku());
        Assertions.assertEquals(quantity, orderPo.getQuantity());
    }
    @Test
    public void testPurchaseOrder(){
        String userId = "3";
        String productSku ="0000-0-3";
        Integer quantity = 3;
        String key = CommonStaticData.PRODUCT_KEY + productSku;
        Integer beforeValue = Integer.parseInt(stringRedisTemplate.opsForValue().get(key));
        String res = orderService.purchaseOrder(userId, productSku, quantity);
        Integer afterValue =  Integer.parseInt(stringRedisTemplate.opsForValue().get(key));
        Assertions.assertEquals(CommonStaticData.BUY_SUCCESS, res);
        Assertions.assertEquals(beforeValue, (afterValue+quantity));
    }

}
