package Cinema.Movie.dto;

import Cinema.Movie.model.Paiement;
import java.math.BigDecimal;

public class PaiementMapper {

    public record PaiementDto(Long id, Long reservationId, BigDecimal amount, String status) {}

    public static PaiementDto toDto(Paiement paiement) {
        if (paiement == null) return null;
        return new PaiementDto(
                paiement.getId(),
                paiement.getReservationId(),
                paiement.getAmount(),
                paiement.getStatus()
        );
    }

    public static Paiement toEntity(PaiementDto dto) {
        if (dto == null) return null;
        Paiement paiement = new Paiement();
        paiement.setId(dto.id());
        paiement.setReservationId(dto.reservationId());
        paiement.setAmount(dto.amount());
        paiement.setStatus(dto.status());
        return paiement;
    }
}
