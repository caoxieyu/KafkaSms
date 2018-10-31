/***
 * @pName management
 * @name ExceptionServiceImpl
 * @user DF
 * @date 2018/8/13
 * @desc
 */
package com.kafka.sms.biz.impl;

import com.kafka.sms.biz.IExceptionService;
import com.kafka.sms.entity.db.Exceptions;
import com.kafka.sms.repository.ExceptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ExceptionServiceImpl extends BaseServiceImpl<Exceptions> implements IExceptionService {
    private final ExceptionMapper exceptionMapper;

    @Autowired
    public ExceptionServiceImpl(ExceptionMapper exceptionMapper) {
        this.exceptionMapper = exceptionMapper;
    }


    /**
     * 异步插入记录 韦德 2018年8月13日13:33:29
     *
     * @param exceptions
     * @return
     */
    @Override
    @Async
    public void asyncInsert(Exceptions exceptions) {
        exceptionMapper.insert(exceptions);
    }
}
