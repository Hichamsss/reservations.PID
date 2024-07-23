package be.iccbxl.pid.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import be.iccbxl.pid.Model.Locality;

public interface LocalityRepository extends CrudRepository<Locality, Long> {
	Locality findBypostaLocality (String postalCode);
	Optional<Locality> findByLocality (String locality);

}
