package com.weitest.testapcapital.model.merchant;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MerchantBalanceLogPo {
    /** 商戶id */
    private String merchantId;
    /** 全系統訂單Id */
    private String recordCode;
    /** 金額 */
    private BigDecimal amount;
    /** 加總後金額 */
    private BigDecimal afterBalance;

    private LocalDateTime gmtCreated;
}
