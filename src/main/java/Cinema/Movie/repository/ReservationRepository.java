package Cinema.Movie.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import Cinema.Movie.model.Reservation;
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
   List<Reservation> findByCustomerId(Long customerId);
}