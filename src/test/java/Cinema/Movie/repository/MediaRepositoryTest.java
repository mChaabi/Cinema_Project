package Cinema.Movie.repository;

import Cinema.Movie.model.Media;
import Cinema.Movie.model.Media.TypeMedia;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class MediaRepositoryTest {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Doit sauvegarder un média et le retrouver par son ID")
    void shouldSaveAndFindMediaById() {
        // Given
        Media media = new Media();
        media.setMedia("poster_inception.jpg");
        media.setTypeMedia(TypeMedia.IMAGE);

        // When
        Media savedMedia = mediaRepository.save(media);
        Optional<Media> foundMedia = mediaRepository.findById(savedMedia.getId());

        // Then
        assertThat(foundMedia).isPresent();
        assertThat(foundMedia.get().getMedia()).isEqualTo("poster_inception.jpg");
        assertThat(foundMedia.get().getTypeMedia()).isEqualTo(TypeMedia.IMAGE);
    }

    @Test
    @DisplayName("Doit retourner la liste de tous les médias")
    void shouldReturnAllMedias() {
        // Given
        Media media1 = new Media();
        media1.setMedia("trailer.mp4");
        media1.setTypeMedia(TypeMedia.VIDEO);

        Media media2 = new Media();
        media2.setMedia("banner.png");
        media2.setTypeMedia(TypeMedia.IMAGE);

        entityManager.persistAndFlush(media1);
        entityManager.persistAndFlush(media2);

        // When
        List<Media> medias = mediaRepository.findAll();

        // Then
        assertThat(medias).hasSize(2);
    }

    @Test
    @DisplayName("Doit lever une exception si le nom du média est null")
    void shouldFailWhenMediaIsNull() {
        // Given
        Media media = new Media();
        media.setMedia(null); // Viol de la contrainte @Column(nullable = false)
        media.setTypeMedia(TypeMedia.DOCUMENT);

        // When / Then
        assertThatThrownBy(() -> mediaRepository.saveAndFlush(media))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Doit supprimer un média par son ID")
    void shouldDeleteMediaById() {
        // Given
        Media media = new Media();
        media.setMedia("doc_synopsis.pdf");
        media.setTypeMedia(TypeMedia.DOCUMENT);
        Media savedMedia = entityManager.persistAndFlush(media);

        // When
        mediaRepository.deleteById(savedMedia.getId());

        // Then
        Optional<Media> deletedMedia = mediaRepository.findById(savedMedia.getId());
        assertThat(deletedMedia).isEmpty();
    }
}