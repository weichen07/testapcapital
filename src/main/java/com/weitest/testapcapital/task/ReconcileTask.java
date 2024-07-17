package com.weitest.testapcapital.task;

import com.weitest.testapcapital.service.merchant.ReconcileService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReconcileTask {
    @Resource
    private ReconcileService reconcileService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void reconcileInventory() {
        reconcileService.reconcileInventory();
    }
}
