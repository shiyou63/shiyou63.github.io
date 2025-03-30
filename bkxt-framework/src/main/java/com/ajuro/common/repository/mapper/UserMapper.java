package com.ajuro.common.repository.mapper;

import com.ajuro.common.repository.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserMapper extends BaseMapper<User> {
    List<String> selectRoles(Long userId);
}