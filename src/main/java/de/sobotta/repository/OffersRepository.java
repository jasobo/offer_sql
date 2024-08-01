package de.sobotta.repository;

import de.sobotta.model.Offers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffersRepository extends JpaRepository<Offers, Long> {

}
