package be.iccbxl.pid.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor (force = true , access = AccessLevel.PROTECTED)
@Entity
@Table(name="representations")
public class Representation {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name="show_id", nullable=false)
	private Show show;

	/**
	 * Date de création de la représentation
	 */
	private LocalDateTime when;
	
	/**
	 * Lieu de prestation de la représentation
	 */
	@ManyToOne
	@JoinColumn(name="location_id", nullable=true)
	private Location location;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(
		  name = "reservations", 
		  joinColumns = @JoinColumn(name = "representation_id"), 
		  inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users = new ArrayList<>();

	
	public List<User> getUsers() {
		return users;
	}

	public Representation addUser(User user) {
		if(!this.users.contains(user)) {
			this.users.add(user);
			user.addRepresentation(this);
		}
		
		return this;
	}
	
	public Representation removeUser(User user) {
		if(this.users.contains(user)) {
			this.users.remove(user);
			user.getRepresentations().remove(this);
		}
		
		return this;
	}

	
}
