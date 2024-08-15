package be.iccbxl.pid.Service;

import be.iccbxl.pid.Model.Role;
import be.iccbxl.pid.Model.User;
import be.iccbxl.pid.Repository.RoleRepository;
import be.iccbxl.pid.Repository.UserRepository;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;

import java.util.List;



@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	public List<User> getAllUsers() {

		List<User> users = new ArrayList<>();

		userRepository.findAll().forEach(users::add);

		return users;
	}

	public User getUser(long id) {
		
		
		return userRepository.findById(id);
		}

	public void addUser(User user) {
        Role defaultRole = roleRepository.findByRole("member");

        if (defaultRole == null) {
            System.out.println("Le rôle par défaut 'member' n'a pas été trouvé dans la base de données.");
            return;
        }

        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.addRole(defaultRole);
            userRepository.save(user);
            System.out.println("L'utilisateur a été ajouté avec succès avec le rôle par défaut 'member'.");
        } catch (Exception e) {
            System.out.println("Une erreur s'est produite lors de l'ajout de l'utilisateur : " + e.getMessage());
        }
    }
	
	
	public void updateUser(String id, User user) {
		userRepository.save(user);
	}


	public void deleteUser(String id) {
		Long indice = (long) Integer.parseInt(id);
		userRepository.deleteById(indice);

	}

	public User findByLogin(String login) {

		return userRepository.findByLogin(login);
	}

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

}
