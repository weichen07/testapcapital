package com.weitest.testapcapital.model.merchant.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DifferentVo {
    /** 商戶id */
    private String merchantId;
    /** 全系統訂單Id */
    private String recordCode;
    /** 金額 */
    private BigDecimal amount;

    private String userId;
    private String sku;
    private Integer quantity;
    private BigDecimal totalPrice;
}
