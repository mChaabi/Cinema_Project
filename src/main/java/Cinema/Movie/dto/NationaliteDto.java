package Cinema.Movie.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NationaliteDto(
    Long id,

    @NotBlank(message = "Le libellé de la nationalité est obligatoire.")
    @Size(min = 2, max = 50, message = "Le libellé de la nationalité doit contenir entre 2 et 50 caractères.")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s\\-]+$", message = "La nationalité ne doit contenir que des lettres, espaces ou tirets.")
    String libelle
) {}