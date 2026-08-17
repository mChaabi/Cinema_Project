package Cinema.Movie.dto;

import Cinema.Movie.model.Personne;
import org.springframework.stereotype.Component;

@Component
public class PersonneMapper {

    public PersonneDto toDto(Personne personne) {
        if (personne == null) {
            return null;
        }
        return new PersonneDto(
            personne.getId(),
            personne.getNom(),
            personne.getPrenom(),
            personne.getDateNaissance(),
            personne.getTypePersonne(),
            personne.getPhoto()
        );
    }

    public Personne toEntity(PersonneDto personneDto) {
        if (personneDto == null) {
            return null;
        }
        Personne personne = new Personne();
        personne.setId(personneDto.id());
        personne.setNom(personneDto.nom());
        personne.setPrenom(personneDto.prenom());
        personne.setDateNaissance(personneDto.dateNaissance());
        personne.setTypePersonne(personneDto.typePersonne());
        personne.setPhoto(personneDto.photo());
        return personne;
    }
}