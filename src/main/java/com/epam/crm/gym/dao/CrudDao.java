package com.epam.crm.gym.dao;

import java.util.List;

public interface CrudDao<T, ID> {
    T save(T entity);
    T get(ID id);
    List<T> findAll();
    void delete(ID id);
}
