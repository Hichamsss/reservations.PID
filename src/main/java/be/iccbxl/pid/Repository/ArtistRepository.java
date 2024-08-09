package be.iccbxl.pid.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import be.iccbxl.pid.Model.Artist;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
	List<Artist> findByLastname(String lastname);

	Artist findById(long id);
}
