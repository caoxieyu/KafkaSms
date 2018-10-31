/***
 * @pName management
 * @name Messages
 * @user DF
 * @date 2018/8/29
 * @desc
 */
package com.kafka.sms.entity.db;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_messages")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Messages {
    @Id
    private Integer messageId;
    private Integer userId;
    private String message;
    private Integer state;
    private Date addTime;
}
