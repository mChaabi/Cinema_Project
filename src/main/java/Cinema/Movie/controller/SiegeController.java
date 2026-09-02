package Cinema.Movie.controller;

import Cinema.Movie.dto.SiegeMapper;
import Cinema.Movie.model.Siege;
import Cinema.Movie.service.SiegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sieges")
public class SiegeController {

    @Autowired
    private SiegeService siegeService;

    @GetMapping
    public List<SiegeMapper.SiegeDto> getAllSieges() {
        return siegeService.getAllSieges().stream()
                .map(siege -> SiegeMapper.toDto(
                        siege,
                        siege.getIsReserved() != null ? siege.getIsReserved() : false
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiegeMapper.SiegeDto> getSiegeById(@PathVariable Long id) {
        return siegeService.getSiegeById(id)
                .map(siege -> ResponseEntity.ok(SiegeMapper.toDto(
                        siege,
                        siege.getIsReserved() != null ? siege.getIsReserved() : false
                )))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/seance/{seanceId}")
    public List<SiegeMapper.SiegeDto> getSiegesBySeance(@PathVariable Long seanceId) {
        return siegeService.getSiegesBySeance(seanceId).stream()
                .map(siege -> SiegeMapper.toDto(
                        siege,
                        siege.getIsReserved() != null ? siege.getIsReserved() : false
                ))
                .collect(Collectors.toList());
    }

    @PostMapping
    public SiegeMapper.SiegeDto createSiege(@RequestBody SiegeMapper.SiegeDto siegeDto) {
        Siege siege = SiegeMapper.toEntity(siegeDto);
        Siege savedSiege = siegeService.saveSiege(siege);
        return SiegeMapper.toDto(savedSiege, false); // ✅ Se le pasa el booleano requerido
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSiege(@PathVariable Long id) {
        siegeService.deleteSiege(id);
        return ResponseEntity.noContent().build();
    }
}
