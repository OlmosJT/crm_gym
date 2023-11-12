package com.epam.crm.gym;

import com.epam.crm.gym.model.Trainee;
import com.epam.crm.gym.model.UserE;
import com.epam.crm.gym.service.TraineeService;
import com.epam.crm.gym.service.TrainerService;
import com.epam.crm.gym.service.TrainingService;
import com.epam.crm.gym.service.UserService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Facade {
    private final TraineeService  traineeService;
    private final TrainerService  trainerService;
    private final TrainingService trainingService;
    private final UserService userService;



    public Facade(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService, UserService userService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
        this.userService = userService;
    }

    public Trainee createTrainee(String firstname, String lastname, String address, LocalDateTime dob) {
        UserE newUser = new UserE(0, firstname, lastname, "", "", false);
        newUser = userService.createUser(newUser);
        Trainee trainee = new Trainee(0, dob, address, newUser.getId());
        return traineeService.createTrainee(trainee);
    }

}
