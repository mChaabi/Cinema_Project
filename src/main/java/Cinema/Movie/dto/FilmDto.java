package Cinema.Movie.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record FilmDto(
    Long id,

    @NotBlank(message = "Le titre du film est obligatoire.")
    @Size(min = 1, max = 50, message = "Le titre doit faire entre 1 et 50 caractères.")
    String titre,

    String description,


    @Positive(message = "La durée doit être un nombre positif.")
    int duree,

    LocalDate dateSortie,

    @Min(value = 1888, message = "L'année du film est invalide.")
    @Max(value = 2100, message = "L'année ne peut pas être dans le lointain futur.")
    int annee,

    Long genreId,
    String genreLibelle,
    Long nationaliteId,
    String nationaliteLibelle,
    Long realisateurId,
    String realisateurNomComplet,

    List<Long> acteurIds,

    String photoUrl,
    List<MediaDto> medias
) {}