package com.epam.crm.gym.config;

import com.epam.crm.gym.Facade;
import com.epam.crm.gym.dao.TraineeDao;
import com.epam.crm.gym.dao.TrainerDao;
import com.epam.crm.gym.dao.TrainingDao;
import com.epam.crm.gym.dao.UserEDao;
import com.epam.crm.gym.dao.impl.TraineeDaoImpl;
import com.epam.crm.gym.dao.impl.TrainerDaoImpl;
import com.epam.crm.gym.dao.impl.TrainingDaoImpl;
import com.epam.crm.gym.dao.impl.UserEDaoImpl;
import com.epam.crm.gym.service.TraineeService;
import com.epam.crm.gym.service.TrainerService;
import com.epam.crm.gym.service.TrainingService;
import com.epam.crm.gym.service.impl.TraineeServiceImpl;
import com.epam.crm.gym.service.impl.TrainerServiceImpl;
import com.epam.crm.gym.service.impl.TrainingServiceImpl;
import com.epam.crm.gym.storage.InMemoryStorage;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.epam.crm.gym")
@EnableAspectJAutoProxy
public class ApplicationConfig {
/*
    // storage bean
    @Bean
    public InMemoryStorage inMemoryStorage() {
        return new InMemoryStorage();
    }

    // Dao Beans
    @Bean
    public UserEDao userEDao(InMemoryStorage inMemoryStorage) {
        return new UserEDaoImpl(inMemoryStorage);
    }

    @Bean
    public TraineeDao traineeDao(InMemoryStorage inMemoryStorage) {
        return new TraineeDaoImpl(inMemoryStorage);
    }

    @Bean
    public TrainerDao trainerDao(InMemoryStorage inMemoryStorage) {
        return new TrainerDaoImpl(inMemoryStorage);
    }

    @Bean
    public TrainingDao trainingDao(InMemoryStorage inMemoryStorage) {
        return new TrainingDaoImpl(inMemoryStorage);
    }

    // Service Beans
    @Bean
    public TraineeService traineeService(TraineeDao traineeDao, UserEDao userEDao) {
        return new TraineeServiceImpl(traineeDao, userEDao);
    }

    @Bean
    public TrainerService trainerService(TrainerDao trainerDao, UserEDao userEDao) {
        return new TrainerServiceImpl(trainerDao, userEDao);
    }

    @Bean
    public TrainingService trainingService(TrainingDao trainingDao) {
        return new TrainingServiceImpl(trainingDao);
    }

    @Bean
    public Facade facade(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
        return new Facade(traineeService, trainerService, trainingService);
    }
*/
}
