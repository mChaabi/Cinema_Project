package Cinema.Movie.controller.admin;

import Cinema.Movie.dto.PersonneDto;
import Cinema.Movie.dto.PersonneMapper;
import Cinema.Movie.model.Personne;
import Cinema.Movie.model.Personne.TypePersonne;
import Cinema.Movie.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/realisateurs")
public class AdminRealisateurController {

    @Autowired
    private PersonneService personneService;

    @Autowired
    private PersonneMapper personneMapper;


    @GetMapping
    public String listRealisateurs(Model model) {
        List<Personne> realisateurs = personneService.getListAll().stream()
                .filter(p -> p.getTypePersonne() == TypePersonne.REALISATEUR)
                .collect(Collectors.toList());
        
        model.addAttribute("realisateurs", realisateurs);
        return "realisateurs/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("realisateur", new PersonneDto(null, null, null, null, TypePersonne.REALISATEUR, null));
        return "realisateurs/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Personne personne = personneService.get(id);
        PersonneDto dto = personneMapper.toDto(personne);
        model.addAttribute("realisateur", dto);
        return "realisateurs/form";
    }

    @PostMapping("/save")
    public String saveRealisateur(@ModelAttribute("realisateur") PersonneDto dto, 
                                  @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        
        Personne personne = personneMapper.toEntity(dto);
        personne.setTypePersonne(TypePersonne.REALISATEUR);

        if (!imageFile.isEmpty()) {
            String uploadDir = "src/main/resources/static/images/realisateurs/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String filename = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path filePath = uploadPath.resolve(filename);
            
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            personne.setPhoto("/images/realisateurs/" + filename);
        } else {
            if (personne.getId() != null) {
                Personne existingPerson = personneService.get(personne.getId());
                personne.setPhoto(existingPerson.getPhoto());
            }
        }

        if (personne.getId() != null) {
            personneService.update(personne);
        } else {
            personneService.save(personne);
        }
        
        return "redirect:/admin/realisateurs?success";
    }

    @PostMapping("/delete/{id}")
    public String deleteRealisateur(@PathVariable("id") Long id) {
        personneService.delete(id);
        return "redirect:/admin/realisateurs?deleted";
    }
}