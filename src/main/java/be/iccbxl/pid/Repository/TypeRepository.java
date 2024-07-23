package be.iccbxl.pid.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import be.iccbxl.pid.Model.Type;

public interface TypeRepository extends CrudRepository<Type, Long> {
	Type findByType(String type);
	Optional<Type> findByType(Long id);
	
}
