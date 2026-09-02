package Cinema.Movie.dto;

import Cinema.Movie.model.Film;
import Cinema.Movie.model.Genre;
import Cinema.Movie.model.Nationalite;
import Cinema.Movie.model.Personne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FilmMapper {

    @Autowired
    private MediaMapper mediaMapper;

    public FilmDto toDto(Film film) {
        if (film == null) return null;

        List<Long> acteurIds = film.getActeurs() != null
            ? film.getActeurs().stream().map(Personne::getId).collect(Collectors.toList())
            : new ArrayList<>();

        List<MediaDto> mediaDtos = film.getMedias() != null
                ? film.getMedias().stream().map(mediaMapper::toDto).collect(Collectors.toList())
                : new ArrayList<>();


        return new FilmDto(
                film.getId(),
                film.getTitre(),
                film.getDescription(),
                film.getDuree(),
                film.getDateSortie(),
                film.getAnnee(),

                // Genre ID et Libellé
                film.getGenre() != null ? film.getGenre().getId() : null,
                film.getGenre() != null ? film.getGenre().getLibelle() : null,

                // Nationalité ID et Libellé
                film.getNationalite() != null ? film.getNationalite().getId() : null,
                film.getNationalite() != null ? film.getNationalite().getLibelle() : null,

                // Réalisateur ID et Nom/Prénom
                film.getRealisateur() != null ? film.getRealisateur().getId() : null,
                film.getRealisateur() != null ? film.getRealisateur().getPrenom() + " " + film.getRealisateur().getNom() : null,

                acteurIds,
                film.getPhotoUrl(),
                mediaDtos
        );
    }

    public Film toEntity(FilmDto dto) {
        if (dto == null) return null;
        Film film = new Film();
        film.setId(dto.id());
        film.setTitre(dto.titre());
        film.setDuree(dto.duree());
        film.setAnnee(dto.annee());
        film.setPhotoUrl(dto.photoUrl());

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
        if (dto.acteurIds() != null) {
            List<Personne> acteurs = dto.acteurIds().stream().map(aId -> {
                Personne p = new Personne();
                p.setId(aId);
                return p;
            }).collect(Collectors.toList());
            film.setActeurs(acteurs);
        }
        return film;
    }
}