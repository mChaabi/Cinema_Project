package Cinema.Movie.controller.admin;

import Cinema.Movie.dto.SeanceDto;
import Cinema.Movie.dto.SeanceMapper;
import Cinema.Movie.model.Seance;
import Cinema.Movie.service.SeanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/seances")
public class AdminSeanceController {

    @Autowired
    private SeanceService seanceService;

    @Autowired
    private SeanceMapper seanceMapper;

    @GetMapping
    public String listSeances(Model model) {
        List<SeanceDto> seances = seanceService.getListAll().stream()
                .map(seanceMapper::toDto)
                .collect(Collectors.toList());
        
        model.addAttribute("seances", seances);
        return "seances/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("seance", new SeanceDto(null, null, null, null, null, null));
        return "seances/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Seance seance = seanceService.get(id);
        SeanceDto seanceDto = seanceMapper.toDto(seance);
        
        model.addAttribute("seance", seanceDto);
        return "seances/form";
    }

    @PostMapping("/save")
    public String saveSeance(@ModelAttribute("seance") SeanceDto seanceDto) {
        Seance seance = seanceMapper.toEntity(seanceDto);
        
        if (seance.getId() != null) {
            seanceService.update(seance);
        } else {
            seanceService.save(seance);
        }
        
        return "redirect:/admin/seances?success";
    }

    @PostMapping("/delete/{id}")
    public String deleteSeance(@PathVariable("id") Long id) {
        seanceService.delete(id);
        return "redirect:/admin/seances?deleted";
    }
}