package Cinema.Movie.controller;

import Cinema.Movie.dto.ReservationMapper;
import Cinema.Movie.dto.ReservationResponseDto;
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

    // Crear una reserva (con DTO)
    @PostMapping
    public ReservationMapper.ReservationDto createReservation(@RequestBody ReservationMapper.ReservationDto reservationDto) {
        return reservationService.createReservation(reservationDto);
    }

    // Recuperar todas las reservaciones (DTO)
    @GetMapping
    public List<ReservationMapper.ReservationDto> getAllReservations() {
        return reservationService.getAllReservations();
    }

    // Recuperar una reserva por ID (DTO)
    @GetMapping("/{id}")
    public ResponseEntity<ReservationMapper.ReservationDto> getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 👈 CAMBIO CLAVE AQUÍ: Usamos el método enriquecido que devuelve ReservationResponseDto
    @GetMapping("/user/{userId}")
    public List<ReservationResponseDto> getReservationsByUser(@PathVariable Long userId) {
        return reservationService.getReservationsEnrichedByUser(userId);
    }

    // Eliminar una reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}