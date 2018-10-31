/***
 * @pName management
 * @name GameMemberGroup
 * @user DF
 * @date 2018/8/19
 * @desc
 */
package com.kafka.sms.entity.db;

import lombok.*;

import javax.persistence.Table;
import java.util.Date;


@Table(name = "tb_game_member_group")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GameMemberGroup {
    private Integer groupId;
    private Long roomCode;
    private Integer userId;
    private Integer isOwner;
    private Integer isConfirm;
    private Date addTime;
    private Date exitTime;
}
