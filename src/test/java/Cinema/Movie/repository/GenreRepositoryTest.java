package Cinema.Movie.repository;

import Cinema.Movie.model.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Doit sauvegarder un genre et le retrouver par son ID")
    void shouldSaveAndFindGenreById() {
        // Given
        Genre genre = new Genre();
        genre.setLibelle("Action");

        // When
        Genre savedGenre = genreRepository.save(genre);
        Optional<Genre> foundGenre = genreRepository.findById(savedGenre.getId());

        // Then
        assertThat(foundGenre).isPresent();
        assertThat(foundGenre.get().getLibelle()).isEqualTo("Action");
    }

    @Test
    @DisplayName("Doit retourner la liste de tous les genres")
    void shouldReturnAllGenres() {
        // Given
        Genre genre1 = new Genre();
        genre1.setLibelle("Comédie");

        Genre genre2 = new Genre();
        genre2.setLibelle("Drame");

        entityManager.persistAndFlush(genre1);
        entityManager.persistAndFlush(genre2);

        // When
        List<Genre> genres = genreRepository.findAll();

        // Then
        assertThat(genres).hasSize(2);
    }

    @Test
    @DisplayName("Doit lever une exception si le libellé est null")
    void shouldFailWhenLibelleIsNull() {
        // Given
        Genre genre = new Genre();
        genre.setLibelle(null); 

        // When / Then
        assertThatThrownBy(() -> genreRepository.saveAndFlush(genre))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Doit supprimer un genre par son ID")
    void shouldDeleteGenreById() {
        // Given
        Genre genre = new Genre();
        genre.setLibelle("Horreur");
        Genre savedGenre = entityManager.persistAndFlush(genre);

        // When
        genreRepository.deleteById(savedGenre.getId());

        // Then
        Optional<Genre> deletedGenre = genreRepository.findById(savedGenre.getId());
        assertThat(deletedGenre).isEmpty();
    }
}