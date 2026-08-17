package Cinema.Movie.repository;

import Cinema.Movie.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Devrait sauvegarder et retrouver un utilisateur par son ID")
    void shouldSaveAndFindUserById() {
        // Arrange
        User user = new User(null, "john_doe", "john@example.com", "secret123");

        // Act
        User savedUser = userRepository.save(user);
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        // Assert
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("john_doe");
        assertThat(foundUser.get().getEmail()).isEqualTo("john@example.com");
    }

    @Test
    @DisplayName("Devrait lever une exception en cas de doublon sur le username")
    void shouldThrowExceptionWhenDuplicateUsername() {
        // Arrange
        User user1 = new User(null, "unique_user", "email1@example.com", "pass1");
        User user2 = new User(null, "unique_user", "email2@example.com", "pass2");

        entityManager.persistAndFlush(user1);

        // Act & Assert
        assertThatThrownBy(() -> userRepository.saveAndFlush(user2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Devrait lever une exception en cas de doublon sur l'email")
    void shouldThrowExceptionWhenDuplicateEmail() {
        // Arrange
        User user1 = new User(null, "user1", "same@example.com", "pass1");
        User user2 = new User(null, "user2", "same@example.com", "pass2");

        entityManager.persistAndFlush(user1);

        // Act & Assert
        assertThatThrownBy(() -> userRepository.saveAndFlush(user2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}