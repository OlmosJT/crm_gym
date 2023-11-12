package com.epam.crm.gym;

import com.epam.crm.gym.config.ApplicationConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        Facade facade = context.getBean(Facade.class);
        facade.createTrainee(
                "Olmos",
                "Davronov",
                "Tasanno Street, 15",
                LocalDateTime.of(2002, 2, 17, 12, 0)
        );
    }
}
