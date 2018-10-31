/***
 * @pName management
 * @name BootstrapController
 * @user DF
 * @date 2018/8/26
 * @desc
 */
package com.kafka.sms.controller;

import com.kafka.sms.biz.IUserService;
import com.kafka.sms.entity.JsonResult;
import com.kafka.sms.entity.db.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/management/bootstrap")
public class BootstrapController {

    @GetMapping("/login")
    public String login(){
        return "bootstrap/index";
    }
}
