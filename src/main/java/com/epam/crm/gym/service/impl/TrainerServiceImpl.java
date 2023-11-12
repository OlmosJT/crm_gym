package com.epam.crm.gym.service.impl;

import com.epam.crm.gym.aspect.Loggable;
import com.epam.crm.gym.dao.TrainerDao;
import com.epam.crm.gym.dao.UserEDao;
import com.epam.crm.gym.exception.NotFoundException;
import com.epam.crm.gym.model.Trainee;
import com.epam.crm.gym.model.Trainer;
import com.epam.crm.gym.model.UserE;
import com.epam.crm.gym.service.TrainerService;
import org.springframework.stereotype.Service;

import static com.epam.crm.gym.util.ProfileGenerator.generatePassword;

@Service
public class TrainerServiceImpl implements TrainerService {
    private final TrainerDao trainerDao;
    private final UserEDao userEDao;

    public TrainerServiceImpl(TrainerDao trainerDao, UserEDao userEDao) {
        this.trainerDao = trainerDao;
        this.userEDao = userEDao;
    }

    @Override
    @Loggable
    public Trainer createTrainer(Trainer trainer) {
        UserE user = userEDao.get(trainer.getUserId());
        if(user == null) {
            throw new IllegalArgumentException("Absent user id.");
        }
        String generatedUsername = generateUsername(user.getFirstName(), user.getLastName());
        String generatedPassword = generatePassword();

        user.setUsername(generatedUsername);
        user.setPassword(generatedPassword);
        userEDao.save(user);

        return trainerDao.save(trainer);
    }

    @Override
    @Loggable
    public Trainer findTrainerById(long id) throws NotFoundException {
        Trainer trainer = trainerDao.get(id);
        if(trainer == null) {
            throw new NotFoundException("Trainer not found by id: " + id);
        }
        return trainer;
    }

    @Override
    @Loggable
    public void updateTrainer(Trainer trainer) throws NotFoundException {
        if(trainerDao.get(trainer.getId()) == null) {
            throw new NotFoundException("Trainer record not exist by id: " + trainer.getId());
        }

        trainerDao.save(trainer);
    }

    @Override
    @Loggable
    public void deleteTrainerById(long id) {
        trainerDao.delete(id);
    }

    public String generateUsername(String firstname, String lastname) {
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
