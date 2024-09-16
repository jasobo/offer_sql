package de.sobotta.controller;

import de.sobotta.DTO.OfferDTO;
import de.sobotta.response.OffersResponse;
import de.sobotta.service.OffersService;
import de.sobotta.utils.GlobalExceptionHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SecurityRequirement(name = "basicAuth")
@RestController
@RequestMapping("/api/offers")
public class OffersController {
    private static final Logger logger = LoggerFactory.getLogger(OffersService.class);
    @Autowired
    private OffersService offersService;

    @Operation(summary="Get all Offers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All offers are shown",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OfferDTO.class))}),
            @ApiResponse(responseCode = "401", description = "You have no right to be here",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<OffersResponse> getAll() {
        return offersService.findAll();
    }

    @Operation(summary = "Get one offer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "You got the offer, yay",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OfferDTO.class))}),
            @ApiResponse(responseCode = "401", description = "You have no right to be here",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Offer not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{offerNr}")
    public OffersResponse getByOfferNr(@PathVariable String offerNr) {
        logger.info("Hello");
        return offersService.findByOfferNr(offerNr);
    }

    @Operation(summary= "Search through all offers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description ="No offers found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OfferDTO.class))}),
            @ApiResponse(responseCode = "401", description = "You have no right to be here",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/search")
    public ResponseEntity<List<OfferDTO>> search(@Validated @RequestBody OfferDTO searchValues) {

        /*if(searchValues.getOfferName() == null &&
                searchValues.getPrice() == null &&
                searchValues.getOfferType() == null &&
                searchValues.getValidUntil() == null
        ){
            throw new GlobalExceptionHandler.InvalidInputException("At least one search parameter must be provided!");
        }*/

        List<OfferDTO> offers = offersService.searchAll(
                searchValues.getOfferName(),
                searchValues.getOfferType(),
                searchValues.getPrice(),
                searchValues.getValidUntil()
        );
        if (offers.isEmpty()){
            throw new GlobalExceptionHandler.NotFoundException("No offers found");
        } else {
            return ResponseEntity.ok(offers);
        }
    }

    @Operation(summary = "Create one offer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Offer created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OfferDTO.class))}),
            @ApiResponse(responseCode = "401", description = "You have no right to be here",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public OffersResponse create(@RequestBody OfferDTO offerDTO) {
        return offersService.save(offerDTO);
    }

    @Operation(summary = "Update one offer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offer updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OfferDTO.class))}),
            @ApiResponse(responseCode = "401", description = "You have no right to be here",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Offer not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{offerNr}")
    public OffersResponse update(@PathVariable String offerNr, @RequestBody OfferDTO offerDTO) {
        return offersService.update(offerNr, offerDTO);
    }

    @Operation(summary = "Delete an offer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Offer was dealt with",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OfferDTO.class))}),
            @ApiResponse(responseCode = "401", description = "You have no right to be here",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Offer not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{offerNr}")
    public void delete(@PathVariable String offerNr) {
        offersService.delete(offerNr);
    }
}
