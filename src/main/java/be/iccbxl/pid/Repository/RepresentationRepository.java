package be.iccbxl.pid.Repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import be.iccbxl.pid.Model.Location;
import be.iccbxl.pid.Model.Representation;
import be.iccbxl.pid.Model.Show;

public interface RepresentationRepository extends CrudRepository<Representation, Long> {
	List<Representation> findByShow(Show show);
	List<Representation> findByLocation(Location location);
	List<Representation> findByWhen(LocalDateTime when);
}

