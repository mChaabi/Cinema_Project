package Cinema.Movie.repository;

import Cinema.Movie.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ReservationSiegeRepository extends JpaRepository<Reservation, Long> {

    // Obtiene los IDs de los asientos reservados para una sesión (excluyendo canceladas)
    @Query("SELECT s.id FROM Reservation r JOIN r.sieges s WHERE r.seanceId = :seanceId AND r.statut <> 'ANNULEE'")
    Set<Long> findSiegeIdsByReservationSeanceId(@Param("seanceId") Long seanceId);

    // Obtiene los números de los asientos por el ID de la reserva usando la relación ManyToMany
    @Query("SELECT s.numero FROM Reservation r JOIN r.sieges s WHERE r.id = :reservationId")
    List<String> findNumerosSiegeByReservationId(@Param("reservationId") Long reservationId);
}