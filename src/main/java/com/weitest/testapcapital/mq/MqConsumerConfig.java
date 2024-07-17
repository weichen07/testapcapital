//package com.weitest.testapcapital.mq;
//
//
//import com.alibaba.fastjson.JSON;
//import com.weitest.testapcapital.constant.MqStaticMerchantData;
//import com.weitest.testapcapital.constant.MqStaticUserData;
//import com.weitest.testapcapital.model.merchant.vo.MerchantAndBalanceLogVo;
//import com.weitest.testapcapital.model.users.vo.OrderBalanceLogVo;
//import com.weitest.testapcapital.service.MerchantService;
//import com.weitest.testapcapital.service.OrderService;
//import jakarta.annotation.PreDestroy;
//import jakarta.annotation.Resource;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
//import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
//import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.remoting.common.RemotingHelper;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//
//@Component
//@ConfigurationProperties(prefix = "consumer")
//@Getter
//@Setter
//@Order(2222)
//@Slf4j
//public class MqConsumerConfig {
//
//    public static Integer num = 0;
//
//    private String nameServ;
//    private String model;
//    private String groupName;
//    private String certificate;
//    private String topic;
//    private String tags;
//    private String filters;
//    private int consumeThreadMin;
//    private int consumeThreadMax;
//    private DefaultMQPushConsumer consumer = null;
//
//    @Resource
//    private OrderService orderService;
//    @Resource
//    private MerchantService merchantService;
//
//    @Bean
//    public DefaultMQPushConsumer exposeConsumer() throws Exception {
//        consumer = new DefaultMQPushConsumer(groupName);
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//        consumer.setConsumeThreadMin(consumeThreadMin);
//        consumer.setConsumeThreadMax(consumeThreadMax);
//        consumer.setNamesrvAddr(nameServ);
//        consumer.subscribe(topic, tags);
//        consumer.registerMessageListener((MessageListenerConcurrently) (msgList, ctx) -> process(msgList));
//        consumer.start();
//        return consumer;
//    }
//    public ConsumeConcurrentlyStatus process(final List<MessageExt> msgList) {
//        Message msg = msgList.get(0);
//        String tag = msg.getTags();
//        try {
//            String messageBody = new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET);
//            switch (tag) {
//                //如果分服務的話 可以將這的業務拆分 已經用模組來命名class了
//                case MqStaticUserData.TAG_INSERT_ORDERS_BALANCE_LOG:{
//                    OrderBalanceLogVo vo = JSON.parseObject(messageBody, OrderBalanceLogVo.class);
//                    orderService.insertOrderAndBalanceLog();
//                    break;
//                }
//                case MqStaticMerchantData.TAG_MERCHANT_BALANCE_AND_LOG:{ // 新增用户
//                    MerchantAndBalanceLogVo ao = JSON.parseObject(messageBody, MerchantAndBalanceLogVo.class);
//                    merchantService.updateBalanceAndLog();
//                    break;
//                }
//                default:
//                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            }
//            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//
//        }  catch (Exception e) {
//            e.printStackTrace();
//            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
//        }
//    }
//
//    @PreDestroy
//    public void stop() {
//        if (consumer != null) {
//            consumer.shutdown();
//        }
//    }
//
//}
