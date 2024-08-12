package de.sobotta.repository;

import de.sobotta.model.Offers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface OffersRepository extends JpaRepository<Offers, Long> {
    @Query(value = "SELECT * FROM OFFERS WHERE offer_Nr = :offerNr", nativeQuery = true)
    Optional<Offers> findByOfferNr(@Param("offerNr") String offerNr);

    boolean existsByOfferNr(String offerNr);


    List<Offers> findAllByValidUntilBefore(LocalDate validUntil);
}
