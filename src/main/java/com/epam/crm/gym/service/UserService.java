package com.epam.crm.gym.service;

import com.epam.crm.gym.model.UserE;

public interface UserService {
    UserE createUser(UserE user);
    UserE findUserEById(long id);
    void updateUserE(UserE user);
    void deleteUserEById(long id);
}
