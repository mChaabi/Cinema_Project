package Cinema.Movie.service;

import Cinema.Movie.dto.DashboardDto;
import Cinema.Movie.dto.FilmMapper;
import Cinema.Movie.dto.SalleMapper;
import Cinema.Movie.dto.SeanceMapper;
import Cinema.Movie.model.Film;
import Cinema.Movie.model.Reservation;
import Cinema.Movie.model.Salle;
import Cinema.Movie.model.Seance;
import Cinema.Movie.repository.FilmRepository;
import Cinema.Movie.repository.ReservationRepository;
import Cinema.Movie.repository.SalleRepository;
import Cinema.Movie.repository.SeanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired private FilmRepository filmRepository;
    @Autowired private SalleRepository salleRepository;
    @Autowired private SeanceRepository seanceRepository;
    @Autowired private ReservationRepository reservationRepository;

    @Autowired private FilmMapper filmMapper;
    @Autowired private SalleMapper salleMapper;
    @Autowired private SeanceMapper seanceMapper;

    public DashboardDto getDashboardData() {
        List<Film> films = filmRepository.findAll();
        List<Salle> salles = salleRepository.findAll();
        List<Seance> seances = seanceRepository.findAll();

        long totalReservations = reservationRepository.count();

        // Protegemos por si la lista viene vacía o da null
        List<Reservation> confirmadas = reservationRepository.findByStatut("CONFIRMEE");
        long totalBillets = (confirmadas != null) ? confirmadas.stream()
                .mapToLong(Reservation::getNbrPlaces)
                .sum() : 0;

        // Change Map<Long, Integer> to Map<String, Integer>
        Map<String, Integer> occupation = new HashMap<>();
        for (Salle salle : salles) {
            Integer reserved = reservationRepository.countPlacesReserveesToday(salle.getId());
            int resCount = (reserved != null) ? reserved : 0;

            int pct = (salle.getCapacite() > 0) ? (resCount * 100 / salle.getCapacite()) : 0;

            // Convert salle.getId() to String using String.valueOf() or .toString()
            occupation.put(String.valueOf(salle.getId()), pct);
        }

        return new DashboardDto(
                films != null ? films.stream().map(filmMapper::toDto).toList() : List.of(),
                salles != null ? salles.stream().map(salleMapper::toDto).toList() : List.of(),
                seances != null ? seances.stream().map(seanceMapper::toDto).toList() : List.of(),
                totalReservations,
                totalBillets,
                occupation
        );
    }
}