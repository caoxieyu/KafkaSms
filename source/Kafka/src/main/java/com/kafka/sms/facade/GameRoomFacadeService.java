/***
 * @pName management
 * @name GameRoomFacadeService
 * @user DF
 * @date 2018/8/21
 * @desc
 */
package com.kafka.sms.facade;

import java.util.function.Consumer;

public interface GameRoomFacadeService {
    /**
     * 结算 韦德 2018年8月21日01:02:27
     * @param token
     * @param roomCode
     * @param grade
     */
    void  closeAccounts(String token, Long roomCode, Double grade);

    /**
     * 结算 韦德 2018年8月29日20:53:36
     * @param roomCode
     */
    void  closeAccounts(Long roomCode);

    /**
     * 强制结算 韦德 2018年9月2日12:56:44
     * @param roomCode
     */
    void executeCloseAccounts(Long roomCode);

    /**
     * 修改成绩 韦德 2018年8月29日20:31:57
     * @param userId
     * @param grade
     * @param roomCode
     */
    void editGrade(Integer userId, Double grade, Long roomCode);
}