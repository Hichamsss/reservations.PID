package be.iccbxl.pid.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import be.iccbxl.pid.Model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findByLastname(String lastname);

	User findById(long id);
	User findByLogin(String login);
	User findByEmail(String email);
	
	boolean existsByEmail(String email);

}
