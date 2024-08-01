package de.sobotta.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class PersonDTO {
    private long personId;
    private String personNr;
    private String firstName;
    private String lastName;
    private Date birthDay;
    private String street;
    private String city;
}
