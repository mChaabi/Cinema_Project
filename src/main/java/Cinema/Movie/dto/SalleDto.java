package Cinema.Movie.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SalleDto(
    Long id,

    @NotNull(message = "Le numéro de la salle est obligatoire.")
    @Min(value = 1, message = "Le numéro de la salle doit être supérieur à 0.")
    Integer numero,

    @NotNull(message = "La capacité est obligatoire.")
    @Min(value = 10, message = "Une salle doit comporter au moins 10 places.")
    @Max(value = 1000, message = "La capacité d'une salle ne peut pas dépasser 1000 places.")
    Integer capacite
) {}