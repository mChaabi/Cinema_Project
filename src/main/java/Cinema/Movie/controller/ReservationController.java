package Cinema.Movie.controller;

import Cinema.Movie.model.Reservation;
import Cinema.Movie.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationController {
   @Autowired
   private ReservationService reservationService;
   @PostMapping
   public Reservation createReservation(@RequestBody Reservation reservation) {
       return reservationService.reserverTicket(reservation);
   }
   @GetMapping("/user/{userId}")
   public List<Reservation> getReservationsByUser(@PathVariable Long userId) {
       return reservationService.getHistoriqueByUser(userId);
   }
}
