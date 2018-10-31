/***
 * @pName management
 * @name GameRoomCallbackResp
 * @user DF
 * @date 2018/8/21
 * @desc
 */
package com.kafka.sms.entity.resp;

import com.kafka.sms.entity.db.GameRoom;
import com.kafka.sms.entity.db.Settlement;
import com.kafka.sms.entity.db.Subareas;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GameRoomCallbackResp {
    private Integer userId;
    private GameRoom gameRoom;
    private Subareas subareas;
}
