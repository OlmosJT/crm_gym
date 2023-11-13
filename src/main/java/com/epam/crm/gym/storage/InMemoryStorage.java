package com.epam.crm.gym.storage;

import com.epam.crm.gym.model.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.management.ReflectionException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Getter
@Slf4j
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
        List<UserE> userRecords = parseUsersCsvFile(filePathUsers);
        userRecords.forEach(userE -> userEMap.put(userE.getId(), userE));
        log.info("Prepared data has added to users map: " + userEMap);

        List<Trainee> traineeRecords = parseTraineesCsvFile(filePathTrainees);
        traineeRecords.forEach(trainee -> traineeMap.put(trainee.getId(), trainee));
        log.info("Prepared data has added to trainee map: " + traineeMap);

    }

    private List<UserE> parseUsersCsvFile(String filePathUsers) {
        List<UserE> userRecords = new ArrayList<>();

        try(final FileReader fileReader = new FileReader(filePathUsers)) {
            CSVParser csvParser = new CSVParser(
                    fileReader,
                    CSVFormat.Builder
                            .create(CSVFormat.DEFAULT)
                            .setHeader()
                            .setSkipHeaderRecord(true)
                            .build()
            );

            for (CSVRecord csvRecord: csvParser) {
                long id = Long.parseLong(csvRecord.get("id"));
                String firstName = csvRecord.get("firstname");
                String lastName = csvRecord.get("lastname");
                String username = csvRecord.get("username");
                String password = csvRecord.get("password");
                boolean isActive = Boolean.parseBoolean(csvRecord.get("isActive"));

                UserE user = new UserE(id, firstName, lastName, username, password, isActive);
                userRecords.add(user);
            }

        } catch (FileNotFoundException e) {
            log.error("Parcing csv file not found: " + e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return userRecords;
    }
    private List<Trainee> parseTraineesCsvFile(String filePathTrainees) {
        List<Trainee> traineeRecords = new ArrayList<>();

        try(final FileReader fileReader = new FileReader(filePathTrainees)) {
            CSVParser csvParser = new CSVParser(
                    fileReader,
                    CSVFormat.Builder
                            .create(CSVFormat.DEFAULT)
                            .setHeader()
                            .setSkipHeaderRecord(true)
                            .build()
            );

            for (CSVRecord csvRecord: csvParser) {
                long id = Long.parseLong(csvRecord.get("id"));
                LocalDate dob = LocalDate.parse(csvRecord.get("dob"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String address = csvRecord.get("address");
                long userId = Long.parseLong(csvRecord.get("userId"));

                Trainee trainee = new Trainee(id, dob, address, userId);
                traineeRecords.add(trainee);
            }

        } catch (FileNotFoundException e) {
            log.error("File not found: " + e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return traineeRecords;
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
        } else if (fieldType == Long.class || fieldType == long.class) {
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
