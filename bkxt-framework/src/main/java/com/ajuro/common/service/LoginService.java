package com.ajuro.common.service;

import com.ajuro.common.model.vo.UserLoginVo;
import com.ajuro.common.dto.UserLoginDto;
import com.ajuro.common.response.ResponseResult;

public interface LoginService {
    UserLoginVo login(UserLoginDto dto);
    //退出登录
    ResponseResult logout();
}
