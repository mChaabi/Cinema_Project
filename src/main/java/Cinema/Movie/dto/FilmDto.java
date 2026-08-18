package Cinema.Movie.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public record FilmDto(
    Long id,

    @NotBlank(message = "Le titre du film est obligatoire.")
    @Size(min = 1, max = 50, message = "Le titre doit faire entre 1 et 50 caractères.")
    String titre,

    @Positive(message = "La durée doit être un nombre positif.")
    int duree,

    @Min(value = 1888, message = "L'année du film est invalide.")
    @Max(value = 2100, message = "L'année ne peut pas être dans le lointain futur.")
    int annee,

    Long genreId,
    Long nationaliteId,
    Long realisateurId,

    List<Long> acteurIds,

    // Main poster image URL
    String photoUrl
) {}