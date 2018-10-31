/***
 * @pName management
 * @name RoomCardResp
 * @user DF
 * @date 2018/8/30
 * @desc
 */
package com.kafka.sms.entity.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomCardResp {
    /*roomCard*/
    private Integer cardId;
    private Integer userId;
    private String pandaId;
    private Integer state;
    private Date addTime;
    private Date updateTime;

    /*users*/
    private String phone;
}
