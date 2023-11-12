package com.epam.crm.gym.model;

import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserE {
    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isActive;
}
