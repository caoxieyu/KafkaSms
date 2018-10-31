/***
 * @pName management
 * @name FinanceFacadeService
 * @user DF
 * @date 2018/8/21
 * @desc
 */
package com.kafka.sms.facade;

import com.kafka.sms.entity.db.Recharge;
import com.kafka.sms.entity.db.Withdraw;

public interface FinanceFacadeService {

    /**
     * 申请提现 韦德 2018年8月21日10:57:26
     *
     * @param token
     * @param amount
     */
    void addWithdraw(String token, Double amount );


    /**
     * 提现审批 韦德 2018年8月21日10:42:23
     * @return
     */
    boolean confirmWithdraw(Withdraw withdraw);


    /**
     * 充值审批 韦德 2018年8月21日10:42:23
     * @return
     */
    boolean confirmRecharge(Recharge recharge);

    /**
     * 充值 韦德 2018年8月31日18:14:50
     * @param token
     * @param amount
     */
    void addRecharge(String token, Double amount);

}
