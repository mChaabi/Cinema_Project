package Cinema.Movie.service;

import Cinema.Movie.model.Nationalite;
import Cinema.Movie.repository.NationaliteRepository;
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
class NationaliteServiceTest {

    @Mock
    private NationaliteRepository nationaliteRepository;

    @InjectMocks
    private NationaliteService nationaliteService;

    private Nationalite nationalite;

    @BeforeEach
    void setUp() {
        nationalite = new Nationalite();
        nationalite.setId(1L);
        // Ajustez les attributs si nécessaire (ex: nationalite.setLibelle("Marocaine");)
    }

    @Test
    @DisplayName("Devrait sauvegarder une nationalité")
    void shouldSaveNationalite() {
        // Arrange
        when(nationaliteRepository.save(any(Nationalite.class))).thenReturn(nationalite);

        // Act
        Nationalite savedNationalite = nationaliteService.save(nationalite);

        // Assert
        assertThat(savedNationalite).isNotNull();
        assertThat(savedNationalite.getId()).isEqualTo(1L);
        verify(nationaliteRepository, times(1)).save(nationalite);
    }

    @Test
    @DisplayName("Devrait récupérer une nationalité par son ID")
    void shouldGetNationaliteById() {
        // Arrange
        when(nationaliteRepository.findById(1L)).thenReturn(Optional.of(nationalite));

        // Act
        Nationalite foundNationalite = nationaliteService.get(1L);

        // Assert
        assertThat(foundNationalite).isNotNull();
        assertThat(foundNationalite.getId()).isEqualTo(1L);
        verify(nationaliteRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Devrait lever une exception si la nationalité n'existe pas lors du get")
    void shouldThrowExceptionWhenNationaliteNotFoundOnGet() {
        // Arrange
        when(nationaliteRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> nationaliteService.get(99L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Devrait retourner la liste de toutes les nationalités")
    void shouldGetListAll() {
        // Arrange
        when(nationaliteRepository.findAll()).thenReturn(List.of(nationalite));

        // Act
        List<Nationalite> nationalites = nationaliteService.getListAll();

        // Assert
        assertThat(nationalites).hasSize(1);
        verify(nationaliteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Devrait retourner une page de nationalités")
    void shouldGetListPaged() {
        // Arrange
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.Direction.ASC, "id");
        Page<Nationalite> nationalitePage = new PageImpl<>(List.of(nationalite));
        when(nationaliteRepository.findAll(pageRequest)).thenReturn(nationalitePage);

        // Act
        Page<Nationalite> result = nationaliteService.getList(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(nationaliteRepository, times(1)).findAll(pageRequest);
    }

    @Test
    @DisplayName("Devrait mettre à jour une nationalité existante")
    void shouldUpdateNationalite() {
        // Arrange
        when(nationaliteRepository.findById(1L)).thenReturn(Optional.of(nationalite));
        when(nationaliteRepository.save(any(Nationalite.class))).thenReturn(nationalite);

        // Act
        nationaliteService.update(nationalite);

        // Assert
        verify(nationaliteRepository, times(1)).findById(1L);
        verify(nationaliteRepository, times(1)).save(nationalite);
    }

    @Test
    @DisplayName("Devrait supprimer une nationalité par son ID")
    void shouldDeleteNationalite() {
        // Arrange
        doNothing().when(nationaliteRepository).deleteById(1L);

        // Act
        nationaliteService.delete(1L);

        // Assert
        verify(nationaliteRepository, times(1)).deleteById(1L);
    }
}