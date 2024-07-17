package com.weitest.testapcapital.mapper.merchant;


import com.weitest.testapcapital.model.merchant.MerchantBalanceLogPo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;


public interface MerchantBalanceLogMapper {



    @Insert("INSERT INTO merchants_balance_log (merchant_id, record_code, amount,after_balance,gmt_created) " +
            "VALUES (#{merchantId}, #{recordCode}, #{amount},#{afterBalance},#{gmtCreated})")
    void insertMerchantBalanceLog(MerchantBalanceLogPo merchantBalanceLog);


    @Select("SELECT * FROM merchants_balance_log WHERE gmt_created between #{startTime} and #{endTime}")
    List<MerchantBalanceLogPo> findByCreate(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Select("SELECT * FROM merchants_balance_log WHERE record_code = #{recordCode} ")
    MerchantBalanceLogPo findLogByRecordCode(@Param("recordCode") String recordCode);

}
