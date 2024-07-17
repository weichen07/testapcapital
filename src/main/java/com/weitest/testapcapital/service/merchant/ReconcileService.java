package com.weitest.testapcapital.service.merchant;

import com.alibaba.fastjson.JSON;
import com.weitest.testapcapital.mapper.merchant.MerchantBalanceLogMapper;
import com.weitest.testapcapital.model.merchant.MerchantBalanceLogPo;
import com.weitest.testapcapital.model.merchant.vo.DifferentVo;
import com.weitest.testapcapital.model.users.OrderPo;
import com.weitest.testapcapital.service.users.OrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReconcileService {

    @Resource
    private MerchantBalanceLogMapper merchantBalanceLogMapper;
    @Resource
    private OrderService orderService;

    public void reconcileInventory() {
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime yesterday = today.minusDays(1);
        List<MerchantBalanceLogPo> merchantBalanceLogPoList = merchantBalanceLogMapper.findByCreate(yesterday,today);
        List<OrderPo> orderPoList= orderService.findByCreate(yesterday,today);
        List<DifferentVo> differentList = new ArrayList<>();
        Map<String,OrderPo> map = orderPoList.stream().collect(Collectors.toMap(OrderPo::getRecordCode,orderPo -> orderPo));
        for(MerchantBalanceLogPo one : merchantBalanceLogPoList){
            OrderPo orderPo = map.get(one.getRecordCode());
            if(ObjectUtils.isEmpty(orderPo)){
                DifferentVo vo = new DifferentVo();
                vo.setMerchantId(one.getMerchantId());
                vo.setRecordCode(one.getRecordCode());
                vo.setAmount(one.getAmount());
                differentList.add(vo);
                continue;
            }
            if(one.getAmount().compareTo(orderPo.getTotalPrice())!=0){
                DifferentVo vo = new DifferentVo();
                vo.setMerchantId(one.getMerchantId());
                vo.setRecordCode(one.getRecordCode());
                vo.setAmount(one.getAmount());
                vo.setSku(orderPo.getSku());
                vo.setUserId(orderPo.getUserId());
                vo.setTotalPrice(orderPo.getTotalPrice());
                vo.setQuantity(orderPo.getQuantity());
                differentList.add(vo);
            }
            map.remove(one.getRecordCode());
        }
        if(!map.isEmpty()){
            map.forEach((key,orderPo)->{
                DifferentVo vo = new DifferentVo();
                vo.setSku(orderPo.getSku());
                vo.setUserId(orderPo.getUserId());
                vo.setTotalPrice(orderPo.getTotalPrice());
                vo.setQuantity(orderPo.getQuantity());
                differentList.add(vo);
            });
        }
        if(!differentList.isEmpty()){
            System.out.println(JSON.toJSONString(differentList));
        }
        System.out.println("end");
    }


}
