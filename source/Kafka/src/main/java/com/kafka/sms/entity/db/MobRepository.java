/***
 * @pName Kafka
 * @name User
 * @user HongWei
 * @date 2018/10/31
 * @desc
 */
package com.kafka.sms.entity.db;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_mob_repository")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MobRepository {
    /**
     * 商品ID
     */
    private Integer mobId ;

    /**
     * 项目ID
     */
    private Integer projectId ;

    /**
     * 卖家ID
     */
    private Integer merchantId ;

    /**
     * 手机号码
     */
    private String phone ;

    /**
     * 运营商(0=移动,1=联通,2=电信)
     */
    private Integer type ;

    /**
     * 登记时间
     */
    private Date add_date ;

    /**
     * 归属地
     */
    private String area_id ;

    /**
     * 短信内容
     */
    private String content ;

    /**
     * 号码状态(0=正常,1=已锁定)
     */
    private Integer status ;
}
