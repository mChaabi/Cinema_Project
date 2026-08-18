package Cinema.Movie.service;

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

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
 