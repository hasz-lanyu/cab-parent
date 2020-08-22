package org.cab.authen.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import org.cab.api.authen.service.UserAuthenService;
import org.cab.api.user.module.User;
import org.cab.api.user.module.UserExample;
import org.cab.authen.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public class UserAuthenServiceImpl implements UserAuthenService {
    private static Logger log = LoggerFactory.getLogger(UserAuthenServiceImpl.class);

    @Autowired
    private UserMapper userMapper;


    @Override
    public User selectUserByUserName(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(example);
        if (users.size()==1){
            return users.get(0);
        }
        return null;
    }
}
