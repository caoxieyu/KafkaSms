/***
 * @pName management
 * @name GameMemberGroupService
 * @user DF
 * @date 2018/8/19
 * @desc
 */
package com.kafka.sms.biz;

import com.kafka.sms.entity.db.GameMemberGroup;
import com.kafka.sms.entity.dbExt.GameMemberGroupDetailInfo;

import java.util.List;

public interface IGameMemberGroupService extends IBaseService<GameMemberGroup> {
    /**
     * 查询指定房间内的成员列表 韦德 2018年8月21日01:31:06
     * @param roomCode
     * @return
     */
    List<GameMemberGroup> getListByRoom(Long roomCode);

    /**
     * 查询指定房间内的成员列表 韦德 2018年8月21日01:31:06
     * @param roomCode
     * @return
     */
    List<GameMemberGroupDetailInfo> getDetailInfoListByRoom(Long roomCode);
}
