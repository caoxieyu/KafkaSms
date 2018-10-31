/***
 * @pName management
 * @name ExceptionMapper
 * @user DF
 * @date 2018/8/13
 * @desc
 */
package com.kafka.sms.repository;

import com.kafka.sms.entity.db.Exceptions;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExceptionMapper extends MyMapper<Exceptions> {
    @Insert("INSERT INTO tb_exceptions(`body`,`add_time`) VALUES(#{body},NOW())")
    /**
     * 插入记录 韦德 2018年8月16日16:33:19
     */
    int insert(Exceptions exceptions);
}
