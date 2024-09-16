package de.sobotta.service;

import de.sobotta.DTO.OfferDTO;
import de.sobotta.model.Offers;
import de.sobotta.repository.OffersRepository;
import de.sobotta.response.OffersResponse;
import de.sobotta.utils.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class OffersService {
    private static final Logger logger = LoggerFactory.getLogger(OffersService.class);
    @Autowired
    private OffersRepository offersRepository;

    public List<OffersResponse> findAll() {
        List<Offers> offers = offersRepository.findAll();
        List<OffersResponse> offersResponses = new ArrayList<>();
        for (Offers o : offers) {
            OffersResponse offersResponse = mapToResponse(o);
            offersResponses.add(offersResponse);
        }
        return offersResponses;
    }

    public OffersResponse findByOfferNr(String offerNr) {
        try {
            Offers offer = offersRepository.findByOfferNr(offerNr)
                    .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException(offerNr));
            logger.info("Found offer: " + offer.getOfferNr());
            return mapToResponse(offer);
        } catch (GlobalExceptionHandler.NotFoundException e) {
            logger.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Error finding offer");
        }
    }

    public OffersResponse save(OfferDTO offerDTO) {
        try {
            Offers offers = mapToOffers(offerDTO);
            offers.setOfferNr(generatedOfferNr());
            offers.setCreationDate(LocalDate.now());
            offersRepository.save(offers);
            logger.info("Offers saved successfully");
            return mapToResponse(offers);
        } catch (Exception e) {
            logger.error("Error saving offer");
            throw new RuntimeException("Error saving offer");
        }
    }

    public OffersResponse update(String offerNr, OfferDTO offerDTO) {
        try {
            Offers toUpdate = offersRepository.findByOfferNr(offerNr)
                    .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException(offerNr));

            toUpdate.setOfferName(offerDTO.getOfferName());
            toUpdate.setOfferType(offerDTO.getOfferType());
            toUpdate.setPrice(offerDTO.getPrice());
            toUpdate.setValidUntil(offerDTO.getValidUntil());
            logger.info("Updating offer: " + toUpdate.getOfferNr());
            offersRepository.save(toUpdate);
            return mapToResponse(toUpdate);
        } catch (GlobalExceptionHandler.NotFoundException e) {
            logger.error(e.getMessage());
            throw e;
        } catch (Exception e) {
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
        } catch (GlobalExceptionHandler.NotFoundException e) {
            logger.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Error deleting offer");
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void deleteAfterExpire(){
        try {
            LocalDate day = LocalDate.now();
            List<Offers> expired = offersRepository.findAllByValidUntilBefore(day);
            offersRepository.deleteAll(expired);
            logger.info("Expired offers were deleted.");
        } catch(Exception e) {
            logger.error("Error during offer cleanup task", e);
        }
    }

    private String generatedOfferNr() {
        String offerNr;
        do {
            int numberPart = new Random().nextInt(99999);
            offerNr = "O" + String.format("%05d", numberPart);
        } while (!offersRepository.existsByOfferNr(offerNr));
        return offerNr;
    }

    public OfferDTO mapToDTO(Offers offers) {
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setOfferName(offers.getOfferName());
        offerDTO.setOfferType(offers.getOfferType());
        offerDTO.setPrice(offers.getPrice());
        offerDTO.setValidUntil(offers.getValidUntil());
        return offerDTO;
    }

    public OffersResponse mapToResponse(Offers offers) {
        OffersResponse offersResponse = new OffersResponse();
        offersResponse.setOfferId(offers.getOfferId());
        offersResponse.setOfferNr(offers.getOfferNr());
        offersResponse.setOfferName(offers.getOfferName());
        offersResponse.setOfferType(offers.getOfferType());
        offersResponse.setPrice(offers.getPrice());
        offersResponse.setCreationDate(offers.getCreationDate());
        offersResponse.setValidUntil(offers.getValidUntil());
        return offersResponse;
    }

    public Offers mapToOffers(OfferDTO offerDTO) {
        Offers offers = new Offers();
        offers.setOfferName(offerDTO.getOfferName());
        offers.setOfferType(offerDTO.getOfferType());
        offers.setPrice(offerDTO.getPrice());
        offers.setValidUntil(offerDTO.getValidUntil());
        return offers;
    }

    public List<OfferDTO> searchAll(String offerName, long offerType, float price, LocalDate validUntil) {
        try{
            List<Offers> offers = offersRepository.findAll((root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();

                if(offerName != null && !offerName.isEmpty()){
                    predicates.add(cb.like(root.get("offerName"), "%"+offerName+"%"));
                }

                if(offerType != 0){
                    predicates.add(cb.equal(root.get("offerType"), offerType));
                }

                if(price != 0){
                    predicates.add(cb.greaterThanOrEqualTo(root.get("price"), price));
                }
                if(validUntil != null){
                    predicates.add(cb.lessThanOrEqualTo(root.get("validUntil"), validUntil));
                }
                return cb.and(predicates.toArray(new Predicate[0]));
            });

            List<OfferDTO> responseList = new ArrayList<>();
            for (Offers offer : offers) {
                responseList.add(mapToDTO(offer));
            }

            return responseList;

        } catch (GlobalExceptionHandler.NotFoundException e) {
            logger.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Error finding offer");
        }
    }
}
