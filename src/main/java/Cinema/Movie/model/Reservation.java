package Cinema.Movie.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
   private String statut;
   private LocalDateTime dateReservation;

   @ManyToMany
   @JoinTable(
           name = "reservation_siege",
           joinColumns = @JoinColumn(name = "reservation_id"),
           inverseJoinColumns = @JoinColumn(name = "siege_id")
   )
   private List<Siege> sieges = new ArrayList<>();
   @PrePersist
   protected void onCreate() {
       this.dateReservation = LocalDateTime.now();
   }
}