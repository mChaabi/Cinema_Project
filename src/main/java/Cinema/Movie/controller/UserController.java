package Cinema.Movie.controller;

import Cinema.Movie.dto.*;
import Cinema.Movie.entity.Role;
import Cinema.Movie.entity.User;
import Cinema.Movie.repository.UserRepository;
import Cinema.Movie.service.UserService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> getAllUsers() {
        List<User> users = userService.getListAll();
        users.forEach(u -> u.setPassword(null));
        return users;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.get(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setPassword(null); // Nunca exponer la contraseña
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Ce nom d'utilisateur est déjà utilisé."));
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Cette adresse e-mail est déjà utilisée."));
        }

        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        User saved = userService.save(user);
        saved.setPassword(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.username()).orElse(null);

        if (user != null && passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            user.setPassword(null);
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Nom d'utilisateur ou mot de passe incorrect."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        userService.update(user);
        User updated = userService.get(id);
        updated.setPassword(null);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Método para actualizar el perfil mediante Authentication
    @PutMapping("/me")
    public UserDto updateCurrentUser(Authentication authentication, @RequestBody UpdateProfileDto dto) {
        String username = authentication.getName();
        User updated = userService.updateProfile(username, dto);
        return UserMapper.toDto(updated);
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> changePassword(Authentication authentication, @RequestBody ChangePasswordDto dto) {
        userService.changePassword(authentication.getName(), dto.currentPassword(), dto.newPassword());
        return ResponseEntity.ok().build();
    }
}