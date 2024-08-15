package be.iccbxl.pid.Repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import be.iccbxl.pid.Model.Location;
import be.iccbxl.pid.Model.Show;

public interface ShowRepository extends CrudRepository<Show, Long> {
	Show findBySlug(String slug);
	Show findByTitle(String title);
	List<Show> findByLocation(Location location);
}

