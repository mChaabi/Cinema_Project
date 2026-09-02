package Cinema.Movie.controller;
import Cinema.Movie.dto.MediaDto;
import Cinema.Movie.dto.MediaMapper;
import Cinema.Movie.model.Film;
import Cinema.Movie.model.Media;
import Cinema.Movie.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/medias")
@CrossOrigin(origins = "http://localhost:4200")
public class MediaController {
   @Autowired
   private MediaService mediaService;
   @Autowired
   private MediaMapper mediaMapper;
   @GetMapping
   public List<MediaDto> getAllMedias() {
       return mediaService.getListAll().stream()
               .map(mediaMapper::toDto)
               .collect(Collectors.toList());
   }
   @GetMapping("/{id}")
   public MediaDto getMediaById(@PathVariable Long id) {
       return mediaMapper.toDto(mediaService.get(id));
   }


    @PostMapping
    public MediaDto addMedia(@RequestBody MediaDto mediaDto) {
        Media media = mediaMapper.toEntity(mediaDto);

        if (media.getThumbnailUrl() == null && "VIDEO".equals(mediaDto.typeMedia())) {
            String vimeoThumb = mediaService.resolveVimeoThumbnail(media.getMedia());
            if (vimeoThumb != null) {
                media.setThumbnailUrl(vimeoThumb);
            }
        }

        Media savedMedia = mediaService.save(media);
        return mediaMapper.toDto(savedMedia);
    }


   @PutMapping("/{id}")
   public void updateMedia(@PathVariable Long id, @RequestBody MediaDto mediaDto) {
       Media media = mediaMapper.toEntity(mediaDto);
       media.setId(id);
       mediaService.update(media);
   }
   @DeleteMapping("/{id}")
   public void deleteMedia(@PathVariable Long id) {
       mediaService.delete(id);
   }

    @PostMapping("/upload")
    public MediaDto uploadMedia(
            @RequestParam("file") MultipartFile file,
            @RequestParam("filmId") Long filmId,
            @RequestParam("typeMedia") String typeMedia
    ) throws IOException {
        String uploadDir = "uploads/";
        Files.createDirectories(Paths.get(uploadDir));

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Media media = new Media();
        media.setMedia("/uploads/" + fileName);
        media.setTypeMedia(Media.TypeMedia.valueOf(typeMedia));

        Film film = new Film();
        film.setId(filmId);
        media.setFilm(film);

        Media saved = mediaService.save(media);
        return mediaMapper.toDto(saved);
    }
}