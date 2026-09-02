// dto/ReservationResponseDto.java
package Cinema.Movie.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ReservationResponseDto(
        Long id,
        LocalDateTime dateReservation,
        Integer nbrPlaces,
        String statut,
        Long seanceId,
        String filmTitre,
        String filmPhotoUrl,
        Integer salleNumero,
        String dateProjection,
        String heureDebut,
        List<String> siegesNumeros,
        BigDecimal montantPaye,
        String statutPaiement
) {}