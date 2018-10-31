/***
 * @pName management
 * @name MemberController
 * @user DF
 * @date 2018/8/31
 * @desc
 */
package com.kafka.sms.controller;

import com.kafka.sms.biz.IPermissionRelationService;
import com.kafka.sms.biz.IUserService;
import com.kafka.sms.entity.JsonArrayResult;
import com.kafka.sms.entity.JsonResult;
import com.kafka.sms.entity.db.Users;
import com.kafka.sms.entity.dbExt.UserDetailInfo;
import com.kafka.sms.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/management/member")
public class MemberController extends BaseController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IPermissionRelationService permissionRelationService;

    @GetMapping("/index")
    public String  index(){
        return "member/index";
    }

    /**
     * 会员列表 韦德 2018年8月29日11:42:31
     * @return
     */
    @GetMapping("/getMemberLimit")
    @ResponseBody
    public JsonArrayResult<UserDetailInfo> getMemberLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime){
        Integer count = 0;
        List<UserDetailInfo> list = userService.getLimit(page, limit, condition, state, beginTime, endTime);
        JsonArrayResult jsonArrayResult = new JsonArrayResult(0, list);
        if (StringUtil.isBlank(condition)
                && StringUtil.isBlank(beginTime)
                && StringUtil.isBlank(endTime)
                && (state == null || state == 0)){
            count = userService.getCount();
        }else{
            count = userService.getLimitCount(condition, state, beginTime, endTime);
        }
        jsonArrayResult.setCount(count);
        return jsonArrayResult;
    }

    /**
     * 更新密码
     * @return
     */
    @PostMapping("/updatePassword")
    @ResponseBody
    public JsonResult updatePassword(Users users){
        userService.updatePassword(users);
        return JsonResult.successful();
    }

    /**
     * 删除用户
     * @return
     */
    @PostMapping("/updateAvailability")
    @ResponseBody
    public JsonResult updateAvailability(Users users){
        userService.updateAvailability(users);
        return JsonResult.successful();
    }


    /**
     * 冻结
     * @return
     */
    @PostMapping("/updateEnable")
    @ResponseBody
    public JsonResult updateEnable(Users users){
        userService.updateEnable(users);
        return JsonResult.successful();
    }


    /**
     * 设置权限
     * @param userId
     * @param roleName
     * @return
     */
    @PostMapping("/changeRole")
    @ResponseBody
    public JsonResult changeRole(Integer userId, String roleName){
        permissionRelationService.replace(userId, roleName);
        return JsonResult.successful();
    }


    /**
     * 设置代理商
     * @param userId
     * @return
     */
    @PostMapping("/setMerchant")
    @ResponseBody
    public JsonResult setMerchant(Integer userId){
        userService.setMerchant(userId);
        return JsonResult.successful();
    }
}
