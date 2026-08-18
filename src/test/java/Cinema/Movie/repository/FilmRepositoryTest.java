package Cinema.Movie.repository;

import Cinema.Movie.model.Film;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FilmRepositoryTest {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private TestEntityManager entityManager; 

    @Test
    @DisplayName("Doit sauvegarder un film et le retrouver par son ID")
    void shouldSaveAndFindFilmById() {
        // Given
        Film film = new Film();
        film.setTitre("Inception");
        film.setDuree(148);

        // When
        Film savedFilm = filmRepository.save(film);
        Optional<Film> foundFilm = filmRepository.findById(savedFilm.getId());

        // Then
        assertThat(foundFilm).isPresent();
        assertThat(foundFilm.get().getTitre()).isEqualTo("Inception");
    }

    @Test
    @DisplayName("Doit retourner la liste de tous les films")
    void shouldReturnAllFilms() {
        // Given
        Film film1 = new Film();
        film1.setTitre("Avatar");
        
        Film film2 = new Film();
        film2.setTitre("Titanic");

        entityManager.persistAndFlush(film1);
        entityManager.persistAndFlush(film2);

        // When
        List<Film> films = filmRepository.findAll();

        // Then
        assertThat(films).hasSize(2);
    }

    @Test
    @DisplayName("Doit supprimer un film par son ID")
    void shouldDeleteFilmById() {
        // Given
        Film film = new Film();
        film.setTitre("Matrix");
        Film savedFilm = entityManager.persistAndFlush(film);

        // When
        filmRepository.deleteById(savedFilm.getId());

        // Then
        Optional<Film> deletedFilm = filmRepository.findById(savedFilm.getId());
        assertThat(deletedFilm).isEmpty();
    }
}