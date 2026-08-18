package Cinema.Movie.controller.admin;

import Cinema.Movie.entity.User;
import Cinema.Movie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/admin/profile")
public class AdminProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String showProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        Optional<User> userOpt = userRepository.findByUsername(currentUsername);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
        }

        return "profile/index";
    }

    @PostMapping("/update")
    public String updateProfileInfo(@RequestParam("username") String newUsername,
                                    @RequestParam("email") String newEmail,
                                    HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        Optional<User> userOpt = userRepository.findByUsername(currentUsername);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            
            if (!user.getUsername().equals(newUsername)) {
                if (userRepository.existsByUsername(newUsername)) {
                    return "redirect:/admin/profile?error=usernameTaken";
                }
            }

            user.setUsername(newUsername);
            user.setEmail(newEmail);
            userRepository.save(user);


            if (!currentUsername.equals(newUsername)) {
                request.getSession().invalidate();
                return "redirect:/login?logout";
            }
        }

        return "redirect:/admin/profile?success";
    }

    @PostMapping("/update-password")
    public String updatePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        Optional<User> userOpt = userRepository.findByUsername(currentUsername);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                return "redirect:/admin/profile?error=wrongPassword";
            }

            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }

        return "redirect:/admin/profile?passwordSuccess";
    }
}
 