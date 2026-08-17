package Cinema.Movie.service;

import Cinema.Movie.model.Personne;
import Cinema.Movie.repository.PersonneRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonneService extends AbstractService<Personne, Long> {

    @Autowired
    private PersonneRepository personneRepository;

    @Override
    protected JpaRepository<Personne, Long> getRepository() {
        return personneRepository;
    }
 
}
