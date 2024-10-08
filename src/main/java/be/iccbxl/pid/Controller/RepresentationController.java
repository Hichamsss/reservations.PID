package be.iccbxl.pid.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import be.iccbxl.pid.Model.Representation;
import be.iccbxl.pid.Service.RepresentationService;

@Controller
public class RepresentationController {
	@Autowired
	RepresentationService representationservice;

	@GetMapping("/representations")
public String index(Model model) {
		List<Representation> representations = representationservice.getAll();

		model.addAttribute("representations", representations);
		model.addAttribute("title", "Liste des representations");
		
        	return "representation/index";
}
	
	@GetMapping("/representations/{id}")
public String show(Model model, @PathVariable("id") long id) {
		Representation representation = representationservice.get(id);

		model.addAttribute("representation", representation);
		model.addAttribute("date", representation.getWhen().toLocalDate());
		model.addAttribute("heure", representation.getWhen().toLocalTime());
		model.addAttribute("title", "Fiche d'une representation");
		
        	return "representation/show";
	}

}

