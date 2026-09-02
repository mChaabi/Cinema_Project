package Cinema.Movie.controller;
import Cinema.Movie.dto.SeanceDto;
import Cinema.Movie.dto.SeanceMapper;
import Cinema.Movie.dto.SiegeMapper;
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

    @GetMapping("/film/{filmId}")
    public List<SeanceDto> getByFilm(@PathVariable Long filmId) {
        return seanceService.getSeancesByFilm(filmId);
    }

    @GetMapping("/{id}")
    public SeanceDto getById(@PathVariable Long id) {
        return seanceService.getSeanceById(id);
    }

    @GetMapping("/{id}/sieges")
    public List<SiegeMapper.SiegeDto> getSieges(@PathVariable Long id) {
        return seanceService.getSiegesAvecStatut(id);
    }
}