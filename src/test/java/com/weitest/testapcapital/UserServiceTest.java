package com.weitest.testapcapital;

import com.alibaba.fastjson.JSON;
import com.weitest.testapcapital.model.users.UserBalanceLogPo;
import com.weitest.testapcapital.model.users.UserPo;
import com.weitest.testapcapital.service.users.UserBalanceLogService;
import com.weitest.testapcapital.service.users.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@Slf4j
@SpringBootTest
public class UserServiceTest {
    @Resource
    private UserService userService;
    @Resource
    private UserBalanceLogService userBalanceLogService;

    @Test
    public void testRechargeBalance(){
        String userId = "3";
        BigDecimal amount = new BigDecimal("100");
        UserPo before = userService.findByUserId(userId);
        System.out.println(JSON.toJSONString(before));
        userService.rechargeBalance(userId,amount);
        UserPo after = userService.findByUserId(userId);
        System.out.println(JSON.toJSONString(after));
        UserBalanceLogPo one = userBalanceLogService.findByUserIdLatest(userId);
        Assertions.assertEquals(0, amount.compareTo((after.getBalance().subtract(before.getBalance()))));
        Assertions.assertEquals(0, one.getAmount().compareTo(amount));
        System.out.println("end");
    }
}
