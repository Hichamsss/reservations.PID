package be.iccbxl.pid.Controller;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import be.iccbxl.pid.Model.Representation;
import be.iccbxl.pid.Model.Show;
import be.iccbxl.pid.Security.CustomUserDetails;
import be.iccbxl.pid.Service.RepresentationService;
import jakarta.servlet.http.HttpSession;

@Controller
public class ReservationController {
	
	@Autowired
	private RepresentationService representationService;

	@PostMapping("/confirmReservation")
	public String confirmReservation(@RequestParam("nbPlaces") int nbPlaces, 
	        @RequestParam("representationWhen") LocalDateTime representationWHen,
	        HttpSession session, 
	        Model model) {

	    // Utilisez la date et l'heure pour retrouver la représentation associée
	    Representation representation = representationService.getByWhen(representationWHen);
	    Show show = representation.getShow(); 

	    // Récupérer les informations de l'utilisateur connecté
	    CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String username = customUserDetails.getUsername();

        double total = representation.getShow().getPrice() * nbPlaces;

	    
	    
	    // Ajouter les informations récupérées au modèle pour la page de confirmation
	    model.addAttribute("nbPlaces", nbPlaces);
	    model.addAttribute("username", username);
	    model.addAttribute("representation", representation);
	    model.addAttribute("show", show);

	    session.setAttribute("nbPlaces", nbPlaces);
	    session.setAttribute("representationId", representation.getId());
        session.setAttribute("total", total); 
        session.setAttribute("userId", customUserDetails.getId()); 



	    return "reservation/confirmation"; // Redirection vers une vue de confirmation
	}
	
	

	
}
