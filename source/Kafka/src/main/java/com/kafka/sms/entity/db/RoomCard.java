/***
 * @pName management
 * @name RoomCard
 * @user DF
 * @date 2018/8/20
 * @desc
 */
package com.kafka.sms.entity.db;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_room_card")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoomCard {
    @Id
    private Integer cardId;
    private Integer userId;
    private String pandaId;
    private Integer state;
    private Date addTime;
    private Date updateTime;
}
