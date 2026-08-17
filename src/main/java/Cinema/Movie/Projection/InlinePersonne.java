package Cinema.Movie.Projection;

import java.sql.Date;
import java.util.List;
import org.springframework.data.rest.core.config.Projection;

import Cinema.Movie.model.Film;
import Cinema.Movie.model.Media;
import Cinema.Movie.model.Nationalite;
import Cinema.Movie.model.Personne;
import Cinema.Movie.model.Personne.TypePersonne;

@Projection(name = "inlinePersonne", types = { Personne.class })
public interface InlinePersonne {

    Long getId();
    String getNom();
    String getPrenom();
    Date getDateNaissance();
    TypePersonne getTypePersonne();

    // Inlined Relationships
    Nationalite getNationalite();
    List<Media> getMedia();
    List<Film> getFilms();
}