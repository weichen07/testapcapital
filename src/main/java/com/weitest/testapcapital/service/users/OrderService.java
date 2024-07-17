package com.weitest.testapcapital.service.users;

import com.weitest.testapcapital.constant.CommonStaticData;
import com.weitest.testapcapital.mapper.users.OrderMapper;
import com.weitest.testapcapital.model.merchant.ProductPo;
import com.weitest.testapcapital.model.users.OrderPo;
import com.weitest.testapcapital.model.users.UserBalanceLogPo;
import com.weitest.testapcapital.service.merchant.MerchantService;
import com.weitest.testapcapital.service.merchant.ProductService;
import com.weitest.testapcapital.util.EncodeGenerateUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private UserService userService;
    @Resource
    private UserBalanceLogService userBalanceLogService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ProductService productService;
    @Resource
    private MerchantService merchantService;

    /**
     * 下單購買
     * @param userId 用戶id
     * @param productSku 商品sku
     * @param quantity 購買數量
     * @return 銷售狀態
     */
    @Transactional(rollbackFor = Exception.class)
    public String purchaseOrder(String userId, String productSku, Integer quantity) {
        LocalDateTime gmtCreated = LocalDateTime.now();
        String key = CommonStaticData.PRODUCT_KEY + productSku;
        //這部分比較好的做法是監控binlog 修改庫存時 同步發送mq給service修改redis庫存 目前就是簡單寫
        long skuNum = stringRedisTemplate.opsForValue().increment(key,-quantity.longValue());
        if(skuNum<=0){
            //加庫存
            stringRedisTemplate.opsForValue().increment(key,quantity.longValue());
            log.info("庫存不足:"+userId+","+productSku+","+quantity);
            return "庫存不足";
        }
        //可以將金額塞進redis 改hash結構 這樣就可以少掉用這個service
        //分布式的話 這個改成feign調用
        //分布式系統的話 這裡可以改成直接扣庫存 如果這個service失敗在用mq最終一致性去加回來
        ProductPo product = productService.findBySku(productSku);
        BigDecimal totalPrice = product.getPrice().multiply(new BigDecimal(quantity));

        userService.updateBalance(userId, totalPrice.multiply(CommonStaticData.MINUS_ONE_BIGDEC));
        //分布式的話 這個改成feign調用 強一致
        productService.updateProductQuantity(productSku, (quantity*CommonStaticData.MINUS_ONE_INT));
        String recordCodeOrder = EncodeGenerateUtil.getRecordCode(EncodeGenerateUtil.PLACE_ORDER,Integer.parseInt(userId));
        // 可改成發送mq
        // 插入訂單與餘額log
        insertOrderAndBalanceLog(userId,productSku,quantity,recordCodeOrder,totalPrice,gmtCreated);
        // 分布式的話 這個改成發送mq
        merchantService.updateBalanceAndLog(totalPrice,recordCodeOrder,product.getMerchantId(),gmtCreated);
        return CommonStaticData.BUY_SUCCESS;
    }
    /**
     * 插入用戶訂單還有用戶餘額log
     * @param userId 用戶id
     * @param productSku 商品sku
     * @param quantity 購買數量
     * @param recordCode 訂單編碼
     * @param totalPrice 訂單金額
     * @param gmtCreated 訂單發起時間
     */
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void insertOrderAndBalanceLog(String userId,String productSku,Integer quantity,String recordCode,BigDecimal totalPrice,LocalDateTime gmtCreated){
        OrderPo order = new OrderPo();
        order.setUserId(userId);
        order.setSku(productSku);
        order.setQuantity(quantity);
        order.setTotalPrice(totalPrice);
        order.setRecordCode(recordCode);
        order.setGmtCreated(gmtCreated);
        UserBalanceLogPo userBalanceLog = new UserBalanceLogPo();
        userBalanceLog.setAmount(totalPrice.multiply(CommonStaticData.MINUS_ONE_BIGDEC));
        userBalanceLog.setRecordCode(recordCode);
        userBalanceLog.setUserId(userId);
        orderMapper.insertOrder(order);
        userBalanceLogService.insertUserBalanceLog(userBalanceLog);
    }

    public List<OrderPo> findByCreate(LocalDateTime startTime, LocalDateTime endTime){
        return orderMapper.findByCreate(startTime,endTime);
    }
    public OrderPo findByRecordCode(String recordCode){
        return orderMapper.findByRecordCode(recordCode);
    }


}
