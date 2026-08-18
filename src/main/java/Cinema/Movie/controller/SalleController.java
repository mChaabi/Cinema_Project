package Cinema.Movie.controller;
 
import Cinema.Movie.model.Salle;
import Cinema.Movie.service.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@RequestMapping("/api/salles")
@CrossOrigin(origins = "http://localhost:4200")
public class SalleController {
 
    @Autowired
    private SalleService salleService;
 
    @GetMapping
    public List<Salle> getAllSalles() {
        return salleService.getListAll();
    }
 
    @GetMapping("/{id}")
    public Salle getSalleById(@PathVariable Long id) {
        return salleService.get(id);
    }
 
    @PostMapping
    public Salle addSalle(@RequestBody Salle salle) {
        return salleService.save(salle);
    }
 
    @DeleteMapping("/{id}")
    public void deleteSalle(@PathVariable Long id) {
        salleService.delete(id);
    }
 
    @PutMapping("/{id}")
    public void updateSalle(@PathVariable Long id, @RequestBody Salle salle) {
        salle.setId(id);
        salleService.update(salle);
    }
}