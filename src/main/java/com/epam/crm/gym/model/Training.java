package com.epam.crm.gym.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Training {
    private long id;
    private long traineeId;
    private long trainerId;
    private String trainingName;
    private long trainingTypeId;
    private LocalDateTime trainingDate;
    private int trainingDuration;
}
