package com.ajuro.common.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVo {

    private String token;        // JWT令牌
    private UserInfoVo userInfo; // 用户信息
}