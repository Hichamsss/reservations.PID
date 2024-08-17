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
        model.addAttribute("user", User.createInstance());
        return "register/registration";
    }

    @PostMapping("/register")
    public String processRegistrationForm(
            @RequestParam String password, 
            @RequestParam String confirmPassword,
            @ModelAttribute @Valid User user, 
            BindingResult result, 
            Model model,
            RedirectAttributes redirectAttributes) {

        // Vérification des mots de passe
        if (!password.equals(confirmPassword)) {
            model.addAttribute("errorPassword", "Les mots de passe ne correspondent pas.");
            return "register/registration";
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register/registration";
        }

        // Vérification de l'existence de l'email
        if (userService.existsByEmail(user.getEmail())) {
            model.addAttribute("errorEmail", "Cet email est déjà utilisé. Veuillez en choisir un autre.");
            return "register/registration";
        }

        try {
            // Ajouter l'utilisateur
            userService.addUser(user);

            // Ajouter un message de succès dans les attributs de redirection
            redirectAttributes.addFlashAttribute("successMessage", "Création du compte réussie !");

            String confirmationSubject = "Confirmation d'inscription";

            String sb = "Bienvenue " + user.getLogin() + "\n" +
                    "Merci pour votre inscription.";

            // Envoyer l'e-mail de confirmation
            emailService.sendConfirmationEmail(user.getEmail(), confirmationSubject, sb);

            return "redirect:/register";

        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("errorLogin", "Une erreur s'est produite lors de l'inscription. Veuillez réessayer.");
            return "register/registration";
        }
    }
}
