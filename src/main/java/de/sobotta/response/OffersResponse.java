package de.sobotta.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class OffersResponse {
    private long offerId;
    private String offerNr;
    private String offerName;
    private long offerType;
    private float price;
    private LocalDate creationDate;
    private LocalDate validUntil;
}
