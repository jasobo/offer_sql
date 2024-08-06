package de.sobotta.repository;

import de.sobotta.model.Offers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OffersRepository extends JpaRepository<Offers, Long> {
    Optional<Offers> findByOfferNr(String offerNr);

}
