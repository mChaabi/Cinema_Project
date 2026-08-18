package Cinema.Movie.controller;
import Cinema.Movie.dto.CustomersDto;
import Cinema.Movie.dto.CustomersMapper;
import Cinema.Movie.model.Customers;
import Cinema.Movie.service.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomersController {
   @Autowired
   private CustomersService customersService;
   @Autowired
   private CustomersMapper customersMapper;
   @GetMapping
   public List<CustomersDto> getAllCustomers() {
       return customersService.getListAll().stream()
               .map(customersMapper::toDto)
               .collect(Collectors.toList());
   }
   @GetMapping("/{id}")
   public CustomersDto getCustomerById(@PathVariable Long id) {
       return customersMapper.toDto(customersService.get(id));
   }
   @PostMapping
   public CustomersDto addCustomer(@RequestBody CustomersDto customersDto) {
       Customers customer = customersMapper.toEntity(customersDto);
       Customers savedCustomer = customersService.save(customer);
       return customersMapper.toDto(savedCustomer);
   }
   @PutMapping("/{id}")
   public void updateCustomer(@PathVariable Long id, @RequestBody CustomersDto customersDto) {
       Customers customer = customersMapper.toEntity(customersDto);
       customer.setId(id);
       customersService.update(customer);
   }
   @DeleteMapping("/{id}")
   public void deleteCustomer(@PathVariable Long id) {
       customersService.delete(id);
   }
}