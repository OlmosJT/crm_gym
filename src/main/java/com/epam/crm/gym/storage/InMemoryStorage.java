package com.epam.crm.gym.storage;

import com.epam.crm.gym.model.*;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.management.ReflectionException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Component
public class InMemoryStorage implements InitializingBean {
    private final Map<Long, UserE> userEMap = new HashMap<>();
    private final Map<Long, Trainee> traineeMap = new HashMap<>();
    private final Map<Long, Trainer> trainerMap = new HashMap<>();
    private final Map<Long, Training> trainingMap = new HashMap<>();
    private final Map<Long, TrainingType> trainingTypeMap = new HashMap<>();

    @Value("${file.trainees.path}")
    private String filePathTrainees;

    @Value("${file.trainers.path}")
    private String filePathTrainers;

    @Value("${file.trainingTypes.path}")
    private String filePathTrainingTypes;

    @Value("${file.users.path}")
    private String filePathUsers;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            List<UserE> userRecords = parseListFromCSV(filePathUsers, UserE.class);
            userRecords.forEach(userE -> userEMap.put(userE.getId(), userE));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> List<T> parseListFromCSV(String csvFilePath, Class<T> clazz) throws IOException {
        List<T> objects = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                T object = instantiateAndPopulateObject(fields, clazz);
                objects.add(object);
            }
        } catch (ReflectionException | NoSuchFieldException | InvocationTargetException | NoSuchMethodException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return objects;
    }

    private static <T> T instantiateAndPopulateObject(String[] fields, Class<T> clazz) throws ReflectionException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);

        T object = (T) constructor.newInstance();

        for (int i = 0; i < fields.length; i++) {
            String fieldName = clazz.getDeclaredFields()[i].getName();
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);

            Object fieldValue = convertValue(fields[i], field.getType());
            field.set(object, fieldValue);
        }

        return object;

    }

    private static Object convertValue(String fieldValue, Class<?> fieldType) {
        if (fieldType == String.class) {
            return fieldValue;
        } else if (fieldType == Long.class || fieldType == long.class ) {
            return Long.parseLong(fieldValue);
        } else if (fieldType == Integer.class || fieldType == int.class) {
            return Integer.parseInt(fieldValue);
        } else if (fieldType == Boolean.class || fieldType == boolean.class) {
            return Boolean.parseBoolean(fieldValue);
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + fieldType);
        }
    }
}
