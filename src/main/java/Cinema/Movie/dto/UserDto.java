package Cinema.Movie.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDto(
    Long id,

    @NotBlank(message = "Username is required")
    String username,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email,

    // Password can be blank on edit (meaning keep the same), but we validate it in controller if it's an add
    String password,

    String role
) {}
 