package de.sobotta.controller;

import de.sobotta.DTO.OfferDTO;
import de.sobotta.service.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OffersController {
    @Autowired
    private OffersService offersService;

    @GetMapping
    public List<OfferDTO> getAll() {
        return offersService.findAll();
    }

    @GetMapping("{offerId}")
    public OfferDTO getById(@PathVariable long offerId) {
        return offersService.findById(offerId);
    }

    @PostMapping
    public OfferDTO create(@RequestBody OfferDTO offerDTO) {
        return offersService.save(offerDTO);
    }

    @PutMapping("{offerId}")
    public OfferDTO update(@PathVariable long offerId, @RequestBody OfferDTO offerDTO) {
        return offersService.update(offerId, offerDTO);
    }

    @DeleteMapping("{offerId}")
    public void delete(@PathVariable long offerId) {
        offersService.delete(offerId);
    }
}
