package com.weitest.testapcapital.model.users;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserBalanceLogPo {
    /** 用戶id */
    private String userId;
    /** 全系統訂單Id */
    private String recordCode;
    /** 金額 */
    private BigDecimal amount;
}
