package Cinema.Movie.service;

import Cinema.Movie.dto.UpdateProfileDto;
import Cinema.Movie.entity.User;
import Cinema.Movie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getListAll() {
        return userRepository.findAll();
    }

    public User get(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Méthode manquante 1 : Rechercher par nom d'utilisateur
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé : " + username));
    }

    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void update(User user) {
        User existingUser = get(user.getId());
        if (existingUser != null) {
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            } else {
                user.setPassword(existingUser.getPassword());
            }
            userRepository.save(user);
        }
    }

    // Méthode manquante 2 : Mettre à jour le profil (attention à la minuscule 'u' dans updateProfile)
    public User updateProfile(String username, UpdateProfileDto dto) {
        User user = findByUsername(username);
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        return userRepository.save(user);
    }

    // UserService.java — nuevo método
    public void changePasswordById(Long id, String currentPassword, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Mot de passe actuel incorrect.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}