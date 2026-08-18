package Cinema.Movie.dto;

import Cinema.Movie.model.Personne.TypePersonne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public record PersonneDto(
    Long id,

    @NotBlank(message = "Le nom est obligatoire.")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères.")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s\\-]+$", message = "Le nom contient des caractères non autorisés.")
    String nom,

    @NotBlank(message = "Le prénom est obligatoire.")
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères.")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s\\-]+$", message = "Le prénom contient des caractères non autorisés.")
    String prenom,

    @Past(message = "La date de naissance doit être dans le passé.")
    @DateTimeFormat(pattern = "yyyy-MM-dd") 
    Date dateNaissance,

    @NotNull(message = "Le type de personne (ACTEUR ou REALISATEUR) est obligatoire.")
    TypePersonne typePersonne,

    String photo
) {}