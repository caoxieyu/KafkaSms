/***
 * @pName management
 * @name BadBodyServiceImpl
 * @user DF
 * @date 2018/8/13
 * @desc
 */
package com.kafka.sms.biz.impl;

import com.kafka.sms.biz.IBadBoyService;
import com.kafka.sms.exception.MsgException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class BadBodyServiceImpl implements IBadBoyService {

    @Autowired
    private RedisTemplate redisTemplate;
    
    /**
     * 检查IP地址是否在注册黑名单中
     *
     * @param ip
     * @return
     */
    @Override
    public void isRegisterBlackList(String ip) {
        Object cacheIp = redisTemplate.opsForValue().get("short:register:ip");
        if(cacheIp != null && String.valueOf(cacheIp).equalsIgnoreCase(ip)) throw new MsgException("明天再来吧");
    }

    /**
     * 添加到注册黑名单中
     *
     * @param ip
     */
    @Override
    public void addToRegisterBlackList(String ip) {
        redisTemplate.opsForValue().set("short:register:ip", ip, 1, TimeUnit.DAYS);
    }
}
