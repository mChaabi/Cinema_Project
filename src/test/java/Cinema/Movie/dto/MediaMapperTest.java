package Cinema.Movie.dto;

import Cinema.Movie.model.Media;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MediaMapperTest {

    private final MediaMapper mapper = new MediaMapper();

    @Test
    void testToDto() {
        Media media = new Media();
        media.setId(1L);
        media.setMedia("video.mp4");
        media.setTypeMedia(Media.TypeMedia.VIDEO);

        MediaDto dto = mapper.toDto(media);

        assertNotNull(dto);
        assertEquals(1L, dto.id());
        assertEquals("video.mp4", dto.media());
        assertEquals(Media.TypeMedia.VIDEO, dto.typeMedia());
    }
}