package be.iccbxl.pid.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
}
