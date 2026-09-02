package Cinema.Movie.controller;
import Cinema.Movie.dto.DashboardDto;
import Cinema.Movie.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardDto> getDashboardData() {
        DashboardDto dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(dashboardData);
    }
}