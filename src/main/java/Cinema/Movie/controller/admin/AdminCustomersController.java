package Cinema.Movie.controller.admin;

import Cinema.Movie.dto.UserDto;
import Cinema.Movie.entity.Role;
import Cinema.Movie.entity.User;
import Cinema.Movie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/customers")
public class AdminCustomersController {

    @Autowired
    private UserService userService;

    private UserDto toDto(User user) {
    	return new UserDto(user.getId(), user.getUsername(), user.getEmail(), (String) null, user.getRole());
    }

    private User toEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.id());
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setRole(Role.USER); 
        return user;
    }

    @GetMapping
    public String listCustomers(Model model) {
        List<UserDto> customers = userService.getListAll().stream()
                .filter(u -> "USER".equals(u.getRole()))
                .map(this::toDto)
                .collect(Collectors.toList());

        model.addAttribute("customers", customers);
        return "customers/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("customer", new UserDto(null, "", "", "", Role.USER));
        return "customers/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        User user = userService.get(id);
        UserDto dto = toDto(user);
        model.addAttribute("customer", dto);
        return "customers/form";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute("customer") UserDto dto) {
        User customer = toEntity(dto);

        if (customer.getId() != null) {
            userService.update(customer);
        } else {
            if (customer.getPassword() == null || customer.getPassword().isEmpty()) {
                return "redirect:/admin/customers/add?error=passwordRequired";
            }
            userService.save(customer);
        }

        return "redirect:/admin/customers?success";
    }

    @PostMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/customers?deleted";
    }
}
 