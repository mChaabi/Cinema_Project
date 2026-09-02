package Cinema.Movie.model;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seance extends AbstractModel<Long> {

    private static final long serialVersionUID = 6992208427439369561L;

    private LocalDate dateProjection;

    // Correcto: Declarados como LocalTime arriba
    private LocalTime heureDebut;
    private LocalTime heureFin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Film_ID")
    private Film film;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "salle_ID")
    private Salle salle;

    // ¡No necesitas escribir getters ni setters aquí! Lombok los genera automáticamente.
}