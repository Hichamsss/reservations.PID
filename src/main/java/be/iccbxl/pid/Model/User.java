package be.iccbxl.pid.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String login;
	private String password;
	private String firstname;
	private String lastname;
	private String email;
	private String langue;
	private LocalDateTime created_at;
	
	@ManyToMany(mappedBy = "users")
	private List<Role> roles = new ArrayList<>();
	
	public User addRole(Role role) {
		if(!this.roles.contains(role)) {
			this.roles.add(role);
			role.addUser(this);
		}
		
		return this;
	}
	
	public User removeRole(Role role) {
		if(this.roles.contains(role)) {
			this.roles.remove(role);
			role.getUsers().remove(this);
		}
		
		return this;
	}


}
