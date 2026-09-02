package Cinema.Movie.service;

import Cinema.Movie.dto.SeanceDto;
import Cinema.Movie.dto.SeanceMapper;
import Cinema.Movie.dto.SiegeMapper;
import Cinema.Movie.model.Seance;
import Cinema.Movie.model.Siege;
import Cinema.Movie.repository.ReservationSiegeRepository;
import Cinema.Movie.repository.SeanceRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import Cinema.Movie.repository.SiegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class SeanceService extends AbstractService<Seance, Long> {

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private SiegeRepository siegeRepository;

    @Autowired
    private SeanceMapper seanceMapper;
    @Autowired
    private ReservationSiegeRepository reservationSiegeRepository;

    @Override
    protected JpaRepository<Seance, Long> getRepository() {
        return seanceRepository;
    }
    
    public List<Seance> getSeancesParDate(Date date){
    	return seanceRepository.findByDateProjection(date);
    }

    public List<SeanceDto> getSeancesByFilm(Long filmId) {
        List<Seance> seances = seanceRepository.findByFilmId(filmId);

        return seances.stream()
                .map(seanceMapper::toDto)
                .collect(Collectors.toList());
    }

    public SeanceDto getSeanceById(Long id) {
        Seance s = seanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Séance introuvable"));
        return new SeanceDto(
                s.getId(), s.getDateProjection(), s.getHeureDebut(), s.getHeureFin(),
                s.getFilm().getId(), s.getFilm().getTitre(), s.getFilm().getPhotoUrl(),
                s.getSalle().getId(), s.getSalle().getNumero(), s.getSalle().getCapacite()
        );
    }

    public List<SiegeMapper.SiegeDto> getSiegesAvecStatut(Long seanceId) {
        Seance seance = seanceRepository.findById(seanceId)
                .orElseThrow(() -> new RuntimeException("Séance introuvable"));

        // 2. Obtener todos los asientos de la sala de esta séance
        List<Siege> tousLesSieges = siegeRepository.findBySalleId(seance.getSalle().getId());

        // 3. Obtener los IDs de los asientos que ya están ocupados para esta séance específica
        Set<Long> siegesOccupes = reservationSiegeRepository.findSiegeIdsByReservationSeanceId(seanceId);

        return tousLesSieges.stream()
                .map(siege -> SiegeMapper.toDto(
                        siege,
                        siegesOccupes.contains(siege.getId()) // true si está en la lista de ocupados
                ))
                .collect(Collectors.toList());
    }

}
