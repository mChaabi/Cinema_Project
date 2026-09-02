package Cinema.Movie.dto;

import Cinema.Movie.model.Reservation;
import Cinema.Movie.model.Siege;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationMapper {

    public record ReservationDto(
            Long id,
            Long seanceId,
            Long customerId,
            int nbrPlaces,
            LocalDateTime dateReservation,
            List<SiegeMapper.SiegeDto> sieges
    ) {}

    public static ReservationDto toDto(Reservation reservation) {
        if (reservation == null) return null;

        List<SiegeMapper.SiegeDto> siegeDtos = reservation.getSieges() != null ?
                reservation.getSieges().stream()
                        .map(siege -> SiegeMapper.toDto(siege, true)) // ✅ Solución con lambda
                        .collect(Collectors.toList())
                : List.of();

        return new ReservationDto(
                reservation.getId(),
                reservation.getSeanceId(),
                reservation.getCustomerId(),
                reservation.getNbrPlaces(),
                reservation.getDateReservation(),
                siegeDtos
        );
    }

    public static Reservation toEntity(ReservationDto dto, List<Siege> sieges) {
        if (dto == null) return null;

        Reservation reservation = new Reservation();
        reservation.setId(dto.id());
        reservation.setSeanceId(dto.seanceId());
        reservation.setCustomerId(dto.customerId());
        reservation.setNbrPlaces(dto.nbrPlaces());
        reservation.setDateReservation(dto.dateReservation());
        reservation.setSieges(sieges != null ? sieges : List.of());

        return reservation;
    }
}