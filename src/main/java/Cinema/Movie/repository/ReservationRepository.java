package Cinema.Movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import Cinema.Movie.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
   List<Reservation> findByCustomerId(Long customerId);

   // Utilisation d'une requête explicite pour éviter l'erreur de nom de propriété
   @Query("SELECT r FROM Reservation r WHERE r.statut = :statut")
   List<Reservation> findByStatut(@Param("statut") String statut);

   @Query("SELECT SUM(r.nbrPlaces) FROM Reservation r JOIN Seance s ON r.seanceId = s.id WHERE s.salle.id = :salleId")
   Integer countPlacesReserveesToday(@Param("salleId") Long salleId);
}