package Cinema.Movie.repository;

import Cinema.Movie.model.Siege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SiegeRepository extends JpaRepository<Siege, Long> {
    List<Siege> findBySeanceId(Long seanceId);

    @Query("SELECT s FROM Siege s WHERE s.salle.id = :salleId")
    List<Siege> findBySalleId(@Param("salleId") Long salleId);
}
