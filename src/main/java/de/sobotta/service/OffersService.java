package de.sobotta.service;

import de.sobotta.DTO.OfferDTO;
import de.sobotta.model.Offers;
import de.sobotta.repository.OffersRepository;
import de.sobotta.utils.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class OffersService {
    private static final Logger logger = LoggerFactory.getLogger(OffersService.class);
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

    public OfferDTO findByOfferNr(String offerNr) {
        try {
            Offers offer = offersRepository.findByOfferNr(offerNr)
                    .orElseThrow(()-> new GlobalExceptionHandler.NotFoundException(offerNr));
            logger.info("Found offer: " + offer.getOfferNr());
            return mapToDTO(offer);
        }
        catch (GlobalExceptionHandler.NotFoundException e){
            logger.error(e.getMessage());
            throw e;
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Error finding offer");
        }
    }

    public OfferDTO save(OfferDTO offerDTO) {
        try {
            Offers offers = mapToOffers(offerDTO);
            offers.setOfferNr(generatedOfferNr());
            offersRepository.save(offers);
            logger.info("Offers saved successfully");
            return offerDTO;
        }catch (Exception e) {
            logger.error("Error saving offer");
            throw new RuntimeException("Error saving offer");
        }
    }

    public OfferDTO update(String offerNr, OfferDTO offerDTO) {
        try{
        Offers toUpdate = offersRepository.findByOfferNr(offerNr)
                .orElseThrow(()-> new GlobalExceptionHandler.NotFoundException(offerNr));

            toUpdate.setOfferName(offerDTO.getOfferName());
            toUpdate.setOfferType(offerDTO.getOfferType());
            toUpdate.setPrice(offerDTO.getPrice());
            toUpdate.setValidUntil(offerDTO.getValidUntil());
            logger.info("Updating offer: " + toUpdate.getOfferNr());
            offersRepository.save(toUpdate);
            return mapToDTO(toUpdate);
        }catch (GlobalExceptionHandler.NotFoundException e){
            logger.error(e.getMessage());
            throw e;
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Error updating offer");
        }
    }

    public void delete(String offerNr) {
        try {
            Offers toDelete = offersRepository.findByOfferNr(offerNr)
                    .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException(offerNr));
            logger.info("Deleting offer: " + toDelete.getOfferNr());
            offersRepository.deleteById(toDelete.getOfferId());
        }catch (GlobalExceptionHandler.NotFoundException e){
            logger.error(e.getMessage());
            throw e;
        }catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Error deleting offer");
        }
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
