package Cinema.Movie.controller;
import Cinema.Movie.dto.SalleDto;
import Cinema.Movie.dto.SalleMapper;
import Cinema.Movie.model.Salle;
import Cinema.Movie.service.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/salles")
@CrossOrigin(origins = "http://localhost:4200")
public class SalleController {
   @Autowired
   private SalleService salleService;
   @Autowired
   private SalleMapper salleMapper;
   @GetMapping
   public List<SalleDto> getAllSalles() {
       return salleService.getListAll().stream()
               .map(salleMapper::toDto)
               .collect(Collectors.toList());
   }
   @GetMapping("/{id}")
   public SalleDto getSalleById(@PathVariable Long id) {
       return salleMapper.toDto(salleService.get(id));
   }
   @PostMapping
   public SalleDto addSalle(@RequestBody SalleDto salleDto) {
       Salle salle = salleMapper.toEntity(salleDto);
       Salle savedSalle = salleService.save(salle);
       return salleMapper.toDto(savedSalle);
   }
   @PutMapping("/{id}")
   public void updateSalle(@PathVariable Long id, @RequestBody SalleDto salleDto) {
       Salle salle = salleMapper.toEntity(salleDto);
       salle.setId(id);
       salleService.update(salle);
   }
   @DeleteMapping("/{id}")
   public void deleteSalle(@PathVariable Long id) {
       salleService.delete(id);
   }
}