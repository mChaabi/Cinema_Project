package Cinema.Movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import Cinema.Movie.model.Salle;


@CrossOrigin("http://localhost:4200")
@Repository
public interface SalleRepository extends JpaRepository<Salle, Long> {

}
