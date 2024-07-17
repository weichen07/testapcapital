package com.weitest.testapcapital.service.merchant;

import com.weitest.testapcapital.mapper.merchant.MerchantBalanceLogMapper;
import com.weitest.testapcapital.mapper.merchant.MerchantMapper;
import com.weitest.testapcapital.model.merchant.MerchantBalanceLogPo;
import com.weitest.testapcapital.model.merchant.MerchantPo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
public class MerchantService {
    @Resource
    private MerchantMapper merchantMapper;
    @Resource
    private MerchantBalanceLogMapper merchantBalanceLogMapper;

    /**
     * 更新商戶餘額還有商戶銷售log
     * @param totalPrice 訂單金額
     * @param recordCode 訂單編碼
     * @param merchantId 商戶id
     * @param gmtCreated 訂單發起時間
     */
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void updateBalanceAndLog(BigDecimal totalPrice, String recordCode, String merchantId,LocalDateTime gmtCreated){
        MerchantBalanceLogPo merchantBalanceLogPo = new MerchantBalanceLogPo();
        merchantBalanceLogPo.setAmount(totalPrice);
        merchantBalanceLogPo.setRecordCode(recordCode);
        merchantBalanceLogPo.setMerchantId(merchantId);
        merchantBalanceLogPo.setGmtCreated(gmtCreated);
        MerchantPo merchantPo = new MerchantPo();
        merchantPo.setMerchantId(merchantId);
        merchantPo.setBalance(totalPrice);
        MerchantPo one = findByMerchant(merchantId);
        merchantBalanceLogPo.setAfterBalance(one.getBalance().add(totalPrice));
        merchantMapper.updateBalance(merchantPo);
        merchantBalanceLogMapper.insertMerchantBalanceLog(merchantBalanceLogPo);
    }

    public MerchantBalanceLogPo findLogByRecordCode(String recordCode){
        return merchantBalanceLogMapper.findLogByRecordCode(recordCode);
    }

    public MerchantPo findByMerchant(String merchantId){
        return merchantMapper.findByMerchantLock(merchantId);
    }
    public MerchantPo findByMerchantTest(String merchantId){
        return merchantMapper.findByMerchant(merchantId);
    }

}
