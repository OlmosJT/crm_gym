package com.epam.crm.gym.model;

import lombok.*;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter @Setter @EqualsAndHashCode
public class Trainee {
    private long id;
    private LocalDate dob;
    private String address;
    private long userId;
}
