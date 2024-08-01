package de.sobotta.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@Entity
public class Person {
    @Id
    private long personId;
    private String personNr;
    private String firstName;
    private String lastName;
    private Date birthDay;
    private String street;
    private String city;
}
