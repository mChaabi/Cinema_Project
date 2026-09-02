package Cinema.Movie.model;

import jakarta.persistence.*;
import lombok.Data;

// Siege.java
@Entity
@Table(name = "siege")
@Data
public class Siege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;

    @Column(name = "seance_id", nullable = false)
    private Long seanceId;

    @Column(name = "is_reserved")
    private Boolean isReserved = false;
}