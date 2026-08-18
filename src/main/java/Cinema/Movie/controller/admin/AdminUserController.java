package Cinema.Movie.controller.admin;

import Cinema.Movie.dto.UserDto;
import Cinema.Movie.entity.User;
import Cinema.Movie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    private UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), null, user.getRole());
    }

    private User toEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.id());
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setRole("ADMIN"); 
        return user;
    }

    @GetMapping
    public String listUsers(Model model) {
        List<UserDto> users = userService.getListAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new UserDto(null, "", "", "", "ADMIN"));
        return "users/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        User user = userService.get(id);
        UserDto dto = toDto(user);
        model.addAttribute("user", dto);
        return "users/form";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") UserDto dto) {
        User user = toEntity(dto);

        if (user.getId() != null) {
            userService.update(user);
        } else {
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                return "redirect:/admin/users/add?error=passwordRequired";
            }
            userService.save(user);
        }

        return "redirect:/admin/users?success";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/users?deleted";
    }
}
 