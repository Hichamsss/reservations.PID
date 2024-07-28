package be.iccbxl.pid.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	
	@GetMapping ("/user")//Pour y associer une URL
	public String getUser() {
		return "Welcome, User";
	}
	
	@GetMapping ("/admin")
	public String getAdmin() {
		return "Welcome, Admin";
	}
}
