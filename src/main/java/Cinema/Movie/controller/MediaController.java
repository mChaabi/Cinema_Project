package Cinema.Movie.controller;
import Cinema.Movie.dto.MediaDto;
import Cinema.Movie.dto.MediaMapper;
import Cinema.Movie.model.Media;
import Cinema.Movie.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
}