package com.epam.crm.gym.service.impl;

import com.epam.crm.gym.aspect.Loggable;
import com.epam.crm.gym.dao.TrainingDao;
import com.epam.crm.gym.exception.NotFoundException;
import com.epam.crm.gym.model.Training;
import com.epam.crm.gym.service.TrainingService;
import org.springframework.stereotype.Service;

@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingDao trainingDao;

    public TrainingServiceImpl(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }

    @Override
    @Loggable
    public Training createTraining(Training training) {
        return trainingDao.save(training);
    }

    @Override
    @Loggable
    public Training findTrainingById(long id) throws NotFoundException {
        Training training = trainingDao.get(id);
        if(training == null) {
            throw new NotFoundException("Absent Training id: " + id);
        }
        return training;
    }
}
