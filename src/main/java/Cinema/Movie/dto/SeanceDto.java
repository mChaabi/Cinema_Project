package Cinema.Movie.dto;

import jakarta.validation.constraints.NotNull;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public record SeanceDto(
    Long id,

    @NotNull(message = "La date de projection est obligatoire.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dateProjection,

    @NotNull(message = "L'heure de début est obligatoire.")
    @DateTimeFormat(pattern = "HH:mm")
    Date heureDebut,

    @NotNull(message = "L'heure de fin est obligatoire.")
    @DateTimeFormat(pattern = "HH:mm")
    Date heureFin,

    @NotNull(message = "L'identifiant du film est obligatoire.")
    Long filmId,

    @NotNull(message = "L'identifiant de la salle est obligatoire.")
    Long salleId
) {}