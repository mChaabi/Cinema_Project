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
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@ecine.com");
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
            System.out.println("Default admin user created (admin/admin)");
        }
    }
}
 