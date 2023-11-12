package com.epam.crm.gym.util;

import java.util.Random;

public class ProfileGenerator {
    public static String generatePassword() {
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int character = random.nextInt(95) + 32;
            password.append((char) character);
        }
        return password.toString();
    }
}
