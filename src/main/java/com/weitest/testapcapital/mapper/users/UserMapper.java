package com.weitest.testapcapital.mapper.users;

import com.weitest.testapcapital.model.users.UserPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

public interface UserMapper {

    @Update("UPDATE users SET balance = balance + #{balance} WHERE user_id = #{userId}")
    void updateBalance(@Param("userId") String userId, @Param("balance") BigDecimal balance);
    @Select("SELECT * FROM users WHERE user_id = #{userId}")
    UserPo findByUserId(@Param("userId") String userId);
}
