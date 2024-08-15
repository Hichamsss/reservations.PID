package be.iccbxl.pid.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChoixController {

    @PostMapping("/choixPlaces")
    public String choixPlaces(@RequestParam("representationWhen") String representationWhen,
                              @RequestParam("nbPlaces") int nbPlaces,
                              Model model) {
        // Ajouter les informations nécessaires au modèle
        model.addAttribute("representationWhen", representationWhen);
        model.addAttribute("nbPlaces", nbPlaces);

        // Rediriger vers la vue choix.html
        return "reservation/choix"; 
    }
}
