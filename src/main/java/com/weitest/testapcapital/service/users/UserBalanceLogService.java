package com.weitest.testapcapital.service.users;

import com.weitest.testapcapital.mapper.users.UserBalanceLogMapper;
import com.weitest.testapcapital.model.users.UserBalanceLogPo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserBalanceLogService {
    @Resource
    private UserBalanceLogMapper userBalanceLogMapper;

    public void insertUserBalanceLog(UserBalanceLogPo userBalanceLog){
        userBalanceLogMapper.insertUserBalanceLog(userBalanceLog);
    }
    public UserBalanceLogPo findByUserIdLatest(String userId){
        return userBalanceLogMapper.findByUserIdLatest(userId);
    }
    public UserBalanceLogPo findByRecordCode(String recordCode){
        return userBalanceLogMapper.findByRecordCode(recordCode);
    }

}
