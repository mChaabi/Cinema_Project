package Cinema.Movie.controller.admin;

import Cinema.Movie.dto.FilmDto;
import Cinema.Movie.dto.FilmMapper;
import Cinema.Movie.model.Film;
import Cinema.Movie.model.Personne;
import Cinema.Movie.model.Personne.TypePersonne;
import Cinema.Movie.service.FilmService;
import Cinema.Movie.service.GenreService;
import Cinema.Movie.service.NationaliteService;
import Cinema.Movie.service.PersonneService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/films")
public class AdminFilmController {

    @Autowired
    private FilmService filmService;
    @Autowired
    private FilmMapper filmMapper;
    
    @Autowired
    private GenreService genreService;
    @Autowired
    private NationaliteService nationaliteService;
    @Autowired
    private PersonneService personneService; 

    @GetMapping
    public String listFilms(Model model) {
        model.addAttribute("films", filmService.getListAll());
        return "films/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("film", new FilmDto(null, null, 0, 0, null, null, null));
        
        model.addAttribute("genres", genreService.getListAll());
        model.addAttribute("nationalites", nationaliteService.getListAll());
        
        List<Personne> realisateurs = personneService.getListAll().stream()
                .filter(p -> p.getTypePersonne() == TypePersonne.REALISATEUR)
                .collect(Collectors.toList());
        model.addAttribute("realisateurs", realisateurs); 
        
        return "films/form"; 
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Film film = filmService.get(id);
        FilmDto filmDto = filmMapper.toDto(film);
        
        model.addAttribute("film", filmDto);
        
        model.addAttribute("genres", genreService.getListAll());
        model.addAttribute("nationalites", nationaliteService.getListAll());
        
        List<Personne> realisateurs = personneService.getListAll().stream()
                .filter(p -> p.getTypePersonne() == TypePersonne.REALISATEUR)
                .collect(Collectors.toList());
        model.addAttribute("realisateurs", realisateurs); 
        
        return "films/form"; 
    }

    @PostMapping("/save")
    public String saveFilm(@ModelAttribute("film") FilmDto filmDto) {
        Film film = filmMapper.toEntity(filmDto);
        
        if (film.getId() != null) {
            filmService.update(film);
        } else {
            filmService.save(film);
        }
        
        return "redirect:/admin/films?success";
    }

    @PostMapping("/delete/{id}")
    public String deleteFilm(@PathVariable("id") Long id) {
        filmService.delete(id);
        return "redirect:/admin/films?deleted";
    }
}