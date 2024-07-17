package com.weitest.testapcapital.model.merchant.vo;

import com.weitest.testapcapital.model.merchant.MerchantBalanceLogPo;
import com.weitest.testapcapital.model.merchant.MerchantPo;
import lombok.Data;

@Data
public class MerchantAndBalanceLogVo {
    private MerchantPo merchantPo;
    private MerchantBalanceLogPo merchantBalanceLogPo;
}
