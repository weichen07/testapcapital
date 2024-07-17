package com.weitest.testapcapital.mapper.merchant;

import com.weitest.testapcapital.model.merchant.MerchantPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface MerchantMapper {


    @Update("UPDATE merchants SET balance = balance + #{balance} WHERE merchant_id = #{merchantId}")
    void updateBalance(MerchantPo merchantPo);


    @Select("SELECT * FROM merchants WHERE merchant_id = #{merchantId} for update")
    MerchantPo findByMerchantLock(@Param("merchantId") String merchantId);
    @Select("SELECT * FROM merchants WHERE merchant_id = #{merchantId}")
    MerchantPo findByMerchant(@Param("merchantId") String merchantId);
}
