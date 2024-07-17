package com.weitest.testapcapital.model.users.vo;

import com.weitest.testapcapital.model.users.OrderPo;
import com.weitest.testapcapital.model.users.UserBalanceLogPo;
import lombok.Data;

@Data
public class OrderBalanceLogVo {
    private UserBalanceLogPo userBalanceLogPo;
    private OrderPo orderPo;
}
