package Cinema.Movie.dto;

import Cinema.Movie.model.Salle;
import org.springframework.stereotype.Component;

@Component
public class SalleMapper {

    public SalleDto toDto(Salle salle) {
        if (salle == null) {
            return null;
        }
        return new SalleDto(salle.getId(), salle.getNumero(), salle.getCapacite());
    }

    public Salle toEntity(SalleDto salleDto) {
        if (salleDto == null) {
            return null;
        }
        Salle salle = new Salle();
        salle.setId(salleDto.id());
        salle.setNumero(salleDto.numero());
        salle.setCapacite(salleDto.capacite());
        return salle;
    }
}