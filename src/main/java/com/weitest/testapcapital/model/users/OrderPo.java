package com.weitest.testapcapital.model.users;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderPo {
    private String id;
    /** 全系統訂單Id */
    private String recordCode;
    private String userId;
    private String sku;
    private Integer quantity;
    private BigDecimal totalPrice;
    private LocalDateTime gmtCreated;

}
