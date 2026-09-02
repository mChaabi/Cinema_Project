package Cinema.Movie.config;

import Cinema.Movie.entity.Role;
import Cinema.Movie.entity.User;
import Cinema.Movie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            // 3 Admins (Contraseña: admin123)
            createAndSaveUser("admin1", "admin1@cinema.com", "admin123", Role.ADMIN);
            createAndSaveUser("admin2", "admin2@cinema.com", "admin123", Role.ADMIN);
            createAndSaveUser("admin3", "admin3@cinema.com", "admin123", Role.ADMIN);

            // 2 Users (Contraseña: user123)
            createAndSaveUser("user1", "user1@cinema.com", "user123", Role.USER);
            createAndSaveUser("user2", "user2@cinema.com", "user123", Role.USER);

            System.out.println("--> Se han creado los 5 usuarios de prueba correctamente.");
        }
    }

    private void createAndSaveUser(String username, String email, String rawPassword, Role role) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(role);
        userRepository.save(user);
    }
}