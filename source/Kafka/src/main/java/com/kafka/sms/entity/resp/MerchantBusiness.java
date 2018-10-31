/***
 * @pName Kafka
 * @name MerchantBusiness
 * @user DF
 * @date 2018/10/23
 * @desc
 */
package com.kafka.sms.entity.resp;

import com.kafka.sms.entity.db.MerchantMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MerchantBusiness {
    private Double regiIncome;
    private Double returnIncome;
    private List<MerchantMessage> merchantMessageList;
    private Integer friends;
}
