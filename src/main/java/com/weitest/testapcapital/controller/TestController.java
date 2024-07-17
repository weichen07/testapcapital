package com.weitest.testapcapital.controller;

import com.weitest.testapcapital.response.Response;
import com.weitest.testapcapital.service.merchant.ReconcileService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private ReconcileService reconcileService;
    @GetMapping("/reconcileInventory")
    public Response reconcileInventory() {
        reconcileService.reconcileInventory();
        return Response.success("reconcileInventory successfully");
    }
}
