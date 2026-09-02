package Cinema.Movie.dto;

import java.util.List;
import java.util.Map;

public record DashboardDto(
        List<FilmDto> films,
        List<SalleDto> salles,
        List<SeanceDto> seances,
        long totalReservations,      // ← nuevo
        long totalBilletsVendus,     // ← nuevo (suma de nbPlaces de todas las reservations CONFIRMEE)
        Map<String, Integer> occupationParSalle// ← nuevo: salleId -> % ocupación hoy
) {}
