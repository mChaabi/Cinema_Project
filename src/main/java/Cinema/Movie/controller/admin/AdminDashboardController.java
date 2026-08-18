package Cinema.Movie.controller.admin;

import Cinema.Movie.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/admin/dashboard", "/admin"})
public class AdminDashboardController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private SalleService salleService;

    @Autowired
    private SeanceService seanceService;

    @Autowired
    private UserService userService;

    @Autowired
    private PersonneService personneService;

    @GetMapping
    public String dashboard(Model model) {
        long totalFilms = filmService.count();
        long totalSalles = salleService.count();
        long totalSeances = seanceService.count();
        long totalCustomers = userService.getListAll().stream()
            .filter(u -> "USER".equals(u.getRole()))
            .count();
        
        long totalActeurs = personneService.getListAll().stream()
            .filter(p -> p.getTypePersonne() == Cinema.Movie.model.Personne.TypePersonne.ACTEUR)
            .count();
            
        long totalRealisateurs = personneService.getListAll().stream()
            .filter(p -> p.getTypePersonne() == Cinema.Movie.model.Personne.TypePersonne.REALISATEUR)
            .count();

        model.addAttribute("totalFilms", totalFilms);
        model.addAttribute("totalSalles", totalSalles);
        model.addAttribute("totalSeances", totalSeances);
        model.addAttribute("totalCustomers", totalCustomers);
        model.addAttribute("totalActeurs", totalActeurs);
        model.addAttribute("totalRealisateurs", totalRealisateurs);

        return "dashboard/index";
    }
}
 