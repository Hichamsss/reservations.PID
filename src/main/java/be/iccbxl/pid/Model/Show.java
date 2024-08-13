package be.iccbxl.pid.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.slugify.Slugify;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@Entity
@Table(name="shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String slug;

    private String title;
    
    private String description;

    @Column(name="poster_url")
    private String posterUrl;

    /**
     * Lieu de création du spectacle
     */
    @ManyToOne
    @JoinColumn(name="location_id", nullable=true)
    private Location location;

    private boolean bookable;
    
    private double price;

    /**
     * Date de création du spectacle
     */
    @Column(name="created_at")
    private LocalDateTime createdAt;

    /**
     * Date de modification du spectacle
     */
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(targetEntity=Representation.class, mappedBy="show")
    private List<Representation> representations = new ArrayList<>();

    @ManyToMany(mappedBy = "shows")
    private List<ArtistType> artistTypes = new ArrayList<>();



    public Show(String title, String description, String posterUrl, Location location, boolean bookable,
                double price) {
    	this.title = title;
    	
        Slugify slg = new Slugify();
        this.slug = slg.slugify(title);
        this.description = description;
        this.posterUrl = posterUrl;
        this.location = location;
        this.bookable = bookable;
        this.price = price;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    public void setTitle(String title) {
        this.title = title;
        Slugify slg = new Slugify();
        this.slug = slg.slugify(title);

    }


    public void setLocation(Location location) {
        this.location.removeShow(this);	//déménager de l’ancien lieu
        this.location = location;
        this.location.addShow(this);		//emménager dans le nouveau lieu
    }

    public Show addRepresentation(Representation representation) {
        if(!this.representations.contains(representation)) {
            this.representations.add(representation);
            representation.setShow(this);
        }

        return this;
    }

    public Show removeRepresentation(Representation representation) {
        if(this.representations.contains(representation)) {
            this.representations.remove(representation);
            if(representation.getLocation().equals(this)) {
                representation.setLocation(null);
            }
        }

        return this;
    }

    public Show addArtistType(ArtistType artistType) {
        if(!this.artistTypes.contains(artistType)) {
            this.artistTypes.add(artistType);
            artistType.addShow(this);
        }

        return this;
    }

    public Show removeArtistType(ArtistType artistType) {
        if(this.artistTypes.contains(artistType)) {
            this.artistTypes.remove(artistType);
            artistType.getShows().remove(this);
        }

        return this;
    }
    
    public static Show createInstance() {
        return new Show();
    }



}
