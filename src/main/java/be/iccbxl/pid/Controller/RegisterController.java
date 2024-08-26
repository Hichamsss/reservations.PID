package be.iccbxl.pid.Controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import be.iccbxl.pid.Model.User;
import be.iccbxl.pid.Service.EmailService;
import be.iccbxl.pid.Service.UserService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/register")
    public String register(Model model) {
        // Créez une nouvelle instance d'utilisateur et l'ajoutez au modèle
        model.addAttribute("user", User.createInstance());
        return "register/registration";
    }

    @PostMapping("/register")
    public String processRegistrationForm(
            @RequestParam String password, 
            @ModelAttribute @Valid User user, 
            BindingResult result, 
            Model model,
            RedirectAttributes redirectAttributes) {

        // Vérifier si le mot de passe est vide
        if (password == null || password.isEmpty()) {
            return "register/registration";
        }

        // Vérifier les erreurs de validation
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register/registration";
        }

        // Vérifier si l'email existe déjà
        if (userService.existsByEmail(user.getEmail())) {
            model.addAttribute("errorEmail", "Cet email est déjà utilisé. Veuillez en choisir un autre.");
            return "register/registration";
        }

        try {
            // Ajouter l'utilisateur à la base de données
            userService.addUser(user);

            // Ajouter un message de succès aux attributs de redirection
            redirectAttributes.addFlashAttribute("successMessage", "Création du compte réussie !");

            // Préparer les détails de l'e-mail de confirmation
            String confirmationSubject = "Confirmation d'inscription";
            String confirmationMessage = "Bienvenue " + user.getLogin() + "\n" +
                    "Merci de vous être inscrit sur ShopUpNow.";

            // Envoyer l'e-mail de confirmation
            emailService.sendConfirmationEmail(user.getEmail(), confirmationSubject, confirmationMessage);

            // Rediriger vers la page d'inscription
            return "redirect:/register";

        } catch (DataIntegrityViolationException ex) {
            // En cas d'erreur, afficher un message d'erreur
            model.addAttribute("errorLogin", "Une erreur s'est produite lors de l'inscription. Veuillez réessayer.");
            return "register/registration";
        }
    }
}
