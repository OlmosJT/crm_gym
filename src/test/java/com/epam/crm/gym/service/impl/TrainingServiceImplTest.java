package com.epam.crm.gym.service.impl;

import com.epam.crm.gym.dao.TrainingDao;
import com.epam.crm.gym.dao.impl.TrainingDaoImpl;
import com.epam.crm.gym.model.Training;
import com.epam.crm.gym.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import static org.mockito.Mockito.times;

class TrainingServiceImplTest {

    TrainingDao trainingDao;

    TrainingService trainingService;

    @BeforeEach
    public void setUp() {
        trainingDao = Mockito.mock(TrainingDaoImpl.class);

        trainingService = new TrainingServiceImpl(trainingDao);
    }

    @Test
    void createTraining() {
        Training training = new Training(
                2L,
                9L,
                36L,
                "Powerlift gym",
                3L,
                LocalDateTime.now(),
                96
        );

        Mockito.when(trainingDao.save(training)).thenReturn(training);

        trainingService.createTraining(training);

        Mockito.verify(trainingDao, times(1)).save(training);
    }

    @Test
    void findTrainingById() {
    }
}