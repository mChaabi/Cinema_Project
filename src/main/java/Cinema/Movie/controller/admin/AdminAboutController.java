package Cinema.Movie.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/about")
public class AdminAboutController {

    @GetMapping
    public String about() {
        return "aboutus/list";
    }
}
 