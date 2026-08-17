package Cinema.Movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import  Cinema.Movie.model.Genre;

@CrossOrigin("http://localhost:4200")
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

}
