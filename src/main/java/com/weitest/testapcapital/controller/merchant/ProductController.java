package com.weitest.testapcapital.controller.merchant;

import com.weitest.testapcapital.response.Response;
import com.weitest.testapcapital.service.merchant.ProductService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Resource
    private ProductService productService;

    @PostMapping("/inventory/{sku}")
    public Response inventoryProduct(@PathVariable String sku, @RequestParam String merchantId, @RequestParam Integer quantity) {
        int count = productService.inventoryProduct(merchantId, sku, quantity);
        if(count == 0){
            return Response.fail("merchantId error");
        }
        return Response.success("product inventory successfully");
    }


}
