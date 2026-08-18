package Cinema.Movie.dto;

import Cinema.Movie.model.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    // Convertir Entité -> DTO
    public GenreDto toDto(Genre genre) {
        if (genre == null) {
            return null;
        }
        return new GenreDto(genre.getId(), genre.getLibelle());
    }

    // Convertir DTO -> Entité
    public Genre toEntity(GenreDto genreDto) {
        if (genreDto == null) {
            return null;
        }
        Genre genre = new Genre();
        genre.setId(genreDto.id());
        genre.setLibelle(genreDto.libelle());
        return genre;
    }
}