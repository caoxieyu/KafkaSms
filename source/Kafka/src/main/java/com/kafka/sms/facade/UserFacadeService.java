/***
 * @pName management
 * @name UserFacadeService
 * @user DF
 * @date 2018/8/20
 * @desc
 */
package com.kafka.sms.facade;

import com.kafka.sms.entity.db.Users;

public interface UserFacadeService {
    /**
     * 注册用户 韦德 2018年8月20日15:39:37
     * @param param
     * @return
     */
    boolean register(Users param);
}
