package com.epam.crm.gym;

import com.epam.crm.gym.model.Trainee;
import com.epam.crm.gym.model.UserE;
import com.epam.crm.gym.service.TraineeService;
import com.epam.crm.gym.service.TrainerService;
import com.epam.crm.gym.service.TrainingService;
import com.epam.crm.gym.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Slf4j
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

    public Trainee createTrainee(String firstname, String lastname, String address, LocalDate dob) {
        log.info(": \tmethod: createTrainee(%s, %s, %s, %s)".formatted(firstname, lastname, address, dob.toString()));
        UserE newUser = new UserE(0, firstname, lastname, "", "", false);
        newUser = userService.createUser(newUser);
        log.info(": \tcreated UserE newUser instance: " + newUser + "\tprocess on creating trainee...");
        Trainee trainee = new Trainee(0, dob, address, newUser.getId());
        log.info(": \tcreated Trainee trainee instance: " + trainee + "\tprocess on creating trainee...");
        log.info(":\tprocess on service calling to create a trainee");
        return traineeService.createTrainee(trainee);
    }

}
