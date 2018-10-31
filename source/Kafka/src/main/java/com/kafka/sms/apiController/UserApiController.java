/***
 * @pName Kafka
 * @name UserApiController
 * @user HongWei
 * @date 2018/10/31
 * @desc
 */
package com.kafka.sms.apiController;

import com.kafka.sms.entity.JsonResult;
import com.kafka.sms.entity.resp.PhoneResp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/user")
public class UserApiController {

    /**
     * 获取手机号接口 2018年10月31日21:59:18
     * @param projects 项目ID, 格式: 10000,10001,10002
     * @param provinces 省份(可空,缺省全国) 510000,220000
     * @param citys 城市(可空,缺省全省) 110105,110109
     * @param numberType 号码类型, 0=不限运营商\1=移动\2=联通\3=电信\4=国际\5=虚拟\6=非虚拟; 130-189是指定号段
     * @param quantity 单次取号数量(缺省为1), 最大支持单次取号数量为100
     * @param appKey 作者对接应用唯一密匙(可空, 缺省为系统账户计算利润)
     * @param privateAppKey 私有应用密匙(可空), 相当于密码, 只有知道的人才能使用
     * @param merchantId 商家id(可空), 用户选择收取指定商户的号码
     * @return
     */
    @PostMapping("getPhone")
    public JsonResult<PhoneResp> getPhone(@RequestParam(value = "projects", required = true) @NotNull String projects
            , @RequestParam(value = "provinces", defaultValue = "000000", required = false) String provinces
            , @RequestParam(value = "citys", defaultValue = "000000", required = false) String citys
            , @RequestParam(value = "numberType", defaultValue = "0", required = true) Integer numberType
            , @RequestParam(value = "quantity", defaultValue = "1", required = true) Integer quantity
            , String appKey, String privateAppKey, String merchantId){
        PhoneResp response = new PhoneResp();
        return new JsonResult<>().successful(response);
    }
}
