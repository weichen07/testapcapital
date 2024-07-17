package com.weitest.testapcapital.mapper.users;


import com.weitest.testapcapital.model.users.UserBalanceLogPo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface UserBalanceLogMapper {



    @Insert("INSERT INTO users_balance_log (user_id, record_code, amount) VALUES (#{userId}, #{recordCode}, #{amount})")
    void insertUserBalanceLog(UserBalanceLogPo userBalanceLog);

    @Select("SELECT * FROM users_balance_log WHERE user_id = #{userId} order by id desc limit 1")
    UserBalanceLogPo findByUserIdLatest(@Param("userId") String userId);

    @Select("SELECT * FROM users_balance_log WHERE record_code = #{recordCode} ")
    UserBalanceLogPo findByRecordCode(@Param("recordCode") String recordCode);

}
