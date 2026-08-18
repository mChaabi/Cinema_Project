package Cinema.Movie.controller.admin;

import Cinema.Movie.dto.NationaliteDto;
import Cinema.Movie.dto.NationaliteMapper;
import Cinema.Movie.model.Nationalite;
import Cinema.Movie.service.NationaliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller 
@RequestMapping("/admin/nationalites") 
public class AdminNationaliteController {

    @Autowired
    private NationaliteService nationaliteService;

    @Autowired
    private NationaliteMapper nationaliteMapper;

    @GetMapping
    public String listNationalites(Model model) {
        List<NationaliteDto> nationalites = nationaliteService.getListAll().stream()
                .map(nationaliteMapper::toDto)
                .collect(Collectors.toList());
        
        model.addAttribute("nationalites", nationalites);
        return "nationalite/list"; 
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("nationalite", new NationaliteDto(null, null));
        return "nationalite/form"; 
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Nationalite nationalite = nationaliteService.get(id);
        NationaliteDto nationaliteDto = nationaliteMapper.toDto(nationalite);
        
        model.addAttribute("nationalite", nationaliteDto);
        return "nationalite/form"; 
    }

    @PostMapping("/save")
    public String saveNationalite(@ModelAttribute("nationalite") NationaliteDto nationaliteDto) {
        Nationalite nationalite = nationaliteMapper.toEntity(nationaliteDto);
        
        if (nationalite.getId() != null) {
            nationaliteService.update(nationalite);
        } else {
            nationaliteService.save(nationalite);
        }
        
        return "redirect:/admin/nationalites?success";
    }

    @PostMapping("/delete/{id}")
    public String deleteNationalite(@PathVariable("id") Long id) {
        nationaliteService.delete(id);
        return "redirect:/admin/nationalites?deleted";
    }
}