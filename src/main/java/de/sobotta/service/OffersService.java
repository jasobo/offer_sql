package de.sobotta.service;

import de.sobotta.DTO.OfferDTO;
import de.sobotta.model.Offers;
import de.sobotta.repository.OffersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OffersService {
    @Autowired
    private OffersRepository offersRepository;

    public List<OfferDTO> findAll() {
        List<Offers> offers = offersRepository.findAll();
        List<OfferDTO> offerDTOs = new ArrayList<>();
        for (Offers o : offers) {
            OfferDTO offerDTO = mapToDTO(o);
            offerDTOs.add(offerDTO);
        }
        return offerDTOs;
    }

    public OfferDTO findById(long offerId) {
        Optional<Offers> optionalOffer = offersRepository.findById(offerId);
        if (optionalOffer.isPresent()) {
            return mapToDTO(optionalOffer.get());
        } else {
            throw new RuntimeException("Offer not found with id: " + offerId);
        }
    }

    public OfferDTO save(OfferDTO offerDTO) {
        Offers offers = mapToOffers(offerDTO);
        offers.setOfferNr(generatedOfferNr());
        offersRepository.save(offers);
        return offerDTO;
    }

    public OfferDTO update(long offerId, OfferDTO offerDTO) {
        Optional<Offers> optionalOffer = offersRepository.findById(offerId);
        if (optionalOffer.isPresent()) {
            Offers toUpdate = optionalOffer.get();
            toUpdate.setOfferName(offerDTO.getOfferName());
            toUpdate.setOfferType(offerDTO.getOfferType());
            toUpdate.setPrice(offerDTO.getPrice());
            toUpdate.setValidUntil(offerDTO.getValidUntil());
            offersRepository.save(toUpdate);
            return mapToDTO(toUpdate);
        } else {
            throw new RuntimeException("Offer not found with id: " + offerId);
        }
    }

    public void delete(long offerId) {
        offersRepository.deleteById(offerId);
    }

    private String generatedOfferNr() {
        int numberPart = new Random().nextInt(99999);
        return "O" + String.format("%05d", numberPart);

    }

    public OfferDTO mapToDTO(Offers offers){
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setOfferId(offers.getOfferId());
        offerDTO.setOfferNr(offers.getOfferNr());
        offerDTO.setOfferName(offers.getOfferName());
        offerDTO.setOfferType(offers.getOfferType());
        offerDTO.setPrice(offers.getPrice());
        offerDTO.setCreationDate(offers.getCreationDate());
        offerDTO.setValidUntil(offers.getValidUntil());
        return offerDTO;
    }

    public Offers mapToOffers(OfferDTO offerDTO) {
        Offers offers = new Offers();
        offers.setOfferId(offerDTO.getOfferId());
        offers.setOfferNr(offerDTO.getOfferNr());
        offers.setOfferName(offerDTO.getOfferName());
        offers.setOfferType(offerDTO.getOfferType());
        offers.setPrice(offerDTO.getPrice());
        offers.setCreationDate(offerDTO.getCreationDate());
        offers.setValidUntil(offerDTO.getValidUntil());
        return offers;
    }
}
