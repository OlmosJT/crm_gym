package com.epam.crm.gym.dao.impl;

import com.epam.crm.gym.aspect.Loggable;
import com.epam.crm.gym.dao.UserEDao;
import com.epam.crm.gym.model.UserE;
import com.epam.crm.gym.storage.InMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserEDaoImpl implements UserEDao {
    private static long counter = 0;
    private final Map<Long, UserE> users;


    public UserEDaoImpl(InMemoryStorage inMemoryStorage) {
        users = inMemoryStorage.getUserEMap();
    }

    @Override
    public UserE save(UserE user) {
        if(user.getId() == 0) {
            long newId = nextId();
            user.setId(newId);
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public UserE get(Long id) {
        return users.get(id);
    }

    @Override
    public List<UserE> findAll() {
        return users.values().stream().toList();
    }

    @Override
    public void delete(Long id) {
        users.remove(id);
    }

    private long nextId() {
        return ++counter;
    }

    @Override
    public boolean existByUsername(String username) {
        return users.values().stream().anyMatch(userE -> userE.getUsername().equals(username));
    }
}
