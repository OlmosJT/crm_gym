package com.epam.crm.gym.dao.impl;

import com.epam.crm.gym.dao.TraineeDao;
import com.epam.crm.gym.model.Trainee;
import com.epam.crm.gym.storage.InMemoryStorage;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public class TraineeDaoImpl implements TraineeDao {
    private static long counter = 0;
    private final Map<Long, Trainee> trainees;

    public TraineeDaoImpl(InMemoryStorage inMemoryStorage) {
        this.trainees = inMemoryStorage.getTraineeMap();
    }

    @Override
    public Trainee save(Trainee trainee) {
        if(trainee.getId() == 0) {
            long newId = nextId();
            trainee.setId(newId);
        }
        trainees.put(trainee.getId(), trainee);
        return trainee;
    }

    @Override
    public Trainee get(Long id) {
        return trainees.get(id);
    }

    @Override
    public List<Trainee> findAll() {
        return trainees.values().stream().toList();
    }

    @Override
    public void delete(Long id) {
        trainees.remove(id);
    }

    private long nextId() {
        return ++counter;
    }
}
