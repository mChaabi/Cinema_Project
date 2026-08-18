package Cinema.Movie.service;

import Cinema.Movie.model.Genre;
import Cinema.Movie.repository.GenreRepository;
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
class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService;

    private Genre genre;

    @BeforeEach
    void setUp() {
        genre = new Genre();
        genre.setId(1L);
        // Ajustez le nom de l'attribut selon votre entité Genre (ex: nom, libelle, etc.)
    }

    @Test
    @DisplayName("Devrait sauvegarder un genre")
    void shouldSaveGenre() {
        // Arrange
        when(genreRepository.save(any(Genre.class))).thenReturn(genre);

        // Act
        Genre savedGenre = genreService.save(genre);

        // Assert
        assertThat(savedGenre).isNotNull();
        assertThat(savedGenre.getId()).isEqualTo(1L);
        verify(genreRepository, times(1)).save(genre);
    }

    @Test
    @DisplayName("Devrait récupérer un genre par son ID")
    void shouldGetGenreById() {
        // Arrange
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));

        // Act
        Genre foundGenre = genreService.get(1L);

        // Assert
        assertThat(foundGenre).isNotNull();
        assertThat(foundGenre.getId()).isEqualTo(1L);
        verify(genreRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Devrait lever une exception si le genre n'existe pas lors du get")
    void shouldThrowExceptionWhenGenreNotFoundOnGet() {
        // Arrange
        when(genreRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> genreService.get(99L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Devrait retourner la liste de tous les genres")
    void shouldGetListAll() {
        // Arrange
        when(genreRepository.findAll()).thenReturn(List.of(genre));

        // Act
        List<Genre> genres = genreService.getListAll();

        // Assert
        assertThat(genres).hasSize(1);
        verify(genreRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Devrait retourner une page de genres")
    void shouldGetListPaged() {
        // Arrange
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.Direction.ASC, "id");
        Page<Genre> genrePage = new PageImpl<>(List.of(genre));
        when(genreRepository.findAll(pageRequest)).thenReturn(genrePage);

        // Act
        Page<Genre> result = genreService.getList(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(genreRepository, times(1)).findAll(pageRequest);
    }

    @Test
    @DisplayName("Devrait mettre à jour un genre existant")
    void shouldUpdateGenre() {
        // Arrange
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        when(genreRepository.save(any(Genre.class))).thenReturn(genre);

        // Act
        genreService.update(genre);

        // Assert
        verify(genreRepository, times(1)).findById(1L);
        verify(genreRepository, times(1)).save(genre);
    }

    @Test
    @DisplayName("Devrait supprimer un genre par son ID")
    void shouldDeleteGenre() {
        // Arrange
        doNothing().when(genreRepository).deleteById(1L);

        // Act
        genreService.delete(1L);

        // Assert
        verify(genreRepository, times(1)).deleteById(1L);
    }
}