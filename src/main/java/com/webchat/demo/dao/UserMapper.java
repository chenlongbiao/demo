package com.webchat.demo.dao;

import com.webchat.demo.domain.User;
import org.springframework.stereotype.Repository;

/**
 * Created by 陈龙飚 on 2017/7/11 0011.
 */
@Repository
public interface UserMapper {
    User selectUserByName(String username);

    /**
     * 注册用户
     * @param user
     * @return
     */
    Integer signUp(User user);

    /**
     * 获取用户信息
     *
     */
    User selectUserByNAP(User user);
}
