package Cinema.Movie.dto;

import Cinema.Movie.dto.UserDto;
import Cinema.Movie.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // Convertir l'entité User vers UserDto
    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                null, // On ne renvoie jamais le mot de passe dans le DTO pour des raisons de sécurité
                user.getRole(),
                user.getPhotoUrl()
        );
    }

    // Convertir UserDto vers l'entité User (utile si besoin lors d'une création/mise à jour complète)
    public static User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setId(dto.id());
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setRole(dto.role());
        user.setPhotoUrl(dto.photoUrl());
        return user;
    }
}