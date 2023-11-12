package com.epam.crm.gym.dao;

import com.epam.crm.gym.model.UserE;

public interface UserEDao extends CrudDao<UserE, Long> {

    boolean existByUsername(String username);
}
