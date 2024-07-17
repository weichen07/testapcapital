package com.weitest.testapcapital;

import com.alibaba.fastjson.JSON;
import com.weitest.testapcapital.model.merchant.MerchantBalanceLogPo;
import com.weitest.testapcapital.model.merchant.MerchantPo;
import com.weitest.testapcapital.service.merchant.MerchantService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@SpringBootTest
public class MerchantServiceTest {
    @Resource
    private MerchantService merchantService;

    @Test
    public void testUpdateBalanceAndLog(){
        BigDecimal totalPrice = new BigDecimal("20");
        String recordCode = "test123456" ;
        String merchantId = "3";
        MerchantPo beforeMerchantPo = merchantService.findByMerchantTest(merchantId);
        System.out.println(JSON.toJSONString(beforeMerchantPo));
        LocalDateTime gmtCreated = LocalDateTime.now();
        merchantService.updateBalanceAndLog(totalPrice,recordCode,merchantId,gmtCreated);
        MerchantPo afterMerchantPo = merchantService.findByMerchantTest(merchantId);
        System.out.println(JSON.toJSONString(afterMerchantPo));
        MerchantBalanceLogPo merchantBalanceLogPo = merchantService.findLogByRecordCode(recordCode);
        Assertions.assertEquals(merchantId, merchantBalanceLogPo.getMerchantId());
        Assertions.assertEquals(recordCode,merchantBalanceLogPo.getRecordCode());
        Assertions.assertEquals(0, totalPrice.compareTo(merchantBalanceLogPo.getAmount()));
        Assertions.assertEquals(0, afterMerchantPo.getBalance().compareTo(beforeMerchantPo.getBalance().add(totalPrice)));
        System.out.println("END");
    }
}
