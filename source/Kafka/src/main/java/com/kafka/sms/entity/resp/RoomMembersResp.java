/***
 * @pName Kafka
 * @name RoomMembersResp
 * @user DF
 * @date 2018/10/13
 * @desc
 */
package com.kafka.sms.entity.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomMembersResp {
    private Integer count;
    private Integer persons;
}
