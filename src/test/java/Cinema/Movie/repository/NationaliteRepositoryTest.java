package Cinema.Movie.repository;

import Cinema.Movie.model.Nationalite;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class NationaliteRepositoryTest {

    @Autowired
    private NationaliteRepository nationaliteRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Doit sauvegarder une nationalité et la retrouver par son ID")
    void shouldSaveAndFindNationaliteById() {
        // Given
        Nationalite nationalite = new Nationalite();
        nationalite.setLibelle("Marocaine");

        // When
        Nationalite saved = nationaliteRepository.save(nationalite);
        Optional<Nationalite> found = nationaliteRepository.findById(saved.getId());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getLibelle()).isEqualTo("Marocaine");
    }

    @Test
    @DisplayName("Doit filtrer les nationalités par préfixe du libellé avec pagination")
    void shouldFindByLibelleStartsWithWithPagination() {
        // Given
        Nationalite nat1 = new Nationalite();
        nat1.setLibelle("Marocaine");

        Nationalite nat2 = new Nationalite();
        nat2.setLibelle("Martiniquaise");

        Nationalite nat3 = new Nationalite();
        nat3.setLibelle("Française");

        entityManager.persistAndFlush(nat1);
        entityManager.persistAndFlush(nat2);
        entityManager.persistAndFlush(nat3);

        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Nationalite> pageResult = nationaliteRepository.findByLibelleStartsWith("Mar", pageable);

        // Then
        assertThat(pageResult.getContent()).hasSize(2);
        assertThat(pageResult.getTotalElements()).isEqualTo(2);
        assertThat(pageResult.getContent())
                .extracting(Nationalite::getLibelle)
                .containsExactlyInAnyOrder("Marocaine", "Martiniquaise");
    }

    @Test
    @DisplayName("Doit retourner une page vide si aucun libellé ne commence par le préfixe")
    void shouldReturnEmptyPageWhenNoMatch() {
        // Given
        Nationalite nat = new Nationalite();
        nat.setLibelle("Espagnole");
        entityManager.persistAndFlush(nat);

        Pageable pageable = PageRequest.of(0, 5);

        // When
        Page<Nationalite> pageResult = nationaliteRepository.findByLibelleStartsWith("Ang", pageable);

        // Then
        assertThat(pageResult.getContent()).isEmpty();
        assertThat(pageResult.getTotalElements()).isEqualTo(0);
    }

    @Test
    @DisplayName("Doit lever une exception si le libellé est null")
    void shouldFailWhenLibelleIsNull() {
        // Given
        Nationalite nationalite = new Nationalite();
        nationalite.setLibelle(null); // Contrainte @Column(nullable = false)

        // When / Then
        assertThatThrownBy(() -> nationaliteRepository.saveAndFlush(nationalite))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Doit supprimer une nationalité par son ID")
    void shouldDeleteNationaliteById() {
        // Given
        Nationalite nationalite = new Nationalite();
        nationalite.setLibelle("Italienne");
        Nationalite saved = entityManager.persistAndFlush(nationalite);

        // When
        nationaliteRepository.deleteById(saved.getId());

        // Then
        Optional<Nationalite> deleted = nationaliteRepository.findById(saved.getId());
        assertThat(deleted).isEmpty();
    }
}