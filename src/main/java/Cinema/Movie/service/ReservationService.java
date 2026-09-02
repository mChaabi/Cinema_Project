package Cinema.Movie.service;
import Cinema.Movie.dto.ReservationMapper;
import Cinema.Movie.dto.ReservationResponseDto;
import Cinema.Movie.model.Paiement;
import Cinema.Movie.model.Reservation;
import Cinema.Movie.model.Seance;
import Cinema.Movie.model.Siege;
import Cinema.Movie.repository.*;
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
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private ReservationSiegeRepository reservationSiegeRepository ;
    @Autowired
    private PaiementRepository paiementRepository;

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

    public List<ReservationResponseDto> getReservationsEnrichedByUser(Long userId) {
        List<Reservation> reservations = getHistoriqueByUser(userId);

        return reservations.stream().map(r -> {
            Seance seance = seanceRepository.findById(r.getSeanceId()).orElse(null);

            List<String> sieges = reservationSiegeRepository
                    .findNumerosSiegeByReservationId(r.getId()); // si no existe, ver Paso 3

            Optional<Paiement> paiement = paiementRepository.findByReservationId(r.getId());

            return new ReservationResponseDto(
                    r.getId(),
                    r.getDateReservation(),
                    r.getNbrPlaces(),
                    r.getStatut(),
                    r.getSeanceId(),
                    seance != null ? seance.getFilm().getTitre() : "Film indisponible",
                    seance != null ? seance.getFilm().getPhotoUrl() : null,
                    seance != null ? seance.getSalle().getNumero() : null,
                    seance != null ? seance.getDateProjection().toString() : null,
                    seance != null ? seance.getHeureDebut().toString() : null,
                    sieges != null ? sieges : List.of(),   // ✅ nunca null, siempre lista vacía como mínimo
                    paiement.map(Paiement::getAmount).orElse(null),
                    paiement.map(Paiement::getStatus).orElse("EN_ATTENTE")
            );
        }).collect(Collectors.toList());
    }

   public List<Reservation> getHistoriqueByUser(Long customerId) {
       return reservationRepository.findByCustomerId(customerId);
   }

    // Supprimer une réservation
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
