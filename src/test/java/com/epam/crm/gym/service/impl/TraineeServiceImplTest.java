package com.epam.crm.gym.service.impl;

import com.epam.crm.gym.dao.TraineeDao;
import com.epam.crm.gym.dao.UserEDao;
import com.epam.crm.gym.dao.impl.TraineeDaoImpl;
import com.epam.crm.gym.exception.NotFoundException;
import com.epam.crm.gym.model.Trainee;
import com.epam.crm.gym.model.UserE;
import com.epam.crm.gym.service.TraineeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

class TraineeServiceImplTest {
    TraineeDao mockTraineeDao;
    UserEDao mockUserDao;

    TraineeService traineeService;

    @BeforeEach
    public void setUp() {
        mockTraineeDao = Mockito.mock(TraineeDaoImpl.class);
        mockUserDao = Mockito.mock(UserEDao.class);

        traineeService = new TraineeServiceImpl(mockTraineeDao, mockUserDao);
    }

    @Test
    public void createTrainee_success() {
        UserE user = new UserE(3, "Jonny", "Cage", "Jonny.Cage", "password123", true);
        Trainee trainee = new Trainee(1, LocalDateTime.now(), "Address 1", 3);

        Mockito.when(mockUserDao.get(Mockito.anyLong())).thenReturn(user);
        Mockito.when(mockUserDao.existByUsername(Mockito.anyString())).thenReturn(false);

        Mockito.when(mockTraineeDao.save(trainee)).thenReturn(trainee);

        Trainee createdTrainee = traineeService.createTrainee(trainee);
        Mockito.verify(mockUserDao, times(1)).get(trainee.getUserId());
        Mockito.verify(mockUserDao, times(1)).save(user);
        Mockito.verify(mockTraineeDao, times(1)).save(trainee);
    }

    @Test
    public void createTrainee_invalidUserId_throwsException() {
        Trainee trainee = new Trainee(1, LocalDateTime.now(), "Address 1", 123);

        Mockito.when(mockUserDao.get(trainee.getUserId())).thenReturn(null);

        try {
            traineeService.createTrainee(trainee);
            Assertions.fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Absent user id.", e.getMessage());
        }


        Mockito.verify(mockUserDao, times(1)).get(trainee.getUserId());

        // Verify that no saving operations were performed
        Mockito.verifyNoMoreInteractions(mockUserDao);
        Mockito.verifyNoMoreInteractions(mockTraineeDao);
    }

    @Test
    public void findTraineeById_success() {
        Trainee trainee = new Trainee(5, LocalDateTime.now(), "Address somewhere in neverland", 19);

        Mockito.when(mockTraineeDao.get(Mockito.anyLong())).thenReturn(trainee);

        Trainee foundTrainee = traineeService.findTraineeById(trainee.getId());
        Assertions.assertEquals(trainee, foundTrainee);
        Mockito.verify(mockTraineeDao, times(1)).get(trainee.getId());
    }

    @Test
    public void findTraineeById_invalidId_throwsNotFoundException() {
        Mockito.when(mockTraineeDao.get(Mockito.anyLong())).thenReturn(null);

        try {
            traineeService.findTraineeById(5);
            Assertions.fail("Expected NotFoundException");
        } catch (NotFoundException exception) {
            Assertions.assertEquals("Trainee not found by id: 5", exception.getMessage());
        }

        Mockito.verify(mockTraineeDao, times(1)).get(any());
    }

    @Test
    public void updateTrainee_success() {
        Trainee trainee = new Trainee(
                1L,
                LocalDateTime.of(1995, 6, 2, 15, 48),
                "New Jersey St, 55",
                1234
        );

        Mockito.when(mockTraineeDao.get(trainee.getId())).thenReturn(trainee);

        traineeService.updateTrainee(trainee);

        Mockito.verify(mockTraineeDao, times(1)).save(trainee);
    }

    @Test
    public void updateTrainee_invalidId_throwsNotFoundException() {
        Trainee trainee = new Trainee(
                10L,
                LocalDateTime.of(1995, 6, 2, 15, 48),
                "New Jersey St, 55",
                256
        );

        Mockito.when(mockTraineeDao.get(trainee.getId())).thenReturn(null);

        try {
            traineeService.updateTrainee(trainee);
            Assertions.fail("Expected NotFoundException when absent trainee id is given.");
        } catch (NotFoundException e) {
            Assertions.assertEquals("Trainee record not exist by id: " + trainee.getId(), e.getMessage());
        }
    }

    @ParameterizedTest
    @CsvSource({"John,Jane", "Tom,Arthur", "Merlin,Smith"})
    public void generateUsername_uniqueUsername(String firstname, String lastname) {
        Mockito.when(mockUserDao.existByUsername(anyString())).thenReturn(false);

        TraineeServiceImpl traineeService = new TraineeServiceImpl(any(), mockUserDao);

        String generatedUsername = traineeService.generateUsername(firstname, lastname);
        Assertions.assertEquals(firstname + "." + lastname, generatedUsername);

        // Verify that userEDao.existByUsername() was called only once
        Mockito.verify(mockUserDao, times(1))
                .existByUsername(firstname + "." + lastname);
    }
}