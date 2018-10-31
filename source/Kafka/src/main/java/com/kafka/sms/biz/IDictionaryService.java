/***
 * @pName management
 * @name DictionaryService
 * @user DF
 * @date 2018/8/16
 * @desc
 */
package com.kafka.sms.biz;

import com.kafka.sms.entity.db.Dictionary;
import com.kafka.sms.entity.resp.GroupInformation;

public interface IDictionaryService extends IBaseService<Dictionary> {

    /**
     * 获取聚合广告信息 韦德 2018年8月18日13:11:46
     * @return
     */
    GroupInformation getGroupInformation();

    /**
     * 上传二维码 韦德 2018年8月31日13:31:29
     * @param url
     */
    void updateQRCode(String url);

    /**
     * 刷新本地字典 韦德 2018年8月31日13:46:02
     */
    void refresh();

    /**
     * 更新基础配置信息 韦德 2018年9月2日01:44:08
     * @param html
     * @param appMarquee
     */
    void updateConfiguration(String html, String give, String version, String iosDownload, String androidDownload, String regPackagePrice, String playAwardPrice, String joinCount, String appMarquee);
}
