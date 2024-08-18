package be.iccbxl.pid.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import be.iccbxl.pid.Model.Show;
import be.iccbxl.pid.Service.ShowService;

@Controller
public class HomeController {

    @Autowired
    ShowService showService;

    @GetMapping("/home")
    public String home(Model model) {
        List<Show> shows = showService.getAll();
        model.addAttribute("shows", shows);
        return "home/home";
    }

}
