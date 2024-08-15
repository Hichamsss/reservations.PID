package be.iccbxl.pid.Repository;

import org.springframework.data.repository.CrudRepository;

import be.iccbxl.pid.Model.Locality;

public interface LocalityRepository extends CrudRepository<Locality, Long> {
	Locality findByPostalCode(String postalCode);
	Locality findByLocality(String locality);
}
