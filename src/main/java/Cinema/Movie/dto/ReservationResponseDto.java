package Cinema.Movie.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationResponseDto(
        Long id,
        Long seanceId,
        Long customerId,
        int nbrPlaces,
        LocalDateTime dateReservation,
        List<SiegeResponseDto> sieges
) {}
