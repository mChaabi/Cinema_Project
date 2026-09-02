package Cinema.Movie.dto;

import java.math.BigDecimal;

public record PaiementRequestDto(Long reservationId, BigDecimal amount, String status) {}
