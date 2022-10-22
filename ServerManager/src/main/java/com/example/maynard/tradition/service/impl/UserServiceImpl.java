package com.example.maynard.tradition.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.maynard.tradition.entity.*;
import com.example.maynard.tradition.mapper.*;
import com.example.maynard.tradition.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

/**
 * <pre>
 *     用户业务逻辑实现类
 * </pre>
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public void saveByUser(User user) {
        if (user != null && user.getUserId() != null) {
            userMapper.updateById(user);
        } else {
            userMapper.insert(user);
        }
    }

    @Override
    public User findByUserName(String userName) {
        User param = new User();
        param.setUserName(userName);
        return userMapper.selectOne(param);
    }

    @Override
    public User findByUserId(Long userId) {
        return userMapper.selectById(userId);
    }


    @Override
    public void removeUser(Long userId) {
        userMapper.deleteById(userId);
    }

    @Override
    public Page<User> findAll(Page<User> page) {
        return page.setRecords(userMapper.findAll(page));
    }

}
