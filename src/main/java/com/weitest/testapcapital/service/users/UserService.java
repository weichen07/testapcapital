package com.weitest.testapcapital.service.users;

import com.weitest.testapcapital.mapper.users.UserMapper;
import com.weitest.testapcapital.model.users.UserBalanceLogPo;
import com.weitest.testapcapital.model.users.UserPo;
import com.weitest.testapcapital.util.EncodeGenerateUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
@Slf4j
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserBalanceLogService userBalanceLogService;

    public void updateBalance(String userId, BigDecimal amount) {
        userMapper.updateBalance(userId, amount);
    }

    @Transactional(rollbackFor = Exception.class)
    public void rechargeBalance(String userId, BigDecimal amount) {
        String recordCode = EncodeGenerateUtil.getRecordCode(EncodeGenerateUtil.USER_RECHARFE,Integer.parseInt(userId));
        UserBalanceLogPo userBalanceLog = new UserBalanceLogPo();
        userBalanceLog.setAmount(amount);
        userBalanceLog.setRecordCode(recordCode);
        userBalanceLog.setUserId(userId);
        updateBalance(userId, amount);
        userBalanceLogService.insertUserBalanceLog(userBalanceLog);
    }

    public UserPo findByUserId(String userId){
        return userMapper.findByUserId(userId);
    }



}
