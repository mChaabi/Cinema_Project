package Cinema.Movie.dto;

import Cinema.Movie.model.Film;
import Cinema.Movie.model.Salle;
import Cinema.Movie.model.Seance;
import org.springframework.stereotype.Component;

@Component
public class SeanceMapper {

    public SeanceDto toDto(Seance seance) {
        if (seance == null) {
            return null;
        }
        Long filmId = (seance.getFilm() != null) ? seance.getFilm().getId() : null;
        Long salleId = (seance.getSalle() != null) ? seance.getSalle().getId() : null;

        return new SeanceDto(
            seance.getId(),
            seance.getDateProjection(),
            seance.getHeureDebut(),
            seance.getHeureFin(),
            filmId,
            salleId
        );
    }

    public Seance toEntity(SeanceDto seanceDto) {
        if (seanceDto == null) {
            return null;
        }
        Seance seance = new Seance();
        seance.setId(seanceDto.id());
        seance.setDateProjection(seanceDto.dateProjection());
        seance.setHeureDebut(seanceDto.heureDebut());
        seance.setHeureFin(seanceDto.heureFin());

        if (seanceDto.filmId() != null) {
            Film film = new Film();
            film.setId(seanceDto.filmId());
            seance.setFilm(film);
        }

        if (seanceDto.salleId() != null) {
            Salle salle = new Salle();
            salle.setId(seanceDto.salleId());
            seance.setSalle(salle);
        }

        return seance;
    }
}