package com.epam.crm.gym.dao.impl;

import com.epam.crm.gym.dao.TrainingTypeDao;
import com.epam.crm.gym.model.TrainingType;
import com.epam.crm.gym.storage.InMemoryStorage;

import java.util.List;
import java.util.Map;

public class TrainingTypeDaoImpl implements TrainingTypeDao {
    private static long counter = 0;
    private final Map<Long, TrainingType> trainingTypes;

    public TrainingTypeDaoImpl(InMemoryStorage inMemoryStorage) {
        trainingTypes = inMemoryStorage.getTrainingTypeMap();
    }

    @Override
    public TrainingType save(TrainingType trainingType) {
        if(trainingType.getId() == 0){
            long newId = nextId();
            trainingType.setId(newId);
        }
        trainingTypes.put(trainingType.getId(), trainingType);
        return null;
    }

    @Override
    public TrainingType get(Long id) {
        return trainingTypes.get(id);
    }

    @Override
    public List<TrainingType> findAll() {
        return trainingTypes.values().stream().toList();
    }

    @Override
    public void delete(Long id) {
        trainingTypes.remove(id);
    }

    private long nextId() {
        return ++counter;
    }
}
