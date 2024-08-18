package be.iccbxl.pid.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.iccbxl.pid.Model.User;
import be.iccbxl.pid.Security.CustomUserDetailService;
import be.iccbxl.pid.Security.CustomUserDetails;

@Controller
public class LoginController {

    @Autowired
    private CustomUserDetailService customUserDetailService;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login")
    public String login(@ModelAttribute("errorMessage") String errorMessage, 
                        @ModelAttribute("successMessage") String successMessage,
                        Model model) {
        if (errorMessage != null && !errorMessage.isEmpty()) {
            model.addAttribute("errorMessage", errorMessage);
        }
        if (successMessage != null && !successMessage.isEmpty()) {
            model.addAttribute("successMessage", successMessage);
        }
        return "login/login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute("loginUser") User loginUser, RedirectAttributes redirectAttributes) {
        CustomUserDetails user = (CustomUserDetails) customUserDetailService.loadUserByUsername(loginUser.getLogin());

        if (user != null && bCryptPasswordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
            redirectAttributes.addFlashAttribute("successMessage", "L'authentification a réussi.");
            return "redirect:/home";
        }

        redirectAttributes.addFlashAttribute("errorMessage", "L'authentification a échoué.");
        return "redirect:/login";
    }

}
