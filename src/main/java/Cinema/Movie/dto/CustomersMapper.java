package Cinema.Movie.dto;

import Cinema.Movie.model.Customers;
import org.springframework.stereotype.Component;

@Component
public class CustomersMapper {

    public CustomersDto toDto(Customers customers) {
        if (customers == null) {
            return null;
        }
        return new CustomersDto(
            customers.getId(),
            customers.getFirstname(),
            customers.getLastname(),
            customers.getEmail()
        );
    }

    public Customers toEntity(CustomersDto customersDto) {
        if (customersDto == null) {
            return null;
        }
        Customers customers = new Customers();
        customers.setId(customersDto.id());
        customers.setFirstname(customersDto.firstname());
        customers.setLastname(customersDto.lastname());
        customers.setEmail(customersDto.email());
        return customers;
    }
}