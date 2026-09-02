package Cinema.Movie.dto;

import java.util.List;

public record ReservationRequestDto(
        Long seanceId,
        Long customerId,
        int nbrPlaces,
        List<Long> siegeIds // Souvent on envoie juste les IDs des sièges lors de la création
) {}