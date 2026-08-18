package Cinema.Movie.controller;
import Cinema.Movie.dto.SeanceDto;
import Cinema.Movie.dto.SeanceMapper;
import Cinema.Movie.model.Seance;
import Cinema.Movie.service.SeanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/seances")
@CrossOrigin(origins = "http://localhost:4200")
public class SeanceController {
   @Autowired
   private SeanceService seanceService;
   @Autowired
   private SeanceMapper seanceMapper;
   @GetMapping
   public List<SeanceDto> getAllSeances() {
       return seanceService.getListAll().stream()
               .map(seanceMapper::toDto)
               .collect(Collectors.toList());
   }
   @GetMapping("/{id}")
   public SeanceDto getSeanceById(@PathVariable Long id) {
       return seanceMapper.toDto(seanceService.get(id));
   }
   @PostMapping
   public SeanceDto addSeance(@RequestBody SeanceDto seanceDto) {
       Seance seance = seanceMapper.toEntity(seanceDto);
       Seance savedSeance = seanceService.save(seance);
       return seanceMapper.toDto(savedSeance);
   }
   @PutMapping("/{id}")
   public void updateSeance(@PathVariable Long id, @RequestBody SeanceDto seanceDto) {
       Seance seance = seanceMapper.toEntity(seanceDto);
       seance.setId(id);
       seanceService.update(seance);
   }
   @DeleteMapping("/{id}")
   public void deleteSeance(@PathVariable Long id) {
       seanceService.delete(id);
   }
}