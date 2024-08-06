package de.sobotta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "OFFERS", schema = "C##JAN")
public class Offers {
    @Id
    @Column(name = "offerId")
    private long offerId;

    @Column(name = "offerNr")
    private String offerNr;

    @Column(name = "offerName")
    private String offerName;

    @Column(name = "offerType")
    private long offerType;

    @Column(name = "price")
    private float price;

    @Column(name = "creationDate")
    private Date creationDate;

    @Column(name = "validUntil")
    private Date validUntil;
}
