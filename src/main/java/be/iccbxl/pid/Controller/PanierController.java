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
import java.time.format.DateTimeParseException;

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
        try {
            // Convertir le paramètre representationWhen en LocalDateTime
            LocalDateTime when = LocalDateTime.parse(representationWhen);

            // Récupérer l'objet Representation correspondant
            Representation representation = representationRepository.findByWhen(when);

            if (representation == null || representation.getShow() == null) {
                model.addAttribute("error", "La représentation spécifiée n'existe pas.");
                return "panier/error"; // Rediriger vers une page d'erreur personnalisée
            }

            // Valider le nombre de places réduites
            if (nbReducedPlaces > nbPlaces) {
                nbReducedPlaces = nbPlaces; // Limiter le nombre de réductions au nombre total de places
            }

            double fullPrice = representation.getShow().getPrice();
            double reducedPrice = fullPrice * 0.7;
            double totalPrice = (nbPlaces - nbReducedPlaces) * fullPrice + nbReducedPlaces * reducedPrice;

            // Formater le totalPrice pour qu'il ait deux chiffres après la virgule
            String formattedTotalPrice = String.format("%.2f", totalPrice);

            // Ajouter les informations de réservation au modèle
            model.addAttribute("representationWhen", representationWhen);
            model.addAttribute("selectedSeats", selectedSeats);
            model.addAttribute("nbPlaces", nbPlaces);
            model.addAttribute("nbReducedPlaces", nbReducedPlaces);
            model.addAttribute("totalPrice", formattedTotalPrice); // Ajouter le prix total formaté au modèle

            // Rediriger vers la page de paiement
            return "panier/payement"; // Indique la vue à rendre dans le dossier templates/panier/

        } catch (DateTimeParseException e) {
            model.addAttribute("error", "La date et l'heure spécifiées ne sont pas valides.");
            return "panier/error"; // Rediriger vers une page d'erreur personnalisée
        }
    }
}
