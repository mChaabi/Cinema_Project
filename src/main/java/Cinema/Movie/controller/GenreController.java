package Cinema.Movie.controller;
 
import Cinema.Movie.model.Genre;
import Cinema.Movie.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@RequestMapping("/api/genres")
@CrossOrigin(origins = "http://localhost:4200")
public class GenreController {
 
    @Autowired
    private GenreService genreService;
 
    @GetMapping
    public List<Genre> getAllGenres() {
        return genreService.getListAll();
    }
 
    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable Long id) {
        return genreService.get(id);
    }
 
    @PostMapping
    public Genre addGenre(@RequestBody Genre genre) {
        return genreService.save(genre);
    }
 
    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable Long id) {
        genreService.delete(id);
    }
 
    @PutMapping("/{id}")
    public void updateGenre(@PathVariable Long id, @RequestBody Genre genre) {
        genre.setId(id);
        genreService.update(genre);
    }
}