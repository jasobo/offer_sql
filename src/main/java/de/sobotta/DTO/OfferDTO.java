package de.sobotta.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class OfferDTO {

    private String offerName;
    private long offerType;
    private float price;
    private LocalDate validUntil;
}
