package com.epam.crm.gym.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter @Setter @EqualsAndHashCode
public class Trainee {
    private long id;
    private LocalDateTime dob;
    private String address;
    private long userId;
}
