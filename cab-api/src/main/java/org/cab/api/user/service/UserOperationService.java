package org.cab.api.user.service;

import org.cab.api.user.module.User;
import org.cab.api.user.module.UserPageInfo;

import java.util.List;

public interface UserOperationService {
    void updateLoginTimeById(Long id);

    void addUser(User user);

    User selectUserByUserName(String username);

    List<User> selectAll();

    UserPageInfo selectUserByPage(Integer pageNumber, Integer pageSize, String key);

    void updateUserById(User user);

    void deleteUserById(Long id);
}
