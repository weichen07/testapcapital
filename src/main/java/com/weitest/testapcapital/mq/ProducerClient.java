//package com.weitest.testapcapital.mq;
//
//import ch.qos.logback.classic.Logger;
//import com.alibaba.fastjson.JSON;
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.PreDestroy;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.exception.MQBrokerException;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.apache.rocketmq.client.producer.SendCallback;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.client.producer.SendStatus;
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.remoting.exception.RemotingException;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.core.annotation.Order;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//interface MessageBuilder {
//    void build();
//
//    Message of(String from, String recordCode, int transactionType, Object obj);
//    Collection<Message> of(String from, String recordCode, int transactionType, List<Object> obj);
//}
//
//@Component
//@ConfigurationProperties(prefix = "producer")
//@Order(value = 2223)
//@Getter
//@Setter
//@Slf4j
//public class ProducerClient implements MessageBuilder{
//    private static final String DEFAULT_GROUP_NAME = "MERCHANT";
//    private static final String DEFAULT_NAMESRV_ADDR = "localhost:9876";
//
//
//
//    private String nameSrvAddr;
//
//    private String topic;
//
//    private boolean useVipChannel=false;
//
//    private int maxBatchSize;
//
//    private String groupName;
//
//    private int retryTimesWhenSendFailed;
//
//    private int sendTimeout;
//
//    public static Boolean testSendSuccessOrNot = true;
//
//    private int mqSize;
//
//    private DefaultMQProducer delegate;
//
//    public ProducerClient(){
//
//    }
//
//    @PostConstruct
//    public void run() throws Exception {
//        delegate = new DefaultMQProducer(groupName==null?DEFAULT_GROUP_NAME:groupName);
//        delegate.setNamesrvAddr(nameSrvAddr == null?DEFAULT_NAMESRV_ADDR:nameSrvAddr);
//        delegate.setVipChannelEnabled(useVipChannel);
//        delegate.setRetryTimesWhenSendAsyncFailed(retryTimesWhenSendFailed);
//        if (getSendTimeout()!=0){
//            delegate.setSendMsgTimeout(getSendTimeout());
//        }
//        delegate.setRetryTimesWhenSendAsyncFailed(retryTimesWhenSendFailed);
//        try {
//            delegate.start();
//        } catch (MQClientException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void sendMessageToService(String topic,String tag,String key,String jsonStr) {
//        Message msg = new Message(topic,tag,key,jsonStr.getBytes());
//        asyncSend(msg);
//    }
//    public void asyncSend(Message msg){
//
//        try {
//            delegate.send(msg, new SendCallback() {
//                @Override
//                public void onSuccess(SendResult sendResult) {
//                }
//
//                @Override
//                public void onException(Throwable e) {
//                    e.printStackTrace();
//                }
//            });
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//    @PreDestroy
//    public void shutDown(){
//        delegate.shutdown();
//    }
//
//
//    @Override
//    public void build() {
//
//    }
//
//    @Override
//    public Message of(final String from, final String recordCode, final int transactionType, Object obj) {
//        if (StringUtils.hasLength(from)&&StringUtils.hasLength(recordCode)&&transactionType>0&&obj!=null){
//            Message msg = new Message(getTopic(),from,recordCode,transactionType,JSON.toJSONString(obj).getBytes(),true);
//            return msg;
//        }
//        return null;
//    }
//
//    @Override
//    public List<Message> of(final String from,final String recordCode, final int transactionType, List<Object> obj) {
//        if (StringUtils.hasLength(from)&&StringUtils.hasLength(recordCode)&&transactionType>0&&obj!=null){
//            return obj.stream().map(x->new Message(getTopic(),from,recordCode,transactionType,JSON.toJSONString(obj).getBytes(),true)).collect(Collectors.toList());
//        }
//        return null;
//    }
//
//}
//
//class ListSplitter implements Iterator<List<Message>> {
//    private final List<Message> messages;
//    private static final int DEFAULT_MQ_SIZE = 16384;//128k
//    private final int sizeLimit;
//    private int currIndex;
//
//    public ListSplitter(int sizeLimit, List<Message> messages) {
//        this.sizeLimit = sizeLimit == 0 ? DEFAULT_MQ_SIZE : sizeLimit;
//        this.messages = messages;
//    }
//
//    @Override
//    public boolean hasNext() {
//        return currIndex < messages.size();
//    }
//
//    @Override
//    public List<Message> next() {
//        int nextIndex = currIndex;
//        int totalSize = 0;
//        for (; nextIndex < messages.size(); nextIndex++) {
//            Message message = messages.get(nextIndex);
//            int tmpSize = message.getTopic().length() + message.getBody().length;
//            Map<String, String> properties = message.getProperties();
//            for (Map.Entry<String, String> entry : properties.entrySet()) {
//                tmpSize += entry.getKey().length() + entry.getValue().length();
//            }
//            tmpSize = tmpSize + 20; //for log overhead
//            if (tmpSize > sizeLimit) {
//                //it is unexpected that single message exceeds the sizeLimit
//                //here just let it go, otherwise it will block the splitting process
//                if (nextIndex - currIndex == 0) {
//                    //if the next sublist has no element, add this one and then break, otherwise just break
//                    nextIndex++;
//                }
//                break;
//            }
//            if (tmpSize + totalSize > sizeLimit) {
//                break;
//            } else {
//                totalSize += tmpSize;
//            }
//
//        }
//        List<Message> subList = messages.subList(currIndex, nextIndex);
//        currIndex = nextIndex;
//        return subList;
//    }
//
//    @Override
//    public void remove() {
//        throw new UnsupportedOperationException("Not allowed to remove");
//    }
//}
