package Cinema.Movie.repository;

import Cinema.Movie.model.Customers;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository

public interface CustomersRepository extends JpaRepository<Customers, Long> {

}
 