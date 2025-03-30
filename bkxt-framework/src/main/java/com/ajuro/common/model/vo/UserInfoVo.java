package com.ajuro.common.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserInfoVo {
    private Long id;
    private String nickName;    // 显示名称（可修改）
    private String userName;     // 登录账号（唯一，不可修改）
    private String email;         //邮箱

    private String phonenumber;       // 手机号
    private String signature;   // 个性签名
    private Integer postCount;  // 发帖数
    private String avatar;      // 头像URL
    private String sex;         // 性别（男/女/未知）
}