package be.iccbxl.pid.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


@Entity // Permet de définir la classe comme modèle mappé
@Table(name="artists") // Permet de spécifier le nom que portera la table sans quoi le nom par défaut sera l'entité
public class Artist {
	@Id // indique quelle propiété contient l'identifiant unique qui sera la clé primaire de la table
	@GeneratedValue(strategy=GenerationType.IDENTITY) // Valeur générée automatiquement
	private Long id;
	
	@NotEmpty(message = "The firstname must not be empty.")
	@Size(min=2, max=60, message = "The firstname must be between 2 and 60 characters long.")
	private String firstname;
	
	@NotEmpty(message = "The lastname must not be empty.")
	@Size(min=2, max=60, message = "The lastname must be between 2 and 60 characters long.")
	private String lastname;

	
	@ManyToMany(mappedBy = "artists")
	private List<Type> types = new ArrayList<>();
	
	protected Artist() {}

	public Artist(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public List<Type> getTypes() {
		return types;
	}

	public Artist addType(Type type) {
		if(!this.types.contains(type)) {
			this.types.add(type);
			type.addArtist(this);
		}
		
		return this;
	}
	
	public Artist removeType(Type type) {
		if(this.types.contains(type)) {
			this.types.remove(type);
			type.getArtists().remove(this);
		}
		
		return this;
	}
	@Override
	public String toString() {
		return firstname + " " + lastname;
	}
}

