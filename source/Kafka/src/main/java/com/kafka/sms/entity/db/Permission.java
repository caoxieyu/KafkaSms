/***
 * @pName management
 * @name Permission
 * @user DF
 * @date 2018/9/1
 * @desc
 */
package com.kafka.sms.entity.db;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_permission")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    @Id
    private Integer permissionId;
    private String permissionName;
    private String targetUrl;
}
