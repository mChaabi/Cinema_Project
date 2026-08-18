package Cinema.Movie.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomersDto(
    Long id,

    @NotBlank(message = "Le prénom est obligatoire.")
    @Size(min = 2, max = 40, message = "Le prénom doit contenir entre 2 et 40 caractères.")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s\\-]+$", message = "Le prénom contient des caractères non autorisés.")
    String firstname,

    @NotBlank(message = "Le nom est obligatoire.")
    @Size(min = 2, max = 40, message = "Le nom doit contenir entre 2 et 40 caractères.")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s\\-]+$", message = "Le nom contient des caractères non autorisés.")
    String lastname,

    @NotBlank(message = "L'adresse email est obligatoire.")
    @Email(message = "Le format de l'adresse email est invalide.")
    String email
) {}