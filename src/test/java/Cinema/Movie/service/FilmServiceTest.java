package Cinema.Movie.service;

import Cinema.Movie.model.Film;
import Cinema.Movie.repository.FilmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private FilmService filmService;

    private Film film;

    @BeforeEach
    void setUp() {
        film = new Film();
        film.setId(1L);
        film.setTitre("Inception");
        film.setDuree(148);
        film.setAnnee(2010);
    }

    @Test
    @DisplayName("Devrait sauvegarder un film")
    void shouldSaveFilm() {
        // Arrange
        when(filmRepository.save(any(Film.class))).thenReturn(film);

        // Act
        Film savedFilm = filmService.save(film);

        // Assert
        assertThat(savedFilm).isNotNull();
        assertThat(savedFilm.getTitre()).isEqualTo("Inception");
        verify(filmRepository, times(1)).save(film);
    }

    @Test
    @DisplayName("Devrait récupérer un film par son ID")
    void shouldGetFilmById() {
        // Arrange
        when(filmRepository.findById(1L)).thenReturn(Optional.of(film));

        // Act
        Film foundFilm = filmService.get(1L);

        // Assert
        assertThat(foundFilm).isNotNull();
        assertThat(foundFilm.getId()).isEqualTo(1L);
        verify(filmRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Devrait lever une exception si le film n'existe pas lors du get")
    void shouldThrowExceptionWhenFilmNotFoundOnGet() {
        // Arrange
        when(filmRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> filmService.get(99L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Devrait retourner la liste de tous les films")
    void shouldGetListAll() {
        // Arrange
        when(filmRepository.findAll()).thenReturn(List.of(film));

        // Act
        List<Film> films = filmService.getListAll();

        // Assert
        assertThat(films).hasSize(1);
        assertThat(films.get(0).getTitre()).isEqualTo("Inception");
        verify(filmRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Devrait retourner une page de films")
    void shouldGetListPaged() {
        // Arrange
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.Direction.ASC, "id");
        Page<Film> filmPage = new PageImpl<>(List.of(film));
        when(filmRepository.findAll(pageRequest)).thenReturn(filmPage);

        // Act
        Page<Film> result = filmService.getList(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(filmRepository, times(1)).findAll(pageRequest);
    }

    @Test
    @DisplayName("Devrait mettre à jour un film existant")
    void shouldUpdateFilm() {
        // Arrange
        when(filmRepository.findById(1L)).thenReturn(Optional.of(film));
        when(filmRepository.save(any(Film.class))).thenReturn(film);

        // Act
        filmService.update(film);

        // Assert
        verify(filmRepository, times(1)).findById(1L);
        verify(filmRepository, times(1)).save(film);
    }

    @Test
    @DisplayName("Devrait supprimer un film par son ID")
    void shouldDeleteFilm() {
        // Arrange
        doNothing().when(filmRepository).deleteById(1L);

        // Act
        filmService.delete(1L);

        // Assert
        verify(filmRepository, times(1)).deleteById(1L);
    }
}