/***
 * @pName Kafka
 * @name InviteController
 * @user DF
 * @date 2018/10/12
 * @desc
 */
package com.kafka.sms.controller;

import com.kafka.sms.biz.IUserService;
import com.kafka.sms.entity.Constant;
import com.kafka.sms.entity.resp.UserResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/invite")
public class InviteController {
    @Autowired
    private IUserService userService;

    @GetMapping(value = {"/index", ""})
    public String index(Integer code, final  RedirectAttributes redirectAttributes, final Model model){
        if(code == null || code <= 0) {
            redirectAttributes.addAttribute("errorText", "参数不完整");
            return "redirect:/errorPage";
        }

        // 核验邀请码是否有效
        UserResp user = userService.getUserById(code);
        if(user == null){
            redirectAttributes.addAttribute("errorText", "无效的邀请码");
            return "redirect:/errorPage";
        }

        model.addAttribute("code", code);
        model.addAttribute("inviteCode", "邀请码：000" + code);
        return "invite/index";
    }
}
