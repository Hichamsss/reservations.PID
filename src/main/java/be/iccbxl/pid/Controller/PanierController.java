package be.iccbxl.pid.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/panier")
public class PanierController {

    @PostMapping("/goToPayment")
    public String goToPayment(@RequestParam("representationWhen") String representationWhen,
                              @RequestParam("selectedSeats") String selectedSeats,
                              @RequestParam("nbPlaces") int nbPlaces,
                              Model model) {
        // Ajouter les informations de réservation au modèle
        model.addAttribute("representationWhen", representationWhen);
        model.addAttribute("selectedSeats", selectedSeats);
        model.addAttribute("nbPlaces", nbPlaces);

        // Rediriger vers la page de paiement
        return "panier/payement"; // Indique la vue à rendre dans le dossier templates/panier/
    }
}
