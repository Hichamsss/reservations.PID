package be.iccbxl.pid.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import be.iccbxl.pid.Model.User;
import be.iccbxl.pid.Repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;
	
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		
		repository.findAll().forEach(users::add);
		
		return users;
	}
	
	public User getUser(long id) {
		
		return repository.findById(id);
	}

	public void addUser(User user) {
		repository.save(user);
	}

	public void updateUser(String id, User user) {
		repository.save(user);
	}

	public void deleteUser(String id) {
		Long indice = (long) Integer.parseInt(id);
		
		repository.deleteById(indice);
	}
}
