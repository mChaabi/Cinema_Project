package Cinema.Movie.controller.admin;

import Cinema.Movie.dto.SalleDto;
import Cinema.Movie.dto.SalleMapper;
import Cinema.Movie.model.Salle;
import Cinema.Movie.service.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller 
@RequestMapping("/admin/salles") 
public class AdminSalleController {

    @Autowired
    private SalleService salleService;

    @Autowired
    private SalleMapper salleMapper;

    @GetMapping
    public String listSalles(Model model) {
        List<SalleDto> salles = salleService.getListAll().stream()
                .map(salleMapper::toDto)
                .collect(Collectors.toList());
        
        model.addAttribute("salles", salles);
        return "salles/list"; 
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("salle", new SalleDto(null, null, null));
        return "salles/form"; 
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Salle salle = salleService.get(id);
        SalleDto salleDto = salleMapper.toDto(salle);
        
        model.addAttribute("salle", salleDto);
        return "salles/form"; 
    }

    @PostMapping("/save")
    public String saveSalle(@ModelAttribute("salle") SalleDto salleDto) {
        Salle salle = salleMapper.toEntity(salleDto);
        
        if (salle.getId() != null) {
            salleService.update(salle);
        } else {
            salleService.save(salle);
        }
        
        return "redirect:/admin/salles?success";
    }

    @PostMapping("/delete/{id}")
    public String deleteSalle(@PathVariable("id") Long id) {
        salleService.delete(id);
        return "redirect:/admin/salles?deleted";
    }
}