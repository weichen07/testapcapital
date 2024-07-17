package com.weitest.testapcapital.task;

import com.weitest.testapcapital.constant.CommonStaticData;
import com.weitest.testapcapital.model.merchant.ProductPo;
import com.weitest.testapcapital.service.merchant.ProductService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ProductTask {
    @Resource
    private ProductService productService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PostConstruct
    public void initProductInRedis() {
        List<ProductPo> list = productService.findAll();
        list.forEach(one->{
            String key = CommonStaticData.PRODUCT_KEY + one.getSku();
            stringRedisTemplate.opsForValue().set(key,one.getQuantity().toString());
            System.out.println("key:"+key+",data:"+stringRedisTemplate.opsForValue().get(key));
        });
    }
}
