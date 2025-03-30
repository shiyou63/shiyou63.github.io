package com.ajuro.common.service.Impl;

import com.ajuro.common.dto.UserLoginDto;
import com.ajuro.common.model.vo.UserLoginVo;
import com.ajuro.common.model.vo.UserInfoVo;
import com.ajuro.common.response.ResponseResult;
import com.ajuro.common.security.domain.LoginUser;
import com.ajuro.common.service.LoginService;
import com.ajuro.common.utils.BeanCopyUtils;
import com.ajuro.common.utils.JwtUtil;
import com.ajuro.common.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    public UserLoginVo login(UserLoginDto dto) {
        // 执行认证
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        // 生成JWT
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();

        // 缓存权限（示例缓存用户类型）
        String jwt = JwtUtil.createJWT(userId);

        //下面那行的第一个参数: 把上面那行的jwt，也就是token值保存到Redis。存到时候是键值对的形式，值就是jwt，key要加上 "bloglogin:" 前缀
        //下面那行的第二个参数: 要把哪个对象存入Redis。我们写的是loginUser，里面有权限信息，后面会用到
        redisCache.setCacheObject("bloglogin:"+userId,loginUser);

        // 返回数据
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        return new UserLoginVo(jwt, userInfoVo);
    }

    @Override
    public ResponseResult logout() {

        //获取token，然后解析token值获取其中的userid。SecurityContextHolder是security官方提供的类
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //LoginUser是我们写的类
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        //获取userid
        Long userid = loginUser.getUser().getId();

        //在redis根据key来删除用户的value值，注意之前我们在存key的时候，key是加了'bloglogin:'前缀
        redisCache.deleteObject("bloglogin:"+userid);
        //封装响应返回
        return ResponseResult.okResult();
    }
}
