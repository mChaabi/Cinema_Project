package Cinema.Movie.dto;

import jakarta.validation.constraints.NotNull;
import java.util.Date;

public record SeanceDto(
    Long id,

    @NotNull(message = "La date de projection est obligatoire.")
    Date dateProjection,

    @NotNull(message = "L'heure de début est obligatoire.")
    Date heureDebut,

    @NotNull(message = "L'heure de fin est obligatoire.")
    Date heureFin,

    @NotNull(message = "L'identifiant du film est obligatoire.")
    Long filmId,

    @NotNull(message = "L'identifiant de la salle est obligatoire.")
    Long salleId
) {}