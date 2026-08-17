package Cinema.Movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Cinema.Movie.entity.User;



public interface UserRepository extends JpaRepository<User, Long> {
}
