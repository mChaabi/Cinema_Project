package Cinema.Movie.controller;
import Cinema.Movie.dto.NationaliteDto;
import Cinema.Movie.dto.NationaliteMapper;
import Cinema.Movie.model.Nationalite;
import Cinema.Movie.service.NationaliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/nationalites")
@CrossOrigin(origins = "http://localhost:4200")
public class NationaliteController {
   @Autowired
   private NationaliteService nationaliteService;
   @Autowired
   private NationaliteMapper nationaliteMapper;
   @GetMapping
   public List<NationaliteDto> getAllNationalites() {
       return nationaliteService.getListAll().stream()
               .map(nationaliteMapper::toDto)
               .collect(Collectors.toList());
   }
   @GetMapping("/{id}")
   public NationaliteDto getNationaliteById(@PathVariable Long id) {
       return nationaliteMapper.toDto(nationaliteService.get(id));
   }
   @PostMapping
   public NationaliteDto addNationalite(@RequestBody NationaliteDto nationaliteDto) {
       Nationalite nationalite = nationaliteMapper.toEntity(nationaliteDto);
       Nationalite savedNationalite = nationaliteService.save(nationalite);
       return nationaliteMapper.toDto(savedNationalite);
   }
   @PutMapping("/{id}")
   public void updateNationalite(@PathVariable Long id, @RequestBody NationaliteDto nationaliteDto) {
       Nationalite nationalite = nationaliteMapper.toEntity(nationaliteDto);
       nationalite.setId(id);
       nationaliteService.update(nationalite);
   }
   @DeleteMapping("/{id}")
   public void deleteNationalite(@PathVariable Long id) {
       nationaliteService.delete(id);
   }
}