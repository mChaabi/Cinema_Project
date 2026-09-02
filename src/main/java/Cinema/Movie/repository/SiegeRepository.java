package Cinema.Movie.repository;

import Cinema.Movie.model.Siege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SiegeRepository extends JpaRepository<Siege, Long> {
    List<Siege> findBySeanceId(Long seanceId);
}
