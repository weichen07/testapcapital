package com.weitest.testapcapital.service.merchant;


import com.weitest.testapcapital.mapper.merchant.ProductMapper;
import com.weitest.testapcapital.model.merchant.ProductPo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductService {

    @Resource
    private ProductMapper productMapper;

    public ProductPo findBySku(String sku) {
        return productMapper.findBySku(sku);
    }

    public List<ProductPo> findAll() {
        return productMapper.findAll();
    }
    /**
     * 扣減庫存
     * @param sku 商品sku
     * @param quantity 購買數量
     * */
    public void updateProductQuantity(String sku, Integer quantity) {
        productMapper.updateQuantity(sku, quantity);
    }
    /**
     * 補庫存
     * @param merchantId 用戶id
     * @param sku 商品sku
     * @param quantity 購買數量
     * */
    public int inventoryProduct(String merchantId,String sku, Integer quantity) {
        return productMapper.inventoryProduct(merchantId,sku,quantity);
    }
}
