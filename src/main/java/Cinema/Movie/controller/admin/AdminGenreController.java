package Cinema.Movie.controller.admin;

import Cinema.Movie.dto.GenreDto;
import Cinema.Movie.dto.GenreMapper;
import Cinema.Movie.model.Genre;
import Cinema.Movie.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller 
@RequestMapping("/admin/genres") 
public class AdminGenreController {

    @Autowired
    private GenreService genreService;

    @Autowired
    private GenreMapper genreMapper;

    @GetMapping
    public String listGenres(Model model) {
        List<GenreDto> genres = genreService.getListAll().stream()
                .map(genreMapper::toDto)
                .collect(Collectors.toList());
        
        model.addAttribute("genres", genres);
        return "genre/list"; 
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("genre", new GenreDto(null, null));
        return "genre/form"; 
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Genre genre = genreService.get(id);
        GenreDto genreDto = genreMapper.toDto(genre);
        
        model.addAttribute("genre", genreDto);
        return "genre/form"; 
    }


    @PostMapping("/save")
    public String saveGenre(@ModelAttribute("genre") GenreDto genreDto) {
        Genre genre = genreMapper.toEntity(genreDto);
        
        if (genre.getId() != null) {
            genreService.update(genre);
        } else {
            genreService.save(genre);
        }
        
        return "redirect:/admin/genres?success";
    }


    @PostMapping("/delete/{id}")
    public String deleteGenre(@PathVariable("id") Long id) {
        genreService.delete(id);
        return "redirect:/admin/genres?deleted";
    }
}