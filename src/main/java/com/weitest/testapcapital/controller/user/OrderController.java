package com.weitest.testapcapital.controller.user;

import jakarta.annotation.Resource;
import com.weitest.testapcapital.response.Response;
import org.springframework.web.bind.annotation.*;
import com.weitest.testapcapital.service.users.OrderService;
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/purchase")
    public Response purchaseOrder(@RequestParam String userId, @RequestParam String productSku, @RequestParam Integer quantity) {
        return Response.success(orderService.purchaseOrder(userId, productSku, quantity));
    }
}
