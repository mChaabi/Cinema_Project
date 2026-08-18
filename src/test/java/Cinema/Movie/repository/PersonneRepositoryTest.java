package Cinema.Movie.repository;

import Cinema.Movie.model.Personne;
import Cinema.Movie.model.Personne.TypePersonne;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class PersonneRepositoryTest {

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Doit sauvegarder une personne et la retrouver par son ID")
    void shouldSaveAndFindPersonneById() {
        // Given
        Personne personne = new Personne();
        personne.setNom("Nolan");
        personne.setPrenom("Christopher");
        personne.setTypePersonne(TypePersonne.REALISATEUR);

        // When
        Personne saved = personneRepository.save(personne);
        Optional<Personne> found = personneRepository.findById(saved.getId());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getNom()).isEqualTo("Nolan");
        assertThat(found.get().getTypePersonne()).isEqualTo(TypePersonne.REALISATEUR);
    }

    @Test
    @DisplayName("Doit filtrer les personnes par TypePersonne avec pagination")
    void shouldFindByTypePersonne() {
        // Given
        Personne p1 = new Personne();
        p1.setNom("DiCaprio");
        p1.setPrenom("Leonardo");
        p1.setTypePersonne(TypePersonne.ACTEUR);

        Personne p2 = new Personne();
        p2.setNom("Hardy");
        p2.setPrenom("Tom");
        p2.setTypePersonne(TypePersonne.ACTEUR);

        Personne p3 = new Personne();
        p3.setNom("Spielberg");
        p3.setPrenom("Steven");
        p3.setTypePersonne(TypePersonne.REALISATEUR);

        entityManager.persistAndFlush(p1);
        entityManager.persistAndFlush(p2);
        entityManager.persistAndFlush(p3);

        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Personne> acteursPage = personneRepository.findByTypePersonne(TypePersonne.ACTEUR, pageable);

        // Then
        assertThat(acteursPage.getContent()).hasSize(2);
        assertThat(acteursPage.getContent())
                .extracting(Personne::getNom)
                .containsExactlyInAnyOrder("DiCaprio", "Hardy");
    }

    @Test
    @DisplayName("Doit trouver les personnes nées après ou à une date précise")
    void shouldFindByDateNaissanceGreaterThanEqual() {
        // Given
        Personne p1 = new Personne();
        p1.setNom("Ancien");
        p1.setPrenom("Acteur");
        p1.setTypePersonne(TypePersonne.ACTEUR);
        p1.setDateNaissance(Date.valueOf("1970-01-01"));

        Personne p2 = new Personne();
        p2.setNom("Recent");
        p2.setPrenom("Acteur");
        p2.setTypePersonne(TypePersonne.ACTEUR);
        p2.setDateNaissance(Date.valueOf("1995-05-15"));

        entityManager.persistAndFlush(p1);
        entityManager.persistAndFlush(p2);

        Pageable pageable = PageRequest.of(0, 5);

        // When
        Page<Personne> result = personneRepository.findByDateNaissanceGreaterThanEqual(Date.valueOf("1990-01-01"), pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getNom()).isEqualTo("Recent");
    }

    @Test
    @DisplayName("Doit rechercher par nom ou prénom (recherche partielle / conprenant)")
    void shouldFindByNomContainingOrPrenomContaining() {
        // Given
        Personne p1 = new Personne();
        p1.setNom("Bale");
        p1.setPrenom("Christian");
        p1.setTypePersonne(TypePersonne.ACTEUR);

        Personne p2 = new Personne();
        p2.setNom("Murphy");
        p2.setPrenom("Cillian");
        p2.setTypePersonne(TypePersonne.ACTEUR);

        entityManager.persistAndFlush(p1);
        entityManager.persistAndFlush(p2);

        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Personne> result = personneRepository.findByNomContainingOrPrenomContaining("Chris", "Chris", pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getPrenom()).isEqualTo("Christian");
    }

    @Test
    @DisplayName("Doit lever une exception si le nom ou prénom est null")
    void shouldFailWhenNomIsNull() {
        // Given
        Personne personne = new Personne();
        personne.setNom(null); // Respect de la contrainte @Column(nullable = false)
        personne.setPrenom("Test");
        personne.setTypePersonne(TypePersonne.ACTEUR);

        // When / Then
        assertThatThrownBy(() -> personneRepository.saveAndFlush(personne))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Doit supprimer une personne par son ID")
    void shouldDeletePersonneById() {
        // Given
        Personne personne = new Personne();
        personne.setNom("Pitt");
        personne.setPrenom("Brad");
        personne.setTypePersonne(TypePersonne.ACTEUR);
        Personne saved = entityManager.persistAndFlush(personne);

        // When
        personneRepository.deleteById(saved.getId());

        // Then
        Optional<Personne> deleted = personneRepository.findById(saved.getId());
        assertThat(deleted).isEmpty();
    }
}