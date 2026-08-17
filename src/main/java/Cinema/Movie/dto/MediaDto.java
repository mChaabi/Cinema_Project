package Cinema.Movie.dto;

import Cinema.Movie.model.Media.TypeMedia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MediaDto(
    Long id,

    @NotBlank(message = "Le chemin ou le nom du média est obligatoire.")
    @Size(max = 100, message = "Le nom du média ne doit pas dépasser 100 caractères.")
    String media,

    @NotNull(message = "Le type de média (IMAGE, VIDEO, DOCUMENT) est obligatoire.")
    TypeMedia typeMedia,

    Long filmId
) {}