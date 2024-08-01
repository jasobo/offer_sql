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
public class Offers {
    @Id
    private long offerId;
    private String offerNr;
    private String offerName;
    private long offerType;
    private float price;
    private Date creationDate;
    private Date validUntil;
}
