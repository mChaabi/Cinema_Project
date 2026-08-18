package Cinema.Movie.controller;
import Cinema.Movie.dto.FilmDto;
import Cinema.Movie.dto.FilmMapper;
import Cinema.Movie.model.Film;
import Cinema.Movie.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/films")
@CrossOrigin(origins = "http://localhost:4200")
public class FilmController {
   @Autowired
   private FilmService filmService;
   @Autowired
   private FilmMapper filmMapper; //l'injection dyal l'Mapper
   @GetMapping
   public List<FilmDto> getAllFilms() {
       List<Film> films = filmService.getListAll();
       return films.stream()
               .map(filmMapper::toDto)
               .collect(Collectors.toList());
   }
   @GetMapping("/{id}")
   public FilmDto getFilmById(@PathVariable Long id) {
       Film film = filmService.get(id);
       return filmMapper.toDto(film);
   }
   @PostMapping
   public FilmDto addFilm(@RequestBody FilmDto filmDto) {
       // nhawel dto la mn angular l entity
       Film film = filmMapper.toEntity(filmDto);
       // N-sauvegardi l'Entity f la base de données
       Film savedFilm = filmService.save(film);
       // Nraj3 l'Entity jdida f format DTO
       return filmMapper.toDto(savedFilm);
   }
   @PutMapping("/{id}")
   public void updateFilm(@PathVariable Long id, @RequestBody FilmDto filmDto) {
       Film film = filmMapper.toEntity(filmDto);
       film.setId(id); // Darouri n-setiw l'ID bach ydir Update w maydirch Insert jdida
       filmService.update(film);
   }
   @DeleteMapping("/{id}")
   public void deleteFilm(@PathVariable Long id) {
       filmService.delete(id);
   }
}