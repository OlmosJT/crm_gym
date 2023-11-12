package com.epam.crm.gym.service.impl;

import com.epam.crm.gym.dao.TrainerDao;
import com.epam.crm.gym.dao.UserEDao;
import com.epam.crm.gym.dao.impl.TrainerDaoImpl;
import com.epam.crm.gym.exception.NotFoundException;
import com.epam.crm.gym.model.Trainee;
import com.epam.crm.gym.model.Trainer;
import com.epam.crm.gym.model.UserE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

class TrainerServiceImplTest {
    TrainerDao trainerDao;
    UserEDao userDao;
    TrainerServiceImpl trainerService;

    @BeforeEach
    public void setUp() {
        trainerDao = Mockito.mock(TrainerDaoImpl.class);
        userDao = Mockito.mock(UserEDao.class);
        trainerService = new TrainerServiceImpl(trainerDao, userDao);
    }


    @Test
    void createTrainer_success() {
        Trainer trainer = new Trainer(10L, List.of(1L,2L), 12L);
        UserE user = new UserE(
                12L,
                "Jason",
                "Dima",
                "Jason.Dima",
                "password123",
                true
        );

        Mockito.when(userDao.get(Mockito.anyLong())).thenReturn(user);
        Mockito.when(userDao.existByUsername(Mockito.anyString())).thenReturn(false);

        Mockito.when(trainerDao.save(trainer)).thenReturn(trainer);

        Trainer createdTrainer = trainerService.createTrainer(trainer);
        Mockito.verify(userDao, times(1)).get(trainer.getUserId());
        Mockito.verify(userDao, times(1)).save(user);
        Mockito.verify(trainerDao, times(1)).save(trainer);
    }

    @Test
    public void createTrainer_invalidUserId_throwsIllegalArgumentException() {
        Trainer trainer = new Trainer(10L, List.of(1L,2L), 12L);

        Mockito.when(userDao.get(trainer.getUserId())).thenReturn(null);

        try {
            trainerService.createTrainer(trainer);
            Assertions.fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Absent user id.", e.getMessage());
        }


        Mockito.verify(userDao, times(1)).get(trainer.getUserId());

        // Verify that no saving operations were performed
        Mockito.verifyNoMoreInteractions(userDao);
        Mockito.verifyNoMoreInteractions(trainerDao);
    }

    @Test
    public void findTrainerById_success() {
        Trainer trainer = new Trainer(10L, List.of(1L,2L), 12L);

        Mockito.when(trainerDao.get(Mockito.anyLong())).thenReturn(trainer);

        Trainer foundTrainer = trainerService.findTrainerById(trainer.getId());
        Assertions.assertEquals(trainer, foundTrainer);
        Mockito.verify(trainerDao, times(1)).get(trainer.getId());
    }

    @Test
    public void findTrainerById_invalidId_throwsNotFoundException() {
        Mockito.when(trainerDao.get(Mockito.anyLong())).thenReturn(null);

        try {
            trainerService.findTrainerById(12);
            Assertions.fail("Expected NotFoundException");
        } catch (NotFoundException exception) {
            Assertions.assertEquals("Trainee not found by id: 12", exception.getMessage());
        }

        Mockito.verify(trainerDao, times(1)).get(any());
    }

    @Test
    public void updateTrainer_success() {
        Trainer trainer = new Trainer(10L, List.of(1L,2L), 12L);

        Mockito.when(trainerDao.get(trainer.getId())).thenReturn(trainer);

        trainerService.updateTrainer(trainer);

        Mockito.verify(trainerDao, times(1)).save(trainer);
    }

    @Test
    public void updateTrainer_invalidId_throwsNotFoundException() {
        Trainer trainer = new Trainer(10L, List.of(1L,2L), 12L);

        Mockito.when(trainerDao.get(trainer.getId())).thenReturn(null);

        try {
            trainerService.updateTrainer(trainer);
            Assertions.fail("Expected NotFoundException when absent trainee id is given.");
        } catch (NotFoundException e) {
            Assertions.assertEquals("Trainer record not exist by id: " + trainer.getId(), e.getMessage());
        }
    }
}