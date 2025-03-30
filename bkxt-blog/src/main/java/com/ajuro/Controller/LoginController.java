package com.ajuro.Controller;

import com.ajuro.common.dto.UserLoginDto;
import com.ajuro.common.model.vo.UserLoginVo;
import com.ajuro.common.response.ResponseResult;
import com.ajuro.common.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseResult<UserLoginVo> login(@RequestBody @Valid UserLoginDto dto) {
        UserLoginVo loginVo = loginService.login(dto);
        return ResponseResult.okResult(loginVo);
    }
    @PostMapping("/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }
}