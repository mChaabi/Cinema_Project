package Cinema.Movie.controller;

import Cinema.Movie.dto.PaiementMapper;
import Cinema.Movie.model.Paiement;
import Cinema.Movie.service.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/paiements")
@CrossOrigin(origins = "http://localhost:4200")
public class PaiementController {

    @Autowired
    private PaiementService paiementService;

    @GetMapping
    public List<PaiementMapper.PaiementDto> getAllPaiements() {
        return paiementService.getAllPaiements().stream()
                .map(PaiementMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaiementMapper.PaiementDto> getPaiementById(@PathVariable Long id) {
        return paiementService.getPaiementById(id)
                .map(paiement -> ResponseEntity.ok(PaiementMapper.toDto(paiement)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PaiementMapper.PaiementDto createPaiement(@RequestBody PaiementMapper.PaiementDto paiementDto) {
        Paiement paiement = PaiementMapper.toEntity(paiementDto);
        Paiement savedPaiement = paiementService.savePaiement(paiement);
        return PaiementMapper.toDto(savedPaiement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaiement(@PathVariable Long id) {
        paiementService.deletePaiement(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<PaiementMapper.PaiementDto> getByReservation(@PathVariable Long reservationId) {
        return paiementService.getByReservationId(reservationId)
                .map(p -> ResponseEntity.ok(PaiementMapper.toDto(p)))
                .orElse(ResponseEntity.notFound().build());
    }
}