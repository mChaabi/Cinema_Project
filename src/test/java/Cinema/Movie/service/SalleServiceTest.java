package Cinema.Movie.service;

import Cinema.Movie.model.Salle;
import Cinema.Movie.repository.SalleRepository;
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
class SalleServiceTest {

    @Mock
    private SalleRepository salleRepository;

    @InjectMocks
    private SalleService salleService;

    private Salle salle;

    @BeforeEach
    void setUp() {
        salle = new Salle();
        salle.setId(1L);
        // Ajustez selon les attributs de votre entité Salle (ex: salle.setNumero(1); salle.setCapacite(150);)
    }

    @Test
    @DisplayName("Devrait sauvegarder une salle")
    void shouldSaveSalle() {
        // Arrange
        when(salleRepository.save(any(Salle.class))).thenReturn(salle);

        // Act
        Salle savedSalle = salleService.save(salle);

        // Assert
        assertThat(savedSalle).isNotNull();
        assertThat(savedSalle.getId()).isEqualTo(1L);
        verify(salleRepository, times(1)).save(salle);
    }

    @Test
    @DisplayName("Devrait récupérer une salle par son ID")
    void shouldGetSalleById() {
        // Arrange
        when(salleRepository.findById(1L)).thenReturn(Optional.of(salle));

        // Act
        Salle foundSalle = salleService.get(1L);

        // Assert
        assertThat(foundSalle).isNotNull();
        assertThat(foundSalle.getId()).isEqualTo(1L);
        verify(salleRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Devrait lever une exception si la salle n'existe pas lors du get")
    void shouldThrowExceptionWhenSalleNotFoundOnGet() {
        // Arrange
        when(salleRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> salleService.get(99L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Devrait retourner la liste de toutes les salles")
    void shouldGetListAll() {
        // Arrange
        when(salleRepository.findAll()).thenReturn(List.of(salle));

        // Act
        List<Salle> salles = salleService.getListAll();

        // Assert
        assertThat(salles).hasSize(1);
        verify(salleRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Devrait retourner une page de salles via findPaginated avec le tri par capacité et numéro décroissants")
    void shouldFindPaginatedWithCustomSort() {
        // Arrange
        int page = 0;
        int size = 10;
        PageRequest expectedPageRequest = PageRequest.of(page, size, Sort.by("capacite", "numero").descending());
        Page<Salle> sallePage = new PageImpl<>(List.of(salle));

        when(salleRepository.findAll(expectedPageRequest)).thenReturn(sallePage);

        // Act
        Page<Salle> result = salleService.findPaginated(page, size);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(salleRepository, times(1)).findAll(expectedPageRequest);
    }

    @Test
    @DisplayName("Devrait mettre à jour une salle existante")
    void shouldUpdateSalle() {
        // Arrange
        when(salleRepository.findById(1L)).thenReturn(Optional.of(salle));
        when(salleRepository.save(any(Salle.class))).thenReturn(salle);

        // Act
        salleService.update(salle);

        // Assert
        verify(salleRepository, times(1)).findById(1L);
        verify(salleRepository, times(1)).save(salle);
    }

    @Test
    @DisplayName("Devrait supprimer une salle par son ID")
    void shouldDeleteSalle() {
        // Arrange
        doNothing().when(salleRepository).deleteById(1L);

        // Act
        salleService.delete(1L);

        // Assert
        verify(salleRepository, times(1)).deleteById(1L);
    }
}