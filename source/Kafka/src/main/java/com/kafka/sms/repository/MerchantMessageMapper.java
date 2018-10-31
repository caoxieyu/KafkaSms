/***
 * @pName Kafka
 * @name BusinessMessageMapper
 * @user DF
 * @date 2018/10/23
 * @desc
 */
package com.kafka.sms.repository;

import com.kafka.sms.entity.db.MerchantMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MerchantMessageMapper extends MyMapper<MerchantMessage> {
    @Select("SELECT * FROM tb_merchant_messages WHERE parent_user_id = #{parentUserId} ORDER BY add_time DESC")
    /**
     * 查询子用户信息 韦德 2018年10月23日15:17:12
     * @param userId
     * @return
     */
    List<MerchantMessage> selectChildren(@Param("parentUserId") Integer userId);
}
