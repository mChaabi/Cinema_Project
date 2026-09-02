package Cinema.Movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import Cinema.Movie.Projection.InlineFilm;
import Cinema.Movie.model.Film;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@Repository
@RepositoryRestResource(excerptProjection = InlineFilm.class)
public interface FilmRepository extends JpaRepository<Film, Long> {

    // Ejemplo de método personalizado si quieres buscar películas por año superior al actual
    List<Film> findByAnneeGreaterThan(int annee);

    // Ejemplo si quieres buscar películas que contengan una palabra en su descripción
    List<Film> findByDescriptionContainingIgnoreCase(String keyword);
}