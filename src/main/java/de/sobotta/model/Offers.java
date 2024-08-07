package de.sobotta.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "OFFERS", schema = "C##JAN")
public class Offers {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "offer_Id")
    private long offerId;

    @Column(name = "offer_Nr")
    private String offerNr;

    @Column(name = "offer_Name")
    private String offerName;

    @Column(name = "offer_Type")
    private long offerType;

    @Column(name = "price")
    private float price;

    @Column(name = "creation_Date")
    private LocalDate creationDate;

    @Column(name = "valid_Until")
    private LocalDate validUntil;
}
