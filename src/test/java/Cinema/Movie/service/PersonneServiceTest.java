package Cinema.Movie.service;

import Cinema.Movie.model.Personne;
import Cinema.Movie.repository.PersonneRepository;
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
class PersonneServiceTest {

    @Mock
    private PersonneRepository personneRepository;

    @InjectMocks
    private PersonneService personneService;

    private Personne personne;

    @BeforeEach
    void setUp() {
        personne = new Personne();
        personne.setId(1L);
        // Ajustez les attributs si besoin (ex: personne.setNom("Nolan"); personne.setPrenom("Christopher");)
    }

    @Test
    @DisplayName("Devrait sauvegarder une personne")
    void shouldSavePersonne() {
        // Arrange
        when(personneRepository.save(any(Personne.class))).thenReturn(personne);

        // Act
        Personne savedPersonne = personneService.save(personne);

        // Assert
        assertThat(savedPersonne).isNotNull();
        assertThat(savedPersonne.getId()).isEqualTo(1L);
        verify(personneRepository, times(1)).save(personne);
    }

    @Test
    @DisplayName("Devrait récupérer une personne par son ID")
    void shouldGetPersonneById() {
        // Arrange
        when(personneRepository.findById(1L)).thenReturn(Optional.of(personne));

        // Act
        Personne foundPersonne = personneService.get(1L);

        // Assert
        assertThat(foundPersonne).isNotNull();
        assertThat(foundPersonne.getId()).isEqualTo(1L);
        verify(personneRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Devrait lever une exception si la personne n'existe pas lors du get")
    void shouldThrowExceptionWhenPersonneNotFoundOnGet() {
        // Arrange
        when(personneRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> personneService.get(99L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Devrait retourner la liste de toutes les personnes")
    void shouldGetListAll() {
        // Arrange
        when(personneRepository.findAll()).thenReturn(List.of(personne));

        // Act
        List<Personne> personnes = personneService.getListAll();

        // Assert
        assertThat(personnes).hasSize(1);
        verify(personneRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Devrait retourner une page de personnes")
    void shouldGetListPaged() {
        // Arrange
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.Direction.ASC, "id");
        Page<Personne> personnePage = new PageImpl<>(List.of(personne));
        when(personneRepository.findAll(pageRequest)).thenReturn(personnePage);

        // Act
        Page<Personne> result = personneService.getList(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(personneRepository, times(1)).findAll(pageRequest);
    }

    @Test
    @DisplayName("Devrait mettre à jour une personne existante")
    void shouldUpdatePersonne() {
        // Arrange
        when(personneRepository.findById(1L)).thenReturn(Optional.of(personne));
        when(personneRepository.save(any(Personne.class))).thenReturn(personne);

        // Act
        personneService.update(personne);

        // Assert
        verify(personneRepository, times(1)).findById(1L);
        verify(personneRepository, times(1)).save(personne);
    }

    @Test
    @DisplayName("Devrait supprimer une personne par son ID")
    void shouldDeletePersonne() {
        // Arrange
        doNothing().when(personneRepository).deleteById(1L);

        // Act
        personneService.delete(1L);

        // Assert
        verify(personneRepository, times(1)).deleteById(1L);
    }
}