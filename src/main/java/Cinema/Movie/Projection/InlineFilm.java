package Cinema.Movie.Projection;

import java.util.Date;
import java.util.List;
import org.springframework.data.rest.core.config.Projection;

import Cinema.Movie.model.Film;
import Cinema.Movie.model.Genre;
import Cinema.Movie.model.Nationalite;
import Cinema.Movie.model.Personne;
import Cinema.Movie.model.Media;

@Projection(name = "inlineFilm", types = { Film.class })
public interface InlineFilm {

    Long getId();
    String getTitre(); 
    Integer getDuree();
    String getDescription();
    Date getDateSortie();

    Genre getGenre();
    Nationalite getNationalite();
    List<Personne> getActeurs();
    Personne getRealisateur();
    List<Media> getMedia();
}