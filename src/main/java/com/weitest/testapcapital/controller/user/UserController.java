package com.weitest.testapcapital.controller.user;
import com.weitest.testapcapital.response.Response;
import com.weitest.testapcapital.service.users.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/recharge/{userId}")
    public Response recharge(@PathVariable String userId, @RequestParam BigDecimal amount) {
        userService.rechargeBalance(userId, amount);
        return Response.success("recharge successfully");
    }

}
