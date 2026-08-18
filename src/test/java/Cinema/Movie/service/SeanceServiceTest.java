package Cinema.Movie.service;

import Cinema.Movie.model.Seance;
import Cinema.Movie.repository.SeanceRepository;
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

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeanceServiceTest {

    @Mock
    private SeanceRepository seanceRepository;

    @InjectMocks
    private SeanceService seanceService;

    private Seance seance;

    @BeforeEach
    void setUp() {
        seance = new Seance();
        seance.setId(1L);
        seance.setDateProjection(new Date());
    }

    @Test
    @DisplayName("Devrait sauvegarder une séance")
    void shouldSaveSeance() {
        // Arrange
        when(seanceRepository.save(any(Seance.class))).thenReturn(seance);

        // Act
        Seance savedSeance = seanceService.save(seance);

        // Assert
        assertThat(savedSeance).isNotNull();
        assertThat(savedSeance.getId()).isEqualTo(1L);
        verify(seanceRepository, times(1)).save(seance);
    }

    @Test
    @DisplayName("Devrait récupérer une séance par son ID")
    void shouldGetSeanceById() {
        // Arrange
        when(seanceRepository.findById(1L)).thenReturn(Optional.of(seance));

        // Act
        Seance foundSeance = seanceService.get(1L);

        // Assert
        assertThat(foundSeance).isNotNull();
        assertThat(foundSeance.getId()).isEqualTo(1L);
        verify(seanceRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Devrait lever une exception si la séance n'existe pas lors du get")
    void shouldThrowExceptionWhenSeanceNotFoundOnGet() {
        // Arrange
        when(seanceRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> seanceService.get(99L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Devrait retourner la liste de toutes les séances")
    void shouldGetListAll() {
        // Arrange
        when(seanceRepository.findAll()).thenReturn(List.of(seance));

        // Act
        List<Seance> seances = seanceService.getListAll();

        // Assert
        assertThat(seances).hasSize(1);
        verify(seanceRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Devrait retourner les séances correspondant à une date spécifique")
    void shouldGetSeancesParDate() {
        // Arrange
        Date targetDate = new Date();
        when(seanceRepository.findByDateProjection(targetDate)).thenReturn(List.of(seance));

        // Act
        List<Seance> result = seanceService.getSeancesParDate(targetDate);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        verify(seanceRepository, times(1)).findByDateProjection(targetDate);
    }

    @Test
    @DisplayName("Devrait retourner une page de séances")
    void shouldGetListPaged() {
        // Arrange
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.Direction.ASC, "id");
        Page<Seance> seancePage = new PageImpl<>(List.of(seance));
        when(seanceRepository.findAll(pageRequest)).thenReturn(seancePage);

        // Act
        Page<Seance> result = seanceService.getList(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(seanceRepository, times(1)).findAll(pageRequest);
    }

    @Test
    @DisplayName("Devrait mettre à jour une séance existante")
    void shouldUpdateSeance() {
        // Arrange
        when(seanceRepository.findById(1L)).thenReturn(Optional.of(seance));
        when(seanceRepository.save(any(Seance.class))).thenReturn(seance);

        // Act
        seanceService.update(seance);

        // Assert
        verify(seanceRepository, times(1)).findById(1L);
        verify(seanceRepository, times(1)).save(seance);
    }

    @Test
    @DisplayName("Devrait supprimer une séance par son ID")
    void shouldDeleteSeance() {
        // Arrange
        doNothing().when(seanceRepository).deleteById(1L);

        // Act
        seanceService.delete(1L);

        // Assert
        verify(seanceRepository, times(1)).deleteById(1L);
    }
}