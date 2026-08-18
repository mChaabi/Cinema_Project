package Cinema.Movie.dto;

import Cinema.Movie.model.Nationalite;
import org.springframework.stereotype.Component;

@Component
public class NationaliteMapper {

    // Convertir Entité -> DTO
    public NationaliteDto toDto(Nationalite nationalite) {
        if (nationalite == null) {
            return null;
        }
        return new NationaliteDto(nationalite.getId(), nationalite.getLibelle());
    }

    // Convertir DTO -> Entité
    public Nationalite toEntity(NationaliteDto nationaliteDto) {
        if (nationaliteDto == null) {
            return null;
        }
        Nationalite nationalite = new Nationalite();
        nationalite.setId(nationaliteDto.id());
        nationalite.setLibelle(nationaliteDto.libelle());
        return nationalite;
    }
}