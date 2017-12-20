package com.webchat.demo.service.impl;

import com.webchat.demo.dao.UserMapper;
import com.webchat.demo.domain.User;
import com.webchat.demo.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 陈龙飚 on 2017/7/11 0011.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User selectUserByName(String name) {
        return userMapper.selectUserByName(name);
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     */
    @Override
    public User selectUserByNAP(String username, String password) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        User user1 = userMapper.selectUserByNAP(user);
        return user1;
    }

    @Override
    public String signUp(User user) throws Exception {
        String success=userMapper.signUp(user).toString();
        return success;
    }
}
