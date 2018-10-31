/***
 * @pName management
 * @name RoomCardDetailInfo
 * @user DF
 * @date 2018/8/30
 * @desc
 */
package com.kafka.sms.entity.dbExt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomCardDetailInfo {
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
