/***
 * @pName management
 * @name SubareaRelationMapper
 * @user DF
 * @date 2018/8/18
 * @desc
 */
package com.kafka.sms.repository;

import com.kafka.sms.entity.db.SubareaRelations;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SubareaRelationMapper extends MyMapper<SubareaRelations> {
}
