package com.epam.crm.gym.service;


import com.epam.crm.gym.model.Trainer;

public interface TrainerService {
    // CRUD
    Trainer createTrainer(Trainer trainer);
    Trainer findTrainerById(long id);
    void updateTrainer(Trainer trainee);
    void deleteTrainerById(long id);
}
