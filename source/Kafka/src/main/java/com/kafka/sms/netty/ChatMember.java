/***
 * @pName management
 * @name ChatRoom
 * @user DF
 * @date 2018/8/23
 * @desc
 */
package com.kafka.sms.netty;

import com.kafka.sms.entity.db.GameMemberGroup;
import com.kafka.sms.entity.resp.ChatMemberResp;
import lombok.*;

import io.netty.channel.Channel;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatMember {
    private String senderId;
    private String roomCode;
    private List<GameMemberGroup> receiveList;
    private Channel channel;
}
