package com.epam.crm.gym.dao.impl;

import com.epam.crm.gym.dao.TrainerDao;
import com.epam.crm.gym.model.Trainer;
import com.epam.crm.gym.storage.InMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TrainerDaoImpl implements TrainerDao {
    private static int counter = 0;
    private final Map<Long, Trainer> trainers;

    public TrainerDaoImpl(InMemoryStorage inMemoryStorage) {
        trainers = inMemoryStorage.getTrainerMap();
    }

    @Override
    public Trainer save(Trainer trainer) {
        if(trainer.getId() == 0){
            long newId = nextId();
            trainer.setId(newId);
        }
        trainers.put(trainer.getId(), trainer);
        return trainer;
    }

    @Override
    public Trainer get(Long id) {
        return trainers.get(id);
    }

    @Override
    public List<Trainer> findAll() {
        return trainers.values().stream().toList();
    }

    @Override
    public void delete(Long id) {
        trainers.remove(id);
    }

    private long nextId() {
        return ++counter;
    }
}
