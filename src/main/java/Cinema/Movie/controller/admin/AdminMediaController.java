package Cinema.Movie.controller.admin;

import Cinema.Movie.dto.MediaDto;
import Cinema.Movie.dto.MediaMapper;
import Cinema.Movie.model.Media;
import Cinema.Movie.model.Media.TypeMedia;
import Cinema.Movie.service.FilmService;
import Cinema.Movie.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/media")
public class AdminMediaController {

    @Autowired
    private MediaService mediaService;

    @Autowired
    private MediaMapper mediaMapper;

    @Autowired
    private FilmService filmService;

    @GetMapping
    public String listMedia(Model model) {
        List<MediaDto> medias = mediaService.getListAll().stream()
                .map(mediaMapper::toDto)
                .collect(Collectors.toList());

        model.addAttribute("medias", medias);
        return "media/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("media", new MediaDto(null, null, TypeMedia.IMAGE, null));
        model.addAttribute("films", filmService.getListAll());
        model.addAttribute("typeMediaValues", TypeMedia.values());
        return "media/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Media media = mediaService.get(id);
        MediaDto dto = mediaMapper.toDto(media);

        model.addAttribute("media", dto);
        model.addAttribute("films", filmService.getListAll());
        model.addAttribute("typeMediaValues", TypeMedia.values());
        return "media/form";
    }

    @PostMapping("/save")
    public String saveMedia(@ModelAttribute("media") MediaDto dto) {
        Media media = mediaMapper.toEntity(dto);

        if (media.getId() != null) {
            mediaService.update(media);
        } else {
            mediaService.save(media);
        }

        return "redirect:/admin/media?success";
    }

    @PostMapping("/delete/{id}")
    public String deleteMedia(@PathVariable("id") Long id) {
        mediaService.delete(id);
        return "redirect:/admin/media?deleted";
    }
}
 