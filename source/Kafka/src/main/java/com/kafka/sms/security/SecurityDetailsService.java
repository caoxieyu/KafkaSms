/***
 * @pName mi-ocr-web-app
 * @name UserDetailsServiceEx
 * @user DF
 * @date 2018/7/22
 * @desc
 */
package com.kafka.sms.security;


import com.google.common.base.Joiner;
import com.kafka.sms.biz.IPermissionRelationService;
import com.kafka.sms.biz.IUserService;
import com.kafka.sms.entity.Constant;
import com.kafka.sms.entity.db.PermissionRelation;
import com.kafka.sms.entity.db.Users;
import com.kafka.sms.exception.MsgException;
import com.kafka.sms.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class SecurityDetailsService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IPermissionRelationService permissionRelationService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // 通过用户名查询此用户是否为合法用户
        String ip = RequestUtil.getIp(ServletUtil.getRequest());
        Users param = new Users();
        param.setPhone(username);
        param.setIp(ip);
        Users detail = userService.login(param);
        // 清除缓存
        try{
            redisTemplate.delete("accountAmount_" + Constant.SYSTEM_ACCOUNTS_ID);
        }catch (Exception e){
            System.out.println("清除缓存失败！");
        }

        // 查询权限
        List<PermissionRelation> permissionRelationList = permissionRelationService.getListByUid(detail.getUserId());

        if(permissionRelationList == null || permissionRelationList.isEmpty()) throw new UsernameNotFoundException("您无权访问系统，请向有关部分申请工号！");

        List<String> permissionRoleList = permissionRelationList.stream().map(permissionRelation -> permissionRelation.getPermissionRole()).collect(Collectors.toList());

        permissionRoleList = StringUtil.removeDuplicate(permissionRoleList);

        String authStrings = Joiner.on(",").join(permissionRoleList);

        return new SecurityUserDetails(detail, detail.getPhone(), detail.getPhone(), detail.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(authStrings));
    }


}
