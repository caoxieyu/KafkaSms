/***
 * @pName Kafka
 * @name ErrorController
 * @user DF
 * @date 2018/10/12
 * @desc
 */
package com.kafka.sms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/errorPage")
public class ErrorPageController {

    @GetMapping(value = {"/index", ""})
    public String index(String errorText, final Model model){
        if(errorText == null) errorText = "系统拒绝了您的请求";
        model.addAttribute("errorText", errorText);
        return "errorPage/index";
    }
}
