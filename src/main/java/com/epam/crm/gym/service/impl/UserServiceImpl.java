package com.epam.crm.gym.service.impl;

import com.epam.crm.gym.dao.UserEDao;
import com.epam.crm.gym.exception.NotFoundException;
import com.epam.crm.gym.model.Trainee;
import com.epam.crm.gym.model.UserE;
import com.epam.crm.gym.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserEDao userEDao;

    public UserServiceImpl(UserEDao userEDao) {
        this.userEDao = userEDao;
    }

    @Override
    public UserE createUser(UserE user) {
        return userEDao.save(user);
    }

    @Override
    public UserE findUserEById(long id) throws NotFoundException {
        UserE user = userEDao.get(id);
        if(user == null) {
            throw new NotFoundException("User not found by id: " + id);
        }
        return user;
    }

    @Override
    public void updateUserE(UserE user) throws NotFoundException {
        if(userEDao.get(user.getId()) == null) {
            throw new NotFoundException("User record not exist by id: " + user.getId());
        }
        userEDao.save(user);
    }

    @Override
    public void deleteUserEById(long id) {
        userEDao.delete(id);
    }
}
