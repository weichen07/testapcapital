package com.weitest.testapcapital.mapper.users;


import com.weitest.testapcapital.model.users.OrderPo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;


public interface OrderMapper {



    @Insert("INSERT INTO orders (user_id,record_code,sku, quantity, total_price,gmt_created) " +
            "VALUES (#{userId}, #{recordCode}, #{sku}, #{quantity}, #{totalPrice},#{gmtCreated})")
    void insertOrder(OrderPo orderPo);

    @Select("SELECT * FROM orders WHERE gmt_created between #{startTime} and #{endTime}")
    List<OrderPo> findByCreate(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Select("SELECT * FROM orders WHERE record_code = #{recordCode}")
    OrderPo findByRecordCode(@Param("recordCode") String recordCode);


}
