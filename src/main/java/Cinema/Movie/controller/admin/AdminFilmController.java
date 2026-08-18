package Cinema.Movie.controller.admin;

import Cinema.Movie.dto.FilmDto;
import Cinema.Movie.dto.FilmMapper;
import Cinema.Movie.model.Film;
import Cinema.Movie.model.Personne;
import Cinema.Movie.model.Personne.TypePersonne;
import Cinema.Movie.service.FileStorageService;
import Cinema.Movie.service.FilmService;
import Cinema.Movie.service.GenreService;
import Cinema.Movie.service.NationaliteService;
import Cinema.Movie.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    private FileStorageService fileStorageService;

    private void populateFormModel(Model model) {
        model.addAttribute("genres", genreService.getListAll());
        model.addAttribute("nationalites", nationaliteService.getListAll());

        List<Personne> realisateurs = personneService.getListAll().stream()
                .filter(p -> p.getTypePersonne() == TypePersonne.REALISATEUR)
                .collect(Collectors.toList());
        model.addAttribute("realisateurs", realisateurs);

        List<Personne> acteurs = personneService.getListAll().stream()
                .filter(p -> p.getTypePersonne() == TypePersonne.ACTEUR)
                .collect(Collectors.toList());
        model.addAttribute("acteurs", acteurs);
    }

    @GetMapping
    public String listFilms(Model model) {
        model.addAttribute("films", filmService.getListAll());
        return "films/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("film", new FilmDto(null, null, 0, 0, null, null, null, null, null));
        populateFormModel(model);
        return "films/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Film film = filmService.get(id);
        FilmDto filmDto = filmMapper.toDto(film);
        model.addAttribute("film", filmDto);
        populateFormModel(model);
        return "films/form";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable("id") Long id, Model model) {
        Film film = filmService.get(id);
        model.addAttribute("film", film);
        return "films/details";
    }

    @PostMapping("/save")
    public String saveFilm(@ModelAttribute("film") FilmDto filmDto,
                           @RequestParam(value = "posterFile", required = false) MultipartFile posterFile) {
        Film film = filmMapper.toEntity(filmDto);

        try {
            if (posterFile != null && !posterFile.isEmpty()) {
                if (film.getId() != null) {
                    Film existing = filmService.get(film.getId());
                    if (existing.getPhotoUrl() != null && existing.getPhotoUrl().startsWith("/uploads/")) {
                        fileStorageService.deleteFile(existing.getPhotoUrl());
                    }
                }
                String savedPath = fileStorageService.saveFilmPoster(posterFile);
                film.setPhotoUrl(savedPath);
            } else {
                film.setPhotoUrl(filmDto.photoUrl());
            }
        } catch (IOException e) {
            film.setPhotoUrl(filmDto.photoUrl());
        }

        if (film.getId() != null) {
            Film existing = filmService.get(film.getId());
            film.setMedias(existing.getMedias());
            film.setSeances(existing.getSeances());
            filmService.update(film);
        } else {
            filmService.save(film);
        }

        return "redirect:/admin/films?success";
    }

    @PostMapping("/delete/{id}")
    public String deleteFilm(@PathVariable("id") Long id) {
        Film film = filmService.get(id);
        if (film.getPhotoUrl() != null && film.getPhotoUrl().startsWith("/uploads/")) {
            fileStorageService.deleteFile(film.getPhotoUrl());
        }
        filmService.delete(id);
        return "redirect:/admin/films?deleted";
    }
}