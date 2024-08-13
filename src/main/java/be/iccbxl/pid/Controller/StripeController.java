package be.iccbxl.pid.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import be.iccbxl.pid.Model.Representation;
import be.iccbxl.pid.Model.User;
import be.iccbxl.pid.Service.RepresentationService;
import be.iccbxl.pid.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class StripeController {


	@Autowired
    private RepresentationService representationService; 
	
	@Autowired
    private UserService userService; // Assurez-vous d'avoir initialisé votre service


	@PostMapping("/checkout")
	public String checkout(Model model, HttpServletRequest request, HttpSession session) {
        double total =  (double) session.getAttribute("total");
        int nbPlaces = (int) session.getAttribute("nbPlaces");
        Long representationId = (Long) session.getAttribute("representationId");
        Long userId = (Long) session.getAttribute("userId");

        Representation representation = representationService.get(representationId);
        User user = userService.getUser(userId);

        


        model.addAttribute("total", total);
        model.addAttribute("nbPlaces", nbPlaces);
        model.addAttribute("representation", representation);
        model.addAttribute("user", user);



		return "stripe/checkout";
	}
	
	@GetMapping("stripe/return")
	public String returnUrl() {
		return "stripe/return";
	}

	
	
}
