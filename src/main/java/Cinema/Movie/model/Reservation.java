package Cinema.Movie.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity

@Getter

@Setter
@Table(name = "reservations")
public class Reservation {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private Long seanceId;
   private Long customerId;
   private int nbrPlaces;
   private LocalDateTime dateReservation;
   @PrePersist
   protected void onCreate() {
       this.dateReservation = LocalDateTime.now();
   }
}