package Cinema.Movie.repository;

import Cinema.Movie.model.Salle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SalleRepositoryTest {

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Doit sauvegarder une salle et la retrouver par son ID")
    void shouldSaveAndFindSalleById() {
        // Given
        Salle salle = new Salle();
        salle.setNumero(1);
        salle.setCapacite(150);

        // When
        Salle savedSalle = salleRepository.save(salle);
        Optional<Salle> foundSalle = salleRepository.findById(savedSalle.getId());

        // Then
        assertThat(foundSalle).isPresent();
        assertThat(foundSalle.get().getNumero()).isEqualTo(1);
        assertThat(foundSalle.get().getCapacite()).isEqualTo(150);
    }

    @Test
    @DisplayName("Doit retourner la liste de toutes les salles")
    void shouldReturnAllSalles() {
        // Given
        Salle salle1 = new Salle();
        salle1.setNumero(10);
        salle1.setCapacite(200);

        Salle salle2 = new Salle();
        salle2.setNumero(12);
        salle2.setCapacite(80);

        entityManager.persistAndFlush(salle1);
        entityManager.persistAndFlush(salle2);

        // When
        List<Salle> salles = salleRepository.findAll();

        // Then
        assertThat(salles).hasSize(2);
        assertThat(salles)
                .extracting(Salle::getNumero)
                .containsExactlyInAnyOrder(10, 12);
    }

    @Test
    @DisplayName("Doit mettre à jour la capacité d'une salle")
    void shouldUpdateSalleCapacite() {
        // Given
        Salle salle = new Salle();
        salle.setNumero(5);
        salle.setCapacite(100);
        Salle saved = entityManager.persistAndFlush(salle);

        // When
        saved.setCapacite(120);
        Salle updated = salleRepository.save(saved);

        // Then
        assertThat(updated.getCapacite()).isEqualTo(120);
    }

    @Test
    @DisplayName("Doit supprimer une salle par son ID")
    void shouldDeleteSalleById() {
        // Given
        Salle salle = new Salle();
        salle.setNumero(3);
        salle.setCapacite(90);
        Salle saved = entityManager.persistAndFlush(salle);

        // When
        salleRepository.deleteById(saved.getId());

        // Then
        Optional<Salle> deletedSalle = salleRepository.findById(saved.getId());
        assertThat(deletedSalle).isEmpty();
    }
}