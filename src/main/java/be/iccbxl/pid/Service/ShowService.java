package be.iccbxl.pid.Service;

import be.iccbxl.pid.Dto.ShowDTO;
import be.iccbxl.pid.Model.Location;
import be.iccbxl.pid.Model.Show;
import be.iccbxl.pid.Repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShowService {
    @Autowired
    private ShowRepository repository;

    public List<Show> getAll() {
        List<Show> shows = new ArrayList<>();
        repository.findAll().forEach(shows::add);
        return shows;
    }

    public Show get(String id) {
        Long indice = Long.parseLong(id);
        Optional<Show> show = repository.findById(indice);
        return show.orElse(null);
    }

    public void add(Show show) {
        repository.save(show);
    }

    public void update(String id, Show show) {
        repository.save(show);
    }

    public void delete(String id) {
        Long indice = Long.parseLong(id);
        repository.deleteById(indice);
    }

    public List<Show> getFromLocation(Location location) {
        return repository.findByLocation(location);
    }

    public List<Show> getAllShowsSortedByTitle() {
        List<Show> shows = getAll();
        shows.sort(Comparator.comparing(Show::getTitle));
        return shows;
    }

    // Nouvelle méthode pour obtenir les Shows sous forme de DTO
    public List<ShowDTO> getAllShowDTOs() {
        return getAll().stream()
            .map(ShowDTO::new)
            .collect(Collectors.toList());
    }
}
