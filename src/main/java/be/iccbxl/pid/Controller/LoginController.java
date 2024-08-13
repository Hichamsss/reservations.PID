package be.iccbxl.pid.Controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String login(Model model) {
    	
        return "login/login";
    }
    
    

    @PostMapping("/login")
    public String processLogin(@ModelAttribute("loginUser") User loginUser, Model model) {
        CustomUserDetails user = (CustomUserDetails) customUserDetailService.loadUserByUsername(loginUser.getLogin());

        if (user != null && bCryptPasswordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
            System.out.println("User role: " + user.getAuthorities());
            return "redirect:/home";
        }

        return "redirect:/login?error";
    }


    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "login/forgot-password";
    }    

}
    

