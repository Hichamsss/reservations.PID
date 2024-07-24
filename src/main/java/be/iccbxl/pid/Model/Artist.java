package be.iccbxl.pid.Model;

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

@Data // Raccourci pour @Getter, @Setter, @ToString, @EqualsAndHashCode, RequiredArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@Entity // Permet de définir la classe comme modèle mappé
@Table(name="artists") // Permet de spécifier le nom que portera la table sans quoi le nom par défaut sera l'entité
public class Artist {
	@Id // indique quelle propiété contient l'identifiant unique qui sera la clé primaire de la table
	@GeneratedValue(strategy=GenerationType.AUTO) // Valeur générée automatiquement
	private Long id;
	private String firstname;
	private String lastname;
	
	@ManyToMany(mappedBy = "artists")
	private List<Type> types = new ArrayList<>();

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

