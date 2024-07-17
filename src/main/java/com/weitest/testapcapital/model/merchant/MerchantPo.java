package com.weitest.testapcapital.model.merchant;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MerchantPo {
    /** PK 商戶id */
    private String merchantId;
    /** 商戶名稱 */
    private String merchantName;
    /** 餘額 */
    private BigDecimal balance;
}
