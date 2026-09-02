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
        String filmTitre = (seance.getFilm() != null) ? seance.getFilm().getTitre() : null; // Ajusta el getter si se llama diferente (ej: getTitle())
        String filmPhotoUrl = (seance.getFilm() != null) ? seance.getFilm().getPhotoUrl() : null; // Ajusta según tu entidad Film

        Long salleId = (seance.getSalle() != null) ? seance.getSalle().getId() : null;
        Integer salleNumero = (seance.getSalle() != null) ? seance.getSalle().getNumero() : null; // Ajusta según tu entidad Salle
        Integer salleCapacite = (seance.getSalle() != null) ? seance.getSalle().getCapacite() : null; // Ajusta según tu entidad Salle

        return new SeanceDto(
                seance.getId(),
                seance.getDateProjection(),
                seance.getHeureDebut(),
                seance.getHeureFin(),
                filmId,
                filmTitre,
                filmPhotoUrl,
                salleId,
                salleNumero,
                salleCapacite
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