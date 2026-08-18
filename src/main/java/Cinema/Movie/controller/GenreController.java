package Cinema.Movie.controller;
import Cinema.Movie.dto.GenreDto;
import Cinema.Movie.dto.GenreMapper;
import Cinema.Movie.model.Genre;
import Cinema.Movie.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/genres")
@CrossOrigin(origins = "http://localhost:4200")
public class GenreController {
   @Autowired
   private GenreService genreService;
   @Autowired
   private GenreMapper genreMapper;
   @GetMapping
   public List<GenreDto> getAllGenres() {
       return genreService.getListAll().stream()
               .map(genreMapper::toDto)
               .collect(Collectors.toList());
   }
   @GetMapping("/{id}")
   public GenreDto getGenreById(@PathVariable Long id) {
       return genreMapper.toDto(genreService.get(id));
   }
   @PostMapping
   public GenreDto addGenre(@RequestBody GenreDto genreDto) {
       Genre genre = genreMapper.toEntity(genreDto);
       Genre savedGenre = genreService.save(genre);
       return genreMapper.toDto(savedGenre);
   }
   @PutMapping("/{id}")
   public void updateGenre(@PathVariable Long id, @RequestBody GenreDto genreDto) {
       Genre genre = genreMapper.toEntity(genreDto);
       genre.setId(id);
       genreService.update(genre);
   }
   @DeleteMapping("/{id}")
   public void deleteGenre(@PathVariable Long id) {
       genreService.delete(id);
   }
}