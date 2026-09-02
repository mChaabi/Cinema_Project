package Cinema.Movie.service;

import Cinema.Movie.model.Film;
import Cinema.Movie.repository.FilmRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FilmService extends AbstractService<Film, Long> {

    @Autowired
    private FilmRepository filmRepository;

    @Override
    protected JpaRepository<Film, Long> getRepository() {
        return filmRepository;
    }

    public List<Film> getUpcoming(LocalDate date) {
        // Asegúrate de tener un campo de fecha compatible en tu entidad o repositorio
        return filmRepository.findByAnneeGreaterThan(date.getYear());
    }

}
