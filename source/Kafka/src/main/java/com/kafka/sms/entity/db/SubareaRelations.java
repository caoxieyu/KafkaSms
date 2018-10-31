/***
 * @pName management
 * @name SubareaRelations
 * @user DF
 * @date 2018/8/18
 * @desc
 */
package com.kafka.sms.entity.db;

import lombok.*;

import javax.persistence.Table;

@Table(name = "tb_subarea_relations")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SubareaRelations {
    private Integer relationId;
    private Integer subareaId;
    private Integer joinSubareaId;
}
