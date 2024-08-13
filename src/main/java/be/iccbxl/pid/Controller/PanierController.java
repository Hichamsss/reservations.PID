package be.iccbxl.pid.Controller;

import be.iccbxl.pid.Model.Representation;
import be.iccbxl.pid.Repository.RepresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/panier")
public class PanierController {

    @Autowired
    private RepresentationRepository representationRepository;

    @PostMapping("/goToPayment")
    public String goToPayment(@RequestParam("representationWhen") String representationWhen,
                              @RequestParam("selectedSeats") String selectedSeats,
                              @RequestParam("nbPlaces") int nbPlaces,
                              @RequestParam("nbReducedPlaces") int nbReducedPlaces,
                              Model model) {
        // Convertir le paramètre representationWhen en LocalDateTime
        LocalDateTime when = LocalDateTime.parse(representationWhen);

        // Récupérer l'objet Representation correspondant
        Representation representation = representationRepository.findByWhen(when);

        double totalPrice = 0;
        if (representation != null && representation.getShow() != null) {
            double fullPrice = representation.getShow().getPrice();
            double reducedPrice = fullPrice * 0.7;
            totalPrice = (nbPlaces - nbReducedPlaces) * fullPrice + nbReducedPlaces * reducedPrice;
        }

        // Ajouter les informations de réservation au modèle
        model.addAttribute("representationWhen", representationWhen);
        model.addAttribute("selectedSeats", selectedSeats);
        model.addAttribute("nbPlaces", nbPlaces);
        model.addAttribute("nbReducedPlaces", nbReducedPlaces);
        model.addAttribute("totalPrice", totalPrice); // Ajouter le prix total au modèle

        // Rediriger vers la page de paiement
        return "panier/payement"; // Indique la vue à rendre dans le dossier templates/panier/
    }
}
