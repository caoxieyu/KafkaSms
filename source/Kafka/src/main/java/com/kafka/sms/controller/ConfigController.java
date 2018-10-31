/***
 * @pName management
 * @name ConfigController
 * @user DF
 * @date 2018/8/31
 * @desc
 */
package com.kafka.sms.controller;

import com.kafka.sms.biz.IDictionaryService;
import com.kafka.sms.entity.DataDictionary;
import com.kafka.sms.entity.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/management/config")
public class ConfigController extends BaseController {
    @Autowired
    private IDictionaryService dictionaryService;

    @GetMapping("/pay")
    public String pay(final Model model){
        String qrcode = "/images/upload/" + DataDictionary.DATA_DICTIONARY.get("finance.pays.qrcode.image").getValue();
        if(qrcode == null || qrcode.equalsIgnoreCase("#")) qrcode = "/images/qcode_notFound.png";
        model.addAttribute("qrcode", qrcode);
        return "config/pay";
    }

    @GetMapping("/updateQRCode")
    @ResponseBody
    public JsonResult updateQRCode(String url){
        dictionaryService.updateQRCode(url);
        dictionaryService.refresh();
        return JsonResult.successful();
    }

    @GetMapping("/index")
    public String configuration(final Model model){
        model.addAttribute("consumeServiceHtml", dictionaryService.getGroupInformation().getConsumeServiceHtml());
        model.addAttribute("newUserGive", dictionaryService.getGroupInformation().getGiveAmount());
        model.addAttribute("version", dictionaryService.getGroupInformation().getVersion());
        model.addAttribute("ios", dictionaryService.getGroupInformation().getIosDownload());
        model.addAttribute("android", dictionaryService.getGroupInformation().getAndroidDownload());
        model.addAttribute("regPackagePrice", dictionaryService.getGroupInformation().getRegPackagePrice());
        model.addAttribute("playAwardPrice", dictionaryService.getGroupInformation().getPlayAwardPrice());
        model.addAttribute("joinCount", dictionaryService.getGroupInformation().getJoinCount());
        model.addAttribute("appMarquee", dictionaryService.getGroupInformation().getAppMarquee());
        return "config/index";
    }

    @PostMapping("/updateConfiguration")
    @ResponseBody
    public JsonResult updateConfiguration(String html, String giveAmount, String version, String iosDownload, String androidDownload , String regPackagePrice , String playAwardPrice
            , String joinCount , String appMarquee){
        dictionaryService.updateConfiguration(html, giveAmount, version, iosDownload, androidDownload, regPackagePrice, playAwardPrice, joinCount, appMarquee);
        dictionaryService.refresh();
        return JsonResult.successful();
    }
}