package Cinema.Movie.dto;

import Cinema.Movie.entity.Role;
import Cinema.Movie.entity.User;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public record UserDto(
    Long id,

    @NotBlank(message = "Username is required")
    String username,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email,

    // Password can be blank on edit (meaning keep the same), but we validate it in controller if it's an add
    String password,

    Role role
) {}
 