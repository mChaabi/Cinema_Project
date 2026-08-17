package Cinema.Movie.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record GenreDto(
    Long id,

    @NotBlank(message = "Le libellé du genre ne peut pas être vide ou nul.")
    @Size(min = 2, max = 50, message = "Le libellé doit comporter entre 2 et 50 caractères.")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ0-9\\s\\-_]+$", message = "Le libellé contient des caractères non autorisés.")
    String libelle
) {}