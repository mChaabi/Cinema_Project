package Cinema.Movie.service;
import Cinema.Movie.model.Reservation;
import Cinema.Movie.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ReservationService {
   @Autowired
   private ReservationRepository reservationRepository;
   public Reservation reserverTicket(Reservation reservation) {
       return reservationRepository.save(reservation);
   }
   public List<Reservation> getHistoriqueByUser(Long customerId) {
       return reservationRepository.findByCustomerId(customerId);
   }
}
