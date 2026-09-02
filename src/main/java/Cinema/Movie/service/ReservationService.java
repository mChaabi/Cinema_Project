package Cinema.Movie.service;
import Cinema.Movie.dto.ReservationMapper;
import Cinema.Movie.model.Reservation;
import Cinema.Movie.model.Siege;
import Cinema.Movie.repository.ReservationRepository;
import Cinema.Movie.repository.SiegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {
   @Autowired
   private ReservationRepository reservationRepository;
    @Autowired
    private SiegeRepository siegeRepository;

    // Récupérer toutes les réservations en DTO
    public List<ReservationMapper.ReservationDto> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationMapper::toDto)
                .collect(Collectors.toList());
    }


    // Récupérer une réservation par son ID en DTO
    public Optional<ReservationMapper.ReservationDto> getReservationById(Long id) {
        return reservationRepository.findById(id)
                .map(ReservationMapper::toDto);
    }

    // Créer une réservation à partir d'un DTO (avec gestion des sièges par leurs IDs)
    public ReservationMapper.ReservationDto createReservation(ReservationMapper.ReservationDto dto) {
        // Récupérer les sièges associés si des IDs sont présents dans le DTO
        // (Note: Assurez-vous que votre ReservationDto possède une liste d'IDs de sièges ou gérez-la selon vos besoins)
        List<Siege> sieges = List.of();

        Reservation reservation = ReservationMapper.toEntity(dto, sieges);
        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationMapper.toDto(savedReservation);
    }

   public Reservation reserverTicket(Reservation reservation) {
       return reservationRepository.save(reservation);
   }

   public List<Reservation> getHistoriqueByUser(Long customerId) {
       return reservationRepository.findByCustomerId(customerId);
   }

    // Supprimer une réservation
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
