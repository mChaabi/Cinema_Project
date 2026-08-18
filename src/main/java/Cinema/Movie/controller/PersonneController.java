package Cinema.Movie.controller;
import Cinema.Movie.dto.PersonneDto;
import Cinema.Movie.dto.PersonneMapper;
import Cinema.Movie.model.Personne;
import Cinema.Movie.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/personnes")
@CrossOrigin(origins = "http://localhost:4200")
public class PersonneController {
   @Autowired
   private PersonneService personneService;
   @Autowired
   private PersonneMapper personneMapper;
   @GetMapping
   public List<PersonneDto> getAllPersonnes() {
       return personneService.getListAll().stream()
               .map(personneMapper::toDto)
               .collect(Collectors.toList());
   }
   @GetMapping("/{id}")
   public PersonneDto getPersonneById(@PathVariable Long id) {
       return personneMapper.toDto(personneService.get(id));
   }
   @PostMapping
   public PersonneDto addPersonne(@RequestBody PersonneDto personneDto) {
       Personne personne = personneMapper.toEntity(personneDto);
       Personne savedPersonne = personneService.save(personne);
       return personneMapper.toDto(savedPersonne);
   }
   @PutMapping("/{id}")
   public void updatePersonne(@PathVariable Long id, @RequestBody PersonneDto personneDto) {
       Personne personne = personneMapper.toEntity(personneDto);
       personne.setId(id);
       personneService.update(personne);
   }
   @DeleteMapping("/{id}")
   public void deletePersonne(@PathVariable Long id) {
       personneService.delete(id);
   }
}