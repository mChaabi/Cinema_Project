package Cinema.Movie.controller;

import Cinema.Movie.dto.ReservationMapper;
import Cinema.Movie.dto.ReservationResponseDto;
import Cinema.Movie.dto.SiegeResponseDto;
import Cinema.Movie.model.Reservation;
import Cinema.Movie.model.Seance;
import Cinema.Movie.model.Siege;
import Cinema.Movie.repository.ReservationSiegeRepository;
import Cinema.Movie.repository.SeanceRepository;
import Cinema.Movie.repository.SiegeRepository;
import Cinema.Movie.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationSiegeRepository reservationSiegeRepository;

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private SiegeRepository siegeRepository;

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

    @GetMapping("/{seanceId}/sieges")
    public ResponseEntity<List<SiegeResponseDto>> getSeatsBySeance(@PathVariable Long seanceId) {
        // 1. Buscar la sesión para saber a qué sala pertenece
        Seance seance = seanceRepository.findById(seanceId).orElse(null);
        if (seance == null || seance.getSalle() == null) {
            return ResponseEntity.notFound().build();
        }

        Long salleId = seance.getSalle().getId();

        // 2. Obtener todos los asientos que pertenecen a esa sala
        List<Siege> sieges = siegeRepository.findBySalleId(salleId);

        // 3. Obtener los IDs de los asientos ya reservados para esta sesión (excluyendo canceladas)
        Set<Long> reservedSiegeIds = reservationSiegeRepository.findSiegeIdsByReservationSeanceId(seanceId);

        // 4. Mapear al SiegeResponseDto indicando si está reservado
        List<SiegeResponseDto> response = sieges.stream().map(siege -> {
            boolean isReserved = reservedSiegeIds.contains(siege.getId());
            return new SiegeResponseDto(
                    siege.getId(),
                    siege.getNumero(),
                    seanceId,
                    isReserved
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }


    // Eliminar una reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}