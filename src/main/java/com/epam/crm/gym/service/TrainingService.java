package com.epam.crm.gym.service;


import com.epam.crm.gym.model.Training;

public interface TrainingService {
    // CRUD
    Training createTraining(Training training);
    Training findTrainingById(long id);

}
