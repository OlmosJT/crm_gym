package com.epam.crm.gym.dao.impl;

import com.epam.crm.gym.dao.TrainingDao;
import com.epam.crm.gym.model.Training;
import com.epam.crm.gym.storage.InMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TrainingDaoImpl implements TrainingDao {
    private static long counter = 0;
    private final Map<Long, Training> trainings;

    public TrainingDaoImpl(InMemoryStorage inMemoryStorage) {
        trainings = inMemoryStorage.getTrainingMap();
    }

    @Override
    public Training save(Training training) {
        if(training.getId() == 0) {
            long newId = nextId();
            training.setId(newId);
        }
        trainings.put(training.getId(), training);
        return training;
    }

    @Override
    public Training get(Long id) {
        return trainings.get(id);
    }

    @Override
    public List<Training> findAll() {
        return trainings.values().stream().toList();
    }

    @Override
    public void delete(Long id) {
        trainings.remove(id);
    }

    private long nextId() {
        return ++counter;
    }
}
