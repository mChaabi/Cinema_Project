package Cinema.Movie.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public record SeanceDto(
        Long id,

        @NotNull(message = "La date de projection est obligatoire.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate dateProjection,

        @NotNull(message = "L'heure de début est obligatoire.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        LocalTime heureDebut,

        @NotNull(message = "L'heure de fin est obligatoire.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        LocalTime heureFin,

        @NotNull(message = "L'identifiant du film est obligatoire.")
        Long filmId,

        @NotNull(message = "L'identifiant de la salle est obligatoire.")
        Long salleId
) {}