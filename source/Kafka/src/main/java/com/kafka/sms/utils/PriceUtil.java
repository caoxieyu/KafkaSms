/***
 * @pName proback
 * @name PriceUtil
 * @user DF
 * @date 2018/8/8
 * @desc
 */
package com.kafka.sms.utils;

public class PriceUtil {
    public static Double getSellPrice(Double price, Integer rate){
        return price * (100 + rate) / 100;
    }
}
