package com.epam.crm.gym.service.impl;


import com.epam.crm.gym.dao.TraineeDao;
import com.epam.crm.gym.dao.UserEDao;
import com.epam.crm.gym.exception.NotFoundException;
import com.epam.crm.gym.model.Trainee;
import com.epam.crm.gym.model.UserE;
import com.epam.crm.gym.service.TraineeService;
import org.springframework.stereotype.Service;
import static com.epam.crm.gym.util.ProfileGenerator.generatePassword;

@Service
public class TraineeServiceImpl implements TraineeService {

    private final TraineeDao traineeDao;
    private final UserEDao userEDao;

    public TraineeServiceImpl(TraineeDao traineeDao, UserEDao userEDao) {
        this.traineeDao = traineeDao;
        this.userEDao = userEDao;
    }

    @Override
    public Trainee createTrainee(Trainee trainee) throws IllegalArgumentException {
        UserE user = userEDao.get(trainee.getUserId());
        if(user == null) {
            throw new IllegalArgumentException("Absent user id.");
        }
        String generatedUsername = generateUsername(user.getFirstName(), user.getLastName());
        String generatedPassword = generatePassword();

        user.setUsername(generatedUsername);
        user.setPassword(generatedPassword);
        user.setActive(true);
        userEDao.save(user);

        return traineeDao.save(trainee);
    }

    @Override
    public Trainee findTraineeById(long id) throws NotFoundException {
         Trainee trainee = traineeDao.get(id);
         if(trainee == null) {
             throw new NotFoundException("Trainee not found by id: " + id);
         }
         return trainee;
    }

    @Override
    public void updateTrainee(Trainee trainee) throws NotFoundException {
        if(traineeDao.get(trainee.getId()) == null) {
            throw new NotFoundException("Trainee record not exist by id: " + trainee.getId());
        }

        traineeDao.save(trainee);
    }

    @Override
    public void deleteTraineeById(long id) {
        traineeDao.delete(id);
    }

    String generateUsername(String firstname, String lastname) {
        String baseUsername = firstname + "." + lastname;
        if(userEDao.existByUsername(baseUsername)) {
            int suffix = 1;
            while(userEDao.existByUsername(baseUsername + "." + suffix)) {
                suffix++;
            }
            return baseUsername + "." + suffix;
        }

        return baseUsername;
    }
}
