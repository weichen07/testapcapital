package com.weitest.testapcapital.model.users;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class UserPo {
    /** PK 用戶id */
    private String userId;
    /** 用戶名稱 */
    private String userName;
    /** 餘額 */
    private BigDecimal balance;
}
