package Cinema.Movie.dto;

import Cinema.Movie.model.Film;
import Cinema.Movie.model.Media;
import org.springframework.stereotype.Component;

@Component
public class MediaMapper {

    public MediaDto toDto(Media media) {
        if (media == null) {
            return null;
        }
        Long filmId = (media.getFilm() != null) ? media.getFilm().getId() : null;
        return new MediaDto(
            media.getId(),
            media.getMedia(),
            media.getTypeMedia(),
            filmId
        );
    }

    public Media toEntity(MediaDto mediaDto) {
        if (mediaDto == null) {
            return null;
        }
        Media media = new Media();
        media.setId(mediaDto.id());
        media.setMedia(mediaDto.media());
        media.setTypeMedia(mediaDto.typeMedia());

        if (mediaDto.filmId() != null) {
            Film film = new Film();
            film.setId(mediaDto.filmId());
            media.setFilm(film);
        }

        return media;
    }
}