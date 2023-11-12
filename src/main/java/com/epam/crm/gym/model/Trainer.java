package com.epam.crm.gym.model;

import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Trainer {
    private long id;
    private List<Long> specializationsId;
    private long userId;
}
