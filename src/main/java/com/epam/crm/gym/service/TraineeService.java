package com.epam.crm.gym.service;

import com.epam.crm.gym.model.Trainee;

public interface TraineeService {
    // CRUD
    Trainee createTrainee(Trainee trainee);
    Trainee findTraineeById(long id);
    void updateTrainee(Trainee trainee);
    void deleteTraineeById(long id);
}
