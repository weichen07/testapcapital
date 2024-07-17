package com.weitest.testapcapital.mapper.merchant;


import com.weitest.testapcapital.model.merchant.ProductPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface ProductMapper {

    @Select("SELECT * FROM product WHERE sku = #{sku}")
    ProductPo findBySku(@Param("sku") String sku);

    @Select("SELECT * FROM product")
    List<ProductPo> findAll();

    @Select("SELECT * FROM product WHERE merchant_id =#{merchantId}")
    List<ProductPo> findByMerchant(@Param("merchantId") String merchantId);

    @Update("UPDATE product SET quantity = quantity + #{quantity} WHERE sku = #{sku}")
    void updateQuantity(@Param("sku") String sku, @Param("quantity") Integer quantity);

    @Update("UPDATE product SET quantity = quantity + #{quantity},quantity_all = quantity_all + #{quantity} WHERE sku = #{sku} and merchant_id =#{merchantId}")
    int inventoryProduct(@Param("merchantId") String merchantId,@Param("sku") String sku, @Param("quantity") Integer quantity);

}
