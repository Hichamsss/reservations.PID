package be.iccbxl.pid.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import be.iccbxl.pid.Model.Artist;
import be.iccbxl.pid.Service.ArtistService;


@Controller
public class ArtistController {
	@Autowired
	ArtistService service;

	@GetMapping("/artists")
	public String index(Model model) {
	    List<Artist> artists = service.getAllArtists();

    	    model.addAttribute("artists", artists);
    	    model.addAttribute("title", "Les artistes");
		
            return "artist/index";
    }
	
	//On rajoute la méthode show pour recuperer l'artiste avec son id et l'afficher dans le template show.html 
	@GetMapping("/artists/{id}") // Explication p18
    public String show(Model model, @PathVariable("id") String id) {
	Artist artist = service.getArtist(id);

	model.addAttribute("artist", artist);
	model.addAttribute("title", "Fiche d'un artiste");
		
        return "artist/show";
    }


}

