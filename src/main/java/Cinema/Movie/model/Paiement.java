package Cinema.Movie.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

// Paiement.java
@Entity
@Table(name = "paiement")
@Data
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reservation_id", nullable = false)
    private Long reservationId;

    private BigDecimal amount;
    private String status;
}