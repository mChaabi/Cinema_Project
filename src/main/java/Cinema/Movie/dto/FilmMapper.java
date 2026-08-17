package Cinema.Movie.dto;

import Cinema.Movie.model.Film;
import Cinema.Movie.model.Genre;
import Cinema.Movie.model.Nationalite;
import Cinema.Movie.model.Personne;
import org.springframework.stereotype.Component;

@Component
public class FilmMapper {

    public FilmDto toDto(Film film) {
        if (film == null) return null;
        return new FilmDto(
            film.getId(),
            film.getTitre(),
            film.getDuree(),
            film.getAnnee(),
            film.getGenre() != null ? film.getGenre().getId() : null,
            film.getNationalite() != null ? film.getNationalite().getId() : null,
            film.getRealisateur() != null ? film.getRealisateur().getId() : null
        );
    }

    public Film toEntity(FilmDto dto) {
        if (dto == null) return null;
        Film film = new Film();
        film.setId(dto.id());
        film.setTitre(dto.titre());
        film.setDuree(dto.duree());
        film.setAnnee(dto.annee());

        if (dto.genreId() != null) {
            Genre genre = new Genre();
            genre.setId(dto.genreId());
            film.setGenre(genre);
        }
        if (dto.nationaliteId() != null) {
            Nationalite nat = new Nationalite();
            nat.setId(dto.nationaliteId());
            film.setNationalite(nat);
        }
        if (dto.realisateurId() != null) {
            Personne real = new Personne();
            real.setId(dto.realisateurId());
            film.setRealisateur(real);
        }
        return film;
    }
}