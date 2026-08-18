package Cinema.Movie.repository;

import Cinema.Movie.model.Seance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SeanceRepositoryTest {

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Devrait retourner la liste des séances correspondant à la date de projection")
    void shouldFindByDateProjection() {
        // Arrange
        Date targetDate = parseDate("2026-05-10");
        Date otherDate = parseDate("2026-05-11");

        Seance seance1 = new Seance();
        seance1.setDateProjection(targetDate);

        Seance seance2 = new Seance();
        seance2.setDateProjection(targetDate);

        Seance seanceOther = new Seance();
        seanceOther.setDateProjection(otherDate);

        entityManager.persist(seance1);
        entityManager.persist(seance2);
        entityManager.persist(seanceOther);
        entityManager.flush();

        // Act
        List<Seance> result = seanceRepository.findByDateProjection(targetDate);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).extracting(Seance::getDateProjection)
                .containsOnly(targetDate);
    }

    @Test
    @DisplayName("Devrait retourner une liste vide si aucune séance ne correspond à la date")
    void shouldReturnEmptyListWhenNoSeanceMatchesDate() {
        // Arrange
        Date targetDate = parseDate("2026-05-10");

        // Act
        List<Seance> result = seanceRepository.findByDateProjection(targetDate);

        // Assert
        assertThat(result).isEmpty();
    }
}