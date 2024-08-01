package de.sobotta.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
@Getter
@Setter
@ToString
public class OfferDTO {
    private long offerId;
    private String offerNr;
    private String offerName;
    private long offerType;
    private float price;
    private Date creationDate;
    private Date validUntil;
}
