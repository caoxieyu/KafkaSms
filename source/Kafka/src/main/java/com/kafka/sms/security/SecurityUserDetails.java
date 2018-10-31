/***
 * @pName mi-ocr-web-app
 * @name UserDetailsEx
 * @user DF
 * @date 2018/7/28
 * @desc
 */
package com.kafka.sms.security;

import com.kafka.sms.entity.db.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SecurityUserDetails extends User {
    private String salt;
    private Users detail;

    public Users getDetail() {
        return detail;
    }

    public void setDetail(Users detail) {
        this.detail = detail;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public SecurityUserDetails(Users detail, String username, String salt, String password,
                               Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.detail = detail;
        this.salt = salt;
    }
}
