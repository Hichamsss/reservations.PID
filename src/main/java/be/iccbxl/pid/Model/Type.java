package be.iccbxl.pid.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor ( force = true, access = AccessLevel.PROTECTED)
@Entity
@Table(name="types")
public class Type {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String type;
	
	@ManyToMany
	@JoinTable(
		  name = "artist_type", 
		  joinColumns = @JoinColumn(name = "type_id"), 
		  inverseJoinColumns = @JoinColumn(name = "artist_id"))
	private List<Artist> artists = new ArrayList<>();

	
	public List<Artist> getArtists() {
		return artists;
	}

	public Type addArtist(Artist artist) {
		if(!this.artists.contains(artist)) {
			this.artists.add(artist);
			artist.addType(this);
		}
		
		return this;
	}
	
	public Type removeType(Artist artist) {
		if(this.artists.contains(artist)) {
			this.artists.remove(artist);
			artist.getTypes().remove(this);
		}
		
		return this;
	}


}

