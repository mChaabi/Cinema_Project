package Cinema.Movie.dto;

import Cinema.Movie.model.Siege;

public class SiegeMapper {

    public record SiegeDto(Long id, String numero, Long seanceId, Boolean isReserved) {}

    public static SiegeDto toDto(Siege siege, boolean isReserved) {
        if (siege == null) return null;
        return new SiegeDto(
                siege.getId(),
                siege.getNumero(),
                siege.getSeanceId(), // El Long seanceId que espera la 3ª posición
                isReserved           // El Boolean que espera la 4ª posición
        );}

    public static Siege toEntity(SiegeDto dto) {
        if (dto == null) return null;
        Siege siege = new Siege();
        siege.setId(dto.id());
        siege.setNumero(dto.numero());
        siege.setSeanceId(dto.seanceId());
        siege.setIsReserved(dto.isReserved());
        return siege;
    }
}
