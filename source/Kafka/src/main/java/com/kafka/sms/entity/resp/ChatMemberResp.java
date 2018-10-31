/***
 * @pName management
 * @name ChatMemberResp
 * @user DF
 * @date 2018/8/23
 * @desc
 */
package com.kafka.sms.entity.resp;

import com.kafka.sms.entity.db.GameMemberGroup;
import io.netty.channel.Channel;
import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatMemberResp {
    private GameMemberGroup gameMemberGroup;
    private Channel channel;
}
