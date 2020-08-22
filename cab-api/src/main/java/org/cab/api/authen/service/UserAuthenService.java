package org.cab.api.authen.service;

import org.cab.api.user.module.User;

public interface UserAuthenService {
    User selectUserByUserName(String username);
}
