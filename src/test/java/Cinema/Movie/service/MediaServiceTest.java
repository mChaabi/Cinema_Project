package Cinema.Movie.service;

import Cinema.Movie.model.Media;
import Cinema.Movie.repository.MediaRepository;
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
class MediaServiceTest {

    @Mock
    private MediaRepository mediaRepository;

    @InjectMocks
    private MediaService mediaService;

    private Media media;

    @BeforeEach
    void setUp() {
        media = new Media();
        media.setId(1L);
        // Ajustez les attributs spécifiques selon la classe Media (ex: media.setPath("trailer.mp4");)
    }

    @Test
    @DisplayName("Devrait sauvegarder un média")
    void shouldSaveMedia() {
        // Arrange
        when(mediaRepository.save(any(Media.class))).thenReturn(media);

        // Act
        Media savedMedia = mediaService.save(media);

        // Assert
        assertThat(savedMedia).isNotNull();
        assertThat(savedMedia.getId()).isEqualTo(1L);
        verify(mediaRepository, times(1)).save(media);
    }

    @Test
    @DisplayName("Devrait récupérer un média par son ID")
    void shouldGetMediaById() {
        // Arrange
        when(mediaRepository.findById(1L)).thenReturn(Optional.of(media));

        // Act
        Media foundMedia = mediaService.get(1L);

        // Assert
        assertThat(foundMedia).isNotNull();
        assertThat(foundMedia.getId()).isEqualTo(1L);
        verify(mediaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Devrait lever une exception si le média n'existe pas lors du get")
    void shouldThrowExceptionWhenMediaNotFoundOnGet() {
        // Arrange
        when(mediaRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> mediaService.get(99L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Devrait retourner la liste de tous les médias")
    void shouldGetListAll() {
        // Arrange
        when(mediaRepository.findAll()).thenReturn(List.of(media));

        // Act
        List<Media> medias = mediaService.getListAll();

        // Assert
        assertThat(medias).hasSize(1);
        verify(mediaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Devrait retourner une page de médias")
    void shouldGetListPaged() {
        // Arrange
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.Direction.ASC, "id");
        Page<Media> mediaPage = new PageImpl<>(List.of(media));
        when(mediaRepository.findAll(pageRequest)).thenReturn(mediaPage);

        // Act
        Page<Media> result = mediaService.getList(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(mediaRepository, times(1)).findAll(pageRequest);
    }

    @Test
    @DisplayName("Devrait mettre à jour un média existant")
    void shouldUpdateMedia() {
        // Arrange
        when(mediaRepository.findById(1L)).thenReturn(Optional.of(media));
        when(mediaRepository.save(any(Media.class))).thenReturn(media);

        // Act
        mediaService.update(media);

        // Assert
        verify(mediaRepository, times(1)).findById(1L);
        verify(mediaRepository, times(1)).save(media);
    }

    @Test
    @DisplayName("Devrait supprimer un média par son ID")
    void shouldDeleteMedia() {
        // Arrange
        doNothing().when(mediaRepository).deleteById(1L);

        // Act
        mediaService.delete(1L);

        // Assert
        verify(mediaRepository, times(1)).deleteById(1L);
    }
}