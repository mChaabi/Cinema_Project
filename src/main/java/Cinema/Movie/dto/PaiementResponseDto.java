package Cinema.Movie.dto;

import java.math.BigDecimal;

public record PaiementResponseDto(Long id, Long reservationId, BigDecimal amount, String status) {}
