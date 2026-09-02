package Cinema.Movie.service;

import Cinema.Movie.model.Siege;
import Cinema.Movie.repository.SiegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SiegeService {

    @Autowired
    private SiegeRepository siegeRepository;

    public List<Siege> getAllSieges() {
        return siegeRepository.findAll();
    }

    public Optional<Siege> getSiegeById(Long id) {
        return siegeRepository.findById(id);
    }

    public Siege saveSiege(Siege siege) {
        return siegeRepository.save(siege);
    }

    public void deleteSiege(Long id) {
        siegeRepository.deleteById(id);
    }

    // Méthode spécifique
    public List<Siege> getSiegesBySeance(Long seanceId) {
        return siegeRepository.findBySeanceId(seanceId);
    }
}