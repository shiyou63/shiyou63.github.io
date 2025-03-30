package com.ajuro.common.service.Impl;

import com.ajuro.common.repository.entity.User;
import com.ajuro.common.repository.mapper.UserMapper;
import com.ajuro.common.security.domain.LoginUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户基础信息
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUserName, username));
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 查询权限（示例：直接从用户表类型字段获取）
        String permission = "admin".equals(user.getType()) ? "ADMIN" : "USER";

        return new LoginUser(
                user,
                Collections.singletonList(new SimpleGrantedAuthority(permission))
        );
    }
}
