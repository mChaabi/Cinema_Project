package Cinema.Movie.controller;

import Cinema.Movie.dto.ReservationMapper;
import Cinema.Movie.model.Reservation;
import Cinema.Movie.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationController {
   @Autowired
   private ReservationService reservationService;


    // Créer une réservation (avec DTO)
    @PostMapping
    public ReservationMapper.ReservationDto createReservation(@RequestBody ReservationMapper.ReservationDto reservationDto) {
        return reservationService.createReservation(reservationDto);
    }

    // Récupérer toutes les réservations (DTO)
    @GetMapping
    public List<ReservationMapper.ReservationDto> getAllReservations() {
        return reservationService.getAllReservations();
    }

    // Récupérer une réservation par ID (DTO)
    @GetMapping("/{id}")
    public ResponseEntity<ReservationMapper.ReservationDto> getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

   @GetMapping("/user/{userId}")
   public List<Reservation> getReservationsByUser(@PathVariable Long userId) {
       return reservationService.getHistoriqueByUser(userId);
   }

    // Supprimer une réservation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
