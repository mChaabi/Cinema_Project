package Cinema.Movie.repository;

import Cinema.Movie.model.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    Optional<Paiement> findByReservationId(Long reservationId);
}
